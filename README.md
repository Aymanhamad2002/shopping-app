# 🛒 Shopping App

A full-featured Spring Boot e-commerce backend application with secure user authentication, product catalog management, shopping cart functionality, and order processing capabilities.

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Configuration](#configuration)
  - [Running the Application](#running-the-application)
- [API Endpoints](#-api-endpoints)
- [Usage Examples](#-usage-examples)
- [Learning Goals](#-learning-goals)
- [Future Enhancements](#-future-enhancements)
- [Contributing](#-contributing)
- [License](#-license)
- [Contact](#-contact)

---

## 📘 Overview

The Shopping App is a comprehensive REST API backend built with Spring Boot that powers modern e-commerce experiences. It provides robust user management, secure JWT-based authentication, dynamic product catalogs with categories, fully functional shopping cart operations, and complete order processing workflows.

This project serves as an excellent reference implementation for building secure, scalable, and maintainable e-commerce systems using industry-standard practices and technologies.

### Key Capabilities

- **User Management**: Secure registration and authentication with JWT tokens
- **Product Catalog**: Browse products organized by categories with image support
- **Shopping Cart**: Full CRUD operations for cart management
- **Order Processing**: Place, track, and manage customer orders
- **Data Persistence**: Reliable storage with MySQL and Spring Data JPA
- **Security**: Protected endpoints using Spring Security

---

## ✨ Features

- ✅ **JWT Authentication** - Secure token-based user authentication and authorization
- ✅ **User Registration & Login** - Complete user account management system
- ✅ **Product Management** - CRUD operations for products with category organization
- ✅ **Image Handling** - Product image upload and management
- ✅ **Shopping Cart** - Add, update, remove items with quantity management
- ✅ **Order System** - Place orders, view history, and track order status
- ✅ **MySQL Integration** - Persistent data storage with JPA/Hibernate
- ✅ **RESTful API** - Clean, standardized REST endpoints
- ✅ **Exception Handling** - Global exception handling with meaningful responses
- ✅ **DTO Pattern** - Separation of concerns using Data Transfer Objects
- ✅ **Modular Architecture** - Clean code organization following best practices

---

## 🛠️ Technology Stack

| Component | Technology |
|-----------|------------|
| **Language** | Java 17+ |
| **Framework** | Spring Boot 3.x |
| **Security** | Spring Security + JWT (JJWT) |
| **Database** | MySQL 8.0+ |
| **ORM** | Spring Data JPA (Hibernate) |
| **Build Tool** | Maven |
| **Utilities** | Lombok |
| **Version Control** | Git |

---

## 📂 Project Structure

```
shopping-app/
│
├── src/main/java/com/backendProject/shoppingApp/
│   ├── cart/                    # Shopping cart domain
│   ├── cartItem/                # Cart item entities and logic
│   ├── category/                # Product category management
│   ├── image/                   # Product image handling
│   ├── order/                   # Order processing
│   ├── orderItem/               # Order line items
│   ├── product/                 # Product management
│   ├── user/                    # User management
│   ├── controller/              # REST API controllers
│   ├── dto/                     # Data Transfer Objects
│   ├── enums/                   # Application enumerations
│   ├── exception/               # Exception handling
│   ├── repository/              # JPA repositories
│   ├── response/                # API response models
│   ├── security/                # Security configuration
│   ├── service/                 # Business logic layer
│   ├── utils/                   # Utility classes
│   └── ShoppingAppApplication.java
│
├── src/main/resources/
│   ├── application.properties   # Application configuration
│   └── static/                  # Static resources (optional)
│
├── pom.xml                      # Maven dependencies
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

Before running the application, ensure you have:

- **Java 17** or higher installed
- **Maven 3.6+** for dependency management
- **MySQL 8.0+** database server
- **Git** for version control
- **Postman** or similar tool for API testing (optional)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Aymanhamad2002/shopping-app.git
   cd shopping-app
   ```

2. **Create the database**
   ```sql
   CREATE DATABASE shoppingdb;
   ```

3. **Install dependencies**
   ```bash
   mvn clean install
   ```

### Configuration

Configure the application by editing `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/shoppingdb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# JWT Configuration
jwt.secret=YourSuperSecretKeyMinimum256BitsLong
jwt.expiration=3600000  # 1 hour in milliseconds

# Application Settings
spring.main.allow-circular-references=true
```

> ⚠️ **Security Note**: Never commit sensitive credentials to version control. Use environment variables or external configuration for production deployments.

### Running the Application

**Option 1: Using Maven**
```bash
mvn spring-boot:run
```

**Option 2: Using Java**
```bash
mvn clean package
java -jar target/shopping-app-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

---


## 🎓 Learning Goals

This project demonstrates:

- **RESTful API Design** - Building clean, scalable REST endpoints
- **Spring Boot Architecture** - Layered application structure (Controller → Service → Repository)
- **JWT Authentication** - Implementing stateless authentication
- **Spring Security** - Securing endpoints with role-based access
- **Spring Data JPA** - Database operations with minimal boilerplate
- **DTO Pattern** - Separating domain models from API contracts
- **Exception Handling** - Centralized error handling with meaningful responses
- **Dependency Injection** - Leveraging Spring's IoC container
- **Project Organization** - Modular, maintainable code structure

---

## 🔮 Future Enhancements

- [ ] Refresh token implementation for extended sessions
- [ ] Payment gateway integration (Stripe, PayPal)
- [ ] Product review and rating system
- [ ] Email notifications for orders
- [ ] Admin dashboard with analytics
- [ ] Product search and filtering
- [ ] Swagger/OpenAPI documentation
- [ ] Unit and integration testing with JUnit 5
- [ ] Docker containerization
- [ ] CI/CD pipeline setup
- [ ] Redis caching for improved performance
- [ ] Wishlist functionality

---

## 🤝 Contributing

Contributions are welcome and appreciated! To contribute:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/AmazingFeature`)
3. **Commit** your changes (`git commit -m 'Add some AmazingFeature'`)
4. **Push** to the branch (`git push origin feature/AmazingFeature`)
5. **Open** a Pull Request

Please ensure your code follows the existing style and includes appropriate tests.

---

## 📜 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

You are free to use, modify, and distribute this software.

---

## 📧 Contact

**Ayman Hamad**

- GitHub: [@Aymanhamad2002](https://github.com/Aymanhamad2002)
- Project Link: [https://github.com/Aymanhamad2002/shopping-app](https://github.com/Aymanhamad2002/shopping-app)

For questions, suggestions, or issues, please open an issue on GitHub.

---

<div align="center">

### ⭐ If you found this project helpful, please give it a star!

**Happy Coding! 🚀**

</div>
