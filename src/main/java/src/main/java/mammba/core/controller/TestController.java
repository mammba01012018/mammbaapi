package src.main.java.mammba.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

	@RequestMapping(value = "/welcome.html")
	public ModelAndView firstPage() {
		return new ModelAndView("welcome");
	}

}
