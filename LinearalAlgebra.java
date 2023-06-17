import java.util.Arrays;

public class LinearalAlgebra {
    
    public static String MatrixToString(double[][] matrix) {
        String matrix_string = "";
        for (int i = 0; i < matrix.length; i++)
            matrix_string += Arrays.toString(matrix[i]) + '\n';
        return matrix_string;
    }
    
    public static double[][] Minor(double[][] matrix, int line_to_cut, int column_to_cut) throws Exception {
        for (int i = 1; i < matrix.length; i++)
            if (matrix[0].length != matrix[i].length)
                throw new Exception("Это не матрица");
        if (line_to_cut >= matrix.length)
            throw new Exception("Такой строки в матрице нет");
        if (column_to_cut >= matrix[0].length)
            throw new Exception("Такого столбца в матрице нет");
        double[][] minor = new double[matrix.length - 1][matrix[0].length - 1];
        for(int i = 0; i < line_to_cut; i++) {
            for(int j = 0; j < column_to_cut; j++)
                minor[i][j] = matrix[i][j];
            for (int j = column_to_cut + 1; j < matrix[i].length; j++)
                minor[i][j - 1] = matrix[i][j];
        }
        for(int i = line_to_cut + 1; i < matrix.length; i++) {
            for(int j = 0; j < column_to_cut; j++)
                minor[i - 1][j] = matrix[i][j];
            for (int j = column_to_cut + 1; j < matrix[i].length; j++)
                minor[i - 1][j - 1] = matrix[i][j];
        }
        return minor;
    }
    
    public static double countMatrixDeterminant(double[][] matrix) throws Exception {
        for (int i = 0; i < matrix.length; i++)
            if (matrix.length != matrix[i].length)
                throw new Exception("Это не квадратная матрица");
        if (matrix.length == 0)
            throw new Exception("Матрица пуста");
        if (matrix.length == 1) return matrix[0][0];
        if (matrix.length == 2) return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        if (matrix.length == 3) return
                                        matrix[0][0] * matrix[1][1] * matrix[2][2] + 
                                        matrix[0][1] * matrix[1][2] * matrix[2][0] +
                                        matrix[0][2] * matrix[1][0] * matrix[2][1] -
                                        matrix[0][2] * matrix[1][1] * matrix[2][0] -
                                        matrix[0][1] * matrix[1][0] * matrix[2][2] -
                                        matrix[0][0] * matrix[1][2] * matrix[2][1];
        double determinant = 0;
        for (int i = 0; i < matrix.length; i++)
            determinant += Math.pow(-1, i) * matrix[0][i] * countMatrixDeterminant(Minor(matrix, 0, i));
        return determinant;
    }

    private static double[][] replaceColumnWithVector(double[][] matrix, double[] vector, int column_num) throws Exception {
        for (int i = 1; i < matrix.length; i++)
            if (matrix[0].length != matrix[i].length)
                throw new Exception("Это не матрица");
        if ((column_num < 0)||(column_num > matrix[0].length))
            throw new Exception("Такого столбца нет в матрице");
        if (matrix.length != vector.length)
            throw new Exception("Размер вектора и столбца в матрице не совпадает");
        double[][] new_matrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                new_matrix[i][j] = matrix[i][j];
        for (int i = 0; i < vector.length; i++) {
             new_matrix[i][column_num] = vector[i];
        }
        return new_matrix;
    }
    
    public static double[] solveLinearalSystemWithCramer(double[][] equation_main_matrix, double[] equation_free_coefficients_vector) throws Exception {
        System.out.println("Решение системы методом Краммера:");
        double main_matrix_determenant = countMatrixDeterminant(equation_main_matrix);
        System.out.println("Определитель главной матрицы: " + main_matrix_determenant);
        double[] answers = new double[equation_main_matrix.length];
        for(int i = 0; i < equation_main_matrix.length; i++) {
            double[][] matrix_for_unknown = replaceColumnWithVector(equation_main_matrix, equation_free_coefficients_vector, i);
            double determinant_for_unknown = countMatrixDeterminant(matrix_for_unknown);
            answers[i] = determinant_for_unknown / main_matrix_determenant;
            System.out.println("Матрица неизвестного " + (i+1) + ":");
            System.out.println(LinearalAlgebra.MatrixToString(matrix_for_unknown));
            System.out.println("Её определитель: " + determinant_for_unknown);
            System.out.printf("%f/%f=%f\n", determinant_for_unknown, main_matrix_determenant, answers[i]);
        }
        return answers;
    }

    public static double[][] multiplyMatrixes(double[][] A, double[][] B) throws Exception {
        for (int i = 1; i < A.length; i++)
            if (A[0].length != A[i].length)
                throw new Exception("Первый массив не матрица");
        for (int i = 1; i < B.length; i++)
            if (B[0].length != B[i].length)
                throw new Exception("Второй массив не матрица");
        if (A[0].length != B.length)
            throw new Exception("Длина строк первой матрицы не совпадает с высотой столбцов второй");
        double[][] C = new double[A.length][B[0].length];
        for (int i = 0; i < C.length; i++)
            for (int j = 0; j < C[0].length; j++) {
                C[i][j] = 0;
                for (int k = 0; k < B.length; k++)
                    C[i][j] += A[i][k] * B[k][j];
            }
        return C;
    }

    public static double[][] multiply_matrix_by_number(double number, double[][] matrix) throws Exception {
        for (int i = 1; i < matrix.length; i++)
            if (matrix[0].length != matrix[i].length)
                throw new Exception("Это не матрица");
        double[][] new_matrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                new_matrix[i][j] = number * matrix[i][j];
        return new_matrix;
    }

    public static double[][] transposeMatrix(double[][] matrix) throws Exception {
        for (int i = 1; i < matrix.length; i++)
            if (matrix[0].length != matrix[i].length)
                throw new Exception("Это не матрица");
        double[][] transposed_matrix = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                transposed_matrix[j][i] = matrix[i][j];
        return transposed_matrix;    
    }

    public static double[][] inverseMatrix(double[][] matrix) throws Exception {
        double determinant = countMatrixDeterminant(matrix);
        if (determinant == 0)
            throw new Exception("Обратная матрица не существует, так как определитель равен нулю");
        double[][] minor_matrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < minor_matrix.length; i++)
            for (int j = 0; j < minor_matrix[0].length; j++)
                minor_matrix[i][j] = countMatrixDeterminant(Minor(matrix, i, j)) * Math.pow(-1, i+j);
        double[][] matrix_of_algebraic_compliments = transposeMatrix(minor_matrix);
        return multiply_matrix_by_number(1 / determinant, matrix_of_algebraic_compliments);
    }
}
