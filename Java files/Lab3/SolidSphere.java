public class SolidSphere extends Sphere implements SolidShape {
       
    private double density;

    SolidSphere(double radius, double density) {
        super(radius);
        this.density = density;
    }

    public double getDensity(){
        return this.density;
    }

    public double getMass() {
        return this.getVolume() * this.density;
    }

    @Override
    SolidSphere setRadius(double r) {
        return new SolidSphere(r, this.density);
    }

    @Override
    public String toString() {
        return "Solid" + super.toString() + " with a mass of " +
            String.format("%.2f", this.getMass());
    }
}

