package ss.annalaks.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import ss.annalaks.test.CommonTestMethods;

public class SingleListingPage {
	WebDriver driver;
	CommonTestMethods commonMethods;
	
	public SingleListingPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		commonMethods = new CommonTestMethods(driver);
	}
	
	public void addListingToMemo() {
		commonMethods.ScrollToPageEnd();
		driver.findElement(By.id("a_fav")).click();
	}
}
