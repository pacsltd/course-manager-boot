package net.training.unittest.model;

import org.joda.time.LocalDate;

public class Event {

	private int eventId;
	private String city;
	private LocalDate date;
	private int placesLeft;
	private boolean enabled; 
	
	public Event() {
	}
	
	public Event(String city, LocalDate date) {
		super();
		this.city = city;
		this.date = date;
	}
		
	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getPlacesLeft() {
		return placesLeft;
	}

	public void setPlacesLeft(int placesLeft) {
		this.placesLeft = placesLeft;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	
	
}
