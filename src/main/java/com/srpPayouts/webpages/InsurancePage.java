package com.srpPayouts.webpages;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.srpPayouts.domain.FitObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class InsurancePage {

    public InsurancePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public static ArrayList<FitObject> getInsuranceValues(ArrayList<FitObject> allFitObjects) throws IOException {
        String sURL = "https://esi.evetech.net/latest/insurance/prices/?datasource=tranquility&language=en-us"; //just a string

        // Connect to the URL using java's native library
        URL url = java.net.URI.create(sURL).toURL();
        URLConnection request = url.openConnection();
        request.connect();

        // Convert to a JSON object to print data
        JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
        JsonArray jsonArray = root.getAsJsonArray();

        for(FitObject fitObject : allFitObjects){
            for(int i=0; i<jsonArray.size(); i++){
                if(jsonArray.get(i).getAsJsonObject().get("type_id").toString().equals(fitObject.getTypeId())){
                    double payout = Double.parseDouble(jsonArray.get(i).getAsJsonObject().get("levels").getAsJsonArray().get(5).getAsJsonObject().get("payout").toString());
                    double cost = Double.parseDouble(jsonArray.get(i).getAsJsonObject().get("levels").getAsJsonArray().get(5).getAsJsonObject().get("cost").toString());
                    long insurance = (long)(payout-cost);
                    fitObject.setInsurancePayout(insurance);
                }
            }
        }

        for(FitObject fitObject : allFitObjects) {
            long insuranceCheck = fitObject.getInsurancePayout();
            if(insuranceCheck < 100){
                System.out.println("Warning. Found 0 insurance on: " + fitObject.getShipType() + " - " + fitObject.getDoctrineName());
            }
        }

        return allFitObjects;
    }


}

