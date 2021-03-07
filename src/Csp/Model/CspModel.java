package Csp.Model;


import Csp.Utils;

import java.util.ArrayList;
import java.util.List;

public class CspModel {

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
    private  List<PathModel> pathModelList;

    public CspModel(int n, double p, double a, double r, short algorithm, Boolean arcConsistency) {
        this.n = n;
        this.a = a;
        this.r = r;
        this.p = p;
        this.algorithm = algorithm;
        this.arcConsistency = arcConsistency;
        this.domainSize =  (int)Math.round(Math.pow(n, a));

        this.randomConstraints = (int)Math.round(r * n * Math.log(n));
        this.phaseTransition =  1 - (int)Math.round(Math.pow(Math.E, -1 * a/r));

        this.pair =  (int)Math.round(this.p * Math.pow(this.domainSize, 2));

        this.variables = new int[this.randomConstraints];
        this.domain = new int[this.domainSize];
        for(int i = 0 ; i < this.domainSize; i++) {
            this.domain[i] = i;
        }
        this.constraints = generateIncompatibleTuples(this.randomConstraints, this.pair );

       this.pathModelList = new ArrayList<>();

        for(int i = 0 ; i < getRandomConstraints() ; i++) {
            int from =  Utils.random(0,  getDomainSize());
            int to   =  Utils.random(0,  getDomainSize());

            while(Utils.pointsAlreadyExistsInPath(this.pathModelList , from, to)) {
                from =  Utils.random(0,  getDomainSize());
                to   =  Utils.random(0,  getDomainSize());
            }
            PathModel pathModel =  new PathModel( from, to,   getDomainSize(), getPair());
            this.pathModelList.add( pathModel);
        }




    }

    private int[][]  generateIncompatibleTuples(int x, int y) {


        int [][] constraints = new int[x][y];


        for(int i = 0 ; i < x ; i++) {

            for(int j = 0 ; j < y ; j++) {
                constraints[i][j] = Utils.random(0, this.domainSize-1);
            }

        }


        return constraints;
    }


    public int getN() {
        return n;
    }

    public double getA() {
        return a;
    }

    public double getR() {
        return r;
    }

    public double getP() {
        return p;
    }

    public Boolean getArcConsistency() {
        return arcConsistency;
    }

    public short getAlgorithm() {
        return algorithm;
    }

    public int getRandomConstraints() {
        return randomConstraints;
    }

    public int getDomainSize() {
        return domainSize;
    }

    public int getPhaseTransition() {
        return phaseTransition;
    }

    public int getPair() {
        return pair;
    }

    public int[] getVariables() {
        return variables;
    }

    public int[] getDomain() {
        return domain;
    }

    public int[][] getConstraints() {
        return constraints;
    }

    public List<PathModel> getPathModelList() {
        return pathModelList;
    }

    public   List<PathModel>   getPathModel(int from) {
         int length = pathModelList.size();

        List<PathModel> result = new ArrayList<>();

         for(int i = 0 ; i<length; i++) {
             PathModel pathModel = pathModelList.get(i);
             if(pathModel.getFrom() == from || pathModel.getTo() == from) {
                 result.add(pathModel);
             }
         }
         return result;
    }


}
