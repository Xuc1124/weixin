package com.ex.weather.entity;

public class Now {
	private String text;
	private String code;
	private String temperature;
	private String feels_like;
	private String pressure;
	private String humidity;
	private String visibility;
	private String wind_direction;
	private String wind_direction_degree;
	private String wind_speed;
	private String wind_scale;
	private String clouds;//中国不支持
	private String dew_point;//中国不支持
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getFeels_like() {
		return feels_like;
	}
	public void setFeels_like(String feels_like) {
		this.feels_like = feels_like;
	}
	public String getPressure() {
		return pressure;
	}
	public void setPressure(String pressure) {
		this.pressure = pressure;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public String getWind_direction() {
		return wind_direction;
	}
	public void setWind_direction(String wind_direction) {
		this.wind_direction = wind_direction;
	}
	public String getWind_direction_degree() {
		return wind_direction_degree;
	}
	public void setWind_direction_degree(String wind_direction_degree) {
		this.wind_direction_degree = wind_direction_degree;
	}
	public String getWind_speed() {
		return wind_speed;
	}
	public void setWind_speed(String wind_speed) {
		this.wind_speed = wind_speed;
	}
	public String getWind_scale() {
		return wind_scale;
	}
	public void setWind_scale(String wind_scale) {
		this.wind_scale = wind_scale;
	}
	public String getClouds() {
		return clouds;
	}
	public void setClouds(String clouds) {
		this.clouds = clouds;
	}
	public String getDew_point() {
		return dew_point;
	}
	public void setDew_point(String dew_point) {
		this.dew_point = dew_point;
	}
	public String getLast_update() {
		return last_update;
	}
	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}
	private String last_update;
}
