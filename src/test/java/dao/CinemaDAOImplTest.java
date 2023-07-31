package dao;

import exceptions.FilmNotFoundException;
import models.CarCinema;
import models.Cinema;
import models.ClassicCinema;
import models.Film;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;

import java.util.List;

class CinemaDAOImplTest {
    private Session session;
    private Transaction transaction;
    private CinemaDAOImpl cinemaDAO;


    @BeforeEach
    public void setUp() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        transaction = session.beginTransaction();

        cinemaDAO = new CinemaDAOImpl();
    }

    @AfterEach
    public void tearDown() {
        session.close();
    }

    @Test
    public void should_getAll_cinemas() {
        Film film = new Film();

        film.setTitle("Film-1");
        session.persist(film);

        ClassicCinema classicCinema = new ClassicCinema();
        CarCinema carCinema = new CarCinema();

        classicCinema.setName("ClassicCinema");
        carCinema.setName("CarCinema");
        classicCinema.setNumberOfHalls(10);
        carCinema.setCapacityOfCars(30);
        classicCinema.setFilmsList(List.of(film));
        carCinema.setFilmsList(List.of(film));

        session.persist(classicCinema);
        session.persist(carCinema);
        session.getTransaction().commit();

        List<Cinema> cinemaList = cinemaDAO.getAll();

        Cinema cinema1 = cinemaList.get(0);
        Cinema cinema2 = cinemaList.get(1);

        Assertions.assertEquals(classicCinema.getName(), cinema1.getName());
        Assertions.assertEquals(carCinema.getName(), cinema2.getName());
        System.out.println(cinema1);
        System.out.println(cinema2);
    }

    @Test
    public void should_get_cinema_ById() {
        Film film = new Film();
        film.setTitle("Film-1");
        session.persist(film);

        CarCinema carCinema = new CarCinema();
        carCinema.setName("CarCinema");
        carCinema.setCapacityOfCars(30);
        carCinema.setFilmsList(List.of(film));

        session.persist(carCinema);
        session.getTransaction().commit();

        Cinema cinema = cinemaDAO.getById(1);

        Assertions.assertEquals(carCinema.getName(), cinema.getName());
        Assertions.assertEquals(carCinema.getId(), cinema.getId());
        System.out.println(cinema);
    }


    @Test
    public void should_create_newCinema() {
        Film film = new Film();
        film.setTitle("Film-1");

        CarCinema carCinema = new CarCinema();
        carCinema.setName("CarCinema");
        carCinema.setCapacityOfCars(30);
        carCinema.setFilmsList(List.of(film));

        session.merge(carCinema);
        session.getTransaction().commit();

        CarCinema newCinema = cinemaDAO.create(carCinema);
        Assertions.assertEquals("CarCinema", newCinema.getName());
        Assertions.assertEquals(30, newCinema.getCapacityOfCars());
        Assertions.assertEquals(List.of(film), newCinema.getFilmsList());
    }

    @Test
    void should_update_cinema() {
        Film film = new Film();
        film.setTitle("Film-1");

        CarCinema carCinema = new CarCinema();
        carCinema.setId(1);
        carCinema.setName("CarCinema");
        carCinema.setCapacityOfCars(30);
        carCinema.setFilmsList(List.of(film));

        session.merge(carCinema);
        session.getTransaction().commit();

        CarCinema updatedCinema = cinemaDAO.update(carCinema);
        Assertions.assertEquals(carCinema, updatedCinema);
    }

    @Test
    void should_delete_cinema() {
        Film film = new Film();
        film.setTitle("Film-1");
        session.persist(film);

        CarCinema carCinema = new CarCinema();
        carCinema.setName("CarCinema");
        carCinema.setCapacityOfCars(30);
        carCinema.setFilmsList(List.of(film));
        session.persist(carCinema);
        session.getTransaction().commit();

        cinemaDAO.delete(1);
        Assertions.assertThrows(FilmNotFoundException.class, ()->cinemaDAO.getById(1));
    }
}