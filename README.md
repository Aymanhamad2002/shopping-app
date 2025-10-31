# 🛒 Shopping App

A full-featured **Spring Boot** e-commerce backend application that provides **user management**, **authentication**, **product catalog**, **shopping cart**, and **order processing** functionalities.  
This project demonstrates how to build a secure, modular, and scalable shopping system using **Spring Data JPA**, **Spring Security**, and **JWT (JJWT)**.

---

## 📘 Overview

This Shopping App allows users to:
- Create a new account and log in securely with JWT authentication.
- Browse and view products by category.
- Add, update, or remove items from their shopping cart.
- Place and manage orders.
- Store and retrieve data from a MySQL database.
- Handle secure operations using Spring Security and JJWT.

It’s an excellent example of a cleanly structured **Spring Boot REST API** for online shopping applications.

---

## 🚀 Features

✅ User registration and authentication with JWT  
✅ Product catalog with categories and images  
✅ Add, remove, or update products in cart  
✅ Place, view, and manage orders  
✅ MySQL database integration via JPA/Hibernate  
✅ Secure endpoints with Spring Security  
✅ Exception handling and standardized responses  
✅ DTOs for data transfer and separation from entities  
✅ Clean, modular architecture following best practices  

---

## 🛠️ Technologies Used

| Layer | Technology |
|:------|:------------|
| **Language** | Java |
| **Framework** | Spring Boot |
| **Security** | Spring Security + JWT (io.jsonwebtoken JJWT) |
| **Database** | MySQL |
| **ORM** | Spring Data JPA (Hibernate) |
| **Utility** | Lombok |
| **Build Tool** | Maven |
| **Version Control** | Git & GitHub |

---

## 📂 Project Structure

```yaml
shopping-app/
├── src/
│   ├── main/
│   │   ├── java/com/backendProject/shoppingApp/
│   │   │   ├── cart/             # Cart entity and related business logic
│   │   │   ├── cartItem/         # Items added to the cart
│   │   │   ├── category/         # Product categories and category management
│   │   │   ├── image/            # Image model and utilities for product images
│   │   │   ├── order/            # Order entity and order management logic
│   │   │   ├── orderItem/        # Items within a placed order
│   │   │   ├── product/          # Product entity, service, and controller
│   │   │   ├── user/             # User entity, registration, and authentication
│   │   │   ├── controller/       # REST controllers (user, product, cart, order)
│   │   │   ├── dto/              # Data Transfer Objects for API communication
│   │   │   ├── enums/            # Application enums (roles, statuses, etc.)
│   │   │   ├── exception/        # Custom exceptions and global exception handling
│   │   │   ├── model/            # Common domain models shared between modules
│   │   │   ├── repository/       # Spring Data JPA repositories
│   │   │   ├── response/         # Standardized API responses
│   │   │   ├── security/         # JWT configuration, filters, authentication manager
│   │   │   ├── service/          # Business services (UserService, ProductService, etc.)
│   │   │   ├── utils/            # Helper and utility classes
│   │   │   └── ShopingAppApplication.java   # Main Spring Boot application class
│   │   └── resources/
│   │       ├── application.properties        # App configuration (DB, JWT, etc.)
│   │       └── static/ or templates/         # Optional static content or templates
├── pom.xml
└── README.md
⚙️ Configuration

Before running the project, open
src/main/resources/application.properties and configure:

# Server
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/shoppingdb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT Configuration
jwt.secret=YourSuperSecretKeyHere
jwt.expiration=3600000  # 1 hour (in ms)

# Other Settings
spring.main.allow-circular-references=true


⚠️ Important: Never commit real passwords or JWT secrets in public repositories.
Use environment variables or external configuration files for production.

🧩 How It Works
🟢 User Registration & Login

Users can create an account via /api/auth/register

Login via /api/auth/login to receive a JWT token.

{
  "username": "john_doe",
  "password": "123456"
}


A successful login returns a JWT token that must be included in future requests:

Authorization: Bearer <your_token>

🛍 Shopping Flow

Browse products
GET /api/products

Add product to cart
POST /api/cart/add

View cart items
GET /api/cart

Update or remove item from cart
PUT /api/cart/update/{id} or DELETE /api/cart/remove/{id}

Place order
POST /api/orders/place

View order history
GET /api/orders/user/{userId}

▶️ Running the Application
1️⃣ Clone the Repository
git clone https://github.com/Aymanhamad2002/shopping-app.git
cd shopping-app

2️⃣ Build the Project
mvn clean install

3️⃣ Run the Application
mvn spring-boot:run

4️⃣ Test Using Postman

You can test all REST endpoints using Postman or cURL.

🧠 Learning Goals

This project demonstrates how to:

Design a real-world REST API with Spring Boot.

Implement JWT authentication and authorization.

Use Spring Data JPA for database management.

Organize project modules for maintainability.

Build scalable, layered applications for e-commerce systems.

🧰 Future Improvements

 Add refresh token functionality

 Add payment gateway integration

 Add product review and rating system

 Integrate Swagger/OpenAPI documentation

 Implement admin dashboard

 Add unit and integration testing

🤝 Contributing

Contributions are welcome!
To contribute:

Fork the repository

Create a new feature branch

Commit your changes

Submit a pull request

📜 License

This project is released under the MIT License.
You are free to use, modify, and distribute it.

✉️ Contact

Author: Ayman Hamad

📧 For questions or suggestions, open an issue
.

⭐ If you found this project helpful, please give it a star on GitHub!
