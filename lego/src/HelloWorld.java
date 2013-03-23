import java.io.*;
import lejos.nxt.*;
import lejos.nxt.comm.*;

public class HelloWorld {

  public static void main (String[] args) {
    
    LCD.drawString("- Devoxx -",3, 0);
    LCD.drawString("3615 CLOUD",3, 2);

    ColorSensor l = new ColorSensor(SensorPort.S1);
    l.setFloodlight(ColorSensor.Color.BLUE);


    NXTConnection connection = Bluetooth.waitForConnection();

    DataInputStream in = connection.openDataInputStream();
    try {
        String message = in.readUTF();
        LCD.drawString(message,0, 4);
    } catch (IOException e) {
        LCD.drawString("IO error",0, 4);
    }

    l.setFloodlight(ColorSensor.Color.RED);

    int MAX_DISTANCE = 50; // In centimeters
    int PERIOD = 500; // In milliseconds
    UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
    us.continuous();


    Motor.C.setSpeed(200);
    Motor.B.setSpeed(200);
    Motor.A.setSpeed(720);

    Motor.C.forward();
    Motor.B.backward();
    // NXTRegulatedMotor m = new NXTRegulatedMotor(MotorPort.C);
 
    while (us.getDistance() > 250) {
        // m.rotate(360);
    }

    Motor.C.stop();
    Motor.B.stop();

    Motor.A.rotate(360);

//    connection.close();
    LCD.clear();
  }

}
