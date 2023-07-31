package dao;

import models.CarCinema;
import models.Cinema;
import java.util.List;

/***
 Интерфейс CinemaDAO определяет методы для хранения, поиска, удаления и обновления объектов CinemaDAOImpl.
 ***/

public interface CinemaDAO {
    List<Cinema> getAll();

    Cinema getById(Integer id);

    <T extends Cinema> T create(T cinema);

    <T extends Cinema> T update(T cinema);

    void delete(Integer id);

}

