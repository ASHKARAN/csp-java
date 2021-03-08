package Csp;

import Csp.Model.CspModel;
import Csp.algorithms.BackTracking;
import Csp.algorithms.ForwardChecking;
import Csp.algorithms.FullLookAhead;
import Csp.app.Constants;

public class Csp {
    public Csp(int n, double p, double a, double r, short algorithm, Boolean arcConsistency) {

       CspModel cspModel =  new CspModel( n,  p,  a,  r,  algorithm,  arcConsistency);
       String algorithmString = "";
       switch (algorithm) {
           case Constants.BT:
               algorithmString = "BackTracking";
               if(cspModel.getArcConsistency())
                   algorithmString += " with ArcConsistency";
               break;
           case Constants.FC: {
               algorithmString = "ForwardChecking";
               break;
           }
           case Constants.FLA: {
               algorithmString = "FullLookAhead";
               break;
           }
       }
       System.out.println("Algorithm: "  + algorithmString);
       if(algorithm == Constants.BT) {
             new BackTracking(cspModel);
       }
       else if(algorithm == Constants.FC) {
             new ForwardChecking(cspModel);
       }
       else if(algorithm == Constants.FLA) {
             new FullLookAhead(cspModel);
       }
    }

}
