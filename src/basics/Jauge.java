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

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.FontUtils;

public class Jauge {
	protected Graphics g;
	protected boolean vertical;
	protected float x1;
	protected float y1;
	protected float x2;
	protected float y2;
	protected Color back;
	protected Color fill;
	protected Color outline;
	protected boolean printed;
	protected int value;
	protected int vmax;

	public Jauge(Graphics g, float x1, float y1, float x2, float y2) {
		this.g = g;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		vertical = false;
		back = new Color(255,255,255);
		fill = new Color(0,0,0);
		outline = new Color(0,0,0);
		printed = false;
		value = 0;
		vmax = 100;
	}
	
	public Jauge(Graphics g, float x1, float y1, float x2, float y2, Color back, Color fill, Color outline) {
		this.g = g;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		vertical = false;
		this.back = back;
		this.fill = fill;
		this.outline = outline;
		printed = false;
		value = 0;
		vmax = 100;
	}
	
	public Jauge(Graphics g, float x1, float y1, float x2, float y2, Color back, Color fill, Color outline, boolean vertical, boolean printed) {
		this.g = g;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.vertical = vertical;
		this.back = back;
		this.fill = fill;
		this.outline = outline;
		this.printed = printed;
		value = 0;
		vmax = 100;
	}
	
	public void update(int new_value) {
		if (new_value < 0 || new_value > vmax) {
			return;
		}
		value = new_value;
	}
	
	protected int percent() {
		return (value*100)/vmax;
	}
	
	public void draw() {
		Color old = g.getColor();
		g.setColor(back);
		g.fillRect(x1, y1, Math.abs(x2-x1), Math.abs(y2-y1));
		g.setColor(fill);
		float increment;
		if (vertical) {
			increment = Math.abs((y2-y1)/100.f);
			g.fillRect(x1, y1+(increment*(100-percent())), Math.abs(x2-x1), increment*percent());
		} else {
			increment = Math.abs((x2-x1)/100.f);
			g.fillRect(x1, y1, increment*percent(), Math.abs(y2-y1));
		}
		g.setColor(outline);
		g.drawRect(x1, y1, Math.abs(x2-x1), Math.abs(y2-y1));
		if (printed) {
			g.setColor(new Color(0,0,0));
			//g.drawString(value+"", (x2+x1)/2-5, (y2+y1)/2-5);
			FontUtils.drawCenter(g.getFont(), value+"", (int)x1, (int)((y2+y1)/2)-8, (int)(x2-x1), g.getColor());
		}
		g.setColor(old);
	}

	public boolean isVertical() {
		return vertical;
	}

	public void setVertical(boolean vertical) {
		this.vertical = vertical;
	}

	public Color getBack() {
		return back;
	}

	public void setBack(Color back) {
		this.back = back;
	}

	public Color getFill() {
		return fill;
	}

	public void setFill(Color fill) {
		this.fill = fill;
	}

	public Color getOutline() {
		return outline;
	}

	public void setOutline(Color outline) {
		this.outline = outline;
	}

	public boolean isPrinted() {
		return printed;
	}

	public void setPrinted(boolean printed) {
		this.printed = printed;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		update(value);
	}

	public int getVmax() {
		return vmax;
	}

	public void setVmax(int vmax) {
		this.vmax = vmax;
		if(vmax < value) {
			value = vmax;
		}
	}

	public float getX1() {
		return x1;
	}

	public float getY1() {
		return y1;
	}

	public float getX2() {
		return x2;
	}

	public float getY2() {
		return y2;
	}

}
