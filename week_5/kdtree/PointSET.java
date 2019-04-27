import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

/* *****************************************************************************
 *  Name: Xinlin Song
 *  Date: 2019/1/30
 *  Description:
 **************************************************************************** */
public class PointSET {

    private final SET<Point2D> set;

    public PointSET() {
        set = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        set.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return set.contains(p);
    }

    public void draw() {
        // StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (Point2D point : set) {
            StdDraw.point(point.x(), point.y());
        }

    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        SET<Point2D> pointsInside = new SET<Point2D>();
        for (Point2D point : set) {
            if (point.x() >= rect.xmin() &&
                    point.x() <= rect.xmax() &&
                    point.y() >= rect.ymin() &&
                    point.y() <= rect.ymax()) {
                pointsInside.add(point);
            }
        }
        return pointsInside;

    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        double distMin = Double.POSITIVE_INFINITY;
        double distCurrent;
        Point2D nearest = new Point2D(0, 0);
        for (Point2D point : set) {
            distCurrent = p.distanceSquaredTo(point);
            if (distCurrent < distMin) {
                distMin = distCurrent;
                nearest = point;
            }
        }
        return nearest;

    }

    public static void main(String[] args) {
        // no test
    }
}
