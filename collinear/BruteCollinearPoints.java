/* *****************************************************************************
 *  Name:        Zhang Yi
 *  Date:        2022/03/08
 *  Description: BruteCollinearPoints detection
 **************************************************************************** */

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

        Arrays.sort(points, 0, points.length);

        ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
        for (int i = 0; i < points.length; ++i) {
            for (int j = i + 1; j < points.length; ++j) {
                double ij = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < points.length; ++k) {
                    double ik = points[i].slopeTo(points[k]);
                    if (ij != ik)
                        break;
                    for (int m = k + 1; m < points.length; ++m) {
                        double im = points[i].slopeTo(points[m]);
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

    }

    public int numberOfSegments() { // the number of line segments
        return lineSegments.length;
    }

    public LineSegment[] segments() { // line segments
        return lineSegments;
    }

    public static void main(String[] args) {
        
    }
}
