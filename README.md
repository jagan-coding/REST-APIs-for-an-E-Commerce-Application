# REST APIs for an E-Commerce Application using Spring boot
This Project is a backend system built with **Spring Boot** that provides a set of REST APIs for an e-commerce application.  It supports user authentication, product management, cart operations, order processing, and secure token-based access control.
---
## Features
### Authentication
- Register/Login for **Customers** and **Sellers**
- UUID-based token authentication (Valid for 1 hour)
- Secure Logout and token expiration handling

### Product Management
- Sellers can add, update, delete and view products
- Customers can view available products

### Cart Management
- Customers can add and remove items for their cart
- View cart contents and total price

### Order Management
- Customers can place orders from the cart
- View all orders

## Tech Stack
* Java
* Spring boot
* Spring Data JPA
* MySQL

## Modules
* Authentication Module
* Customer Module
* Seller Module
* Product Module
* Cart Module
* Order Module

## API Root Endpoint
`http://localhost:8081/api`

## API Moudle Endpoints
### Authentication Module
* `POST /auth/register/cus` : Register new customer
* `POST /auth/login/cus` : Login Customer and receive token
* `POST /auth/logout/cus` : Logout customer based on session token
* `POST /auth/register/sel` : Register new Seller
* `POST /auth/login/sel` : Login Seller and receive token
* `POST /auth/logout/sel` : Logout Seller based on session token

### Customer Module
* `GET /cus/{id}` : Get Customer by customer id
* `GET /cus/current` : Get currently Logged in Customer
* `GET /cus/all` : Get All Customers
* `GET /cus/orders` : Get Customer's orders history
* `PUT /cus` : Update Customer's details
* `PUT /cus/upd/contact` : Update Customer's Contact Details
* `PUT /cus/upd/credit` : Update Customer's Credit card Details
* `PUT /cus/upd/password` : Update Customer's Password
* `PUT /cus/upd/address?type=home` : Update Customer's address
* `DELETE /cus` : Deletes logged in user with valid session token

### Seller Module
* `GET /sel/current` : Get Currently Loggedin Seller
* `GET /sel/all` : Get all sellers
* `GET /sel/{id}` : Get seller with seller id
* `GET /sel/mobile` : Get seller by Seller's Mobile number
* `PUT /sel` : Update Seller
* `PUT /sel/upd/password` : Update Seller's password
* `DELETE /sel/{id}` : Delets seller with passed id

### Product Module
* `POST /add/prod` : Add product by Seller
* `GET /prod/{id}` : Get product by Product's Id
* `GET /products` : Get all products
* `PUT /prod` : Update Product by seller
* `GET /prod/sel/{id}` : Gets products of given seller id
* `GET /prod/category/{category}` : get products with given category
* `GET /prod?status=status` : get products with given status
* `DELETE /prod/{id}` : Delete product by Seller

### Cart Module
* `POST /cart/add` : Add item into cart
* `GET /cart` : Get all items in customer's cart
* `PUT /cart/update` : Update cart's item quantity
* `DELETE /cart` : Remove item from cart
* `DELETE /cart/clear` : Clear entire cart

### Order Module
* `POST /ord` : Place the order
* `GET /ord/{id}` : Gets order details with given order id
* `GET /ord` : Gets all orders
* `PUT /ord/cancel/{id}` : Cancel order
* `GET /ord/date` : Gets orders placed on given date (YYYY-MM-DD)
* `GET /cus/ord/{id}` : Getting customer by his order id








