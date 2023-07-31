package dao;

import exceptions.FilmNotFoundException;
import lombok.extern.slf4j.Slf4j;
import models.Actor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import java.util.List;

@Slf4j
public class ActorDAOImpl implements ActorDAO {
    private final SessionFactory sessionFactory;
    public ActorDAOImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List<Actor> getAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            List<Actor> actorsList = session.createQuery("FROM Actor", Actor.class).getResultList();

            session.getTransaction().commit();
            return actorsList;
        }
    }


    @Override
    public Actor getById(Integer id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Actor actor = session.get(Actor.class, id);

            session.getTransaction().commit();

            if (actor == null) {
                log.error("Actor is not found");
                throw new FilmNotFoundException("Actor is not found");
            }
            return actor;
        }
    }


    @Override
    public Actor create(Actor newActor) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            session.persist(newActor);

            session.getTransaction().commit();
            return newActor;
        }
    }


    @Override
    public Actor update(Actor updatedActor) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            session.update(updatedActor);

            session.getTransaction().commit();
            return updatedActor;
        }
    }


    @Override
    public void delete(Integer id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Actor actor = session.get(Actor.class, id);
            session.remove(actor);

            session.getTransaction().commit();
        }
    }
}
