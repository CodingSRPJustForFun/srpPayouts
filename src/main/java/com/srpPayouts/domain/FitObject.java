package com.srpPayouts.domain;

import com.srpPayouts.csv.CSVCode;
import com.srpPayouts.ui.MsgBox;
import java.util.ArrayList;
import java.util.Objects;

public class FitObject {

    private String doctrineName;
    private String shipType;
    private String description;
    private String fittingURL;
    private long hullPrice = 0;
    private long insurancePayout;
    private ArrayList<ModuleObject> modules;
    private long modulesTotalPrice;
    private long totalPayout110;
    private long totalPayout100;
    private long totalPayout75;
    private long totalPayout85;
    private long totalPayout50;
    private long totalPayout40;
    private String typeId;
    private Boolean printEntry;
    private String shipClass;
    private long payoutRate;
    private String lastUpdated;
    private String lastUpdatedDays;

    public String getDoctrineName() { return doctrineName; }
    public void setDoctrineName(String doctrineName) { this.doctrineName = doctrineName; }
    public String getShipType() { return shipType; }
    public void setShipType(String shipType) { this.shipType = shipType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getFittingURL() { return fittingURL; }
    public void setFittingURL(String fittingURL) { this.fittingURL = fittingURL; }
    public long getHullPrice() { return hullPrice; }
    public void setHullPrice(long hullPrice) { this.hullPrice = hullPrice; }
    public long getInsurancePayout() { return insurancePayout; }
    public void setInsurancePayout(long insurancePayout) { this.insurancePayout = insurancePayout; }
    public ArrayList<ModuleObject> getModules() { return modules; }
    public void setModules(ArrayList<ModuleObject> modules) { this.modules = modules; }
    public long getModulesTotalPrice() { return modulesTotalPrice; }
    public void setModulesTotalPrice(long modulesTotalPrice) { this.modulesTotalPrice = modulesTotalPrice; }
    public long getTotalPayout110() { return totalPayout110; }
    public void setTotalPayout110(long totalPayout110) { this.totalPayout110 = totalPayout110; }
    public long getTotalPayout100() { return totalPayout100; }
    public void setTotalPayout100(long totalPayout100) { this.totalPayout100 = totalPayout100; }
    public long getTotalPayout75() { return totalPayout75; }
    public void setTotalPayout75(long totalPayout75) { this.totalPayout75 = totalPayout75; }
    public long getTotalPayout85() { return totalPayout85; }
    public void setTotalPayout85(long totalPayout85) { this.totalPayout85 = totalPayout85; }
    public long getTotalPayout50() { return totalPayout50; }
    public void setTotalPayout50(long totalPayout50) { this.totalPayout50 = totalPayout50; }
    public long getTotalPayout40() { return totalPayout40; }
    public void setTotalPayout40(long totalPayout40) { this.totalPayout40 = totalPayout40; }
    public String getTypeId() { return typeId; }
    public void setTypeId(String typeId) { this.typeId = typeId; }
    public Boolean getPrintEntry() { return printEntry; }
    public void setPrintEntry(Boolean printEntry) { this.printEntry = printEntry; }
    public String getShipClass() { return shipClass; }
    public void setShipClass(String shipClass) { this.shipClass = shipClass; }
    public long getPayoutRate() { return payoutRate; }
    public void setPayoutRate(long payoutRate) { this.payoutRate = payoutRate; }
    public String getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }
    public String getLastUpdatedDays() { return lastUpdatedDays; }
    public void setLastUpdatedDays(String lastUpdatedDays) { this.lastUpdatedDays = lastUpdatedDays; }

    protected boolean canEqual(Object other) {
        return other instanceof FitObject;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FitObject fitObject) || !fitObject.canEqual(this)) {
            return false;
        }
        return hullPrice == fitObject.hullPrice
                && insurancePayout == fitObject.insurancePayout
                && modulesTotalPrice == fitObject.modulesTotalPrice
                && totalPayout110 == fitObject.totalPayout110
                && totalPayout100 == fitObject.totalPayout100
                && totalPayout75 == fitObject.totalPayout75
                && totalPayout85 == fitObject.totalPayout85
                && totalPayout50 == fitObject.totalPayout50
                && totalPayout40 == fitObject.totalPayout40
                && payoutRate == fitObject.payoutRate
                && Objects.equals(doctrineName, fitObject.doctrineName)
                && Objects.equals(shipType, fitObject.shipType)
                && Objects.equals(description, fitObject.description)
                && Objects.equals(fittingURL, fitObject.fittingURL)
                && Objects.equals(modules, fitObject.modules)
                && Objects.equals(typeId, fitObject.typeId)
                && Objects.equals(printEntry, fitObject.printEntry)
                && Objects.equals(shipClass, fitObject.shipClass)
                && Objects.equals(lastUpdated, fitObject.lastUpdated)
                && Objects.equals(lastUpdatedDays, fitObject.lastUpdatedDays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctrineName, shipType, description, fittingURL, hullPrice,
                insurancePayout, modules, modulesTotalPrice, totalPayout110,
                totalPayout100, totalPayout75, totalPayout85, totalPayout50,
                totalPayout40, typeId, printEntry, shipClass, payoutRate,
                lastUpdated, lastUpdatedDays);
    }

    @Override
    public String toString() {
        return "FitObject(doctrineName=" + doctrineName + ", shipType=" + shipType
                + ", description=" + description + ", fittingURL=" + fittingURL
                + ", hullPrice=" + hullPrice + ", insurancePayout=" + insurancePayout
                + ", modules=" + modules + ", modulesTotalPrice=" + modulesTotalPrice
                + ", totalPayout110=" + totalPayout110 + ", totalPayout100=" + totalPayout100
                + ", totalPayout75=" + totalPayout75 + ", totalPayout85=" + totalPayout85
                + ", totalPayout50=" + totalPayout50 + ", totalPayout40=" + totalPayout40
                + ", typeId=" + typeId + ", printEntry=" + printEntry + ", shipClass="
                + shipClass + ", payoutRate=" + payoutRate + ", lastUpdated=" + lastUpdated
                + ", lastUpdatedDays=" + lastUpdatedDays + ")";
    }

    public static ArrayList<FitObject> calculateTotalModuleCost(ArrayList<FitObject> allFitObjects){

        for(FitObject fitObject : allFitObjects){
            ArrayList<ModuleObject> allModules = fitObject.getModules();
            long allModuleCosts = 0;
            if(allModules != null) {
                for (ModuleObject modules : allModules) {
                    allModuleCosts += modules.getTotalPrice();
                }
            }
            fitObject.setModulesTotalPrice(allModuleCosts);
        }
        return allFitObjects;
    }


    public static ArrayList<FitObject> calculatePayouts(ArrayList<FitObject> allFitObjects){

        for(FitObject fitObject : allFitObjects) {
            if(!fitObject.getShipType().substring(0,3).equals("Any")){
            //if (fitObject.getShipType() != "Any Bomber" && fitObject.getShipType() != "Any Command Destroyer" && fitObject.getShipType() != "Any Interceptor" && fitObject.getShipType() != "Any Interdictor") {
                long totalPayout100 = (fitObject.getHullPrice() + fitObject.getModulesTotalPrice() - fitObject.getInsurancePayout());
                double payOutAdjust = totalPayout100 * (fitObject.getPayoutRate() / 100.0);
                totalPayout100 = (long) payOutAdjust;
                long totalPayout110 = (long) (totalPayout100 * 1.1);
                long totalPayout75 = (long) (totalPayout100 * 0.75);
                long totalPayout85 = (long) (totalPayout100 * 0.85);
                long totalPayout50 = (long) (totalPayout100 * 0.5);
                long totalPayout40 = (long) (totalPayout100 * 0.4);
                fitObject.setTotalPayout110(totalPayout110);
                fitObject.setTotalPayout100(totalPayout100);
                fitObject.setTotalPayout75(totalPayout75);
                fitObject.setTotalPayout85(totalPayout85);
                fitObject.setTotalPayout50(totalPayout50);
                fitObject.setTotalPayout40(totalPayout40);
            }
        }
        return allFitObjects;
    }

    public static ArrayList<FitObject> addFixedPriceItems(ArrayList<FitObject> fixedFitObjects){

        FitObject fitObject = null;

        fitObject = new FitObject();
        fitObject.setShipType("Amulet Pod");
        fitObject.setDoctrineName("");
        fitObject.setShipClass("Pod");
        fitObject.setPayoutRate(100);

        String[] modulesToAdd = {"Mid-grade Amulet Alpha","Mid-grade Amulet Beta","Mid-grade Amulet Gamma","Mid-grade Amulet Delta","Mid-grade Amulet Epsilon","Mid-grade Amulet Omega","Inherent Implants 'Noble' Hull Upgrades HG-1003"};
        ArrayList<ModuleObject> newModules = new ArrayList<ModuleObject>();
        for(int i=0; i<modulesToAdd.length; i++) {
            ModuleObject moduleObject = new ModuleObject();
            moduleObject.setModuleName(modulesToAdd[i]);
            moduleObject.setQuantity(1);
            newModules.add(moduleObject);
        }
        fitObject.setModules(newModules);
        fixedFitObjects.add(fitObject);

        fitObject = new FitObject();
        fitObject.setShipType("Any Bomber");
        fitObject.setDoctrineName("");
        fitObject.setShipClass("");
        fitObject.setPayoutRate(100);
        double adjustPayout = 50000000 * fitObject.getPayoutRate() / 100.0;
        double adjustPayout110 = adjustPayout * 1.1;
        fitObject.setTotalPayout100((long)adjustPayout);
        fitObject.setTotalPayout110((long)adjustPayout110);
        fixedFitObjects.add(fitObject);

        fitObject = new FitObject();
        fitObject.setShipType("Any Command Destroyer");
        fitObject.setDoctrineName("Pontifex - Bifrost - Stork");
        fitObject.setShipClass("");
        fitObject.setPayoutRate(100);
        adjustPayout = 55000000 * fitObject.getPayoutRate() / 100.0;
        adjustPayout110 = adjustPayout * 1.1;
        fitObject.setTotalPayout100((long)adjustPayout);
        fitObject.setTotalPayout110((long)adjustPayout110);
        fixedFitObjects.add(fitObject);

        fitObject = new FitObject();
        fitObject.setShipType("Any Interceptor");
        fitObject.setDoctrineName("");
        fitObject.setShipClass("");
        fitObject.setPayoutRate(100);
        adjustPayout = 20000000 * fitObject.getPayoutRate() / 100.0;
        adjustPayout110 = adjustPayout * 1.1;
        fitObject.setTotalPayout100((long)adjustPayout);
        fitObject.setTotalPayout110((long)adjustPayout110);
        fixedFitObjects.add(fitObject);

        fitObject = new FitObject();
        fitObject.setShipType("Any Interdictor");
        fitObject.setDoctrineName("");
        fitObject.setShipClass("");
        fitObject.setPayoutRate(100);
        adjustPayout = 45000000 * fitObject.getPayoutRate() / 100.0;
        adjustPayout110 = adjustPayout * 1.1;
        fitObject.setTotalPayout100((long)adjustPayout);
        fitObject.setTotalPayout110((long)adjustPayout110);
        fixedFitObjects.add(fitObject);

/*
        fitObject = new FitObject();
        fitObject.setShipType("Any Recon Cyno");
        fitObject.setDoctrineName("Rapier - Arazu - Pilgrim - Falcon");
        fitObject.setShipClass("");
        fitObject.setPayoutRate(100);
        adjustPayout = 170000000 * fitObject.getPayoutRate() / 100.0;
        adjustPayout110 = adjustPayout * 1.1;
        fitObject.setTotalPayout100((long)adjustPayout);
        fitObject.setTotalPayout110((long)adjustPayout110);
        fixedFitObjects.add(fitObject);

        fitObject = new FitObject();
        fitObject.setShipType("Any HIC Cyno");
        fitObject.setDoctrineName("Devoter - Onyx - Phobos - Broadsword");
        fitObject.setShipClass("");
        fitObject.setPayoutRate(100);
        adjustPayout = 200000000 * fitObject.getPayoutRate() / 100.0;
        adjustPayout110 = adjustPayout * 1.1;
        fitObject.setTotalPayout100((long)adjustPayout);
        fitObject.setTotalPayout110((long)adjustPayout110);
        fixedFitObjects.add(fitObject);
*/

        fitObject = new FitObject();
        fitObject.setShipType("Booster Pod");
        fitObject.setDoctrineName("Armored Command Mindlink");
        fitObject.setShipClass("Pod");
        fitObject.setPayoutRate(100);
        modulesToAdd = new String[] {"Armored Command Mindlink"};
        newModules = new ArrayList<ModuleObject>();
        for(int i=0; i<modulesToAdd.length; i++) {
            ModuleObject moduleObject = new ModuleObject();
            moduleObject.setModuleName(modulesToAdd[i]);
            moduleObject.setQuantity(1);
            newModules.add(moduleObject);
        }
        fitObject.setModules(newModules);
        fixedFitObjects.add(fitObject);

        fitObject = new FitObject();
        fitObject.setShipType("Booster Pod");
        fitObject.setDoctrineName("Information Command Mindlink");
        fitObject.setShipClass("Pod");
        fitObject.setPayoutRate(100);
        modulesToAdd = new String[] {"Information Command Mindlink"};
        newModules = new ArrayList<ModuleObject>();
        for(int i=0; i<modulesToAdd.length; i++) {
            ModuleObject moduleObject = new ModuleObject();
            moduleObject.setModuleName(modulesToAdd[i]);
            moduleObject.setQuantity(1);
            newModules.add(moduleObject);
        }
        fitObject.setModules(newModules);
        fixedFitObjects.add(fitObject);

        fitObject = new FitObject();
        fitObject.setShipType("Booster Pod");
        fitObject.setDoctrineName("Shield Command Mindlink");
        fitObject.setShipClass("Pod");
        fitObject.setPayoutRate(100);
        modulesToAdd = new String[] {"Shield Command Mindlink"};
        newModules = new ArrayList<ModuleObject>();
        for(int i=0; i<modulesToAdd.length; i++) {
            ModuleObject moduleObject = new ModuleObject();
            moduleObject.setModuleName(modulesToAdd[i]);
            moduleObject.setQuantity(1);
            newModules.add(moduleObject);
        }
        fitObject.setModules(newModules);
        fixedFitObjects.add(fitObject);

        fitObject = new FitObject();
        fitObject.setShipType("Booster Pod");
        fitObject.setDoctrineName("Skirmish Command Mindlink");
        fitObject.setShipClass("Pod");
        fitObject.setPayoutRate(100);
        modulesToAdd = new String[] {"Skirmish Command Mindlink"};
        newModules = new ArrayList<ModuleObject>();
        for(int i=0; i<modulesToAdd.length; i++) {
            ModuleObject moduleObject = new ModuleObject();
            moduleObject.setModuleName(modulesToAdd[i]);
            moduleObject.setQuantity(1);
            newModules.add(moduleObject);
        }
        fitObject.setModules(newModules);
        fixedFitObjects.add(fitObject);

        fitObject = new FitObject();
        fitObject.setShipType("Faction Booster Pod");
        fitObject.setDoctrineName("Caldari Navy Command Mindlink");
        fitObject.setShipClass("Pod");
        fitObject.setPayoutRate(100);
        modulesToAdd = new String[] {"Caldari Navy Command Mindlink"};
        newModules = new ArrayList<ModuleObject>();
        for(int i=0; i<modulesToAdd.length; i++) {
            ModuleObject moduleObject = new ModuleObject();
            moduleObject.setModuleName(modulesToAdd[i]);
            moduleObject.setQuantity(1);
            newModules.add(moduleObject);
        }
        fitObject.setModules(newModules);
        fixedFitObjects.add(fitObject);

        fitObject = new FitObject();
        fitObject.setShipType("Faction Booster Pod");
        fitObject.setDoctrineName("Federation Navy Command Mindlink");
        fitObject.setShipClass("Pod");
        fitObject.setPayoutRate(100);
        modulesToAdd = new String[] {"Federation Navy Command Mindlink"};
        newModules = new ArrayList<ModuleObject>();
        for(int i=0; i<modulesToAdd.length; i++) {
            ModuleObject moduleObject = new ModuleObject();
            moduleObject.setModuleName(modulesToAdd[i]);
            moduleObject.setQuantity(1);
            newModules.add(moduleObject);
        }
        fitObject.setModules(newModules);
        fixedFitObjects.add(fitObject);

        fitObject = new FitObject();
        fitObject.setShipType("Faction Booster Pod");
        fitObject.setDoctrineName("Imperial Navy Command Mindlink");
        fitObject.setShipClass("Pod");
        fitObject.setPayoutRate(100);
        modulesToAdd = new String[] {"Imperial Navy Command Mindlink"};
        newModules = new ArrayList<ModuleObject>();
        for(int i=0; i<modulesToAdd.length; i++) {
            ModuleObject moduleObject = new ModuleObject();
            moduleObject.setModuleName(modulesToAdd[i]);
            moduleObject.setQuantity(1);
            newModules.add(moduleObject);
        }
        fitObject.setModules(newModules);
        fixedFitObjects.add(fitObject);

        fitObject = new FitObject();
        fitObject.setShipType("Faction Booster Pod");
        fitObject.setDoctrineName("Republic Fleet Command Mindlink");
        fitObject.setShipClass("Pod");
        fitObject.setPayoutRate(100);
        modulesToAdd = new String[] {"Republic Fleet Command Mindlink"};
        newModules = new ArrayList<ModuleObject>();
        for(int i=0; i<modulesToAdd.length; i++) {
            ModuleObject moduleObject = new ModuleObject();
            moduleObject.setModuleName(modulesToAdd[i]);
            moduleObject.setQuantity(1);
            newModules.add(moduleObject);
        }
        fitObject.setModules(newModules);
        fixedFitObjects.add(fitObject);

        fitObject = new FitObject();
        fitObject.setShipType("Faction Booster Pod");
        fitObject.setDoctrineName("Guri Malakim Command Mindlink");
        fitObject.setShipClass("Pod");
        fitObject.setPayoutRate(100);
        modulesToAdd = new String[] {"Guri Malakim Command Mindlink"};
        newModules = new ArrayList<ModuleObject>();
        for(int i=0; i<modulesToAdd.length; i++) {
            ModuleObject moduleObject = new ModuleObject();
            moduleObject.setModuleName(modulesToAdd[i]);
            moduleObject.setQuantity(1);
            newModules.add(moduleObject);
        }
        fitObject.setModules(newModules);
        fixedFitObjects.add(fitObject);

        fitObject = new FitObject();
        fitObject.setShipType("Deployable");
        fitObject.setDoctrineName("Mobile Cynosural Inhibitor");
        fitObject.setShipClass("Pod");
        fitObject.setPayoutRate(100);
        modulesToAdd = new String[] {"Mobile Cynosural Inhibitor"};
        newModules = new ArrayList<ModuleObject>();
        for(int i=0; i<modulesToAdd.length; i++) {
            ModuleObject moduleObject = new ModuleObject();
            moduleObject.setModuleName(modulesToAdd[i]);
            moduleObject.setQuantity(1);
            newModules.add(moduleObject);
        }
        fitObject.setModules(newModules);
        fixedFitObjects.add(fitObject);

        sortByShipType(fixedFitObjects);

        return fixedFitObjects;
    }

    public static void sortByShipType(ArrayList<FitObject> allFitObjects) {

        allFitObjects.sort((o1, o2)
                -> o1.getShipType().compareTo(
                o2.getShipType()));
    }

    public static void sortByDoctrineName(ArrayList<FitObject> allFitObjects) {

        allFitObjects.sort((o1, o2)
                -> o1.getDoctrineName().compareTo(
                o2.getDoctrineName()));
    }

    public static void checkForMissingModules(ArrayList<FitObject> allFitObjects, String TYPENAMES_CSV){
        ArrayList<ModuleObject> updatedModules = new ArrayList<>();
        for(FitObject fitObject : allFitObjects){
            if(!fitObject.getShipType().substring(0,3).equals("Any")){
                ArrayList<ModuleObject> allModules = fitObject.getModules();
                if (allModules != null) {
                    for (ModuleObject moduleObject : allModules) {
                        if (moduleObject.getTotalPrice() == 0) {
                            String typeID = CSVCode.getTypeIdForEveTycoon(TYPENAMES_CSV, moduleObject.getModuleName());
                            int value = 0;
                            boolean foundUpdatedPrice = false;
                            System.out.println("WARNING: " + fitObject.getShipType() + " - " + fitObject.getDoctrineName() + " has bad module prices on " + moduleObject.getModuleName());
                            if(!updatedModules.isEmpty()){
                                for(ModuleObject checkForUpdatedModule : updatedModules) {
                                    if(checkForUpdatedModule.getModuleName().equals(moduleObject.getModuleName())){
                                        value = (int)checkForUpdatedModule.getUnitPrice();
                                        foundUpdatedPrice = true;
                                        break;
                                    }
                                }
                            }
                            if(updatedModules.isEmpty() || foundUpdatedPrice == false) {
                                java.awt.Toolkit.getDefaultToolkit().beep();
                                java.awt.Toolkit.getDefaultToolkit().beep();
                                System.out.println("https://evetycoon.com/market/" + typeID);
                                value = MsgBox.askValueInt(moduleObject.getModuleName() + " is currently zero.", moduleObject.getModuleName());
                            }

                            moduleObject.setUnitPrice(value);
                            moduleObject.setTotalPrice(moduleObject.getUnitPrice() * moduleObject.getQuantity());
                            ModuleObject updatedModule = new ModuleObject();
                            updatedModule.setModuleName(moduleObject.getModuleName());
                            updatedModule.setQuantity(moduleObject.getQuantity());
                            updatedModule.setUnitPrice(moduleObject.getUnitPrice());
                            updatedModule.setTotalPrice(moduleObject.getTotalPrice());
                            updatedModules.add(updatedModule);
                        }
                    }
                } else {
                    System.out.println("WARNING: " + fitObject.getShipType() + " - " + fitObject.getDoctrineName() + " has no module prices.");
                }
            }
        }
    }

    public static void showAttribute(String shipName, String attribute, ArrayList<FitObject> allFitObjects) {

        String printAttr = "";

        for(FitObject fitObject : allFitObjects){
            if(fitObject.getShipType().toLowerCase().equals(shipName.toLowerCase())){
                switch (attribute){
                    case "doctrineName":
                        printAttr = fitObject.getDoctrineName();
                        break;
                    case "shipType":
                        printAttr = fitObject.getShipType();
                        break;
                    case "description":
                        printAttr = fitObject.getDescription();
                        break;
                    case "fittingURL":
                        printAttr = fitObject.getFittingURL();
                        break;
                    case "hullPrice":
                        printAttr = String.valueOf(fitObject.getHullPrice());
                        break;
                    case "insurancePayout":
                        printAttr = String.valueOf(fitObject.getInsurancePayout());
                        break;
                    case "modulesTotalPrice":
                        printAttr = String.valueOf(fitObject.getModulesTotalPrice());
                        break;
                    case "totalPayout100":
                        printAttr = String.valueOf(fitObject.getTotalPayout100());
                        break;
                    case "typeId":
                        printAttr = fitObject.getTypeId();
                        break;
                }
                System.out.println("Ship: " + shipName + " " + attribute + ": " + printAttr);
            }
        }
    }

    public static void crossCheckPrices(ArrayList<FitObject> allFitObjects, ArrayList<FitObject> priceCheckFitObjects) {

        for(FitObject fitObject : allFitObjects){
            String doctrineName = "=HYPERLINK(\"" + fitObject.getFittingURL() + "\";\"" + fitObject.getDoctrineName() + "\")";
            String shipType = fitObject.getShipType();
            for(FitObject crossCheck : priceCheckFitObjects)
            {
                String doctrineNameCrossCheck= crossCheck.getDoctrineName();
                String shipTypeCrossCheck = crossCheck.getShipType();
                if(doctrineName.equals(doctrineNameCrossCheck) && shipType.equals(shipTypeCrossCheck)){
                    if(fitObject.getTotalPayout100() < crossCheck.getTotalPayout100() * 0.8  || fitObject.getTotalPayout100() > crossCheck.getTotalPayout100() * 1.2){
                        String incDec = "decrease";
                        if(fitObject.getTotalPayout100() > crossCheck.getTotalPayout100()){
                            incDec = "increase";
                        }
                        System.out.println(shipType + " - " + fitObject.getDoctrineName() + " is out by more than 20%. Difference " + incDec + " is " + String.format("%,d",Math.abs(fitObject.getTotalPayout100() - crossCheck.getTotalPayout100())));
                    }
                    break;
                }
            }


        }
    }

    public static void crossCheckFixedPrices(ArrayList<FitObject> allFitObjects, ArrayList<FitObject> priceCheckFitObjects) {

        for(FitObject fitObject : allFitObjects){
            String doctrineName = fitObject.getDoctrineName();
            String shipType = fitObject.getShipType();
            for(FitObject crossCheck : priceCheckFitObjects)
            {
                String doctrineNameCrossCheck= crossCheck.getDoctrineName();
                String shipTypeCrossCheck = crossCheck.getShipType();
                if(doctrineName.equals(doctrineNameCrossCheck) && shipType.equals(shipTypeCrossCheck)){
                    if(fitObject.getTotalPayout100() < crossCheck.getTotalPayout100() * 0.8  || fitObject.getTotalPayout100() > crossCheck.getTotalPayout100() * 1.2){
                        String incDec = "decrease";
                        if(fitObject.getTotalPayout100() > crossCheck.getTotalPayout100()){
                            incDec = "increase";
                        }
                        System.out.println(shipType + " - " + fitObject.getDoctrineName() + " is out by more than 20%. Difference " + incDec + " is " + String.format("%,d",Math.abs(fitObject.getTotalPayout100() - crossCheck.getTotalPayout100())));
                    }
                    break;
                }
            }


        }
    }
}
