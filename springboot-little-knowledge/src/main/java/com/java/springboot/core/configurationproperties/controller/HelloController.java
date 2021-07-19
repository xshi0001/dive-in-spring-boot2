package com.java.springboot.core.configurationproperties.controller;

import com.java.springboot.core.configurationproperties.bean.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author JClearLove
 * @date 2021/07/16 17:37
 */
@RequestMapping("/hello")
@RestController
public class HelloController {

    @Autowired
    private Person person;

    @RequestMapping("/test")
    public String hello() {
        System.out.println("test：" + person.toString());
        return "hello World";
    }

}
