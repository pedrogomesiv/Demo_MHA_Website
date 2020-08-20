package com.nsa.mhasite.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
    static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public String returnLoginPage()
    {
        LOG.debug("Getting /login form");
        return "login";
    }
}
