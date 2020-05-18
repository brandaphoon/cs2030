public class Cuboid implements Shape3D{
    
    protected final double height;
    protected final double width;
    protected final double length;

    protected Cuboid(double height, double width, double length) {
        this.height = height;
        this.width = width;
        this.length = length; 
    }
    
    double getHeight() {
        return this.height;
    }

    double getWidth() {
        return this.width;
    }

    double getLength() {
        return this.length;
    }
    
    @Override
    public double getVolume() {
        return this.height * this.width * this.length;
    }

    @Override
    public double getSurfaceArea() {
        double lw = this.length * this.width;
        double lh = this.length * this.height;
        double hw = this.height * this.width;
        return (2*lw) + (2*lh) + (2*hw);
    }

    Cuboid setHeight(double h) {
        return new Cuboid(h, this.width, this.length);
    }

    Cuboid setWidth(double w) {
        return new Cuboid(this.height, w, this.length);
    }

    Cuboid setLength(double l) {
        return new Cuboid(this.height, this.width, l);
    }
   

    @Override
    public String toString() {
        return "Cuboid [" + String.format("%.2f", this.height) + " x " + 
            String.format("%.2f", this.width) + " x " + 
            String.format("%.2f", this.length) + "]";
    }
}
