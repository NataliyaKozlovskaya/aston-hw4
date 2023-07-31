package dao;

import dto.FilmDTO;
import exceptions.FilmNotFoundException;
import models.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;

import java.util.List;

class FilmDAOImplTest {
    private Session session;
    private Transaction transaction;
    private FilmDAOImpl filmDAO;


    @BeforeEach
    public void setUp() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        transaction = session.beginTransaction();
        filmDAO = new FilmDAOImpl();
    }

    @AfterEach
    public void tearDown() {
        session.close();
    }

    @Test
    public void should_getAll_films() {
        Actor actor = new Actor();
        actor.setName("Actor");
        session.persist(actor);

        ClassicCinema classicCinema = new ClassicCinema();
        classicCinema.setName("ClassicCinema");
        classicCinema.setNumberOfHalls(10);
        session.persist(classicCinema);

        Film film = new Film();
        film.setTitle("Film-1");
        film.setActorList(List.of(actor));
        film.setCinema(classicCinema);
        session.persist(film);

        session.getTransaction().commit();

        List<Film> filmList = filmDAO.getAll();
        Film film1 = filmList.get(0);

        Assertions.assertEquals(film.getTitle(), film1.getTitle());
    }

    @Test
    public void should_get_film_ById() {
        Actor actor = new Actor();
        actor.setName("Actor");
        session.persist(actor);

        ClassicCinema classicCinema = new ClassicCinema();
        classicCinema.setName("ClassicCinema");
        classicCinema.setNumberOfHalls(10);
        session.persist(classicCinema);

        Film film = new Film();
        film.setTitle("Film-1");
        film.setActorList(List.of(actor));
        film.setCinema(classicCinema);
        session.persist(film);

        session.getTransaction().commit();

        Film filmById = filmDAO.getById(1);

        Assertions.assertEquals(film.getTitle(), filmById.getTitle());
        System.out.println(film);
    }


//    @Test//todo
//    public void should_create_newFilm() {
//        Actor actor = new Actor();
//        actor.setName("Actor");
//        session.persist(actor);
//
//        CarCinema carCinema = new CarCinema();
//        carCinema.setName("CarCinema");
//        carCinema.setCapacityOfCars(30);
//        session.persist(carCinema);
//
//        FilmDTO filmDTO = new FilmDTO();
//        filmDTO.setTitle("Film-1");
//        filmDTO.setCinemaId(carCinema.getId());
//
//        session.merge(filmDTO);
//        session.getTransaction().commit();
//
//        Film newFilm = filmDAO.create(filmDTO);
//
//        Assertions.assertEquals("Film-1", newFilm.getTitle());
//        System.out.println(newFilm);
//    }
//
//    @Test//todo
//    void should_update_film() {
//        Actor actor = new Actor();
//        actor.setName("Actor");
//        session.persist(actor);
//
//        ClassicCinema classicCinema = new ClassicCinema();
//        classicCinema.setName("ClassicCinema");
//        classicCinema.setNumberOfHalls(10);
//        session.persist(classicCinema);
//
//        FilmDTO film = new FilmDTO();
//        film.setId(1);
//        film.setTitle("Film-1");
//        film.setCinemaId(classicCinema.getId());
//        session.persist(film);
//
//        session.getTransaction().commit();
//
//        Film updatedFilm = filmDAO.update(film);
//        Assertions.assertEquals(film, updatedFilm);
//    }

    @Test
    void should_delete_filmById() {
        Actor actor = new Actor();
        actor.setName("Actor");
        session.persist(actor);


        CarCinema carCinema = new CarCinema();
        carCinema.setName("CarCinema");
        carCinema.setCapacityOfCars(30);
        session.persist(carCinema);


        Film film = new Film();
        film.setTitle("Film-1");
        film.setCinema(carCinema);
        film.setActorList(List.of(actor));
        session.persist(film);

        session.getTransaction().commit();

        filmDAO.delete(1);
        Assertions.assertThrows(FilmNotFoundException.class, ()->filmDAO.getById(1));
    }
}