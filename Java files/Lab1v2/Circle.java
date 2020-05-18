public class Circle {

    private final Point centre;
    private final double radius;
    
    private Circle(Point centre, double radius){
        this.centre = centre;
        this.radius = radius;
    }

    public Point getCentre() {
        return this.centre;
    }

    public double getRadius() {
        return this.radius;
    }

    /* boolean contains(Point pt) {
        return this.centre.distanceTo(pt) <= this.radius;
    }*/

    public static Circle getCircle(Point centre, double radius) {      
       if (radius > 0) {
           return new Circle(centre, radius);
       } else {
           return null;
       }
    }

    @Override
    public String toString() {
        return "circle of radius " + this.radius + " centered at "
            + this.centre;
    }
}


