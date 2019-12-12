package my.sle.math;

public class SLESolverMatrixMethod implements SLESolver {

    @Override
    public double[] solve(MatrixD sle) throws CantSolveException, ShapesNotAlignedException {
        var a = sle.slice(0, sle.getCols() - 1);
        var b = sle.slice(sle.getCols() - 1, sle.getCols());

        return a.makeInvertable().multiply(b).transpose().getMatrix()[0];
    }
}
