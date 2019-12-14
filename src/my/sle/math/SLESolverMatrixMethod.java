package my.sle.math;

public class SLESolverMatrixMethod implements SLESolver {
    private static final String methodName = "Матричный метод";

    @Override
    public double[] solve(MatrixD sle) throws DeterminantIsZeroException, ShapesNotAlignedException, InconsistentSLEException {
        MatrixD a = sle.slice(0, sle.getCols() - 1);
        MatrixD b = sle.slice(sle.getCols() - 1, sle.getCols());

        return a.makeInvertable().multiply(b).transpose().getMatrix()[0];
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    @Override
    public boolean isInconsistent(MatrixD sle) {
        return false;
    }
}
