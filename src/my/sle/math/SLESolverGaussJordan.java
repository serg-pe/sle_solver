package my.sle.math;

public class SLESolverGaussJordan implements SLESolver {

    private MatrixD straightForward(MatrixD sle) {
        MatrixD result = new MatrixD(sle);

        for (int rowsTransforms = 1; rowsTransforms < result.getRows(); rowsTransforms++) {
            int elementToZeroIndex = rowsTransforms - 1;
            int currentRowIndex = rowsTransforms - 1;

            for (int transformedRowIndex = rowsTransforms; transformedRowIndex < result.getRows(); transformedRowIndex++) {
                if (result.getMatrix()[transformedRowIndex][elementToZeroIndex] != 0) {
                    double factor = -(result.getMatrix()[currentRowIndex][elementToZeroIndex] / result.getMatrix()[transformedRowIndex][elementToZeroIndex]);
                    result = result.multiplyRow(transformedRowIndex, factor).sumRows(currentRowIndex, transformedRowIndex);
                }
            }
        }

        return result;
    }

    private MatrixD backForward(MatrixD sle) {
        MatrixD result = new MatrixD(sle);

        for (int rowsTransforms = result.getRows() - 1; rowsTransforms > -1; rowsTransforms--) {
            int elementToZeroIndex = rowsTransforms;
            int currentRowIndex = rowsTransforms;

            for (int transformedRowIndex = rowsTransforms; transformedRowIndex > -1; transformedRowIndex--) {
                if (result.getMatrix()[transformedRowIndex][elementToZeroIndex] != 0) {
                    double factor = -(result.getMatrix()[currentRowIndex][elementToZeroIndex] / result.getMatrix()[transformedRowIndex][elementToZeroIndex]);
                    result = result.multiplyRow(transformedRowIndex, factor).sumRows(currentRowIndex, transformedRowIndex);
                }
            }
        }

        return result;
    }

    private double[] getDiagonal(MatrixD matrix) {
        double[] diagonal = new double[matrix.getRows()];

        for (int row = 0; row < diagonal.length; row++)
            diagonal[row] = matrix.getMatrix()[row][matrix.getCols() - 1];

        return diagonal;
    }

    @Override
    public double[] solve(MatrixD sle) throws CantSolveException, ShapesNotAlignedException {
        MatrixD result = new MatrixD(sle);

        if (result.getMatrix()[0][0] == 0)
            for (int row = 1; row < sle.getRows(); row++)
                if (result.getMatrix()[row][0] != 0) {
                    result = result.exchangeRows(0, row);
                    break;
                }

        result = straightForward(result);
        result = backForward(result);

        for (int row = 0; row < result.getRows(); row++)
            result = result.divideRow(row, result.getMatrix()[row][row]);

        return getDiagonal(result);
    }
}
