// package com.music.sound.controller;

// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.servlet.ModelAndView;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;

// @Controller
// @RequestMapping(value = "/")
// public class RootController {

// @RequestMapping(value = { "**" }, method = RequestMethod.GET)
// public ModelAndView getIndexRootLogin() {
// String fileView = "/page/404/index";
// ModelAndView modelAndView = new ModelAndView(fileView);
// return modelAndView;
// }

// @RequestMapping(value = "css/{id}", method = RequestMethod.GET)
// public ModelAndView unexpectedCss(@PathVariable("id") String view) {
// String fileView = "/css/" + view;
// ModelAndView modelAndView = new ModelAndView(fileView);
// return modelAndView;
// }
// }
