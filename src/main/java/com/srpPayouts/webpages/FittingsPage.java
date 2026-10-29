package com.srpPayouts.webpages;

import com.srpPayouts.browser.PageActionCode;
import com.srpPayouts.domain.FitObject;
import com.srpPayouts.ui.MsgBox;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FittingsPage {
    private WebDriver driver;

    PageActionCode pageActionCode = new PageActionCode();

    @FindBy(how = How.XPATH, using = "//a[text()='View All Fits']") private WebElement allFitsLink;
    @FindBy(how = How.XPATH, using = "//a[text()='Next']") private WebElement nextButton;

    //@FindAll(@FindBy(how = How.XPATH, using = "//li[@class='paginate_button next disabled']")) private List<WebElement> allNextButtonDisabled; //new page
    @FindAll(@FindBy(how = How.XPATH, using = "//li[@class='paginate_button page-item next disabled']")) private List<WebElement> allNextButtonDisabled; //old page

    @FindAll(@FindBy(how = How.XPATH, using = "//div[@class='panel-heading' and text()='Character Skill Checks']/../div[@class='panel-body']/p")) private List<WebElement> allPilots;


    public FittingsPage(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    public FittingsPage(WebDriver driver, String url) {
        this.driver=driver;
        driver.get(java.util.Objects.requireNonNull(url));
        PageFactory.initElements(driver, this);
    }

    public FittingsPage()
    {}

    public void waitPageLoaded() throws Throwable {
        //pageActionCode.waitPageTitleHasLoaded (driver, "View All Fits - INIT. Auth"); //new page
        pageActionCode.waitPageTitleHasLoaded (driver, "View All Fittings - INIT. Auth"); //old page
        //pageActionCode.waitElementIsClickable(driver,allFitsLink); //new code
    }

    public ArrayList<FitObject> captureAllFittings(ArrayList<FitObject> allFitObjects) {
        Document resultsPage = Jsoup.parse(java.util.Objects.requireNonNull(driver.getPageSource()));

        Elements allFitRows = resultsPage.select("table#fitTable > tbody > tr");
        for(Element fitRow : allFitRows){
            FitObject fitObject = new FitObject();
            fitObject.setDoctrineName(fitRow.select("td").get(0).text().replace(",","."));
            fitObject.setFittingURL("https://zero.the-initiative.rocks" + fitRow.select("td").get(0).select("a").get(0).attr("href"));

//old page
            fitObject.setShipType(fitRow.select("td").get(1).text());
            String category = fitRow.select("td").get(2).text();
            fitObject.setDescription(fitRow.select("td").get(3).text());
//old page


/* //new page
            String category = fitRow.select("td").get(1).text();
            fitObject.setDescription(fitRow.select("td").get(2).text());
            fitObject.setShipType(fitRow.select("td").get(3).text());
*/ //new page

            fitObject.setShipClass(lookupShipClass(fitObject.getFittingURL()));
            fitObject.setPayoutRate(lookupPayRate(fitObject.getShipClass()));

            if((checkDoctrineName(fitObject.getDoctrineName(), fitObject.getShipType()) & checkCategoryName(category) && checkClassType(fitObject.getShipClass()))) {
                fitObject.setPrintEntry(true);
                allFitObjects.add(fitObject);
            }

        }
        return allFitObjects;
    }

    public static long lookupPayRate(String shipClass)
    {
        long payRate = 0;
        switch(shipClass){
            case "Capital":
            case "Logi":
            case "FC Ship":
            case "Command Destroyer":
            case "Command Ship":
            default:
                payRate = 100;
                break;

            case "Interceptor":
            case "DPS":
            case "Battleship":
            case "Interdictor":
            case "Bomber":
            case "Support":
                payRate = 100;
                break;

        }
        return payRate;
    }

    private String lookupShipClass(String fittingURL) {
        String fittingNumber = fittingURL.replace("https://zero.the-initiative.rocks/fittings/fit/","").replace("/","");
        String shipClass = "";

        switch(fittingNumber) {
            case "53":
            case "168":
            case "169":
            case "170":
            case "181":
            case "182":
            case "183":
            case "185":
            case "186":
            case "187":
            case "188":
            case "189":
            case "190":
            case "191":
            case "192":
            case "195":
            case "196":
            case "289":
            case "299":
            case "300":
            case "301":
            case "302":
            case "303":
            case "305":
            case "337":
            case "338":
            case "512":
            case "573":
            case "574":
            case "577":
            case "627":
            case "628":
            case "629":
            case "630":
            case "631":
            case "737":
            case "802":
            case "803":
            case "831":
            case "832":
            case "833":
            case "834":
            case "837":
            case "893":
            case "894":
                shipClass = "Capital";
                break;

            case "2":
            case "6":
            case "27":
            case "31":
            case "67":
            case "69":
            case "71":
            case "101":
            case "102":
            case "108":
            case "199":
            case "202":
            case "210":
            case "217":
            case "257":
            case "297":
            case "298":
            case "322":
            case "341":
            case "357":
            case "419":
            case "420":
            case "460":
            case "461":
            case "477":
            case "484":
            case "514":
            case "543":
            case "563":
            case "603":
            case "606":
            case "624":
            case "625":
            case "633":
            case "683":
            case "684":
            case "689":
            case "716":
            case "718":
            case "719":
            case "771":
            case "777":
            case "778":
            case "779":
            case "780":
            case "781":
            case "782":
            case "808":
            case "812":
            case "821":
            case "828":
            case "840":
            case "867":
            case "874":
            case "875":
            case "890":
            case "904":
                shipClass = "Logi";
                break;

            case "399":
            case "400":
            case "401":
            case "403":
            case "440":
            case "441":
            case "442":
            case "444":
            case "445":
            case "446":
            case "447":
            case "448":
            case "449":
            case "480":
            case "481":
            case "482":
            case "483":
            case "652":
            case "714":
                shipClass = "Interceptor";
                break;

            case "42":
            case "44":
            case "74":
            case "75":
            case "93":
            case "116":
            case "197":
            case "198":
            case "200":
            case "201":
            case "205":
            case "207":
            case "231":
            case "250":
            case "254":
            case "259":
            case "268":
            case "269":
            case "270":
            case "271":
            case "272":
            case "274":
            case "285":
            case "286":
            case "287":
            case "288":
            case "290":
            case "309":
            case "310":
            case "311":
            case "317":
            case "330":
            case "340":
            case "355":
            case "456":
            case "458":
            case "459":
            case "465":
            case "466":
            case "472":
            case "486":
            case "487":
            case "494":
            case "499":
            case "500":
            case "501":
            case "502":
            case "504":
            case "505":
            case "506":
            case "513":
            case "518":
            case "519":
            case "535":
            case "539":
            case "540":
            case "541":
            case "542":
            case "547":
            case "550":
            case "552":
            case "553":
            case "554":
            case "555":
            case "556":
            case "562":
            case "564":
            case "565":
            case "566":
            case "578":
            case "581":
            case "582":
            case "595":
            case "596":
            case "598":
            case "602":
            case "604":
            case "605":
            case "620":
            case "621":
            case "622":
            case "623":
            case "648":
            case "649":
            case "654":
            case "658":
            case "662":
            case "664":
            case "665":
            case "673":
            case "679":
            case "680":
            case "685":
            case "688":
            case "697":
            case "698":
            case "707":
            case "715":
            case "774":
            case "769":
            case "801":
            case "804":
            case "806":
            case "807":
            case "810":
            case "813":
            case "817":
            case "823":
            case "829":
            case "835":
            case "839":
            case "846":
            case "851":
            case "853":
            case "860":
            case "863":
            case "865":
            case "866":
            case "869":
            case "870":
            case "871":
            case "873":
            case "879":
            case "889":
            case "899":
            case "901":
            case "902":
            case "903":
                shipClass = "DPS";
                break;

            case "1":
            case "4":
            case "5":
            case "15":
            case "65":
            case "97":
            case "98":
            case "99":
            case "109":
            case "110":
            case "111":
            case "121":
            case "164":
            case "171":
            case "172":
            case "173":
            case "175":
            case "176":
            case "177":
            case "203":
            case "204":
            case "208":
            case "209":
            case "211":
            case "212":
            case "232":
            case "233":
            case "236":
            case "240":
            case "244":
            case "245":
            case "246":
            case "258":
            case "279":
            case "316":
            case "319":
            case "320":
            case "323":
            case "324":
            case "325":
            case "326":
            case "328":
            case "342":
            case "343":
            case "344":
            case "345":
            case "346":
            case "356":
            case "359":
            case "360":
            case "397":
            case "398":
            case "425":
            case "426":
            case "454":
            case "455":
            case "468":
            case "475":
            case "476":
            case "503":
            case "520":
            case "544":
            case "545":
            case "546":
            case "548":
            case "549":
            case "559":
            case "560":
            case "561":
            case "569":
            case "575":
            case "584":
            case "585":
            case "586":
            case "587":
            case "588":
            case "592":
            case "593":
            case "594":
            case "601":
            case "626":
            case "635":
            case "638":
            case "646":
            case "647":
            case "650":
            case "651":
            case "653":
            case "656":
            case "657":
            case "659":
            case "663":
            case "674":
            case "677":
            case "687":
            case "692":
            case "693":
            case "694":
            case "695":
            case "696":
            case "700":
            case "701":
            case "702":
            case "704":
            case "705":
            case "706":
            case "717":
            case "770":
            case "772":
            case "775":
            case "776":
            case "785":
            case "786":
            case "789":
            case "790":
            case "791":
            case "792":
            case "795":
            case "796":
            case "797":
            case "809":
            case "816":
            case "820":
            case "824":
            case "825":
            case "826":
            case "827":
            case "830":
            case "836":
            case "838":
            case "841":
            case "842":
            case "843":
            case "868":
            case "872":
            case "876":
            case "877":
            case "878":
            case "880":
            case "881":
            case "882":
            case "883":
            case "884":
            case "885":
            case "886":
            case "887":
            case "888":
            case "891":
            case "892":
            case "895":
            case "896":
            case "897":
            case "898":
            case "900":
                shipClass = "Support";
                break;


            case "218":
            case "219":
            case "228":
            case "307":
            case "321":
            case "329":
            case "331":
            case "332":
            case "347":
            case "462":
            case "463":
            case "492":
            case "493":
            case "497":
            case "690":
            case "818":
            case "819":
                shipClass = "Command Destroyer";
                break;

            case "32":
            case "36":
            case "61":
            case "63":
            case "107":
            case "165":
            case "239":
            case "252":
            case "314":
            case "358":
            case "473":
            case "474":
            case "485":
            case "516":
            case "517":
            case "558":
            case "583":
            case "597":
            case "600":
            case "686":
            case "699":
            case "811":
            case "814":
            case "815":
            case "844":
            case "845":
                shipClass = "Command Ship";
                break;


            case "327":
            case "395":
            case "396":
            case "405":
            case "406":
            case "407":
            case "408":
            case "409":
            case "411":
            case "412":
            case "413":
            case "415":
            case "416":
            case "417":
            case "457":
            case "489":
            case "511":
                shipClass = "Interdictor";
                break;

            case "216":
            case "275":
            case "312":
            case "424":
            case "439":
            case "572":
            case "576":
            case "579":
            case "580":
            case "660":
            case "632":
            case "661":
            case "720":
            case "805":
                shipClass = "Battleship";
                break;

            case "536":
            case "537":
                shipClass = "Structure";
                break;

            case "78":
            case "94":
            case "784":
            case "822":
                shipClass = "FC Ship";
                break;

            case "90":
            case "91":
            case "95":
            case "280":
            case "281":
            case "282":
            case "283":
            case "567":
            case "568":
            case "570":
            case "571":
            case "708":
            case "709":
            case "787":
            case "788":
                shipClass = "Bomber";
                break;

            default:
                shipClass = MsgBox.askValueString("......................... This fit is UNKNOWN .........................\nSet the category or just click OK to leave it as UNKNOWN.\n672,590,862\n\n" +
                        "Capital\n" +
                        "Logi\n" +
                        "FC Ship\n" +
                        "Command Destroyer\n" +
                        "Command Ship\n" +
                        "Interceptor\n" +
                        "DPS\n" +
                        "Battleship\n" +
                        "Interdictor\n" +
                        "Bomber\n" +
                        "Support\n" +
                        "\n", "https://zero.the-initiative.rocks/fittings/fit/" + fittingNumber + "/");
                if(shipClass.length() == 0) {
                    shipClass = "UNKNOWN";
                }
                if(!shipClass.equals("UNKNOWN")) {
                    System.out.println("New doctrine? https://zero.the-initiative.rocks/fittings/fit/" + fittingNumber + "/    - Set to " + shipClass);
                    System.out.println("\t\t\tcase \"" + fittingNumber + "\":");
                }
        }
        return shipClass;
    }

    public boolean checkCategoryName(String category){  //remove these categories
        if(category.toLowerCase().contains("retired")){
            return false;
        }
        return true;
    }

    public boolean checkDoctrineName(String doctrineName, String shipType) { //remove these doctrines

/*
        if(doctrineName.contains("Rokh") && !shipType.equals("Rokh")){
            return false;
        }
*/

        if( doctrineName.contains("LARS 3.0") ||
            doctrineName.contains("Zoolander") ||
            doctrineName.contains("dude") ||
            doctrineName.contains("Anomic") ||
            doctrineName.contains("Minipikas")
        )
            return false;

        return true;
    }

    public boolean checkClassType(String className){
        if(
                className.equals("Interceptor") ||
                className.equals("Interdictor") ||
                className.equals("Bomber") ||
                //className.equals("Command Destroyer") ||
                className.equals("Structure")
        ) {
            return false;
        }
        return true;
    }

    public boolean checkShipType(String shipType, String doctrineName) { //remove these ships
        if (shipType.equals("Purifier") ||
                shipType.equals("Manticore") ||
                shipType.equals("Hound") ||
                shipType.equals("Nemesis") ||
                shipType.equals("Pontifex") ||
                shipType.equals("Bifrost") ||
                shipType.equals("Stork") ||
                shipType.equals("Malediction") ||
                shipType.equals("Stiletto") ||
                shipType.equals("Crow") ||
                shipType.equals("Ares") ||
                shipType.equals("Raptor") ||
                shipType.equals("Eris") ||
                shipType.equals("Flycatcher") ||
                shipType.equals("Heretic") ||
                shipType.equals("Sabre") ||
                shipType.equals("Claw") ||
                shipType.equals("Crusader") ||
                shipType.equals("Taranis") ||
                shipType.equals("Fortizar") ||
                shipType.equals("Aeon") ||
                shipType.equals("Thanatos") ||
                shipType.equals("Archon") ||
                shipType.equals("Nyx") ||
                shipType.equals("Nidhoggur") ||
                shipType.equals("Moros Navy Issue") ||
                shipType.equals("Zirnitra") ||
                shipType.equals("Revelation") ||
                shipType.equals("Revelation Navy Issue") ||
                shipType.equals("Naglfar") ||
                shipType.equals("Phoenix") ||
                shipType.equals("Rorqual") ||
                shipType.equals("Ragnarok") ||
                shipType.equals("Erebus") ||
                shipType.equals("Avatar") ||
                shipType.equals("Chimera") ||
                shipType.equals("Minokawa") ||
                shipType.equals("Moros") ||
                shipType.equals("Hubris") ||
                shipType.equals("Apostle")
        ) {
            return false;
        }
        return true;

    }


    public static String getAllShipTypesString(ArrayList<FitObject> allFitObjects){
        ArrayList<String> tempShipType = new ArrayList<>();
        for(FitObject fitObject : allFitObjects) {
            if(!fitObject.getShipClass().equals("Capital")) {
                tempShipType.add(fitObject.getShipType());
            }
        }
        Collections.sort(tempShipType);

        String allShipTypes = "";
        for(String shipType : tempShipType){
            if(!allShipTypes.contains(shipType)){
                allShipTypes += shipType + "\r\n";
            }
        }
        return allShipTypes;
    }

    public boolean findNextButton() {
        if(allNextButtonDisabled.size() == 0)
            return true;
        return false;
    }

    public void clickNextButton(){
        nextButton.click();
    }
}

