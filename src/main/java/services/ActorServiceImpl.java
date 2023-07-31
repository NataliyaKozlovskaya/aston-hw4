package services;

import dao.ActorDAOImpl;
import models.Actor;
import java.util.List;

public class ActorServiceImpl implements ActorService{
    private final ActorDAOImpl actorDAOImpl = new ActorDAOImpl();

    @Override
    public Actor getById(Integer id) {
        return actorDAOImpl.getById(id);
    }

    @Override
    public List<Actor> getAll() {
        return actorDAOImpl.getAll();
    }

    @Override
    public void delete(Integer id) {
        actorDAOImpl.delete(id);
    }

    @Override
    public Actor create(Actor actor) {
        return actorDAOImpl.create(actor);
    }

    @Override
    public Actor update(Actor actor) {
        return actorDAOImpl.update(actor);
    }

}
