public class Material {
    private final String name;
    private final double density;

    Material(String name, double density) {
        this.name = name;
        this.density = density;
    }

    public String getName() {
        return this.name; 
    }

    public double getDensity() {
        return this.density;
    }

}
