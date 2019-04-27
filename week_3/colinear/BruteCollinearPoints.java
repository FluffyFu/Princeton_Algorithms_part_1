/* *****************************************************************************
 *  Name: Xinlin Song
 *  Date: 12/31/2018
 *  Description: examine 4 points at a time and check weather they all lie on
 *      the same line segement. For simplicity, we assume the input does not
 *      contain 5 or more point line segments. Throw a java.lang.IllegalArgumentException
 *      if the argument to the constructor is null, if any point in the array is null,
 *      or if the argument to the constructor contains a repeated point.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private int numberOfSegments = 0; // number of 4-points colinear segments
    /*
    line segment array contains all the 4-points colinear segments.
    The array size doulbes if the current is full.
     */
    private LineSegment[] segments = new LineSegment[2];

    /*
    Find all line segments containing 4 points
     */
    public BruteCollinearPoints(Point[] points) {
        // input array cannot be null.
        if (points == null) throw new IllegalArgumentException();

        // input array should not have null element
        if (Arrays.asList(points).contains(null)) throw new IllegalArgumentException();

        // input array cannot have duplicate elements.
        Point[] copyPoints = Arrays.copyOfRange(points, 0, points.length);
        Arrays.sort(copyPoints);

        for (int i = 1; i < copyPoints.length; i++) {
            // equal is not implemented.
            if (copyPoints[i - 1].compareTo(copyPoints[i]) == 0)
                throw new IllegalArgumentException();
        }

        for (int i = 0; i < copyPoints.length - 3; i++) {
            for (int j = i + 1; j < copyPoints.length - 2; j++) {
                for (int k = j + 1; k < copyPoints.length - 1; k++) {
                    for (int l = k + 1; l < copyPoints.length; l++) {
                        // throw exception if any point is null.
                        if (copyPoints[i] == null || copyPoints[j] == null ||
                                copyPoints[k] == null || copyPoints[l] == null) {
                            throw new IllegalArgumentException();
                        }
                        // check if the four copyPoints are colinear.
                        if (copyPoints[i].slopeTo(copyPoints[j]) == copyPoints[i]
                                .slopeTo(copyPoints[k]) &&
                                copyPoints[i].slopeTo(copyPoints[j]) == copyPoints[i]
                                        .slopeTo(copyPoints[l])) {

                            // add the line segment and increament the number of segments.
                            if (numberOfSegments == segments.length)
                                resizeSegments(segments.length * 2);
                            segments[numberOfSegments++] = new LineSegment(copyPoints[i],
                                                                           copyPoints[l]);
                        }
                    }
                }
            }
        }

    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        return Arrays.copyOfRange(segments, 0, numberOfSegments);
    }


    /*
    resize segment array.
     */
    private void resizeSegments(int capcity) {
        LineSegment[] newSegments = new LineSegment[capcity];

        for (int i = 0; i < segments.length; i++) {
            newSegments[i] = segments[i];
        }

        segments = newSegments;
    }


    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
