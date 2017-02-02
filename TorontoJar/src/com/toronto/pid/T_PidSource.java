package com.toronto.pid;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class T_PidSource implements PIDSource {

	@Override
	// This method is not used.  The default value of 
	// PIDSourceType.kDisplacement is always used.
	public void setPIDSourceType(PIDSourceType pidSource) {}

	double value = 0.0d;
	
	@Override
	public PIDSourceType getPIDSourceType() { return PIDSourceType.kDisplacement; }

	@Override
	public double pidGet() { return value; }
	
	public void set(double value) { this.value = value; }

}
