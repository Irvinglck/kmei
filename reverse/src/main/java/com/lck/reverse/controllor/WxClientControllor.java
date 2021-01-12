package com.lck.reverse.controllor;


import com.lck.reverse.entity.CommonsResult;
import com.lck.reverse.entity.Consult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class WxClientControllor {

    @PostMapping("/consult") 
    public CommonsResult consult(@RequestBody Consult consult) {

        System.out.println(consult.toString());
        return null;
    }

}
