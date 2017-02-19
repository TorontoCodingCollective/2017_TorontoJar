package com.toronto.oi;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;

/**
 * This is the base class that is used to support all controllers,
 * both GameControllers and JoystickControllers.
 */
public abstract class T_OiController {

	protected Joystick joystick;
	private int port;
	
	public T_OiController(int port) {
		this.port = port;
		joystick = new Joystick(port) {
			// Override the getRawAxis method to return a double rounded to 
			// only 2 decimal places for all axis get
			@Override
			public double getRawAxis(int axis) {
				return Math.round(super.getRawAxis(axis) *100.0) / 100.0;
			}
		};
	}
	
	public abstract double getAxis(T_Axis axis);
	public abstract double getAxis(T_Stick stick, T_Axis axis);

	/** Get the trigger as a double value */
	public double getTrigger()      { return getTrigger(T_Trigger.JOYSTICK); }
	public abstract double getTrigger(T_Trigger trigger);
	
	/** Get the underlying button associated with this enum value */
	public abstract boolean getButton(T_Button button);
	
	/** Get an analog trigger as a button value */
	public boolean getButton(T_Trigger trigger) {
		return getTrigger(trigger) > 0.1;
	}

	/** Get the underlying joystick object associated with this controller */
	public Joystick getJoystick()   { return joystick; }

	/** Get the port this joystick is on */
	public int      getPort()       { return port; }
	
	/** 
	 * Return the joystick POV value
	 * 
	 * @return int (-1) indicates not pressed, compass values (0, 45, 90.... 315) 
	 *             indicate the POV activation states 
	 */
	public int getPov() { return joystick.getPOV(); }
	
	/**
	 *  Returns true if the user is pressing any buttons
	 * @return boolean {@code true} indicating if there is any button pressed on the controller.
	 */
	public abstract boolean isButtonActivated();
	
	/**
	 *  Returns true if the user is pressing any buttons or moving any joysticks beyond the nominal range
	 * @return boolean {@code true} indicating if there is any user action on the controller.
	 */
	public boolean isControllerActivated() {
		return isButtonActivated() || isJoystickActivated();
	}
	
	/**
	 *  Returns true if the user is moving any of the Joysticks on the controller or the triggers
	 * @return boolean {@code true} indicating if there is any Joystick action on the controller.
	 */
	public abstract boolean isJoystickActivated();

	/** Set the rumble on both the left and the right channels simultaneously */
	public void setRumble(double value) {
		setRumble_Left (value);
		setRumble_Right(value);
	}
	

	public void setRumble_Left (double value)  { joystick.setRumble(RumbleType.kLeftRumble,  value); }
	public void setRumble_Right(double value)  { joystick.setRumble(RumbleType.kRightRumble, value); }

	
}
