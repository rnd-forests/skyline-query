package skylinequeries.tools;

import skylinequeries.rtree.geometry.Point;
import skylinequeries.tools.stdlib.StdDraw;

import java.util.List;

/**
 * @author Vinh Nguyen
 */
public class Drawer implements Runnable {

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
    public void run() {
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
    }
}
