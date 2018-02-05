package basics;
/*******************************************************************************
	Galactic Supremacy, Shoot'em up game
	Copyright (C) 2017, 2018  PIOT Thomas
		
	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.
		
	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.
	
	You should have received a copy of the GNU General Public License
	along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

import java.io.Serializable;

public class RoundHitbox implements Serializable {
	private static final long serialVersionUID = -6960704870510041717L;
	private int r;
	private double xor;
	private double yor;

	public RoundHitbox(int r) {
		this.r = r;
		xor = 0;
		yor = 0;
	}

	public RoundHitbox(RoundHitbox hitbox) {
		this.r = hitbox.r;
		this.xor = hitbox.xor;
		this.yor = hitbox.yor;
	}
	
	public RoundHitbox(Hitbox hitbox) {
		r = Math.max(hitbox.getHeight(), hitbox.getWidth())/2;
		xor = hitbox.getXor();
		yor = hitbox.getYor();
	}
	
	@Override
	public String toString() {
		return "RoundHitbox [r=" + r + ", xor=" + xor + ", yor=" + yor + "]";
	}
	
	public boolean check_collision_point(double x, double y) {
		//double normehit = norme(xor,yor);
		//double normept = norme(x,y);
		return (norme(xor-x, yor-y) <= r);
	}
	
	public boolean check_collision(RoundHitbox hitbox) {
		//double norme1 = norme(xor,yor);
		//double norme2 = norme(hitbox.xor,hitbox.yor);
		return (norme(xor-hitbox.xor, yor-hitbox.yor) <= r+hitbox.r);
	}
	
	public void update(double x, double y) {
		xor = x;
		yor = y;
	}
	
	public static double norme(double x, double y) {
		return Math.sqrt((x*x)+(y*y));
	}
	
	public double angle_tan(double direction) {
		/*double x,y;
		x = r*Math.cos(Math.toRadians(direction));
		y = r*Math.sin(Math.toRadians(direction));
		double[] vecOA = {x-xor,y-yor};*/
		double theta = direction - 180;
		if (theta < 0) {
			theta += 360;
		}
		double angle = (180-direction)+2*(theta-90);
		if (angle < 0) {
			angle += 360;
		}
		return angle;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public double getXor() {
		return xor;
	}

	public double getYor() {
		return yor;
	}

}
