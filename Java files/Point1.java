class Point{
		double [] coord;
		Point(double x, double y){
			coord = new double[]{x,y};
		}
		
		double distanceTo(Point otherpoint){
			double dispX = coord[0] - otherpoint.coord[0];
			double dispY = coord[1] - otherpoint.coord[1];
			return Math.sqrt(dispX * dispX + dispY * dispY);
		}
}
