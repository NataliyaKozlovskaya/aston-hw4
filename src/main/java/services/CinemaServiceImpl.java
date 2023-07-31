package services;

import dao.CinemaDAOImpl;
import lombok.extern.slf4j.Slf4j;
import models.Cinema;
import java.util.List;
@Slf4j
public class CinemaServiceImpl implements CinemaService{
    private final CinemaDAOImpl cinemaDAOImpl = new CinemaDAOImpl();

    @Override
    public Cinema getById(Integer id) {
        return cinemaDAOImpl.getById(id);
    }

    @Override
    public List<Cinema> getAll() {
        return cinemaDAOImpl.getAll();
    }

    @Override
    public void delete(Integer id) {
        cinemaDAOImpl.delete(id);
    }

    @Override
    public <T extends Cinema> T create(T cinema) {
        return cinemaDAOImpl.create(cinema);
    }

    @Override
    public <T extends Cinema> T update(T cinema) {
        return cinemaDAOImpl.update(cinema);
    }


}
