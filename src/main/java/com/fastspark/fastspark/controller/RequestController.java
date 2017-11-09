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

@Controller
public class RequestController {
//    @Autowired
//    RequestService requestService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(Model model) {
        model.addAttribute("appName", "CSE");
        model.addAttribute("nodeIP", Client.getIp());
        model.addAttribute("nodePort", Client.getPort());
        model.addAttribute("files", Client.getFileDictionary());
        return "home";
    }

//
//
//    @RequestMapping(value = "/user", method = RequestMethod.POST)
//    public Map<String, String> registerUser(@RequestBody Map<String, String> message) {
//        Client.setIp(message.get("ip"));
//        Client.setPort(Integer.parseInt(message.get("port")));
//        return requestService.registerNode(Client.getIp(), String.valueOf(Client.getPort()), message);
//    }
//
//    @RequestMapping(value = "/nouser", method = RequestMethod.POST)
//    public Map<String, String> unregisterUser(@RequestBody Map<String, String> message) {
////        return unregisterService.unregisterNode(message);
//        return null;
//    }
//
//    @RequestMapping(value = "/join", method = RequestMethod.POST)
//    public int getJoin(@RequestBody Map<String, String> node) {
//        int value=requestService.joinNode(node);
//        return value;
//    }
//
//    @RequestMapping(value = "/leave", method = RequestMethod.POST)
//    public int getLeave(@RequestBody Map<String, String> node) {
//        int value = requestService.leaveNode(node);
//        return value;
//    }
//
//    @RequestMapping(value = "/result", method = RequestMethod.GET)
//    public ArrayList<Map<String, ArrayList<String>>> getResult() {
//
////        return Global.resultList
//        return null;
//    }
//
//    @RequestMapping(value = "/neighbours", method = RequestMethod.GET)
//    public ArrayList<Map<String, String>> getNeighbours() {
//            return null;
////        return Global.neighborTable;
//    }
//
//    @RequestMapping(value = "/selfSearch", method = RequestMethod.POST)
//    public ArrayList<String> selfSearch(@RequestBody Map<String, String> node) {
////        Global.resultList.clear();
////        node.put("ip", Global.nodeIp);
////        node.put("port", Global.nodePort);
////        node.put("hops", 0+"");
////        String uniqueID = UUID.randomUUID().toString();
////        Global.messagesQueue.add(uniqueID);
////        node.put("id", uniqueID);
////
////        ArrayList<String> result = searchService.search(node);
////        if(result.size()>0){
////            Map<String, ArrayList<String>> searchResult = new HashMap<String, ArrayList<String>>();
////            ArrayList<String> ipPort = new ArrayList<>();
////            ipPort.add(Global.nodeIp);
////            ipPort.add(Global.nodePort);
////            ipPort.add(node.get("hops"));
////            searchResult.put("ipPort", ipPort);
////            searchResult.put("files", result);
////            Global.resultList.add(searchResult);
////        }
//
//        return null;
//    }
//
//    @RequestMapping(value = "/search", method = RequestMethod.POST)
//    public void search(@RequestBody Map<String, String> node) {
//
////        System.out.println("Searching....");
////        String query = node.get("file_name");
////        String ip = node.get("ip");
////        String port = node.get("port");
////        String messageId = node.get("id");
////
////        if(Global.messagesQueue.contains(messageId)){
////            System.out.println("success");
////            return;
////        }
////
////        Global.messagesQueue.add(messageId);
////
////        ArrayList<String> result = searchService.search(node);
////        if(result.size() > 0){
////            Map<String, ArrayList<String>> searchResult = new HashMap<String, ArrayList<String>>();
////            ArrayList<String> ipPort = new ArrayList<>();
////            ipPort.add(Global.nodeIp);
////            ipPort.add(Global.nodePort);
////            ipPort.add(node.get("hops"));
////            searchResult.put("ipPort", ipPort);
////            searchResult.put("files", result);
////
////            RestTemplate restTemplate = new RestTemplate();
////            HttpHeaders headers = new HttpHeaders();
////            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
////            String uri="http://"+ip+":"+port+"/searchResult";
////            HttpEntity<Map> entity = new HttpEntity<Map>(searchResult,headers);
////
////            Thread requestThread = new Thread(new Runnable() {
////
////                @Override
////                public void run() {
////                    restTemplate.postForLocation(uri, entity);
////                }
////            });
////            requestThread.start();
////        }
////        return;
//
//    }
//
//    @RequestMapping(value = "/searchResult", method = RequestMethod.POST)
//    public void searchResult(@RequestBody Map<String, ArrayList<String>> node) {
////        Global.resultList.add(node);
////        return;
//    }
//
////    @RequestMapping(value = "/user", method = RequestMethod.POST)
////    public Map<String, String> registerUser(@RequestBody Map<String, String> message) {
////        Global.bootstrapServerIp = message.get("ip");
////        Global.bootstrapServerPort = Integer.parseInt(message.get("port"));
////        return registerService.registerNode(Global.nodeIp, Global.nodePort, message);
////
////    }
////
////    @RequestMapping(value = "/nouser", method = RequestMethod.POST)
////    public Map<String, String> unregisterUser(@RequestBody Map<String, String> message) {
//////        return unregisterService.unregisterNode(message);
////            return null;
////    }
////

}
