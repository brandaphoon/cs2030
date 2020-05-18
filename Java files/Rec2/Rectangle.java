public class Rectangle {
	private final double width;
	private final double height;

	protected Rectangle(double width, double height){
		this.width = width;
		this.height = height;
	}
	
	@Override
	public String toString(){
		double area = this.width * this.height;
		double perimeter = (this.width * 2) + (this.height * 2);
		return "area " +  area + " perimeter " + perimeter;
	}

	/* public Rectangle setWidth(double width){
		this.width = width;
	}

	public Rectangle setHeight(double height){
		this.height = height;
	} */
}
