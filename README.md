# Water Purification Inventory Management

A Java application for managing an inventory of water purification units, tracking individual units, their test results, and generating reports.

This was made for one of my courses, and I would say it was good MVVM practice in a simple environment.

- Model: handles core business logic and data representation of units and test results
- ViewModel: acts as an intermediary, exposing data and logic in a presentation-friendly format
- View: provides the (console) interface for user interaction, decoupled from business logic

## Features

- Add, remove, and update water purification units
- Record test results for each unit
- Generate reports based on unit performance and test outcomes
- Console-based user interface (though technically GUI is applicable)

## Getting Started

### Compile
Either run in IntelliJ or compile it manually:

```bash
javac WaterInventoryManager/src/ca/purification/inventory/main/*.java
java WaterInventoryManager/src/ca/purification/inventory/main/Main
```

