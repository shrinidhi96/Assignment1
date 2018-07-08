package somepackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MinMax {

  public static int numberOfComparisons = 0;

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
   * 
   * @throws IOException For file reader
   */

  public static void main(String[] args) throws IOException {
    int n = 0;
    Datum[] inputBuffer = null;
    Datum[] outputBuffer = new Datum[2];
    int i = 0;
    int sensorId;
    String location;
    float time;
    float temperature;
    char unit;
    String line;
    String path = "C:\\Users\\JAYA\\Forgit\\Assignment1\\MyProject\\src\\somepackage\\obj.txt";
    BufferedReader b = new BufferedReader(new FileReader(path));
    while ((line = b.readLine()) != null) {
      n++;
    }
    inputBuffer = new Datum[n];
    BufferedReader br = new BufferedReader(new FileReader(path));
    while ((line = br.readLine()) != null) {
      String[] attributes = line.split("\\s+");
      sensorId = Integer.parseInt(attributes[0]);
      location = attributes[1];
      time = Float.parseFloat(attributes[2]);
      temperature = Float.parseFloat(attributes[3]);
      unit = attributes[4].charAt(0);
      inputBuffer[i] = new Datum(sensorId, location, time, temperature, unit);
      if (unit == 'F') {
        inputBuffer[i].convertToCelsius();
      }
      if (i == 0) {
        outputBuffer[0] = new Datum(sensorId, location, time, temperature, unit);
        outputBuffer[1] = new Datum(sensorId, location, time, temperature, unit);
      }
      System.out.println();

      i++;
    }
    br.close();
    b.close();

    outputBuffer = neuron(inputBuffer, n, outputBuffer);
    System.out.print("\nMin:");
    outputBuffer[0].printData();
    System.out.print("\nMax:");
    outputBuffer[1].printData();
    String toPrint =
        "\nThe total number of comparisons for n = " + n + " is " + numberOfComparisons;
    System.out.println(toPrint);
  }

  /**
   * The recursive function that finds the min and max from the set of objects.
   */
  public static Datum[] neuron(Datum[] inputBuffer, int n, Datum[] outputBuffer) {
    float t1;
    float t2;
    if (n > 0) {
      if (n == 1) {
        numberOfComparisons += 1;
        t1 = inputBuffer[0].temperature;
        if (t1 < outputBuffer[0].temperature) {
          numberOfComparisons += 1;
          outputBuffer[0].setOutputBuffer(inputBuffer, 0);
        }
        if (t1 > outputBuffer[1].temperature) {
          numberOfComparisons += 1;
          outputBuffer[1].setOutputBuffer(inputBuffer, 0);
        }
        return outputBuffer;
      }
      if (n == 2) {
        numberOfComparisons += 1;
        if (inputBuffer[0].temperature > inputBuffer[1].temperature) {
          numberOfComparisons += 1;
          t1 = inputBuffer[1].temperature; // min
          t2 = inputBuffer[0].temperature; // max
          if (t1 < outputBuffer[0].temperature) {
            numberOfComparisons += 1;
            outputBuffer[0].setOutputBuffer(inputBuffer, 1);
          }
          if (t2 > outputBuffer[1].temperature) {
            numberOfComparisons += 1;
            outputBuffer[1].setOutputBuffer(inputBuffer, 0);
          }

        } else {
          numberOfComparisons += 1;
          t1 = inputBuffer[0].temperature;
          t2 = inputBuffer[1].temperature;
          if (t1 < outputBuffer[0].temperature) {
            numberOfComparisons += 1;
            outputBuffer[0].setOutputBuffer(inputBuffer, 0);
          }
          if (t2 > outputBuffer[1].temperature) {
            numberOfComparisons += 1;
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
      // split and copy
      Datum[] tempInput1 = new Datum[split1];
      Datum[] tempInput2 = new Datum[split2];
      int index = 0;
      int k = 0;
      for (index = 0; index < split1; index++) {
        tempInput1[index] = new Datum(-1, "", 0, -9, 'C'); // simply instantiating
        tempInput1[index].setOutputBuffer(inputBuffer, index);
      }
      for (index = split1; index < n; index++) {
        tempInput2[k] = new Datum(-1, "", 0, -9, 'C'); // simply instantiating
        tempInput2[k++].setOutputBuffer(inputBuffer, index);
      }
      outputBuffer = neuron(tempInput1, split1, outputBuffer);
      outputBuffer = neuron(tempInput2, split2, outputBuffer);
    } // end of if(n>0)
    return outputBuffer;
  }

}
