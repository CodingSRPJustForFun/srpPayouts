package com.srpPayouts.webpages;

import com.srpPayouts.browser.PageActionCode;
import com.srpPayouts.domain.FitObject;
import com.srpPayouts.domain.ModuleObject;
import com.srpPayouts.domain.TBLDS_DiscordObject;
import com.srpPayouts.ui.MsgBox;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;

public class JaniceResultsPage {
    private WebDriver driver;

    PageActionCode pageActionCode = new PageActionCode();

    @FindBy(how = How.XPATH, using = "//textarea[@class='ant-input']") private WebElement textArea;
    @FindBy(how = How.XPATH, using = "//button[@class='ant-btn ant-btn-default']") private WebElement submitButton;
    @FindBy(how = How.XPATH, using = "//tbody[@class='ant-table-tbody']") private WebElement table;

    public JaniceResultsPage(WebDriver driver, String janiceURL) {
        this.driver=driver;
        if(janiceURL.length() > 0)
            driver.get(janiceURL);
        PageFactory.initElements(driver, this);
    }

    public void waitPageLoaded() throws Throwable {
        //pageActionCode.waitPageTitleHasLoaded (driver, "Janice");
        pageActionCode.waitElementIsClickable(driver,table);
        try{
            Thread.sleep(5000);
        } catch(Exception ex){}
        System.out.println(driver.getCurrentUrl());
    }

    public void assignHullPrices(ArrayList<FitObject> allFitObjects) {
        //ArrayList<ModuleObject> allHullsFromJanice = new ArrayList<>();

        Document resultsPage = Jsoup.parse(java.util.Objects.requireNonNull(driver.getPageSource()));
        Element tableBody = resultsPage.select("tbody[class='ant-table-tbody']").first();
        Elements allRows = java.util.Objects.requireNonNull(tableBody).select("tr");
        for(Element row : allRows){
            String thisHullName = row.select("td").get(1).text();
            long thisHullPrice = (long) (Double.parseDouble(row.select("td").get(4).select("span").get(4).text().replace(" ISK", "").replace(",", "")));
            if (thisHullPrice < 1) {
                java.awt.Toolkit.getDefaultToolkit().beep();
                java.awt.Toolkit.getDefaultToolkit().beep();
                thisHullPrice = MsgBox.askValueInt(thisHullName + " is currently zero.", thisHullName);
            }
            for (FitObject fitObject : allFitObjects) {
                if (fitObject.getShipType().equals(thisHullName)) {
                    fitObject.setHullPrice(thisHullPrice);
                }
            }
        }
    }


    public void assignCapitalHullPrices(ArrayList<FitObject> allFitObjects, ArrayList<TBLDS_DiscordObject> allCapitalHullPrices){
        for(FitObject fitObject : allFitObjects){
            String hullName = fitObject.getShipType();
            for(TBLDS_DiscordObject tblds_discordObject : allCapitalHullPrices){
                if(tblds_discordObject.getHullName().equals(hullName)){
                    fitObject.setHullPrice(tblds_discordObject.getHullPrice());
                    break;
                }
            }
        }
    }


    public ArrayList<FitObject> assignModulePrices(ArrayList<FitObject> allFitObjects) {
        Document resultsPage = Jsoup.parse(java.util.Objects.requireNonNull(driver.getPageSource()));
        Element tableBody = resultsPage.select("tbody[class='ant-table-tbody']").first();
        Elements allRows = java.util.Objects.requireNonNull(java.util.Objects.requireNonNull(tableBody)).select("tr");
        for(Element row : allRows){
            String thisModuleName = row.select("td").get(1).text();
            long thisModulePrice = (long)(Double.parseDouble(row.select("td").get(4).select("span").get(4).text().replace(" ISK","").replace(",","")));
            for(FitObject fitObject : allFitObjects){
                ArrayList<ModuleObject> allModuleObjects = fitObject.getModules();
                if(allModuleObjects != null) {
                    for (ModuleObject moduleObject : allModuleObjects) {
                        if(moduleObject.getModuleName().equals("Chemal Tech Repair Booster III") ||
                                moduleObject.getModuleName().equals("Civilian Gatling Autocannon") ||
                                moduleObject.getModuleName().equals("Tetrimon Anti-Drain Booster IV") ||
                                moduleObject.getModuleName().equals("Tetrimon Capacitor Booster IV")) {
                            moduleObject.setUnitPrice(1);
                            moduleObject.setTotalPrice(1);
                        }

                        if (moduleObject.getModuleName().equals(thisModuleName)) {
                            moduleObject.setUnitPrice(thisModulePrice);
                            moduleObject.setTotalPrice(moduleObject.getUnitPrice() * moduleObject.getQuantity());

                            if(thisModuleName.equals("Ultratidal Entropic Disintegrator I") && fitObject.getShipType().equals("Zirnitra")){
                                moduleObject.setUnitPrice(1);
                                moduleObject.setTotalPrice(1);
                            }
                        }
                    }
                }
                fitObject.setModules(allModuleObjects);
            }
        }
        return allFitObjects;
    }
}

