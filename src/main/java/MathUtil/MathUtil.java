package MathUtil;
import java.lang.Math;
public class MathUtil {

    public static double sumPowVector(int[] vec)
    {
        double expSum = 0.0;
        for(int i = 0; i<vec.length;i++)
        {
            expSum += Math.pow(vec[i],2);
        }
        return expSum;
    }

    public static int scalarProductBetweenTwoVectors(int[] v1, int[] v2)
    {
        if(v1.length != v2.length)
            throw new ArithmeticException("Vectors sizes do not match");
        int returnedVal = 0;
        for(int i = 0; i<v1.length;i++)
        {
            returnedVal += v1[i]*v2[i];
        }
        return returnedVal;
    }
    public static int[] getVectorFromTwoPointsTwoDimensions(Position2D a, Position2D b)
    {
        return new int[]{b.x-a.x, b.y-a.y};
    }
    public static double getAngleBetweenTwoVectors(int[] u, int[]v)
    {

        double nu = Math.sqrt(sumPowVector(u));
        double nv = Math.sqrt(sumPowVector(v));
        double scalarProduct = scalarProductBetweenTwoVectors(u,v);
        double theta = Math.acos(scalarProduct/(nu*nv));
        double thetaDegree = Math.toDegrees(theta);
        return thetaDegree;
    }
}
