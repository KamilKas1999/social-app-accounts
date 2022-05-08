package com.kasprzak.kamil.accounts.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HelloWorldController {
    @RestController
    public class testController {

        @GetMapping(value="/")
        //@RequestMapping(value="/",method=RequestMethod.GET)
        public String hello(){
            return "Hello World!!";
        }
    }
}
