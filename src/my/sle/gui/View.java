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

    private JButton fileLoadBtn;
    private JButton fileSaveBtn;
    private JTextField sizeTextField;
    private JButton setSizeTextFieldBtn;

    private JTextField[][] factorsFields;
    private JLabel[][] variableNameFieldsLabels;
    private JButton solveBtn;


    public View(String title) {
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
        fileLoadBtn = new JButton("Загрузить");
        fileSaveBtn = new JButton("Сохранить");

        sleParamsPanel = new JPanel(new FlowLayout());
        sleParamsPanel.add(fileLoadBtn);
        sleParamsPanel.add(fileSaveBtn);
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

    public void updateView(SLEResultData[] results) {
        final String outputPattern = "%s: %s";
        answerPanel.removeAll();

        answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.PAGE_AXIS));
        var answersLabels = new JLabel[results.length];

        for (int resRow = 0; resRow < results.length; resRow++) {
            if (results[resRow].getResult() == null) {
                answersLabels[resRow] = new JLabel(String.format(outputPattern, results[resRow].getMethod(), results[resRow].getErrorMessage()));
            }
            else {
                String resultStr = "";

                for (int resCol = 0; resCol < results[resRow].getResult().length; resCol++)
                    resultStr += Double.toString(results[resRow].getResult()[resCol]).concat(" ");

                answersLabels[resRow] = new JLabel(String.format(outputPattern, results[resRow].getMethod(), resultStr));
            }

            answerPanel.add(answersLabels[resRow]);
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

    public JButton getSolveBtn() {
        return solveBtn;
    }

    public JTextField getSizeTextField() {
        return sizeTextField;
    }

    public JButton getFileLoadBtn() {
        return fileLoadBtn;
    }

    public JButton getFileSaveBtn() {
        return fileSaveBtn;
    }
}
