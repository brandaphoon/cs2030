import java.util.Scanner; //Import Scanner class 

class Main {
	
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		
		Point[] points = new Point[n];

		int max = 0;

		for (int i = 0; i < n; i ++){
			double x = sc.nextDouble();
			double y = sc.nextDouble();

			points[i] = new Point(x,y);
		}

		sc.close();
				
		for (int i = 0; i < n - 1; i ++){
			for (int k = i + 1; k < n; k ++) {
				int numCoverage = discCoverage(points, points[i], points[k]);
				if (max < numCoverage) {
					max = numCoverage;
				}
			}
		}	
		
		System.out.println("Maximum Disc Coverage: " + max);

	}


	public static int discCoverage(Point[] points, Point p, Point q){
		
		int pq = 0;
		int qp = 0;

		if (p.distanceTo(q) <= 2) {
			Circle pCircle = createCircle(p,q,1);
			Circle qCircle = createCircle(q,p,1);
			for (Point pt : points){
				if (pCircle.contains(pt)){
					pq++;
				}

				if (qCircle.contains(pt)){
				       qp++;
				}	       
			}
		}

		if (pq > qp) {
			return pq;
		} else {
			return qp;
		}
	}

	public static Circle createCircle(Point p, Point q, double radius){
		if (p.equals(q)){
			return null;
		}
		
		Point mid = p.midPoint(q);
		double angle = p.angleTo(q);
		double adj = Math.sqrt((radius*radius)-(mid.distanceTo(q)*mid.distanceTo(q)));
		
		Point c = mid.moveTo(Math.PI/2, adj);

		double dist = c.distanceTo(q);

		if (Math.abs(dist - radius) < 1E-3) {
			System.out.println(c);
			return Circle.getCircle(c,radius);
		} else {
			return null;
		}
	}
}
