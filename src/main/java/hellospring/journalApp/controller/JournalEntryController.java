/*
package hellospring.journalApp.controller;

import hellospring.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/Journal")
public class JournalEntryController {
    private HashMap<Long, JournalEntry> entries=new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(entries.values());
    }

    @PostMapping
    public JournalEntry create(@RequestBody JournalEntry myEntry){
        entries.put(myEntry.getId(),myEntry);
        return myEntry;
    }

    @GetMapping("getid/{myId}")
    public JournalEntry getByid(@PathVariable long myId){
        return entries.get(myId);
    }

    @DeleteMapping("deleteid/{myId}")
    public JournalEntry deleteById(@PathVariable long myId){
        return entries.remove(myId);
    }

    @PutMapping("putid/{myId}")
    public JournalEntry changeById(@PathVariable long myId,@RequestBody JournalEntry myEntry){
        return entries.put(myId,myEntry);
    }
}
*/
