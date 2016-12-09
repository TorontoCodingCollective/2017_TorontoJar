package com.toronto.oi;

/**
 * Implementation of the Logitech family of Game Controllers
 * 
 * The Logitech family of game controllers (F310, F510, F710) all have similar
 * button maps.
 * 
 * The controller operates in two modes X-Mode and D-Mode.  This mapping is for the X-Mode.
 * 
 * The controller class inverts the typical controller output on the Y-axis to have
 * a more normalized output of the joystick where the Y axis pushed away from the 
 * operator is a positive number.
 * 
 * All of the methods are protected because they should not be called from external
 * routines.... the generic GameController interfaces should be used.
 *
 */
public class T_Logitech_GameController extends T_GameController {

	public T_Logitech_GameController(int port) {
		super(port);
	}

	protected double getAxis_LeftX()  	 			{ return   super.joystick.getRawAxis(0); }
	protected double getAxis_LeftY()  	 			{ return - super.joystick.getRawAxis(1); }
	protected double getAxis_RightX()    			{ return   super.joystick.getRawAxis(4); }
	protected double getAxis_RightY()    			{ return - super.joystick.getRawAxis(5); }
	
	protected double getTrigger_Left()   			{ return   super.joystick.getRawAxis(2); }
	protected double getTrigger_Right()  			{ return   super.joystick.getRawAxis(3); }
	
	protected boolean getButton_A()              	{ return super.joystick.getRawButton(1); }
	protected boolean getButton_B()              	{ return super.joystick.getRawButton(2); }
	protected boolean getButton_X()              	{ return super.joystick.getRawButton(3); }
	protected boolean getButton_Y()              	{ return super.joystick.getRawButton(4); }
	protected boolean getButton_Back()           	{ return super.joystick.getRawButton(7); }
	protected boolean getButton_Start()          	{ return super.joystick.getRawButton(8); }
	protected boolean getButton_LeftBumper()     	{ return super.joystick.getRawButton(5); }
	protected boolean getButton_RightBumper()    	{ return super.joystick.getRawButton(6); }
	protected boolean getButton_LeftStickPush()  	{ return super.joystick.getRawButton(9); }
	protected boolean getButton_RightStickPush() 	{ return super.joystick.getRawButton(10); }
	
}
