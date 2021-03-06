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
	

	public T_Toggle(T_OiController controller, T_Button button){
		this(controller, button, false);
	}

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
	 * Sets the state of the toggle
	 * @param state
	 * 		The state to which the toggle should be changed
	 */
	public void setToggleState(boolean state){
		this.state = state;
	}
	
	/**
	 * Gets the current state of the toggle
	 * @return A boolean value based on the state of the toggle
	 */
	public boolean getToggleState() { return state; }

}
