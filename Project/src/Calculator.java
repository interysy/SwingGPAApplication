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

    public static Grade calculateExamGrade(List<Coursework> courseworkList,Grade finalGrade) {
        double totalToSubtract = 0;
        double totalWorth =0;

        for (Coursework coursework : courseworkList) {
            totalWorth += coursework.getWorth();
            totalToSubtract += coursework.worthXPoints();
        }
        int gradeAsPoints = finalGrade.getGrade();
        double examWorth = 100 - totalWorth;

        double result = (gradeAsPoints - totalToSubtract) / examWorth;
        int result2 = (int) Math.round(result);
        return Grade.getAlphaGrade(result2);


    }


}

