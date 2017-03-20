package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.AuthorizedUser;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController {


    @RequestMapping( value = "/", method = RequestMethod.GET)
    public void redirectToTwitter(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendRedirect("https://github.com/dms51971007/topjavaFinalProject");
    }}
