package Csp.algorithms;

import Csp.Model.CspModel;
import Csp.Model.PathModel;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.logging.Level;

public class BackTracking {

    private CspModel cspModel ;
    int[] result ;
    public BackTracking(CspModel cspModel) {
        this.cspModel = cspModel;
        this.result = new int[this.cspModel.getN()];
        for(int i = 0 ; i < result.length ; i++) {
            this.result[i] = 0 ;
        }





        Instant start = Instant.now();
        calculate(0 );
        Instant end = Instant.now();
        System.out.println("execution time: " + Duration.between(start, end));




        System.out.println( "Result ");
        for(int i = 0 ; i < result.length ; i++) {
            System.out.print("X" + i + ": " + result[i] + ", ");
        }
        System.out.println( " ");

    }

    public void calculate(int level ) {


        System.out.println(" \n\n **** Level: " + level + " ***** \n\n");
        printResult();
        //for az i ta 4 maghadiri ke x mitone begire
        //level = 0
        // hame line haii ke ba x dar ertebatan mikhunam
        //sharayet ro barresi mikonam
        //age ok bod level++ backtrack
        //age moshkel dasht
        //mizarim loop tamom she
        //ya residim tahesh
        //migim javab nadarim
        //ya migim backtrack kon level--


        if(level >= result.length || level < 0) {
            System.out.println("level "+ level +" out of range");
            return;
        }

        if(result[level] >= cspModel.getDomainSize()) {
            System.out.println("result[level] >= cspModel.getDomainSize()");
            result[level] = 0 ;
            return;
        }

            List<PathModel> pathModelList = cspModel.getPathModel(level);
            if(checkIncompatibleTuples(level, pathModelList)) {
                System.out.println("we are ok here X" + level + " : " + result[level]  );
                if(level==cspModel.getN()-1) {
                    finalAnswer(level);
                    return;
                }
                calculate(++level);
            }
            else {
                System.out.println("i found a problem X" + level + " : " + result[level]  );

                if(level == 0 && result[level] == this.cspModel.getDomainSize()-1) {
                    System.out.println(" cannot solve this problem");
                    return;
                }
                if(result[level] < this.cspModel.getDomainSize()-1)
                    result[level]++;
                else {
                    result[level-1]++ ;
                    level--;
                }
                calculate(level);
            }
        }

        /*
         Backtrack(x)
        if x is not a solution
        return false
        if x is a new solution
        add to list of solutions
        backtrack(expand x)
         */

/*
        level = forward?level++:level;


        if(level >= this.result.length) {
            System.out.println("there is not result for level: " + level);
            return;
        }

        System.out.println( "");
        for(int i = 0 ; i < result.length ; i++) {
            System.out.print("X" + i + ": " + result[i] + ", ");
        }
        System.out.println( " ");
        System.out.println("calculating for row: " + level);



        System.out.println("Current Value: x"+level + ", X" + level +" ");


        for(int i = 0 ; i < result.length; i++) {
            result[level] = i;
            List<PathModel> pathModel = cspModel.getPathModel(i);
            if(checkIncompatibleTuples(level, pathModel)) {
                calculate(level, forward);
            }
            else {
                System.out.println("we have problem here level = " + level);
            }
            for(int j=0 ; j < cspModel.getDomainSize(); j++) {
                result[j] = j;

            }

        }
        for(int i = from ; i < this.result.length ; i++) {

            for(int j = to ;  j < this.result.length ; j++) {

                System.out.println("processing  X"+i + " to X"+j );
                if(i == j ) {
                    System.out.println("continue 1" );
                    continue;
                }
                PathModel pathModel = cspModel.getPathModel(i , j);
                if(pathModel == null) {
                    System.out.println("path not found from X"+i + " to X"+j);
                    calculate(from , to , false);
                    return;

                }
                else {

                    int length = pathModel.getPath().size();
                    for(int k = 0 ; k < length ; k++) {
                        if(
                                result[i] == pathModel.getPath().get(k)[0] &&
                                result[j] == pathModel.getPath().get(k)[1]) {
                            System.out.println("X"+from+ " = X"+to  + "  is not acceptable");
                            calculate(from , to , false);
                            //calculate(level, false);
                            return;
                        }
                    }
                    this.result[i] = i;
                    this.result[j] = j;
                    calculate(from , to , true);
                }
            }
        }



        int from =  this.result[level];
        int to = 0 ;
        if(!forward && level == 0) {
            System.out.println("we have no solution");
        }
        else {
           to  =  forward?level+1:level-1 ;
        }


        PathModel pathModel = cspModel.getPathModel(from , to);
        if(pathModel == null) {
            System.out.println("path not found from X"+from + " to X"+to);
            this.result[level]++;
            calculate(level, true);
            return;
        }

        int length = pathModel.getPath().size();
        for(int i = 0 ; i < length ; i++) {
            if( from == pathModel.getPath().get(i)[0] &&  to == pathModel.getPath().get(i)[1]) {
                System.out.println("X"+from+ " = X"+to);
                this.result[level]--;
                calculate(level, false);
                return;
            }
        }
        if(this.result[level] > this.result.length) {
            System.out.println("level: " + level);
            return;
        }
        this.result[level]++;
        calculate(level, true);


        for(int i = 0 ; i < cspModel.getRandomConstraints() ; i++) {
            PathModel pathModel1 = cspModel.getPathModelList().get(i);

            System.out.print("X" + pathModel1.getFrom() + " X" + pathModel1.getTo() + " : " );

            for(int j=0; j < pathModel1.getPath().size() ; j++) {
                 int[]  xx = pathModel1.getPath().get(j);
                System.out.print ( "("+xx[0]+","+xx[1]+")" );
            }
            System.out.println( " " );
        }

         */




    private Boolean checkIncompatibleTuples(int from,  List<PathModel> pathModelList) {

        System.out.println("checkIncompatibleTuples from: " + from);

        for(PathModel pathModel: pathModelList) {
            printPathModel(pathModel);
        }


        int length = pathModelList.size();

        for(int i = 0 ; i < length ; i++) {
            PathModel pathModel = pathModelList.get(i);
            int pathLength = pathModel.getPath().size();

            int x = 0 ;
            int y = 0 ;
            if (pathModel.getFrom() == from) {
                x = this.result[from];
                y = this.result[pathModel.getTo()];
            } else if (pathModel.getTo() == from) {
                x =  this.result[pathModel.getFrom()];
                y =  this.result[pathModel.getTo()];
            }

            System.out.println("From: " + from + " - X: " + x + " - Y: " + y);

            int[] path = pathModel.getPath().get(i);

            for (int j = 0; j < pathLength; j++) {
                int [] tuple = pathModel.getPath().get(j);
                if (x == tuple[0] && y == tuple[1]) {

                    return false;
                }
            }
        }

        return true;
    }
    public void calculateWithArcConsistency() {

    }

    public void printPathModel(PathModel pathModel1) {

        System.out.print("X" + pathModel1.getFrom() + "("+result[pathModel1.getFrom()]
                +") X" + pathModel1.getTo() + "("+ result[pathModel1.getTo()]  + ") ->  ");

        for(int j=0; j < pathModel1.getPath().size() ; j++) {
            int[]  xx = pathModel1.getPath().get(j);
            System.out.print ( "("+xx[0]+","+xx[1]+")" );
        }
        System.out.println( " " );

    }

    private void printIncompatibleTuples() {
        for(int i = 0 ; i < cspModel.getRandomConstraints() ; i++) {
            PathModel pathModel1 = cspModel.getPathModelList().get(i);

            System.out.print("X" + pathModel1.getFrom() + " X" + pathModel1.getTo() + " : " );

            for(int j=0; j < pathModel1.getPath().size() ; j++) {
                int[]  xx = pathModel1.getPath().get(j);
                System.out.print ( "("+xx[0]+","+xx[1]+")" );
            }
            System.out.println( " " );
        }
    }
    private void printResult() {

        System.out.println("\n\n\nConstraints (incompatible tuples):"  );
        printIncompatibleTuples();
        System.out.println("\nPossible solution:");
        for(int i = 0 ; i < result.length ; i++) {
            System.out.print("X" + i + ": " + result[i] + ", ");
        }
        System.out.println("\n");


    }
    private void finalAnswer(int level) {
        System.out.println("\n\n\n final Answer - Level: " + level);


        printResult();
    }
}
