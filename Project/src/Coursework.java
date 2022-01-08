package GPACalcV2a;

public class Coursework extends JListObject {

    private int worth;

    public Coursework(String name, Grade grade, int worth) {
        super(name, grade);
        this.worth = worth;

    }

    public void setWorth(int worth) {
        this.worth = worth;

    }

    public double worthXPoints() {
        double gradeNum = this.getGrade().getNumericalGrade();
        return (this.getWorth() / 100) * gradeNum;

    }

    public double getWorth() {
        return worth;
    }

}
