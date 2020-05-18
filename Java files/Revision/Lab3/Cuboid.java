public class Cuboid implements Shape3D {
    protected final double height;
    protected final double width;
    protected final double length;

    Cuboid(double height, double width, double length) {
        this.height = height;
        this.width = width;
        this.length = length;
    }

    public double getVolume() {
        double v = this.height * this.width * this.length;
        return Math.round(v * 100.0)/ 100.0;
    }

    public double getSurfaceArea() {
        double r =  (2 * length * width) + (2 * length * height) + 
            (2 * height * width);

        return Math.round(r * 100.0) / 100.0;
    }

    public Cuboid setHeight(double h) {
        return new Cuboid(h, this.width, this.length);
    }

    public Cuboid setWidth(double w) {
        return new Cuboid(this.height, w, this.length);
    }

    public Cuboid setLength(double l) {
        return new Cuboid(this.height, this.width, l);
    }

    @Override
    public String toString() {
        return "Cuboid [" + String.format("%.2f",this.height) + " x " + 
            String.format("%.2f", this.width) + " x " + String.format("%.2f", 
                    this.length) + "]";
    }
}
