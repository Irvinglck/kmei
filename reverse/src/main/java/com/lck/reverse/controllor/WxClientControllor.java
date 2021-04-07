package com.lck.reverse.controllor;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lck.reverse.entity.ConstEnum;
import com.lck.reverse.entity.TConsult;
import com.lck.reverse.entity.TProAttribute;
import com.lck.reverse.entity.respon.ResultMessage;
import com.lck.reverse.service.WxClientService;
import com.lck.reverse.service.impl.TProAttributeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@RequestMapping("/client")
public class WxClientControllor {

    @Autowired
    private WxClientService wxClientService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TProAttributeServiceImpl tProAttributeService;

    @Value("${km.mutil.func.machine}")
    private String MUTIL_FUNC_URL;

    //打印机 / 一体机
    @Value("${km.print.Integrated.machine}")
    private String PRINT_INTEGRATED_URL;

    //多功能复合机
    @Value("${km.oos.secretId}")
    private String sid;
    @Value("${km.oos.secretKey}")
    private String sKey;


    @PostMapping("/consult")
    public ResultMessage consult(@RequestBody TConsult consult) {

        System.out.println(consult.toString());
        return null;
    }

    @GetMapping("/one")
    public ResultMessage consult(@RequestParam(name = "id") Integer id) {
        System.out.println(wxClientService.getOne(id));
        return new ResultMessage().setData(wxClientService.getOne(1)).setCode(200);
    }


    @GetMapping("/print")
    public ResultMessage print(
            @RequestParam(name = "printType") String printType //mutil--多功能机器
    ) {
        //打印类型
        String typeMachine = printType.equals(ConstEnum.MACHINE_TYPE.getMsg()) ? MUTIL_FUNC_URL : PRINT_INTEGRATED_URL;
        String machineData = null;
        try {
            machineData = restTemplate.getForObject(typeMachine, String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(machineData))
            return new ResultMessage().setMsg("调用柯美接口异常****" + typeMachine).setCode(500);
        JSONObject jsonObject = JSON.parseObject(machineData);
        Object message = jsonObject.get("message");
        if (ConstEnum.SUCCESS.getMsg().equals(message)) {
            JSONArray arr = (JSONArray) jsonObject.get("data");
            List<TProAttribute> pros = JSON.parseObject(arr.toJSONString(), new TypeReference<List<TProAttribute>>() {
            });
//            tProAttributeService.saveOrUpdateBatch(pros);
            for (TProAttribute pro : pros) {
                tProAttributeService.saveOrUpdate(pro,new UpdateWrapper<TProAttribute>().lambda().eq(TProAttribute::getId,pro.getId()));
            }

        }
        return new ResultMessage().setMsg("调转柯美多接口异常********" + typeMachine).setCode(500);
    }
}
