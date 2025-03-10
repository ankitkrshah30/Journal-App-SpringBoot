package hellospring.journalApp.service;

import hellospring.journalApp.entity.JournalEntry;
import hellospring.journalApp.repository.JournalEntryRepositry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    public JournalEntryRepositry journalentryrepositry;

    public JournalEntry saveEntry(JournalEntry journalEntry){
        journalentryrepositry.save(journalEntry);
        return journalEntry;
    }

    public List<JournalEntry> findAll(){
        return journalentryrepositry.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalentryrepositry.findById(id);
    }

    public void deleteById(ObjectId id){
        journalentryrepositry.deleteById(id);
    }
}
//controller -----> service -----> repository