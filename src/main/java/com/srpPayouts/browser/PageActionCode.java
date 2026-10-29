package com.srpPayouts.browser;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import java.time.Duration;

public class PageActionCode {

    public void clearText(WebElement objName) throws Throwable {
        objName.sendKeys(Keys.CONTROL + "a");
        objName.sendKeys(Keys.DELETE);
        Thread.sleep(500);
    }

    public void enterText(WebDriver driver, WebElement objName, String strText) throws Throwable {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(java.util.Objects.requireNonNull(Duration.ofSeconds(10)))
                .pollingEvery(java.util.Objects.requireNonNull(Duration.ofSeconds(2)))
                .ignoring(Exception.class);
        wait.until(ExpectedConditions.visibilityOf(java.util.Objects.requireNonNull(objName)));
        objName.sendKeys(strText);
        Thread.sleep(500);
    }

     public void clickObject(WebDriver driver, WebElement objName) throws Throwable {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(java.util.Objects.requireNonNull(Duration.ofSeconds(10)))
                .pollingEvery(java.util.Objects.requireNonNull(Duration.ofSeconds(5)))
                .ignoring(Exception.class);
        wait.until(ExpectedConditions.elementToBeClickable(java.util.Objects.requireNonNull(objName)));
        objName.click();
    }

    public boolean clickObjectNoError(WebDriver driver, WebElement objName) throws Throwable {
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(java.util.Objects.requireNonNull(Duration.ofSeconds(10)))
                    .pollingEvery(java.util.Objects.requireNonNull(Duration.ofMillis(200)))
                    .ignoring(Exception.class);
            wait.until(ExpectedConditions.elementToBeClickable(java.util.Objects.requireNonNull(objName)));
            objName.click();
            return true;
        }
        catch(Exception eX) {
            System.out.println("Failed to click object");
            return false;
        }

    }

    public void jsClickObject(WebDriver driver, WebElement objName) throws Throwable {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", objName);
    }

    public void verifyPageTitle(WebDriver driver, String strExpTitle) throws Throwable {
        String strActTitle = driver.getTitle();
        if(!java.util.Objects.equals(strActTitle, strExpTitle)) {
            throw new AssertionError("Page title does not match. Expected: " + strExpTitle + " Actual: " + strActTitle);
        }
    }

    public String getPageTitle(WebDriver driver) throws Throwable {
        return(driver.getTitle());
    }

    public void waitElementIsVisible(WebDriver driver, WebElement objName) throws Throwable {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(java.util.Objects.requireNonNull(Duration.ofSeconds(10)))
                .pollingEvery(java.util.Objects.requireNonNull(Duration.ofSeconds(2)))
                .ignoring(Exception.class);
        wait.until(ExpectedConditions.visibilityOf(java.util.Objects.requireNonNull(objName)));
    }

    public String getElementText(WebDriver driver, WebElement objName) throws Throwable {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(java.util.Objects.requireNonNull(Duration.ofSeconds(10)))
                .pollingEvery(java.util.Objects.requireNonNull(Duration.ofSeconds(2)))
                .ignoring(Exception.class);
        wait.until(ExpectedConditions.visibilityOf(java.util.Objects.requireNonNull(objName)));
        return (objName.getText());
    }

    public void waitElementContains(WebDriver driver, WebElement objName, String attribute, String expectedValue) throws Throwable {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(java.util.Objects.requireNonNull(Duration.ofSeconds(10)))
                .pollingEvery(java.util.Objects.requireNonNull(Duration.ofSeconds(2)))
                .ignoring(Exception.class);
        wait.until(ExpectedConditions.attributeContains(java.util.Objects.requireNonNull(objName), java.util.Objects.requireNonNull(attribute), java.util.Objects.requireNonNull(expectedValue)));
    }

    public void waitElementIsClickable(WebDriver driver, WebElement objName) throws Throwable {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(java.util.Objects.requireNonNull(Duration.ofSeconds(10)))
                .pollingEvery(java.util.Objects.requireNonNull(Duration.ofSeconds(2)))
                .ignoring(Exception.class);
        wait.until(ExpectedConditions.elementToBeClickable(java.util.Objects.requireNonNull(objName)));
    }

    public void waitPageTitleHasLoaded(WebDriver driver, String expectedPageTitle) throws Throwable {
        //System.out.println("Expected: " + expectedPageTitle + "\r\nActual:   " + driver.getTitle());
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(java.util.Objects.requireNonNull(Duration.ofSeconds(10)))
                .pollingEvery(java.util.Objects.requireNonNull(Duration.ofSeconds(2)))
                .ignoring(Exception.class);
        wait.until(ExpectedConditions.titleIs(java.util.Objects.requireNonNull(expectedPageTitle)));
    }

    public void waitPageTitleHasLoadedContains(WebDriver driver, String expectedPageTitle) throws Throwable {
        //System.out.println("Expected: " + expectedPageTitle + "\r\nActual:   " + driver.getTitle());
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(java.util.Objects.requireNonNull(Duration.ofSeconds(10)))
                .pollingEvery(java.util.Objects.requireNonNull(Duration.ofSeconds(2)))
                .ignoring(Exception.class);
        wait.until(ExpectedConditions.titleContains(java.util.Objects.requireNonNull(expectedPageTitle)));
    }

    public static String getElementTextNoError(WebElement objName) throws Throwable {
        try {
            return objName.getText();
        }
        catch(Exception eX) {
            return "";
        }
    }

    public void waitElementCount(WebDriver driver, By locator, int expectedCount) throws Throwable {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(java.util.Objects.requireNonNull(Duration.ofSeconds(10)))
                .pollingEvery(java.util.Objects.requireNonNull(Duration.ofSeconds(2)))
                .ignoring(Exception.class);
        wait.until(ExpectedConditions.numberOfElementsToBe(java.util.Objects.requireNonNull(locator), java.util.Objects.requireNonNull(expectedCount)));
        Thread.sleep(2000);
    }

    public void waitElementText(WebDriver driver, WebElement objName, String expectedText) throws Throwable {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(java.util.Objects.requireNonNull(Duration.ofSeconds(10)))
                .pollingEvery(java.util.Objects.requireNonNull(Duration.ofSeconds(1)))
                .ignoring(Exception.class);
        wait.until(ExpectedConditions.textToBePresentInElement(java.util.Objects.requireNonNull(objName), java.util.Objects.requireNonNull(expectedText)));
        Thread.sleep(2000);
    }

    public void clickSubMenu(WebDriver driver, WebElement topMenu, WebElement subMenu) throws Throwable {
        Actions builder = new Actions(java.util.Objects.requireNonNull(driver));
        builder.moveToElement(java.util.Objects.requireNonNull(topMenu)).build().perform();
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(java.util.Objects.requireNonNull(Duration.ofSeconds(5)))
                .pollingEvery(java.util.Objects.requireNonNull(Duration.ofMillis(200)))
                .ignoring(Exception.class);
        wait.until(ExpectedConditions.elementToBeClickable(java.util.Objects.requireNonNull(subMenu)));
        subMenu.click();
    }
}