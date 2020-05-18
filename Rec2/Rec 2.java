class Rectangle {
    protected double height;
    protected double width;

    Rectangle(double height, double width) {
        this.height = height;
        this.width = width;
    }

    double getArea() {
        return height * width;
    }

    double getPerimeter() {
        return (2 * height) + (2* width);
    }

    public Rectangle setWidth(double width) {
        return new Rectangle(this.height, width);
    }

    public Rectangle setHeight(double height) {
        return new Rectangle(height, this.width);
    }

    @Override
    public String toString() {
        return "area " + this.getArea() + " and perimeter " + this.getPerimeter();
    }
}

class Square extends Rectangle {

    Square(double l) {
        super(l,l);
    }

    @Override
    public Square setWidth(double width) {
        return new Square(width);
    }

    @Override
    public Square setHeight(double height) {
        return new Square(height);
    }

}

class Main {
    public static void main(String[] args) {
        double sum = 0.0;

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
    }
}