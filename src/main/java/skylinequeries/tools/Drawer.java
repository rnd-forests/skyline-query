package skylinequeries.tools;

import skylinequeries.rtree.geometry.Point;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * A utility class to draw the skyline. Note that this class is not thread-safe.
 *
 * @author Vinh Nguyen
 */
public class Drawer implements Callable {

    private List<Point> points;
    private List<Point> skylinePoints;
    private double xMin;
    private double yMin;
    private double xMax;
    private double yMax;

    public Drawer(List<Point> points, List<Point> skylinePoints,
                  double xMin, double yMin, double xMax, double yMax) {
        this.points = points;
        this.skylinePoints = skylinePoints;
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    @Override
    public Object call() throws Exception {
        StdDraw.setCanvasSize(600, 600);
        StdDraw.setXscale(xMin, xMax);
        StdDraw.setYscale(yMin, yMax);

        for (Point currentPoint : points) {
            if (skylinePoints.contains(currentPoint)) {
                StdDraw.setPenRadius(0.005);
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.point(currentPoint.x(), currentPoint.y());
            }
            else {
                StdDraw.setPenRadius(0.001);
                StdDraw.setPenColor(StdDraw.GRAY);
                StdDraw.point(currentPoint.x(), currentPoint.y());
            }
        }
        return null;
    }
}
