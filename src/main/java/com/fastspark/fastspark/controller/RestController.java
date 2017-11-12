package com.fastspark.fastspark.controller;

import com.fastspark.fastspark.model.ClientForm;
import com.fastspark.fastspark.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by nuwantha on 11/12/17.
 */

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    RequestService requestService;

    @RequestMapping(value = "/",method = RequestMethod.PUT)
    public void loadHomePage(@RequestBody Map<String, String> message) {
        System.out.println("message received");
        requestService.handleMessage(message.get("message"));
    }


}
