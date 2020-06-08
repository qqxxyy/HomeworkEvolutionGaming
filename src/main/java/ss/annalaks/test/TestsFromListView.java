package ss.annalaks.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import ss.annalaks.page.ListingsPage;
import ss.annalaks.page.SearchPage;
import ss.annalaks.page.SingleListingPage;

@TestInstance(value=Lifecycle.PER_CLASS)
public class TestsFromListView {
	private WebDriver driver;
	private ListingsPage objListingsPage;
	private SingleListingPage objSingleListingPage;
	private SearchPage objSearchPage;
	private CommonTestMethods commonMethods;
	

	@BeforeEach
	public void setup() throws URISyntaxException {
		System.setProperty("webdriver.chrome.driver", Paths.get("src","main","resources","chromedriver.exe").toString());
		
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://ss.com/en");
		driver.manage().window().maximize();
		
		commonMethods = new CommonTestMethods(driver);
	}

	@AfterEach
	public void teardown() {
		driver.quit();
	}

	@Test
	public void testsFromListView_OpenOneListingAddToMemo() throws InterruptedException {
		commonMethods.clickOnLink("Animal husbandry");
		commonMethods.clickOnLink("Agricultural animals");
		commonMethods.clickOnLink("Goats");
		
		objListingsPage = new ListingsPage(driver);
		String classifiedText = objListingsPage.getClassifiedText();
		objListingsPage.clickOnFirstListing();
		objSingleListingPage = new SingleListingPage(driver);
		objSingleListingPage.addListingToMemo();
		commonMethods.AssertAndDismissPopup("Advertisement added to favorites.");
		//a goat has been favorited, asserting memo section
		commonMethods.AssertMemoCount(1);
		commonMethods.GoToMemoSection();
		assertEquals(1, driver.findElements(By.className("msg2")).size());
		assertEquals(classifiedText, objListingsPage.getClassifiedText());
	}
	
	@Test
	public void testsFromListView_AddMultipleListingsToMemo() throws InterruptedException {
		commonMethods.clickOnLink("Animal husbandry");
		commonMethods.clickOnLink("Agricultural animals");
		commonMethods.clickOnLink("Goats");
		//we are now looking at goats
		
		objListingsPage = new ListingsPage(driver);
		int numberOfListings = 3;
		List<String> classifiedTexts = objListingsPage.getClassifiedTexts(numberOfListings); 
		objListingsPage.selectSeveralListings(numberOfListings);
		objListingsPage.addToMemoFromList();
		commonMethods.AssertAndDismissPopup("Sludinâjumi ir pievienoti Memo.");
		//multiple goats have been favorited, asserting memo section
		commonMethods.AssertMemoCount(numberOfListings);
		commonMethods.GoToMemoSection();
		commonMethods.compareListingTexts(classifiedTexts, objListingsPage.getClassifiedTexts(numberOfListings));
	}
	
	@Test
	public void testsFromListView_AddSameListingTwice() throws InterruptedException {
		commonMethods.clickOnLink("Animal husbandry");
		commonMethods.clickOnLink("Agricultural animals");
		commonMethods.clickOnLink("Goats");
		//we are now looking at goats
		
		objListingsPage = new ListingsPage(driver);
		int numberOfListings = 1;
		objListingsPage.selectSeveralListings(numberOfListings);
		objListingsPage.addToMemoFromList();
		commonMethods.AssertAndDismissPopup("Sludinâjumi ir pievienoti Memo.");
		objListingsPage.addToMemoFromList();
		commonMethods.AssertAndDismissPopup("Sludinâjumi ir pievienoti Memo.");
		//same goats have been added twice, asserting memo section
		commonMethods.AssertMemoCount(numberOfListings);
		commonMethods.GoToMemoSection();
		assertEquals(numberOfListings, driver.findElements(By.className("msg2")).size());
	}
	
	@Test
	public void testsFromListView_SearchAndAddToMemo() throws InterruptedException {
		driver.findElement(By.linkText("Search")).click();
		objSearchPage = new SearchPage(driver);
		objSearchPage.searchForListings("bmw");
		
		objListingsPage = new ListingsPage(driver);
		int numberOfListings = 2;
		List<String> classifiedTexts = objListingsPage.getClassifiedTexts(numberOfListings); 
		objListingsPage.selectSeveralListings(numberOfListings);
		objListingsPage.addToMemoFromList();
		commonMethods.AssertAndDismissPopup("Sludinâjumi ir pievienoti Memo.");
		//multiple dresses have been favorited, asserting memo section
		commonMethods.AssertMemoCount(numberOfListings);
		commonMethods.GoToMemoSection();
		commonMethods.compareListingTexts(classifiedTexts, objListingsPage.getClassifiedTexts(numberOfListings));
	}
	
	@Test 
	public void testsFromListView_DeleteListingFromMemo(){
		commonMethods.clickOnLink("Animal husbandry");
		commonMethods.clickOnLink("Agricultural animals");
		commonMethods.clickOnLink("Goats");
		
		objListingsPage = new ListingsPage(driver);
		objListingsPage.clickOnFirstListing();
		objSingleListingPage = new SingleListingPage(driver);
		objSingleListingPage.addListingToMemo();
		commonMethods.AssertAndDismissPopup("Advertisement added to favorites.");
		
		commonMethods.GoToMemoSection();
		driver.findElement(By.linkText("Favorites")).click();
		objListingsPage.selectSeveralListings(1);
		driver.findElement(By.id("del_selected_a")).click();
		assertTrue(driver.findElements(By.className("msg2")).isEmpty());
	}

}
