package com.hongbao.web.controller.help;

import com.hongbao.dal.model.User;
import com.hongbao.dal.model.UserAccount;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * Created by shengshan.tang on 2015/11/30 at 19:32
 */
@Controller
public class HelpController {

    @RequestMapping(value = "/innerPage/question")
    public String question(Model model) {
        return "question";
    }

    @RequestMapping(value = "/innerPage/about")
    public String about(Model model) {
        return "about";
    }

    @RequestMapping(value = "/innerPage/settings")
    public String settings(Model model) {
        return "settings";
    }

    @RequestMapping(value = "/innerPage/cooperation")
    public String cooperation(Model model) {
        return "cooperation";
    }

    @RequestMapping(value = "/innerPage/helpDownload")
    public String helpDownload(Model model) {
        return "helpDownload";
    }
}
