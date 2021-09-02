package com.cpung.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cpung.beans.Flight;

public class FlightDAO implements AutoCloseable {
	
	private static final String URL = "jdbc:mysql://localhost:3306/flights?serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASSWORD = "root";

	
	public FlightDAO() {
		connect();
	}
	
	private static Connection connection;
	
	
	public static  void connect() {
		
		
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Flight addflight(Flight flight) throws SQLException{
		
		try {
			String sql = "insert into flights (plane_id, departure_location, arrival_location, arrival_date, departure_date) values ( ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, flight.getPlane_id());
			stmt.setString(2, flight.getDeparture_location());
			stmt.setString(3,  flight.getArrival_location());
			stmt.setDate(4,  flight.getArrival_date() != "" ? Date.valueOf(flight.getArrival_date()) : Date.valueOf("2000-01-01") );
			stmt.setDate(5, flight.getDeparture_date() != "" ? Date.valueOf(flight.getDeparture_date()) : Date.valueOf("2000-01-01"));
			stmt.execute();
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			flight.setFlight_id(rs.getInt(1));
			return flight;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;

		
		
	}
	
	public boolean removeflight(int flightId) throws SQLException{
		
		try {
			String sql = "delete from flights where flight_id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, flightId);
			stmt.execute();
			return true;
		}catch(SQLException e) {
			connection.rollback();
		}
		
		return false;
	
	}
	
	public boolean updateflight(Flight flight) throws SQLException{
		
		try {
			String sql = "UPDATE flights SET plane_id = ?, departure_location = ?, arrival_location = ?, arrival_date = ?, departure_date = ? WHERE (flight_id = ?)";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, flight.getPlane_id());
			stmt.setString(2, flight.getDeparture_location());
			stmt.setString(3, flight.getArrival_location());
			stmt.setDate(4, Date.valueOf(flight.getArrival_date()));
			stmt.setDate(5, Date.valueOf(flight.getDeparture_date()));
			stmt.setInt(6, flight.getFlight_id());
			stmt.executeUpdate();
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	public List<Flight> getAllflights() {
		List<Flight> flights = new ArrayList<>();
		try {
			String sql = "select * from flights";
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			
			while(rs.next()) {
				int flight_id = rs.getInt("flight_id");
				int plane_id = rs.getInt("plane_id");
				String departure_location = rs.getString("departure_location");
				String arrival_location = rs.getString("arrival_location");
				String arrival_date = rs.getString("arrival_date");
				String departure_date = rs.getString("departure_date");
				Flight flight = new Flight(flight_id, plane_id, departure_location, arrival_location, arrival_date, departure_date);
				flights.add(flight);
			}
			return flights;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList();
		
	}
	
	public Flight getAflight(int flightId) {
		Flight flight;
		try {
			String sql = "select * from flights where flight_id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, flightId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				int id = rs.getInt("flight_id");
				int plane_id = rs.getInt("plane_id");
				String departure_location = rs.getString("departure_location");
				String arrival_location = rs.getString("arrival_location");
				String arrival_date = rs.getString("arrival_date");
				String departure_date = rs.getString("departure_date");
				flight = new Flight(id, plane_id, departure_location, arrival_location, arrival_date, departure_date);
				return flight;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	public void close() throws Exception {
		if(connection != null && !connection.isClosed()) {
			connection.close();			
		}

		
	}
	

	
}
