import java.util.Arrays;

public class sr2 {

    private static double getDW_l(int n, int k) throws Exception {
        if ((k < 1)||(k > 5))
            throw new Exception("Принимаются значения степени свободы только от 1 до 5");
        if ((n < 15)||(n > 40))
            throw new Exception("Принимаются значения размера выборки только от 15 до 40");
        double[][] DW_l = {
            {1.08, 1.1, 1.13, 1.16, 1.18, 1.2, 1.22, 1.24, 1.26, 1.27, 1.29, 1.3, 1.32, 1.33, 1.34, 1.35, 1.36, 1.37, 1.38, 1.39, 1.4, 1.41, 1.42, 1.43, 1.43, 1.44},
            {0.95, 0.98, 1.02, 1.05, 1.08, 1.1, 1.13, 1.15, 1.17, 1.19, 1.21, 1.22, 1.24, 1.26, 1.27, 1.28, 1.3, 1.31, 1.32, 1.33, 1.34, 1.35, 1.36, 1.37, 1.38, 1.39},
            {0.82, 0.86, 0.9, 0.93, 0.97, 1, 1.03, 1.05, 1.08, 1.1, 1.12, 1.14, 1.16, 1.18, 1.2, 1.21, 1.23, 1.24, 1.26, 1.27, 1.28, 1.29, 1.31, 1.32, 1.33, 1.34},
            {0.69, 0.74, 0.78, 0.82, 0.86, 0.9, 0.93, 0.96, 0.99, 1.01, 1.04, 1.06, 1.08, 1.1, 1.12, 1.14, 1.16, 1.18, 1.19, 1.21, 1.22, 1.24, 1.25, 1.26, 1.27, 1.29},
            {0.56, 0.62, 0.67, 0.71, 0.75, 0.79, 0.83, 0.86, 0.9, 0.93, 0.95, 0.98, 1.01, 1.03, 1.05, 1.07, 1.09, 1.11, 1.13, 1.15, 1.16, 1.18, 1.19, 1.21, 1.22, 1.23}
        };
        return DW_l[k-1][n-15];
    }

    private static double getDW_r(int n, int k) throws Exception {
        if ((k < 1)||(k > 5))
            throw new Exception("Принимаются значения степени свободы только от 1 до 5");
        if ((n < 15)||(n > 40))
            throw new Exception("Принимаются значения размера выборки только от 15 до 40");
        double[][] DW_r = {
            {1.36, 1.37, 1.38, 1.39, 1.4, 1.41, 1.42, 1.43, 1.44, 1.45, 1.45, 1.46, 1.47, 1.48, 1.48, 1.49, 1.5, 1.5, 1.51, 1.51, 1.52, 1.52, 1.53, 1.54, 1.54, 1.54},
            {1.54, 1.54, 1.54, 1.53, 1.53, 1.54, 1.54, 1.54, 1.54, 1.55, 1.55, 1.55, 1.56, 1.56, 1.56, 1.57, 1.57, 1.57, 1.58, 1.58, 1.58, 1.59, 1.59, 1.59, 1.6, 1.6},
            {1.75, 1.73, 1.71, 1.69, 1.68, 1.68, 1.67, 1.66, 1.66, 1.66, 1.66, 1.65, 1.65, 1.65, 1.65, 1.65, 1.65, 1.65, 1.65, 1.65, 1.65, 1.65, 1.66, 1.66, 1.66, 1.66},
            {1.97, 1.93, 1.9, 1.87, 1.85, 1.83, 1.81, 1.8, 1.79, 1.78, 1.77, 1.76, 1.76, 1.75, 1.74, 1.74, 1.74, 1.73, 1.73, 1.73, 1.73, 1.73, 1.72, 1.72, 1.72, 1.72},
            {2.21, 2.15, 2.1, 2.06, 2.02, 1.99, 1.96, 1.94, 1.92, 1.9, 1.89, 1.88, 1.86, 1.85, 1.84, 1.83, 1.83, 1.82, 1.81, 1.81, 1.8, 1.8, 1.8, 1.79, 1.79, 1.79}
        };
        return DW_r[k-1][n-15];
    }

    private static boolean checkErrorsWithDurbinWatson(double[] errors, int number_of_freedom_degrees) throws Exception {
        System.out.println("Проверяем наличие автокорреляции между ошибками с помошью критерия Дарбина-Уотсона");
        double[] past_errors = Arrays.copyOfRange(errors, 0, errors.length-1);
        double[] present_errors = Arrays.copyOfRange(errors, 1, errors.length);
        double ACF = Statistics.Correlation(past_errors, present_errors);
        System.out.printf("Среднее от varepsilon_1 до varepsilon_%d: (%f+...+(%f))/%d=%f\n", errors.length - 1, past_errors[0], past_errors[past_errors.length-1], past_errors.length, Statistics.Average(past_errors));
        System.out.printf("Среднее от varepsilon_%d до varepsilon_%d: (%f+...+(%f))/%d=%f\n", 2, errors.length, present_errors[0], present_errors[present_errors.length - 1], present_errors.length, Statistics.Average(present_errors));
        System.out.printf("Среднее выборочное от varepsilon_1 до varepsilon_%d: sqrt(1/%d*((%f)^2+...+(%f)^2)-(%f)^2)=%f\n", errors.length - 1, past_errors.length, past_errors[0], past_errors[past_errors.length - 1], Statistics.Average(past_errors), Statistics.StandardDeviation(past_errors));
        System.out.printf("Среднее выборочное от varepsilon_%d до varepsilon_%d: sqrt(1/%d*((%f)^2+...+(%f)^2)-(%f)^2)=%f\n", 2, errors.length, present_errors.length, present_errors[0], present_errors[present_errors.length - 1], Statistics.Average(present_errors), Statistics.StandardDeviation(present_errors));
        System.out.printf("Коэффициент автокорреляции равен (1/%d*((%f)*(%f)+...+(%f)*(%f))-(%f)*(%f))/((%f)*(%f))=%f\n", past_errors.length, past_errors[0], present_errors[0], past_errors[past_errors.length - 1], present_errors[present_errors.length - 1], Statistics.Average(past_errors), Statistics.Average(present_errors), Statistics.StandardDeviation(past_errors), Statistics.StandardDeviation(present_errors), Statistics.Correlation(past_errors, present_errors));
        System.out.println("Автокорреляция ошибок равна " + ACF);
        double DW = 2 * (1 - ACF);
        System.out.printf("Наблюдаемое значение статистики Дарбина-Уотсона равно 2*(1-(%f))=%f\n", ACF, DW);
        double DW_l = getDW_l(errors.length, number_of_freedom_degrees);
        double DW_r = getDW_r(errors.length, number_of_freedom_degrees);
        System.out.printf("Нижние и верхние критические значения статистики Дарбина-Уотсона при уровне значимости 0,05 для размера выборки %d и количества степенией свободы %d равны %f и %f соответсвенно\n", errors.length, number_of_freedom_degrees, DW_l, DW_r);
        if (DW < DW_l) {
            System.out.println("Наблюдаемое значение статистики Дарбина-Уотсона меньше DW_l. Автокорреляция ошибок есть.");
            return true;
        }
        else if ((DW_l < DW)&&(DW < DW_r))
            System.out.println("Наблюдаемое значение статистики Дарбина-Уотсона больше DW_l, но меньше DW_u. Наличие или отсуствие автокорреляции ошибок не определено.");
        else if ((DW > DW_r)&&(DW < 4 - DW_r))
            System.out.println("Наблюдаемое значение статистики Дарбина-Уотсона больше DW_u, но меньше 4-DW_u. Автокорреляции ошибок нет");
        else if ((4 - DW_r < DW)&&(DW < 4 - DW_l))
            System.out.println("Наблюдаемое значение статистики Дарбина-Уотсона больше 4-DW_u, но меньше 4-DW_l. Наличие или отсуствие автокорреляции ошибок не определено.");
        else {
            System.out.println("Наблюдаемое значение статистики Дарбина-Уотсона больше 4-DW_l. Автокорреляция ошибок есть.");
            return true;
        }
        return false;
    }

    private static double TrendError(double[] errors) throws Exception {
        System.out.println("Выполняем оценку остатков");
        double[] past_errors = Arrays.copyOfRange(errors, 0, errors.length-1);
        double[] present_errors = Arrays.copyOfRange(errors, 1, errors.length);
        System.out.print("\n");
        double[] errors_regression = Statistics.LinearalRegression(past_errors, present_errors);
        System.out.print("\n");
        return Statistics.checkPolynomialRegrssionSignificance(past_errors, present_errors, errors_regression);
    }

    private static double TrendModel(double[] sample, double[] polynomial_regression_coefficients) throws Exception {
        System.out.println("Строим трендовую модель\n");
        double[] tacts = new double[sample.length];
        for (int i = 0; i < tacts.length; i++)
            tacts[i] = (double)(i+1);
        double[] errors = Statistics.countPolynomialRegressionErrors(tacts, sample, polynomial_regression_coefficients);
        System.out.print("\n");
        checkErrorsWithDurbinWatson(errors, polynomial_regression_coefficients.length - 1);
        System.out.print("\n");
        return TrendError(errors);
    }

    private static double AutoregressionModel(double[] sample, int autoregression_model_order) throws Exception {
        System.out.println("Строим авторегрессинную модель\n");
        double[][] matrix_of_past = new double[sample.length - autoregression_model_order][autoregression_model_order + 1];
        for (int i = 0; i < matrix_of_past.length; i++) {
            for (int j = 0; j < autoregression_model_order; j++)
                matrix_of_past[i][j] = sample[autoregression_model_order - 1 + i - j];
            matrix_of_past[i][autoregression_model_order] = 1;
        }
        double[] vector_of_future = new double[sample.length - autoregression_model_order];
        for (int i = 0; i < vector_of_future.length; i++)
            vector_of_future[i] = sample[i + autoregression_model_order];
        double[] autoregression_coefficients = Statistics.MultidimentionalRegression(matrix_of_past, vector_of_future);
        return Statistics.MultidimentionalRegressionError(matrix_of_past, vector_of_future, autoregression_coefficients);
    }

    public sr2(double[] sample, double[] polynomial_regression_coefficients, int autoregression_model_order) throws Exception {
        System.out.println("\n\n\nСамостоятельная 2:");
        if (autoregression_model_order > sample.length)
            throw new Exception("Невозможно построить авторегрессионную модель степени больше размера выборки");
        System.out.printf("\n");
        double trend_error = TrendModel(sample, polynomial_regression_coefficients);
        System.out.printf("\n");
        double autoregression_model_error = AutoregressionModel(sample, autoregression_model_order);
        System.out.printf("\n");
        if (trend_error < autoregression_model_error)
            System.out.printf("Так как %f<%f, то тренд лучше авторегрессионной модели\n", trend_error, autoregression_model_error);
        else
            System.out.printf("Так как %f<%f, то авторегрессионная модель лучше тренда\n", autoregression_model_error, trend_error);
    }
}