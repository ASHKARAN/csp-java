package Csp.algorithms;

import Csp.Model.CspModel;
import Csp.Model.PathModel;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

public class ForwardChecking {

    private CspModel cspModel ;
    int[] result ;
    private int lastRollback = -1;
    private String highLevelError = "";
    List<int []> allPossibleSolutions  = new ArrayList<>();
    private final static int REMOVED = -1;
    public ForwardChecking(CspModel cspModel) {
        this.cspModel = cspModel;
        this.result = new int[this.cspModel.getN()];
        for(int i = 0 ; i < result.length ; i++) {
            this.result[i] = 0 ;
        }


        long startTime = System.nanoTime();
        generatePossibleSolutions(0);
        calculate(0 , 0 );
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;

        System.out.println("Execution time: " + timeElapsed + " nano seconds");
        printResult();

    }

    public void calculate(int level, int increment ) {




        //printResult();
        if(increment == 1 ) {
            level++;
            if(level >= this.cspModel.getN()){
                return;
            }

            result[level]  = nextPossibleSolution(level);
            if(result[level] == -1) {
                System.out.println("we cannot continue 1");
                System.out.println("mitonim rollback konim" );
                return;
            }

        }
        else if(increment == -1) {
            level--;
            if(level < 0 ){
                return;
            }
            result[level] = nextPossibleSolution(level);

            if(result[level] == -1) {
                System.out.println("we cannot continue 2");
                System.out.println("mitonim rollback konim");
                return;
            }
        }


        printAllPossibleSolutions();


        System.out.println(" \n\n **** Level: " + level + " ***** \n\n");

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
            if(!checkIncompatibleTuples(level, pathModelList)) {
                System.out.println("we cannot continue 3 ");
                System.out.println("mitonim rollback konim ");
                resetPossibleSolutions(level);
                calculate(level , -1 );
                return;
            }

            int nextPossibleSolution = nextPossibleSolution(level);
            if(nextPossibleSolution == -1) {
                //there is no way to continue
                //rollback
                System.out.println("we cannot continue 3 ");
                System.out.println("mitonim rollback konim ");
                resetPossibleSolutions(level);
                calculate(level , -1 );
                return;
            } else {
                result[level] = nextPossibleSolution;
                calculate( level, 1);
                return;
            }
           /* if(checkIncompatibleTuples(level, pathModelList)) {
                System.out.println("We are ok here X" + level + " : " + result[level]  );
                if(level==cspModel.getN()-1) {
                    return;
                }
                calculate( level, 1);
            }
            else {
                System.out.println("i found a problem X" + level + " : " + result[level]  );

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
                       // finalAnswer(level);
                        return;
                    }
                    lastRollback = level;
                    result[level] = 0;
                    calculate(level , -1 );
                }

            }*/
        }

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
            int targetLevel = -1 ;
            int removalPossibleSolution = -1;
            if (pathModel.getFrom() == from) {
                x = this.result[from];
                y = this.result[pathModel.getTo()];
                targetLevel = pathModel.getTo();
               removalPossibleSolution = y ;
                if( pathModel.getFrom() >  pathModel.getTo())   continue;

            } else if (pathModel.getTo() == from) {
                x =  this.result[pathModel.getFrom()];
                y =  this.result[pathModel.getTo()];
                targetLevel = pathModel.getFrom();
                removalPossibleSolution = x ;
                if( pathModel.getTo() >  pathModel.getFrom())   continue;
            }

            System.out.println("From: " + from + " - X: " + x + " - Y: " + y);

            int[] path = pathModel.getPath().get(i);

            for (int j = 0; j < pathLength; j++) {
                int [] tuple = pathModel.getPath().get(j);
                if (x == tuple[0] && y == tuple[1]) {
                    System.out.println("incompatible with X"+targetLevel + " level: " + from+"  -  x: " + x + " - y: " + y);
                     if(!removePossibleSolution(targetLevel, removalPossibleSolution)) {
                         return false;
                     }
                }
            }
        }
        return  true;
    }

    private int nextPossibleSolution(int level) {
        int [] possible = allPossibleSolutions.get(level);
        for(int i = 0 ; i < possible.length; i++) {
            if(possible[i] != -1)
                return possible[i];
        }
        return -1;

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

    private void generatePossibleSolutions(int level) {
        int length = allPossibleSolutions.size();
        for(int i=level ; i < this.cspModel.getN(); i++) {
           int [] possible = new int[this.cspModel.getDomainSize()];
            for(int j=0 ; j < this.cspModel.getDomainSize(); j++) {
                possible[j] = j;
            }
            if(length == 0 )
                allPossibleSolutions.add(possible);
            else  allPossibleSolutions.set(level, possible);
        }
    }
    private void removeItemFromPossibleSolutions(int level, int solution) {
        int [] possible = allPossibleSolutions.get(level);
        possible[solution] = REMOVED  ;
        allPossibleSolutions.set(level, possible);
    }
    private void resetPossibleSolutions(int from) {
        System.out.println("resetPossibleSolutions from: " + from);
        List<PathModel> pathModelList = cspModel.getPathModel(from);
        for(PathModel pathModel: pathModelList) {
            printPathModel(pathModel);
        }


        int length = pathModelList.size();

        for(int i = from ; i < length ; i++) {
            PathModel pathModel = pathModelList.get(i);
            int pathLength = pathModel.getPath().size();

            int x = 0 ;
            int y = 0 ;
            int targetLevel = -1 ;
            int removalPossibleSolution = -1;

            if (pathModel.getFrom() == from) {
                targetLevel = pathModel.getTo();

            } else if (pathModel.getTo() == from) {
                targetLevel = pathModel.getFrom();
            }
            for(int v = 0 ; v < this.cspModel.getDomainSize(); v++) {

                int [] possible = allPossibleSolutions.get(targetLevel);
                int value = possible[v];
                if(value == REMOVED) {
                    continue;
                }

                if (pathModel.getFrom() == from) {
                    x = this.result[from];
                    y = value;
                    targetLevel = pathModel.getTo();
                    if( pathModel.getFrom() >  pathModel.getTo())   continue;

                } else if (pathModel.getTo() == from) {
                    x =  value;
                    y =  this.result[pathModel.getTo()];
                    if(x >= y)   continue;
                    targetLevel = pathModel.getFrom();
                    if( pathModel.getTo() >  pathModel.getFrom())   continue;
                }

            System.out.println("From: " + from + " - X: " + x + " - Y: " + y);

            int[] path = pathModel.getPath().get(i);

            for (int j = 0; j < pathLength; j++) {
                int [] tuple = pathModel.getPath().get(j);
                if (x == tuple[0] && y == tuple[1]) {

                    System.out.println("incompatible with X"+targetLevel + " level: " + from+"  -  x: " + x + " - y: " + y);
                    int[] possible1 = allPossibleSolutions.get(i);
                    for(int k = 0 ; k < this.cspModel.getDomainSize(); k++) {
                        possible1[k] = k;
                    }
                    allPossibleSolutions.set(i , possible1);
                }
            }



            }



        }



    }
    private int [] getPossibleSolutions(int level) {
        int [] possible = allPossibleSolutions.get(level);
        return possible.length > 0 ? possible: null;
    }
    private int getSinglePossibleSolutions(int level) {
        int [] possible = allPossibleSolutions.get(level);
        return possible.length > 0 ? possible[0]: null;
    }

    private Boolean removePossibleSolution(int level, int value) {
        int [] possible = allPossibleSolutions.get(level);
        for(int i = 0 ; i< possible.length ; i++) {
            if(possible[i] == value) {
                possible[i] = REMOVED;
                allPossibleSolutions.set(level, possible);
                break;
            }
        }

        if(result[level] == value)
            result[level] = nextPossibleSolution(level);
        return result[level] > -1;
    }


   private void printAllPossibleSolutions() {

        System.out.println("********* printAllPossibleSolutions ************");
        for(int i = 0 ; i < allPossibleSolutions.size(); i++) {
            int[] possible = allPossibleSolutions.get(i);
            System.out.print("X"+i+": " + result[i] + " >>  ");
            for(int j = 0  ; j < possible.length; j++) {
                System.out.print(possible[j] + ", ");
            }
            System.out.println("");
        }

/*
        for(int i=0 ; i < this.cspModel.getN(); i++) {
            int [] possible = new int[this.cspModel.getDomainSize()];
            for(int j=0 ; j < this.cspModel.getDomainSize(); j++) {
                possible[j] = j;
            }
            allPossibleSolutions.set(i, possible);
        }*/
    }
}
