package hellospring.journalApp.entity;

import com.mongodb.lang.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private ObjectId id;
    @NonNull
    @Indexed(unique = true)//It has to be done manually by giving command in
    // application.properties
    private String userName;
    @NonNull
    private String password;
    @DBRef//it says that the address of the JournalEntry is used in the User
    private List<JournalEntry> journalEntry=new ArrayList<>();
    private List<String> roles;
}
