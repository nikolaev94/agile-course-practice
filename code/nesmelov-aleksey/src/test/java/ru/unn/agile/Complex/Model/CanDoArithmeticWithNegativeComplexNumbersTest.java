package test.java.ru.unn.agile.Complex.Model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import main.java.ru.unn.agile.Complex.Model.*;

public class CanDoArithmeticWithNegativeComplexNumbersTest {
    private Complex first;
    private Complex second;

    @Before
    public void setUpNegativeComplexNumbers() {
        first = new Complex(-2, -3);
        second = new Complex(-1, -1);
    }

    @Test
    public void canAddComplexNumbersWithNegativeParts() {
        setUpNegativeComplexNumbers();

        Complex result = first.add(second);

        assertEquals(result, new Complex(-3, -4));
    }

    @Test
    public void canSubtractComplexNumbersWithNegativeParts() {
        setUpNegativeComplexNumbers();

        Complex result = first.subtract(second);

        assertEquals(result, new Complex(-1, -2));
    }

    @Test
    public void canMultiplyComplexNumbersWithNegativeParts() {
        setUpNegativeComplexNumbers();

        Complex result = first.multiply(second);

        assertEquals(result, new Complex(-1, 5));
    }

    @Test
    public void canDivideComplexNumbersWithNegativeParts() {
        setUpNegativeComplexNumbers();

        Complex result = first.divide(second);

        assertEquals(result, new Complex(2.5, 0.5));
    }
}
