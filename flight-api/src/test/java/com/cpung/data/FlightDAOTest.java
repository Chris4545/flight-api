package com.cpung.data;

import org.junit.After;
import org.junit.Test;

import com.cpung.beans.Flight;

import java.util.List;

import static org.junit.Assert.*;

public class FlightDAOTest {

	
	@Test
	public void getAllRentals() {
		
	List<Flight> flights;
		try(FlightDAO dao = new FlightDAO()){
			flights = dao.getAllflights();
			assertNotNull(flights);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//int flight_id, int plane_id, String departure_location, String arrival_location,
	//String departure_date, String arrival_date
	@Test
	public void insertIntoFlights() {
		Flight flight = new Flight(1, 1, "ABC", "DEC", "2020-09-12", "2020-09-12");

		try(FlightDAO dao = new FlightDAO()){
			
			assertEquals(dao.addflight(flight), flight);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateFlight() {
		try(FlightDAO dao = new FlightDAO()){
			Flight flight = new Flight();
			assertTrue(dao.updateflight(flight));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@After
	@Test
	public void deleteFlight() {
		try(FlightDAO dao = new FlightDAO()){
			assertTrue(dao.removeflight(1));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}

