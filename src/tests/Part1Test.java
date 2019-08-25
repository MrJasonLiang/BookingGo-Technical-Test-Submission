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
			fail("Expected an InvalidArgumentException to be thrown.");
		} catch (InvalidArgumentException e) {}
		
		// TODO Add more test cases
	}

}
