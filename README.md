# SwagLab-Automation

Selenium WebDriver + TestNG automation framework for [Swag Labs](https://www.saucedemo.com/),
built with the Page Object Model (POM) and data-driven testing using JSON.

## Tech Stack
- Java
- Selenium WebDriver
- TestNG
- Maven
- Jackson (JSON test data)

## Project Structure
- `src/main/java/pages` - Page Objects (LoginPage, InventoryPage)
- `src/main/java/utils` - Utilities (JSON data reader)
- `src/test/java/base` - BaseTest (driver setup/teardown)
- `src/test/java/tests` - Test classes
- `testData/testData.json` - Test data

## Test Coverage
- Valid login
- Invalid login
- Login without password
- Inventory page verification

## How to Run
```
mvn clean test
```
