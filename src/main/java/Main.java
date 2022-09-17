import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

    long millis = System.nanoTime();

    final int n = 9;
    ArrayList<ArrayList<Double>> matrix = new ArrayList<>(n);
    ArrayList<Double> freeTerms = new ArrayList<>(n);

    for (int i = 0; i < n; i++){
        matrix.add(new ArrayList<>());
        for (int j = 0; j < n; j++)
            matrix.get(i).add(/*(double)((int)*/(Math.random()*100 - 50))/*)*/;
        freeTerms.add(/*(double)((int)*/(Math.random()*100 - 50))/*)*/;
    }

    ///создаём СЛАУ на основе матрицы и столбце свободных членов
    AdvancedMatrix slae = new AdvancedMatrix(matrix, freeTerms);

    ///Вывод системы
    System.out.println("\nSystem of linear algebraic equations:\n");
    slae.printAdvancedMatrix();
    System.out.println("\nDeterminant = " + slae.getDeterminant());

    ///Вывод результата
    System.out.println("\nVariables of System of linear algebraic equations:\n");
    ArrayList<Double> variables= slae.getVariables();
    for (int i = 0; i < variables.size(); i++)
        System.out.println("X[" + i + "] = " + variables.get(i));

    System.out.println("Timer of program: " + 1.0*(System.nanoTime() - millis)/1000000);
}
}
