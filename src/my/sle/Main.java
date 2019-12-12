package my.sle;

import my.sle.math.MatrixD;
import my.sle.math.SLESolver;
import my.sle.math.SLESolverGaussJordan;
import my.sle.math.SLESolverMatrixMethod;

public class Main {

    public static void main(String[] args) {
        try {
//            double[][] arr1 = {
//                    {1, 1, 1},
//                    {1, 1, 1},
//                    {1, 1, 1},
//            };
//            double[][] arr2 = {
//                    {1, 1, 1, 1, 1, 1},
//                    {1, 1, 1, 1, 1, 1},
//                    {1, 1, 1, 1, 1, 1}
//            };
//            double[][] arr3 = {
//                    {73, 7, 6, 8},
//                    {75, 11, 2, 18},
//                    {3, 1, 40, 4},
//                    {4, 0, 9, 15},
//            };

            double[][] A = {
                    {3, 2, -1, 4, 9},
                    {2, -1, 5, 23, 0},
                    {1, 0, -1, 5, 12},
                    {1, 7, -1, 8, -12}
            };
            double[][] B = {
                    {4},
                    {23},
                    {5}
            };
//            var m1 = new MatrixD(arr1);
//            var m2 = new MatrixD(arr2);
//            var m3 = new MatrixD(arr3);

//            System.out.println(m3.det());
            var mA = new MatrixD(A);
//            System.out.println(mA.makeInvertable());

            SLESolver matrixMethod = new SLESolverMatrixMethod();
            double[] result = matrixMethod.solve(mA);
            var resultStr = "";
            for (var elem : result)
                resultStr = resultStr.concat(Double.toString(elem) + " ");
            System.out.println(resultStr);

            System.out.println();

            matrixMethod = new SLESolverGaussJordan();
            resultStr = "";
            result = matrixMethod.solve(mA);
            for (var elem : result)
                resultStr = resultStr.concat(Double.toString(elem) + " ");
            System.out.println(resultStr);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
