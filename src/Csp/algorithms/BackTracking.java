package Csp.algorithms;

import Csp.Model.CspModel;
import Csp.Model.PathModel;
import java.util.List;

public class BackTracking {

    private CspModel cspModel ;
    int[] result ;
    private int lastRollback = -1;
    private String highLevelError = "";
    public BackTracking(CspModel cspModel) {
        this.cspModel = cspModel;
        this.result = new int[this.cspModel.getN()];
        for(int i = 0 ; i < result.length ; i++) {
            this.result[i] = 0 ;
        }


        long startTime = System.nanoTime();
        calculate(0 , 0 );
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        printResult();
        System.out.println("Execution time: " + timeElapsed + " nano seconds");


    }


    public void calculate(int level, int increment ) {
        if(increment == 1 ) {
            level++;
            if(level >= this.cspModel.getN()){
                return;
            }
            result[level] = 0;
        }
        else if(increment == -1) {
            level--;
            if(level < 0 ){
                return;
            }
            result[level]++;
        }
        if(level >= result.length || level < 0) {
            return;
        }

        if(result[level] >= cspModel.getDomainSize()) {
            result[level] = 0 ;
            return;
        }

            List<PathModel> pathModelList = cspModel.getPathModel(level);
            if(checkIncompatibleTuples(level, pathModelList)) {
               if(level==cspModel.getN()-1) {
                    return;
                }
                calculate( level, 1);
            }
            else {
                int summary = 0 ;
                for(int res : result) {
                    summary+=res;
                }
                if(summary == result.length * this.cspModel.getDomainSize()-1) {
                    highLevelError = "Error: all variables checked, we have no answer, X"+ level ;
                    return;
                }

                if(result[level] < this.cspModel.getDomainSize()-1) {
                    result[level]++;
                    calculate(level, 0);
                }
                else {
                    if(lastRollback == level) {
                        highLevelError = "Error: end of available domain, we have no answer, 2 rollback on X"+level;
                        return;
                    }
                    lastRollback = level;
                    result[level] = 0;
                    calculate(level , -1 );
                }

            }
        }

    private Boolean checkIncompatibleTuples(int from,  List<PathModel> pathModelList) {
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
            for (int j = 0; j < pathLength; j++) {
                int [] tuple = pathModel.getPath().get(j);
                if (x == tuple[0] && y == tuple[1]) {

                    return false;
                }
            }
        }

        return true;
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

        System.out.println("\nConstraints (incompatible tuples):"  );
        printIncompatibleTuples();
        if(highLevelError.equals("")) {
            System.out.println("\nPossible solution:");
            for(int i = 0 ; i < result.length ; i++) {
                System.out.print("X" + i + ": " + result[i] +
                        (i < result.length-1 ? ", ": ""));
            }
        }else {
            System.out.println("\nPossible solution:");
            System.out.println(highLevelError);
            System.out.println("\nLast values:");
            for(int i = 0 ; i < result.length ; i++) {
                System.out.print("X" + i + ": " + result[i] +
                        (i < result.length-1 ? ", ": ""));
            }
        }

        System.out.println("\n");


    }

}
