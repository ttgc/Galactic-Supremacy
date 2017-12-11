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

public class Hitbox {
	private int height;
	private int width;
	private double xor;
	private double yor;	

	public Hitbox(int w, int h) {
		// TODO Auto-generated constructor stub
		height = h;
		width = w;
		xor = 0;
		yor = 0;
	}
	
	public Hitbox(Hitbox hitbox) {
		// TODO Auto-generated constructor stub
		height = hitbox.height;
		width = hitbox.width;
		xor = hitbox.xor;
		yor = hitbox.yor;
	}
	
	public boolean check_collision_point(double x, double y) {
		return (x > xor-(width/2) && x < xor+(width/2) && y > yor-(height/2) && y < yor+(height/2));
	}

	public boolean check_collision(Hitbox other) {
		boolean col;
		boolean colreverse;
		col = (check_collision_point(other.xor-(other.width/2),other.yor-(other.height/2)) || 
				check_collision_point(other.xor-(other.width/2),other.yor+(other.height/2)) ||
				check_collision_point(other.xor+(other.width/2), other.yor-(other.height/2)) ||
				check_collision_point(other.xor+(other.width/2),other.yor+(other.height/2)));
		colreverse = (other.check_collision_point(xor-(width/2), yor-(height/2)) ||
				other.check_collision_point(xor-(width/2), yor+(height/2)) ||
				other.check_collision_point(xor+(width/2), yor-(height/2)) ||
				other.check_collision_point(xor+(width/2), yor+(height/2)));
		return (col || colreverse);
	}
	
	public void update(double x, double y) {
		xor = x;
		yor = y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
