package com.srpPayouts.webpages;

import com.srpPayouts.browser.PageActionCode;
import com.srpPayouts.domain.FitObject;
import com.srpPayouts.domain.ModuleObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;

public class FittingsHomePage {
    private WebDriver driver;
    private static String PAGE_URL="https://zero.the-initiative.rocks/fittings/fit/all/";

    PageActionCode pageActionCode = new PageActionCode();

    @FindBy(how = How.XPATH, using = "//a[text()='View All Fits']") private WebElement allFitsLink;
    @FindBy(how = How.XPATH, using = "//select[@name='fitTable_length']") private WebElement drpEntries;

    public FittingsHomePage(WebDriver driver) {
        this.driver=driver;
        driver.get(java.util.Objects.requireNonNull(PAGE_URL));                         //THIS LINE FAILS
        PageFactory.initElements(driver, this);
    }

    public void waitPageLoaded() throws Throwable {
        //System.out.println("'" + driver.getTitle() + "'");
        //pageActionCode.waitPageTitleHasLoaded (driver, "View All Fits - INIT. Auth"); //new page
        pageActionCode.waitPageTitleHasLoaded (driver, "View All Fittings - INIT. Auth"); //old page
        //pageActionCode.waitElementIsClickable(driver,allFitsLink); //new page
    }

    public void set100Entries() {
        Select select = new Select(java.util.Objects.requireNonNull(drpEntries));
        select.selectByVisibleText("100");
    }

    public ArrayList<FitObject> addExtraShips(ArrayList<FitObject> allFitObjects) {
        FitObject fitObject = null;

        //Add an FC Monitor
        fitObject = new FitObject();
        fitObject.setDoctrineName("FC's Monitor");
        fitObject.setShipType("Monitor");
        fitObject.setFittingURL("");
        fitObject.setShipClass("FC Ship");
        fitObject.setLastUpdatedDays("");
        fitObject.setLastUpdated("");
        fitObject.setPayoutRate(FittingsPage.lookupPayRate(fitObject.getShipClass()));
        fitObject.setPrintEntry(true);
        String[] modulesToAdd = new String[] {"Sisters Expanded Probe Launcher", "Target Painter II", "50MN Microwarpdrive II", "Medium Micro Jump Drive", "Sisters Combat Scanner Probe", "Sisters Core Scanner Probe"};
        int[] numberToAdd = new int[]{1, 1, 1, 1, 16, 16};

        ArrayList<ModuleObject> newModules = new ArrayList<ModuleObject>();
        for(int i=0; i<modulesToAdd.length; i++) {
            ModuleObject moduleObject = new ModuleObject();
            moduleObject.setModuleName(modulesToAdd[i]);
            moduleObject.setQuantity(numberToAdd[i]);
            newModules.add(moduleObject);
        }
        fitObject.setModules(newModules);
        allFitObjects.add(fitObject);
/*
        //Add a Bonga Obss's Drake Navy Issue
        fitObject = new FitObject();
        fitObject.setDoctrineName("Obss's Drake Navy Issue");
        fitObject.setShipType("Drake Navy Issue");
        fitObject.setFittingURL("");
        fitObject.setShipClass("DPS");
        fitObject.setLastUpdatedDays("");
        fitObject.setLastUpdated("");
        fitObject.setPayoutRate(FittingsPage.lookupPayRate(fitObject.getShipClass()));
        fitObject.setPrintEntry(true);
        modulesToAdd = new String[] {"Ballistic Control System II","Damage Control II","Missile Guidance Enhancer II","50MN Cold-Gas Enduring Microwarpdrive","EM Shield Hardener II","Large Shield Extender II","Multispectrum Shield Hardener II","Small Capacitor Booster II","Navy Cap Booster 400","Small Gremlin Compact Energy Neutralizer","Heavy Missile Launcher II","Caldari Navy Scourge Heavy Missile","Medium Core Defense Field Extender I","Medium Hydraulic Bay Thrusters I","Medium Thermal Shield Reinforcer II","Hornet EC-300"};
        numberToAdd = new int[]{2,1,1,1,1,2,1,1,20,1,7,1000,1,1,1,5};

        newModules = new ArrayList<ModuleObject>();
        for(int i=0; i<modulesToAdd.length; i++) {
            ModuleObject moduleObject = new ModuleObject();
            moduleObject.setModuleName(modulesToAdd[i]);
            moduleObject.setQuantity(numberToAdd[i]);
            newModules.add(moduleObject);
        }
        fitObject.setModules(newModules);
        allFitObjects.add(fitObject);
*/

/*
        //Add a Bonga Party+ DNI
        fitObject = new FitObject();
        fitObject.setDoctrineName("Party+ DNI");
        fitObject.setShipType("Drake Navy Issue");
        fitObject.setFittingURL("");
        fitObject.setShipClass("DPS");
        fitObject.setLastUpdatedDays("");
        fitObject.setLastUpdated("");
        fitObject.setPayoutRate(FittingsPage.lookupPayRate(fitObject.getShipClass()));
        fitObject.setPrintEntry(true);
        modulesToAdd = new String[] {"Damage Control II","Ballistic Control System II","Missile Guidance Enhancer II","Large Shield Extender II","Multispectrum Shield Hardener II","Small Capacitor Booster II","50MN Cold-Gas Enduring Microwarpdrive","EM Shield Hardener II","Heavy Missile Launcher II","Small Gremlin Compact Energy Neutralizer","Medium Hydraulic Bay Thrusters I","Medium Thermal Shield Reinforcer II","Medium Core Defense Field Extender I","Hornet EC-300","Warrior II","Mjolnir Fury Heavy Missile","Caldari Navy Nova Heavy Missile","Caldari Navy Scourge Heavy Missile","Caldari Navy Mjolnir Heavy Missile","Navy Cap Booster 400","Scourge Fury Heavy Missile","Nanite Repair Paste","Nova Fury Heavy Missile","Inferno Fury Heavy Missile","Cladistic-5 'Krai Veles' Filament","Caldari Navy Inferno Heavy Missile"};
        numberToAdd = new int[]{1,2,1,2,1,1,1,1,7,1,1,1,1,5,10,1000,1000,1000,500,10,500,60,1000,500,2,1000};

        newModules = new ArrayList<ModuleObject>();
        for(int i=0; i<modulesToAdd.length; i++) {
            ModuleObject moduleObject = new ModuleObject();
            moduleObject.setModuleName(modulesToAdd[i]);
            moduleObject.setQuantity(numberToAdd[i]);
            newModules.add(moduleObject);
        }
        fitObject.setModules(newModules);
        allFitObjects.add(fitObject);
*/

/*
        //Add an ESS Moa
        fitObject = new FitObject();
        fitObject.setDoctrineName("ESS Moa");
        fitObject.setShipType("Moa");
        fitObject.setFittingURL("");
        fitObject.setShipClass("DPS");
        fitObject.setLastUpdatedDays("");
        fitObject.setLastUpdated("");
        fitObject.setPayoutRate(FittingsPage.lookupPayRate(fitObject.getShipClass()));
        fitObject.setPrintEntry(true);
        modulesToAdd = new String[] {"Damage Control II", "Magnetic Field Stabilizer II", "Large Shield Extender II", "Multispectrum Shield Hardener II", "X5 Enduring Stasis Webifier", "10MN Afterburner II", "Heavy Neutron Blaster II", "Medium EM Shield Reinforcer I", "Medium Core Defense Field Extender I"};
        numberToAdd = new int[]{1, 3, 1, 2, 1, 1, 5, 1, 2};

        newModules = new ArrayList<ModuleObject>();
        for(int i=0; i<modulesToAdd.length; i++) {
            ModuleObject moduleObject = new ModuleObject();
            moduleObject.setModuleName(modulesToAdd[i]);
            moduleObject.setQuantity(numberToAdd[i]);
            newModules.add(moduleObject);
        }
        fitObject.setModules(newModules);
        allFitObjects.add(fitObject);
*/

        return allFitObjects;
    }
}
