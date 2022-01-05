package GPACalc;

import java.util.List;

public class Calculator {

    public static double calculateGPA(List<Object> moduleModel) {
        int result = 0;
        int totalCredits = 0;
        for (Object module : moduleModel) {
            Module module1 = (Module) module;
            result += module1.creditsMgrade();
            totalCredits += module1.getCredits();

        }
        return (((float) result) / totalCredits);

    }

}

