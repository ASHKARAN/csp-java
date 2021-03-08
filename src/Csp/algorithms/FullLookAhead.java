package Csp.algorithms;

import Csp.Model.CspModel;
import Csp.Model.PathModel;
import javafx.geometry.Pos;
import javafx.scene.shape.PathElement;

import java.util.ArrayList;
import java.util.List;

public class FullLookAhead {

    private CspModel cspModel ;
    int[] result ;
    private int lastRollback = -1;
    private String highLevelError = "";
    List<int []> allPossibleSolutions  = new ArrayList<>();
    private final static int REMOVED = -1;
    public FullLookAhead(CspModel cspModel) {
        this.cspModel = cspModel;
        this.result = new int[this.cspModel.getN()];
        for(int i = 0 ; i < result.length ; i++) {
            this.result[i] = 0 ;
        }


        long startTime = System.nanoTime();
        generatePossibleSolutions(0);
        calculate();
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;

        System.out.println("Execution time: " + timeElapsed + " nano seconds");
        printResult();

    }

    public void calculate() {
     //   printAllPossibleSolutions();



        List<PathModel> pathModelList = cspModel.getPathModelList();
        while(!checkIncompatibleTuples(pathModelList)) {
           // System.out.println("we need to check more");
        }

    }

    private Boolean checkIncompatibleTuples(List<PathModel> pathModelList) {

       // System.out.println("checkIncompatibleTuples  " );


      //  printResult();
        int length = pathModelList.size();
        for(int i = 0 ; i < length ; i++) {
            List<PathModel> pathModels = this.cspModel.getPathModel(i);


            int innerLength =  pathModels.size();
            for(int j = 0 ; j < innerLength; j++) {
                PathModel pathModel = pathModels.get(j);
                int x = 0 ;
                int y = 0 ;
                int targetLevel = -1 ;
                int removalPossibleSolution ;
                if (pathModel.getFrom() == i) {
                    x = this.result[i];
                    y = this.result[pathModel.getTo()];
                    targetLevel = pathModel.getTo();
                    removalPossibleSolution = y ;
                    if( pathModel.getFrom() >  pathModel.getTo())   continue;

                } else if (pathModel.getTo() == i) {
                    x =  this.result[pathModel.getFrom()];
                    y =  this.result[pathModel.getTo()];
                    targetLevel = pathModel.getFrom();
                    removalPossibleSolution = x ;
                    if( pathModel.getTo() >  pathModel.getFrom())   continue;
                }
                else {
                    continue;
                }


                int pathLength = pathModel.getPath().size();

                for (int k = 0; k < pathLength; k++) {
                    int [] tuple = pathModel.getPath().get(k);
                 //   printAllPossibleSolutions();
                   // System.out.println( "X"+pathModel.getFrom()+",X"+pathModel.getTo() +   "  - level: "+i+" checking "+ x + ","+y+"  with  " + tuple[0] + ", " + tuple[1]  + " total len : " + pathLength + " k: " + k);
                    if (x == tuple[0] && y == tuple[1]) {
                      //  System.out.println("incompatible with X"+targetLevel + " level: " + i +"  -  x: " + x + " - y: " + y);
                        if(!removePossibleSolution(targetLevel, removalPossibleSolution)) {

                           // System.out.println("we have problem here");
                         //   printAllPossibleSolutions();

                            removePossibleSolution(targetLevel-1, removalPossibleSolution);

                            resetPossibleSolutions(targetLevel);

                         //   printAllPossibleSolutions();

                        }
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
        Boolean unacceptable = false;
        if(highLevelError.equals("")) {
            System.out.println("\nPossible solution:");
            for(int i = 0 ; i < result.length ; i++) {
                System.out.print("X" + i + ": " + result[i] +
                        (i < result.length-1 ? ", ": ""));
                if(result[i] < 0 ) unacceptable = true;
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
        if(unacceptable) {
            System.out.println("Result is unacceptable");
        }
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
       // System.out.println("resetPossibleSolutions from: " + from);
        List<PathModel> pathModelList = cspModel.getPathModel(from);
       /* for(PathModel pathModel: pathModelList) {
            printPathModel(pathModel);
        }*/


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

               // System.out.println("From: " + from + " - X: " + x + " - Y: " + y);

                int[] path = pathModel.getPath().get(i);

                for (int j = 0; j < pathLength; j++) {
                    int [] tuple = pathModel.getPath().get(j);
                    if (x == tuple[0] && y == tuple[1]) {

                       // System.out.println("incompatible with X"+targetLevel + " level: " + from+"  -  x: " + x + " - y: " + y);
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
        if(value == -1)  {
            resetPossibleSolutions(level);
            if(level>0)
            removePossibleSolution(level-1 , result[level-1]);
            return false;
        }
        for(int i = 0 ; i< possible.length ; i++) {
           // System.out.println("xxxx removing lvl: " + level + " - val: " + value);
            if(possible[i] == value) {
                possible[i] = REMOVED;
                allPossibleSolutions.set(level, possible);
                break;
            }
        }
        if(result[level] == value)
            result[level] = nextPossibleSolution(level);


        for(int i = allPossibleSolutions.size()-1;  i >=0 ; i--) {
            if(nextPossibleSolution(i) == -1 && i == 0) {
               System.out.println("we have no answer for this problem");
                System.exit(0);
            }
            else if(nextPossibleSolution(i) == -1 ){
            //    System.out.println("xxxxxxx ");
                removePossibleSolution(i-1, result[i-1]);
                resetPossibleSolutions(i);
               break;
            }
        }


       // System.out.println("changing level : " + level + " value: " + value + " new val: " + result[level] + " - " + (result[level] > -1));
     //   printAllPossibleSolutions();
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

    }
}
