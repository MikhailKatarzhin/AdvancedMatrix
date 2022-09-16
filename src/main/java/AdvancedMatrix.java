import java.security.InvalidParameterException;
import java.util.ArrayList;

public class AdvancedMatrix {
    private final ArrayList<ArrayList<Double>> matrix;
    private final ArrayList<Double> freeTerms;
    private ArrayList<Double> variables;
    private double determinant;

    public AdvancedMatrix(ArrayList<ArrayList<Double>> matrix, ArrayList<Double> freeTerms) {
        if (matrix.size() != freeTerms.size())
            throw new InvalidParameterException("Matrix size don't equals freeTerms size");
        this.matrix = matrix;
        this.freeTerms = freeTerms;
        this.determinant = computeDeterminant(this.matrix);
    }

    public ArrayList<ArrayList<Double>> getMatrix() {
        return matrix;
    }

    public ArrayList<Double> getFreeTerms() {
        return freeTerms;
    }

    public ArrayList<Double> getVariables() {
        return variables;
    }

    public double getDeterminant() {
        return determinant;
    }

    private double computeDeterminant(ArrayList<ArrayList<Double>> matrix){
        double determinant = 0;
        if (matrix.size() > 3){
            for (int i = 0; i < matrix.size(); i++){
                if (matrix.get(0).get(i) != 0) {
                    ArrayList<ArrayList<Double>> minor = new ArrayList<>();
                    for (int m = 1; m < matrix.size() - 1; m++){
                        minor.add(new ArrayList<>());
                        for (int n = 0; n < matrix.size() - 1; n++){
                            if (n == i) continue;
                            minor.get(m).add(matrix.get(m).get(n));
                        }
                    }
                    determinant += computeDeterminant(minor);
                }
            }
        }else{
            for (int i = 0; i < matrix.size(); i++) {
                double multiplier = 1;
                for (int j = 0; j < matrix.size(); j++) {
                    multiplier *= matrix.get((j + i) >= matrix.size() ? (i) : (j + i)).get(j);
                }
                determinant += multiplier;
            }
        }
        return determinant;
    }
}
