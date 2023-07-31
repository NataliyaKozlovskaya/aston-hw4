package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebServlet("/filmsActors")
@Slf4j
public class FilmAndActorServlet extends HttpServlet {
    ObjectMapper mapper = new ObjectMapper();
    private final FilmServiceImpl filmServiceImpl= new FilmServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String parameter1 = request.getParameter("filmId");
        String parameter2 = request.getParameter("actorId");

        if (parameter1 == null | parameter2 == null) {
            log.error("Invalid id");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid id");
        }
        int filmId;
        int actorId;
        try {
            filmId = Integer.parseInt(parameter1);
            actorId = Integer.parseInt(parameter2);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid film id");
            return;
        }
        filmServiceImpl.addFilmActor(filmId, actorId);
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var parameter1 = request.getParameter("filmId");
        var parameter2 = request.getParameter("actorId");

        if (parameter1 == null | parameter2 == null) {
            log.error("Invalid id");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid id");
        }
        int filmId;
        int actorId;
        try {
            filmId = Integer.parseInt(parameter1);
            actorId = Integer.parseInt(parameter2);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid film id");
            return;
        }
        filmServiceImpl.deleteFilmActor(filmId, actorId);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter wr = response.getWriter();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String requestParameter = request.getParameter("actorId");
        if(requestParameter != null) {
            log.info("Получен запрос к эндпоинту: /filmsActors getFilmByActorId");
            int actorId = Integer.parseInt(requestParameter);
            List<Film> filmsList = filmServiceImpl.getFilmByActorId(actorId);
            wr.write(mapper.writeValueAsString(filmsList));
        }
        wr.close();
    }
}
