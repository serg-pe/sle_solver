package my.sle.gui;

import my.sle.math.*;

import javax.swing.*;
import java.awt.*;

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

    private ResultData solveSLE(MatrixD sle) {
        SLESolver[] solvers = {new SLESolverMatrixMethod(), new SLESolverGaussJordan()};
        double[][] result = new double[solvers.length][];
        String[] methods = new String[solvers.length];

        for (int solverIndex = 0; solverIndex < solvers.length; solverIndex++)
            try {
                methods[solverIndex] = solvers[solverIndex].getMethodName();
                result[0] = solvers[solverIndex].solve(sle);
            } catch (CantSolveException | ShapesNotAlignedException exc) {
                result[0] = null;
            }

        return new ResultData(result, methods);
    }

    private void onSolveSLEBtn() {
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
            JOptionPane.showMessageDialog(null, "Введите дробное или целое число", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
            return;
        }

        view.updateView(solveSLE(new MatrixD(sleParams)));
    }

}
