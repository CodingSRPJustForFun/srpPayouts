# SRP Payouts

A Java application that collects ship fitting and market information and writes SRP payout CSV files.

## Requirements

- Java 25 JDK
- Apache Maven
- Google Chrome
- `data/invTypes.csv` (the application input file; download it from the [Fuzzwork EVE data CSV directory](https://www.fuzzwork.co.uk/dump/latest/csv/invTypes.csv))

## Configure

Copy `config.properties.example` to `config.properties`. Adjust the Chrome path or other settings if needed. Keep local configuration, input data, browser profiles, logs, and generated output out of source control.

## Build and run on Windows

1. Set `JAVA_HOME` to your Java 25 JDK installation and ensure Maven is available on `PATH`.
2. Download `invTypes.csv` from the [Fuzzwork EVE data CSV directory](https://www.fuzzwork.co.uk/dump/latest/csv/invTypes.csv) and put it in the `data` directory.
3. Run `Build srphelper.bat`.
4. Run `Run srphelper.bat`.

The generated CSV files are written to `output`.
