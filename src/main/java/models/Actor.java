package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private  String name;

    @ManyToMany
    @JoinTable(name = "film_actor",
              joinColumns = @JoinColumn(name = "actor_id"),
              inverseJoinColumns = @JoinColumn(name="film_id"))
    @JsonIgnore
    private List<Film> filmsList;


}
