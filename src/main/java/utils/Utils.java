package utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class Utils {
	
    public static void takeScreenshot(WebDriver driver) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss").format(new Date());
        String dest = System.getProperty("user.dir") + "/screenshots/screenshot_" + timestamp + ".png";
        try {
            FileUtils.copyFile(source, new File(dest));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String getToday(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }
    
    public static String intToString(int number) {
        return Integer.toString(number);
    }

    public static int stringToInt(String str) {
        return Integer.parseInt(str);
    }
    
    public static void addCookie(WebDriver driver, Cookie cookie) {
        driver.manage().addCookie(cookie);
    }

    public static void deleteCookie(WebDriver driver, String name) {
        driver.manage().deleteCookieNamed(name);
    }

    public static Set<Cookie> getAllCookies(WebDriver driver) {
        return driver.manage().getCookies();
    }
    
    public static void uploadFile(WebElement element, String filePath) {
        element.sendKeys(filePath);
    }
}

