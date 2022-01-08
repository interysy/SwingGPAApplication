# SwingGPAApplication
A simple application, which can be used to calculate your GPA based on the module grades given at the University Of Glasgow. Also supports calculating what exam grade you got based on the overall grade and the grade that needs to be achieved on the exam to get a certain target grade.
 
# How to use  
Firsly download all the files and run the SwingGPACalc.java in a Java Enviroment. The graphical interface should appear. Before this can happen ensure the modules.txt file exists in the same directory as the java files. If there is an issue with the File IO, please replace the path in the main method. 
 
Once in the GUI ...  
The modules taken into account when calculating GPA are held in a list on the left hand side of the GUI, new modules can be added using the '+' and any module can be removed using the '-' button. The user is also able to add some pre written modules to limit the hassle of adding a new module using the 'add a preloaded module' button. Every custom module the user adds is saved in a local text file , for future use (will be held in the preloaded module list).  

Calculation type can be changed using the buttons at the bottom for these the user will be prompted to enter the target or final grade depending on which one is chosen, then coursework can be added and removed using the  '+' , '-' buttons. As with the GPA the calculate button will trigger the calculation to occur and be displayed in the interface. 
 
# On-going work  
I have added the extra calculators I wanted to add, currently working on some errors - especially when the user incorrectly enters the credits or weight of the relevant entity. On top of this I am attempting to beautify the GUI to make it more appealing. 
 
