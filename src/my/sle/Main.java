package my.sle;

import my.sle.math.MatrixD;
import my.sle.math.SLESolver;
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
                    {3, 2, -1, 4},
                    {2, -1, 5, 23},
                    {1, 7, -1, 5}
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

            var matrixMethod = new SLESolverMatrixMethod();
            System.out.println(matrixMethod.solve(mA));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
