class Circle{
	private final Point centre;
	private final double radius;

	private Circle(Point centre, double radius){
		this.centre = centre;
		this.radius = radius;
	}

	static Circle getCircle(Point centre, double radius){
		if (radius > 0){
			return new Circle(centre, radius);
		} else {
			return null;
		}
	}

	@Override
	public String toString(){
		return "circle of radius " + this.radius + " centered at " + this.centre;
	}
}
