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
        for (int i = 0; i < points.length; ++i) {
            // copy the points to sortedPoints
            for (int j = 0; j < points.length; ++j)
                sortedPoints[j] = points[j];
            // sort the sortedPoints by slope order to points[i]
            Arrays.sort(sortedPoints, 0, points.length, points[i].slopeOrder());

            // start from 1 cause sortedPoints[0] is points[i] itself
            int start = 1;
            while (start + 2 < points.length) { // at least 3 points needed
                if (points[i].compareTo(sortedPoints[start]) > 0) {
                    ++start;
                    continue;
                }
                double iStart = points[i].slopeTo(sortedPoints[start]);
                int j = 1;
                while (start + j < points.length
                        && points[i].slopeTo(sortedPoints[start + j]) == iStart
                        && points[i].compareTo(sortedPoints[start + j]) <= 0)
                    ++j;
                if (start + j < points.length
                        && points[i].slopeTo(sortedPoints[start + j]) == iStart
                        && points[i].compareTo(sortedPoints[start + j]) > 0) {
                    start = start + j;
                    continue;
                }
                // collinear happens
                if (j > 2) {
                    Point[] tmpPoints = new Point[j];
                    for (int k = 0; k < j; ++k)
                        tmpPoints[k] = sortedPoints[start + k];
                    Arrays.sort(tmpPoints);
                    segments.add(new LineSegment(points[i], tmpPoints[j - 1]));
                }
                start = start + j;
            }
        }

        int n = segments.size();
        lineSegments = new LineSegment[n];
        for (int i = 0; i < n; ++i)
            lineSegments[i] = segments.get(i);
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
