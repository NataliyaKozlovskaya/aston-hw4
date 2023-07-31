package services;

import dao.FilmDAOImpl;
import dto.FilmDTO;
import models.Film;
import java.util.List;
public class FilmServiceImpl implements FilmService{
    private final FilmDAOImpl filmDAOImpl = new FilmDAOImpl();

    @Override
    public Film getById(Integer id) {
        return filmDAOImpl.getById(id);
    }

    @Override
    public List<Film> getAll(){
       return filmDAOImpl.getAll();
    }

    @Override
    public void delete(Integer id) {
        filmDAOImpl.delete(id);
    }

    @Override
    public Film create(FilmDTO film) {
        return filmDAOImpl.create(film);
    }

    @Override
    public Film update(FilmDTO film) {
        return filmDAOImpl.update(film);
    }

    @Override
    public void addFilmActor(Integer filmId, Integer actorId) {
            filmDAOImpl.addFilmActor(filmId, actorId);
    }

    @Override
    public void deleteFilmActor(Integer filmId, Integer actorId) {
        filmDAOImpl.deleteFilmActor(filmId, actorId);
    }

    @Override
    public List<Film> getFilmByActorId(Integer actorId) {
        return filmDAOImpl.getFilmByActorId(actorId);
    }
}
