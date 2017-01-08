package io.github.learnjava8;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

public class LambdaTests {

	// The following is what you would call a functional interface (one method)
	public static interface Reversable {
		String reverse(String value);
	}

	@Test
	public void testClassicPreJava8Syntax() {
		// This is the old way to inline an implementation
		Reversable reverser = new Reversable() {
			@Override
			public String reverse(String value) {
				return new StringBuilder(value).reverse().toString();
			}
		};
		assertEquals("notgnihsaW", reverser.reverse("Washington"));
	}

	@Test
	public void testLambdaExpression() {
		Reversable reverser = value -> new StringBuilder(value).reverse().toString();
		assertEquals("notgnihsaW", reverser.reverse("Washington"));
	}

	// Functional interfaces don't need arguments
	public static interface NoArgFunctionalInterface {
		int calculate();
	}

	@Test
	public void testNoArgFunctionalInterface() {
		// Use () as a place holder
		NoArgFunctionalInterface randomValueGenerator = () -> (new Random()).nextInt(100) + 1;
		int randomValue = randomValueGenerator.calculate();
		assertTrue(randomValue > 0);
	}

	// Functional interfaces do arguments too
	public static interface ThreeArgFunctionalInterface {
		int sum(int a, int b, int c);
	}

	@Test
	public void testThreeArgFunctionalInterface() {
		// You can still use braces if you need to use more than one line
		ThreeArgFunctionalInterface adder = (a, b, c) -> {
			return a + b + c;
		};
		int sum = adder.sum(3, 6, 9);
		assertEquals(18, sum);
	}
}
