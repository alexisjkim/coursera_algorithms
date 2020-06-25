package algorithms1.kdtrees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

public class KdTree {
    private Node head = null;
    private int size = 0;
    //  private KdTree tree;

    private class Node  {
        private Point2D point;
        private boolean level; //true = vertical, false = horizontal
        private Node left;
        private Node right;
        private double max; //greatest node in subtree

        public Node (Point2D point1, boolean direction) {
            point = point1;
            level = direction;
            left = null;
            right = null;
        }
    }

    // construct an empty set of points
    public KdTree(){

    }

    // is the set empty?
    public boolean isEmpty() {
        return (head == null);
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        if (head == null) {
            head = new Node(p, true);
            size ++;
        }

        else {

            if (!this.contains(p)) {
                Node current = head;

                while (!current.point.equals(p)) {

                    if (current.level) {
                        if (p.x() > current.point.x()) {
                            if (current.right == null) {
                                current.right = new Node(p, false);
                                break;
                            }
                            current = current.right;
                        }
                        else {
                            if (current.left == null) {
                                current.left = new Node(p, false);
                                break;
                            }
                            current = current.left;
                        }
                    }
                    else {
                        if (p.y() > current.point.y()) {
                            if (current.right == null) {
                                current.right = new Node(p, true);
                                break;
                            }
                            current = current.right;
                        }
                        else {
                            if (current.left == null) {
                                current.left = new Node(p, true);
                                break;
                            }
                            current = current.left;
                        }
                    }
                }


                size++;
            }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        Node current = head;
        while (current != null) {
            if (current.point.equals(p)) return true;
            if (current.level) {
                if (p.x() > current.point.x()) {
                    current = current.right;
                }
                else {
                    current = current.left;
                }
            } else {
                if (p.y() > current.point.y()) {
                    current = current.right;
                }
                else {
                    current = current.left;
                }
            }
        }
        return false;

    }

    // draw all points to standard draw
    public void draw() {
        Queue<Node> queue = new Queue<>();
        queue.enqueue(head);

        while (!queue.isEmpty()) {
            Node current = queue.dequeue();
            current.point.draw();

            if (current.right != null) queue.enqueue(current.right);
            if (current.left != null) queue.enqueue(current.left);


        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> stack = new Stack<>();
        if (head == null) return stack;
        if (rect == null) throw new IllegalArgumentException();

        Queue<Node> queue = new Queue<>();
        queue.enqueue(head);

        while (!queue.isEmpty()) {
            Node current = queue.dequeue();

            if (rect.xmin() <= current.point.x() && current.point.x() <= rect.xmax() && rect.ymin() <= current.point.y() && current.point.y() <= rect.ymax()) {
                stack.push(current.point);
            }

            if (current.level) {
                if (current.point.x() >= rect.xmax() && current.left != null) {
                    queue.enqueue(current.left);
                }
                else if (current.point.x() <= rect.xmin() && current.right != null) {
                    queue.enqueue(current.right);
                }
                else {
                    if (current.right != null) queue.enqueue(current.right);
                    if (current.left != null) queue.enqueue(current.left);

                }
            }
            else {
                if (current.point.y() >= rect.ymax() && current.left != null)  {
                    queue.enqueue(current.left);
                }
                else if (current.point.y() <= rect.ymin() && current.right != null) {
                    queue.enqueue(current.right);
                }
                else {
                    if (current.right != null) queue.enqueue(current.right);
                    if (current.left != null) queue.enqueue(current.left);
                }
            }
        }
        return stack;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (this.isEmpty()) return null;
        if (p == null) throw new IllegalArgumentException();

        Point2D answer = head.point;
        double distance = head.point.distanceTo(p);


        Queue<Node> queue = new Queue<>();
        queue.enqueue(head);

        while (!queue.isEmpty()) {
            Node current = queue.dequeue();
            if (current.point.distanceTo(p) < distance) {
                answer = current.point;
                distance = answer.distanceTo(p);
            }


            if (current.right != null) queue.enqueue(current.right);
            if (current.left != null) queue.enqueue(current.left);


        }
        return answer;
    }

    public static void main(String[] args) {
        KdTree a = new KdTree();
        a.insert(new Point2D(0.75,0.25));
        a.insert(new Point2D(0.0,0.0));
        a.insert(new Point2D(0.25,1.0));
        RectHV rect = new RectHV(0,0,2,2);

        Iterable<Point2D> stack = a.range(rect);
        // for (Point2D i : stack) {
        //     System.out.println(i.x());
        //     System.out.println(i.y());
        // }

        // System.out.println("nearest");
        // Point2D point = a.nearest(new Point2D(0,0));
        // System.out.println(point.x());
        // System.out.println(point.y());
    }
}