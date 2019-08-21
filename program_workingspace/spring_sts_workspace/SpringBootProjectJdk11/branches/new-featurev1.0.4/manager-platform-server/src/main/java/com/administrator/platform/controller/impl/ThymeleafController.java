/**
 * @author : 孙留平
 * @since : 2018年10月11日 下午11:21:20
 * @see:
 */
package com.administrator.platform.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : Administrator
 * @since : 2018年10月11日 下午11:21:20
 * @see :
 */
@RequestMapping("/thymeleaf")
@Controller
public class ThymeleafController {
    @RequestMapping("/thymeleafTest")
    public String thymeleafTest(Model model) {
        model.addAttribute("result", true);
        return "userDefined/thymeleaf/thymeleaf_test";

    }
}
