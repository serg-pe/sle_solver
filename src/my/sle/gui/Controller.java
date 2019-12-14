package my.sle.gui;

import my.sle.math.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void init() {
        view.updateView(model.getMatrixSize());
        view.getViewFrame().setVisible(true);

        view.getSetSizeTextFieldBtn().addActionListener(event -> this.onSetSizeBtn());
        view.getSolveBtn().addActionListener(event -> this.onSolveSLEBtn());

        view.getFileLoadBtn().addActionListener(event -> this.onLoadBtnClick());
        view.getFileSaveBtn().addActionListener(event -> this.onSaveBtnClick());
    }

    private MatrixD readMatrix() throws MatrixReadException {
        double[][] sleParams = new double[view.getFactorsFields().length][view.getFactorsFields()[0].length];

        boolean hasErrors = false;
        for (int row = 0; row < sleParams.length; row++)
            for (int col = 0; col < sleParams[row].length; col++)
                try {
                    sleParams[row][col] = Double.parseDouble(view.getFactorsFields()[row][col].getText());
                    view.getFactorsFields()[row][col].setBackground(Color.green);
                } catch (NumberFormatException e) {
                    view.getFactorsFields()[row][col].setBackground(Color.red);
                    hasErrors = true;
                }

        if (hasErrors) {
            throw new MatrixReadException("Введите дробное или целое число");
        }

        return new MatrixD(sleParams);
    }

    private void onSaveBtnClick() {
        var saveDialog = new JFileChooser();

        saveDialog.setDialogTitle("Выберите файл для сохранения");
        int userSelection = saveDialog.showDialog(view.getViewFrame(), "Сохранить");

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                MatrixD matrix = readMatrix();

                FileOutputStream fileOutput = new FileOutputStream(saveDialog.getSelectedFile().getAbsoluteFile());
                ObjectOutput objectOutput = new ObjectOutputStream(fileOutput);
                objectOutput.writeObject(matrix);

                objectOutput.flush();
                objectOutput.close();
            } catch (IOException | MatrixReadException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onLoadBtnClick() {
        var loadDialog = new JFileChooser();

        loadDialog.setDialogTitle("Выберите файл для загрузки");
        int userSelection = loadDialog.showDialog(view.getViewFrame(), "Загрузить");

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                FileInputStream fileInput = new FileInputStream(loadDialog.getSelectedFile().getAbsoluteFile());
                ObjectInputStream objectInput = new ObjectInputStream(fileInput);
                MatrixD matrix = (MatrixD) objectInput.readObject();
                fileInput.close();

                view.updateView(matrix);
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onSetSizeBtn() {
        int size;

        try {
            size = Integer.parseInt(view.getSizeTextField().getText());
            model.setMatrixSize(size);
        } catch (NumberFormatException e) {
            size = 0;
            JOptionPane.showMessageDialog(null, "Введите целое число", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
        }

        view.updateView(size);
    }

    private SLEResultData[] solveSLE(MatrixD sle) {
        SLESolver[] solvers = {new SLESolverMatrixMethod(), new SLESolverGaussJordanElimination()};
        SLEResultData[] results = new SLEResultData[solvers.length];

        for (int solverIndex = 0; solverIndex < solvers.length; solverIndex++)
            try {
                results[solverIndex] = new SLEResultData(solvers[solverIndex].solve(sle), solvers[solverIndex].getMethodName(), "");
            } catch (DeterminantIsZeroException | ShapesNotAlignedException | InconsistentSLEException e) {
                results[solverIndex] = new SLEResultData(null, solvers[solverIndex].getMethodName(), e.getMessage());
            }

        return results;
    }

    private void onSolveSLEBtn() {
        try {
            view.updateView(solveSLE(readMatrix()));
        } catch (MatrixReadException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
        }
    }

}
