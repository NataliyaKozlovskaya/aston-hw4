package dao;

import exceptions.FilmNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.CarCinema;
import models.Cinema;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import java.util.List;

/**
 * Класс CinemaDAOImpl описывает логику хранения, обновления и поиска объектов в базе данных.
 */
@Slf4j
@RequiredArgsConstructor
public class CinemaDAOImpl implements CinemaDAO {
    private final SessionFactory sessionFactory;
    public CinemaDAOImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List<Cinema> getAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            List<Cinema> cinemaList = session.createQuery("FROM Cinema", Cinema.class).getResultList();

            session.getTransaction().commit();
            return cinemaList;
        }
    }

    @Override
    public Cinema getById(Integer id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Cinema cinema = session.get(Cinema.class, id);

            session.getTransaction().commit();

            if (cinema == null) {
                log.error("Cinema is not found");
                throw new FilmNotFoundException("Cinema is not found");
            }
            return cinema;
        }
    }

        @Override
        public <T extends Cinema> T create(T newCinema) {
            try (Session session = sessionFactory.getCurrentSession()) {
                session.beginTransaction();

                session.persist(newCinema);

                session.getTransaction().commit();
                return newCinema;
            }
        }


        @Override
        public <T extends Cinema> T update(T updatedCinema) {
            try (Session session = sessionFactory.getCurrentSession()) {
                session.beginTransaction();

                session.update(updatedCinema);

                session.getTransaction().commit();
                return updatedCinema;
            }
        }

        @Override
        public void delete (Integer id){
            try (Session session = sessionFactory.getCurrentSession()) {
                session.beginTransaction();

                Cinema cinema = session.get(Cinema.class, id);
                session.remove(cinema);

                session.getTransaction().commit();
            }
        }

}

