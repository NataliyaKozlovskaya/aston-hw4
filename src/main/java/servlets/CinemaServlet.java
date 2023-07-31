package servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.Cinema;
import models.CarCinema;
import models.ClassicCinema;
import services.CinemaServiceImpl;
import services.FilmServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
@WebServlet("/cinemas")
@NoArgsConstructor
public class CinemaServlet extends HttpServlet {
    private final CinemaServiceImpl cinemaServiceImpl = new CinemaServiceImpl();
    ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter wr = response.getWriter();
        String str = request.getParameter("id");

        if(str != null){
            log.info("Получен запрос /cinemas getById");
            int id = Integer.parseInt(str);
            Cinema cinema = cinemaServiceImpl.getById(id);
            wr.write(mapper.writeValueAsString(cinema));
        }else {
            log.info("Получен запрос /cinemas getAll");
            List<Cinema> cinemaList = cinemaServiceImpl.getAll();
            wr.write(mapper.writeValueAsString(cinemaList));
        }
        wr.close();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Получен запрос к эндпоинту: /cinemas create");
        try{
            Cinema cinema = checkTypeOfCinema(request, response);

            Cinema newCinema = cinemaServiceImpl.create(cinema);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            mapper.writeValue(response.getWriter(), newCinema);

        } catch (JsonProcessingException e) {
            log.error("Failed to parse cinema data", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to parse cinema data");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Получен запрос к эндпоинту: /cinemas update");
        try{
            Cinema cinema = checkTypeOfCinema(request, response);

            Cinema updateCinema = cinemaServiceImpl.update(cinema);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            mapper.writeValue(response.getWriter(), updateCinema);

        }catch(JsonProcessingException e){
            log.error("Failed to parse cinema data", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to parse cinema data");
        }
    }

    private Cinema checkTypeOfCinema(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonNode jsonNode = mapper.readTree(request.getReader());
        Cinema cinema = null;
        if (jsonNode.has("dtype")) {
            String dtype = jsonNode.get("dtype").asText();
            ((ObjectNode) jsonNode).remove("dtype");
            if ("CAR".equals(dtype)) {
                cinema = mapper.treeToValue(jsonNode, CarCinema.class);
            } else if ("CLASSIC".equals(dtype)) {
                cinema = mapper.treeToValue(jsonNode, ClassicCinema.class);
            }
            if (cinema == null) {
                log.error("Invalid cinema data");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid cinema data");
            }
        }
        return cinema;
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Получен запрос к эндпоинту: /cinemas delete");
        String idParameter = request.getParameter("id");

        if (idParameter == null) {
            log.error("Invalid Cinema id");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Cinema id");
        }
        int id;
        try{
            id = Integer.parseInt(idParameter);
            cinemaServiceImpl.delete(id);
        }catch (NumberFormatException e) {
            log.error("Invalid Cinema id");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Cinema id");
        }
    }

}
