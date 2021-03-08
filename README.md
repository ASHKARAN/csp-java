# csp-java
CSP with BT, FC, FLA

JDK 1.8, 
IDE: Intellij IDEA

Starter class: Main.java

Backtracking class: algorithms/Backtracking.java

Forward Checking class: algorithms/ForwardChecking.java

Full look ahead class: algorithms/FullLookAhead.java


You can use csp.sh to run this code on Linux

or you can use csp.bat to run this code on Windows

or run this code on your Terminal

java -cp csp-java.jar Csp.Main

First output of the code:  

Welcome to Csp
Please enter n (Integer)

after entering the valid inputs, you must see some output like this

Arc Consistency will be applied

Let's run the calculation

Algorithm: BackTracking with ArcConsistency

Constraints (incompatible tuples):

X3 X2 : (2,0)(2,2)(1,0) 

X0 X3 : (0,0)(1,2)(0,1) 

X2 X0 : (0,0)(0,1)(0,2) 

X1 X3 : (1,2)(2,0)(0,0) 

Possible solution:

X0: 0, X1: 0, X2: 0, X3: 0

Execution time: 47131 nano seconds

Execution time is calculated for main method


