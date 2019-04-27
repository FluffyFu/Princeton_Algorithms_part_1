/* *****************************************************************************
 *  Name: Xinlin Song
 *  Date: 2/15/2019
 *  Description: KdTree
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private Node root;
    private int size = 0;
    private Point2D nearestPoint;
    private double shortestDistSquare;

    private static class Node {
        private final Point2D p;
        private final RectHV rect;
        private Node lb;
        private Node rt;

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("calls insert() with a null key");
        // no need to insert if p is already in the tree.
        if (contains(p)) return;

        if (root == null) {
            root = insert(null, p, true, new RectHV(0.0, 0.0, 1.0, 1.0));
            size++;
        }
        else {
            root = insert(root, p, true, root.rect);
            size++;
        }
    }

    /*
    helper function to insert a point to a kd-tree.

    Args:
        x (Node): current root node.
        p (Point2D): the point needs to be inserted.
        vertical (bool): weather comparing x coordinate.
        curRect (RectHV): corresponding rectangle of point p.

     */

    private Node insert(Node x, Point2D p, boolean vertical, RectHV curRect) {
        // create a leaf
        if (x == null) {
            return new Node(p, curRect);
        }
        // compare x coordinates (draw verticle line)
        if (vertical) {
            // smaller than the root, go to left subtree.
            if (Point2D.X_ORDER.compare(p, x.p) < 0) {
                // reuse the existing rectangle
                // only create new RectHV for leaves.
                if (x.lb != null) {
                    x.lb = insert(x.lb, p, false, x.lb.rect);
                }
                else {
                    curRect = getRect(x, p, true, x.rect);
                    x.lb = insert(x.lb, p, false, curRect);
                }
            }
            // larger than the root, go to right subtree.
            else if (Point2D.X_ORDER.compare(p, x.p) >= 0) {
                if (x.rt != null) {
                    x.rt = insert(x.rt, p, false, x.rt.rect);
                }
                else {
                    curRect = getRect(x, p, true, x.rect);
                    x.rt = insert(x.rt, p, false, curRect);
                }
            }
        }
        // compare y coordinates (draw horizontal line)
        else {
            if (Point2D.Y_ORDER.compare(p, x.p) < 0) {
                if (x.lb != null) {
                    x.lb = insert(x.lb, p, true, x.lb.rect);
                }
                else {
                    curRect = getRect(x, p, false, x.rect);
                    x.lb = insert(x.lb, p, true, curRect);
                }
            }
            else if (Point2D.Y_ORDER.compare(p, x.p) >= 0) {
                if (x.rt != null) {
                    x.rt = insert(x.rt, p, true, x.rt.rect);
                }
                else {
                    curRect = getRect(x, p, false, x.rect);
                    x.rt = insert(x.rt, p, true, curRect);
                }
            }
        }
        return x;
    }

    /*
    Get the corresponding rectangle of p, given root x.

    Args:
        x: current root
        p: point need to be inserted
        verticle: weather the deviding line is drawn vertically
        rootRec: the correspondig rectangle of root x.

     */
    private RectHV getRect(Node x, Point2D p, boolean vertical, RectHV rootRec) {
        double preXMin = rootRec.xmin();
        double preYMin = rootRec.ymin();
        double preXMax = rootRec.xmax();
        double preYMax = rootRec.ymax();
        RectHV currentRec;
        if (vertical) {
            if (Point2D.X_ORDER.compare(p, x.p) < 0)
                currentRec = new RectHV(preXMin, preYMin, x.p.x(), preYMax);
            else
                currentRec = new RectHV(x.p.x(), preYMin, preXMax, preYMax);
        }
        else {
            if (Point2D.Y_ORDER.compare(p, x.p) < 0)
                currentRec = new RectHV(preXMin, preYMin, preXMax, x.p.y());
            else
                currentRec = new RectHV(preXMin, x.p.y(), preXMax, preYMax);

        }
        return currentRec;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("calls contains with a null key!");
        return contains(root, p, true);
    }

    private boolean contains(Node x, Point2D p, boolean vertical) {
        if (x == null) return false;
        if (x.p.equals(p)) return true;
        if (vertical) {
            if (p.x() < x.p.x()) return contains(x.lb, p, false);
            else return contains(x.rt, p, false);
        }
        else {
            if (p.y() < x.p.y()) return contains(x.lb, p, true);
            else return contains(x.rt, p, true);
        }
    }

    public void draw() {
        inorderTraverse(root, true);
    }

    private void draw(Node curNode, boolean verticle) {
        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(curNode.p.x(), curNode.p.y());
        StdDraw.setPenRadius(0.005);
        // draw verticle line
        if (verticle) {
            StdDraw.setPenColor(StdDraw.MAGENTA);
            double x = curNode.p.x();
            double yMin = curNode.rect.ymin();
            double yMax = curNode.rect.ymax();
            StdDraw.line(x, yMin, x, yMax);
        }
        // draw horizontal line
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            double xMin = curNode.rect.xmin();
            double y = curNode.p.y();
            double xMax = curNode.rect.xmax();
            StdDraw.line(xMin, y, xMax, y);
        }

    }

    private void inorderTraverse(Node curRoot, boolean verticle) {
        if (curRoot != null) {
            inorderTraverse(curRoot.lb, !verticle);
            draw(curRoot, verticle);
            inorderTraverse(curRoot.rt, !verticle);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        // stack to store points in rect
        Stack<Point2D> stack = new Stack<Point2D>();
        range(root, rect, stack);
        return stack;
    }

    /*
    Helper function to get all the points in the KdTree within the given rectangle.
     */
    private void range(Node curRoot, RectHV rect, Stack<Point2D> stack) {
        if (curRoot == null) {
            return;
        }
        if (rect.contains(curRoot.p))
            stack.push(curRoot.p);
        if ((curRoot.lb != null) && (curRoot.lb.rect.intersects(rect)))
            range(curRoot.lb, rect, stack);
        if ((curRoot.rt != null) && (curRoot.rt.rect.intersects(rect)))
            range(curRoot.rt, rect, stack);
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        else {
            nearestPoint = root.p;
            shortestDistSquare = nearestPoint.distanceSquaredTo(p);
            nearest(root, p);
            return nearestPoint;
        }
    }

    /*
    Helper function to find the nearest point in the KdTree for a given point p.
    Args:
        root (Node): current root of the KdTree.
        nearestPoint (Point2D): current nearest point.
        shortestDistSquare (double): current distance square between nearestPoint
            and the given point p.
     */

    private void nearest(Node curRoot, Point2D p) {
        // no need to go further if shortestDistSquare is already
        // smaller than the distance square between root rectangle and point p.
        if (curRoot.rect.distanceSquaredTo(p) >= shortestDistSquare) return;
        // reach a leaf
        if ((curRoot.lb == null) && (curRoot.rt == null))
            return;

        // check if current node is the nearest point
        double distSquare = curRoot.p.distanceSquaredTo(p);
        if (distSquare < shortestDistSquare) {
            nearestPoint = curRoot.p;
            shortestDistSquare = distSquare;
        }

        else if (curRoot.lb == null) {
            nearest(curRoot.rt, p);
        }

        else if (curRoot.rt == null) {
            nearest(curRoot.lb, p);
        }

        // both rt and lb are not null
        else {
            // both lb and rt are possible.
            // if lb rectanlge is closer to p search lb first, otherwise search rt first.
            if (curRoot.lb.rect.distanceSquaredTo(p) < curRoot.rt.rect
                    .distanceSquaredTo(p)) {
                nearest(curRoot.lb, p);
                nearest(curRoot.rt, p);
            }
            else {
                nearest(curRoot.rt, p);
                nearest(curRoot.lb, p);
            }

        }

    }

    public static void main(String[] args) {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.7, 0.2));
        tree.insert(new Point2D(0.7, 0.2));
        tree.insert(new Point2D(0.9, 0.9));
        tree.insert(new Point2D(0.4, 0.7));
        tree.insert(new Point2D(0.9, 0.6));
        tree.insert(new Point2D(0.6, 0.4));


        tree.draw();
        //
        // StdDraw.setPenColor(StdDraw.GREEN);
        // StdDraw.setPenRadius(0.1);
        // Point2D point = new Point2D(0.5, 0.5);
        // point.draw();
        // Point2D nearest = tree.nearest(point);
        //
        // StdDraw.setPenColor(StdDraw.ORANGE);
        // nearest.draw();
        // StdOut.println(tree.contains(new Point2D(0.7, 0.2)));
        // StdOut.println(tree.contains(new Point2D(0.5, 0.4)));
        // StdOut.println(tree.contains(new Point2D(0.2, 0.3)));
        // StdOut.println(tree.contains(new Point2D(0.6, 0.4)));
        StdOut.println(tree.size());
        // StdOut.println(tree.isEmpty());

        // int n = Integer.parseInt(args[0]);
        // for (int i = 0; i < n; i++) {
        //     double x = StdRandom.uniform(0.0, 1.0);
        //     double y = StdRandom.uniform(0.0, 1.0);
        //     StdOut.printf("%8.6f %8.6f\n", x, y);
        //     tree.insert(new Point2D(x, y));
        //     // tree.draw();
        //
        // }
        // RectHV rect = new RectHV(0.5, 0.5, 1.0, 1.0);
        // // StdOut.printf("contained points");
        // int num = 0;
        // for (Point2D point : tree.range(rect)) {
        //     // StdOut.println(point);
        //     num++;
        // }
        // StdOut.printf("number of points contained %d \n", num);
    }
}
