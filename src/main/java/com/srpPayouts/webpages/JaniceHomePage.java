package com.srpPayouts.webpages;

import com.srpPayouts.browser.PageActionCode;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class JaniceHomePage {
    private WebDriver driver;
    private static String PAGE_URL="https://janice.e-351.com/";

    PageActionCode pageActionCode = new PageActionCode();

    @FindBy(how = How.XPATH, using = "//textarea[@class='ant-input']") private WebElement textArea;
    @FindBy(how = How.XPATH, using = "//button[@class='ant-btn ant-btn-default']") private WebElement submitButton;

    public JaniceHomePage(WebDriver driver) {
        this.driver=driver;
        driver.get(java.util.Objects.requireNonNull(PAGE_URL));
        PageFactory.initElements(driver, this);
    }

    public JaniceHomePage(WebDriver driver, String janiceShipUrl) {
        this.driver=driver;
        driver.get(java.util.Objects.requireNonNull(janiceShipUrl));
        PageFactory.initElements(driver, this);
    }

    public void waitPageLoaded() throws Throwable {
        //pageActionCode.waitPageTitleHasLoaded (driver, "Janice");
        pageActionCode.waitElementIsClickable(driver,submitButton);
    }


    public void pasteList(String stringToPaste) throws Throwable {
        StringSelection selection = new StringSelection(stringToPaste);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        textArea.click();
        pageActionCode.enterText(driver, textArea, Keys.CONTROL + "v");
    }

    public void clickSubmit() {
        submitButton.click();
    }
}

