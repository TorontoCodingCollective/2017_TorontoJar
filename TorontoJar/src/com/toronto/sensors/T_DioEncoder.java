package com.toronto.sensors;

import edu.wpi.first.wpilibj.Encoder;

public class T_DioEncoder extends T_Encoder {
	
	private final Encoder encoder;
	
	public T_DioEncoder(int channelA, int channelB) {
		this(channelA, channelB, false);
	}

	public T_DioEncoder(int channelA, int channelB, boolean inverted) {
		super(inverted);
		encoder = new Encoder(channelA, channelB);
	}
	
	/**
	 * Get the underlying wpilib {@link Encoder} for this T_Encoder
	 * @return underlying {@link Encoder}
	 */
	public Encoder getEncoder() { return encoder; }

	@Override
	public int get() {
		if (super.inverted) {
			return -encoder.get();
		}
		else {
			return encoder.get();
		}
	}

	@Override
	public double getRate() {
		if (super.inverted) {
			return -encoder.getRate();
		}
		else {
			return encoder.getRate();
		}
	}

	@Override
	public void reset() {
		encoder.reset();
	}

}
