package ss.annalaks.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import ss.annalaks.test.CommonTestMethods;

public class SearchPage {
	WebDriver driver;
	CommonTestMethods commonMethods;
	
	public SearchPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		commonMethods = new CommonTestMethods(driver);
	}
	
	public void searchForListings(String searchTerm) throws InterruptedException {
		driver.findElement(By.id("ptxt")).sendKeys(searchTerm);
		Thread.sleep(1000);
		driver.findElement(By.id("cmp_1")).click();
		driver.findElement(By.id("sbtn")).click();
	}
}
