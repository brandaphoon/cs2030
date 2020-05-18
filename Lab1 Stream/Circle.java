public class Circle {
    
    private final Point centre;
    private final double radius;

    private Circle(Point centre, double radius) {
        this.centre = centre;
        this.radius = radius;
    }

    public Point getCentre() {
        return this.centre;
    }

    public double getRadius() {
        return this.radius;
    }

    public static Circle getCircle(Point pt, double r) {
        if (r <= 0) {
            return null;
        } 
        return new Circle(pt, r);
    }

    @Override
    public String toString() {
        return "circle of radius " + String.format("%.1f", this.radius) +
        " centred at " + this.centre.toString();
    }


}