package services;

import models.Cinema;
import java.util.List;

public interface CinemaService {
    Cinema getById(Integer id);
    List<Cinema> getAll();
    void delete(Integer id);
    <T extends Cinema> T create(T cinema);
    <T extends Cinema> T update(T cinema);


}
