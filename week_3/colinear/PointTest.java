import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

import java.util.Arrays;

/* *****************************************************************************
 *  Name: Xinlin Song
 *  Date: 12/31/2018
 *  Description: Test module for Point class
 **************************************************************************** */
public class PointTest {
    private Point pointA = new Point(0, 0);
    private Point pointB = new Point(1, 1);
    private Point pointC = new Point(1, 2);
    private Point pointD = new Point(0, 0);
    private Point pointE = new Point(2, 1);
    private Point pointF = new Point(1, 3);


    @Test
    public void slopeTo() {
        assert (1.0 == pointA.slopeTo(pointB));
        assert (2.0 == pointA.slopeTo(pointC));
        assert (pointB.slopeTo(pointC) == pointB.slopeTo(pointF));
    }

    @Test
    public void compareTo() {
        assert (pointA.compareTo(pointB) < 0);
        assert (pointB.compareTo(pointC) < 0);
        assert (pointA.compareTo(pointD) == 0);
        assert (pointB.compareTo(pointA) > 0);
        assert (pointB.compareTo(pointE) < 0);
        assert (pointE.compareTo(pointB) > 0);
    }

    @Test
    public void slopeOrder() {
        Point origin = new Point(0, 0);

        Point[] arrayPoint = new Point[5];
        arrayPoint[0] = new Point(1, 1);
        arrayPoint[1] = new Point(1, 3);
        arrayPoint[2] = new Point(1, 2);
        arrayPoint[3] = new Point(2, 2);
        arrayPoint[4] = new Point(2, 3);

        Arrays.sort(arrayPoint, origin.slopeOrder());

        for (Point point : arrayPoint) {
            StdOut.println(point);
        }
    }

    @Test
    public void testSortPoints() {
        Point[] points = new Point[4];
        points[0] = new Point(0, 0);
        points[1] = new Point(2, 1);
        points[2] = new Point(1, 2);
        points[3] = new Point(1, 1);

        Arrays.sort(points);

        for (Point point : points) {
            StdOut.println(point);
        }

    }

}
