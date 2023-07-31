package servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.FilmDTO;
import lombok.extern.slf4j.Slf4j;
import models.Film;
import services.FilmServiceImpl;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/films")
@Slf4j
public class FilmServlet extends HttpServlet {
    private final FilmServiceImpl filmServiceImpl= new FilmServiceImpl();
    ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter wr = response.getWriter();

        String str = request.getParameter("id");
        if(str != null){
            log.info("Получен запрос к эндпоинту: /films getById");
            int id = Integer.parseInt(str);
            Film film = filmServiceImpl.getById(id);
            wr.write(mapper.writeValueAsString(film));
        }else {
            log.info("Получен запрос к эндпоинту: /films getAll");
            List<Film> filmsList = filmServiceImpl.getAll();
            wr.write(mapper.writeValueAsString(filmsList));
        }
        wr.close();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Получен запрос к эндпоинту: /films create");
        try {
            FilmDTO film = mapper.readValue(request.getReader(), FilmDTO.class);
            Film newFilm = filmServiceImpl.create(film);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            mapper.writeValue(response.getWriter(), newFilm);

        } catch (JsonProcessingException e) {
            log.error("Failed to parse film data", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to parse film data");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Получен запрос к эндпоинту: /films update");
        try{
            FilmDTO film = mapper.readValue(request.getReader(), FilmDTO.class);
            if (film == null) {
                log.error("Invalid film data");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid film data");
            }
            Film updatedFilm = filmServiceImpl.update(film);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            mapper.writeValue(response.getWriter(), updatedFilm);

        }catch (JsonProcessingException e){
            log.error("Failed to parse film data", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to parse film data");
        }
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Получен запрос к эндпоинту: /films delete");

        String idParameter = request.getParameter("id");
        if (idParameter == null) {
            log.error("Invalid film id");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid film id");
        }
        int id;
        try {
            id = Integer.parseInt(idParameter);
        } catch (NumberFormatException e) {
            log.error("Invalid film id");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid film id");
            return;
        }
        filmServiceImpl.delete(id);
    }

}
