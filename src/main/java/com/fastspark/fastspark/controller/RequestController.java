package com.fastspark.fastspark.controller;

import com.fastspark.fastspark.model.Client;
import com.fastspark.fastspark.service.RequestService;
import jdk.nashorn.internal.objects.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import sun.misc.Cleaner;

import javax.xml.ws.RequestWrapper;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static javax.swing.text.html.FormSubmitEvent.MethodType.GET;
import static javax.swing.text.html.FormSubmitEvent.MethodType.POST;

/**
 * Created by nuwantha on 11/10/17.
 */

@RestController
public class RequestController {
    @Autowired
    RequestService requestService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(Model model) {
        model.addAttribute("appName", "CSE");
        model.addAttribute("nodeIP", Client.getIp());
        model.addAttribute("nodePort", Client.getPort());
        model.addAttribute("files", Client.getFileDictionary());
        return "home";
    }

    @RequestMapping(value = "/user1", method = RequestMethod.GET)
    public Map<String, String> registerUser()  {
            return requestService.registerNode();
    }

    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public String getJoin(@RequestBody Map<String, String> message) {
        System.out.println("/request is started");
        Client.handleMessage(message.get("message"));
        System.out.println(message.get("message"));
        Client.handleMessage(message.get("message"));
        return "requset return";
    }


}
