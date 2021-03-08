package Csp.Model;

import Csp.Utils;

import java.util.ArrayList;
import java.util.List;

public class PathModel {

    private int from;
    private int to;
    private int domainSize;
    private int pair;
    private List<int[]> path ;
    public PathModel  (int from, int to, int domainSize, int pair) {

        this.from = from;
        this.to = to;
        this.domainSize = domainSize;
        this.pair = pair;
        path = new ArrayList<>();

        for(int i = 0 ; i < pair ; i++) {
            int [] data = new int[2];
            int x =  Utils.random(0, this.domainSize-1);
            int y = Utils.random(0, this.domainSize-1);

            while(pointsAlreadyExistsInArray(path, x , y)) {
                x =  Utils.random(0, this.domainSize-1);
                y =  Utils.random(0, this.domainSize-1);
            }
            data[0] = x;
            data[1] = y;
            path.add(data);
        }
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public List<int[]> getPath() {
        return path;
    }

    private Boolean pointsAlreadyExistsInArray(List<int[]> path , int x, int y) {

        int length = path.size();
        for(int i = 0 ; i < length; i++) {
            if (
                     path.get(i)[0] == x &&
                     path.get(i)[1] == y

            ) {

                return true;
            }
        }
        return false;
    }
}
