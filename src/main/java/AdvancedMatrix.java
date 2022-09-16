import java.security.InvalidParameterException;
import java.util.ArrayList;

public class AdvancedMatrix {
    private final ArrayList<ArrayList<Double>> matrix;
    private ArrayList<ArrayList<Double>> adjugateMatrix = null;
    private final ArrayList<Double> freeTerms;
    private ArrayList<Double> variables;
    private Double determinant;

    public AdvancedMatrix(ArrayList<ArrayList<Double>> matrix, ArrayList<Double> freeTerms) throws InvalidParameterException{
        if (matrix.size() < 2)
            throw new InvalidParameterException("Matrix size mast be at least 2");
        if (matrix.size() != freeTerms.size())
            throw new InvalidParameterException("Matrix size don't equals freeTerms size");
        this.matrix = matrix;
        this.freeTerms = freeTerms;
        computeAdjugateMatrix();
        computeDeterminantFromAdjugateMatrix();
        computeVariables();
    }

    private double computeDeterminant(ArrayList<ArrayList<Double>> matrix){
        double determinant = 0;
        if (matrix.size() > 2){
            for (int i = 0; i < matrix.size(); i++){
                if (matrix.get(0).get(i) == 0D) continue;
                ArrayList<ArrayList<Double>> minor = new ArrayList<>();
                for (int m = 1; m < matrix.size(); m++){
                    minor.add(new ArrayList<>());
                    for (int n = 0; n < matrix.size(); n++){
                        if (n == i) continue;
                        minor.get(m-1).add(matrix.get(m).get(n));
                    }
                }
                determinant += computeDeterminant(minor) * matrix.get(0).get(i) * i%2 == 1 ? -1 : 1;
            }
        }else{
            determinant = matrix.get(0).get(0) * matrix.get(1).get(1) - matrix.get(0).get(1) * matrix.get(1).get(0);
        }
        return determinant;
    }

    private void computeDeterminantFromAdjugateMatrix(){
        determinant = 0D;
        for (int i = 0; i < matrix.size(); i++)
            determinant += adjugateMatrix.get(0).get(i) * matrix.get(0).get(i);
    }

    private void computeAdjugateMatrix(){
        adjugateMatrix = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++){
            adjugateMatrix.add(new ArrayList<>());
            for (int j = 0; j < matrix.size(); j++) {
                if (matrix.get(i).get(j) == 0D) {
                    adjugateMatrix.get(i).add(0D);
                    continue;
                }
                ArrayList<ArrayList<Double>> minor = new ArrayList<>();
                for (int m = 0; m < matrix.size(); m++)
                    if (m != i){
                        minor.add(new ArrayList<>());
                        for (int n = 0; n < matrix.size(); n++)
                            if (n != j)
                                minor.get(m > i ? m - 1 : m).add(matrix.get(m).get(n > j ? n - 1 : n));
                    }
                adjugateMatrix.get(i).add(computeDeterminant(minor) * ((i + j) % 2 == 1 ? -1 : 1));
            }
        }
    }

    private void computeVariables(){
        if (determinant == 0D) {
            variables = null;
            return;
        }
        variables = new ArrayList<>();
        for (int i = 0; i < freeTerms.size(); i++){
            double var = 0;
            for (int j = 0; j < freeTerms.size(); j++)
                var += freeTerms.get(j) * adjugateMatrix.get(j).get(i) / determinant;
            variables.add(var);
        }
    }
}
