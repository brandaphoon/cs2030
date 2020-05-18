import java.util.Scanner;
import java.util.stream.*;
import java.util.function.Consumer;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Point[] points = readPoints(sc);

        /*for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                int dc = discCoverage(points[i], points[j], points);
                if (dc > max) {
                    max = dc;
                }
            }
        }*/

        /*int max = Stream.iterate(0, x -> x + 1)
            .limit(points.length-1)
            .map(x -> discCoverage(points[x], points[x+1], points))
            .max(Integer::compare).get();*/

            
        int max = Arrays.stream(points)
                        .map(
                            pt -> Arrays.stream(points)
                            .map(x -> {
                                if (x == pt) {
                                    return 0;
                                } else {
                                    return discCoverage(pt,x,points);
                                }
                            })
                            .max(Integer::compare).get())
            .max(Integer::compare).get();
            

        
            
        

        System.out.println("Maximum Disc Coverage: " + max);

        sc.close();
    }

    public static Point[] readPoints(Scanner sc) {
        //int numPoints = sc.nextInt();
        Point[] points = Stream.iterate(0, x -> x + 1)
                                .limit(sc.nextInt())
                                .map(x -> new Point(sc.nextDouble(), sc.nextDouble()))
                                .toArray(s -> new Point[s]);
                                //converting array to point
        
        /*new Point[numPoints];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(sc.nextDouble(), sc.nextDouble());
        }*/

        return points;
    }

    public static Circle createCircle(Point p, Point q, double radius) {
        if (p.equals(q)) {
            return null;
        }

        Point m = p.midPoint(q);
        double pmDist = p.distanceTo(m);

        if (radius < pmDist) {
            return null;
        }

        double pqAngle = p.angleTo(q);
        double pcAngle = Math.acos(pmDist / radius);
        double angle = pqAngle + pcAngle;
        Point centre;

        if (angle <= Math.PI) {
            centre = p.moveTo(angle, radius);
        } else {
            centre = p.moveTo(- (2 * Math.PI - angle), radius);
        }

        return Circle.getCircle(centre, radius);
    }

    public static boolean contains(Circle c, Point p) {
        return c.getCentre().distanceTo(p) <= c.getRadius();
    }

    public static int discCoverage(Point p, Point q, Point[] points) {
        
        int coverage = 0;
        int altCoverage = 0;

        if (p.distanceTo(q) <= 2) {
            Circle circle = createCircle(p, q, 1);
            Circle altCircle = createCircle(q, p, 1);
            
            coverage = (int) Stream.iterate(0, x -> x + 1).limit(points.length).filter(x -> contains(circle, points[x])).count();
             
            altCoverage = (int) Stream.iterate(0, x -> x + 1).limit(points.length).filter(x -> contains(altCircle, points[x])).count();
            /*for (Point pt : points) { 
                if (contains(circle,pt)) {
                    coverage++;
                }
                if (contains(altCircle,pt)) {
                    altCoverage++;
                }
            }*/
        }

        return (coverage >= altCoverage) ?  coverage : altCoverage;
    }

}

