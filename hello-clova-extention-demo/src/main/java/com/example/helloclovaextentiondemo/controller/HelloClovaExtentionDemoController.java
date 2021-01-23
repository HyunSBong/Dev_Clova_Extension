package com.example.helloclovaextentiondemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clova")
public class HelloClovaExtentionDemoController {

    @ResponseBody
    @RequestMapping(value = "/v1/hello", method = RequestMethod.GET, produces = "application/json")
    public String hello() {
        return "hello clova api";
    }
}
