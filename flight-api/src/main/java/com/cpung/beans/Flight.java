package com.cpung.beans;


public class Flight {

	private int flight_id;
	private int plane_id;
	private String departure_location;
	private String arrival_location;
	private String departure_date;
	private String arrival_date;
	
	
	

	public Flight() {
	}



	public Flight(int flight_id, int plane_id, String departure_location, String arrival_location,
			String departure_date, String arrival_date) {
		this.flight_id = flight_id;
		this.plane_id = plane_id;
		this.departure_location = departure_location;
		this.arrival_location = arrival_location;
		this.departure_date = departure_date;
		this.arrival_date = arrival_date;
	}




	public int getFlight_id() {
		return flight_id;
	}



	public void setFlight_id(int flight_id) {
		this.flight_id = flight_id;
	}



	public int getPlane_id() {
		return plane_id;
	}



	public void setPlane_id(int plane_id) {
		this.plane_id = plane_id;
	}



	public String getDeparture_location() {
		return departure_location;
	}



	public void setDeparture_location(String departure_location) {
		this.departure_location = departure_location;
	}



	public String getArrival_location() {
		return arrival_location;
	}



	public void setArrival_location(String arrival_location) {
		this.arrival_location = arrival_location;
	}



	public String getDeparture_date() {
		return departure_date;
	}



	public void setDeparture_date(String departure_date) {
		this.departure_date = departure_date;
	}



	public String getArrival_date() {
		return arrival_date;
	}



	public void setArrival_date(String arrival_date) {
		this.arrival_date = arrival_date;
	}

	

	
	
	
	
}
