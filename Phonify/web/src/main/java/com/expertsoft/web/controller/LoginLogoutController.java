package com.expertsoft.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginLogoutController {

    private static final String VIEW_NAME_LOGIN = "login";

    private static final String LOGIN_ERROR_MESSAGE_ATTRIBUTE = "loginErrorMessage";
    private static final String LOGOUT_MESSAGE_ATTRIBUTE = "logoutMessage";
    private static final String REDIRECT_URL_ATTRIBUTE = "redirectUrl";

    private static final String LOGIN_ERROR_MESSAGE_KEY = "login.error.message";
    private static final String LOGOUT_MESSAGE_KEY = "logout.message";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String loginPage(@RequestParam(name = "error", required = false) String error,
                            @RequestParam(name = "logout", required = false) String logout,
                            @RequestHeader("Referer") String redirectUrl,  Model model) {
        if (error != null) {
            model.addAttribute(LOGIN_ERROR_MESSAGE_ATTRIBUTE, messageSource.getMessage(LOGIN_ERROR_MESSAGE_KEY, null, null));
        }
        if (logout != null) {
            model.addAttribute(LOGOUT_MESSAGE_ATTRIBUTE, messageSource.getMessage(LOGOUT_MESSAGE_KEY, null, null));
        }
        model.addAttribute(REDIRECT_URL_ATTRIBUTE, redirectUrl);
        return VIEW_NAME_LOGIN;
    }

}
