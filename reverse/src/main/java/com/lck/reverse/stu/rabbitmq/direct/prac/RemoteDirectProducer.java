package com.lck.reverse.stu.rabbitmq.direct.prac;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lck.reverse.entity.respon.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class RemoteDirectProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @GetMapping("/getServer")
    public ResultMessage direct(
            @RequestParam(name="customerId") String customerId,
            @RequestParam(name="businessId") String businessId
    ){
        if(StringUtils.isEmpty(customerId)||StringUtils.isEmpty(businessId)){
            ResultMessage.getDefaultResultMessage(500,"参数异常customerId: "+customerId+" businessId: "+businessId);
        }
        JSONObject json = new JSONObject();
        json.put("customerId",customerId);
        json.put("businessId",businessId);
        String s = JSON.toJSONString(json);
        rabbitTemplate.convertAndSend(RemoteDirectConfig.DIRECT_EXCHANGE,"direct.customer.key",s);
        log.info("remoteBank请求mq(direct)坐席用户==> "+json);
        return ResultMessage.getDefaultResultMessage(200,"请求坐席服务成功");
    }
}
