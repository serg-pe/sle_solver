package my.sle.gui;

import javax.swing.*;
import java.awt.*;

public class View {
    private JFrame viewFrame;
    private JPanel viewPanel;
    private JPanel sleParamsPanel;
    private JPanel slePanel;
    private JPanel answerPanel;
    private JLabel sizeLabel;
    private JTextField sizeTextField;
    private JButton setSizeTextFieldBtn;
    private JTextField[][] factorsFields;
    private JLabel[][] variableNameFieldsLabels;
    private JLabel[] resultLabels;
    private JButton solveBtn;


    public View(String title) {
        viewFrame = new JFrame(title);
        viewFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        viewFrame.setSize(600, 600);
        viewPanel = new JPanel(new GridBagLayout());
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
        viewFrame.setContentPane(viewPanel);


        sizeLabel = new JLabel("Количество переменных");
        sizeTextField = new JTextField("");
        setSizeTextFieldBtn = new JButton("Применить");
        sizeTextField.setPreferredSize(new Dimension(60, 25));
        solveBtn = new JButton("Решить");

        sleParamsPanel = new JPanel(new FlowLayout());
        sleParamsPanel.add(sizeLabel);
        sleParamsPanel.add(sizeTextField);
        sleParamsPanel.add(setSizeTextFieldBtn);
        sleParamsPanel.add(solveBtn);
        viewPanel.add(sleParamsPanel);




    }

    public JFrame getViewFrame() {
        return viewFrame;
    }

    public JPanel getViewPanel() {
        return viewPanel;
    }

    public JTextField getSizeTextField() {
        return sizeTextField;
    }

    public JButton getSetSizeTextFieldBtn() {
        return setSizeTextFieldBtn;
    }

    public JTextField[][] getFactorsFields() {
        return factorsFields;
    }

    public JLabel[][] getVariableNameFieldsLabels() {
        return variableNameFieldsLabels;
    }

    public JLabel[] getResultLabels() {
        return resultLabels;
    }

    public JButton getSolveBtn() {
        return solveBtn;
    }

}
