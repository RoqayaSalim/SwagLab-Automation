# SwagLab Test Automation Framework

UI test automation framework for [Swag Labs (saucedemo.com)](https://www.saucedemo.com), built with **Java, Selenium WebDriver, TestNG and Maven**, using the **Page Object Model** and **data-driven testing** with JSON.

## Tech Stack

| Area | Tool |
|---|---|
| Language | Java |
| UI automation | Selenium WebDriver 4.35 |
| Test runner | TestNG 7.10 |
| Build tool | Maven |
| Test data | JSON (Jackson) |
| Reporting | Allure Report |
| Design pattern | Page Object Model (POM) |

## Project Structure

```
SwagLab
├── src
│   ├── main/java
│   │   ├── pages          # Page Objects (LoginPage, InventoryPage, CartPage, CheckoutPage)
│   │   └── utils          # DataDriven (JSON reader), ReportListener
│   └── test/java
│       ├── base           # BaseTest (driver setup / teardown)
│       └── tests          # Test classes (LoginTest, InventoryTest, CartTest, CheckoutTest)
├── testData               # testData.json (credentials, products, checkout info)
├── testrun.xml            # TestNG suite
└── pom.xml
```

## Test Coverage

**Login**
- Login flow using credentials read from `testData.json`

**Inventory page**
- Page title, cart icon and number of products (6)
- Social media links (Twitter/X, Facebook, LinkedIn) open the correct site in a new tab

**Cart**
- Cart is empty after login
- Add 3 products (names read from JSON) and verify they appear in the same order
- Remove one product and verify the remaining items
- Cart state after logout and login again

**Checkout**
- Cart total: sum of the 3 prices read from the Inventory page equals the "Item total" at checkout
- Checkout with an empty cart (see Known Findings)

## Data-Driven Testing

All test data lives in `testData/testData.json`; nothing is hardcoded in the tests:

```json
{
  "validLogin": { "username": "standard_user", "password": "secret_sauce" },
  "cartProducts": [
    "Sauce Labs Backpack",
    "Sauce Labs Bolt T-Shirt",
    "Sauce Labs Onesie"
  ],
  "checkoutInfo": { "firstName": "Roqaya", "lastName": "Salim", "postalCode": "12345" }
}
```

## Known Findings

- **Checkout with an empty cart is not blocked.** Swag Labs lets the user continue through Checkout and finish the order with zero items, and shows "Thank you for your order!". `CheckoutTest` expects an error or a blocked flow, so it **fails on purpose** to document this defect.

## Getting Started

**Prerequisites:** JDK 17 or higher, Maven, Google Chrome. (Allure CLI is optional, for viewing reports.)

```bash
# Clone
git clone https://github.com/RoqayaSalim/SwagLab.git
cd SwagLab

# Run all tests
mvn clean test
```

You can also run `testrun.xml` directly from IntelliJ IDEA (right click > Run).

### Allure report

```bash
allure serve target/allure-results
```

## Author

**Roqaya Salim**
Software Tester | ISTQB CTFL

- GitHub: [RoqayaSalim](https://github.com/RoqayaSalim)
- LinkedIn: [roqaya-salim](https://linkedin.com/in/roqaya-salim)
