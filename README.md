Bank Management System (Java Swing + MySQL)

A desktop ATM-style Bank Management System built with Java Swing and MySQL. It supports user onboarding (Signup 1 → Signup 2 → Signup 3), secure login, and ATM operations including Deposit, Withdrawal, Fast Cash, Balance Enquiry, Mini Statement, and PIN Change.

Features
- Login with card number + PIN
- Signup flow (3 pages) that persists data to MySQL tables `signup`, `signup2`, `signup3`, `login`
- Main ATM UI with:
  - Deposit (adds to balance and records transactions)
  - Withdrawal (deducts from balance and records transactions)
  - Fast Cash (quick withdrawal presets; treated same as withdrawal for balance; transactions show as "Fast Cash")
  - Balance Enquiry (ledger-based running balance)
  - Mini Statement (lists all transactions by PIN in chronological order)
  - PIN Change (updates across `login`, `signup3`, and ledger mappings)

Tech Stack
- Java 8+ (tested on Java 22)
- Swing (UI)
- MySQL 8 (Connector/J required)

Project Structure
```
Bank-Management-System/
  lib/                         # External jars (mysql-connector-j, jcalendar)
  src/
    bank/management/system/    # Java sources (UI + DB)
    icon/                      # UI assets (bank.png, atm, etc.)
  out/                         # Build output (gitignored)
  database.properties          # DB credentials (see below)
  database_setup.sql           # Full schema + sample data
  build.bat / build.sh         # Build & run helpers
  README.md                    # This file
```

Database Setup
1) Create DB and tables using the provided script:
- File: `database_setup.sql`
- This creates database `bankSystem`, drops and recreates the tables, inserts two sample users, and grants a user.

Key tables used by the app:
- `login(form_no, card_number, pin)`
- `signup(form_no, name, father_name, dob, gender, email, marital_status, address, city, state, pin_code)`
- `signup2(form_no, religion, category, income, education, occupation, pan_number, aadhar_number, senior_citizen, existing_account)`
- `signup3(form_no, account_type, card_number, pin, facility)`
- `bank(pin, date, type, amount)`  ← ledger entries; balance is computed from this
- `transactions(pin, date, type, amount)`  ← used for mini statements

Credentials Configuration
Create `database.properties` in the project root (`Bank-Management-System/`):
```
db.url=jdbc:mysql://localhost:3306/bankSystem
db.user=krish
db.password=krish@0412
```
If this file is missing, the app falls back to the same defaults above.

Install Dependencies
Place these jars in `lib/`:
- mysql-connector-j-8.x.x.jar
- jcalendar-1.4.jar (for date picker)

Build and Run (Windows)
Use the batch script:
```
build.bat
```
What it does:
- Compiles sources to `out/`
- Copies `src/icon` → `out/icon` so images load via ClassLoader
- Runs the Login screen

Manual commands:
```
cd Bank-Management-System
javac -encoding UTF-8 -cp "lib/*" -d out src\bank\management\system\*.java
xcopy /E /I /Y src\icon out\icon >nul
java -cp "out;lib/*" bank.management.system.Login
```

Build and Run (macOS/Linux)
```
./build.sh
```
Or manually:
```
cd Bank-Management-System
javac -encoding UTF-8 -cp "lib/*" -d out src/bank/management/system/*.java
cp -R src/icon out/icon
java -cp "out:lib/*" bank.management.system.Login
```

Test Accounts
- Card: `5040936012345678`  PIN: `1234`
- Card: `5040936012345679`  PIN: `5678`

Notes
- Balance is not a stored column. It is computed from `bank` ledger entries: sum(Deposit) − sum(Withdrawal/Fast Cash).
- Mini Statement lists all rows from `transactions` by `pin`, ordered by date.
- Fast Cash inserts a `Withdrawal` row in `bank` and a `Fast Cash` row in `transactions`.

License
MIT (or your preferred license)


