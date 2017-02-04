package com.toronto.oi;

/**
 * The GameController class is the abstract implementation for all GameControllers, 
 * including XBox, PSx, WII and PC game controllers (ie. Logitech).
 * 
 * Specific implementations of the GameController interface should override the 
 * protected methods in order to implement specific mappings of buttons
 * and axis values.
 * <p>
 * The mapping should be done using the super.joystick.getRawAxis() and 
 * the super.joystick.getRawButton() methods to return the correct button
 * or axis for each of the calls. 
 *
 */
public abstract class T_GameController extends T_OiController {

	public T_GameController(int port) { super(port); }
	
	@Override
	public double getAxis(T_Axis axis) { 
		System.out.println("Use method getAxis(T_Side, T_Axis) for T_GameControllers");
		return 0.0;
	}
	@Override
	public double getAxis(T_Stick side, T_Axis axis) {
		switch (side) {
		case LEFT:
			switch (axis) {
			case X:     return getAxis_LeftX(); 
			case Y:		return getAxis_LeftY();
			default:    break;
			}
			break;
			
		case RIGHT:
			switch (axis) {
			case X:     return getAxis_RightX(); 
			case Y:		return getAxis_RightY();
			default:    break;
			}
			break;
		}
		System.out.println("Axis (" + side + "," + axis + ") "
				+ "is not implemented for GameControllers.");
		return 0.0;
	}
	
	@Override
	public boolean getButton(T_Button button) { 
		switch (button) {
		case A:					return getButton_A();
		case B:					return getButton_B();
		case X:					return getButton_X();
		case Y:					return getButton_Y();
		case CROSS:				return getButton_Cross();
		case CIRCLE:			return getButton_Circle();
		case SQUARE:			return getButton_Square();
		case TRIANGLE:			return getButton_Triangle();
		case BACK:				return getButton_Back();
		case START:				return getButton_Start();
		case LEFT_BUMPER:		return getButton_LeftBumper();			
		case RIGHT_BUMPER:		return getButton_RightBumper();
		case LEFT_STICK:		return getButton_LeftStickPush();
		case RIGHT_STICK:		return getButton_RightStickPush();
		default:                break;
		}
		System.out.println("Button (" + button + ") "
				+ "is not implemented for GameControllers.");
		return false;
	}
	
	@Override
	public double getTrigger(T_Trigger trigger) { 
		switch (trigger) {
		case LEFT:		return getTrigger_Left();
		case RIGHT:		return getTrigger_Right();
		default:        break;
		}
		System.out.println("Trigger (" + trigger + ") "
				+ "is not implemented for GameControllers.");
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
		return     getButton(T_Button.A)
				|| getButton(T_Button.B)
				|| getButton(T_Button.X)
				|| getButton(T_Button.Y)
				|| getButton(T_Button.BACK)
				|| getButton(T_Button.START)
				|| getButton(T_Button.LEFT_BUMPER)
				|| getButton(T_Button.RIGHT_BUMPER)
				|| getButton(T_Button.LEFT_STICK)
				|| getButton(T_Button.RIGHT_STICK)
				
				|| getPov() >= 0;
	}

	/**
	 *  Returns true if the user is moving any of the Joysticks on the controller or the triggers
	 * @return boolean {@code true} indicating if there is any Joystick action on the controller.
	 */
	@Override
	public boolean isJoystickActivated() {
		return     Math.abs(getAxis(T_Stick.LEFT,  T_Axis.X)) > .1   
				|| Math.abs(getAxis(T_Stick.LEFT,  T_Axis.Y)) > .1
				|| Math.abs(getAxis(T_Stick.RIGHT, T_Axis.X)) > .1
				|| Math.abs(getAxis(T_Stick.RIGHT, T_Axis.Y)) > .1
				
				|| getTrigger(T_Trigger.LEFT)   > .1 
				|| getTrigger(T_Trigger.RIGHT)  > .1; 
	}
	
	@Override
	public String toString() {
		
		String name = "";
		name = super.joystick.getName();
		
		if (name.trim().isEmpty()) {
			String msg = "Game Controller Unplugged on port (" + super.getPort() + ")";
//			return msg;
		}
		
		StringBuilder sb = new StringBuilder(128);
		
		sb.append("Buttons(").append(super.joystick.getButtonCount()).append(")");
		
		sb.append((isControllerActivated() ? "A " : "   "));
		
		sb.append("Left(") .append(getAxis(T_Stick.LEFT,  T_Axis.X)) 
		  .append(",")     .append(getAxis(T_Stick.LEFT,  T_Axis.Y)) .append(") ");
		sb.append("Right(").append(getAxis(T_Stick.RIGHT, T_Axis.X)) 
		  .append(",")     .append(getAxis(T_Stick.RIGHT, T_Axis.Y)) .append(") ");
		
		sb.append("Trigger(").append(getTrigger(T_Trigger.LEFT))
		  .append(",")       .append(getTrigger(T_Trigger.RIGHT)).append(") ");
				
		sb.append( (getButton(T_Button.A)       		? "A"    : ""));
		sb.append( (getButton(T_Button.CROSS)   		? "*"    : ""));
		sb.append( (getButton(T_Button.B)               ? "B"    : ""));
		sb.append( (getButton(T_Button.CIRCLE)     		? "O"    : ""));
		sb.append( (getButton(T_Button.X)         		? "X"    : ""));
		sb.append( (getButton(T_Button.SQUARE)          ? "[]"   : ""));
		sb.append( (getButton(T_Button.Y)               ? "Y"    : ""));
		sb.append( (getButton(T_Button.TRIANGLE)        ? "/\\"  : ""));
		sb.append( (getButton(T_Button.BACK)            ? "Back" : ""));
		sb.append( (getButton(T_Button.START)           ? "Start": ""));
		sb.append( (getButton(T_Button.LEFT_BUMPER)     ? "Lb"   : ""));
		sb.append( (getButton(T_Button.RIGHT_BUMPER)    ? "Rb"   : ""));
		sb.append( (getButton(T_Button.LEFT_STICK)      ? "Ls"   : ""));
		sb.append( (getButton(T_Button.RIGHT_STICK)     ? "Rs"   : ""));
				
		sb.append( (getPov() >= 0 ? " POV(" + getPov() + ")" : ""));
					
		return sb.toString();
	}
	
	/* 
	 *************************************************************************************
	 * The following protected methods are a very simple
	 * abstract interface for defining new GameControllers
	 * and easily mapping the buttons.
	 *************************************************************************************
	 */
	protected abstract double getAxis_LeftX();
	protected abstract double getAxis_LeftY();
	protected abstract double getAxis_RightX();
	protected abstract double getAxis_RightY();

	// The following buttons are cross implemented so that only the 
	// buttons applying to the controller used XBox, PS need to be
	// defined and the other controller will work.  
	// Overridding classes must override either the A, B, X, Y or
	// Cross, Circle, Square, and Triangle values.
	protected boolean getButton_A()              { return getButton_Cross(); }
	protected boolean getButton_B()              { return getButton_Circle(); }
	protected boolean getButton_Circle()         { return getButton_B(); }          
	protected boolean getButton_Cross()          { return getButton_A(); }         
	protected boolean getButton_Square()         { return getButton_X(); }
	protected boolean getButton_Triangle()       { return getButton_Y(); }
	protected boolean getButton_X()              { return getButton_Square(); }
	protected boolean getButton_Y()              { return getButton_Triangle(); }

	protected abstract boolean getButton_LeftBumper();    
	protected abstract boolean getButton_LeftStickPush();   
	protected abstract boolean getButton_RightBumper(); 
	protected abstract boolean getButton_RightStickPush();
	
	protected abstract boolean getButton_Back();
	protected abstract boolean getButton_Start();
	
	protected abstract double getTrigger_Left();
	protected abstract double getTrigger_Right();

}
