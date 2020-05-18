public class Point {

    private final double x;
    private final double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double getX() {
        return this.x;
    }

    double getY() {
        return this.y;
    }

    Point midPoint(Point q) {
        double xCoord = (this.x + q.getX()) / 2;
        double yCoord = (this.y + q.getY()) / 2;
        return new Point(xCoord, yCoord);
    }
    
    double angleTo(Point q) {
       if (this.equals(q)) {
            return 0;
        }

        double xD = q.getX() - this.x;
        double yD = q.getY() - this.y;
    
        if (xD == 0) {
            if (this.y < q.getY()) {
                return Math.PI / 2;
            } else {
                return -(Math.PI / 2);
            }
        } else if (yD == 0) {
            if (this.x < q.getX()) {
                return 0;
            } else {
                return Math.PI;
            }
        }

        double angle = Math.atan(xD / yD);

        if (this.x <= q.getX() && this.y <= q.getY()) {
            return angle;
        } else if (this.x > q.getX() && this.y > q.getY()) {
            return angle - Math.PI;
        } else if (this.x > q.getX() && this.y <= q.getY()) {
            return Math.PI + angle;
        } else {
            return angle;
        }
    }
    
    Point moveTo(double angle, double d) {
        double xCoord = this.x + d * Math.cos(angle);
        double yCoord = this.y + d * Math.sin(angle);
        return new Point(xCoord, yCoord);
    }
    
    double distanceTo(Point otherPoint) {
        double x = this.x - otherPoint.getX();
        double y = this.y - otherPoint.getY();
        return Math.sqrt(x * x + y * y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Point) {
            Point p = (Point) obj;
            return Math.abs(this.x - p.getX()) < 1E-3 && 
                Math.abs(this.y - p.getY()) < 1E-3;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "point (" + String.format("%.3f", this.x) + ", " + 
            String.format("%.3f",this.y) + ")";
    }

}
