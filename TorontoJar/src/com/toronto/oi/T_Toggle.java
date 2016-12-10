package com.toronto.oi;

/**
 * The Toggle class is used to turn an underlying button into a toggle.
 * <p>
 * Each time the underlying button is pressed, the toggle changes state
 * <p>
 * In order for the toggle to work, it needs to be called from the 
 * OI periodic loop.
 */
public class T_Toggle {
	// Yo boys im singing song
	// 123123123
	
	private final T_OiController controller;
	private final T_Button       button;
	
	public T_Toggle(T_OiController controller, T_Button button) {
		this.controller = controller;
		this.button     = button;
	}
	
	/**
	 * Update the Toggle and return the current state after the update.
	 * 
	 * @return currentState
	 */
	public boolean update() {
		
		return false;
		
	}
	
	public boolean getValue() { return false; }

}
