package Automation.Selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;


public class SeleniumAutomationTest {
	
WebDriver driver;
	
	@Before
	public void setup() {
		System.setProperty("webdriver.gecko.driver","/Users/arun/Downloads/geckodriver");
		driver = new FirefoxDriver();
	    driver.navigate().to("http://www.wikipedia.org/");
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void WikipediaTest() {
		String searchText = "Automation";
		driver.findElement(By.id("searchInput")).sendKeys(searchText);
		
		//Assert that English is selected 
		Select select = new Select(driver.findElement(By.id("searchLanguage")));
		WebElement selectedOptionElement = select.getFirstSelectedOption();
		String selectedLang = selectedOptionElement.getText();
		Assert.assertEquals("English", selectedLang);
		
		//Click on search
		driver.findElement(By.xpath("//button[@class='pure-button pure-button-primary-progressive']")).click();
		
		//Assert the heading 
		String heading = driver.findElement(By.id("firstHeading")).getText();
		heading = heading.toLowerCase();
		searchText = searchText.toLowerCase();
		Assert.assertEquals(searchText,heading);
		
		//verify if available in other languages
		WebElement langListElement = driver.findElement(By.xpath("//nav[@id='p-lang']//ul[@class='vector-menu-content-list']"));
		List<WebElement> langList =  langListElement.findElements(By.xpath("./child::*"));
		
		List<String> languages = new ArrayList<String>();
	
		 for (WebElement e  : langList) {
			 languages.add(e.getText());
	        }
		 
		 Assert.assertTrue(languages.contains("Deutsch"));
		 Assert.assertTrue(languages.contains("Italiano"));
		 
		 //Navigate to different language page 
		 driver.findElement(By.xpath("//a[contains(text(),'Deutsch')]")).click();
		 
		 //Assert that English language is present in the new language page 
		 WebElement engElement = driver.findElement(By.xpath("//li[@class='interlanguage-link interwiki-en']//a[@class='interlanguage-link-target'][contains(text(),'English')]"));
		 Assert.assertTrue(engElement.getText().equalsIgnoreCase("English"));
	}
	
	@After
	public void teardown() {
		driver.quit();
	}

}
