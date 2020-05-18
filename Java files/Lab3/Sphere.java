public class Sphere implements Shape3D {

    protected final double radius;

    Sphere(double radius) {
        this.radius = radius;
    }

    double getRadius() {
        return this.radius;
    }
    
    public double getSurfaceArea() {
        return (4 * Math.PI * Math.pow(this.radius,2));
    }

    public double getVolume() {
        return (4 * Math.PI * Math.pow(this.radius, 3)) / 3;
    }

    Sphere setRadius(double r) {
        return new Sphere(r);
    }

    @Override
    public String toString() {
        return "Sphere [" + String.format("%.2f", this.radius) + "]";
    }
}
