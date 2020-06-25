package algorithms1.collinear;/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;
    private int collinear;
    private int counter;
    private Point small;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }

        Point[] points1 = points.clone();

        Point[] points2 = Arrays.copyOf(points, points.length);

        //      LineSegment[] lst1 = new LineSegment[points1.length * points1.length * points1.length * points1.length];
        ArrayList<LineSegment> lst1 = new ArrayList<LineSegment>();

        Arrays.sort(points2);

        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        for (int i = 0; i < points2.length; i++) {

            Point p = points2[i];
            Arrays.sort(points1);
            Arrays.sort(points1, p.slopeOrder());
            //small =
            collinear = 1;

            for (int j = 0; j < points1.length - 1; j++) {
                if (p.slopeTo(points1[j]) == p.slopeTo(points1[j+1])) {

                    collinear++;
                    if (collinear == 2) {
                        small = points1[j];
                        collinear++;
                    }
                    else if (collinear >= 4 && j+1 == points1.length - 1) {
                        if (small.compareTo(p) == 1) {

                            LineSegment line = new LineSegment(p, points1[j+1]);

                            lst1.add(line);

                            counter ++;
                        }
                        collinear = 1;
                    }
                }

                else if (collinear >= 4) {

                    if (small.compareTo(p) == 1) {
                        LineSegment line = new LineSegment(p, points1[j]);

                        lst1.add(line);

                        counter ++;
                    }

                    collinear = 1;
                }
                else {
                    collinear = 1;
                }

            }

        }
        segments = new LineSegment[counter];
        for (int i=0; i<counter; i++) {
            segments[i] = lst1.get(i);
        }

    }


    public int numberOfSegments() {
        return counter;
    }

    public LineSegment[] segments() {
        return segments;
    }
    
    public static void main(String[] args) {
        Point a = new Point (10000,0);
        Point b = new Point(0,10000);
        Point c = new Point(3000,7000);
        Point d = new Point(7000,3000);
        Point e = new Point(20000,21000);
        Point f = new Point(3000,4000);
        Point g = new Point(14000,15000);
        Point h = new Point(6000,7000);

        Point[] lst = new Point[8];
        lst[0] = a;
        lst[1] = b;
        lst[2] = c;
        lst[3] = d;
        lst[4] = e;
        lst[5] = f;
        lst[6] = g;
        lst[7] = h;

        FastCollinearPoints x = new FastCollinearPoints(lst);

        LineSegment[] seg = x.segments();
        for (int i = 0; i < x.numberOfSegments(); i++) {
            System.out.println(seg[i].toString());
        }

    }
}

