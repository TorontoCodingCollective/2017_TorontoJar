package com.toronto.sensors;

import edu.wpi.first.wpilibj.Counter;

public class T_CounterEncoder extends T_Encoder {
	
	private final Counter counter;

	public T_CounterEncoder(int channel) {
		counter = new Counter(channel);
		counter.setDistancePerPulse(1.0);
	}
	
	/**
	 * Get the underlying wpilib {@link Encoder} for this T_Encoder
	 * @return underlying {@link Encoder}
	 */
	public Counter getCounter() { return counter; }

	@Override
	public int get() {
		return counter.get();

	}

	@Override
	public double getRate() {
		return counter.getRate();

	}

	@Override
	public void reset() {
		counter.reset();
	}

}
