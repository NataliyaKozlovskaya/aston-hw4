package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype", discriminatorType = DiscriminatorType.STRING)
@Entity
@Table(name = "cinema")
public abstract class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    protected String name;

    @OneToMany(mappedBy = "cinema")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JsonIgnore
    @ToString.Exclude
    protected List<Film> filmsList = new ArrayList<>();

}
