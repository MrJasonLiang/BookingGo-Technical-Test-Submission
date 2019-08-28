package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import main.Part1;
import main.Part1.InvalidArgumentException;

public class Part1Test {
	@Test
	public void testPart1Validation() {
		try {
			new Part1().run(new String[] {});
			fail("Did not detect no arguments supplied.");
		} catch (InvalidArgumentException e) {}
		
		try {
			new Part1().run(new String[] {"1.31,2.34"});
			fail("Did not detect only 1 location supplied.");
		} catch (InvalidArgumentException e) {}
		
		try {
			new Part1().run(new String[] {"1.31,2.41", "2"});
			fail("Did not detect invalid dropoff location (only 1 of 2 coordinates given) supplied.");
		} catch (InvalidArgumentException e) {}

		try {
			new Part1().run(new String[] {"-2.305,2.463", "one,two"});
			fail("Did not detect invalid dropoff location (contains letters) supplied.");
		} catch (InvalidArgumentException e) {}
		
		try {
			new Part1().run(new String[] {"-2.305,2.46.3", "3.41,5.241"});
			fail("Did not detect invalid pickup location (longitude is not a number) supplied.");
		} catch (InvalidArgumentException e) {}
		
		try {
			new Part1().run(new String[] {"-2.305,2.463", "2.311,2.453", "-5"});
			fail("Did not detect invalid number of passengers (negative number) supplied.");
		} catch (InvalidArgumentException e) {}
		
		try {
			new Part1().run(new String[] {"-2.305,2.463", "2.311,2.453", "two"});
			fail("Did not detect invalid number of passengers (not a number) supplied.");
		} catch (InvalidArgumentException e) {}
		
		try {
			new Part1().run(new String[] {"-2.305,2.463", "2.311,2.453", "4.4"});
			fail("Did not detect invalid number of passengers (not a whole number) supplied.");
		} catch (InvalidArgumentException e) {}
	}
}
