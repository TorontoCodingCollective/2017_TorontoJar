package com.toronto.pid;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;

public class T_MotorSpeedPidController extends PIDController {

	private final Encoder encoder;
	private final double  encoderMaxSpeed;
	
	private double error    = 0.0d;
	private double integral = 0.0d;
	private double output   = 0.0d;

	/**
	 * Allocate a PID object with the given constants for P, I, D, using a 50ms period.
	 *
	 * @param Kp     the proportional coefficient
	 * @param Ki     the integral coefficient
	 * @param encoder the Encoder used for this speed controller
	 * @param encoderMaxSpeed the max speed for this motor in encoder counts per second
	 */
	public T_MotorSpeedPidController(double Kp, double Ki, Encoder encoder, double encoderMaxSpeed) {
		super(Kp, Ki, 0.0d, 1.0d, encoder, new T_PidOutput(), kDefaultPeriod);
		this.encoder         = encoder;
		this.encoderMaxSpeed = encoderMaxSpeed;
	}

	/**
	 * By using a feed forward term of 1.0, the setpoint of the speed controller
	 * can be set to the requested setpoint to more quickly get the motors up to speed.  
	 */
	protected double calculateFeedForward() { 
		return this.getSetpoint() * getF(); 
	}

	/**
	 * Set the PID Controller gain parameters. Set the proportional, integral, and differential
	 * coefficients.
	 *
	 * @param p Proportional coefficient
	 * @param i Integral coefficient
	 * @param d Differential coefficient
	 */
	public synchronized void setPID(double p, double i, double d) {
		setPID(p, i, d, 1.0d);
	}

	/**
	 * Set the PID Controller gain parameters. Set the proportional, integral, and ignore the 
	 * differential value.
	 *
	 * @param p Proportional coefficient
	 * @param i Integral coefficient
	 * @param d Differential coefficient is not used - always set to zero.
	 * @param f Feed forward coefficient
	 */
	public synchronized void setPID(double p, double i, double d, double f) {
		super.setPID(p, i, 0.0d, 1.0);
	}

	@Override
	public synchronized void disable() {
		super.disable();
		integral = 0.0d;
		error    = 0.0d;
	}
	
	@Override
	public synchronized void enable() {
		super.enable();
		integral = 0.0d;
		error    = 0.0d;
	}
	
	@Override
	public double get() { return output; }
	
	@Override
	public double getError() { return error; }
	
	/** 
	 * This method should be called only once per loop.  The PID calculate 
	 * will set the PID output value which can be returned multiple times using
	 * the get() command.  In the base wpilib code, the calculate routine is called
	 * on another thread and is not exposed externally.  
	 */
	public double calculatePidOutput() {
		
		if (!isEnabled()) { return 0.0d; } 
		
		double normalizedFeedback = encoder.getRate() / encoderMaxSpeed;
		
		// Limit the feedback to +/- 1.0
		if (normalizedFeedback < -1.0) { normalizedFeedback = -1.0; }
		if (normalizedFeedback >  1.0) { normalizedFeedback =  1.0; }
		
		// Calculate the error
		double setpoint = getSetpoint();
		
		error = setpoint - normalizedFeedback;
		
		output = error * getP();
		
		// Clip the proportional output to +/- 1.0
		if (output < -1.0) { output = -1.0; }
		if (output >  1.0) { output = 1.0; }
		
		// Integrate the error if there is a i value
		double i = getI();
		if (i > 0) {
			
			integral += error;

			// The integral output can often saturate the controller
			// output.  In order to minimize this saturation, calculate
			// the max value for the integral component, and 
			// clip the integration at that point.
			double maxIntegral = (1.0 - Math.abs(output)) / i;

			if (Math.abs(integral) > maxIntegral) {
				integral = Math.signum(integral) * maxIntegral;
			}
			
			output += integral * i;

			// Clip the output to +/- 1.0
			if (output < -1.0) { output = -1.0; }
			if (output >  1.0) { output = 1.0; }
		}
		
		output += calculateFeedForward();

		// Clip the output
		if (output < -1.0) { output = -1.0; }
		if (output >  1.0) { output = 1.0; }
		
		return output;
	}

}
