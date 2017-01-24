package com.toronto.oi;

/**
 * Implementation of the Logitech family of Game Controllers
 * 
 * The Logitech Joystick Controller includes the X3D Pro Joystick
 * 
 * The controller class inverts the typical controller output on the Y-axis to have
 * a more normalized output of the joystick where the Y axis pushed away from the 
 * operator is a positive number.
 * 
 * The slider returns a value between 0 and 1
 * 
 * All of the methods are protected because they should not be called from external
 * routines.... the generic GameController interfaces should be used.
 *
 */
public class T_Logitech_Joystick extends T_JoystickController {

	public T_Logitech_Joystick(int port) {
		super(port);
	}

	protected double getAxis_JoystickX()            { return   super.joystick.getRawAxis(0); }
	protected double getAxis_JoystickY()            { return - super.joystick.getRawAxis(1); }
	protected double getAxis_Twist()                { return   super.joystick.getRawAxis(2); }
	
	/** Return a value between 0 and 1 for the slider where 0 is the most negative and 1.0 is the most
	 *  positive value.  The native interface returns +1.0 for the most negative position and -1.0 for
	 *  the most positive position.  These values are normallized before they are returned.
	 */
	protected double getAxis_Slider()               { return (1.0 - super.joystick.getRawAxis(3)) / 2.0; }
	
	protected double getTrigger_Joystick()          { 
		return getButton(T_Button.ONE) ? 1.0 : 0.0; }
	
}
