package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DietPlannerTest {
	private DietPlanner dietPlanner;

	// HOW TO USE beforeEach AND afterEach

	@BeforeEach
	void setup() {
		this.dietPlanner = new DietPlanner(20, 30, 50);
	}

	@AfterEach
	void afterEach() {
		System.out.println("Testing After Each");
	}

	@Test
	void should_Return_Diet_Plan_When_calculateDiet() {
		// given
		Coder coder = new Coder(1.82, 75, 26, Gender.MALE);
		DietPlan expected = new DietPlan(2202, 110, 73, 275);
		// when
		DietPlan actual = dietPlanner.calculateDiet(coder);

		// then
		assertAll(() -> assertEquals(expected.getCalories(), actual.getCalories()),
				() -> assertEquals(expected.getProtein(), actual.getProtein()),
				() -> assertEquals(expected.getFat(), actual.getFat()),
				() -> assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate()));
	}

}
