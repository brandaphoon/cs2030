import java.util.stream.*;
import java.util.ArrayList;


public class Point {
    private final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double distanceTo(Point q) {
        double dx = this.x - q.x;
        double dy = this.y - q.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public Point midPoint(Point q) {
        double midPointX = (this.x + q.x) / 2;
        double midPointY = (this.y + q.y) / 2;
        return new Point(midPointX, midPointY);
    }

    /*public Point midPoint(Point pt){
        return Stream.of(pt, this).reduce((f,s) -> new Point(
            Math.abs(f.getX() - s.getX())/2, Math.abs(f.getY() - s.getY())/2
            )).get();
         
    }*/

    public double angleTo(Point q) {
        // Points are the same
        if (this.equals(q)) {
            return 0;
        }

        double xDist = q.x - this.x;
        double yDist = q.y - this.y;

        if (xDist == 0) {
            if (q.y > this.y) {
                return Math.PI / 2;
            } else {
                return - Math.PI  / 2;
            }
        } else if (yDist == 0) {
            if (q.x > this.x) {
                return 0;
            } else {
                return Math.PI;
            }
        }

        double refAngle = Math.atan(xDist / yDist);

        if (q.x >= this.x && q.y >= this.y) {
            return refAngle;
        } else if (q.x < this.x && q.y >= this.y) {
            return Math.PI + refAngle;
        } else if (q.x < this.x && q.y < this.y) {
            return refAngle - Math.PI;
        } else {
            return refAngle;
        }
    }

    Point moveTo(double angle, double d) {
        //double xCoord = this.x + d * Math.cos(angle);
        //double yCoord = this.y + d * Math.sin(angle);
        //return new Point(xCoord, yCoord);
        if (d == 0) {
            return this;
        }
        return Stream.of(this)
                    .map(x -> new Point(x.getX() + d * Math.cos(angle), x.getY() + d * Math.sin(angle)))
                    .findFirst().get();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Point) {
            Point p = (Point) obj;
            return Math.abs(this.x - p.x) < 1E-15 && Math.abs(this.y - p.y) < 1E-15;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "point (" + String.format("%.3f",this.x) + ", "+ String.format("%.3f",this.y) + ")";
    }
}