package com.srpPayouts.webpages;

import com.srpPayouts.browser.PageActionCode;
import com.srpPayouts.domain.FitObject;
import com.srpPayouts.domain.ModuleObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuyAllPage {
    private WebDriver driver;

    PageActionCode pageActionCode = new PageActionCode();
    private static final Clipboard CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard();

    private static final String[] moduleBlackList =
    {
            "Armored Command Mindlink",
            "Black Market 'Valdimar' Drone Durability DD-1001",
            "Black Market 'Valdimar' Drone Durability DD-1003 1,338,000,000",
            "Black Market 'Valdimar' Drone Durability DD-1005 4,980,000,000",
            "Black Market 'Valdimar' Drone Navigation DN-701 19,820,000",
            "Black Market 'Valdimar' Drone Navigation DN-703 734,000,000",
            "Black Market 'Valdimar' Drone Navigation DN-705 2,950,000,000",
            "Black Market 'Valdimar' Drone Sharpshooting DS-801 45,960,000",
            "Black Market 'Valdimar' Drone Sharpshooting DS-803 720,100,000",
            "Black Market 'Valdimar' Drone Sharpshooting DS-805 3,998,000,000",
            "Black Market 'Valdimar' Repair Drone Operation DR-901",
            "Black Market 'Valdimar' Repair Drone Operation DR-903",
            "Black Market 'Valdimar' Repair Drone Operation DR-905",
            "Caldari Navy Command Mindlink",
            "CreoDron 'Bumblebee' Drone Tuner T10-5D",
            "CreoDron 'Yellowjacket' Drone Tuner D5-10T",
            "Cybernetic 'Source' Subprocessor",
            "Cybernetic Subprocessor - Improved",
            "Cybernetic Subprocessor - Standard",
            "Eifyr and Co. 'Alchemist' Biology BY-805",
            "Eifyr and Co. 'Alchemist' Biology BY-810",
            "Eifyr and Co. 'Alchemist' Gas Harvesting GH-801",
            "Eifyr and Co. 'Alchemist' Gas Harvesting GH-803",
            "Eifyr and Co. 'Alchemist' Gas Harvesting GH-805",
            "Eifyr and Co. 'Alchemist' Neurotoxin Control NC-903",
            "Eifyr and Co. 'Alchemist' Neurotoxin Control NC-905",
            "Eifyr and Co. 'Alchemist' Neurotoxin Recovery NR-1003",
            "Eifyr and Co. 'Alchemist' Neurotoxin Recovery NR-1005",
            "Eifyr and Co. 'Gunslinger' Large Projectile Turret LP-1001",
            "Eifyr and Co. 'Gunslinger' Large Projectile Turret LP-1002",
            "Eifyr and Co. 'Gunslinger' Large Projectile Turret LP-1003",
            "Eifyr and Co. 'Gunslinger' Large Projectile Turret LP-1004",
            "Eifyr and Co. 'Gunslinger' Large Projectile Turret LP-1005",
            "Eifyr and Co. 'Gunslinger' Large Projectile Turret LP-1006",
            "Eifyr and Co. 'Gunslinger' Medium Projectile Turret MP-801",
            "Eifyr and Co. 'Gunslinger' Medium Projectile Turret MP-802",
            "Eifyr and Co. 'Gunslinger' Medium Projectile Turret MP-803",
            "Eifyr and Co. 'Gunslinger' Medium Projectile Turret MP-804",
            "Eifyr and Co. 'Gunslinger' Medium Projectile Turret MP-805",
            "Eifyr and Co. 'Gunslinger' Medium Projectile Turret MP-806",
            "Eifyr and Co. 'Gunslinger' Motion Prediction MR-701",
            "Eifyr and Co. 'Gunslinger' Motion Prediction MR-702",
            "Eifyr and Co. 'Gunslinger' Motion Prediction MR-703",
            "Eifyr and Co. 'Gunslinger' Motion Prediction MR-704",
            "Eifyr and Co. 'Gunslinger' Motion Prediction MR-705",
            "Eifyr and Co. 'Gunslinger' Motion Prediction MR-706",
            "Eifyr and Co. 'Gunslinger' Small Projectile Turret SP-601",
            "Eifyr and Co. 'Gunslinger' Small Projectile Turret SP-602",
            "Eifyr and Co. 'Gunslinger' Small Projectile Turret SP-603",
            "Eifyr and Co. 'Gunslinger' Small Projectile Turret SP-604",
            "Eifyr and Co. 'Gunslinger' Small Projectile Turret SP-605",
            "Eifyr and Co. 'Gunslinger' Small Projectile Turret SP-606",
            "Eifyr and Co. 'Gunslinger' Surgical Strike SS-901",
            "Eifyr and Co. 'Gunslinger' Surgical Strike SS-902",
            "Eifyr and Co. 'Gunslinger' Surgical Strike SS-903",
            "Eifyr and Co. 'Gunslinger' Surgical Strike SS-904",
            "Eifyr and Co. 'Gunslinger' Surgical Strike SS-905",
            "Eifyr and Co. 'Gunslinger' Surgical Strike SS-906",
            "Eifyr and Co. 'Rogue' Acceleration Control AC-601",
            "Eifyr and Co. 'Rogue' Acceleration Control AC-602",
            "Eifyr and Co. 'Rogue' Acceleration Control AC-603",
            "Eifyr and Co. 'Rogue' Acceleration Control AC-604",
            "Eifyr and Co. 'Rogue' Acceleration Control AC-605",
            "Eifyr and Co. 'Rogue' Acceleration Control AC-606",
            "Eifyr and Co. 'Rogue' Afterburner AB-602",
            "Eifyr and Co. 'Rogue' Afterburner AB-604",
            "Eifyr and Co. 'Rogue' Afterburner AB-606",
            "Eifyr and Co. 'Rogue' Afterburner AB-608",
            "Eifyr and Co. 'Rogue' Afterburner AB-610",
            "Eifyr and Co. 'Rogue' Afterburner AB-612",
            "Eifyr and Co. 'Rogue' Evasive Maneuvering EM-701",
            "Eifyr and Co. 'Rogue' Evasive Maneuvering EM-702",
            "Eifyr and Co. 'Rogue' Evasive Maneuvering EM-703",
            "Eifyr and Co. 'Rogue' Evasive Maneuvering EM-704",
            "Eifyr and Co. 'Rogue' Evasive Maneuvering EM-705",
            "Eifyr and Co. 'Rogue' Evasive Maneuvering EM-706",
            "Eifyr and Co. 'Rogue' Fuel Conservation FC-801",
            "Eifyr and Co. 'Rogue' Fuel Conservation FC-802",
            "Eifyr and Co. 'Rogue' Fuel Conservation FC-803",
            "Eifyr and Co. 'Rogue' Fuel Conservation FC-804",
            "Eifyr and Co. 'Rogue' Fuel Conservation FC-805",
            "Eifyr and Co. 'Rogue' Fuel Conservation FC-806",
            "Eifyr and Co. 'Rogue' High Speed Maneuvering HS-901",
            "Eifyr and Co. 'Rogue' High Speed Maneuvering HS-902",
            "Eifyr and Co. 'Rogue' High Speed Maneuvering HS-903",
            "Eifyr and Co. 'Rogue' High Speed Maneuvering HS-904",
            "Eifyr and Co. 'Rogue' High Speed Maneuvering HS-905",
            "Eifyr and Co. 'Rogue' High Speed Maneuvering HS-906",
            "Eifyr and Co. 'Rogue' Navigation NN-601",
            "Eifyr and Co. 'Rogue' Navigation NN-602",
            "Eifyr and Co. 'Rogue' Navigation NN-603",
            "Eifyr and Co. 'Rogue' Navigation NN-604",
            "Eifyr and Co. 'Rogue' Navigation NN-605",
            "Eifyr and Co. 'Rogue' Navigation NN-606",
            "Eifyr and Co. 'Rogue' Warp Drive Operation WD-602",
            "Eifyr and Co. 'Rogue' Warp Drive Operation WD-604",
            "Eifyr and Co. 'Rogue' Warp Drive Operation WD-606",
            "Eifyr and Co. 'Rogue' Warp Drive Operation WD-608",
            "Eifyr and Co. 'Rogue' Warp Drive Operation WD-610",
            "Eifyr and Co. 'Rogue' Warp Drive Operation WD-612",
            "Eifyr and Co. 'Rogue' Warp Drive Speed WS-605",
            "Eifyr and Co. 'Rogue' Warp Drive Speed WS-608",
            "Eifyr and Co. 'Rogue' Warp Drive Speed WS-610",
            "Eifyr and Co. 'Rogue' Warp Drive Speed WS-613",
            "Eifyr and Co. 'Rogue' Warp Drive Speed WS-618",
            "Federation Navy Command Mindlink",
            "Genolution Core Augmentation CA-1",
            "Genolution Core Augmentation CA-2",
            "Genolution Core Augmentation CA-3",
            "Genolution Core Augmentation CA-4",
            "Hardwiring - Zainou 'Sharpshooter' ZMX11",
            "Hardwiring - Zainou 'Sharpshooter' ZMX110",
            "Hardwiring - Zainou 'Sharpshooter' ZMX1100",
            "High-grade Amulet Alpha",
            "High-grade Amulet Beta",
            "High-grade Amulet Delta",
            "High-grade Amulet Epsilon",
            "High-grade Amulet Gamma",
            "High-grade Amulet Omega",
            "High-grade Ascendancy Alpha",
            "High-grade Ascendancy Beta",
            "High-grade Ascendancy Delta",
            "High-grade Ascendancy Epsilon",
            "High-grade Ascendancy Gamma",
            "High-grade Ascendancy Omega",
            "High-grade Asklepian Alpha",
            "High-grade Asklepian Beta",
            "High-grade Asklepian Delta",
            "High-grade Asklepian Epsilon",
            "High-grade Asklepian Gamma",
            "High-grade Asklepian Omega",
            "High-grade Crystal Alpha",
            "High-grade Crystal Beta",
            "High-grade Crystal Delta",
            "High-grade Crystal Epsilon",
            "High-grade Crystal Gamma",
            "High-grade Crystal Omega",
            "High-grade Grail Alpha",
            "High-grade Grail Beta",
            "High-grade Grail Delta",
            "High-grade Grail Epsilon",
            "High-grade Grail Gamma",
            "High-grade Grail Omega",
            "High-grade Halo Alpha",
            "High-grade Halo Beta",
            "High-grade Halo Delta",
            "High-grade Halo Epsilon",
            "High-grade Halo Gamma",
            "High-grade Halo Omega",
            "High-grade Hydra Alpha",
            "High-grade Hydra Beta",
            "High-grade Hydra Delta",
            "High-grade Hydra Epsilon",
            "High-grade Hydra Gamma",
            "High-grade Hydra Omega",
            "High-grade Jackal Alpha",
            "High-grade Jackal Beta",
            "High-grade Jackal Delta",
            "High-grade Jackal Epsilon",
            "High-grade Jackal Gamma",
            "High-grade Jackal Omega",
            "High-grade Mimesis Alpha",
            "High-grade Mimesis Beta",
            "High-grade Mimesis Delta",
            "High-grade Mimesis Epsilon",
            "High-grade Mimesis Gamma",
            "High-grade Mimesis Omega",
            "High-grade Nirvana Alpha",
            "High-grade Nirvana Beta",
            "High-grade Nirvana Delta",
            "High-grade Nirvana Epsilon",
            "High-grade Nirvana Gamma",
            "High-grade Nirvana Omega",
            "High-grade Rapture Alpha",
            "High-grade Rapture Beta",
            "High-grade Rapture Delta",
            "High-grade Rapture Epsilon",
            "High-grade Rapture Gamma",
            "High-grade Rapture Omega",
            "High-grade Savior Alpha",
            "High-grade Savior Beta",
            "High-grade Savior Delta",
            "High-grade Savior Epsilon",
            "High-grade Savior Gamma",
            "High-grade Savior Omega",
            "High-grade Snake Alpha",
            "High-grade Snake Beta",
            "High-grade Snake Delta",
            "High-grade Snake Epsilon",
            "High-grade Snake Gamma",
            "High-grade Snake Omega",
            "High-grade Spur Alpha",
            "High-grade Spur Beta",
            "High-grade Spur Delta",
            "High-grade Spur Epsilon",
            "High-grade Spur Gamma",
            "High-grade Spur Omega",
            "High-grade Talisman Alpha",
            "High-grade Talisman Beta",
            "High-grade Talisman Delta",
            "High-grade Talisman Epsilon",
            "High-grade Talisman Gamma",
            "High-grade Talisman Omega",
            "High-grade Talon Alpha",
            "High-grade Talon Beta",
            "High-grade Talon Delta",
            "High-grade Talon Epsilon",
            "High-grade Talon Gamma",
            "High-grade Talon Omega",
            "Imperial Navy Command Mindlink",
            "Imperial Navy Modified 'Noble' Implant",
            "Imperial Special Ops Field Enhancer - Standard",
            "Information Command Mindlink",
            "Inherent Implants 'Highwall' Mining MX-1001",
            "Inherent Implants 'Highwall' Mining MX-1003",
            "Inherent Implants 'Highwall' Mining MX-1005",
            "Inherent Implants 'Highwall' Mining Upgrades MU-1001",
            "Inherent Implants 'Highwall' Mining Upgrades MU-1003",
            "Inherent Implants 'Highwall' Mining Upgrades MU-1005",
            "Inherent Implants 'Lancer' Controlled Bursts CB-701",
            "Inherent Implants 'Lancer' Controlled Bursts CB-702",
            "Inherent Implants 'Lancer' Controlled Bursts CB-703",
            "Inherent Implants 'Lancer' Controlled Bursts CB-704",
            "Inherent Implants 'Lancer' Controlled Bursts CB-705",
            "Inherent Implants 'Lancer' Controlled Bursts CB-706",
            "Inherent Implants 'Lancer' Gunnery RF-901",
            "Inherent Implants 'Lancer' Gunnery RF-902",
            "Inherent Implants 'Lancer' Gunnery RF-903",
            "Inherent Implants 'Lancer' Gunnery RF-904",
            "Inherent Implants 'Lancer' Gunnery RF-905",
            "Inherent Implants 'Lancer' Gunnery RF-906",
            "Inherent Implants 'Lancer' Large Energy Turret LE-1001",
            "Inherent Implants 'Lancer' Large Energy Turret LE-1002",
            "Inherent Implants 'Lancer' Large Energy Turret LE-1003",
            "Inherent Implants 'Lancer' Large Energy Turret LE-1004",
            "Inherent Implants 'Lancer' Large Energy Turret LE-1005",
            "Inherent Implants 'Lancer' Large Energy Turret LE-1006",
            "Inherent Implants 'Lancer' Medium Energy Turret ME-801",
            "Inherent Implants 'Lancer' Medium Energy Turret ME-802",
            "Inherent Implants 'Lancer' Medium Energy Turret ME-803",
            "Inherent Implants 'Lancer' Medium Energy Turret ME-804",
            "Inherent Implants 'Lancer' Medium Energy Turret ME-805",
            "Inherent Implants 'Lancer' Medium Energy Turret ME-806",
            "Inherent Implants 'Lancer' Small Energy Turret SE-601",
            "Inherent Implants 'Lancer' Small Energy Turret SE-602",
            "Inherent Implants 'Lancer' Small Energy Turret SE-603",
            "Inherent Implants 'Lancer' Small Energy Turret SE-604",
            "Inherent Implants 'Lancer' Small Energy Turret SE-605",
            "Inherent Implants 'Lancer' Small Energy Turret SE-606",
            "Inherent Implants 'Noble' Hull Upgrades HG-1001",
            "Inherent Implants 'Noble' Hull Upgrades HG-1002",
            "Inherent Implants 'Noble' Hull Upgrades HG-1003",
            "Inherent Implants 'Noble' Hull Upgrades HG-1004",
            "Inherent Implants 'Noble' Hull Upgrades HG-1005",
            "Inherent Implants 'Noble' Hull Upgrades HG-1006",
            "Inherent Implants 'Noble' Hull Upgrades HG-1008",
            "Inherent Implants 'Noble' Mechanic MC-801",
            "Inherent Implants 'Noble' Mechanic MC-802",
            "Inherent Implants 'Noble' Mechanic MC-803",
            "Inherent Implants 'Noble' Mechanic MC-804",
            "Inherent Implants 'Noble' Mechanic MC-805",
            "Inherent Implants 'Noble' Mechanic MC-806",
            "Inherent Implants 'Noble' Remote Armor Repair Systems RA-701",
            "Inherent Implants 'Noble' Remote Armor Repair Systems RA-702",
            "Inherent Implants 'Noble' Remote Armor Repair Systems RA-703",
            "Inherent Implants 'Noble' Remote Armor Repair Systems RA-704",
            "Inherent Implants 'Noble' Remote Armor Repair Systems RA-705",
            "Inherent Implants 'Noble' Remote Armor Repair Systems RA-706",
            "Inherent Implants 'Noble' Repair Proficiency RP-901",
            "Inherent Implants 'Noble' Repair Proficiency RP-902",
            "Inherent Implants 'Noble' Repair Proficiency RP-903",
            "Inherent Implants 'Noble' Repair Proficiency RP-904",
            "Inherent Implants 'Noble' Repair Proficiency RP-905",
            "Inherent Implants 'Noble' Repair Proficiency RP-906",
            "Inherent Implants 'Noble' Repair Systems RS-601",
            "Inherent Implants 'Noble' Repair Systems RS-602",
            "Inherent Implants 'Noble' Repair Systems RS-603",
            "Inherent Implants 'Noble' Repair Systems RS-604",
            "Inherent Implants 'Noble' Repair Systems RS-605",
            "Inherent Implants 'Noble' Repair Systems RS-606",
            "Inherent Implants 'Squire' Capacitor Emission Systems ES-701",
            "Inherent Implants 'Squire' Capacitor Emission Systems ES-702",
            "Inherent Implants 'Squire' Capacitor Emission Systems ES-703",
            "Inherent Implants 'Squire' Capacitor Emission Systems ES-704",
            "Inherent Implants 'Squire' Capacitor Emission Systems ES-705",
            "Inherent Implants 'Squire' Capacitor Emission Systems ES-706",
            "Inherent Implants 'Squire' Capacitor Management EM-801",
            "Inherent Implants 'Squire' Capacitor Management EM-802",
            "Inherent Implants 'Squire' Capacitor Management EM-803",
            "Inherent Implants 'Squire' Capacitor Management EM-804",
            "Inherent Implants 'Squire' Capacitor Management EM-805",
            "Inherent Implants 'Squire' Capacitor Management EM-806",
            "Inherent Implants 'Squire' Capacitor Systems Operation EO-601",
            "Inherent Implants 'Squire' Capacitor Systems Operation EO-602",
            "Inherent Implants 'Squire' Capacitor Systems Operation EO-603",
            "Inherent Implants 'Squire' Capacitor Systems Operation EO-604",
            "Inherent Implants 'Squire' Capacitor Systems Operation EO-605",
            "Inherent Implants 'Squire' Capacitor Systems Operation EO-606",
            "Inherent Implants 'Squire' Energy Grid Upgrades EU-701",
            "Inherent Implants 'Squire' Energy Grid Upgrades EU-702",
            "Inherent Implants 'Squire' Energy Grid Upgrades EU-703",
            "Inherent Implants 'Squire' Energy Grid Upgrades EU-704",
            "Inherent Implants 'Squire' Energy Grid Upgrades EU-705",
            "Inherent Implants 'Squire' Energy Grid Upgrades EU-706",
            "Inherent Implants 'Squire' Energy Pulse Weapons EP-701",
            "Inherent Implants 'Squire' Energy Pulse Weapons EP-702",
            "Inherent Implants 'Squire' Energy Pulse Weapons EP-703",
            "Inherent Implants 'Squire' Energy Pulse Weapons EP-704",
            "Inherent Implants 'Squire' Energy Pulse Weapons EP-705",
            "Inherent Implants 'Squire' Energy Pulse Weapons EP-706",
            "Inherent Implants 'Squire' Power Grid Management EG-601",
            "Inherent Implants 'Squire' Power Grid Management EG-602",
            "Inherent Implants 'Squire' Power Grid Management EG-603",
            "Inherent Implants 'Squire' Power Grid Management EG-604",
            "Inherent Implants 'Squire' Power Grid Management EG-605",
            "Inherent Implants 'Squire' Power Grid Management EG-606",
            "Inherent Implants 'Yeti' Ice Harvesting IH-1001",
            "Inherent Implants 'Yeti' Ice Harvesting IH-1003",
            "Inherent Implants 'Yeti' Ice Harvesting IH-1005",
            "Inquest 'Eros' Stasis Webifier MR-803",
            "Inquest 'Eros' Stasis Webifier MR-805",
            "Inquest 'Eros' Stasis Webifier MR-807",
            "Inquest 'Hedone' Entanglement Optimizer WS-802",
            "Inquest 'Hedone' Entanglement Optimizer WS-803",
            "Inquest 'Hedone' Entanglement Optimizer WS-804",
            "Limited Cybernetic Subprocessor",
            "Limited Cybernetic Subprocessor - Beta",
            "Limited Memory Augmentation",
            "Limited Memory Augmentation - Beta",
            "Limited Neural Boost",
            "Limited Neural Boost - Beta",
            "Limited Ocular Filter",
            "Limited Ocular Filter - Beta",
            "Limited Social Adaptation Chip",
            "Limited Social Adaptation Chip - Beta",
            "Low-grade Amulet Alpha",
            "Low-grade Amulet Beta",
            "Low-grade Amulet Delta",
            "Low-grade Amulet Epsilon",
            "Low-grade Amulet Gamma",
            "Low-grade Amulet Omega",
            "Low-grade Asklepian Alpha",
            "Low-grade Asklepian Beta",
            "Low-grade Asklepian Delta",
            "Low-grade Asklepian Epsilon",
            "Low-grade Asklepian Gamma",
            "Low-grade Asklepian Omega",
            "Low-grade Centurion Alpha",
            "Low-grade Centurion Beta",
            "Low-grade Centurion Delta",
            "Low-grade Centurion Epsilon",
            "Low-grade Centurion Gamma",
            "Low-grade Centurion Omega",
            "Low-grade Crystal Alpha",
            "Low-grade Crystal Beta",
            "Low-grade Crystal Delta",
            "Low-grade Crystal Epsilon",
            "Low-grade Crystal Gamma",
            "Low-grade Crystal Omega",
            "Low-grade Edge Alpha",
            "Low-grade Edge Beta",
            "Low-grade Edge Delta",
            "Low-grade Edge Epsilon",
            "Low-grade Edge Gamma",
            "Low-grade Edge Omega",
            "Low-grade Grail Alpha",
            "Low-grade Grail Beta",
            "Low-grade Grail Delta",
            "Low-grade Grail Epsilon",
            "Low-grade Grail Gamma",
            "Low-grade Grail Omega",
            "Low-grade Halo Alpha",
            "Low-grade Halo Beta",
            "Low-grade Halo Delta",
            "Low-grade Halo Epsilon",
            "Low-grade Halo Gamma",
            "Low-grade Halo Omega",
            "Low-grade Harvest Alpha",
            "Low-grade Harvest Beta",
            "Low-grade Harvest Delta",
            "Low-grade Harvest Epsilon",
            "Low-grade Harvest Gamma",
            "Low-grade Harvest Omega",
            "Low-grade Hydra Alpha",
            "Low-grade Hydra Beta",
            "Low-grade Hydra Delta",
            "Low-grade Hydra Epsilon",
            "Low-grade Hydra Gamma",
            "Low-grade Hydra Omega",
            "Low-grade Jackal Alpha",
            "Low-grade Jackal Beta",
            "Low-grade Jackal Delta",
            "Low-grade Jackal Epsilon",
            "Low-grade Jackal Gamma",
            "Low-grade Jackal Omega",
            "Low-grade Mimesis Alpha",
            "Low-grade Mimesis Beta",
            "Low-grade Mimesis Delta",
            "Low-grade Mimesis Epsilon",
            "Low-grade Mimesis Gamma",
            "Low-grade Mimesis Omega",
            "Low-grade Nirvana Alpha",
            "Low-grade Nirvana Beta",
            "Low-grade Nirvana Delta",
            "Low-grade Nirvana Epsilon",
            "Low-grade Nirvana Gamma",
            "Low-grade Nirvana Omega",
            "Low-grade Nomad Alpha",
            "Low-grade Nomad Beta",
            "Low-grade Nomad Delta",
            "Low-grade Nomad Epsilon",
            "Low-grade Nomad Gamma",
            "Low-grade Nomad Omega",
            "Low-grade Rapture Alpha",
            "Low-grade Rapture Beta",
            "Low-grade Rapture Delta",
            "Low-grade Rapture Epsilon",
            "Low-grade Rapture Gamma",
            "Low-grade Rapture Omega",
            "Low-grade Savior Alpha",
            "Low-grade Savior Beta",
            "Low-grade Savior Delta",
            "Low-grade Savior Epsilon",
            "Low-grade Savior Gamma",
            "Low-grade Savior Omega",
            "Low-grade Snake Alpha",
            "Low-grade Snake Beta",
            "Low-grade Snake Delta",
            "Low-grade Snake Epsilon",
            "Low-grade Snake Gamma",
            "Low-grade Snake Omega",
            "Low-grade Spur Alpha",
            "Low-grade Spur Beta",
            "Low-grade Spur Delta",
            "Low-grade Spur Epsilon",
            "Low-grade Spur Gamma",
            "Low-grade Spur Omega",
            "Low-grade Talisman Alpha",
            "Low-grade Talisman Beta",
            "Low-grade Talisman Delta",
            "Low-grade Talisman Epsilon",
            "Low-grade Talisman Gamma",
            "Low-grade Talisman Omega",
            "Low-grade Talon Alpha",
            "Low-grade Talon Beta",
            "Low-grade Talon Delta",
            "Low-grade Talon Epsilon",
            "Low-grade Talon Gamma",
            "Low-grade Talon Omega",
            "Low-grade Virtue Alpha",
            "Low-grade Virtue Beta",
            "Low-grade Virtue Delta",
            "Low-grade Virtue Epsilon",
            "Low-grade Virtue Gamma",
            "Low-grade Virtue Omega",
            "Memory Augmentation - Basic",
            "Memory Augmentation - Improved",
            "Memory Augmentation - Standard",
            "Michi's Excavation Augmentor",
            "Mid-grade Amulet Alpha",
            "Mid-grade Amulet Beta",
            "Mid-grade Amulet Delta",
            "Mid-grade Amulet Epsilon",
            "Mid-grade Amulet Gamma",
            "Mid-grade Amulet Omega",
            "Mid-grade Ascendancy Alpha",
            "Mid-grade Ascendancy Beta",
            "Mid-grade Ascendancy Delta",
            "Mid-grade Ascendancy Epsilon",
            "Mid-grade Ascendancy Gamma",
            "Mid-grade Ascendancy Omega",
            "Mid-grade Asklepian Alpha",
            "Mid-grade Asklepian Beta",
            "Mid-grade Asklepian Delta",
            "Mid-grade Asklepian Epsilon",
            "Mid-grade Asklepian Gamma",
            "Mid-grade Asklepian Omega",
            "Mid-grade Centurion Alpha",
            "Mid-grade Centurion Beta",
            "Mid-grade Centurion Delta",
            "Mid-grade Centurion Epsilon",
            "Mid-grade Centurion Gamma",
            "Mid-grade Centurion Omega",
            "Mid-grade Crystal Alpha",
            "Mid-grade Crystal Beta",
            "Mid-grade Crystal Delta",
            "Mid-grade Crystal Epsilon",
            "Mid-grade Crystal Gamma",
            "Mid-grade Crystal Omega",
            "Mid-grade Edge Alpha",
            "Mid-grade Edge Delta",
            "Mid-grade Edge Omega",
            "Mid-grade Halo Beta",
            "Mid-grade Halo Delta",
            "Mid-grade Halo Epsilon",
            "Mid-grade Halo Gamma",
            "Mid-grade Halo Omega",
            "Mid-grade Harvest Alpha",
            "Mid-grade Harvest Beta",
            "Mid-grade Harvest Delta",
            "Mid-grade Harvest Epsilon",
            "Mid-grade Harvest Gamma",
            "Mid-grade Harvest Omega",
            "Mid-grade Hydra Alpha",
            "Mid-grade Hydra Beta",
            "Mid-grade Hydra Delta",
            "Mid-grade Hydra Epsilon",
            "Mid-grade Hydra Gamma",
            "Mid-grade Hydra Omega",
            "Mid-grade Mimesis Alpha",
            "Mid-grade Mimesis Beta",
            "Mid-grade Mimesis Delta",
            "Mid-grade Mimesis Epsilon",
            "Mid-grade Mimesis Gamma",
            "Mid-grade Mimesis Omega",
            "Mid-grade Nirvana Alpha",
            "Mid-grade Nirvana Beta",
            "Mid-grade Nirvana Delta",
            "Mid-grade Nirvana Epsilon",
            "Mid-grade Nirvana Gamma",
            "Mid-grade Nirvana Omega",
            "Mid-grade Nomad Alpha",
            "Mid-grade Nomad Beta",
            "Mid-grade Nomad Delta",
            "Mid-grade Nomad Epsilon",
            "Mid-grade Nomad Gamma",
            "Mid-grade Nomad Omega",
            "Mid-grade Rapture Alpha",
            "Mid-grade Rapture Beta",
            "Mid-grade Rapture Delta",
            "Mid-grade Rapture Epsilon",
            "Mid-grade Rapture Gamma",
            "Mid-grade Rapture Omega",
            "Mid-grade Savior Alpha",
            "Mid-grade Savior Beta",
            "Mid-grade Savior Delta",
            "Mid-grade Savior Epsilon",
            "Mid-grade Savior Gamma",
            "Mid-grade Savior Omega",
            "Mid-grade Snake Alpha",
            "Mid-grade Snake Beta",
            "Mid-grade Snake Delta",
            "Mid-grade Snake Epsilon",
            "Mid-grade Snake Gamma",
            "Mid-grade Snake Omega",
            "Mid-grade Talisman Alpha",
            "Mid-grade Talisman Beta",
            "Mid-grade Talisman Delta",
            "Mid-grade Talisman Epsilon",
            "Mid-grade Talisman Gamma",
            "Mid-grade Talisman Omega",
            "Mid-grade Virtue Alpha",
            "Mid-grade Virtue Beta",
            "Mid-grade Virtue Delta",
            "Mid-grade Virtue Epsilon",
            "Mid-grade Virtue Gamma",
            "Mid-grade Virtue Omega",
            "Mining Foreman Mindlink",
            "Neural 'Source' Boost",
            "Neural Boost - Improved",
            "Neural Boost - Standard",
            "Neural Lace 'Blackglass' Net Intrusion 920-40",
            "Numon Family Heirloom",
            "Ocular Filter - Basic",
            "Ocular Filter - Improved",
            "Ocular Filter - Standard",
            "Ogdin's Eye Coordination Enhancer",
            "ORE Mining Director Mindlink",
            "Overmind 'Goliath' Drone Tuner T25-10S",
            "Overmind 'Hawkmoth' Drone Tuner S10-25T",
            "Pashan's Turret Customization Mindlink",
            "Pashan's Turret Handling Mindlink",
            "Poteque 'Prospector' Archaeology AC-905",
            "Poteque 'Prospector' Astrometric Acquisition AQ-702",
            "Poteque 'Prospector' Astrometric Acquisition AQ-706",
            "Poteque 'Prospector' Astrometric Acquisition AQ-710",
            "Poteque 'Prospector' Astrometric Pinpointing AP-602",
            "Poteque 'Prospector' Astrometric Pinpointing AP-606",
            "Poteque 'Prospector' Astrometric Pinpointing AP-610",
            "Poteque 'Prospector' Astrometric Rangefinding AR-802",
            "Poteque 'Prospector' Astrometric Rangefinding AR-806",
            "Poteque 'Prospector' Astrometric Rangefinding AR-810",
            "Poteque 'Prospector' Environmental Analysis EY-1005",
            "Poteque 'Prospector' Hacking HC-905",
            "Poteque 'Prospector' Salvaging SV-905",
            "Republic Fleet Command Mindlink",
            "Republic Special Ops Field Enhancer - Gamma",
            "Sansha Modified 'Gnome' Implant",
            "Shaqil's Speed Enhancer",
            "Shield Command Mindlink",
            "Skirmish Command Mindlink",
            "Social Adaptation Chip - Basic",
            "Social Adaptation Chip - Improved",
            "Social Adaptation Chip - Standard",
            "Whelan Machorin's Ballistic Smartlink",
            "Zainou 'Beancounter' Industry BX-801",
            "Zainou 'Beancounter' Industry BX-802",
            "Zainou 'Beancounter' Industry BX-804",
            "Zainou 'Beancounter' Metallurgy MY-701",
            "Zainou 'Beancounter' Metallurgy MY-703",
            "Zainou 'Beancounter' Metallurgy MY-705",
            "Zainou 'Beancounter' Reprocessing RX-801",
            "Zainou 'Beancounter' Reprocessing RX-802",
            "Zainou 'Beancounter' Reprocessing RX-804",
            "Zainou 'Beancounter' Research RR-601",
            "Zainou 'Beancounter' Research RR-603",
            "Zainou 'Beancounter' Research RR-605",
            "Zainou 'Beancounter' Science SC-801",
            "Zainou 'Beancounter' Science SC-805",
            "Zainou 'Deadeye' Guided Missile Precision GP-801",
            "Zainou 'Deadeye' Guided Missile Precision GP-802",
            "Zainou 'Deadeye' Guided Missile Precision GP-803",
            "Zainou 'Deadeye' Guided Missile Precision GP-804",
            "Zainou 'Deadeye' Guided Missile Precision GP-805",
            "Zainou 'Deadeye' Guided Missile Precision GP-806",
            "Zainou 'Deadeye' Large Hybrid Turret LH-1001",
            "Zainou 'Deadeye' Large Hybrid Turret LH-1002",
            "Zainou 'Deadeye' Large Hybrid Turret LH-1003",
            "Zainou 'Deadeye' Large Hybrid Turret LH-1004",
            "Zainou 'Deadeye' Large Hybrid Turret LH-1005",
            "Zainou 'Deadeye' Large Hybrid Turret LH-1006",
            "Zainou 'Deadeye' Medium Hybrid Turret MH-801",
            "Zainou 'Deadeye' Medium Hybrid Turret MH-802",
            "Zainou 'Deadeye' Medium Hybrid Turret MH-803",
            "Zainou 'Deadeye' Medium Hybrid Turret MH-804",
            "Zainou 'Deadeye' Medium Hybrid Turret MH-805",
            "Zainou 'Deadeye' Medium Hybrid Turret MH-806",
            "Zainou 'Deadeye' Missile Bombardment MB-701",
            "Zainou 'Deadeye' Missile Bombardment MB-702",
            "Zainou 'Deadeye' Missile Bombardment MB-703",
            "Zainou 'Deadeye' Missile Bombardment MB-704",
            "Zainou 'Deadeye' Missile Bombardment MB-705",
            "Zainou 'Deadeye' Missile Bombardment MB-706",
            "Zainou 'Deadeye' Missile Projection MP-701",
            "Zainou 'Deadeye' Missile Projection MP-702",
            "Zainou 'Deadeye' Missile Projection MP-703",
            "Zainou 'Deadeye' Missile Projection MP-704",
            "Zainou 'Deadeye' Missile Projection MP-705",
            "Zainou 'Deadeye' Missile Projection MP-706",
            "Zainou 'Deadeye' Rapid Launch RL-1001",
            "Zainou 'Deadeye' Rapid Launch RL-1002",
            "Zainou 'Deadeye' Rapid Launch RL-1003",
            "Zainou 'Deadeye' Rapid Launch RL-1004",
            "Zainou 'Deadeye' Rapid Launch RL-1005",
            "Zainou 'Deadeye' Rapid Launch RL-1006",
            "Zainou 'Deadeye' Sharpshooter ST-901",
            "Zainou 'Deadeye' Sharpshooter ST-902",
            "Zainou 'Deadeye' Sharpshooter ST-903",
            "Zainou 'Deadeye' Sharpshooter ST-904",
            "Zainou 'Deadeye' Sharpshooter ST-905",
            "Zainou 'Deadeye' Sharpshooter ST-906",
            "Zainou 'Deadeye' Small Hybrid Turret SH-601",
            "Zainou 'Deadeye' Small Hybrid Turret SH-602",
            "Zainou 'Deadeye' Small Hybrid Turret SH-603",
            "Zainou 'Deadeye' Small Hybrid Turret SH-604",
            "Zainou 'Deadeye' Small Hybrid Turret SH-605",
            "Zainou 'Deadeye' Small Hybrid Turret SH-606",
            "Zainou 'Deadeye' Target Navigation Prediction TN-901",
            "Zainou 'Deadeye' Target Navigation Prediction TN-902",
            "Zainou 'Deadeye' Target Navigation Prediction TN-903",
            "Zainou 'Deadeye' Target Navigation Prediction TN-904",
            "Zainou 'Deadeye' Target Navigation Prediction TN-905",
            "Zainou 'Deadeye' Target Navigation Prediction TN-906",
            "Zainou 'Deadeye' Trajectory Analysis TA-701",
            "Zainou 'Deadeye' Trajectory Analysis TA-702",
            "Zainou 'Deadeye' Trajectory Analysis TA-703",
            "Zainou 'Deadeye' Trajectory Analysis TA-704",
            "Zainou 'Deadeye' Trajectory Analysis TA-705",
            "Zainou 'Deadeye' Trajectory Analysis TA-706",
            "Zainou 'Gnome' Launcher CPU Efficiency LE-601",
            "Zainou 'Gnome' Launcher CPU Efficiency LE-602",
            "Zainou 'Gnome' Launcher CPU Efficiency LE-603",
            "Zainou 'Gnome' Launcher CPU Efficiency LE-604",
            "Zainou 'Gnome' Launcher CPU Efficiency LE-605",
            "Zainou 'Gnome' Launcher CPU Efficiency LE-606",
            "Zainou 'Gnome' Shield Emission Systems SE-801",
            "Zainou 'Gnome' Shield Emission Systems SE-802",
            "Zainou 'Gnome' Shield Emission Systems SE-803",
            "Zainou 'Gnome' Shield Emission Systems SE-804",
            "Zainou 'Gnome' Shield Emission Systems SE-805",
            "Zainou 'Gnome' Shield Emission Systems SE-806",
            "Zainou 'Gnome' Shield Management SM-701",
            "Zainou 'Gnome' Shield Management SM-702",
            "Zainou 'Gnome' Shield Management SM-703",
            "Zainou 'Gnome' Shield Management SM-704",
            "Zainou 'Gnome' Shield Management SM-705",
            "Zainou 'Gnome' Shield Management SM-706",
            "Zainou 'Gnome' Shield Operation SP-901",
            "Zainou 'Gnome' Shield Operation SP-902",
            "Zainou 'Gnome' Shield Operation SP-903",
            "Zainou 'Gnome' Shield Operation SP-904",
            "Zainou 'Gnome' Shield Operation SP-905",
            "Zainou 'Gnome' Shield Operation SP-906",
            "Zainou 'Gnome' Shield Upgrades SU-601",
            "Zainou 'Gnome' Shield Upgrades SU-602",
            "Zainou 'Gnome' Shield Upgrades SU-603",
            "Zainou 'Gnome' Shield Upgrades SU-604",
            "Zainou 'Gnome' Shield Upgrades SU-605",
            "Zainou 'Gnome' Shield Upgrades SU-606",
            "Zainou 'Gnome' Weapon Upgrades WU-1001",
            "Zainou 'Gnome' Weapon Upgrades WU-1002",
            "Zainou 'Gnome' Weapon Upgrades WU-1003",
            "Zainou 'Gnome' Weapon Upgrades WU-1004",
            "Zainou 'Gnome' Weapon Upgrades WU-1005",
            "Zainou 'Gnome' Weapon Upgrades WU-1006",
            "Zainou 'Gypsy' CPU Management EE-601",
            "Zainou 'Gypsy' CPU Management EE-602",
            "Zainou 'Gypsy' CPU Management EE-603",
            "Zainou 'Gypsy' CPU Management EE-604",
            "Zainou 'Gypsy' CPU Management EE-605",
            "Zainou 'Gypsy' CPU Management EE-606",
            "Zainou 'Gypsy' Electronic Warfare EW-901",
            "Zainou 'Gypsy' Electronic Warfare EW-902",
            "Zainou 'Gypsy' Electronic Warfare EW-903",
            "Zainou 'Gypsy' Electronic Warfare EW-904",
            "Zainou 'Gypsy' Electronic Warfare EW-905",
            "Zainou 'Gypsy' Electronic Warfare EW-906",
            "Zainou 'Gypsy' Electronics Upgrades EU-601",
            "Zainou 'Gypsy' Electronics Upgrades EU-602",
            "Zainou 'Gypsy' Electronics Upgrades EU-603",
            "Zainou 'Gypsy' Electronics Upgrades EU-604",
            "Zainou 'Gypsy' Electronics Upgrades EU-605",
            "Zainou 'Gypsy' Electronics Upgrades EU-606",
            "Zainou 'Gypsy' Long Range Targeting LT-801",
            "Zainou 'Gypsy' Long Range Targeting LT-802",
            "Zainou 'Gypsy' Long Range Targeting LT-803",
            "Zainou 'Gypsy' Long Range Targeting LT-804",
            "Zainou 'Gypsy' Long Range Targeting LT-805",
            "Zainou 'Gypsy' Long Range Targeting LT-806",
            "Zainou 'Gypsy' Propulsion Jamming PJ-801",
            "Zainou 'Gypsy' Propulsion Jamming PJ-802",
            "Zainou 'Gypsy' Propulsion Jamming PJ-803",
            "Zainou 'Gypsy' Propulsion Jamming PJ-804",
            "Zainou 'Gypsy' Propulsion Jamming PJ-805",
            "Zainou 'Gypsy' Propulsion Jamming PJ-806",
            "Zainou 'Gypsy' Sensor Linking SL-901",
            "Zainou 'Gypsy' Sensor Linking SL-902",
            "Zainou 'Gypsy' Sensor Linking SL-903",
            "Zainou 'Gypsy' Sensor Linking SL-904",
            "Zainou 'Gypsy' Sensor Linking SL-905",
            "Zainou 'Gypsy' Sensor Linking SL-906",
            "Zainou 'Gypsy' Signature Analysis SA-701",
            "Zainou 'Gypsy' Signature Analysis SA-702",
            "Zainou 'Gypsy' Signature Analysis SA-703",
            "Zainou 'Gypsy' Signature Analysis SA-704",
            "Zainou 'Gypsy' Signature Analysis SA-705",
            "Zainou 'Gypsy' Signature Analysis SA-706",
            "Zainou 'Gypsy' Target Painting TG-901",
            "Zainou 'Gypsy' Target Painting TG-902",
            "Zainou 'Gypsy' Target Painting TG-903",
            "Zainou 'Gypsy' Target Painting TG-905",
            "Zainou 'Gypsy' Target Painting TG-906",
            "Zainou 'Gypsy' Weapon Disruption WD-901",
            "Zainou 'Gypsy' Weapon Disruption WD-902",
            "Zainou 'Gypsy' Weapon Disruption WD-903",
            "Zainou 'Gypsy' Weapon Disruption WD-904",
            "Zainou 'Gypsy' Weapon Disruption WD-905",
            "Zainou 'Gypsy' Weapon Disruption WD-906",
            "Zainou 'Snapshot' Auto-Targeting Explosion Radius FR-1001",
            "Zainou 'Snapshot' Auto-Targeting Explosion Radius FR-1002",
            "Zainou 'Snapshot' Auto-Targeting Explosion Radius FR-1003",
            "Zainou 'Snapshot' Auto-Targeting Explosion Radius FR-1004",
            "Zainou 'Snapshot' Auto-Targeting Explosion Radius FR-1005",
            "Zainou 'Snapshot' Auto-Targeting Explosion Radius FR-1006",
            "Zainou 'Snapshot' Cruise Missiles CM-601",
            "Zainou 'Snapshot' Cruise Missiles CM-602",
            "Zainou 'Snapshot' Cruise Missiles CM-603",
            "Zainou 'Snapshot' Cruise Missiles CM-604",
            "Zainou 'Snapshot' Cruise Missiles CM-605",
            "Zainou 'Snapshot' Cruise Missiles CM-606",
            "Zainou 'Snapshot' Defender Missiles DM-801",
            "Zainou 'Snapshot' Defender Missiles DM-802",
            "Zainou 'Snapshot' Defender Missiles DM-803",
            "Zainou 'Snapshot' Defender Missiles DM-804",
            "Zainou 'Snapshot' Defender Missiles DM-806",
            "Zainou 'Snapshot' Heavy Assault Missiles AM-702",
            "Zainou 'Snapshot' Heavy Assault Missiles AM-703",
            "Zainou 'Snapshot' Heavy Assault Missiles AM-704",
            "Zainou 'Snapshot' Heavy Assault Missiles AM-705",
            "Zainou 'Snapshot' Heavy Assault Missiles AM-706",
            "Zainou 'Snapshot' Heavy Missiles HM-701",
            "Zainou 'Snapshot' Heavy Missiles HM-702",
            "Zainou 'Snapshot' Heavy Missiles HM-703",
            "Zainou 'Snapshot' Heavy Missiles HM-704",
            "Zainou 'Snapshot' Heavy Missiles HM-705",
            "Zainou 'Snapshot' Heavy Missiles HM-706",
            "Zainou 'Snapshot' Light Missiles LM-901",
            "Zainou 'Snapshot' Light Missiles LM-902",
            "Zainou 'Snapshot' Light Missiles LM-903",
            "Zainou 'Snapshot' Light Missiles LM-904",
            "Zainou 'Snapshot' Light Missiles LM-905",
            "Zainou 'Snapshot' Light Missiles LM-906",
            "Zainou 'Snapshot' Rockets RD-901",
            "Zainou 'Snapshot' Rockets RD-902",
            "Zainou 'Snapshot' Rockets RD-903",
            "Zainou 'Snapshot' Rockets RD-904",
            "Zainou 'Snapshot' Rockets RD-905",
            "Zainou 'Snapshot' Rockets RD-906",
            "Zainou 'Snapshot' Torpedoes TD-601",
            "Zainou 'Snapshot' Torpedoes TD-602",
            "Zainou 'Snapshot' Torpedoes TD-603",
            "Zainou 'Snapshot' Torpedoes TD-604",
            "Zainou 'Snapshot' Torpedoes TD-605",
            "Zainou 'Snapshot' Torpedoes TD-606",
            "Zor's Custom Navigation Hyper-Link",
            "Zor's Custom Navigation Link",

            "Harvest Webifier Booster II",
            "Harvest Webifier Booster III"

    };

    @FindBy(how = How.XPATH, using = "//a[text()='View All Fits']") private WebElement allFitsLink;
    @FindBy(how = How.XPATH, using = "//button[@id='buyAllButton']") private WebElement buyAllButton;

    @FindAll(@FindBy(how = How.XPATH, using = "//dl/dd/ul/li")) private List<WebElement> doctrineList;


    public BuyAllPage(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    public BuyAllPage(WebDriver driver, String url) {
        this.driver=driver;
        driver.get(java.util.Objects.requireNonNull(url));
        PageFactory.initElements(driver, this);
    }

    public void waitPageLoaded() throws Throwable {
        //System.out.println("'" + driver.getTitle() + "'");
        pageActionCode.waitPageTitleHasLoadedContains (driver, " Fitting - INIT. Auth");
        //pageActionCode.waitElementIsClickable(driver,allFitsLink); //new page
    }

    public void getLastUpdated(FitObject fitObject) {

        try {
            Document resultsPage = Jsoup.parse(java.util.Objects.requireNonNull(driver.getPageSource()));
            Elements allDDs = resultsPage.selectXpath("//dd");
            String lastUpdated = allDDs.get(2).text();
            fitObject.setLastUpdated(lastUpdated);

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
            Date dateBefore = sdf.parse(lastUpdated);
            Date dateAfter = sdf.parse(sdf.format(new Date()));
            long timeDiff = Math.abs(dateAfter.getTime() - dateBefore.getTime());
            long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
            if(daysDiff < 61) {
                fitObject.setLastUpdatedDays(String.valueOf("Warning: " + daysDiff));
            } else {
                fitObject.setLastUpdatedDays("");
            }

        } catch(Exception ex){
            System.out.println("Days since updated failed for: " + fitObject.getFittingURL());
        }
    }

    public void getAllModules(FitObject fitObject) {

        String clipBoard = "";
        while(clipBoard.length()==0 || clipBoard.equals("Empty")) {
            setClipboardContent("Empty");
            clickBuyAll();
            clipBoard = getClipBoard(fitObject);
        }
        String modules[] = clipBoard.split("\n");
        ArrayList<ModuleObject> fitModules = new ArrayList<>();

        for(int i=1; i<modules.length; i++) {    //Don't add ship
            Pattern p = Pattern.compile(" x[0-9]+");  // insert your pattern here
            Matcher m = p.matcher(modules[i]);
            int moduleCount = 1;
            if (m.find()) {
                int position = m.start() + 2;
                moduleCount = Integer.parseInt(modules[i].substring(position));
            }
            modules[i] = modules[i].replaceAll(" x[0-9]+", "");
            if(moduleIsAllowed(modules[i])) {
                ModuleObject moduleObject = new ModuleObject();
                if(modules[i].equals("Imperial Navy Medium EMP Smartbomb")){
                    modules[i] = "Dark Blood Medium EMP Smartbomb"; //Swap Imperial for Dark Blood as Imperial is always sold out
                    System.out.println("Swapped Imp bomb for Dark Blood");
                }
                moduleObject.setModuleName(modules[i]);
                moduleObject.setQuantity(moduleCount);
                fitModules.add(moduleObject);
            }
        }
        fitObject.setModules(fitModules);
    }

    /*
    public void getNoDoctrineFits(FitObject fitObject){
        Document resultsPage = Jsoup.parse(driver.getPageSource());
        Elements allDoctrines = resultsPage.selectXpath("//a[contains(@href,'/fittings/doctrine')]");
        if(allDoctrines.size()==0){
            String fitUrl = fitObject.getFittingURL();
            if(isFitURLAllowed(fitUrl)) {
                //System.out.println(fitObject.getDoctrineName());
                //System.out.println(fitObject.getShipType());
                //System.out.println(fitUrl);
                //System.out.println("-------------------------------------------------------------");
            }
        }
    }


    private boolean isFitURLAllowed(String fitUrl) {
        String[] notAllowed = {
                "/1/",
                "/121/",
                "/171/",
                "/172/",
                "/173/",
                "/175/",
                "/176/",
                "/177/",
                "/197/",
                "/198/",
                "/217/",
                "/232/",
                "/233/",
                "/236/",
                "/239/",
                "/250/",
                "/252/",
                "/314/",
                "/454/",
                "/577/",
                "/590/",
                "/651/",
                "/672/",
                "/692/",
                "/693/",
                "/737/",
                "/785/",
                "/786/"
        };
        boolean returnVal = true;
        for(int i=0; i< notAllowed.length; i++){
            if(fitUrl.contains(notAllowed[i])){
                returnVal = false;
                break;
            }
        }
        return returnVal;
    }
    */

    private void clickBuyAll(){
        buyAllButton.click();
    }

    public static void setClipboardContent(String text) {
        try {
            Thread.sleep(100);
            CLIPBOARD.setContents(new StringSelection(text), null);
        } catch(Exception ex) {
            System.out.println("Failed to clear clipboard. Trying once more.");
            try {
                Thread.sleep(200);
            } catch(Exception ex2){}
            CLIPBOARD.setContents(new StringSelection(text), null);
        }
    }

    public String getClipBoard(FitObject fitObject){
        try {
            Thread.sleep(100);
            return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (HeadlessException e) {
        } catch (UnsupportedFlavorException e) {
        } catch (IOException e) {
        } catch (Exception e){
            System.out.println("Failed on: " + fitObject.getShipType() + " - " + fitObject.getFittingURL() + " Trying again.");
        }
        return "";
    }

    public static String getAllModulesString(ArrayList<FitObject> allFits) {
        String allModuleTypes = "";
        ArrayList<String> allModulesList = new ArrayList<>();

        for(FitObject fit : allFits){
            ArrayList<ModuleObject> allModules = fit.getModules();
            if(allModules != null) {
                for (ModuleObject module : allModules) {
                    String moduleName = module.getModuleName();
                    boolean foundMatch = false;
                    for (String existingModule : allModulesList) {
                        if (moduleName.equals(existingModule)) {
                            foundMatch = true;
                            break;
                        }
                    }
                    if (!foundMatch) {
                        allModulesList.add(moduleName);
                    }
                }
            }
        }

        allModulesList = addOtherModules(allModulesList);

        Collections.sort(allModulesList);
        for(String existingModule : allModulesList) {
            allModuleTypes += existingModule + "\r\n";
        }

        return allModuleTypes;
    }

    private static ArrayList<String> addOtherModules(ArrayList<String> allModulesList) {

        allModulesList.add("Armored Command Mindlink");
        allModulesList.add("Information Command Mindlink");
        allModulesList.add("Shield Command Mindlink");
        allModulesList.add("Skirmish Command Mindlink");

        allModulesList.add("Caldari Navy Command Mindlink");
        allModulesList.add("Federation Navy Command Mindlink");
        allModulesList.add("Imperial Navy Command Mindlink");
        allModulesList.add("Republic Fleet Command Mindlink");
        allModulesList.add("Guri Malakim Command Mindlink");

        allModulesList.add("Mid-grade Amulet Alpha");
        allModulesList.add("Mid-grade Amulet Beta");
        allModulesList.add("Mid-grade Amulet Gamma");
        allModulesList.add("Mid-grade Amulet Delta");
        allModulesList.add("Mid-grade Amulet Epsilon");
        allModulesList.add("Mid-grade Amulet Omega");
        allModulesList.add("Inherent Implants 'Noble' Hull Upgrades HG-1003");

        allModulesList.add("Mobile Cynosural Inhibitor");

        return allModulesList;
    }

    private boolean moduleIsAllowed(String module) {
        boolean moduleAllowed = true;
        for(int i=0; i<moduleBlackList.length; i++){
            if(module.toUpperCase().equals(moduleBlackList[i].toUpperCase())){
                moduleAllowed = false;
                break;
            }
        }
        return moduleAllowed;
    }





}

