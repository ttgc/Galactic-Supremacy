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

public class Points {
	public double x;
	public double y;

	public Points() {
		// TODO Auto-generated constructor stub
		x = 0;
		y = 0;
	}
	
	public Points(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Points(Points other) {
		x = other.x;
		y = other.y;
	}
	
	public double distance(Points other) {
		return Math.abs(Math.sqrt((x*x)+(y*y))-Math.sqrt((other.x*other.x)+(other.y*other.y)));
	}
	
	public double module() {
		return Math.sqrt((x*x)+(y*y));
	}
	
	public double arg() {
		if (x == 0 && y == 0) {
			return -1;
		}
		double result = Math.acos(x/module());
		if (y < 0) {
			result = -result;
		}
		return result;
	}
	
	public void setPolarCoords(double r, double theta) {
		x = r*Math.cos(theta);
		y = r*Math.sin(theta);
	}

}
