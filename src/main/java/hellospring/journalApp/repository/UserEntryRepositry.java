package hellospring.journalApp.repository;

import hellospring.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryRepositry extends MongoRepository<User, ObjectId> {
    User findByUserName(String userName);

    User deleteByUserName(String userName);
}
