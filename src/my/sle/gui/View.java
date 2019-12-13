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
//        TODO Try to add ScrollPane
        viewFrame = new JFrame(title);
        viewFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        viewFrame.setSize(600, 600);
        viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
        viewFrame.setContentPane(viewPanel);


        sizeLabel = new JLabel("Количество переменных");
        sizeTextField = new JTextField("");
        setSizeTextFieldBtn = new JButton("Применить");
        sizeTextField.setPreferredSize(new Dimension(50, 25));
        solveBtn = new JButton("Решить");

        sleParamsPanel = new JPanel(new FlowLayout());
        sleParamsPanel.add(sizeLabel);
        sleParamsPanel.add(sizeTextField);
        sleParamsPanel.add(setSizeTextFieldBtn);
        sleParamsPanel.add(solveBtn);
        viewPanel.add(sleParamsPanel);


        slePanel = new JPanel();
        viewPanel.add(slePanel);

        answerPanel = new JPanel();
        viewPanel.add(answerPanel);

        viewFrame.pack();
    }

    public void updateView(int size) {
        int rows = size;
        int cols = size + 1;

        slePanel.removeAll();

        sizeTextField.setText(Integer.toString(size));

        slePanel.setLayout(new GridLayout(rows, cols));

        factorsFields = new JTextField[rows][cols];
        variableNameFieldsLabels = new JLabel[rows][cols];

        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++) {
                if (col == cols - 1) {
                    variableNameFieldsLabels[row][col] = new JLabel("=");
                    factorsFields[row][col] = new JTextField("0");

                    slePanel.add(variableNameFieldsLabels[row][col]);
                    slePanel.add(factorsFields[row][col]);
                }
                else {
                    variableNameFieldsLabels[row][col] = new JLabel("X".concat(Integer.toString(col + 1)));
                    factorsFields[row][col] = new JTextField("0");

                    slePanel.add(factorsFields[row][col]);
                    slePanel.add(variableNameFieldsLabels[row][col]);
                }
            }

        viewFrame.validate();
        viewFrame.pack();
    }

    public void updateView(ResultData results) {
        answerPanel.removeAll();

        answerPanel.setLayout(new GridLayout(results.getResult().length, results.getResult()[0].length));

        for (int row = 0; row < results.getResult().length; row++) {
            answerPanel.add(new JLabel(results.getMethods()[row]));
            if (results.getResult()[row] == null)
                answerPanel.add(new JLabel("Метод не подходит для решения системы"));
            else {
                resultLabels = new JLabel[results.getResult()[row].length];
                for (int col = 0; col < results.getResult()[row].length; col++) {
                    resultLabels[col] = new JLabel("X".concat(Integer.toString(col)).concat(": ").concat(Double.toString(results.getResult()[row][col])));
                    answerPanel.add(resultLabels[col]);
                }
            }
        }

        viewFrame.validate();
        viewFrame.pack();
    }

    public JFrame getViewFrame() {
        return viewFrame;
    }

    public JButton getSetSizeTextFieldBtn() {
        return setSizeTextFieldBtn;
    }

    public JTextField[][] getFactorsFields() {
        return factorsFields;
    }

    public JLabel[] getResultLabels() {
        return resultLabels;
    }

    public JButton getSolveBtn() {
        return solveBtn;
    }

    public JTextField getSizeTextField() {
        return sizeTextField;
    }
}
