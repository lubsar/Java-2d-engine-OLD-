package svk.sglubos.engine.gfx.particle;

public class ParticlePath {
	
	private int nextPoint;
	private int[][] points;
	private double[] velocities = new double[2];
	
	public ParticlePath(int[][] points) {
		this.points = points;
	}
	
	public void setNextPoint(int index) {
		nextPoint = index;
	}
	
	public double[] calculateVelocities(int x, int y) {
		int longditude = points[0][nextPoint] - x;
		int lactitude = points[1][nextPoint] - y;
		double distance = Math.sqrt(Math.pow(lactitude, 2) + Math.pow(longditude, 2)); 
		
		velocities[0] = longditude / distance;
		velocities[1] = lactitude / distance;
		
		return velocities;
	}
	
	public static double[] calculateVelocities(double x, double y, int xa, int ya) {
		double[] velocities = new double[2];
		
		double longditude = xa - x;
		double lactitude = ya - y;
		double distance = Math.sqrt(Math.pow(lactitude, 2) + Math.pow(longditude, 2)); 
		
		velocities[0] = longditude / distance;
		velocities[1] = lactitude / distance;
		
		return velocities; 
	}
	
	public double[] getVelocities() {
		return velocities;
	}
	
}
