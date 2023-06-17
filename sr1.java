import java.util.Arrays;

public class sr1 {
    public double[] ACF;
    public double[] linear_regrssion_coefficients;
    public double[] second_degree_polynome_regrssion_coefficients;
    public double[] third_degree_polynome_regrssion_coefficients;

    private void countAutocorrelationFunction(double[] sample, int ACF_last_lag) throws Exception {
        ACF = new double[ACF_last_lag];        
        System.out.println("\n\nАвтрокорреляционная функция: ");
        for (int k = 1; k <= ACF_last_lag; k++) {
            double[] sample1 = Arrays.copyOfRange(sample, 0, sample.length - k);
            double[] sample2 = Arrays.copyOfRange(sample, k, sample.length);
            System.out.printf("Среднее от x_1 до x_%d: (%f+...+(%f))/%d=%f\n", sample.length - k, sample1[0], sample1[sample1.length-1], sample1.length, Statistics.Average(sample1));
            System.out.printf("Среднее от x_%d до x_%d: (%f+...+(%f))/%d=%f\n", k+1, sample.length, sample2[0], sample2[sample2.length - 1], sample2.length, Statistics.Average(sample2));
            System.out.printf("Среднее выборочное от x_1 до x_%d: sqrt(1/%d*((%f)^2+...+(%f)^2)-(%f)^2)=%f\n", sample.length - k, sample1.length, sample1[0], sample1[sample1.length - 1], Statistics.Average(sample1), Statistics.StandardDeviation(sample1));
            System.out.printf("Среднее выборочное от x_%d до x_%d: sqrt(1/%d*((%f)^2+...+(%f)^2)-(%f)^2)=%f\n", k+1, sample.length, sample2.length, sample2[0], sample2[sample1.length - 1], Statistics.Average(sample2), Statistics.StandardDeviation(sample2));
            System.out.printf("Автокоррляционная функция при лаге k=%d равна (1/%d*((%f)*(%f)+...+(%f)*(%f))-(%f)*(%f))/((%f)*(%f))=%f\n", k, sample1.length, sample1[0], sample2[0], sample1[sample1.length - 1], sample2[sample2.length - 1], Statistics.Average(sample1), Statistics.Average(sample2), Statistics.StandardDeviation(sample1), Statistics.StandardDeviation(sample2), Statistics.Correlation(sample1, sample2));
            ACF[k-1] = Statistics.Correlation(sample1, sample2);
        }
        System.out.println("\nАвтокорреляционная функция: " + Arrays.toString(ACF) + "\n");
    }

    public sr1(double[] sample, int ACF_last_lag) throws Exception {
        if ((ACF_last_lag >= sample.length)||(ACF_last_lag < 1))
            throw new Exception("Лаг не может быть больше или равен размеру выборки и меньше 1");
        System.out.println("\n\n\nСамостоятельная 1:");
        countAutocorrelationFunction(sample, ACF_last_lag);
        double[] tacts = new double[sample.length];
        for (int i = 1; i <= sample.length; i++)
            tacts[i-1] = (double)i;
        linear_regrssion_coefficients = Statistics.LinearalRegression(tacts, sample);
        Statistics.checkPolynomialRegrssionSignificance(tacts, sample, linear_regrssion_coefficients);
        System.out.println("\n");
        second_degree_polynome_regrssion_coefficients = Statistics.PolynomialRegression(tacts, sample, 2);
        Statistics.checkPolynomialRegrssionSignificance(tacts, sample, second_degree_polynome_regrssion_coefficients);
        System.out.println("\n");
    }
}
