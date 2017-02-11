package com.toronto.sensors;

import edu.wpi.first.wpilibj.DigitalInput;

public class T_LimitSwitch {
	
	public enum DefaultState { TRUE, FALSE };
	
	public static final boolean DEFAULT_FALSE = false;
	
	private final boolean defaultState;
	
	private final DigitalInput digitalInput;
	
	public T_LimitSwitch(int dioPort, DefaultState defaultState) {
	
		if (defaultState == DefaultState.TRUE) {
			this.defaultState = true;
		} else {
			this.defaultState = false;
		}
		
		digitalInput = new DigitalInput(dioPort);
	}
	
	public boolean atLimit() {
		return digitalInput.get() != defaultState;
	}

}
