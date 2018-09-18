package com.allinpay.xmltrans.controller;

import com.allinpay.xmltrans.service.TranxServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/tltbackMsg")
public class tltTestCtrl {
    TranxServiceImpl tranxService = new TranxServiceImpl();
    @Autowired
    private void setTranxService(TranxServiceImpl tranxService){this.tranxService=tranxService;}

    @RequestMapping("index")
    //@ResponseBody
    public String comIn(){
        return "/showUser";
    }
}
