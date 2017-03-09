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

	public enum RumbleState { ON, OFF, PULSE_ON, PULSE_OFF };
	
	private final double PULSE_ON_TIME = .2;
	private final double PULSE_OFF_TIME = .1;
	private final double PULSE_VOLUME = .8;
	
	private int rumblePulseCount = 0;
	private RumbleState rumbleState = RumbleState.OFF;
	private long   timeoutStartTime = 0;
	private double timeout = 0;
	
	public T_OiController(int port) {
		this.port = port;
		joystick = new Joystick(port) {
			// Override the getRawAxis method to return a double rounded to 
			// only 2 decimal places for all axis get
			@Override
			public double getRawAxis(int axis) {
				return Math.round(super.getRawAxis(axis) * 100.0) / 100.0;
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

	/**
	 * Get the current RumbleState.  This can allow a command to know if the rumble
	 * is on or off.
	 * @return RumbleState describing the current rumble.
	 */
	public RumbleState getRumbleState() { return rumbleState; }
	
	/** Set the rumble on both the left and the right channels simultaneously */
	public void setRumble(double value) {
		
		if (value == 0) {
			rumbleState = RumbleState.OFF;
		} else {
			rumbleState = RumbleState.ON;
		}
		
		setRumble_Left (value);
		setRumble_Right(value);
	}
	

	/** Set the rumble on both the left and the right channels simultaneously */
	public void setRumblePulse(int pulseCount) {
		
		if (pulseCount <= 0) {
			setRumble(0);
			return;
		}
		
		rumblePulseCount = pulseCount;
		rumbleState = RumbleState.PULSE_ON;
		timeoutStartTime = System.currentTimeMillis();
		timeout = PULSE_ON_TIME;
		
		setRumble_Left (PULSE_VOLUME);
		setRumble_Right(PULSE_VOLUME);
	}

	/**
	 * Set the volume of the rumble for the given duration.
	 * <p>
	 * This is essentially a single pulse.
	 * @param value between 0 and 1.0
	 * @param duration in seconds
	 */
	public void setRumblePulse(double value, double timeout) {
		
		rumblePulseCount = 1;
		rumbleState = RumbleState.PULSE_ON;
		timeoutStartTime = System.currentTimeMillis();
		this.timeout = timeout;
		
		setRumble_Left (value);
		setRumble_Right(value);
	}
	
	private void setRumble_Left (double value)  { joystick.setRumble(RumbleType.kLeftRumble,  value); }
	private void setRumble_Right(double value)  { joystick.setRumble(RumbleType.kRightRumble, value); }

	/**
	 * Update periodic is used to control the rumble and update internal OI parameters
	 */
	public void updatePeriodic() {
		
		if (rumbleState == RumbleState.PULSE_ON || rumbleState == RumbleState.PULSE_OFF) {

			long curTime = System.currentTimeMillis();

			double duration = ((double) (curTime - timeoutStartTime)) / 1000;
			if (duration > timeout) { 

				switch (rumbleState) {
			
					case PULSE_ON:
						
						// Turn the pulse off and stop if there are no more pulses
					
						rumblePulseCount --;
						
						if (rumblePulseCount <= 0) {
							setRumble(0);
							break;
						}
		
						rumbleState      = RumbleState.PULSE_OFF;
						timeout          = PULSE_OFF_TIME;
						timeoutStartTime = curTime;
						
						setRumble_Left (0);
						setRumble_Right(0);
						
						break;
				
					case PULSE_OFF:
					
						// Turn the pulse back on
						
						rumbleState      = RumbleState.PULSE_ON;
						timeout          = PULSE_ON_TIME;
						timeoutStartTime = curTime;
						
						setRumble_Left (PULSE_VOLUME);
						setRumble_Right(PULSE_VOLUME);
						
						break;
	
					default: break;
				}
	
			}
		}
	}
	
}
