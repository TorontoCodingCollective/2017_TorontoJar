package com.toronto.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class T_Subsystem extends Subsystem {

	@Override
	protected void initDefaultCommand() {
		
	}
	
	public abstract void robotInit();
	public abstract void updatePeriodic();

}
