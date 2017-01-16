package com.toronto.oi;

public class T_PS4_GameController extends T_GameController {

	public T_PS4_GameController(int port) {
		super(port);
	}

	public double getAxis_LeftY() 				{ return - super.joystick.getRawAxis(1); 	}
	public double getAxis_LeftX() 				{ return  super.joystick.getRawAxis(0); 	}
	public double getAxis_RightY() 				{ return - super.joystick.getRawAxis(3); 	}
	public double getAxis_RightX() 				{ return super.joystick.getRawAxis(2); 		}
	
	public double getTrigger_Right() 			{ return super.joystick.getRawAxis(4); 		}
	public double getTrigger_Left() 			{ return super.joystick.getRawAxis(5); 		}

	public boolean getButton_Triangle() 		{ return super.joystick.getRawButton(3);	}
	public boolean getButton_Cross()        	{ return super.joystick.getRawButton(1);	}
	public boolean getButton_Circle()       	{ return super.joystick.getRawButton(2);	}
	public boolean getButton_Square()       	{ return super.joystick.getRawButton(0);	}

	public boolean getButton_Back() 			{ return super.joystick.getRawButton(8);	}
	public boolean getButton_Start() 			{ return super.joystick.getRawButton(9);	}
	public boolean getButton_LeftBumper() 		{ return super.joystick.getRawButton(4);	}
	public boolean getButton_RightBumper() 		{ return super.joystick.getRawButton(5);	}
	public boolean getButton_LeftStickPush() 	{ return super.joystick.getRawButton(10);	}
	public boolean getButton_RightStickPush()	{ return super.joystick.getRawButton(11);	}

}