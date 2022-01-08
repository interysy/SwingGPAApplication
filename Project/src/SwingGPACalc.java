package GPACalcV2a;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


public class SwingGPACalc extends JFrame implements ActionListener, ListSelectionListener {
    private JList moduleList;
    private JButton addBtn;
    private JButton removeBtn;
    private JLabel subjectLabel;
    private JButton changeGradeBtn;
    private JButton changeBtn;
    private JLabel gradeLabel;
    private JLabel creditsLabel;
    private JLabel gpaLabel;
    private JButton calculateBtn;
    private JPanel mainPanel;
    private JComboBox newGradeField;
    private JTextField newCreditField;
    private JLabel errorLabel;
    private JButton addPreloadedBtn;
    private JButton neededForTargetBtn;
    private JButton examGradeBtn;
    private JButton GPABtn;


    private final DefaultListModel<Module> moduleModel = new DefaultListModel<>();  // for GPA calculations
    private final DefaultListModel<Module> preloadedModel = new DefaultListModel<>();  // for preloading modules
    private final DefaultListModel<Coursework> componentsModel = new DefaultListModel<>(); // for exam grade calculations
    private final DefaultListModel<Coursework> componentsModel2 = new DefaultListModel<>();  // for target grade calculations

    private final Path path;  // used to load the preloaded modules
    private JListObject selected = null;
    private int mode = 0;
    // 0 = GPA , 1 = Target Grade , 2 = Exam Grade


    public SwingGPACalc(Path path, List<Module> preloadedList) {

        // PRELOAD
        this.path = path;

        for (Module module : preloadedList) {
            preloadedModel.addElement(module);
        }


        // INITIAL MODEL FOR JLIST
        moduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        moduleList.addListSelectionListener(this);
        moduleList.setModel(moduleModel);

        // GUI RELATED
        setContentPane(mainPanel);
        setTitle("GPA Calculator");
        setSize(850, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addBtn.addActionListener(this);
        removeBtn.addActionListener(this);
        changeGradeBtn.addActionListener(this);
        changeBtn.addActionListener(this);
        calculateBtn.addActionListener(this);
        addPreloadedBtn.addActionListener(this);
        neededForTargetBtn.addActionListener(this);
        examGradeBtn.addActionListener(this);
        GPABtn.addActionListener(this);

        newGradeField.setEnabled(false);
        changeGradeBtn.setEnabled(false);
        newCreditField.setEnabled(false);
        changeBtn.setEnabled(false);

        setVisible(true);
    }

    // for creating option panes, used throughout the application
    public int OptionPane(String msg, JTextField name, JComboBox<Grade> grade, JTextField number, String title) {
        return JOptionPane.showOptionDialog(this, new Object[]{msg, name, grade, number}, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if (addBtn.equals(source)) {
            if (mode == 0) {
                try {
                    JTextField moduleName = new JTextField();
                    JComboBox<Grade> grade = new JComboBox<>(Grade.values());
                    JTextField credit = new JTextField(2);
                    String msg = "Enter name,grade and amount of credits...";
                    int result = OptionPane(msg, moduleName, grade, credit, "Module Adder");
                    if (result == 0) {
                        String moduleName2 = moduleName.getText();
                        Grade gradeToAdd = (Grade) grade.getSelectedItem();
                        int creditToAdd = Integer.parseInt(credit.getText());
                        Module newModule = new Module(moduleName2, gradeToAdd, creditToAdd);

                        if (moduleModel.contains(newModule)) {
                            errorLabel.setText("Module has already been added");
                        } else {
                            moduleModel.addElement(newModule);
                            selected = newModule;
                            setSubject();
                            if (!preloadedModel.contains(newModule)) {
                                FileManipulator.writeToFile(path, moduleName2, creditToAdd);
                            }
                        }
                    }
                } catch (NumberFormatException e2) {
                    errorLabel.setText("Entered invalid characters in the credits text entry");
                }
            } else if (mode == 1) {
                JTextField cName = new JTextField();
                JComboBox<Grade> grade = new JComboBox<>(Grade.values());
                JTextField worth = new JTextField(2);
                String msg = "Enter name,grade and the percentage coursework is worth...";
                //int result = JOptionPane.showOptionDialog(this, new Object[]{msg, cName, grade, worth}, "Coursework Adder", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                int result = OptionPane(msg, cName, grade, worth, "Coursework Adder");
                if (result == 0) {
                    String courseworkName = cName.getText();
                    Grade gradeToAdd = (Grade) grade.getSelectedItem();
                    int worthToAdd = Integer.parseInt(worth.getText());
                    Coursework newCoursework = new Coursework(courseworkName, gradeToAdd, worthToAdd);

                    componentsModel2.addElement(newCoursework);
                    selected = newCoursework;
                    setCoursework();
                }
            } else if (mode == 2) {
                JTextField cName = new JTextField();
                JComboBox<Grade> grade = new JComboBox<>(Grade.values());
                JTextField worth = new JTextField(2);
                String msg = "Enter name,grade and the percentage coursework is worth...";
                int result = OptionPane(msg, cName, grade, worth, "Coursework Adder");
                //int result = JOptionPane.showOptionDialog(this, new Object[]{msg, cName, grade, worth}, "Coursework Adder", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (result == 0) {
                    String courseworkName = cName.getText();
                    Grade gradeToAdd = (Grade) grade.getSelectedItem();
                    int worthToAdd = Integer.parseInt(worth.getText());
                    Coursework newCoursework = new Coursework(courseworkName, gradeToAdd, worthToAdd);

                    componentsModel.addElement(newCoursework);
                    selected = newCoursework;
                    setCoursework();
                }
            }
        } else if (removeBtn.equals(source)) {
            int indexOfSelected = moduleList.getSelectedIndex();
            switch (mode) {
                case 0:
                    moduleModel.remove(indexOfSelected);
                    break;
                case 1:
                    if (indexOfSelected != 0) {
                        componentsModel2.remove(indexOfSelected);
                    }
                    break;
                case 2:
                    if (indexOfSelected != 0) {
                        componentsModel.remove(indexOfSelected);
                    }
                    break;
            }
        } else if (changeGradeBtn.equals(source)) {
            if (selected != null) {
                selected.setGrade((Grade) newGradeField.getSelectedItem());
            }
        } else if (changeBtn.equals(source)) {
            try {
                int toChangeTo = Integer.parseInt(newCreditField.getText());
                switch (mode) {
                    case 0 -> {
                        Module temp = (Module) selected;
                        temp.setCredits(toChangeTo);
                    }
                    case 1, 2 -> {
                        Coursework temp2 = (Coursework) selected;
                        temp2.setWorth(toChangeTo);
                    }
                }
            } catch (NumberFormatException dummy) {
                errorLabel.setText("The number of credits/weight has to be an integer");
            }


        } else if (calculateBtn.equals(source)) {
            switch (mode) {
                case 0 -> {
                    double res = Calculator.calculateGPA(Arrays.asList(moduleModel.toArray()));
                    double res2 = Math.round(res);
                    gpaLabel.setText("GPA is : " + res2 + " ( " + res + " ) " + " or " + Grade.getAlphaGrade((int) res2));
                }
                case 1 -> {
                    Grade result = Calculator.calculateExamGradeForTarget(Arrays.asList(componentsModel2.toArray()));
                    gpaLabel.setText("Grade in exam needs to be at least: " + result);
                }
                case 2 -> {
                    Grade result1 = Calculator.calculateExamGrade(Arrays.asList(componentsModel.toArray()));
                    gpaLabel.setText("The exam grade must have been at least: " + result1);
                }
            }


        } else if (addPreloadedBtn.equals(source)) {
            if (mode == 0) {
                JList preloadList = new JList();
                preloadList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                preloadList.setModel(preloadedModel);
                JScrollPane pane = new JScrollPane(preloadList);
                JComboBox<Grade> grade = new JComboBox<>(Grade.values());
                JLabel msg = new JLabel("Please pick the module to add");
                JLabel msg2 = new JLabel("Grade");
                int result = JOptionPane.showOptionDialog(this, new Object[]{msg, pane, msg2, grade}, "Module Adder", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (result == 0) {
                    Grade gradeToAdd = (Grade) grade.getSelectedItem();
                    Module module = (Module) preloadList.getSelectedValue();
                    module.setGrade(gradeToAdd);

                    if (moduleModel.contains(module)) {
                        errorLabel.setText("Module has already been added");
                    } else {
                        moduleModel.addElement(module);
                        selected = module;
                        setSubject();
                    }
                }
            } else {
                addPreloadedBtn.setEnabled(false); // safety in case other functions fail to disable it
            }

        } else if (examGradeBtn.equals(source)) {
            creditsLabel.setText("The weight as a %");
            mode = 2;
            addPreloadedBtn.setEnabled(false);
            moduleList.setModel(componentsModel);
            gpaLabel.setText("Please add relevant coursework pieces to what was the module box");
            if (componentsModel.isEmpty()) {
                JComboBox<Grade> grade = new JComboBox<>(Grade.values());
                JLabel msg = new JLabel("Please enter your overall grade");
                int result = JOptionPane.showOptionDialog(this, new Object[]{msg, grade}, "Overall Grade Adder", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (result == 0) {
                    FinalGrade finalGrade = new FinalGrade((Grade) grade.getSelectedItem());
                    componentsModel.addElement(finalGrade);

                }
            }

        } else if (GPABtn.equals(source)) {
            creditsLabel.setText("Credits");
            mode = 0;
            addPreloadedBtn.setEnabled(true);
            moduleList.setModel(moduleModel);
        } else if (neededForTargetBtn.equals(source)) {
            creditsLabel.setText("The weight as a %");
            mode = 1;
            addPreloadedBtn.setEnabled(false);
            moduleList.setModel(componentsModel2);
            if (componentsModel2.isEmpty()) {
                JComboBox<Grade> grade = new JComboBox<>(Grade.values());
                JLabel msg = new JLabel("Please enter your target grade");
                int result = JOptionPane.showOptionDialog(this, new Object[]{msg, grade}, "Target Grade Adder", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (result == 0) {
                    TargetGrade targetGrade = new TargetGrade((Grade) grade.getSelectedItem());
                    componentsModel2.addElement(targetGrade);

                }
            }
        }


    }


    // Overriding the GUI form
    private void createUIComponents() {
        newGradeField = new JComboBox(Grade.values());

    }

    // Setting the selected item in the JList
    public void enabler() {
        newGradeField.setEnabled(true);
        changeGradeBtn.setEnabled(true);
        newCreditField.setEnabled(true);
        changeBtn.setEnabled(true);

    }

    public void setSubject() {
        Module tempSelected = (Module) selected;
        subjectLabel.setText(tempSelected.getName());
        newGradeField.setSelectedItem(tempSelected.getGrade());
        newCreditField.setText(String.valueOf(tempSelected.getCredits()));

        enabler();
    }

    public void setCoursework() {
        Coursework tempSelected = (Coursework) selected;
        subjectLabel.setText(tempSelected.getName());
        newGradeField.setSelectedItem(tempSelected.getGrade());
        newCreditField.setText(String.valueOf(tempSelected.getWorth()));

        enabler();

    }

    // Listener for changes in JList
    public void valueChanged(ListSelectionEvent e) {
        selected = (JListObject) moduleList.getSelectedValue();
        if (selected == null) {
            subjectLabel.setText("Choose or add subject / coursework");
            newGradeField.setEnabled(false);
            changeGradeBtn.setEnabled(false);
            newCreditField.setEnabled(false);
            changeBtn.setEnabled(false);
        } else {
            switch (mode) {
                case 0 -> setSubject();
                case 1, 2 -> setCoursework();
            }

        }
    }

    // The method enabling application execution
    public static void main(String[] args) {
        Path path = Paths.get("src/GPACalcV2a/modules");   // need to fill in path
        List<Module> preloaded = FileManipulator.readFile(path);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SwingGPACalc GUI = new SwingGPACalc(path, preloaded);

            }


        });
    }

}
