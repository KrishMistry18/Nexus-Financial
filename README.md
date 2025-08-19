## 💳 Bank Management System (Java Swing + MySQL)

An ATM-style desktop app built with Java Swing and MySQL. It includes a smooth 3-step onboarding, secure login, and a full set of ATM operations: Deposit, Withdrawal, Fast Cash, Balance Enquiry, Mini Statement, and PIN Change.

### ✨ Highlights
- **🔐 Secure login**: Card + PIN validation with prepared statements
- **🧭 Guided onboarding**: Signup 1 → Signup 2 → Signup 3 with data saved to MySQL
- **🏦 ATM features**:
  - **Deposit**: records to ledger + transactions
  - **Withdrawal**: safe balance checks and logging
  - **⚡ Fast Cash**: one-click quick withdrawal (logged as Withdrawal in ledger, as Fast Cash in transactions)
  - **📊 Balance Enquiry**: balance is computed from a ledger, never duplicated
  - **🧾 Mini Statement**: complete transaction history by PIN, ordered by date
  - **🔁 PIN Change**: updates across `login` and `signup3`
- **🧱 Robust design**: balance is derived from `bank` ledger (sum of deposits − withdrawals) to avoid inconsistency
- **🧰 Easy setup**: configurable `database.properties` and cross-platform build scripts

### 🛠 Tech Stack
- Java 8+ (tested on Java 22)
- Swing (desktop UI)
- MySQL 8 (Connector/J)

### 📁 Project Structure
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

### 🗄️ Database Setup
1) Execute `database_setup.sql` in MySQL to create `bankSystem`, all tables, seed users, and grant access.
2) Ensure your DB user/password match `database.properties`.

Key tables:
- `login(form_no, card_number, pin)`
- `signup(form_no, name, father_name, dob, gender, email, marital_status, address, city, state, pin_code)`
- `signup2(form_no, religion, category, income, education, occupation, pan_number, aadhar_number, senior_citizen, existing_account)`
- `signup3(form_no, account_type, card_number, pin, facility)`
- `bank(pin, date, type, amount)`  ← ledger entries
- `transactions(pin, date, type, amount)`  ← mini statements

### 🔧 Credentials Configuration
Create or edit `database.properties` in the project root:
```
db.url=jdbc:mysql://localhost:3306/bankSystem
db.user=krish
db.password=krish@0412
```
If missing, defaults match the above.

### 📦 Dependencies
Place these jars in `lib/`:
- mysql-connector-j-8.x.x.jar
- jcalendar-1.4.jar

### 🚀 Build and Run (Windows)
Use the script:
```
build.bat
```
What it does:
- Compiles sources to `out/`
- Copies `src/icon` → `out/icon` so images load via ClassLoader
- Starts the Login screen

Manual alternative:
```
cd Bank-Management-System
javac -encoding UTF-8 -cp "lib/*" -d out src\bank\management\system\*.java
xcopy /E /I /Y src\icon out\icon >nul
java -cp "out;lib/*" bank.management.system.Login
```

### 🐧 Build and Run (macOS/Linux)
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

### 🧪 Test Accounts
- Card: `5040936012345678`  PIN: `1234`
- Card: `5040936012345679`  PIN: `5678`

### 💡 Notes
- **No duplicated balance**: it’s computed from the `bank` ledger (Deposits − Withdrawals/Fast Cash)
- **Mini Statement** shows all entries from `transactions`, ordered by date
- **Fast Cash** logs to both `bank` and `transactions` for complete history

### 📜 License
MIT (or your preferred license)

---

## 📞 **Contact**

- Project: <https://github.com/KrishMistry18/EduCycle>
- Email: <mistrykrish2005@gmail.com>
- LinkedIn: <https://www.linkedin.com/in/krish-mistry-0290522b7>

---

**Built with ❤️ by Krish Mistry** 
