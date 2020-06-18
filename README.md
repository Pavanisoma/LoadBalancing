# LoadBalancing (Languague : Java)
Docusing_Coding_Challenge


I have maintained three txt files in the program.

scannercopy.txt => to store input values given during run time.

workingcopy.txt => file from where I took server names randomly with weighted percentage.

Finalcopy.txt => this is copy of the scannercopy.txt which can be used when workingcopy.txt becomes empty

Steps: 

Step 1:
Created a function checkprogram() which returns server names considering weighted percentage. It will also remove that particular server record from the workingcopy .txt file when number of servers become zero.
Once the file becomes empty. It will try to store the values from Finalcopy.txt and retrieves server names.

Step 2:
Once all values are stored into scannercopy.txt, I checked if the scannercopy.txt and Finalcopy.txt are same. If both are same then no need to make changes. If both files are not same then I updated both workingcopy.txt and Finalcopy.txt to scannercopy.txt

Step 3:
In anytime if scannercopy.txt is changed then I made sure that workingcopy.txt and Finalcopy.txt are updated.

Step 4:
Tested the program giving multiple inputs and verified if it is working as expected.
Given input “X:3 Y:2” runtime and check the return server name. Ran program25 times and was able to get X 15 times and Y 10 times.


![image](https://user-images.githubusercontent.com/42732676/85077086-5b0c2000-b176-11ea-9509-a7980b654c81.png)
