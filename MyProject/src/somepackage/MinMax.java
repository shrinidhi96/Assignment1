package somepackage;

import java.util.Scanner;

public class MinMax {

  public static class Datum {
    int sensorId;
    String location;
    float time;
    float temperature;
    char unit;

    /**
     * Constructor of the class to initialize the class members.
     */
    public Datum(int sensorId, String location, float time, float temperature, char unit) {
      this.sensorId = sensorId;
      this.location = location;
      this.time = time;
      this.temperature = temperature;
      this.unit = unit;

    }

    /**
     * Used to print the contents of the calling object.
     */
    public void printData() {
      System.out.print("\nSensor id:" + this.sensorId);
      System.out.print("\nLocation:" + this.location);
      System.out.print("\nTime:" + this.time);
      System.out.print("\nTemperature:" + this.temperature);
      System.out.print("\nUnit:" + this.unit);
      System.out.println();

    }

    /**
     * Converts F to C, since the standard unit is C in this program.
     */

    public void convertToCelsius() {
      float k = this.temperature;
      k = (k - 32) * (5 / 9);
      this.temperature = k;
      this.unit = 'C';

    }

    /**
     * Copies the attributes of the specific inputBuffer object at position i to the calling object.
     */
    public void setOutputBuffer(Datum[] inputBuffer, int i) {

      this.sensorId = inputBuffer[i].sensorId;
      this.location = inputBuffer[i].location;
      this.time = inputBuffer[i].time;
      this.temperature = inputBuffer[i].temperature;
      this.unit = inputBuffer[i].unit;
    }

  }

  /**
   * main function.
   */

  public static void main(String[] args) {
    int n = 0;
    System.out.println("Enter the amount of data in the input buffer:");
    Scanner s = new Scanner(System.in);
    n = s.nextInt();
    Datum[] inputBuffer = new Datum[n];
    Datum[] outputBuffer = new Datum[2];
    int i = 0;
    int sensorId;
    String location;
    float time;
    float temperature;
    char unit;
    System.out.println("Feed the input buffer please!");
    for (i = 0; i < n; i++) {
      System.out.print("Sensor id:");
      sensorId = s.nextInt();
      System.out.print("Location:");
      location = s.next();
      System.out.print("Time:");
      time = s.nextFloat();
      System.out.print("Temperature:");
      temperature = s.nextFloat();
      System.out.print("Unit:");
      unit = s.next().charAt(0);
      inputBuffer[i] = new Datum(sensorId, location, time, temperature, unit);
      if (unit == 'F') {
        inputBuffer[i].convertToCelsius();
      }
      if (i == 0) {
        outputBuffer[0] = new Datum(sensorId, location, time, temperature, unit);
        outputBuffer[1] = new Datum(sensorId, location, time, temperature, unit);
      }
      System.out.println();
    }
    outputBuffer = neuron(inputBuffer, n, outputBuffer);
    System.out.println("\nMin:");
    outputBuffer[0].printData();
    System.out.println("\nMax:");
    outputBuffer[1].printData();
    s.close();

  }

  /**
   * The recursive function that finds the min and max from the set of objects.
   */
  public static Datum[] neuron(Datum[] inputBuffer, int n, Datum[] outputBuffer) {
    float t1;
    float t2;
    if (n > 0) {
      if (n == 1) {
        t1 = inputBuffer[0].temperature;
        if (t1 < outputBuffer[0].temperature) {
          outputBuffer[0].setOutputBuffer(inputBuffer, 0);
        }
        if (t1 > outputBuffer[1].temperature) {
          outputBuffer[1].setOutputBuffer(inputBuffer, 0);
        }
        return outputBuffer;
      }
      if (n == 2) {
        if (inputBuffer[0].temperature > inputBuffer[1].temperature) {
          t1 = inputBuffer[1].temperature; // min
          t2 = inputBuffer[0].temperature; // max
          if (t1 < outputBuffer[0].temperature) {
            outputBuffer[0].setOutputBuffer(inputBuffer, 1);
          }
          if (t2 > outputBuffer[1].temperature) {
            outputBuffer[1].setOutputBuffer(inputBuffer, 0);
          }

        } else {
          t1 = inputBuffer[0].temperature;
          t2 = inputBuffer[1].temperature;
          if (t1 < outputBuffer[0].temperature) {
            outputBuffer[0].setOutputBuffer(inputBuffer, 0);
          }
          if (t2 > outputBuffer[1].temperature) {
            outputBuffer[1].setOutputBuffer(inputBuffer, 1);
          }
        }
        return outputBuffer;
      }
      int split1;
      int split2;
      int i = 0;
      while ((int) Math.pow(2, i) < n) {
        i++;
      }
      split1 = (int) Math.pow(2, i - 1);
      split2 = n - split1;
      outputBuffer = neuron(inputBuffer, split1, outputBuffer);
      outputBuffer = neuron(inputBuffer, split2, outputBuffer);
    } // end of if(n>0)
    return outputBuffer;
  }

}
