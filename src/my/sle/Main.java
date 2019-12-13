package my.sle;

import my.sle.gui.Controller;
import my.sle.gui.Model;
import my.sle.gui.View;
import my.sle.math.MatrixD;
import my.sle.math.SLESolver;
import my.sle.math.SLESolverGaussJordan;
import my.sle.math.SLESolverMatrixMethod;

public class Main {

    public static void main(String[] args) {
        try {
            var model = new Model();
            var view = new View("Решение СЛАУ");
            var controller = new Controller(model, view);
            controller.init();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
