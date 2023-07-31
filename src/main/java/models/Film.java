package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    private  String title;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @ManyToMany(mappedBy = "filmsList")
    @JsonIgnore
    @ToString.Exclude
    private List<Actor> actorList = new ArrayList<>();

}
