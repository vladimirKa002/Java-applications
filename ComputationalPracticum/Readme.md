**Differential equations analyzer (Computational practicum)**

This program is a tool for analyzing differential equations. User will have 
an ability to input any First Order Differential equation (in a form of 
y' = ...), and plot graphs by using Analytical, Euler, Improved Euler, and Runge-Kutta
methods. The program will also build graphs for local and global errors for each
method compared to analytical approach.

There is also an opportunity to input only y' expression, and 
obtain graphs by running WolframAlpha.

The program was written on Java with use of JavaFX GUI library. 
In order to launch application the device should have installed JRE 
with minimum version 1.8.

**Some important aspects**

1. WolframAlpha is free only for student purposes. This particular project is based on student
license which provides 2000 free requests per month.
2. There are restrictions on functions that are recognized by the Shunting-yard algorithm.
It is possible that WolframAlpha will return unrecognizable result. If it is so, unfortunately,
user will have to solve equation by himself and use the standard input. 
Full information about input format can be found in the information
box while running the application.
3. The executable jar file is the path: target/Differential equations analyzer.jar

![Input](images/Input.PNG?raw=true)

![Graphs](images/Graphs.PNG?raw=true)