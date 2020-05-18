class Point {

    private final double x;
    private final double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    Point setX(double x) {
        return new Point(x, this.y);
    }

    Point setY(double y) {
        return new Point(this.x,y);
    }

    double getX() {
        return this.x;
    }

    double getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "point (" + String.format("%.3f",this.x) + ", " + 
                String.format("%.3f",this.y) + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Point) {
            Point p = (Point) obj;
            return Math.abs(p.getX() - this.x) < 1E-3 && 
                    Math.abs(p.getY() - this.y) < 1E-3;
        } else {
            return false;
        }
    }

    Point midPoint(Point otherpoint) {
        double xCoord =  (this.x + otherpoint.getX())/2;
        double yCoord = (this.y + otherpoint.getY())/2;
        Point midpt = new Point(xCoord, yCoord);
        return midpt;
    }

    double angleTo(Point otherpoint) {
        double xC = otherpoint.getX() - this.x;
        double yC = otherpoint.getY() - this.y;
        return Math.atan2(yC, xC);

    }

    Point moveTo(double angle, double d) {
        double xCoord = this.x + d * Math.cos(angle);
        double yCoord = this.y + d * Math.sin(angle);
        Point pt = new Point(xCoord, yCoord);
        return pt;
    }

    double distanceTo(Point otherpoint) {
        double dx = this.x - otherpoint.getX();
        double dy = this.y - otherpoint.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

}
