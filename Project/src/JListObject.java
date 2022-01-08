package GPACalcV2a;

public class JListObject {

    protected String name;
    private Grade grade;


    public JListObject(String name, Grade grade) {
        this.name = name;
        this.grade = grade;
    }


    public String getName() {
        return name;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return name;
    }


}
