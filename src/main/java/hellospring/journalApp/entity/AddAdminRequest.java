package hellospring.journalApp.entity;

import com.mongodb.lang.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddAdminRequest {
    @NonNull
    private String userName;
}
