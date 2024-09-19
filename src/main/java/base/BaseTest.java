package base;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;


public class BaseTest {
    protected WebDriver driver;
    private int defaulTimeoutUntilClickable = Config.getIntProperty("defaultTimeoutSecondsUntilClickable");
    private int defaulTimeoutUntilVisible = Config.getIntProperty("defaultTimeoutSecondsUntilVisible");
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeSuite
    public void setUpReport() {
    	System.setProperty("allure.results.directory", "target/allure-results");
    }
 
    @BeforeMethod
	public void setUp(ITestResult result) {
        String browser = Config.getStringProperty("browser");
        String url = Config.getStringProperty("url");
        startAndMaximizeBrowser(browser);
        goToURL(url);
    }
    
    public void startAndMaximizeBrowser(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Browser \"" + browser + "\" is not supported.");
        }
        driver.manage().window().maximize();
    }
    
    public void goToURL (String url) {
    	driver.get(url);
    	Allure.step("Navigating to URL: " + url);
    }

    @AfterMethod
	public void tearDown() {
        if (driver != null) {
        	Utils.takeScreenshot(driver);
        	Allure.step("Taking screenshot before closing the browser");
            Allure.step("Closing the browser");
            driver.quit();
        }
    }
    
    
    /* Elements interactions */
    @Step("Waiting for element to be clickable: {locator}")
    public WebElement waitForElementClickable(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(defaulTimeoutUntilClickable))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    @Step("Waiting for element to be visible: {locator}")
    public WebElement waitForElementVisible(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(defaulTimeoutUntilVisible))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Step("Click on element: {locator}")
    public void click(By locator) {
        scrollToElement(locator);
        waitForElementClickable(locator).click();
        Allure.step("Clicked on element: " + locator);
    }

    @Step("Entering text '{text}' in element: {locator}")
    public void enterText(By locator, String text) {
        scrollToElement(locator);
        WebElement element = waitForElementVisible(locator);
        element.sendKeys(text);
        Allure.step("Entered text '" + text + "' in element: " + locator);
    }

    @Step("Clear and enter text '{text}' in element: {locator}")
    public void clearAndEnterText(By locator, String text) {
        scrollToElement(locator);
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
        Allure.step("Cleared and entered text '" + text + "' in element: " + locator);
    }

    @Step("Selecting checkbox: {locator}")
    public void selectCheckbox(By locator) {
        scrollToElement(locator);
        WebElement checkbox = waitForElementClickable(locator);
        if (!checkbox.isSelected()) {
            checkbox.click();
            Allure.step("Checkbox selected: " + locator);
        } else {
            Allure.step("Checkbox already selected: " + locator);
        }
    }

    @Step("Select dropdown by text '{text}' on element: {locator}")
    public void selectDropdownByText(By locator, String text) {
        scrollToElement(locator);
        WebElement element = waitForElementVisible(locator);
        Select dropdown = new Select(element);
        dropdown.selectByVisibleText(text);
        Allure.step("Selected dropdown option by text '" + text + "' on element: " + locator);
    }

    @Step("Select dropdown by value '{value}' on element: {locator}")
    public void selectDropdownByValue(By locator, String value) {
        scrollToElement(locator);
        WebElement element = waitForElementVisible(locator);
        Select dropdown = new Select(element);
        dropdown.selectByValue(value);
        Allure.step("Selected dropdown option by value '" + value + "' on element: " + locator);
    }

    @Step("Scroll to element: {locator}")
    public void scrollToElement(By locator) {
        WebElement element = waitForElementVisible(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Allure.step("Scrolled to element: " + locator);
    }

    @Step("Check if element located by '{locator}' is present")
    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            Allure.step("Element is present: " + locator);
            return true;
        } catch (NoSuchElementException e) {
            Allure.step("Element is not present: " + locator);
            return false;
        }
    }

    @Step("Check if element located by '{locator}' is visible")
    public boolean isElementVisible(By locator) {
        try {
            boolean isVisible = driver.findElement(locator).isDisplayed();
            Allure.step("Element is visible: " + locator);
            return isVisible;
        } catch (NoSuchElementException e) {
            Allure.step("Element is not visible: " + locator);
            return false;
        }
    }
    
    @Step("Check if element located by '{locator}' is displayed")
    public boolean isElementDisplayed(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Switch to alert")
    public Alert switchToAlert() {
        Alert alert = driver.switchTo().alert();
        Allure.step("Switched to alert");
        return alert;
    }

    @Step("Hover over element located by '{locator}'")
    public void hoverOverElement(By locator) {
        WebElement element = waitForElementVisible(locator);
        new Actions(driver).moveToElement(element).perform();
        Allure.step("Hovered over element: " + locator);
    }

    @Step("Validate page title. Expected: '{expectedTitle}'")
    public boolean validatePageTitle(String expectedTitle) {
        boolean isValid = driver.getTitle().equals(expectedTitle);
        Allure.step("Page title validation result: " + isValid + " (expected: " + expectedTitle + ")");
        return isValid;
    }

    @Step("Validate page URL. Expected: '{expectedUrl}'")
    public boolean validatePageUrl(String expectedUrl) {
        boolean isValid = driver.getCurrentUrl().equals(expectedUrl);
        Allure.step("Page URL validation result: " + isValid + " (expected: " + expectedUrl + ")");
        return isValid;
    }
    
    public void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
