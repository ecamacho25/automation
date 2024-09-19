package testCases;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import pages.HomePage;

public class HomePageTest extends HomePage{
	
	@Test
	@Description ("This is the first test automated")
	public void testOne () {
		enterCity("New York");
		enterFirstName("Edier");
		enterLastName("Camacho");
		waitFor(5);
		
	}

}
