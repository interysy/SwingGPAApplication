package GPACalc;

import java.util.Objects;

public class Module {

    private String moduleName;
    private int credits;
    private Grade grade;

    public Module(String moduleName, int credits, Grade grade) {
        this.moduleName = moduleName;
        this.credits = credits;
        this.grade = grade;

    }

    public int creditsMgrade() {
        return credits * grade.getGrade();

    }

    public int getCredits() {
        return credits;
    }

    public Grade getGrade() {
        return grade;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void setModuleName(String name) {
        this.moduleName = name;
    }

    public String toString() {
        return moduleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return credits == module.credits && moduleName.equals(module.moduleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleName, credits);
    }
}
