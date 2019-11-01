package org.lemon.entrance.controller;

import org.lemon.entrance.model.ReturnModel;
import org.lemon.entrance.utils.LoggerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by Lemon on 2016/5/6.
 */
@Controller
@RequestMapping("/test")
public class Test {
    private ReturnModel model=null;
    @RequestMapping("get")
    @ResponseBody
    public ReturnModel get(){
        model=new ReturnModel();
        model.setSuccess(true);
        model.setObject("成功");
        return model;
    }
}
