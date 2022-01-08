package GPACalcV2a;

import java.util.Objects;

public class Module extends JListObject {

    private int credits;

    public Module(String name, Grade grade, int credits) {
        super(name, grade);
        this.credits = credits;

    }

    public int creditsMgrade() {
        return credits * this.getGrade().getNumericalGrade();
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return credits == module.credits && name.equals(module.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(credits);
    }
}
