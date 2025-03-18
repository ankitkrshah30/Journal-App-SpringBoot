package hellospring.journalApp.controller;


import hellospring.journalApp.entity.AddAdminRequest;
import hellospring.journalApp.entity.User;
import hellospring.journalApp.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserEntryService userEntryService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> userList=userEntryService.findAll();
        if(userList!=null&&!userList.isEmpty()){
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/add-admin")
    public ResponseEntity<?> addNewAdmin(@RequestBody AddAdminRequest user){
        List<User> userList=userEntryService.findAll();
        if(userList!=null&&!userList.isEmpty()){
            for(User x:userList){
                if(x.getUserName().equals(user.getUserName())){
                    x.getRoles().add("Admin");
                    userEntryService.saveUser(x);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
            System.out.println("User Not Found in List.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println("UserList is Empty");
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
