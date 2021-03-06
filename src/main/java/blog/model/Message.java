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

    @Column(name = "is_favorite")
    private Boolean isFavorite;

    @Column(name = "is_complete")
    private Boolean isComplete;

    public Message(Character character, String name, String description, Boolean isFavorite, Boolean isComplete) {
        this.character = character;
        this.name = name;
        this.description = description;
        this.isFavorite = isFavorite;
        this.isComplete = isComplete;
    }
}
