import java.awt.Color;

public class FilledCircle extends Circle {
    private final Color color;

    FilledCircle(double radius, Color color) {
        super(radius);
        this.color = color;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + getColor();
    }
}
