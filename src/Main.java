import java.util.Scanner;

public class Main {
    private int n   ;
    private double a, r, p;




    public static void main(String[] args) {

       new Main();

    }

    public Main() {

        System.out.println("Welcome to Csp");


        int example = 1;
        switch (example) {
            case 1: {
                new Csp(4, 0.33, 0.8, 0.7, (short) 1, false);
                break;
            }
            case 2: {
                new Csp(6, 0.33, 0.8, 0.7, (short) 1, false);
                break;
            }
            case 3: {
                new Csp(8, 0.33, 0.8, 0.7, (short) 1, false);
                break;
            }
        }

       if(true)  return;







        System.out.println("Please enter n");


        Scanner scanner = new Scanner(System.in);
        try {
            n = Integer.parseInt(scanner.nextLine());
        }
        catch (NumberFormatException e) {
            utils.exit(e.toString());
        }

        if(n < 2) {
            utils.exit("n must be greater than 2");
        }
        System.out.println("n=" + n);



        System.out.println("Please enter a");
        try {
            a = Double.parseDouble(scanner.nextLine());
        }
        catch (NumberFormatException e) {
            utils.exit(e.toString());
        }

        if(a <= 0 || a >= 1) {
            utils.exit("a must be 0 < a < 1 ");
        }
        System.out.println("a=" + a);




        System.out.println("Please enter r");
        try {
            r = Double.parseDouble(scanner.nextLine());
        }
        catch (NumberFormatException e) {
            utils.exit(e.toString());
        }

        if(r <= 0 || r >= 1) {
            utils.exit("r must be 0 < r < 1 ");
        }
        System.out.println("r=" + r);




        System.out.println("Please enter p");
        try {
            p = Double.parseDouble(scanner.nextLine());
        }
        catch (NumberFormatException e) {
            utils.exit(e.toString());
        }

        if(p <= 0 || p >= 1) {
            utils.exit("p must be 0 < p < 1 ");
        }
        System.out.println("p=" + p);


        System.out.println("Please select Algorithm number");
        System.out.println("1 BT Standard Backtracking");
        System.out.println("2 FC Forward Checking");
        System.out.println("3 FLA Full Look Ahead (also called MAC).");

        short algorithm = 0 ;
        try {
            algorithm = Short.parseShort(scanner.nextLine());
        }
        catch (NumberFormatException e) {
            utils.exit(e.toString());
        }

        if(algorithm < 1 || algorithm >3) {
            utils.exit("algorithm must be 0 < algorithm < 4 ");
        }
        System.out.println("algorithm =" + algorithm);

        if(algorithm == Csp.BT) {

            System.out.println("Do you want's to use Arc Consistency? y/n");

            String tmp = scanner.nextLine();
            Boolean arcConsistency = tmp.toLowerCase().equals("y");
            System.out.println("Arc Consistency will be applied");
            System.out.println("Let's run the calculation");
            new Csp(n, a, r, p , algorithm, arcConsistency);
        }



    }
     
}
