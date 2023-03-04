# Food-delivery-management
## About :thought_balloon:
I have built this application for a catering company that need a food delivery management system. There are going to be 3 types of users: client, admin and employee. They are going to need to be registered in order to access the application and based on their role they will be able to execute some specific tasks or queries.
## Features :white_check_mark:
### Admin capabilities:
- Import the initial set of products which will populate the menu from a .csv file.
- Manage the products from the menu: add/delete/modify products and create new products composed of several products from the menu.
- Generate reports about the performed orders considering the following criteria: 
    - time interval of the orders
    - the number of times a product was ordered
    - the clients that made at least a given number of orders
    - the products ordered in a specific day and the amount.
### Client capabilities:
- Register and use the registered username and password to log in within the system. 
- View the list of products from the menu. 
- Search for products based on one or multiple criteria such as keyword (e.g., “soup”), rating, number of calories/proteins/fats/sodium/prices. 
- Create an order consisting of several products.
### Employee capabilities: 
- Notified each time a new order is performed by a client.
### Design patterns used:
- Singleton Design Pattern
- Observer Design Pattern
- Composite Design Pattern
- Design by Contract
## GUI :computer: 
![login](https://user-images.githubusercontent.com/63104735/222917586-dee6f7fa-c662-4534-8f95-3da368359182.PNG)

![admin login](https://user-images.githubusercontent.com/63104735/222917591-96c9cc4b-23fd-4743-910d-a1cf73921430.PNG)
![admin capabilities](https://user-images.githubusercontent.com/63104735/222917593-04399475-e88a-4bba-8164-d40adf273418.PNG)
![report1](https://user-images.githubusercontent.com/63104735/222917594-57eff33f-5c55-40af-828b-99dad64eb215.PNG)
![report2](https://user-images.githubusercontent.com/63104735/222917596-e9f87626-cc18-4444-bede-1ac6737adc12.PNG)
![report3](https://user-images.githubusercontent.com/63104735/222917598-1b5de587-7f4b-4d97-aeb8-63f077f54a22.PNG)
![client capabilities](https://user-images.githubusercontent.com/63104735/222917602-b7a33959-fca3-4c25-98ec-7ca0ca68f91e.PNG)
![employee capabilities](https://user-images.githubusercontent.com/63104735/222917603-15ad0303-b97f-4335-a72b-8062a3ce039e.PNG)
