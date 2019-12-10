package my.sle.math;

import java.util.Arrays;

public class MatrixD {
    private int rows;
    private int cols;

    private double[][] matrix;

    public MatrixD(int size) {
        rows = cols = size;
        matrix = new double[rows][cols];

    }

    public MatrixD(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        matrix = new double[rows][cols];
    }

    public MatrixD(double[][] array) {
        rows = array.length;
        cols = array[0].length;
        matrix = array.clone();
        for (int i = 0; i < rows; i++)
            matrix[i] = array[i].clone();
    }

    private double[] getRow(int number) {
        return matrix[number].clone();
    }

    private MatrixD addStr() {
        return null;
    }

    public double det() throws ShapesNotAlignedException {
        /*
        * Метод Гаусса
        */
        double det = 0;

        if (cols != rows)
            throw new ShapesNotAlignedException(String.format("No determinant for non-square matrix (%d, %d)", cols, rows));

        if (rows == 1)
            return matrix[0][0];

        if (rows == 2)
            return matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];

        for (int i = 1; i < rows; i++) {
            int subRowIndex = i - 1;

            for (int row = i; row < rows; row++) {
                var rate = -(matrix[i][i - 1] / matrix[subRowIndex][subRowIndex]);
                matrix[row][i - 1] = 0;
                for (int j = row; j < cols; j++) {

                    matrix[row][j] += matrix[subRowIndex][j] * rate;
                }
            }
        }

        for (int i = 0; i < rows; i++)
            det *= matrix[i][i];

        return det;
    }

    public MatrixD transpose() {
        MatrixD result = new MatrixD(cols, rows);

        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++)
                result.matrix[j][i] = this.matrix[i][j];

        return result;
    }

    public MatrixD multiply(MatrixD factor) throws ShapesNotAlignedException {
        if (this.cols != factor.rows)
            throw new ShapesNotAlignedException(String.format("Shapes not aligned for (%d, %d) & (%d, %d)", this.rows, this.cols, factor.rows, factor.cols));

        MatrixD result = new MatrixD(this.rows, factor.cols);

        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < factor.cols; j++)
                for (int k = 0; k < factor.rows; k++)
                    result.matrix[i][j] += this.matrix[i][k] * factor.matrix[k][j];

        return result;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    @Override
    public String toString() {
        String repr = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                repr += Double.toString(matrix[i][j]);
                if (j != cols - 1)
                    repr += " ";
            }
            if (i != rows - 1)
                repr += "\n";
        }
        return repr;
    }
}
