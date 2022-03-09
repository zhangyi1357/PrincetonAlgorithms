/* *****************************************************************************
 *  Name:       Zhang Yi
 *  Date:       2022/03/09
 *  Description:FastCollinearPoints
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private LineSegment[] lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        // check if points is null
        if (points == null)
            throw new IllegalArgumentException();
        // check if any point is null or repeated
        for (int i = 0; i < points.length; ++i) {
            if (points[i] == null)
                throw new IllegalArgumentException();
            for (int j = 0; j < i; ++j) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
        }


        ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
        Point[] sortedPoints = new Point[points.length];
        Point[] copyPoints = new Point[points.length];
        for (int i = 0; i < points.length; ++i) {
            sortedPoints[i] = points[i];
            copyPoints[i] = points[i];
        }
        Arrays.sort(sortedPoints, 0, points.length);
        for (int i = 0; i < points.length; ++i) {
            for (int j = 0; j < points.length; ++j)
                points[j] = sortedPoints[j];
            Arrays.sort(points, i + 1, points.length, points[i].slopeOrder());
            int start = i + 1, j;
            while (start + 2 < points.length) {
                if (points[i].compareTo(points[start]) > 0) {
                    ++start;
                    continue;
                }
                double iStart = points[i].slopeTo(points[start]);
                j = 1;
                while (start + j < points.length
                        && points[i].slopeTo(points[start + j]) == iStart)
                    ++j;
                if (j > 2) { // collinear happens
                    Point[] tmpPoints = new Point[j + 1];
                    tmpPoints[j] = points[i];
                    for (int k = 0; k < j; ++k)
                        tmpPoints[k] = points[start + k];
                    Arrays.sort(tmpPoints);
                    segments.add(new LineSegment(points[i], tmpPoints[j]));
                }
                start = start + j;
            }
        }

        int n = segments.size();
        lineSegments = new LineSegment[n];
        for (int i = 0; i < n; ++i)
            lineSegments[i] = segments.get(i);

        for (int i = 0; i < points.length; ++i)
            points[i] = copyPoints[i];
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
