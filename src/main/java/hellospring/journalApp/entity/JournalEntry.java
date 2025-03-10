package hellospring.journalApp.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
@Data//this includes 6 such annotation such as Getter,Setter etc.;
@NoArgsConstructor//this is not present in the @Data
@Document//row
public class JournalEntry {
    @Id//uniqueKey
    private ObjectId id;
    private String  title,content;
    private LocalDateTime date;

}
