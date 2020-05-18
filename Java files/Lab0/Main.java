import java.util.Scanner; //Import Scanner class 

class Main {
	
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		
		double pX = sc.nextDouble();
		double pY = sc.nextDouble();

		double qX = sc.nextDouble();
		double qY = sc.nextDouble();

		double rad = sc.nextDouble();
		
		Circle c = createCircle(new Point(pX, pY), new Point(qX, qY), rad);

		if (c == null){
			System.out.println("No valid circle can be created");
		} else {
			System.out.println("Created: " + c);
		}
			
		
	}


	static Circle createCircle(Point p, Point q, double radius){
		if (p.equals(q)){
			return null;
		}
		
		Point mid = p.midPoint(q);
		double angle = p.angleTo(q);
		double adj = Math.sqrt((radius*radius)-(mid.distanceTo(q)*mid.distanceTo(q)));

		Point c = mid.moveTo(Math.PI/2, adj);
		double dist = c.distanceTo(q);

		if (dist - radius >= -0.0001 && dist - radius <= 0.0001){
			return Circle.getCircle(c,radius);
		} else {
			 return null;	
		}

	}
}
