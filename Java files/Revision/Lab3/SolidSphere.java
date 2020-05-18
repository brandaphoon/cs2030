public class SolidSphere extends Sphere implements SolidShape {
    private final double density;

    SolidSphere(double radius, double density) {
        super(radius);
        this.density = density;
    }

    public double getMass() {
        double m = this.density * this.getVolume();
        return Math.round(m * 100.00)/100.00;
    }

    public double getDensity() { 
        return this.density;
    }
    
    @Override
    public SolidSphere setRadius(double r) {
        return new SolidSphere(r, this.density);
    }

    @Override
    public String toString() {
        return "Solid" + super.toString() + " with a mass of " + this.getMass();
    }
}


