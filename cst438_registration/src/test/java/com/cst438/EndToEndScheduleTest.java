package com.cst438;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cst438.service.JwtService;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class EndToEndScheduleTest {
	public static final String CHROME_DRIVER_FILE_LOCATION = "/Users/machavez/Desktop/chromedriver-mac-arm64/chromedriver";

	public static final String URL = "http://localhost:3000";

	public static final String TEST_USER_ALIAS = "admin"; 
	public static final String TEST_USER_ROLE = "admin";

	public static final String TEST_CITY = "Seattle";

	public static final int SLEEP_DURATION = 1000; // 1 second.
	
	@Autowired
	private JwtService jwtService;
	
	@Order(1)
	@Test
	public void addCityToList() throws Exception {
		
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
		String jwtToken = TEST_USER_ALIAS;

		ChromeOptions options = new ChromeOptions();
		options.addArguments("Authorization=Bearer " + jwtToken);

		WebDriver driver = new ChromeDriver(options);

		// Puts an Implicit wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		
	}

}
