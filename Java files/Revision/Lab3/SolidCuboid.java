public class SolidCuboid extends Cuboid implements SolidShape {
    private final double density;

    SolidCuboid(double height, double width, double length, double density) {
        super(height, width, length);
        this.density = density;
    }

    public double getMass() {
        double mass = this.getVolume() * this.density;
        return Math.round(mass * 100.0) / 100.0;
    }

    public double getDensity() {
        return this.density;
    }

    @Override
    public SolidCuboid setHeight(double h) {
        return new SolidCuboid(h, this.width, this.length, this.density);
    }

    @Override
    public SolidCuboid setWidth(double w) {
        return new SolidCuboid(this.height, w, this.length, this.density);
    }

    @Override
    public SolidCuboid setLength(double l) {
        return new SolidCuboid(this.height, this.width, l, this.density);
    }

    @Override
    public String toString() {
        return "Solid" + super.toString() + " with a mass of " + this.getMass();
    }
}
        
