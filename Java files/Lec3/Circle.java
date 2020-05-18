public class Circle {
    //So that the subclass can access
    protected final double radius;
    
    Circle(double radius) {
        this.radius = radius;
    }

    double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public String toString() {
        return "circle: area " + String.format("%.2f", getArea()) + 
            ", perimeter " + String.format("%.2f", getPerimeter());
    }
}
