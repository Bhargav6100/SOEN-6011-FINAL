package main.java;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * A Swing-based application that calculates the tangent of a given angle in degrees.
 * The application includes a graphical user interface (GUI) for user input, result display,
 * and computation time.
 *
 * @author Bhargav Fofandi
 * @version 1.1.0
 */
public class TangentFunctionCalculator {

  private static final int FRAME_WIDTH = 400;
  private static final int FRAME_HEIGHT = 300;
  private static final int TEXT_FIELD_LENGTH = 10;
  private static final double EPSILON = 1e-15;
  private static final double SMALL_VALUE = 1e-10;

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> createAndShowGui());
  }

  /** Creates and displays the GUI for the Tangent Function Calculator. */
  private static void createAndShowGui() {
    JFrame frame = new JFrame("Tangent Function Calculator");
    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    frame.setLayout(new BorderLayout());

    JPanel inputPanel = new JPanel(new FlowLayout());
    JLabel angleLabel = new JLabel("Enter angle in degrees:");
    JTextField angleField = new JTextField(TEXT_FIELD_LENGTH);
    JButton calculateButton = new JButton("Calculate");
    JButton exitButton = new JButton("Exit");

    inputPanel.add(angleLabel);
    inputPanel.add(angleField);
    inputPanel.add(calculateButton);
    inputPanel.add(exitButton);

    JTextArea resultArea = new JTextArea(5, 20);
    resultArea.setEditable(false);

    JLabel timeLabel = new JLabel("Computation time:");
    JTextField timeField = new JTextField(15);
    timeField.setEditable(false);

    JPanel timePanel = new JPanel(new FlowLayout());
    timePanel.add(timeLabel);
    timePanel.add(timeField);

    JScrollPane scrollPane = new JScrollPane(resultArea);
    frame.add(inputPanel, BorderLayout.NORTH);
    frame.add(scrollPane, BorderLayout.CENTER);
    frame.add(timePanel, BorderLayout.SOUTH);

    calculateButton.addActionListener(e -> {
      try {
        double degrees = Double.parseDouble(angleField.getText());
        long startTime = System.nanoTime();
        double result = calculateTangent(degrees);
        long endTime = System.nanoTime();
        long duration = endTime - startTime; // duration in nanoseconds
        double durationInMs = duration / 1e6; // convert to milliseconds
        resultArea.setText(String.format("The tangent of %.2f degrees is %.4f%n", degrees, result));
        timeField.setText(String.format("%.6f ms", durationInMs));
      } catch (NumberFormatException ex) {
        resultArea.setText("Invalid input. Please enter a valid number.");
      } catch (IllegalArgumentException ex) {
        resultArea.setText(ex.getMessage());
      }
    });

    exitButton.addActionListener(e -> {
      int confirm = JOptionPane.showConfirmDialog(frame,
              "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
      if (confirm == JOptionPane.YES_OPTION) {
        JOptionPane.showMessageDialog(frame,
            "Thank you for using the Tangent Function Calculator.");
        System.exit(0);
      }
    });

    frame.setVisible(true);
  }


  /**
   * Computes the tangent of the given angle in degrees without using built-in trigonometric
   * functions.
   *
   * @param degrees the angle in degrees
   * @return the tangent of the angle
   * @throws IllegalArgumentException if the angle is where the tangent is undefined
   */
  public static double calculateTangent(double degrees) throws IllegalArgumentException {
    double normalizedDegrees = degrees % 360;
    if (normalizedDegrees < 0) {
      normalizedDegrees += 360;
    }

    if ((normalizedDegrees % 180) == 90) {
      throw new IllegalArgumentException(
          "The tangent function is undefined at this angle" 
          + "(angle is an odd multiple of 90 degrees).");
    }

    double radians = degrees * Math.PI / 180.0;
    double sin = calculateSine(radians);
    double cos = calculateCosine(radians);

    if (Math.abs(cos) < EPSILON) {
      throw new IllegalArgumentException(
          "The tangent function is undefined at this angle (cosine of the angle is zero).");
    }

    double result = sin / cos;

    if (Math.abs(result) < SMALL_VALUE) {
      result = 0.0;
    }

    return result;
  }

  /**
   * Computes the sine of the given angle in radians using a Taylor series approximation.
   *
   * @param radians the angle in radians
   * @return the sine of the angle
   */
  public static double calculateSine(double radians) {
    double sine = radians;
    double term = radians;
    int n = 1;

    while (Math.abs(term) > EPSILON) {
      term = -term * radians * radians / (2 * n * (2 * n + 1));
      sine += term;
      n++;
    }

    return sine;
  }

  /**
   * Computes the cosine of the given angle in radians using a Taylor series approximation.
   *
   * @param radians the angle in radians
   * @return the cosine of the angle
   */
  public static double calculateCosine(double radians) {
    double cosine = 1.0;
    double term = 1.0;
    int n = 1;

    while (Math.abs(term) > EPSILON) {
      term = -term * radians * radians / (2 * n * (2 * n - 1));
      cosine += term;
      n++;
    }

    return cosine;
  }
}
