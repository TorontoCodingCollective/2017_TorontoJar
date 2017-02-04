package com.toronto.pid;

import edu.wpi.first.wpilibj.PIDOutput;

public class T_PidOutput implements PIDOutput {

	double output = 0.0;
	
	@Override
	public void pidWrite(double output) {
		this.output = output;
	}

	// Get the current output value of the PID controller
	public double get() { return output; }
}
