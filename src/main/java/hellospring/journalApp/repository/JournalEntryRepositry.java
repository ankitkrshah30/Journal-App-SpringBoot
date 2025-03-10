package hellospring.journalApp.repository;

import hellospring.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepositry  extends MongoRepository<JournalEntry, ObjectId> {
}
