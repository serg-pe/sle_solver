package my.sle.gui;

import my.sle.math.MatrixD;

import javax.swing.*;
import java.awt.*;

// Представление - внешний вид программы.
// Включает в себя окно, компоненты и методы для обновления окна и его компонентов
// Используется в Контроллере (Controller)
// Роль - отображать информацию пользователю. "Пользователь видит Представление"
public class View {
    private JFrame viewFrame; // Главное окно
    private JPanel viewPanel; // Контейнер для компонентов окна
    private JPanel sleParamsPanel; // Контейнер для кнопок загрузки, сохранения, применения размера матрицы, решения и поля ввода размера
    private JPanel slePanel; // Контейнер с компонентами ввода СЛАУ
    private JPanel answerPanel; // Контейнер для вывода ответа
    private JLabel sizeLabel;

    private JButton fileLoadBtn;
    private JButton fileSaveBtn;
    private JTextField sizeTextField;
    private JButton setSizeTextFieldBtn;

    private JTextField[][] factorsFields;
    private JLabel[][] variableNameFieldsLabels;
    private JButton solveBtn;

//    Инициализация компонентов окна
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

//    Обновление внешнего вида окна при изменении размера матрицы
//    вызывается нажатием кнопки Примеить (setSizeTextFieldBtn)
    public void updateView(int size) {
        int rows = size;
        int cols = size + 1;

//        Удаление всех старых компонентов из контейнера с компонентами ввода СЛАУ
        slePanel.removeAll();
//        Установка текста в поле ввода текста
        sizeTextField.setText(Integer.toString(size));

//        Установка сетки вывода компонентов
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

//    Обновление внешнего вида окна при решении СЛАУ
//    вызывается нажатием кнопки Решить (solveBtn)
    public void updateView(SLEResultData[] results) {
        final String outputPattern = "%s: %s";
        answerPanel.removeAll();

//        Вывод в текстовые поля контейнера для вывода ответа по вертикали
        answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.PAGE_AXIS));
        JLabel[] answersLabels = new JLabel[results.length];

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

//    Обновление внешнего вида окна при получении матрицы
//    вызывается после загрузки матрицы из файла
    public void updateView(MatrixD matrix) {
        this.updateView(matrix.getRows());

        for (int row = 0; row < factorsFields.length; row++)
            for (int col = 0; col < factorsFields[row].length; col++)
                factorsFields[row][col].setText(Double.toString(matrix.getMatrix()[row][col]));

        viewFrame.validate();
        viewFrame.pack();
    }

//    Методы получения компонентов для контроллера (Controller)
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
