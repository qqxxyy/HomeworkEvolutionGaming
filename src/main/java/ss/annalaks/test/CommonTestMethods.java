package ss.annalaks.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

public class CommonTestMethods{
	WebDriver driver;
	public CommonTestMethods(WebDriver driver) {
		this.driver = driver;
	}
	
	public String getChromeDriverPath() {
		return Paths.get("src","main","resources","chromedriver.exe").toString();
	}
	public void ScrollToPageEnd() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	public void ScrollToPageStart() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0, 0)");
	}
	public void AssertAndDismissPopup(String expectedText) {
		assertTrue(driver.findElement(By.id("alert_dv")).isDisplayed());
		assertEquals(expectedText, driver.findElement(By.id("alert_msg")).getText());
		driver.findElement(By.id("alert_ok")).click();
	}
	public void AssertMemoCount(int expectedCount) {
		assertEquals(" (" + expectedCount + ")", driver.findElement(By.id("mnu_fav_id")).getText());
	}
	public void GoToMemoSection() {
		ScrollToPageStart();
		driver.findElement(By.linkText("Memo")).click();
		//driver.findElement(By.linkText("Favorites")).click();
	}
	public void compareListingTexts(List<String> expectedText, List<String> actualText) {
		Collections.sort(expectedText);
		Collections.sort(actualText);
		
		for(int i = 0; i<expectedText.size(); i++) {
			assertTrue(expectedText.get(i).contains(actualText.get(i)), "Listings are not the same");
		}
	}
	public void clickOnLink(String linkText) {
		//This was necessary to avoid StaleElementReferenceException
		try {
			driver.findElement(By.linkText(linkText)).click();
		} 
		catch(StaleElementReferenceException e) {
			driver.findElement(By.linkText(linkText)).click();
		}
	}
}