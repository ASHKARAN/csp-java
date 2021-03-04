public class Csp {

    private int n;
    private double a,r,p;
    private Boolean arcConsistency = false;
    private short algorithm;


    private int randomConstraints;
    private int domainSize = 0;
    private int phaseTransition = 0;
    private double pair = 0;//pd2;

    public static final int BT = 1;
    public static final int FC = 2;
    public static final int FLA = 3;

    private int[] variables ;

    public Csp(int n, double p, double a, double r, short algorithm, Boolean arcConsistency) {
        this.n = n;
        this.a = a;
        this.r = r;
        this.p = p;
        this.algorithm = algorithm;
        this.arcConsistency = arcConsistency;
        this.domainSize =  (int)Math.round(Math.pow(n, a));
        this.randomConstraints = (int)Math.round(r * n * Math.log(n));
        this.phaseTransition =  1 - (int)Math.round(Math.pow(Math.E, -1 * a/r));

        this.variables = new int[n];
        calculate();
    }


    private void calculate() {

        System.out.println("n: " + this.n);
        System.out.println("p: " + this.p);
        System.out.println("a: " + this.a);
        System.out.println("r: " + this.r);
        System.out.println("d: " + this.domainSize);
        System.out.println("rn ln n: " + this.randomConstraints);
        System.out.println("pt: " + this.phaseTransition);
        if(this.phaseTransition <= this.p) {
            System.out.println("pt <= p unacceptable ");
            utils.exit("pt <= p unacceptable ");
        }
    }


}
