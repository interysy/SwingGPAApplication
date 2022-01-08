package GPACalcV2a;

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

    public static Grade calculateExamGradeForTarget(List<Object> courseworkList) {
        double totalWorth = 0;
        double totalToSubtract = 0;
        TargetGrade targetGrade = (TargetGrade) courseworkList.get(0);
        courseworkList.subList(1, courseworkList.size());

        for (Object coursework : courseworkList) {

            Coursework coursework1 = (Coursework) coursework;
            totalWorth = totalWorth + ((coursework1.getWorth()) / 100);
            totalToSubtract = totalToSubtract + (coursework1.worthXPoints());

        }
        double targetAsPoints = (targetGrade.getGrade().getNumericalGrade()) - 0.5; // 0.5 accounts for rounding , so that we can calculate minumum grade
        double examWorth = 1 - totalWorth;


        double result1 = (targetAsPoints - totalToSubtract);
        double result2 = result1 / examWorth;
        int result3 = (int) Math.round(result2);

        return Grade.getAlphaGrade(result3);

    }

    public static Grade calculateExamGrade(List<Object> courseworkList) {
        double totalToSubtract = 0;
        double totalWorth = 0;
        FinalGrade finalGrade = (FinalGrade) courseworkList.get(0);
        courseworkList = courseworkList.subList(1, courseworkList.size());


        for (Object coursework : courseworkList) {

            Coursework coursework1 = (Coursework) coursework;
            totalWorth = totalWorth + ((coursework1.getWorth()) / 100);
            totalToSubtract = totalToSubtract + (coursework1.worthXPoints());


        }

        double gradeAsPoints = (finalGrade.getGrade().getNumericalGrade()) - 0.5;
        double examWorth = 1 - totalWorth;


        double result1 = (gradeAsPoints - totalToSubtract);
        double result2 = result1 / examWorth;
        int result3 = (int) Math.round(result2);

        return Grade.getAlphaGrade(result3);

    }


}

