package my.sle.math;

public class VectorD {
    private double[] data;

    public VectorD(int size) {
        data = new double[size];
    }

    public VectorD(double[] vector) {
        data = vector.clone();
    }

    public double[] getData() {
        return data;
    }

    public int getSize() {
        return data.length;
    }

    @Override
    public String toString() {
        var repr = "";

        for (int i = 0; i < getSize(); i++) {
            repr = repr.concat(Double.toString(data[i]));
            if (i < getSize() - 1)
                repr = repr.concat(" ");
        }

        return repr;
    }
}
