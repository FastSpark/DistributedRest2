package com.fastspark.fastspark.controller;

import com.fastspark.fastspark.model.Client;
import com.fastspark.fastspark.model.ClientForm;
import com.fastspark.fastspark.model.Node;
import com.fastspark.fastspark.model.SearchResult;
import com.fastspark.fastspark.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * Created by nuwantha on 11/10/17.
 */

@Controller
public class RequestController {

    @Autowired
    RequestService requestService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String loadHomePageStart(Model model) {
        model.addAttribute("Client",new ClientForm());
        model.addAttribute("SearchResult",new SearchResult());
        return "home";
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public String search(SearchResult searchResult,Model model) {
        model.addAttribute("Client",new ClientForm());
        model.addAttribute("SearchResult",new SearchResult());
        System.out.println("search received "+searchResult.getSearchFile());
        requestService.initializeSearch(searchResult.getSearchFile());
        return "home";
    }

    @RequestMapping(value = "/leave",method = RequestMethod.GET)
    public String leave(Model model) {
        model.addAttribute("Client",new ClientForm());
        model.addAttribute("SearchResult",new SearchResult());
        requestService.leave();
        return "home";
    }

}
