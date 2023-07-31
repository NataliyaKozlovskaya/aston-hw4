package dao;

import dto.FilmDTO;
import exceptions.FilmNotFoundException;
import lombok.extern.slf4j.Slf4j;
import models.Actor;
import models.Cinema;
import models.Film;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import java.util.List;

@Slf4j
public class FilmDAOImpl implements FilmDAO {
    private final SessionFactory sessionFactory;
    public FilmDAOImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List<Film> getAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            List<Film> filmsList = session.createQuery("FROM Film", Film.class).getResultList();

            session.getTransaction().commit();
            return filmsList;
        }
    }


    @Override
    public Film getById(Integer id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Film film = session.get(Film.class, id);

            session.getTransaction().commit();

            if (film == null) {
                log.error("Film is not found");
                throw new FilmNotFoundException("Film is not found");
            }
            return film;
        }
    }


    @Override
    public Film create(FilmDTO newFilm) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Integer cinemaId = newFilm.getCinemaId();
            Cinema cinema = session.get(Cinema.class, cinemaId);

            Film film = new Film();
            film.setCinema(cinema);
            film.setTitle(newFilm.getTitle());

            session.persist(film);

            session.getTransaction().commit();
            return film;
        }
    }


    @Override
    public Film update(FilmDTO updatedFilm) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Integer cinemaId = updatedFilm.getCinemaId();
            Cinema cinema = session.get(Cinema.class, cinemaId);

            Film film = new Film();
            film.setCinema(cinema);
            film.setId(updatedFilm.getId());
            film.setTitle(updatedFilm.getTitle());

            session.update(film);

            session.getTransaction().commit();
            return film;
        }
    }


    @Override
    public void delete(Integer id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Film film = session.get(Film.class, id);
            session.remove(film);

            session.getTransaction().commit();
        }
    }

    @Override
    public void addFilmActor(Integer filmId, Integer actorId) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Film film = session.get(Film.class, filmId);
            Actor actor = session.get(Actor.class, actorId);

            film.getActorList().add(actor);
            actor.getFilmsList().add(film);

            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteFilmActor(Integer filmId, Integer actorId) {
        try  (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Film film = session.get(Film.class, filmId);
            Actor actor = session.get(Actor.class, actorId);

            film.getActorList().remove(actor);
            actor.getFilmsList().remove(film);

            session.getTransaction().commit();
        }
    }

    @Override
    public List<Film> getFilmByActorId(Integer actorId) {
        try  (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Actor actor = session.get(Actor.class, actorId);
            List<Film> filmsList = actor.getFilmsList();
            Hibernate.initialize(filmsList);

            session.getTransaction().commit();

            return filmsList;
        }
    }
}
