package GPACalc;

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


    private final DefaultListModel<Module> moduleModel = new DefaultListModel<>();
    private final DefaultListModel<Module> preloadedModel = new DefaultListModel<>();

    private final Path path;
    private Module selected = null;

    public SwingGPACalc(Path path, List<Module> preloadedList) {

        this.path = path;
        for (Module module : preloadedList) {
            preloadedModel.addElement(module);
        }


        moduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        moduleList.addListSelectionListener(this);
        moduleList.setModel(moduleModel);

        setContentPane(mainPanel);
        setTitle("GPA Calculator");
        setSize(700, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addBtn.addActionListener(this);
        removeBtn.addActionListener(this);
        changeGradeBtn.addActionListener(this);
        changeBtn.addActionListener(this);
        calculateBtn.addActionListener(this);
        addPreloadedBtn.addActionListener(this);

        newGradeField.setEnabled(false);
        changeGradeBtn.setEnabled(false);
        newCreditField.setEnabled(false);
        changeBtn.setEnabled(false);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if (addBtn.equals(source)) {
            try {
                JTextField moduleName = new JTextField();
                JComboBox<Grade> grade = new JComboBox<>(Grade.values());
                JTextField credit = new JTextField(2);
                String msg = "Enter name,grade and amount of credits...";
                int result = JOptionPane.showOptionDialog(this, new Object[]{msg, moduleName, grade, credit}, "Module Adder", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                System.out.println(result);
                if (result == 0) {
                    String moduleName2 = moduleName.getText();
                    Grade gradeToAdd = (Grade) grade.getSelectedItem();
                    int creditToAdd = Integer.parseInt(credit.getText());
                    Module newModule = new Module(moduleName2, creditToAdd, gradeToAdd);
                    System.out.println(result);

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
                System.out.println("Something went wrong");
            }
        } else if (removeBtn.equals(source)) {
            int indexOfSelected = moduleList.getSelectedIndex();
            moduleModel.remove(indexOfSelected);
        } else if (changeGradeBtn.equals(source)) {
            if (selected != null) {
                selected.setGrade((Grade) newGradeField.getSelectedItem());
            }
        } else if (changeBtn.equals(source)) {
            try {
                int credits = Integer.parseInt(newCreditField.getText());
                selected.setCredits(credits);
            } catch (NumberFormatException dummy) {
                errorLabel.setText("The number of credits has to be an integer");
            }


        } else if (calculateBtn.equals(source)) {
            double res = Calculator.calculateGPA(Arrays.asList(moduleModel.toArray()));
            double res2 = Math.round(res);

            gpaLabel.setText("GPA is : " + res2 + " ( " + res + " ) " + " or " + Grade.getAlphaGrade((int) res2));


        } else if (addPreloadedBtn.equals(source)) {
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

        }


    }

    public static void main(String[] args) {
        Path path = Paths.get("src/GPACalc/modules");   // need to fill in path
        List<Module> preloaded = FileManipulator.readFile(path);
        System.out.println(preloaded);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SwingGPACalc GUI = new SwingGPACalc(path, preloaded);

            }


        });
    }


    private void createUIComponents() {
        newGradeField = new JComboBox(Grade.values());

    }

    public void setSubject() {
        subjectLabel.setText(selected.getModuleName());
        newGradeField.setSelectedItem(selected.getGrade());
        newCreditField.setText(String.valueOf(selected.getCredits()));

        newGradeField.setEnabled(true);
        changeGradeBtn.setEnabled(true);
        newCreditField.setEnabled(true);
        changeBtn.setEnabled(true);

    }

    public void valueChanged(ListSelectionEvent e) {
        selected = (Module) moduleList.getSelectedValue();
        if (selected == null) {
            subjectLabel.setText("Choose or add subject");
            newGradeField.setEnabled(false);
            changeGradeBtn.setEnabled(false);
            newCreditField.setEnabled(false);
            changeBtn.setEnabled(false);
        } else {
            setSubject();
        }
    }
}
