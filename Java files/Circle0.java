class Circle{
	Point centre;
	double radius;

	Circle(Point centre, double radius){
		this.centre = centre;
		this.radius = radius;
	}
	
	boolean contains(Point otherPoint){
		double dx = this.centre.x - otherPoint.x;
		double dy = this.centre.y - otherPoint.y;
		double dist = Math.sqrt(dx*dx + dy*dy);
		return dist < this.radius;
	}

}
