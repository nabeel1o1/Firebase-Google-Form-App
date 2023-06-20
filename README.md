# Firebase-Google-Form-App

This is an assigment app in which I was assigned to create an app which has CRUD operations of Firebase and also authenticate user by email and password.
So, I developed this app which has login and registration option and after the successful login/register, I give the option to add a form on firebase database through this app. 
There are three fields user has to put data to save it on firebase database. User can add, edit, delete and get all the data.

# Architecture 
I used MVVM architecture for this app because, this app is Data-Driven App, which mean this app heavily relies on data, such as fetching data, storing data, and performing all other CRUD operations.
So for this purpose, MVVM provides a clear separation between the data layer (Model) and the UI layer (View and ViewModel). This separation allows for easier data management and integration with data sources.

Also I developed this app on Single Activity Architecture pattern.
