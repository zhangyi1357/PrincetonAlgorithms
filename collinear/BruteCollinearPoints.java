/* *****************************************************************************
 *  Name:        Zhang Yi
 *  Date:        2022/03/08
 *  Description: BruteCollinearPoints detection
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {  // finds all line segments containing 4 points
        // check if points are null
        if (points == null)
            throw new IllegalArgumentException();
        // check if points are valid and not repeated
        for (int i = 0; i < points.length; ++i) {
            if (points[i] == null)
                throw new IllegalArgumentException();
            for (int j = 0; j < i; ++j) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
        }

        Point[] copyPoints = new Point[points.length];
        for (int i = 0; i < points.length; ++i) {
            copyPoints[i] = points[i];
        }
        Arrays.sort(points, 0, points.length);

        ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
        for (int i = 0; i < points.length; ++i) {
            for (int j = i + 1; j < points.length; ++j) {
                double ij = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < points.length; ++k) {
                    double ik = points[i].slopeTo(points[k]);
                    // StdOut.printf("ij : %f, ik: %f, i: %d, j: %d\n", ij, ik, i, j);
                    if (ij != ik)
                        continue;
                    for (int m = k + 1; m < points.length; ++m) {
                        double im = points[i].slopeTo(points[m]);
                        // StdOut.printf("ij: %f, ik: %f\n", ij, im);
                        if (ij == im) {
                            segments.add(new LineSegment(points[i], points[m]));
                        }
                    }
                }
            }
        }

        int n = segments.size();
        lineSegments = new LineSegment[n];
        for (int i = 0; i < n; ++i) {
            lineSegments[i] = segments.get(i);
        }

        for (int i = 0; i < points.length; ++i) {
            points[i] = copyPoints[i];
        }
    }

    public int numberOfSegments() { // the number of line segments
        return lineSegments.length;
    }

    public LineSegment[] segments() { // line segments
        return lineSegments;
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
