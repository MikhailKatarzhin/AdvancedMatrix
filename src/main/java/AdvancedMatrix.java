import java.security.InvalidParameterException;
import java.util.ArrayList;

public class AdvancedMatrix {
    private final ArrayList<ArrayList<Double>> matrix;
    private ArrayList<ArrayList<Double>> adjugateMatrix = null;
    private final ArrayList<Double> freeTerms;
    private ArrayList<Double> variables;
    private Double determinant;

    public AdvancedMatrix(ArrayList<ArrayList<Double>> matrix, ArrayList<Double> freeTerms) throws InvalidParameterException{
        if (matrix.size() < 1)
            throw new InvalidParameterException("Matrix size mast be at least 1");
        if (matrix.size() != freeTerms.size())
            throw new InvalidParameterException("Matrix size don't equals freeTerms size");
        this.matrix = matrix;
        this.freeTerms = freeTerms;
        long millis = System.nanoTime();
        computeAdjugateMatrix();
        System.out.println("Timer of compute adjugate matrix: " + 1.0*(System.nanoTime() - millis)/1000000);
        computeDeterminantFromAdjugateMatrix();
        System.out.println("Timer of compute determinant using adjugate matrix: " + 1.0*(System.nanoTime() - millis)/1000000);
        computeVariables();
        System.out.println("Timer of compute slae: " + 1.0*(System.nanoTime() - millis)/1000000);
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
                double tmp = computeDeterminant(minor);
                tmp *= matrix.get(0).get(i);
                tmp *= (i%2 == 1 ? -1 : 1);
                determinant += tmp;
            }
        }else if (matrix.size() == 2){
            determinant = matrix.get(0).get(0) * matrix.get(1).get(1) - matrix.get(0).get(1) * matrix.get(1).get(0);
        }else {
            determinant = matrix.get(0).get(0);
        }
        return determinant;
    }

    private void computeDeterminantFromAdjugateMatrix(){
        if (matrix.size() == 1){
            determinant = matrix.get(0).get(0);
            return;
        }
        determinant = 0D;
        for (int i = 0; i < matrix.size(); i++)
            determinant += adjugateMatrix.get(0).get(i) * matrix.get(0).get(i);
    }

    private void computeAdjugateMatrix(){
        adjugateMatrix = new ArrayList<>();
        if (matrix.size() == 1){
            adjugateMatrix.add(new ArrayList<>());
            adjugateMatrix.get(0).add(1D);
            return;
        }
        for (int i = 0; i < matrix.size(); i++){
            adjugateMatrix.add(new ArrayList<>());
            for (int j = 0; j < matrix.size(); j++) {
                ArrayList<ArrayList<Double>> minor = new ArrayList<>();
                for (int m = 0; m < matrix.size(); m++)
                    if (m != i){
                        minor.add(new ArrayList<>());
                        for (int n = 0; n < matrix.size(); n++)
                            if (n != j)
                                minor.get(m > i ? m - 1 : m).add(matrix.get(m).get(n));
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

    /**
     * @return null if determinant is 0
     */
    public ArrayList<Double> getVariables() {
        if (determinant == 0D)
            return null;
        return new ArrayList<>(variables);
    }

    public void printAdvancedMatrix(){
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.size(); j++)
                System.out.printf("A[%d][%d] %s%.5f\t", i, j, matrix.get(i).get(j) >= 0 ? "+" : "", matrix.get(i).get(j));
            System.out.println(" = B[" + i + "] " + freeTerms.get(i));
        }
    }

    public Double getDeterminant() {
        return determinant;
    }
}
