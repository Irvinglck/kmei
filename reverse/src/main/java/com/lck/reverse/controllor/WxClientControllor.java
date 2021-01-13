package com.lck.reverse.controllor;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.lck.reverse.entity.ConstEnum;
import com.lck.reverse.entity.TConsult;
import com.lck.reverse.entity.TProAttribute;
import com.lck.reverse.entity.respon.CommonsResult;
import com.lck.reverse.service.WxClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@RequestMapping("/client")
public class WxClientControllor {

    @Autowired
    private WxClientService wxClientService;

    @Autowired
    private RestTemplate restTemplate;



    //多功能复合机
    @Value("${km.mutil.func.machine}")
    private String MUTIL_FUNC;

    //打印机 / 一体机
    @Value("${km.print.Integrated.machine}")
    private String PRINT_INTEGRATED;

    @PostMapping("/consult")
    public CommonsResult consult(@RequestBody TConsult consult) {

        System.out.println(consult.toString());
        return null;
    }

    @GetMapping("/one")
    public CommonsResult consult(@RequestParam(name = "id") Integer id) {
        System.out.println(wxClientService.getOne(id));
        return new CommonsResult().setData(wxClientService.getOne(1)).setId(200);
    }


    @GetMapping("/tempClient")
    public CommonsResult consult() {
        String mutiFunc = restTemplate.getForObject(MUTIL_FUNC,String.class);
        JSONObject jsonObject = JSON.parseObject(mutiFunc);
        Object message = jsonObject.get("message");
        if(ConstEnum.SUCCESS.getMsg().equals(message)){
            JSONArray arr = (JSONArray) jsonObject.get("data");
            List<TProAttribute> pros = JSON.parseObject(arr.toJSONString(), new TypeReference<List<TProAttribute>>() {
            });
            int i = wxClientService.insertBatch(pros);
        }

        return new CommonsResult().setMessage("入库成功").setId(200);
    }
}
