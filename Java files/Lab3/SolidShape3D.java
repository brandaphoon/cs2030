public class SolidShape3D {

    private Shape3D shape;
    private Material material;

    SolidShape3D(Shape3D shape, Material material) {
        this.shape = shape;
        this.material = material;
    }

    double getDensity() {
        return this.material.getDensity();
    }

    double getMass() {
        return this.shape.getVolume() * this.getDensity();
    }
    
    @Override
    public String toString() {
        return "Solid" + this.shape.toString() + " with a mass of " + 
            String.format("%.2f", this.getMass());
    }
}
