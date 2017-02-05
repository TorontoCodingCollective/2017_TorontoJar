package com.toronto.sensors;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public abstract class T_Encoder implements PIDSource {

	protected boolean inverted = false;

	public T_Encoder() {}

	public T_Encoder(boolean inverted) {
		setInverted(inverted);
	}

	/**
	 * Gets the current count from the encoder.
	 * <br>
	 * A negative value indicates negative rotations.
	 * @return The current counts of the encoder.
	 */
	public abstract int get();

	/**
	 * Get the inverted setting on this encoder
	 * @return {@literal true} if inverted, {@literal false} otherwise
	 */
	public boolean getInverted() { return inverted; }
	
	@Override
	public PIDSourceType getPIDSourceType() { return PIDSourceType.kDisplacement; }

	/**
	 * Get the current rate of the encoder in counts per second.
	 * <p>
	 * A negative value indicates negative rate of rotation.
	 *
	 * @return The current rate of the encoder.
	 */
	public abstract double getRate();
	
	@Override
	public double pidGet() { return get(); }

	/**
	 * Reset the current encoder count to zero
	 */
	public abstract void reset();

	/**
	 * Set this encoder to the passed in inversion state state.
	 * <p> An inverted encoder will return a negative rate and distance.
	 * @param {@literal true} if the encoder is inverted {@literal false} otherwise
	 */
	public void setInverted(boolean inverted) {
		this.inverted = inverted;
		reset();
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {}

}
