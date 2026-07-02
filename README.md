# ATM Core - Java OOP & SQL Simulator

### 📌 Project Overview (High-Level Summary)
This project is a fully functional Automated Teller Machine (ATM) simulation running right inside the computer's command line. The program allows users to securely log in with a card, check their balance, and perform cash withdrawals. The true value behind this development is that it utilizes a real, in-memory SQL database to manage data under the hood, making the system incredibly fast and safe. The software's stability is guaranteed by an automated testing suite that verifies all banking workflows are working flawlessly before every run.

---

### 💡 The Story & Milestone
This project marks a massive personal milestone: **this is the first piece of code I have ever created and shared on GitHub.** During my learning journey, I completely lost the thread when it came to Java and the core concepts of Object-Oriented Programming (OOP). That is when I teamed up with an AI collaborator to review the theory through practical, hands-on examples—and out of that joint effort, this ATM simulator began to take shape. As a fun highlight of this journey, **while doing some manual testing, I accidentally stumbled upon a hidden UI bug** (duplicated menu inputs caused by a scanner buffer issue) which we then successfully tracked down and fixed in the code!

---

### 🛠️ Technical Architecture & Implementation
This application is a Java-based **CLI (Command Line Interface)** software built with Clean Code principles in mind, moving completely away from a monolithic structure.

* **Database Layer:** We migrated the initial fragile CSV file-handling to a modern, in-memory **H2 SQL Database** (`jdbc:h2:mem`). Data lives securely in the RAM, and balance updates or credential checks are executed using standard SQL queries.
* **Business Logic:** Distinct components handle the technical input validation (`LoginValidator`), the security card-blocking mechanism after 3 failed attempts (`LoginSession`), and financial operations (`CashWithdrawal`).
* **Testing Strategy (JUnit 5):** The entire backend is safeguarded by automated **Unit Tests**. We used Data-Driven Testing and equivalence partitioning to test boundary values, ensuring instant feedback on system stability whenever code changes.