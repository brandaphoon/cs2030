class Point {
	private final double x;
	private final double y;

	Point(double x, double y){
		this.x = x;
		this.y = y;
	}

	// Point p = new Point(1,1);
	// p.setX(2);

	Point setX(double x){
		//Maintaining Immutability
		return new Point(x, this.y);
	}

	Point setY(double y){
		return new Point(this.x, y);
	}

	Point moveBy(double dx, double dy){
		return new Point(dx + this.x, dy + this.y);
	}
	
	@Override
	public boolean equals(Object obj){
		if (this == obj){
			return true;
		} else if (obj instanceof Point) {
			Point p = (Point) obj;
			return p.x == this.x && p.y == this.x;
		} else {
			return false;
		}
	}
	//(1.0,2.0)
	@Override
	public String toString(){
		return "(" + this.x + "." + this.y + ")";
	}		

	double distanceTo(Point otherPoint) {
		double dx = this.x - otherPoint.x;
		double dy = this.y - otherPoint.y;
		return Math.sqrt(dx * dx + dy * dy);
	}
}

