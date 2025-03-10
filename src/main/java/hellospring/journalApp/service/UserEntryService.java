package hellospring.journalApp.service;

import hellospring.journalApp.entity.User;
import hellospring.journalApp.repository.UserEntryRepositry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserEntryService {

    @Autowired
    public UserEntryRepositry userentryrepositry;

    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public User saveEntry(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new ArrayList<>());
        userentryrepositry.save(user);
        return user;
    }

    public List<User> findAll(){
        return userentryrepositry.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userentryrepositry.findById(id);
    }

    public void deleteById(ObjectId id){
        userentryrepositry.deleteById(id);
    }

    public User findByUserName(String userName){
        return userentryrepositry.findByUserName(userName);
    }
}
//controller-->Service-->repository
