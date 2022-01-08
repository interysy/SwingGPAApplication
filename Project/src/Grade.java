package GPACalcV2a;

import java.util.HashMap;
import java.util.Map;

public enum Grade {
    A1(22), A2(21), A3(20), A4(19), A5(18), B1(17), B2(16), B3(15), C1(14), C2(13), C3(12), D1(11), D2(10),
    D3(9), E1(8), E2(7), E3(6), F1(5), F2(4), F3(3), G1(2), G2(1), H(0);

    private final int grade;
    private static Map<Integer, Grade> conversion = new HashMap<>();
    private static Map<Grade,Double> conversion2 = new HashMap<>();

    Grade(int grade) {

        this.grade = grade;

    }

    public int getNumericalGrade() {
        return grade;
    }


    public static Grade getAlphaGrade(int nGrade) {
        for (Grade grade1 : Grade.values()) {
            conversion.put(grade1.getNumericalGrade(), grade1);
        }
        return conversion.get(nGrade);
    }

    public static double getPercentage(Grade grade) {
        double[] dummyList = {92,85,79,74,70,67,64,60,57,54,50,47,44,40,37,34,30,27,24,20,15,10,0};
        for (int i = 0; i < dummyList.length; i++) {
            conversion2.put(Grade.values()[i],dummyList[i]);
        }

        return conversion2.get(grade);


    }
}
