package blog.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(nullable = false)
    private Character character;

    @NotNull
    @Column(nullable = false)
    private String name;

    @Lob
    private String description;

    public Message(Character character, String name, String description) {
        this.character = character;
        this.name = name;
        this.description = description;
    }
}
