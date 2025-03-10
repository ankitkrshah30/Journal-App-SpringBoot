package hellospring.journalApp.controller;

import hellospring.journalApp.entity.JournalEntry;
import hellospring.journalApp.entity.User;
import hellospring.journalApp.service.JournalEntryService;
import hellospring.journalApp.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @GetMapping("/{userName}")

    public ResponseEntity<List<JournalEntry>> getAll(@PathVariable String userName){
        User user=userEntryService.findByUserName(userName);
        if(user!=null){
            List<JournalEntry> journalEntries=user.getJournalEntry();
            if(!journalEntries.isEmpty()){
                return new ResponseEntity<>(journalEntries,HttpStatus.OK);
            }
            System.out.println("The List is Empty.");
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @Transactional
    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntry> create(@RequestBody JournalEntry myEntry, @PathVariable String userName){
        try {
            User user = userEntryService.findByUserName(userName);
            if (user != null) {
                try {
                    myEntry.setDate(LocalDateTime.now());
                    JournalEntry journalEntry = journalentryservice.saveEntry(myEntry);
                    user.getJournalEntry().add(journalEntry);
                    userEntryService.saveEntry(user);
                    return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @GetMapping("getid/{myId}")
    public ResponseEntity<JournalEntry> getByid(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry =journalentryservice.findById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deleteid/{userName}/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId,@PathVariable String userName){
        User user=userEntryService.findByUserName(userName);
        if(user!=null){
            JournalEntry toBeDeleted=journalentryservice.findById(myId).orElse(null);
            if(toBeDeleted!=null){
                user.getJournalEntry().removeIf(x->x.equals(toBeDeleted));
                userEntryService.saveEntry(user);
                journalentryservice.deleteById(myId);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("putid/{userName}/{myId}")
    public ResponseEntity<JournalEntry> changeById(@PathVariable ObjectId myId,
                                                   @RequestBody JournalEntry newEntry,
                                                   @PathVariable String userName){
        User user=userEntryService.findByUserName(userName);
        if(user!=null) {
            JournalEntry old = journalentryservice.findById(myId).orElse(null);
            if (old != null) {
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                JournalEntry journalEntry = journalentryservice.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.CREATED);
            }
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
