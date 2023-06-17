import java.util.Scanner;

public class Main {
    public static void main(String[] terminal_argumnets) throws Exception {
        if (terminal_argumnets.length == 0) {
            System.out.print("Введите числа из выборки в качестве аргументов коммандной строки");
            return;
        }
        Scanner terminal_input = new Scanner(System.in);
        double[] sample = new double[terminal_argumnets.length];
        for (int i = 0; i < terminal_argumnets.length; i++)
            sample[i] = Double.parseDouble(terminal_argumnets[i]);
        System.out.println("До какого лага считать? Введите натуральное число, меньшее размера выборки.");
        sr1 sr1_solution = new sr1(sample, terminal_input.nextInt());
        System.out.println("Введите порядок автокорреляционной модели");
        int autoregression_model_order = terminal_input.nextInt();
        System.out.println("Считаем линейный или квадратичный тренд? Введите 1 или 2");
        switch (terminal_input.nextInt()) {
            case 1:
                new sr2(sample, sr1_solution.linear_regrssion_coefficients, autoregression_model_order);
                break;
            case 2:
                new sr2(sample, sr1_solution.second_degree_polynome_regrssion_coefficients, autoregression_model_order);
                break;
                default:
                System.out.println("Нужно было ввести 1 или 2");
                break;
        }
        terminal_input.close();
    }
}
