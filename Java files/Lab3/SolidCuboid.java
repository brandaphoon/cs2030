public class SolidCuboid extends Cuboid implements SolidShape { 
    private final double density;

    SolidCuboid(double height, double width, double length, double density) {
        super(height, width, length);
        this.density = density;
    }

    public double getDensity() {
        return this.density;
    }

    @Override
    SolidCuboid setHeight(double h) {
        return new SolidCuboid(h, this.width, this.length, this.density);
    }

    @Override
    SolidCuboid setWidth(double w) {
        return new SolidCuboid(this.height, w, this.length, this.density);
    }
    
    @Override
    SolidCuboid setLength(double l) {
        return new SolidCuboid(this.height, this.width, l, this.density);
    }
    
    public double getMass() {
        return this.getVolume() * this.getDensity();
    }

    @Override
    public String toString() {
/*        return "SolidCuboid [" + String.format("%.2f", this.height) + " x " +
            String.format("%.2f", this.width) + " x " + 
            String.format("%.2f", this.length) + "] with a mass of " + 
*/        return "Solid" + super.toString() + " with a mass of " + 
            String.format("%.2f", this.getMass());
    }
}
