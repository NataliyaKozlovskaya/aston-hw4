package services;

import models.Actor;
import java.util.List;

public interface ActorService {
    Actor getById(Integer id);
    List<Actor> getAll();
    void delete(Integer id);
    Actor create(Actor actor);
    Actor update(Actor actor);

}
