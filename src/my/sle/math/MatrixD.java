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

        for (int i = 0; i < factor.rows; i++)
            for (int j = 0; j < factor.rows; j++)
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
