package Csp;

import Csp.Model.PathModel;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {


    public static void exit(String message) {
        System.out.println(message);
        System.exit(0);
    }

    public static int random(int min, int max) {
        return  ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static Boolean pointsAlreadyExistsInPath(List<PathModel> pathModelList, int from , int to) {

        int length = pathModelList.size();
        if(from == to ) return true;
        for(int i = 0 ; i < length; i++) {
            if (
                    (pathModelList.get(i).getFrom() == from &&
                    pathModelList.get(i).getTo() == to)
                    ||
                    (pathModelList.get(i).getFrom() == to &&
                            pathModelList.get(i).getTo() == from)
            ) {

                return true;
            }
        }
        return false;
    }

}
