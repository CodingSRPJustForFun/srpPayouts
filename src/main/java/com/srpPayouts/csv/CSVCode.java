package com.srpPayouts.csv;

import com.srpPayouts.config.AppConfig;
import com.srpPayouts.domain.FitObject;
import com.srpPayouts.domain.ModuleObject;
import com.srpPayouts.ui.MsgBox;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CSVCode {

    public static void WriteDataCSV(ArrayList<FitObject> allFitObjects, ArrayList<FitObject> fixedFitObjects) throws IOException {

        Path outputDir = AppConfig.outputDir();
        Files.createDirectories(outputDir);
        Path fileName = AppConfig.fitObjectsCsv();

        boolean fileExists = Files.isRegularFile(fileName);
        if (fileExists) {
            File file = fileName.toFile();
            boolean fileIsLocked = !file.renameTo(file);
            if (fileIsLocked) {
                MsgBox.info("Data CSV File open. Close now.", "CSV Writer");
            } else {
                //File exists but not open
                int fileSuffix = 1;
                while (fileExists) {
                    Path temp = outputDir.resolve("FitObjects_" + (fileSuffix++) + ".csv");
                    fileExists = Files.isRegularFile(temp);
                    if (!fileExists) {
                        fileName = temp;
                        break;
                    }

                }
            }
        }

        FileWriter csvWriter = new FileWriter(fileName.toFile());

        csvWriter.append("Hull");
        csvWriter.append(",");
        csvWriter.append("Doctrine Name");
        csvWriter.append(",");
        csvWriter.append("Class");
        csvWriter.append(",");
        csvWriter.append("Rate%");
        csvWriter.append(",");

        csvWriter.append("Full Payout");
        csvWriter.append(",");
        csvWriter.append("Full Payout + 10%");
        csvWriter.append(",");
        csvWriter.append("75% Payout");
        csvWriter.append(",");
        csvWriter.append("75% Payout + 10%");
        csvWriter.append(",");
        csvWriter.append("Days since last change");

        //csvWriter.append(",");
        //csvWriter.append("-25% Payout");
        //csvWriter.append(",");
        //csvWriter.append("-50% Payout");
        //csvWriter.append(",");
        //csvWriter.append("Hull Price");
        //csvWriter.append(",");
        //csvWriter.append("Modules Price");
        //csvWriter.append(",");
        //csvWriter.append("Insurance");
        csvWriter.append("\n");

        int lineCount = 1;
        for (FitObject fixedFitObject : fixedFitObjects) {
            csvWriter.append(fixedFitObject.getShipType());
            csvWriter.append(",");
            csvWriter.append(fixedFitObject.getDoctrineName());
            csvWriter.append(",");
            csvWriter.append(",");
            csvWriter.append(String.valueOf(fixedFitObject.getPayoutRate()));
            csvWriter.append(",");

            csvWriter.append(String.valueOf(fixedFitObject.getTotalPayout100()));
            //if (fixedFitObject.getShipType().equals("Amulet Pod")) {
                csvWriter.append(",");
                csvWriter.append(String.valueOf(fixedFitObject.getTotalPayout110()));
                //csvWriter.append(String.valueOf(fixedFitObject.getTotalPayout75()));
                //csvWriter.append(",");
                //csvWriter.append(String.valueOf(fixedFitObject.getTotalPayout50()));
            //}
            String updateSpacer = ",,,,";
            if (lineCount == 1) {
                csvWriter.append(updateSpacer);
                csvWriter.append("Updated");
            }
            if (lineCount == 2) {
                String[] suffixes =
                        //    0     1     2     3     4     5     6     7     8     9
                        {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                                //    10    11    12    13    14    15    16    17    18    19
                                "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                                //    20    21    22    23    24    25    26    27    28    29
                                "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                                //    30    31
                                "th", "st"};
                DateFormat dateFormat1 = new SimpleDateFormat("d");
                DateFormat dateFormat2 = new SimpleDateFormat("MMMMM");
                Date date = new Date();
                int day = Integer.parseInt(dateFormat1.format(date));
                String dayStr = day + suffixes[day];
                String monStr = dateFormat2.format(date);
                //System.out.println(dayStr + " " + monStr);
                csvWriter.append(updateSpacer);
                csvWriter.append(dayStr + " " + monStr);
            }
            lineCount++;
            csvWriter.append("\n");
        }
        csvWriter.append("\n");
        

        for(FitObject fitObject : allFitObjects){
            if(fitObject.getPrintEntry() == true) {
                if(!fitObject.getShipClass().equals("Capital")){
                    csvWriter.append(fitObject.getShipType());
                    csvWriter.append(",");
                    csvWriter.append("=HYPERLINK(\"" + fitObject.getFittingURL() + "\";\"" + fitObject.getDoctrineName() + "\")"); //=HYPERLINK("https://zero.the-initiative.rocks/fittings/fit/154/";"Krueger")
                    csvWriter.append(",");
                    csvWriter.append(fitObject.getShipClass());
                    csvWriter.append(",");
                    csvWriter.append(String.valueOf(fitObject.getPayoutRate()));
                    csvWriter.append(",");
                    csvWriter.append(String.valueOf(fitObject.getTotalPayout100()));
                    csvWriter.append(",");
                    csvWriter.append(String.valueOf(fitObject.getTotalPayout110()));
                    csvWriter.append(",");
                    csvWriter.append(String.valueOf(fitObject.getTotalPayout75()));
                    csvWriter.append(",");
                    csvWriter.append(String.valueOf(fitObject.getTotalPayout85()));
                    csvWriter.append(",");
                    csvWriter.append(fitObject.getLastUpdatedDays());
                    //csvWriter.append(",");
                    //csvWriter.append(String.valueOf(fitObject.getTotalPayout50()));
                    //csvWriter.append(",");
                    //csvWriter.append(String.valueOf(fitObject.getHullPrice()));
                    //csvWriter.append(",");
                    //csvWriter.append(String.valueOf(fitObject.getModulesTotalPrice()));
                    //csvWriter.append(",");
                    //csvWriter.append(String.valueOf(fitObject.getInsurancePayout()));
                    csvWriter.append("\n");
                    if(fitObject.getHullPrice() < 1){
                        System.out.println("WARNING: Found zero value hull - " + fitObject.getShipType());
                    }
                }
            }


        }
        csvWriter.append("\n\n");
        csvWriter.append("Capitals\n");

        lineCount = 1;
        for(FitObject fitObject : allFitObjects) {
            if (fitObject.getPrintEntry() == true) {
                if (fitObject.getShipClass().equals("Capital")) {
                    if (fitObject.getHullPrice() == 0) {
                        csvWriter.append(fitObject.getShipType() + " (No hull / fitting notes)");
                    } else {
                        csvWriter.append(fitObject.getShipType() + " (No fitting notes)");
                    }
                    csvWriter.append(",");
                    csvWriter.append("=HYPERLINK(\"" + fitObject.getFittingURL() + "\";\"" + fitObject.getDoctrineName() + "\")"); //=HYPERLINK("https://zero.the-initiative.rocks/fittings/fit/154/";"Krueger")
                    csvWriter.append(",");
                    csvWriter.append(fitObject.getShipClass());
                    csvWriter.append(",");
                    csvWriter.append(String.valueOf(fitObject.getPayoutRate()));
                    csvWriter.append(",");
                    csvWriter.append(String.valueOf(fitObject.getTotalPayout100()));
                    csvWriter.append(",");
                    csvWriter.append(String.valueOf(fitObject.getTotalPayout110()));
                    csvWriter.append(",");
                    csvWriter.append(String.valueOf(fitObject.getTotalPayout75()));
                    csvWriter.append(",");
                    csvWriter.append(String.valueOf(fitObject.getTotalPayout85()));
                    csvWriter.append(",");
                    csvWriter.append(fitObject.getLastUpdatedDays());
                    //csvWriter.append(",");
                    //csvWriter.append(String.valueOf(fitObject.getTotalPayout50()));
                    //csvWriter.append(",");
                    //csvWriter.append(String.valueOf(fitObject.getHullPrice()));
                    //csvWriter.append(",");
                    //csvWriter.append(String.valueOf(fitObject.getModulesTotalPrice()));
                    //csvWriter.append(",");
                    //csvWriter.append(String.valueOf(fitObject.getInsurancePayout()));
                    String updateSpacer = ",,";
                    if (lineCount == 1) {
                        csvWriter.append(updateSpacer);
                        csvWriter.append("Updated");
                    }
                    if (lineCount == 2) {
                        String[] suffixes =
                                //    0     1     2     3     4     5     6     7     8     9
                                {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                                        //    10    11    12    13    14    15    16    17    18    19
                                        "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                                        //    20    21    22    23    24    25    26    27    28    29
                                        "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                                        //    30    31
                                        "th", "st"};
                        DateFormat dateFormat1 = new SimpleDateFormat("d");
                        DateFormat dateFormat2 = new SimpleDateFormat("MMMMM");
                        Date date = new Date();
                        int day = Integer.parseInt(dateFormat1.format(date));
                        String dayStr = day + suffixes[day];
                        String monStr = dateFormat2.format(date);
                        //System.out.println(dayStr + " " + monStr);
                        csvWriter.append(updateSpacer);
                        csvWriter.append(dayStr + " " + monStr);
                    }
                    lineCount++;
                    csvWriter.append("\n");
                }
                if(fitObject.getHullPrice() < 1){
                    System.out.println("WARNING: Found zero value hull - " + fitObject.getShipType());
                }
            }
        }
        csvWriter.flush();
        csvWriter.close();
    }

    public static ArrayList<FitObject> assignTypeId(ArrayList<FitObject> allFitObjects, String typeCsvFileName) throws IOException, CsvException {

        List<String[]> fileIN = null;

        try(CSVReader reader = new CSVReaderBuilder(
                new FileReader(typeCsvFileName))
                .withSkipLines(1)           // skip the first line, header info
                .build()) {

            if(fileIN == null)
                fileIN = reader.readAll();

            for(FitObject fitObject : allFitObjects){
                String shipType = fitObject.getShipType();
                Iterator<String[]> iterator = fileIN.iterator();

                while (iterator.hasNext()) {
                    String[] record = iterator.next();
                    String csvTypeName = record[2];
                    if(csvTypeName.equals(shipType)){
                        if(shipType.equals("Avatar"))   //Avatar has two TypeId's for some reason
                            fitObject.setTypeId("11567");
                        else
                            fitObject.setTypeId(record[0]);
                    }
                }
            }
        }
        return allFitObjects;
    }

    public static String getTypeIdForEveTycoon(String typeCsvFileName, String typeName) {

        List<String[]> fileIN = null;
        String typeID = "";

        try(CSVReader reader = new CSVReaderBuilder(
                new FileReader(typeCsvFileName))
                .withSkipLines(1)           // skip the first line, header info
                .build()) {

            if(fileIN == null)
                fileIN = reader.readAll();

            Iterator<String[]> iterator = fileIN.iterator();

            while (iterator.hasNext()) {

                String[] record = iterator.next();
                String csvTypeName = record[2];
                if(csvTypeName.equals(typeName)){
                    typeID = record[0];
                }
            }
        } catch(Exception ex){}
        return typeID;
    }

    public static void writeDoctrineToCSV(ArrayList<FitObject> allFitObjects) throws IOException {

        Path outputDir = AppConfig.outputDir();
        Files.createDirectories(outputDir);
        Path fileName = AppConfig.doctrineCsv();

        boolean fileExists = Files.isRegularFile(fileName);
        if (fileExists) {
            File file = fileName.toFile();
            boolean fileIsLocked = !file.renameTo(file);
            if (fileIsLocked) {
                MsgBox.info("Data CSV File open. Close now.", "CSV Writer");
            } else {
                //File exists but not open
                int fileSuffix = 1;
                while (fileExists) {
                    Path temp = outputDir.resolve("DoctrineObjects_" + (fileSuffix++) + ".csv");
                    fileExists = Files.isRegularFile(temp);
                    if (!fileExists) {
                        fileName = temp;
                        break;
                    }

                }
            }
        }

        FileWriter csvWriter = new FileWriter(fileName.toFile());

        for(FitObject fitObject : allFitObjects){
            if(fitObject.getPrintEntry() == true) {
                csvWriter.append(fitObject.getShipType());
                csvWriter.append("\n");
                csvWriter.append(fitObject.getDoctrineName());
                csvWriter.append("\n");
                csvWriter.append(fitObject.getFittingURL());
                csvWriter.append("\n");
                csvWriter.append(fitObject.getDescription());
                csvWriter.append("\n");
                csvWriter.append(fitObject.getShipClass());
                csvWriter.append("\n");
                csvWriter.append(String.valueOf(fitObject.getPayoutRate()));
                csvWriter.append("\n");

                ArrayList<ModuleObject> allModuleObjects = fitObject.getModules();
                if (allModuleObjects != null) {
                    for (ModuleObject moduleObject : allModuleObjects) {
                        csvWriter.append(moduleObject.getModuleName());
                        csvWriter.append(",");
                        csvWriter.append(String.valueOf(moduleObject.getQuantity()));
                        csvWriter.append("\n");
                    }
                }
                csvWriter.append("**********\n");
            }
        }
        csvWriter.flush();
        csvWriter.close();
    }

    public static ArrayList<FitObject> getAllFitObjectsFromCrossCheckFile(ArrayList<FitObject> allFitObjects, String doctrineCsv) throws IOException, CsvException {
        List<String[]> fileIN = null;

        try (CSVReader reader = new CSVReaderBuilder(
                new FileReader(doctrineCsv))
                .build()) {

            if (fileIN == null)
                fileIN = reader.readAll();

            Iterator<String[]> iterator = fileIN.iterator();

            FitObject fitObject = null;

            while (iterator.hasNext()) {
                String[] record = iterator.next();
                if(!record[0].equals("Hull")){
                    if(record.length > 1) {
                        if(record[0].length() > 0) {
                            fitObject = new FitObject();
                            fitObject.setShipType(record[0]);
                            fitObject.setDoctrineName(record[1]);
                            fitObject.setTotalPayout100(Long.valueOf(record[4]));
                            allFitObjects.add(fitObject);
                        }
                    }
                }
            }

        } catch(Exception ex){
            System.out.println("Cross check file not found.");
        }
        return allFitObjects;
    }

    public static ArrayList<FitObject> getAllDoctrineObjects(ArrayList<FitObject> allFitObjects, String doctrineCsv) throws IOException, CsvException {
        List<String[]> fileIN = null;

        try(CSVReader reader = new CSVReaderBuilder(
                new FileReader(doctrineCsv))
                .build()) {

            if(fileIN == null)
                fileIN = reader.readAll();

            Iterator<String[]> iterator = fileIN.iterator();
            int recordCount = 0;

            FitObject fitObject = new FitObject();
            ModuleObject moduleObject = null;
            ArrayList<ModuleObject> allModules = new ArrayList<>();

            while (iterator.hasNext()) {
                String[] record = iterator.next();
                String info = record[0];
                if(info.equals("**********")){
                    fitObject.setModules(allModules);
                    fitObject.setPrintEntry(true);
                    allFitObjects.add(fitObject);
                    recordCount = 0;
                } else {
                    switch (recordCount) {
                        case 0:
                            fitObject = new FitObject();
                            fitObject.setShipType(record[0]);
                            break;
                        case 1:
                            fitObject.setDoctrineName(record[0]);
                            break;
                        case 2:
                            fitObject.setFittingURL(record[0]);
                            break;
                        case 3:
                            fitObject.setDescription(record[0]);
                            break;
                        case 4:
                            fitObject.setShipClass(record[0]);
                            break;
                        case 5:
                            fitObject.setPayoutRate(Long.parseLong(record[0]));
                            allModules = new ArrayList<>();
                            break;

                        default:
                            try {
                                moduleObject = new ModuleObject();
                                moduleObject.setModuleName(record[0]);
                                moduleObject.setQuantity(Integer.parseInt(record[1]));
                                allModules.add(moduleObject);
                            } catch (Exception ex) {
                                System.out.println("Error parsing module data.");
                            }
                            break;

                    }
                    recordCount++;
                }
            }
        } catch(Exception ex) {
            System.out.println("Can't find: " + doctrineCsv);
        }

        return allFitObjects;
    }
}
