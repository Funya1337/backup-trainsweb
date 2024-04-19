package com.train.train.utils;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class RedirectUtil {
    public static ModelAndView redirect(String path) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(path);
        redirectView.setExposeModelAttributes(false);

        return new ModelAndView(redirectView);
    }
}
