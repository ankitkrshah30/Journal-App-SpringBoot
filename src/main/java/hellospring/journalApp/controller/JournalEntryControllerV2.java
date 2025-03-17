package hellospring.journalApp.controller;

import hellospring.journalApp.entity.JournalEntry;
import hellospring.journalApp.entity.User;
import hellospring.journalApp.service.JournalEntryService;
import hellospring.journalApp.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/JournalM")
public class JournalEntryControllerV2 {

    @Autowired//autowired to service
    private JournalEntryService journalentryservice;

    @Autowired
    private UserEntryService userEntryService;


    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userEntryService.findByUserName(userName);
        if(user!=null){
            List<JournalEntry> journalEntries=user.getJournalEntry();
            if(!journalEntries.isEmpty()){
                return new ResponseEntity<>(journalEntries,HttpStatus.OK);
            }
            System.out.println("The List is Empty.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println("User Not Found");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<JournalEntry> create(@RequestBody JournalEntry myEntry){
        try {
            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            String userName=authentication.getName();
            User user = userEntryService.findByUserName(userName);
            if (user != null) {
                try {
                    myEntry.setDate(LocalDateTime.now());
                    JournalEntry journalEntry = journalentryservice.saveEntry(myEntry);
                    user.getJournalEntry().add(journalEntry);
                    userEntryService.saveUser(user);
                    return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
            System.out.println("User Not Found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @GetMapping("getid/{myId}")
    public ResponseEntity<JournalEntry> getByid(@PathVariable ObjectId myId){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User user=userEntryService.findByUserName(userName);
        List<JournalEntry> collect=user.getJournalEntry().stream().filter(x->x.getId().equals(myId)).toList();
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry =journalentryservice.findById(myId);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("deleteid/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userEntryService.findByUserName(userName);
        if(user!=null){
            JournalEntry toBeDeleted=journalentryservice.findById(myId).orElse(null);
            if(toBeDeleted!=null){
                user.getJournalEntry().removeIf(x->x.equals(toBeDeleted));
                userEntryService.saveUser(user);
                journalentryservice.deleteById(myId);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("putid/{myId}")
    public ResponseEntity<JournalEntry> changeById(@PathVariable ObjectId myId,
                                                   @RequestBody JournalEntry newEntry){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userEntryService.findByUserName(userName);
        List<JournalEntry> all=user.getJournalEntry().stream().filter(x->x.getId().equals(myId)).toList();
        if(!all.isEmpty()) {
            JournalEntry old = journalentryservice.findById(myId).orElse(null);
            if (old != null) {
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                old.setDate(LocalDateTime.now());
                JournalEntry journalEntry = journalentryservice.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.CREATED);
            }
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
