package dao;

import models.Actor;
import java.util.List;

/***
 Интерфейс ActorDAO определяет методы для хранения, поиска, удаления и обновления объектов ActorDAOImpl.
 ***/

public interface ActorDAO {
    List<Actor> getAll();

    Actor getById(Integer id);

    Actor create(Actor actor);

    Actor update(Actor actor);

    void delete(Integer id);

}

