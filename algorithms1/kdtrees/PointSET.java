package algorithms1.kdtrees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;
import java.util.Iterator;

public class PointSET {
    // private TreeSet<Point2D> tree;
    private SET<Point2D> tree;

    // construct an empty set of points
    public PointSET(){
        //    tree = new TreeSet<>(new comparator1());
        tree = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    // number of points in the set
    public int size() {
        return tree.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        if (!tree.contains(p)) {
            tree.add(p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return tree.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        Iterator<Point2D> points = tree.iterator();
        while (points.hasNext()) {
            points.next().draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Stack<Point2D> stack = new Stack<>();
        if (tree.isEmpty()) return null;

        Iterator<Point2D> points = tree.iterator();

        while ( points.hasNext()) {
            Point2D current = points.next();

            if (rect.xmin() <= current.x() && current.x() <= rect.xmax() && rect.ymin() <= current.y() && current.y() <= rect.ymax()) {
                stack.push(current);
            }
        }
        return stack;


    }


    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (tree.isEmpty()) return null;

        Iterator<Point2D> points = tree.iterator();

        Point2D current = points.next();
        Point2D answer = current;
        double distance = answer.distanceTo(p);

        while ( points.hasNext()) {
            current = points.next();

            if (current.distanceTo(p) < distance) {
                answer = current;
                distance = answer.distanceTo(p);
            }
        }
        return answer;

    }

    private class comparator1 implements Comparator<Point2D> {

        public int compare(Point2D p1, Point2D p2) {
            if (p1.x() > p2.x()) return 1;
            else return -1;
        }
    }

    public static void main(String[] args) {
        PointSET a = new PointSET();
        System.out.println(a.size());
        a.insert(new Point2D(0,1));
        System.out.println(a.size());
        a.insert(new Point2D(0,1));
        System.out.println(a.size());


    }
}