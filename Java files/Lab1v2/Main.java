import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        
        Point[] points = readPoints(n,sc);
  
        int coverage = 0;

        for (int i = 0; i < n-1; i++) {
            for (int j = i + 1; j < n; j++) {
                int cov = discCoverage(points[i], points[j], points);
                if (coverage < cov) {
                    coverage = cov;
                }  
            }
        }
        sc.close();
        System.out.println("Maximum Disc Coverage: " + coverage);
    }
    
    public static int discCoverage(Point p, Point q, Point[] points) {
        
        int coverage = 0;
        int altCoverage = 0;

        if (p.distanceTo(q) <= 2) {
            Circle circle = createCircle(p, q, 1);
            Circle altCircle = createCircle(q, p, 1);
            
            for (Point pt : points) { 
                if (contains(circle,pt)) {
                    coverage++;
                }
                if (contains(altCircle,pt)) {
                    altCoverage++;
                }
            }
        }

        if (coverage >= altCoverage) {
            return coverage;
        } else {
            return altCoverage;
       }
    }

    public static Point[] readPoints(int n, Scanner sc) {
        Point[] pts = new Point[n];
        
        //Scanner sc = new Scanner(System.in);

        for (int i = 0; i < n; i++) {
            double x = sc.nextDouble();
            double y = sc.nextDouble();
            pts[i] = new Point(x,y);
        }

        //sc.close();
        return pts;

    }

    public static boolean contains(Circle c, Point p) {
        return c.getCentre().distanceTo(p) <= c.getRadius();
    }

    public static Circle createCircle(Point p, Point q, double radius) {
        if (p.equals(q)) {
            return null;
        }

        Point mid = p.midPoint(q);
        double mqDist = mid.distanceTo(q);
        double adj = Math.sqrt((radius * radius) - (mqDist * mqDist));        
        
        if (mqDist > radius) {
            return null;
        }

        Point c;

        if (p.getX() > q.getX()) {
            c = mid.moveTo(- Math.PI / 2, adj);
        } else {
            c = mid.moveTo(Math.PI / 2, adj);
        }
       
        return Circle.getCircle(c, radius);
      
    }
}

