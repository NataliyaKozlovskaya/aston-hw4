package servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import models.Actor;
import services.ActorServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/actors")
@Slf4j
public class ActorServlet extends HttpServlet {
    private final ActorServiceImpl actorServiceImpl= new ActorServiceImpl();
    ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter wr = response.getWriter();
        String str = request.getParameter("id");

        if(str != null){
            log.info("Получен запрос к эндпоинту: /actors getById");
            int id = Integer.parseInt(str);
            Actor actor = actorServiceImpl.getById(id);
            wr.write(mapper.writeValueAsString(actor));
        }else {
            log.info("Получен запрос к эндпоинту: /actors getAll");
            List<Actor> actorsList = actorServiceImpl.getAll();
            wr.write(mapper.writeValueAsString(actorsList));
        }
        wr.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Получен запрос к эндпоинту: /actors create");
        try {
            Actor actor = mapper.readValue(request.getReader(), Actor.class);
            if (actor == null) {
                log.error("Invalid actor data");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid actor data");
            }
            Actor newActor = actorServiceImpl.create(actor);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            mapper.writeValue(response.getWriter(), newActor);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse actor data", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to parse actor data");
        }
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Получен запрос к эндпоинту: /actors update");
        try {
            Actor actor = mapper.readValue(request.getReader(), Actor.class);
            if (actor == null) {
                log.error("Invalid actor data");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid actor data");
            }
            Actor updateActor = actorServiceImpl.update(actor);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            mapper.writeValue(response.getWriter(), updateActor);

        } catch (JsonProcessingException e) {
            log.error("Failed to parse actor data", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to parse actor data");
        }
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Получен запрос к эндпоинту: /actors delete");
        String idParameter = request.getParameter("id");

        if (idParameter == null) {
            log.error("Invalid actor id");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid actor id");
        }
        int id;
        try {
            id = Integer.parseInt(idParameter);
        } catch (NumberFormatException e) {
            log.error("Invalid actor id");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid actor id");
            return;
        }
        actorServiceImpl.delete(id);
    }

}
