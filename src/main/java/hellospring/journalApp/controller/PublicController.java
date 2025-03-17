package hellospring.journalApp.controller;

import hellospring.journalApp.entity.User;
import hellospring.journalApp.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserEntryService userentryservice;

    @GetMapping("/ok")
    //@RequestMapping(value ="/ok",method = RequestMethod.GET)
    public String healthCheck(){
        return "working";
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userentryservice.saveNewUser(user);
    }
}
