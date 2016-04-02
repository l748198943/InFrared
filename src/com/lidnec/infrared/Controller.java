package com.lidnec.infrared;

public class Controller {

	public Controller all() {
		return new Controller();
	}
    
	public int getPower(){
		return IRConstants.AC_POWER_ON;
	}
}
