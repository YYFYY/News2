package com.SpringBoot.main.controller.IndexView;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexViewController {
		
	@RequestMapping("/")
	public String index() {
		return "redirect:index/index";
	}
}
