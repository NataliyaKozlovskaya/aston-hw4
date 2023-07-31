package models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
@ToString(callSuper = true)
@Getter
@Setter
@Entity
@DiscriminatorValue("CAR")
public class CarCinema extends Cinema {

    @Column(name = "capacity_of_cars")
    private Integer capacityOfCars;

}
