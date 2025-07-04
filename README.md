
# 🛒 E-Commerce Website Selenium Automation Project

This project automates the key user workflows on an e-commerce website using **Java, Selenium WebDriver, and TestNG**. It helps test website features like product selection, adding to cart, user registration, and category verification.

---

## ✅ What This Project Does

This automation project performs the following:

1. Opens the e-commerce website
2. Checks if categories like **"SPECIALS"** have enough products
3. Selects a random product and logs its price
4. Adds the product to the cart
5. Navigates to checkout
6. Redirects to the registration page
7. Fills the registration form using test data from a CSV file
8. Takes a screenshot after execution
9. Generates a test report

---

## 🧰 Technologies Used

| Tool/Library     | Purpose                          |
|------------------|----------------------------------|
| Java             | Programming language             |
| Selenium WebDriver | Web browser automation         |
| TestNG           | Test framework                   |
| Maven            | Build and dependency manager     |
| OpenCSV          | Reading data from CSV files      |

---

## 🗂️ Folder Structure

ECommerce_Testing_Assignment/
├── pom.xml # Maven project file
├── report.txt # Generated test report
├── screenshots/ # Folder where screenshots are saved
├── src/
│ ├── main/
│ │ └── java/
│ │ └── pages/ # Page Object Model classes
│ ├── test/
│ │ ├── java/
│ │ │ └── tests/ # Test classes (ECommerceWorkflowTest.java)
│ │ └── resources/
│ │ └── testdata.csv # Test data for registration (CSV file)
└── README.md # This instruction file

yaml
Copy
Edit

---

## 🧪 How to Run This Project

Follow the steps below:

### 📦 Step 1: Clone the project or download the zip

```bash
git clone https://github.com/your-username/ecommerce-automation.git
Or download and extract the zip.

⚙️ Step 2: Open in VS Code or IntelliJ
Open the folder in your Java IDE

Make sure Java 8+ and Maven are installed

📁 Step 3: Add the Test Data File
Create this file:

bash
Copy
Edit
src/test/resources/testdata.csv
Paste the following sample content:

csv
Copy
Edit
firstName,lastName,email,password
Ujjval,Pateliya,ujjval@test.com,Test@123
▶️ Step 4: Run the Tests
In terminal or CMD:

bash
Copy
Edit
mvn clean test
📋 Test Case Overview
Test Case	Description	Status
✅ Verify Product Categories	Checks if category pages have products	Pass
✅ Add Products to Cart	Selects and adds random products	Pass
✅ Registration with CSV Data	Fills form using testdata.csv	Pass
✅ Screenshot Capture	Takes screenshot at end of test	Pass
✅ Report Generation	Saves a text report as report.txt	Pass

📸 Screenshot Example
Screenshots are saved automatically in the /screenshots/ folder with date and time.

📑 Sample Report
After the test run, you’ll find a report:

Copy
Edit
report.txt
It includes:

Product added

Category checked

Steps passed/failed

Timestamp

💡 Notes
⚠️ Make sure Chrome browser is installed

✅ ChromeDriver is auto-managed using WebDriverManager

🚫 If elements are not found, check the selectors or website availability

👤 Author
Ujjval Pateliya
Computer Science Student
Selenium Automation Enthusiast
Email: ujjvalpateliya@gmail.com

📌 Final Tip
This project is a great base for automating real-world e-commerce workflows. You can easily expand it by adding:

Login validation

Negative test cases

Cross-browser testing

