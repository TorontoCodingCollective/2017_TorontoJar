package com.toronto.oi;

public abstract class T_JoystickController extends T_OiController {

	public T_JoystickController(int port) { super(port); }
	
	@Override
	public double getAxis(T_Axis axis) { 
		switch (axis) {
		case X:
		case Y:
		case TWIST:
		case SLIDER:
		default:       	break;
		}
		System.out.println("Axis (" + axis + ") "
				+ "is not implemented for JoystickControllers.");
		return 0.0;
	}

	@Override
	public double getAxis(T_Stick side, T_Axis axis) {
		System.out.println("Use method getAxis(T_Axis) for T_JoystickControllers");
		return 0.0;
	}
	
	/** 
	 * For Joystick Controllers with numbered buttons, assume that the button
	 * numbers are returned properly on the driver station.
	 * 
	 * NOTE: If the button mapping is off, then this routine can be 
	 *       overridden by the implementation to return the
	 *       correct button mapping.
	 */
	@Override
	public boolean getButton(T_Button button) { 
		switch (button) {
		case ONE:		return super.joystick.getRawButton(1);
		case TWO:		return super.joystick.getRawButton(2);
		case THREE:		return super.joystick.getRawButton(3);
		case FOUR:		return super.joystick.getRawButton(4);
		case FIVE:		return super.joystick.getRawButton(5);
		case SIX:		return super.joystick.getRawButton(6);
		case SEVEN:		return super.joystick.getRawButton(7);
		case EIGHT:		return super.joystick.getRawButton(8);
		case NINE:		return super.joystick.getRawButton(9);
		case TEN:		return super.joystick.getRawButton(10);
		case ELEVEN:	return super.joystick.getRawButton(11);
		case TWELVE:	return super.joystick.getRawButton(12);
		default:        break;
		}
		System.out.println("Button (" + button + ") "
				+ "is not implemented for GameControllers.");
		return false;
	}

	@Override
	public double getTrigger(T_Trigger trigger) { 
		switch (trigger) {
		case JOYSTICK:	return getTrigger_Joystick();
		default:        break;
		}
		System.out.println("Trigger (" + trigger + ") "
				+ "is not implemented for Joysticks.");
		return 0.0;
	}
	
	/**
	 *  Returns true if the user is pressing any buttons
	 * @return boolean {@code true} indicating if there is any button pressed on the controller.
	 */
	@Override
	public boolean isButtonActivated() {
		// Note: it is not necessary to check both Button.A and Button Cross because they
		//       are the same button and they call each other.
		return     getButton(T_Button.ONE)
				|| getButton(T_Button.TWO)
				|| getButton(T_Button.THREE)
				|| getButton(T_Button.FOUR)
				|| getButton(T_Button.FIVE)
				|| getButton(T_Button.SIX)
				|| getButton(T_Button.SEVEN)
				|| getButton(T_Button.EIGHT)
				|| getButton(T_Button.NINE)
				|| getButton(T_Button.TEN)
				|| getButton(T_Button.ELEVEN)
				|| getButton(T_Button.TWELVE)
				
				|| getPov() >= 0;
	}
	
	/**
	 *  Returns true if the user is moving any of the Joystick axis on the controller.
	 *  Note: The Slider is not considered here because it does not auto-zero. The slider can be
	 *        set to any value and does not indicate the user is manipulating the slider.
	 */
	@Override
	public boolean isJoystickActivated() {
		return     Math.abs(getAxis(T_Axis.X))      > .1   
				|| Math.abs(getAxis(T_Axis.Y))      > .1
				|| Math.abs(getAxis(T_Axis.TWIST))  > .1;
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder(128);
		
		sb.append("Type(").append(super.joystick.getType())
		  .append(",")    .append(super.joystick.getButtonCount()).append(")");
		
		sb.append((isControllerActivated() ? "A " : "   "));
		
		sb.append("Joystick(") .append(getAxis(T_Axis.X)) 
		  .append(",")         .append(getAxis(T_Axis.Y)) .append(") ");
		sb.append("Twist(")    .append(getAxis(T_Axis.TWIST)) .append(") ");
		sb.append("Slider(")   .append(getAxis(T_Axis.SLIDER)).append(") ");
			
		sb.append( (getButton(T_Button.ONE)       		? "1"    : ""));
		sb.append( (getButton(T_Button.TWO)       		? "2"    : ""));
		sb.append( (getButton(T_Button.THREE)    		? "3"    : ""));
		sb.append( (getButton(T_Button.FOUR)       		? "4"    : ""));
		sb.append( (getButton(T_Button.FIVE)       		? "5"    : ""));
		sb.append( (getButton(T_Button.SIX)       		? "6"    : ""));
		sb.append( (getButton(T_Button.SEVEN)       	? "7"    : ""));
		sb.append( (getButton(T_Button.EIGHT)       	? "8"    : ""));
		sb.append( (getButton(T_Button.NINE)       		? "9"    : ""));
		sb.append( (getButton(T_Button.TEN)       		? "A"    : ""));
		sb.append( (getButton(T_Button.ELEVEN)       	? "B"    : ""));
		sb.append( (getButton(T_Button.TWELVE)     		? "C"    : ""));
				
		sb.append( (getPov() >= 0 ? " POV(" + getPov() + ")" : ""));
					
		return sb.toString();
	}
	
	/*************************************************************************************
	 * The following protected methods are a very simple
	 * abstract interface for defining new GameControllers
	 * and easily mapping the buttons.
	 */
	protected abstract double getAxis_JoystickX();
	protected abstract double getAxis_JoystickY();
	protected abstract double getAxis_Twist();
	protected abstract double getAxis_Slider();

	/** 
	 * This interface supports an analog trigger
	 * <p> 
	 * When a digital trigger is used as input, the value returned
	 * should be either 0 or 1.0. 
	 */ 
	protected abstract double getTrigger_Joystick();

}
