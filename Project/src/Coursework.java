package GPACalc;

public class Coursework{

    private String name;
    private double worth;
    private Grade grade;


    public Coursework(String name) {
        this.name = name;
    }

    public double worthXPoints() {
        return worth*grade.getGrade();


    }

    public double getWorth() {
        return worth;
    }

    public String getName() {
        return name;
    }
}
