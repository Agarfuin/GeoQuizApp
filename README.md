# GeoQuizApp

This project has prepared for University of Bradford COS7025-B Mobile Application Development Final Couserwork and this repository consists of the mentioned project source code. The project is a native android application which is about a geography quiz game where people are trying to know the name and location of the country from a given image.

## Multiple Choice

This part is fairly simple. There is an image shown in the screen and there are 4 options of different countries. The user tries to know which of the countries does the shown image is related to.

<p align="center">
    <img src="https://i.imgur.com/5M2np1c.png" width="250"> 
</p>

## Country Location

If the user's answer on the first part(multiple choice) correctly, then this part starts. This time the user tries to find the selected country in the first part and locate it on the map.

<p align="center">
  <img src="https://i.imgur.com/0PbvoHU.png" width="250">
</p>

## Game Logic

When the user first launches the app, a welcoming fragment appears with a single button of starting the game...

<p align="center">
  <img src="https://i.imgur.com/XUq0oJO.png" width="250">
</p>

There are 40 questions in total and if the user answers both of the parts correctly, then correct answer fragment shows up and that specific question gets deleted from the database.

<p align="center">
    <img src="https://i.imgur.com/hMwgoPD.png" width="250"> 
</p>

If the user guesses the country wrong in either part, then wrong answer fragment shows and the question stays inside the database and it will appear again in the upcoming questions.

<p align="center">
    <img src="https://i.imgur.com/rZ0Q8rs.png" width="250"> 
</p>

Finally if the user answers all 40 questions correctly, an endgame fragment appears with a single button of restarting the game from the beginnig...

<p align="center">
    <img src="https://i.imgur.com/6iPflhC.png" width="250"> 
</p>

## Run the Project

In order to run the project, any version of Android Studio should do good.

Import the project to your computer, choose any virtual device in anroid studio and simply run the app.

If you want to get the APK for your mobile phone:

> Android Studio -> Build -> Build Bundle(s) / APK(s) -> Build APK(s)

Have fun!
