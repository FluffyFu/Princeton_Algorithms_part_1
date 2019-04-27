/* *****************************************************************************
 *  Name: Xinlin Song
 *  Date: 12/31/2018
 *  Description: Fast algorithm to solve colinear problem. Time complexity is
 *      n^2 log(n). Throw a java.lang.IllegalArgumentException if the argument
 *      to the constructor is null, if any point in the array is null, or if
 *      the argument to the constructor contains a repeated point.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private int numberOfSegments = 0;
    private LineSegment[] lineSegments = new LineSegment[2];

    /*
    Find collinear segments.
     */
    public FastCollinearPoints(Point[] points) {
        // input array should not be null.
        if (points == null) throw new IllegalArgumentException();

        // input array should not have null.
        if (Arrays.asList(points).contains(null)) throw new IllegalArgumentException();

        // input array should not have duplicate points.
        Point[] copyPoints = Arrays.copyOfRange(points, 0, points.length);
        Arrays.sort(copyPoints);

        for (int i = 1; i < copyPoints.length; i++) {
            if (copyPoints[i - 1].compareTo(copyPoints[i]) == 0)
                throw new IllegalArgumentException();
        }
        // return if points length is less then 4.
        if (copyPoints.length < 4) return;


        for (int i = 0; i < copyPoints.length; i++) {
            Point origin = copyPoints[i];   // origin or pivot.
            findAndAddSegemntsWithPoint(origin, copyPoints);
        }

    }


    /*
    Find line segments the contains certain point.
     */

    /*
    private void findAndAddSegemntsWithPoint(Point origin, Point[] points) {
        int counter = 0;
        Comparator<Point> slopeOrder = origin.slopeOrder();

        // sort Points with slopeOrder, the first is always origin.
        Point[] copyPoints = Arrays.copyOfRange(points, 0, points.length);
        Arrays.sort(copyPoints, slopeOrder);

        int start = 1;
        int end = 2;
        while (end < points.length) {
            if (copyPoints[start].slopeTo(origin) == copyPoints[end].slopeTo(origin)) {
                counter++;
            }
            else {
                if (counter >= 2 && origin.compareTo(copyPoints[start]) < 0) {
                    addLineSegment(new LineSegment(origin, copyPoints[end - 1]));
                    numberOfSegments++;
                }
                counter = 0;
                start = end;
            }
            end++;
        }
        if (counter >= 2 && origin.compareTo(copyPoints[start]) < 0) {
            addLineSegment(new LineSegment(origin, copyPoints[end - 1]));
            numberOfSegments++;
        }
    }
    */


    private void findAndAddSegemntsWithPoint(Point origin, Point[] points) {
        int counter = 0;
        Comparator<Point> slopeOrder = origin.slopeOrder();

        // sort Points with slopeOrder, the first is always origin.
        Point[] copyPoints = Arrays.copyOfRange(points, 0, points.length);
        Arrays.sort(copyPoints, slopeOrder);

        int start = 1;
        int end = 2;
        while (end < points.length) {
            while ((end < points.length) &&
                    (copyPoints[start].slopeTo(origin) == copyPoints[end].slopeTo(origin))) {
                counter++;
                end++;
            }

            if (counter >= 2 && origin.compareTo(copyPoints[start]) < 0) {
                addLineSegment(new LineSegment(origin, copyPoints[end - 1]));
                numberOfSegments++;
            }
            counter = 0;
            start = end;
            end++;
        }
    }

    /*
    Add a line segment to array. resize array if needed.
     */
    private void addLineSegment(LineSegment segment) {
        if (numberOfSegments == lineSegments.length) {
            resizeLineSegments(lineSegments.length * 2);
        }
        lineSegments[numberOfSegments] = segment;
    }

    /*
    resize segment array.
    */
    private void resizeLineSegments(int capcity) {
        LineSegment[] newSegments = new LineSegment[capcity];

        for (int i = 0; i < lineSegments.length; i++) {
            newSegments[i] = lineSegments[i];
        }

        lineSegments = newSegments;
    }

    /*
    Return the number of collinear segments.
     */
    public int numberOfSegments() {
        return numberOfSegments;
    }

    /*
    Return an array of collinear segments.
     */
    public LineSegment[] segments() {
        return Arrays.copyOfRange(lineSegments, 0, numberOfSegments);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
