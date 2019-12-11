package my.sle.math;

public class SLESolverMatrixMethod implements SLESolver {

    @Override
    public MatrixD solve(MatrixD sle) throws CantSolveException, ShapesNotAlignedException {
        MatrixD a = sle.slice(0, sle.getCols() - 1);
        MatrixD b = sle.slice(sle.getCols() - 1, sle.getCols());

        return a.makeInvertable().multiply(b);
    }
}
