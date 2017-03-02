package com.toronto.sensors;

import edu.wpi.first.wpilibj.Counter;

public class T_CounterEncoder extends T_Encoder {
	
	private final Counter counter;

	private double prevRate = 0;
	private int    badDataCount = 0;
	private double maxValidRate;
	
	public T_CounterEncoder(int channel, double maxRate) {
		counter = new Counter(channel);
		counter.setDistancePerPulse(1.0);
		this.maxValidRate = maxRate *1.5;
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
		
		// Note: The counter encoder can be very noisy.
		// Occasionally, the encoder reads very large
		// numbers, especially in the case where
		// another nearby motor starts up.
		double rate = counter.getRate();
		
		// Throw away bad data
		// The value is bad if the rate < 0;
		if (rate < 0) {
			return prevRate;
		}
		
		// The value is bad if the rate is 
		// more than 150% of the maxRate
		if (rate > maxValidRate) {
			return prevRate;
		}

		// The encoder should not slew at more than
		// 20 rotations per second per 20ms
		// Note: this number is arbitrary.
		double delta = rate - prevRate;
		
		if ( Math.abs(delta) > 20.0 ) {
			
			badDataCount++;

			// If there were 10 bad data elements
			// in a row, then assume the data is 
			// good and continue from this point
			if (badDataCount > 10) {
				badDataCount = 0;
				prevRate = rate;
				return rate;
			}
			
			return prevRate;
		}
		
		badDataCount = 0;
		prevRate = rate;
		return rate;
	}

	@Override
	public void reset() {
		counter.reset();
	}

}
