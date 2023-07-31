package models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Getter
@Setter
@Entity
@DiscriminatorValue("CLASSIC")
public class ClassicCinema extends Cinema {

    @Column(name = "number_of_halls")
    private Integer numberOfHalls;

}
