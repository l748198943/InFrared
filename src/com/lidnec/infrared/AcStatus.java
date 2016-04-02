package com.lidnec.infrared;

public class AcStatus {

	private int power;
	private int mode;
	private int temperature;
	private int windSpeed;
	private int windDirect;
	
	
	
	public AcStatus(int power, int mode, int temperature, int windSpeed, int windDirect) {
		super();
		this.power = power;
		this.mode = mode;
		this.temperature = temperature;
		this.windSpeed = windSpeed;
		this.windDirect = windDirect;
	}
	
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public int getTemperature() {
		return temperature;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	public int getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(int windSpeed) {
		this.windSpeed = windSpeed;
	}
	public int getWindDirect() {
		return windDirect;
	}
	public void setWindDirect(int windDirect) {
		this.windDirect = windDirect;
	}

	

}
