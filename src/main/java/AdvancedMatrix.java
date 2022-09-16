import java.security.InvalidParameterException;
import java.util.ArrayList;

public class AdvancedMatrix {
    private final ArrayList<ArrayList<Double>> matrix;
    private ArrayList<ArrayList<Double>> AdjugateMatrix = null;
    private final ArrayList<Double> freeTerms;
    private ArrayList<Double> variables;
    private Double determinant = null;

    public AdvancedMatrix(ArrayList<ArrayList<Double>> matrix, ArrayList<Double> freeTerms) throws InvalidParameterException{
        if (matrix.size() < 2)
            throw new InvalidParameterException("Matrix size mast be at least 2");
        if (matrix.size() != freeTerms.size())
            throw new InvalidParameterException("Matrix size don't equals freeTerms size");
        this.matrix = matrix;
        this.freeTerms = freeTerms;
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

    /**
     * lazy load determinant
     */
    public double getDeterminant() {
        if (determinant == null)
            determinant = computeDeterminant(this.matrix);
        return determinant;
    }

    private double computeDeterminant(ArrayList<ArrayList<Double>> matrix){
        double determinant = 0;
        if (matrix.size() > 2){
            for (int i = 0; i < matrix.size(); i++){
                if (matrix.get(0).get(i) == 0) continue;
                ArrayList<ArrayList<Double>> minor = new ArrayList<>();
                for (int m = 0; m < matrix.size() - 1; m++){
                    minor.add(new ArrayList<>());
                    for (int n = 0; n < matrix.size(); n++){
                        if (n == i) continue;
                        minor.get(m).add(matrix.get(m+1).get(n));
                    }
                }
                determinant += computeDeterminant(minor) * matrix.get(0).get(i) * i%2 == 1 ? -1 : 1;
            }
        }else{
            determinant = matrix.get(0).get(0) * matrix.get(1).get(1) - matrix.get(0).get(1) * matrix.get(1).get(0);
        }
        return determinant;
    }
}
