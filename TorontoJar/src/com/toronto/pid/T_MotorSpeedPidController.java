package com.toronto.pid;

import edu.wpi.first.wpilibj.PIDController;

public class T_MotorSpeedPidController extends PIDController {

	private final T_PidSource source;
	
	/**
	 * Allocate a PID object with the given constants for P, I, D, using a 50ms period.
	 *
	 * @param Kp     the proportional coefficient
	 * @param Ki     the integral coefficient
	 * @param Kd     the derivative coefficient
	 * @param Kf     the feed forward term
	 * @param source The PIDSource object that is used to get values
	 * @param output The PIDOutput object that is set to the output percentage
	 */
	public T_MotorSpeedPidController(double Kp, double Ki, T_PidSource source,
			T_PidOutput output) {
		super(Kp, Ki, 0.0d, 0.0d, source, output, kDefaultPeriod);
		this.source = source;
	}

	/**
	 * Override the feed forward term to be the standard feed forward value for a position controller.
	 * NOTE:  The FRC code for a feed forward is only used for rate control.
	 *        A feed forward term does not make sense for a gyro PID.
	 */
	protected double calculateFeedForward() { 
		return this.getF() * source.pidGet(); 
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
		setPID(p, i, d, 0.0d);
	}

	/**
	 * Set the PID Controller gain parameters. Set the proportional, integral, and differential
	 * coefficients.
	 *
	 * @param p Proportional coefficient
	 * @param i Integral coefficient
	 * @param d Differential coefficient is not used - always set to zero.
	 * @param f Feed forward coefficient
	 */
	public synchronized void setPID(double p, double i, double d, double f) {
		super.setPID(p, i, 0.0d, f);
	}

}
