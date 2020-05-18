public class SolidShape3D {
    private final Shape3D shape;
    private final Material material;

    SolidShape3D(Shape3D shape, Material material) {
        this.shape = shape;
        this.material = material;
    }

    public double getMass() {
        double m = this.shape.getVolume() * this.material.getDensity();
        return Math.round(m * 100.00)/ 100.00;
    }

    public double getDensity() {
        return this.material.getDensity();
    }

    @Override
    public String toString() {
        return "Solid" + this.shape.toString() + " with a mass of " + 
            String.format("%.2f", this.getMass());
    }
}

    
