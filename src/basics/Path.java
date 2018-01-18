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

package basics;

import java.util.Vector;

public class Path {
	private boolean repeat;
	private boolean reverse;
	//private boolean relative;
	private float speed;
	private Vector<Points> points;
	private Points current;
	private int direction;
	private int curstep;

	public Path(Points from, Points to, float speed) {
		// TODO Auto-generated constructor stub
		repeat = true;
		reverse = false;
		//relative = true;
		this.speed = speed;
		points = new Vector<Points>();
		points.add(from);
		points.add(to);
		current = new Points(from);
		direction = 0;
		curstep = 1;
	}
	
	public Path(Points from, Points to, float speed, boolean repeat, boolean reverse) {
		// TODO Auto-generated constructor stub
		this.repeat = repeat;
		this.reverse = reverse;
		//this.relative = relative;
		this.speed = speed;
		points = new Vector<Points>();
		points.add(from);
		points.add(to);
		current = new Points(from);
		direction = 0;
		curstep = 1;
	}
	
	public void addPoint(Points newpoint) {
		points.add(points.size()-1,newpoint);
	}
	
	public void delPoint(int index) {
		points.remove(index);
	}
	
	public void addPoint(Vector<Points> vec) {
		points.addAll(points.size()-1,vec);
	}
	
	public Points step() {
		Points calc = new Points(points.get(curstep).x-current.x, points.get(curstep).y-current.y);
		double floatdir = calc.arg();
		current.x += speed*Math.cos(floatdir);
		current.y += speed*Math.sin(floatdir);
		direction = (int) floatdir;
		calc = new Points(points.get(curstep).x-current.x, points.get(curstep).y-current.y);
		double newfloatdir = calc.arg();
		if (newfloatdir != floatdir) {
			curstep++;
			if (curstep >= points.size() && repeat) {
				curstep = 0;
				if (reverse) {
					curstep = 1;
					Vector<Points> cop = new Vector<Points>(points.size());
					for (int i=0;i<points.size();i++) {
						cop.set(cop.size()-i-1, points.get(i));
					}
					points = cop;
				}
			} else if (curstep >= points.size()) {
				curstep--;
			}
		}
		return current;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public boolean isReverse() {
		return reverse;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Vector<Points> getPoints() {
		return points;
	}

	public Points getCurrent() {
		return current;
	}

	public int getDirection() {
		return direction;
	}

	public int getCurstep() {
		return curstep;
	}

	/*public boolean isRelative() {
		return relative;
	}*/

}
