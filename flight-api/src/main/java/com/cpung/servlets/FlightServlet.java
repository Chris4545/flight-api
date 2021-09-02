package com.cpung.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.cpung.beans.Flight;
import com.cpung.data.FlightDAO;


@WebServlet(urlPatterns = "/flight")
public class FlightServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Flight> flight = new ArrayList<>();
		try (FlightDAO dao = new FlightDAO()){
			if(req.getParameter("id") != null && req.getParameter("id") != "") {
				flight.add(dao.getAflight(Integer.parseInt(req.getParameter("id"))));
			}else {
				flight = dao.getAllflights();
			}

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(flight);
			resp.getWriter().print(json);
			resp.setContentType("application/json");
			resp.setStatus(200);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		try(FlightDAO dao = new FlightDAO()){
			Flight flight = mapper.readValue(req.getInputStream(), Flight.class);
			flight = dao.addflight(flight);
			String json = mapper.writeValueAsString(flight);
			resp.getWriter().print(json);
			resp.setContentType("application/json");
			resp.setStatus(201);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try(FlightDAO dao = new FlightDAO()){
			Integer value = new Integer(req.getReader().readLine());
			dao.removeflight(value); 

				
		}catch(Exception e) {
			e.printStackTrace();
			resp.setStatus(400);
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		try(FlightDAO dao = new FlightDAO()) {
			Flight flight = mapper.readValue(req.getInputStream(), Flight.class);
			dao.updateflight(flight);
			String json = mapper.writeValueAsString(flight);
			resp.getWriter().print(json);
			resp.setStatus(201);
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
	
}
