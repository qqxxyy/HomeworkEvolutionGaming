package ss.annalaks.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import ss.annalaks.test.CommonTestMethods;

public class ListingsPage {
	WebDriver driver;
	CommonTestMethods commonMethods;
	
	public ListingsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		commonMethods = new CommonTestMethods(driver);
	}
	
	public String getClassifiedText() {
		String text = driver.findElement(By.className("msg2")).findElement(By.tagName("a")).getText();
		return text;
	}
	
	public List<String> getClassifiedTexts(int listingCount) {
		List<WebElement> classifieds = driver.findElements(By.className("msg2"));
		List<String> classifiedTexts = new ArrayList<String>();
		for(int i=0; i<listingCount; i++) {
			classifiedTexts.add(classifieds.get(i).findElement(By.tagName("a")).getText());
		}
		return classifiedTexts;
	}
	
	public void clickOnFirstListing() {
		driver.findElement(By.className("msg2")).click();
	}
	
	public void selectSeveralListings(int selectedElementCount) {
		List<WebElement> listingCheckboxContainers =  driver.findElements(By.className("pp0"));
		for(int i=0; i<selectedElementCount; i++) {
			listingCheckboxContainers.get(i).findElement(By.tagName("input")).click();
		}
	}
	
	public void addToMemoFromList() {
		commonMethods.ScrollToPageEnd();
		driver.findElement(By.id("a_fav_sel")).click();
	}
}
