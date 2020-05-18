import java.lang.Math;

public class Sphere implements Shape3D {
    protected final double radius;

    Sphere(double r) {
        this.radius = r;
    }

    public double getVolume() {
        double v = (4/3) * Math.PI * Math.pow(this.radius,3);
        return Math.round(v * 100.00)/100.00;
    }

    public double getSurfaceArea() {
        double sa = 4 * Math.PI * Math.pow(this.radius,2);
        return Math.round(sa * 100.00)/100.00;
    }

    public Sphere setRadius(double r) {
        return new Sphere(r);
    }

    @Override
    public String toString() {
        return "Sphere [" + this.radius + "]";
    }
}
