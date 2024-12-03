#  Customer Management System

## Overview
This project is a microservices-based architecture for managing customers and transactions.

---

## Services:
* Customer Service - Manages customer-related data. 
* Transaction Service - Handles transactions.
* API Gateway - Gateway for routing requests.
* Config Service - Centralized configuration.
* Discovery Service - Service registry (Eureka).

---
## Setup Instructions
1. Clone the repository.
2. Start the **Config Service** (`http://localhost:8888`).
3. Start the **Discovery Service** (`http://localhost:8761`).
4. Start the **Customer Service**(`http://localhost:8080`) and **Transaction Service** (`http://localhost:8081`).
5. Start the **API Gateway** (`http://localhost:8222`).

## Endpoints

### 1. **Using API Gateway (`http://localhost:8222`)**
#### Customer Service:
* Add new customer:

  Post request: http://localhost:8222/customers/

* Get customer details:

  Get request: http://localhost:8222/customers/{id}

#### Transaction Service:
* Top up:

  Post request: http://localhost:8222/transactions/top-up/{customerCif}/{amount}

* Purchase:

  Post request: http://localhost:8222/transactions/purchase/{customerCif}/{amount}

* Refund:

  Post request: http://localhost:8222/transactions/refund/{customerCif}/{refundAmount}

* Get Transaction History of Customer:

  Get request: http://localhost:8222/transactions/customer/{customerCif}

### 2. **Without Api Gateway**
#### Customer Service:
* Add new customer:

  Post request: http://localhost:8080/customers/

* Get customer details:

  Get request: http://localhost:8080/customers/{id}

#### Transaction Service:
* Top up:

  Post request: http://localhost:8081/transactions/top-up/{customerCif}/{amount}

* Purchase:

  Post request: http://localhost:8081/transactions/purchase/{customerCif}/{amount}

* Refund:

  Post request: http://localhost:8081/transactions/refund/{customerCif}/{refundAmount}

* Get Transaction History of Customer:

  Get request: http://localhost:8081/transactions/customer/{customerCif}

---
Example JSON format for create new Customer:
```
{
    "firstName":"John",
    "lastName":"Doe",
    "birthDate":"2000-01-01"
}
```
#### Discovery Server: http://localhost:8761
