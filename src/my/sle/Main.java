package my.sle;

import my.sle.gui.View;
import my.sle.math.MatrixD;
import my.sle.math.SLESolver;
import my.sle.math.SLESolverGaussJordan;
import my.sle.math.SLESolverMatrixMethod;

public class Main {

    public static void main(String[] args) {
        try {
            View view = new View("Решение СЛАУ");
            view.getViewFrame().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
