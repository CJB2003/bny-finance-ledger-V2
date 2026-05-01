# BNY Financial Corp - Account Ledger Application 📊
A financial transaction tracking application built in Java. Started as a CLI application and expanded into a full JavaFX desktop app with MySQL database integration.

## 🚀 Features
**CLI Application**

- Add deposits and payments that save to a transactions.csv file
- View a ledger with all transactions, deposits only, or payments only
- Run pre-defined reports:
  - Month to Date
  - Previous Month
  - Year to Date
  - Previous Year
  - Search by Vendor (partial name matching, case-insensitive)



**JavaFX Desktop Application**

- Login and Sign Up screens with MySQL authentication
- Dashboard showing:
  - Live account balance, income, and expenses pulled from the database
  - Deposit and payment submission form that automatically timestamps transactions
  - Instant balance refresh after every transaction
- Ledger screen with a full transaction table, filterable by All, Deposits, or Payments
- Smooth navigation between Dashboard, Transactions, and Ledger screens
- Logout functionality that returns to the login screen

## 🛠️ Tech Stack
- **Backend**: Java
- **Database**: MySQL
- **Frontend**: JavaFX for GUI
- **Build Tools**: Maven
- **Database Driver**: JDBC

## 📦 Installation
1. **Prerequisites**: Ensure Java Development Kit (JDK) and a compatible database system are installed.
2. **Clone Repository**: Clone the BNY Finance Ledger Application repository from GitHub.
3. **Build Project**: Use Maven to build the project.
4. **Configure Database**: Configure the database connection settings in the application properties file.
5. **Run Application**: Execute the application using the main class (e.g., `BnyLedgerApp.java`).

## 💻 Usage
1. **Launch Application**: Start the application and navigate to the login page.
2. **Login**: Enter valid credentials to access the application.
3. **Dashboard**: Upon successful login, the dashboard will display an overview of your financial data.
4. **Navigation**: Use the navigation menu to access different features, such as transaction management, ledger management, and user management.

## 📂 Project Structure
```markdown
- BNY-Finance-Ledger-App
  - src
    - main
      - java
        - com.bny
          - BnyLedgerApp.java
          - controllers
            - LedgerController.java
            - MenuController.java
            - DashboardController.java
            - ...
          - models
            - Model.java
            - ...
          - utils
            - DatabaseConnection.java
            - ...
      - resources
        - application.properties
        - ...
    - test
      - java
        - ...
  - target
  - pom.xml (if using Maven) or build.gradle (if using Gradle)
```

## Author
Built by Chris as part of the Pluralsight Java Academy Capstone Project.
