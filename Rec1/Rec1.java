import sun.launcher.resources.launcher;
import java.util.Scanner;

class Vector2D {
    private double[] coord2D;

    Vector2D(double x, double y) {
        double[] c = {x,y};
        this.coord2D = c;
    }

    void add(Vector2D v) {
        double newX = this.coord2D[0] + v.coord2D[0];
        double newY = this.coord2D[1] + v.coord2D[1];
        double[] c = {newX, newY};
        this.coord2D = c;
    }
}

class Point {
    private double x;
    private double y;
    
    Point(double x, double y) {
    this.x = x;
    this.y = y;
    }

    double distance(Point otherpoint) {
        double dispX = this.x - otherpoint.x;
        double dispY = this.y - otherpoint.y;
        return Math.sqrt(dispX * dispX + dispY * dispY);
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
    
class Circle {
    private Point centre;
    private double radius;
    
    Circle(Point centre) {
        this.centre = centre;
        this.radius = 1.0;
    }

    Circle(Point centre, double radius) {
        this.centre = centre;
        this.radius = radius;
    }
    
    boolean contains(Point point) {
        return centre.distance(point) <= radius;
    }


    @Override
    public String toString() {
        return "Circle centred at " + this.centre +
        " with radius " + this.radius;
    }
}

class Three {
    static int countCoverage(Circle circle, Point[] points) {  
        int n = 0;
        for (Point pt: points) {
            if (circle.contains(pt)) {
                n++;
            }
        }
        return n;
    }

    static void findCoverage(Point[] points) {
        Circle[] circles = new Circle[points.length];
        for (int i = 0; i < points.length; i++) {
            circles[i] = new Circle(points[i]);
        }

        for (Circle c : circles) {
            int num = countCoverage(c, points);
            System.out.println(c.toString() + "contains " + num + " points." );
        }

    }

}

class Main {

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();

        Point[] points = new Point[num]; 
        for (int i = 0; i < num; i++) {
            double x = sc.nextDouble();
            double y = sc.nextDouble();
            points[i] = new Point(x,y);
        }

        Three.findCoverage(points);

        sc.close();
        //remember to close scanner
        //do not use two scanner
    }

}
