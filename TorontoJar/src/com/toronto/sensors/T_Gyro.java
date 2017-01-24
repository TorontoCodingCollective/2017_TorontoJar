package com.toronto.sensors;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;

/**
 * This class extends the FRC AnalogGyro class to add some additional functionality.
 * When returning the angle, the T_Gyro always returns a value from 0 to 360 degrees.
 * <p>
 * In order to support PID controls, the getError(angle) method can return the 
 * error in degrees from the target angle.
 *
 */
public class T_Gyro extends AnalogGyro {
	
	double offset = 0.0;
	boolean inverted = false;
	
	/**
	 * Gyro constructor with a precreated analog channel object. Use this constructor when the analog
	 * channel needs to be shared.
	 *
	 * @param channel The AnalogInput object that the gyro is connected to. Gyros can only be used on
	 *                on-board channels 0-1.
	 */
	public T_Gyro(AnalogInput channel) {
		super(channel);
	}
	
	/**
	 * Gyro constructor with a precreated analog channel object. Use this constructor when the analog
	 * channel needs to be shared and the gyro is inverted.
	 *
	 * @param channel The AnalogInput object that the gyro is connected to. Gyros can only be used on
	 *                on-board channels 0-1.
	 * @param inverted {@code true} if the gyro is inverted, {@code false} otherwise.
	 */
	public T_Gyro(AnalogInput channel, boolean inverted) {
		super(channel);
		this.inverted = inverted;
	}
	
	/**
	 * Gyro constructor with a precreated analog channel object along with parameters for presetting
	 * the center and offset values. Bypasses calibration.
	 *
	 * @param channel The analog channel the gyro is connected to. Gyros can only be used on on-board
	 *                channels 0-1.
	 * @param center  Preset uncalibrated value to use as the accumulator center value.
	 * @param offset  Preset uncalibrated value to use as the gyro offset.
	 */
	public T_Gyro(AnalogInput channel, int center, double offset) {
		super(channel, center, offset);
	}
	
	/**
	 * Gyro constructor with a precreated analog channel object along with parameters for presetting
	 * the center and offset values. Bypasses calibration.
	 *
	 * @param channel The analog channel the gyro is connected to. Gyros can only be used on on-board
	 *                channels 0-1.
	 * @param center  Preset uncalibrated value to use as the accumulator center value.
	 * @param offset  Preset uncalibrated value to use as the gyro offset.
	 * @param inverted {@code true} if the gyro is inverted, {@code false} otherwise.
	 */
	public T_Gyro(AnalogInput channel, int center, double offset, boolean inverted) {
		super(channel, center, offset);
		//FIXME: inverted?
	}
	
	/**
	 * Gyro constructor using the channel number
	 *
	 * @param channel The analog channel the gyro is connected to. Gyros can only be used on on-board
	 *                channels 0-1.
	 */
	public T_Gyro(int channel) {
		 super(channel);
	 }
	 
	/**
	 * Gyro constructor using the channel number
	 *
	 * @param channel The analog channel the gyro is connected to. Gyros can only be used on on-board
	 *                channels 0-1.
	 * @param inverted {@code true} if the gyro is inverted, {@code false} otherwise.
	 */
	public T_Gyro(int channel, boolean inverted) {
		 super(channel);
		 // FIXME:?
	 }
	 
	 @Override 
	 public void reset() {
		 // FIXME: Should the reset also clear the offset?
		 // how about setAngle(0.0d);?
	 }
	 
	 /**
	  * Set the gyro to the supplied angle.  This routine will ensure the 
	  * output of the gyro is reset to the supplied angle.
	  * @param angle
	  */
	 public void setAngle(double angle) {
		 // FIXME: reset the gyro and set the offset to the passed in angle
	 }
	 
	 /** 
	  * Get the error in degrees from the current angle to the target angle.  This 
	  * routine calculates the minimum error, always returning an angle between
	  * -180 degrees and +180 degrees.
	  * @param targetAngle (0 to 360 degrees)
	  * @return angle difference from the current angle (-180 to +180 degrees).  A positive
	  * number indicates the target is clockwise from the current angle.  A negative 
	  * number indicates the target is counter-clockwise from the current angle.
	  */
	 public double getAngleError(double targetAngle) {
		 
		 double currentAngle = getAngle();
		 // FIXME: calculate the error
//		 double offset;
//		 
//		 //if 180<current<360 then convert current angle to fall within 0<current<-180
//		 //does same to target
//		 current = (current > 180) ? current - 360: current;
//		 target = (target > 180) ? target - 360: current;
//		 
//		 offset = target - current;
//		 return (offset);

		 return 0.0d;
		 
	 }

	 /**
	  * Set the gyro inversion to the passed in state.  This routine also resets the 
	  * current gyro angle to zero and clears the offset.  The inversion state can 
	  * also be set using the appropriate constructor.
	  * @param inverted {@code true} if the gyro is inverted {@code false} otherwise
	  */
	 public void setInverted(boolean inverted) {
		 
		 // FIXME: Do something appropriate here.
	 }
	 
	 @Override
	 public double getRate() {
		 // FIXME:  What if the gyro is inverted (upside down)?
		 return super.getRate();
	 }
	 
	 @Override
	 public double getAngle() {
		 
		 // FIXME: What if the Gyro is inverted (upside down)?
		 
		 // FIXME:  What if the angle is negative (always return a positive number).
		 
		 return (super.getAngle() % 360.0);  // <- this will not work
	 }
	 
}
