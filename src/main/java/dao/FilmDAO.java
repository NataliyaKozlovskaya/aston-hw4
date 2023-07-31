package dao;

import dto.FilmDTO;
import models.Film;
import java.util.List;

/***
 Интерфейс FilmDAO определяет методы для хранения, поиска, удаления и обновления объектов FilmDAOImpl.
 ***/

public interface FilmDAO {
    List<Film> getAll();
    Film getById(Integer id);

    Film create(FilmDTO film);

    Film update(FilmDTO film);

    void delete(Integer id);

    void addFilmActor(Integer filmId, Integer actorId);

    void deleteFilmActor(Integer filmId, Integer actorId);

    List<Film> getFilmByActorId(Integer actorId);

}

