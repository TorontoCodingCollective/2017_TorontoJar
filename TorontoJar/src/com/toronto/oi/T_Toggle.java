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
	
	private boolean state, released = true;
	
	private final T_OiController controller;
	private final T_Button       button;
	
	public T_Toggle(T_OiController controller, T_Button button, boolean start) {
		this.controller = controller;
		this.button     = button;
		this.state 		= start;
	}
	
	/**
	 * Update the Toggle and return the current state after the update.
	 */
	public void update() {
		if(released){
			if(controller.getButton(button)){
				released = false;
				state = !state;
			}
		}
		else{
			released = !controller.getButton(button);
		}
	}
	/**
	 * Gets the current state of the toggle
	 * @return A boolean value based on the state of the toggle	
	 */
	public boolean getValue() { return state; }

}
