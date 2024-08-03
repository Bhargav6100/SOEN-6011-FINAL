import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TangentFunctionCalculator is a Swing-based application that calculates the tangent of a given angle in degrees.
 * The application includes a graphical user interface (GUI) for user input, result display, and computation time.
 */
public class TangentFunctionCalculator {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the main frame
            JFrame frame = new JFrame("Tangent Function Calculator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLayout(new BorderLayout());

            // Create the input panel with components
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new FlowLayout());

            JLabel angleLabel = new JLabel("Enter angle in degrees:"); // Label for angle input
            JTextField angleField = new JTextField(10); // Text field for angle input
            JButton calculateButton = new JButton("Calculate"); // Button to trigger calculation

            inputPanel.add(angleLabel);
            inputPanel.add(angleField);
            inputPanel.add(calculateButton);

            // Create the result area
            JTextArea resultArea = new JTextArea();
            resultArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(resultArea); // Scroll pane for result area

            // Create the time panel with components
            JLabel timeLabel = new JLabel("Computation time: "); // Label for computation time
            JTextField timeField = new JTextField(15); // Text field to display computation time
            timeField.setEditable(false);

            JPanel timePanel = new JPanel();
            timePanel.setLayout(new FlowLayout());
            timePanel.add(timeLabel);
            timePanel.add(timeField);

            // Add components to the frame
            frame.add(inputPanel, BorderLayout.NORTH);
            frame.add(scrollPane, BorderLayout.CENTER);
            frame.add(timePanel, BorderLayout.SOUTH);

            // Add action listener to the calculate button
            calculateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        double degrees = Double.parseDouble(angleField.getText());
                        long startTime = System.nanoTime();
                        double result = tan(degrees);
                        long endTime = System.nanoTime();
                        long duration = (endTime - startTime); // duration in nanoseconds
                        double durationInMs = duration / 1e6; // convert to milliseconds
                        resultArea.setText(String.format("The tangent of %.2f degrees is %.4f%n", degrees, result));
                        timeField.setText(String.format("%.6f ms", durationInMs));
                    } catch (NumberFormatException ex) {
                        resultArea.setText("Invalid input. Please enter a valid number.");
                    } catch (IllegalArgumentException ex) {
                        resultArea.setText(ex.getMessage());
                    }
                }
            });

            frame.setVisible(true);
        });
    }

    /**
     * Computes the tangent of the given angle in degrees without using built-in trigonometric functions.
     *
     * @param degrees the angle in degrees
     * @return the tangent of the angle
     * @throws IllegalArgumentException if the angle is where the tangent is undefined
     */
    public static double tan(double degrees) throws IllegalArgumentException {
        // Normalize degrees to the range [0, 360)
        double normalizedDegrees = degrees % 360;
        if (normalizedDegrees < 0) {
            normalizedDegrees += 360;
        }

        // Check if the normalized angle is an odd multiple of 90 degrees
        if ((normalizedDegrees % 180) == 90) {
            throw new IllegalArgumentException("The tangent function is undefined at this angle (angle is an odd multiple of 90 degrees).");
        }

        // Convert degrees to radians
        double radians = degrees * Math.PI / 180.0;

        // Calculate sine and cosine manually
        double sin = sine(radians);
        double cos = cosine(radians);

        // Handle very small cosine values to avoid division by zero
        if (Math.abs(cos) < 1e-15) {
            throw new IllegalArgumentException("The tangent function is undefined at this angle (cosine of the angle is zero).");
        }

        double result = sin / cos;

        // Handle very small values of result to avoid displaying -0.0000
        if (Math.abs(result) < 1e-10) {
            result = 0.0;
        }

        return result;
    }

    /**
     * Computes the sine of the given angle in radians.
     *
     * @param radians the angle in radians
     * @return the sine of the angle
     */
    public static double sine(double radians) {
        double sine = radians; // First term in the series
        double term = radians;
        int n = 1; // counter used for generating the factorial in the denominator of the Taylor series terms.

        while (Math.abs(term) > 1e-15) {
            term = -term * radians * radians / (2 * n * (2 * n + 1)); // Taylor series to compute Sine
            sine += term;
            n++;
        }

        return sine;
    }

    /**
     * Computes the cosine of the given angle in radians.
     *
     * @param radians the angle in radians
     * @return the cosine of the angle
     */
    public static double cosine(double radians) {
        double cosine = 1.0; // First term in the series
        double term = 1.0;
        int n = 1; // counter used for generating the factorial in the denominator of the Taylor series terms.

        while (Math.abs(term) > 1e-15) {
            term = -term * radians * radians / (2 * n * (2 * n - 1)); // Taylor series for Cosine
            cosine += term;
            n++;
        }

        return cosine;
    }
}
