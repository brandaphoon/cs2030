public class Material {
    private String name;
    private double density;

    Material(String name, double density) {
        this.name = name;
        this.density = density;
    }

    double getDensity() {
        return this.density;
    }

    String getName() {
        return this.name;
    }
}
