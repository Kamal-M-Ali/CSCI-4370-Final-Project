package edu.cs.uga;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for index.html
 */
@Controller
public class IndexController {
    @GetMapping("")
    public ModelAndView home() {
        return new ModelAndView("index");
    }
}
