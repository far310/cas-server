package com.cas.info.controller;

import com.cas.info.dao.UserRepository;
import com.cas.info.entity.User;
import jdk.nashorn.internal.parser.Token;
import net.unicon.cas.client.configuration.CasClientConfigurationProperties;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;


@Controller
public class HomeController {

    @Autowired
    CasClientConfigurationProperties casClientConfigurationProperties;

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/index")
    public String getIndex() {
        return "index";
    }

    @RequestMapping("/")
    public String getHome() {
        return "index";
    }


    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:" + casClientConfigurationProperties.getServerUrlPrefix() + "logout?service=" + casClientConfigurationProperties.getClientHostUrl() + "/index";
    }

    @RequestMapping("/getPage")
    public String getPage(HttpServletRequest request, @RequestParam String url) {
        Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
        AttributePrincipal principal = assertion.getPrincipal();
        User userInfo = userRepository.findAllByUsername(principal.getName());
        String token = UUID.randomUUID().toString();
        return "redirect:" + url + "?token=" + token ;
    }
}
