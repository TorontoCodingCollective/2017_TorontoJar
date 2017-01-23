package com.toronto.sensors;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;

public class T_Gyro extends AnalogGyro {
	
	/*check if inverted (boolean) multiply by negative 1
	* 
	*
	*/
	public T_Gyro(AnalogInput channel) {
		super(channel);
	}
	
	public T_Gyro(AnalogInput channel, int center, double offset) {
		super(channel, center, offset);
	}
	
	 public T_Gyro(int channel) {
		 super(channel);
	 }
	 
	 /**
	  * 
	  * @param current: current angle
	  * @param target: where you want to go
	  * @return 
	  */
	 public double getOffset(double current, double target) {
		 
		 double offset;
		 
		 //if 180<current<360 then convert current angle to fall within 0<current<-180
		 //does same to target
		 current = (current > 180) ? current - 360: current;
		 target = (target > 180) ? target - 360: current;
		 
		 offset = target - current;
		 return (offset);
	 }
	 
	 /**
	  * 
	  * @param current: current angle
	  * @return return 0<=current<360
	  */
	 public double getAngle(double current) {	 
		 return (current %= 360.0);
	 }
	 
	 
	 
	 
}
