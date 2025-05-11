# ATM-CONSOLE-APP
# 🏧 ATM Management System (Java)

A simple Java-based ATM Management System that allows **Admin** and **User** operations such as managing cash notes, user withdrawals, balance checks, and more. This project demonstrates object-oriented principles, abstraction, and generics.

---

## 🚀 Features

### 👤 User Features
- Login with account number and PIN
- Check account balance
- Withdraw money (with proper note distribution)
- View transaction history

### 🛡️ Admin Features
- Login with admin credentials
- Refill ATM with specific notes (INR 2000, 500, 100)
- View available cash/note counts
- Monitor transaction logs

---

## 🧰 Tech Stack

- Language: **Java**
- Concepts: **OOP, Abstraction, Generics, Inheritance**
- Environment: **Console-based Application**

---

## 📁 Project Structure

```bash
ATM-Management-System/
│
├── Main.java
├── User.java
├── Admin.java
├── ATM.java
├── Note.java
├── Notes.java (abstract class)
├── Transaction.java
└── utils/
    └── Validation.java
