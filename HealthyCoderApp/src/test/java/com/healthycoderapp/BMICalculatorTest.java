package com.healthycoderapp;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class BMICalculatorTest {

	private String environment = "dev";

	// HOW TO USE BeforeAll AND AfterAll (Note : we must use the static keyword for
	// beforeAll and afterAll methods)

	@BeforeAll
	static void beforeAll() {
		System.out.println("Testing Before All");
	}

	@AfterAll
	static void afterall() {
		System.out.println("Testing After All");
	}

	@Test
	void test() {
		assertTrue(BMICalculator.isDietRecommended(80, 1.5));
	}

	@Test
	void should_Return_True_When_isDietRecommended() {

		// given
		double height = 1.5;
		double weight = 80;

		// when
		boolean result = BMICalculator.isDietRecommended(weight, height);

		// then
		assertTrue(result);
	}

	@Test
	void should_Return_False_When_isDietRecommended() {

		// given
		double height = 5;
		double weight = 80;

		// when
		boolean result = BMICalculator.isDietRecommended(weight, height);

		// then
		assertFalse(result);
	}

	// HOW TO CHECK EXCEPTIONS IN JUNIT

	@Test
	void should_Throw_ArithmeticException_When_isDietRecommended() {

		// given
		double height = 0;
		double weight = 80;

		// when
		Executable executable = () -> BMICalculator.isDietRecommended(weight, height);

		// then
		assertThrows(ArithmeticException.class, executable);
	}

	@Test
	void should_Return_Worst_Coder_When_findCoderWithWorstBMI() {

		// given
		List<Coder> coders = new ArrayList();
		coders.add(new Coder(1.80, 60));
		coders.add(new Coder(1.82, 98));
		coders.add(new Coder(1.82, 64));

		// when
		Coder result = BMICalculator.findCoderWithWorstBMI(coders);

		// then
		assertEquals(1.82, result.getHeight());
		assertEquals(98, result.getWeight());
	}

	// IN THE ABOVE TEST CASE IF ONE ASSERT FAILS WE WON'T BE ABLE TO CHECK THE
	// SECOND ASSERT IN THAT CASE WE MUST USE assertAll

	@Test
	void should_Return_Worst_Coder_When_findCoderWithWorstBMI_Using_assertAll() {

		// given
		List<Coder> coders = new ArrayList();
		coders.add(new Coder(1.80, 60));
		coders.add(new Coder(1.82, 98));
		coders.add(new Coder(1.82, 64));

		// when
		Coder result = BMICalculator.findCoderWithWorstBMI(coders);

		// then
		assertAll(() -> assertEquals(1.82, result.getHeight()), () -> assertEquals(98, result.getWeight()));
	}

	// HOW TO COMPARE NULL

	@Test
	void should_Return_Null_When_findCoderWithWorstBMI() {

		// given
		List<Coder> coders = new ArrayList();

		// when
		Coder result = BMICalculator.findCoderWithWorstBMI(coders);

		// then
		assertNull(result);
	}

	@ParameterizedTest
	@ValueSource(doubles = { 90, 110.0, 92.4 })
	void should_Return_True_When_isDietRecommended_Using_ParameterizedTest(double coderWeights) {

		// given
		double height = 1.72;
		double weight = coderWeights;

		// when
		boolean result = BMICalculator.isDietRecommended(weight, height);

		// then
		assertTrue(result);
	}

	@ParameterizedTest(name = "weight={0},height={1}")
	@CsvSource(value = { "85.0,1.72", "95.0,1.75", "110.0,1.78" })
	void should_Return_True_When_isDietRecommended_Using_ParameterizedTest_And_CsvSource(double coderWeights,
			double coderHeights) {

		// given
		double height = coderHeights;
		double weight = coderWeights;

		// when
		boolean result = BMICalculator.isDietRecommended(weight, height);

		// then
		assertTrue(result);
	}

	// WE CAN USE THE DATA FORM THE CSV FILE BU USING THE @CsvFileSource ANNOTATION
	// IN resources WE JUST NEED TO SPECIFY THE FILE NAME BECAUSE IT AUTOMATICALLY
	// LOOKS IN THE PATH ie.src/test/resources
	// numLinesToSkip IS USED TO SKIP THE LINES IN THE FILE TO IGNORE FOR EXAMPLE
	// THE HEADER
	@ParameterizedTest(name = "weight={0},height={1}")
	@CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
	void should_Return_True_When_isDietRecommended_Using_ParameterizedTest_And_CsvFileSource(double coderWeights,
			double coderHeights) {

		// given
		double height = coderHeights;
		double weight = coderWeights;

		// when
		boolean result = BMICalculator.isDietRecommended(weight, height);

		// then
		assertTrue(result);
	}

	// RepeatedTest ANNOTATION IS USED TO REPEAT THE TEST METHOD FOR MULTIPLE TIMES
	// WE MAY USE THIS WHEN WE HAVE SOME RANDOM NUMBERS GENERATION OR USING WITH
	// MULTIPLE THREADS, WE MAY ALSO PROVIDE THE DISPLAY NAME BY USING name
	// ATTRIBUTE

	@RepeatedTest(value = 10, name = RepeatedTest.LONG_DISPLAY_NAME)
	void should_Return_True_When_isDietRecommended_Using_RepeatedTest() {

		// given
		double height = 1.5;
		double weight = 80;

		// when
		boolean result = BMICalculator.isDietRecommended(weight, height);

		// then
		assertTrue(result);
	}

	// HOW TO TEST THE PERFORMANCE IS GIVEN BELOW

	@Test
	void should_Check_Performance_When_findCoderWithWorstBMI() {

		// given
		List<Coder> coders = new ArrayList();
		for (int i = 1; i <= 10000; i++) {
			coders.add(new Coder(1.80 + i, 60 + i));
		}

		// when
		// EXECUTABLE IS AN FunctionalInterface HAVING ABSTRACT METHOD AS execute
		Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);

		// then
		// TRY CHANGING THE DURATINN T0 1 MS TO SEE THE FAILURE
		assertTimeout(Duration.ofMillis(500), executable);
	}

	// HOW TO USE ASSUMPTIONS
	// WE CAN USE assumeTrue FOR ASSUMTIONS
	// IN THE BELOW METHOD I WANT TO RUN THIS TEST METHOD ONLY WHEN THE ENVIRONMENT
	// IS dev
	// WE CANNOT USE assertTrue instead of assumeTrue BECAUSE IF THE CONDITION IS
	// NOT SATISFIED
	// IT WILL FAIL THE TEST CASE
	@Test

	void should_Check_Performance_When_findCoderWithWorstBMI_Using_Assumptions() {

		// given
		assumeTrue(this.environment.equals("dev"));
		List<Coder> coders = new ArrayList();
		for (int i = 1; i <= 10000; i++) {
			coders.add(new Coder(1.80 + i, 60 + i));
		}

		// when
		// EXECUTABLE IS AN FunctionalInterface HAVING ABSTRACT METHOD AS execute
		Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);

		// then
		// TRY CHANGING THE DURATINN T0 1 MS TO SEE THE FAILURE
		assertTimeout(Duration.ofMillis(5), executable);
	}

	// HOW WE CAN ORGANISE OUR TEST METHODS IS BY USING Nested ANNOTATION AND
	// PLACING ALL THE TEST METHODS FOT THE SAME METHOD IN A INNER CLASS
	// WE CAN USE DisplayName TO DISPLAY ANY NAME FOR CLASS AS WELL AS METHOD
	@Nested
	@DisplayName("This is how we an display any name for a class")
	class getBMIScoresTests {
		// HOW TO COMPARE ARRAYS
		// WE CAN USE Disabled ANNOTATION TO SKIP ANY METHOD
		// WE CAN USE DisabledOnOs ANNOTATION TO SKIP ANY METHOD ON A PARTICULAR OS
		@Test
//		@Disabled
//		@DisabledOnOs(OS.WINDOWS)
		void should_Return_BMIScores_When_getBMIScores() {

			// given
			List<Coder> coders = new ArrayList();
			coders.add(new Coder(1.80, 60));
			coders.add(new Coder(1.82, 98));
			coders.add(new Coder(1.82, 64.7));

			double[] expected = { 18.52, 29.59, 19.53 };

			// when
			double[] result = BMICalculator.getBMIScores(coders);
			// then
			assertArrayEquals(result, expected);
		}
	}
}
