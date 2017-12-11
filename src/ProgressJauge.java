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

public class ProgressJauge extends Jauge {
	protected Color[] fill;

	public ProgressJauge(Graphics g, float x1, float y1, float x2, float y2) {
		super(g, x1, y1, x2, y2);
		// TODO Auto-generated constructor stub
		fill = new Color[1];
		fill[0] = new Color(255,255,255);
	}

	public ProgressJauge(Graphics g, float x1, float y1, float x2, float y2, Color back, Color[] fill, Color outline) {
		super(g, x1, y1, x2, y2, back, fill[0], outline);
		// TODO Auto-generated constructor stub
		this.fill = fill;
	}

	public ProgressJauge(Graphics g, float x1, float y1, float x2, float y2, Color back, Color[] fill, Color outline,
			boolean vertical, boolean printed) {
		super(g, x1, y1, x2, y2, back, fill[0], outline, vertical, printed);
		// TODO Auto-generated constructor stub
		this.fill = fill;
	}
	
	public void draw() {
		Color old = g.getColor();
		g.setColor(back);
		g.fillRect(x1, y1, Math.abs(x2-x1), Math.abs(y2-y1));
		float increment;
		/*java.awt.Color javafill = new java.awt.Color(fill.getRed(), fill.getGreen(), fill.getBlue());
		java.awt.Color javafill2 = new java.awt.Color(fill2.getRed(), fill2.getGreen(), fill2.getBlue());
		int scale_color = (javafill2.getRGB()-javafill.getRGB())/10;
		int sign = 1;
		if (javafill2.getRGB() < javafill.getRGB()) {
			sign = -1;
		}*/
		float seg = 100f/((float)fill.length);
		if (vertical) {
			increment = Math.abs((y2-y1)/100.f);
			for (int i=fill.length-1;i>=fill.length-(int)percent()/seg;i--) {
				//g.setColor(new Color(javafill.getRGB()+(sign*i*scale_color)));
				g.setColor(fill[i]);
				g.fillRect(x1, y1+(increment*(100-percent()))+((fill.length-i)*seg*increment), Math.abs(x2-x1), seg*increment);
			}
			g.setColor(fill[fill.length-1]);
			g.fillRect(x1, y1+(increment*(100-percent()))/*+((percent()/seg)*seg*increment)*/, Math.abs(x2-x1), increment*(percent()-seg*(fill.length-1-fill.length+((int)percent()/seg))));
		} else {
			increment = Math.abs((x2-x1)/100.f);
			for (int i=fill.length-1;i>=0;i--) {
				//g.setColor(new Color(javafill.getRGB()+(sign*i*scale_color)));
				g.setColor(fill[i]);
				g.drawRect(x1+(i*seg*increment), y1, seg*increment, Math.abs(y2-y1));
			}
			g.setColor(fill[fill.length-1]);
			g.drawRect(x1+((percent()/seg)*seg*increment), y1, increment*percent(), Math.abs(y2-y1));
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

	public Color[] getFillScale() {
		return fill;
	}
	
	@Override
	public Color getFill() {
		return null;
	}

	public void setFill(Color[] fill) {
		this.fill = fill;
	}

}
