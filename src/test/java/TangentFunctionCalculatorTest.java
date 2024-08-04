package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import main.java.TangentFunctionCalculator;
import org.junit.Test;

/**
 * Tests for {@link TangentFunctionCalculator}.
 */
public class TangentFunctionCalculatorTest {

  private static final double DELTA = 1e-6; // Tolerance for floating-point comparisons.

  @Test
  public void testCalculateTangent() {
    // Test for zero degrees.
    assertEquals("Tangent of 0 degrees should be 0.", 0.0, 
        TangentFunctionCalculator.calculateTangent(0), DELTA);

    // Test for 45 degrees.
    assertEquals("Tangent of 45 degrees should be 1.", 1.0, 
        TangentFunctionCalculator.calculateTangent(45), DELTA);

    // Test for 180 degrees.
    assertEquals("Tangent of 180 degrees should be 0.", 0.0, 
        TangentFunctionCalculator.calculateTangent(180), DELTA);

    // Test for 270 degrees (undefined).
    try {
      TangentFunctionCalculator.calculateTangent(90);
      fail("Tangent of 90 degrees should throw IllegalArgumentException.");
    } catch (IllegalArgumentException e) {
      assertTrue("Expected IllegalArgumentException to be thrown", true);
    }

    try {
      TangentFunctionCalculator.calculateTangent(270);
      fail("Tangent of 270 degrees should throw IllegalArgumentException.");
    } catch (IllegalArgumentException e) {
      assertTrue("Expected IllegalArgumentException to be thrown", true);
    }
  }

  @Test
  public void testCalculateSine() {
    // Sine tests based on unit circle values.
    assertEquals("Sine of 0 radians should be 0.", 0.0, 
        TangentFunctionCalculator.calculateSine(0), DELTA);

    // Sine for PI/2 radians (90 degrees).
    assertEquals("Sine of PI/2 radians should be 1.", 1.0, 
        TangentFunctionCalculator.calculateSine(Math.PI / 2), DELTA);

    // Sine for PI radians (180 degrees).
    assertEquals("Sine of PI radians should be 0.", 0.0, 
        TangentFunctionCalculator.calculateSine(Math.PI), DELTA);
  }

  @Test
  public void testCalculateCosine() {
    // Cosine tests based on unit circle values.
    assertEquals("Cosine of 0 radians should be 1.", 1.0, 
        TangentFunctionCalculator.calculateCosine(0), DELTA);

    // Cosine for PI/2 radians (90 degrees).
    assertEquals("Cosine of PI/2 radians should be 0.", 0.0, 
        TangentFunctionCalculator.calculateCosine(Math.PI / 2), DELTA);

    // Cosine for PI radians (180 degrees).
    assertEquals("Cosine of PI radians should be -1.", -1.0, 
        TangentFunctionCalculator.calculateCosine(Math.PI), DELTA);
  }
}
