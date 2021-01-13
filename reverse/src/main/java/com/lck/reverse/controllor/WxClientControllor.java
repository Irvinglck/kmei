package com.lck.reverse.controllor;


import com.lck.reverse.entity.TConsult;
import com.lck.reverse.entity.respon.CommonsResult;
import com.lck.reverse.service.WxClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;

@RestController
@RequestMapping("/client")
public class WxClientControllor {

    @Autowired
    private WxClientService wxClientService;

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

}
