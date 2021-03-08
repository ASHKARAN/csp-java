package Csp;

import Csp.Model.CspModel;
import Csp.algorithms.BackTracking;
import Csp.algorithms.ForwardChecking;
import Csp.app.Constants;

public class Csp {

    private int n;
    private double a,r,p;
    private Boolean arcConsistency = false;
    private short algorithm;


    private int randomConstraints;
    private int domainSize = 0;
    private int phaseTransition = 0;
    private int pair = 0;//pd2;


    private int[] variables ;
    private int[] domain ;
    private int[][] constraints ;

    public Csp(int n, double p, double a, double r, short algorithm, Boolean arcConsistency) {

       CspModel cspModel =  new CspModel( n,  p,  a,  r,  algorithm,  arcConsistency);
       /* System.out.println("----------- START OF LOGS -----------");
        log(cspModel);
        System.out.println("----------- END OF LOGS -----------");*/
       if(algorithm == Constants.BT) {
             new BackTracking(cspModel);
       }
       else if(algorithm == Constants.FC) {
             new ForwardChecking(cspModel);
       }


    }


    private void log(CspModel cspModel) {

        System.out.println("n: " + cspModel.getN());
        System.out.println("p: " + cspModel.getP());
        System.out.println("a: " + cspModel.getA());
        System.out.println("r: " + cspModel.getR());
        System.out.println("d: " + cspModel.getDomainSize());
        System.out.println("rn ln n: " + cspModel.getRandomConstraints());
        System.out.println("pt: " + cspModel.getPhaseTransition());
        if(cspModel.getPhaseTransition() <= cspModel.getP()) {
            System.out.println("pt <= p unacceptable ");
            Utils.exit("pt <= p unacceptable ");
        }
        System.out.println("pair: " + cspModel.getPair());






    }


}
