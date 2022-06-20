package com.mediscreen.patientinfo.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** Controller for HomePage Contain a method that return the homepage. */
@Controller
public class HomeController {

  @GetMapping("/")
  public String homePage() {
    return "home";
  }
}
