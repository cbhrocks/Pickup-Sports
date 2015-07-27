package edu.rosehulman.pickupsports;

import java.sql.Date;

import android.location.Location;

public class SportEvent {

	private long id;
	
	private String sport;
	
	private String description;
	
	private Date date;
	
	private Location location;
	
	private String availability;
	
	private boolean interested;

	public SportEvent(long id, String sport, String description, Date date, Location location, String availability){
		this.id = id;
		this.sport=sport;
		this.description = description;
		this.date=date;
		this.location=location;
		this.availability = availability;
		this.interested = false;
	}

	public long getId() {
		return id;
	}
	
	public boolean isInterested() {
		return interested;
	}

	public void setInterested(boolean interested) {
		this.interested = interested;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public String getAvailability() {
		return "Availability: " + availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getDistance() {
		// TODO Do This!
		return this.id*1.5 +" Miles";
	}
	
}
