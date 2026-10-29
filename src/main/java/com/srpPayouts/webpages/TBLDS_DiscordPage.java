package com.srpPayouts.webpages;

import com.srpPayouts.browser.PageActionCode;
import com.srpPayouts.domain.TBLDS_DiscordObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;

public class TBLDS_DiscordPage {
    private WebDriver driver;

    PageActionCode pageActionCode = new PageActionCode();

    @FindBy(how = How.XPATH, using = "//code[contains(text(),'Navy Issue Dreads')]") private WebElement allPricesMessage;
    @FindBy(how = How.XPATH, using = "//ol[@data-list-id='chat-messages']") private WebElement mainWindow;

    public TBLDS_DiscordPage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://discord.com/channels/1275408985171820585/1466309870365179945");
        PageFactory.initElements(driver, this);
    }

    public void waitPageLoaded() throws Throwable {
        pageActionCode.waitElementIsClickable(driver, mainWindow);
        driver.findElement(By.xpath("//ol[@data-list-id='chat-messages']")).click();
        driver.findElement(By.xpath("//ol[@data-list-id='chat-messages']")).sendKeys(Keys.CONTROL, Keys.HOME);
        driver.findElement(By.xpath("//ol[@data-list-id='chat-messages']")).click();
        driver.findElement(By.xpath("//ol[@data-list-id='chat-messages']")).sendKeys(Keys.CONTROL, Keys.HOME);
        driver.findElement(By.xpath("//ol[@data-list-id='chat-messages']")).click();
        driver.findElement(By.xpath("//ol[@data-list-id='chat-messages']")).sendKeys(Keys.CONTROL, Keys.HOME);
    }

    public void getCapitalPrices(ArrayList<TBLDS_DiscordObject> allCapitalHullPrices) {
        String allPricesText = allPricesMessage.getText();
        String[] allPrices = allPricesText.split("\n");

        for (int i = 0; i < allPrices.length; i++) {
            if (allPrices[i].contains("Navy Issue Dreads")) {
                String price = allPrices[i].replace("Navy Issue Dreads", "").replace("—", "").replace("ISK", "").trim();
                if (price.toUpperCase().contains("B")) {
                    price = price.toUpperCase().replace("B", "");
                    long hullprice = (long)(Double.parseDouble(price) * 1000000000.0);

                    TBLDS_DiscordObject tblds_discordObject = new TBLDS_DiscordObject();
                    tblds_discordObject.setHullName("Revelation Navy Issue");
                    tblds_discordObject.setHullPrice(hullprice);
                    allCapitalHullPrices.add(tblds_discordObject);

                    tblds_discordObject = new TBLDS_DiscordObject();
                    tblds_discordObject.setHullName("Phoenix Navy Issue");
                    tblds_discordObject.setHullPrice(hullprice);
                    allCapitalHullPrices.add(tblds_discordObject);

                    tblds_discordObject = new TBLDS_DiscordObject();
                    tblds_discordObject.setHullName("Moros Navy Issue");
                    tblds_discordObject.setHullPrice(hullprice);
                    allCapitalHullPrices.add(tblds_discordObject);
                }
            } else if (allPrices[i].contains("FAX")) {
                String price = allPrices[i].replace("FAX", "").replace("—", "").replace("ISK", "").replaceAll("\\u2069","").trim();
                if (price.toUpperCase().contains("B")) {
                    price = price.toUpperCase().replace("B", "");
                    long hullprice = (long)(Double.parseDouble(price) * 1000000000.0);

                    TBLDS_DiscordObject tblds_discordObject = new TBLDS_DiscordObject();
                    tblds_discordObject.setHullName("Apostle");
                    tblds_discordObject.setHullPrice(hullprice);
                    allCapitalHullPrices.add(tblds_discordObject);

                    tblds_discordObject = new TBLDS_DiscordObject();
                    tblds_discordObject.setHullName("Minokawa");
                    tblds_discordObject.setHullPrice(hullprice);
                    allCapitalHullPrices.add(tblds_discordObject);
                }
            } else if (allPrices[i].contains("Rorq")) {
                String price = allPrices[i].replace("Rorq", "").replace("—", "").replace("ISK", "").trim();
                if (price.toUpperCase().contains("B")) {
                    price = price.toUpperCase().replace("B", "");
                    long hullprice = (long)(Double.parseDouble(price) * 1000000000.0);

                    TBLDS_DiscordObject tblds_discordObject = new TBLDS_DiscordObject();
                    tblds_discordObject.setHullName("Rorqual");
                    tblds_discordObject.setHullPrice(hullprice);
                    allCapitalHullPrices.add(tblds_discordObject);
                }
            } else if (allPrices[i].contains("Super Carrier")) {
                String price = allPrices[i].replace("Super Carrier", "").replace("-", "").replace("ISK", "").trim();
                if (price.toUpperCase().contains("B")) {
                    price = price.toUpperCase().replace("B", "");
                    long hullprice = (long)(Double.parseDouble(price) * 1000000000.0);

                    TBLDS_DiscordObject tblds_discordObject = new TBLDS_DiscordObject();
                    tblds_discordObject.setHullName("Hel");
                    tblds_discordObject.setHullPrice(hullprice);
                    allCapitalHullPrices.add(tblds_discordObject);

                    tblds_discordObject = new TBLDS_DiscordObject();
                    tblds_discordObject.setHullName("Aeon");
                    tblds_discordObject.setHullPrice(hullprice);
                    allCapitalHullPrices.add(tblds_discordObject);

                    tblds_discordObject = new TBLDS_DiscordObject();
                    tblds_discordObject.setHullName("Wyvern");
                    tblds_discordObject.setHullPrice(hullprice);
                    allCapitalHullPrices.add(tblds_discordObject);

                    tblds_discordObject = new TBLDS_DiscordObject();
                    tblds_discordObject.setHullName("Nyx");
                    tblds_discordObject.setHullPrice(hullprice);
                    allCapitalHullPrices.add(tblds_discordObject);
                }
            } else if (allPrices[i].contains("Carrier")) {
                String price = allPrices[i].replace("Carrier", "").replace("—", "").replace("ISK", "").trim();
                if (price.toUpperCase().contains("B")) {
                    price = price.toUpperCase().replace("B", "");
                    long hullprice = (long)(Double.parseDouble(price) * 1000000000.0);

                    TBLDS_DiscordObject tblds_discordObject = new TBLDS_DiscordObject();
                    tblds_discordObject.setHullName("Archon");
                    tblds_discordObject.setHullPrice(hullprice);
                    allCapitalHullPrices.add(tblds_discordObject);

                    tblds_discordObject = new TBLDS_DiscordObject();
                    tblds_discordObject.setHullName("Chimera");
                    tblds_discordObject.setHullPrice(hullprice);
                    allCapitalHullPrices.add(tblds_discordObject);

                    tblds_discordObject = new TBLDS_DiscordObject();
                    tblds_discordObject.setHullName("Thanatos");
                    tblds_discordObject.setHullPrice(hullprice);
                    allCapitalHullPrices.add(tblds_discordObject);

                    tblds_discordObject = new TBLDS_DiscordObject();
                    tblds_discordObject.setHullName("Nidhoggur");
                    tblds_discordObject.setHullPrice(hullprice);
                    allCapitalHullPrices.add(tblds_discordObject);
                }
            } else if (allPrices[i].contains("T1 Dread")) {
                String price = allPrices[i].replace("T1 Dread", "").replace("—", "").replace("ISK", "").trim();
                if (price.toUpperCase().contains("B")) {
                    price = price.toUpperCase().replace("B", "");
                    long hullprice = (long)(Double.parseDouble(price) * 1000000000.0);

                    TBLDS_DiscordObject tblds_discordObject = new TBLDS_DiscordObject();
                    tblds_discordObject.setHullName("Phoenix");
                    tblds_discordObject.setHullPrice(hullprice);
                    allCapitalHullPrices.add(tblds_discordObject);
                }
            } else if (allPrices[i].contains("Zirnitra")) {
                String price = allPrices[i].replace("Zirnitra", "").replace("+gun", "").replace("—", "").replace("ISK", "").trim();
                if (price.toUpperCase().contains("B")) {
                    price = price.replace("B", "");
                    long hullprice = (long)(Double.parseDouble(price) * 1000000000.0);

                    TBLDS_DiscordObject tblds_discordObject = new TBLDS_DiscordObject();
                    tblds_discordObject.setHullName("Zirnitra");
                    tblds_discordObject.setHullPrice(hullprice);
                    allCapitalHullPrices.add(tblds_discordObject);
                }
            }
        }
    }
}

