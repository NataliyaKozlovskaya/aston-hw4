package services;

import dto.FilmDTO;
import models.Film;
import java.util.List;

public interface FilmService {
    Film getById(Integer id);
    List<Film> getAll();
    void delete(Integer id);

    Film create(FilmDTO film);

    Film update(FilmDTO film);

    void addFilmActor(Integer filmId, Integer actorId);

    void deleteFilmActor(Integer filmId, Integer actorId);

    List<Film> getFilmByActorId(Integer actorId);

}
