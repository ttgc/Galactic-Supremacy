package gameplay;
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

import java.util.Vector;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

import states.levels.Level;

public class Dialog {
	private Vector<String> msg;
	private Vector<Integer> duration;
	private int position;
	private int time;
	private boolean started;

	public Dialog() {
		// TODO Auto-generated constructor stub
		msg = new Vector<String>();
		duration = new Vector<Integer>();
		position = 0;
		time = 0;
		started = false;
	}
	
	public void start() {
		started = true;
		time = 0;
	}
	
	public void stop() {
		started = false;
	}
	
	public void reset() {
		position = 0;
	}
	
	public void restart() {
		position = 0;
		start();
	}
	
	public void render(Graphics g) {
		if (!started) {
			return;
		}
		Color old = g.getColor();
		Font oldf = g.getFont();
		g.setColor(new Color(0,0,0,0.7f));
		g.fillRect(0, 472, 800, 128);
		g.setColor(new Color(255,255,255));
		g.setFont(Level.getFonts()[1]);
		g.drawString(msg.get(position), 4, 476);
		g.setColor(old);
		g.setFont(oldf);
	}
	
	public void update(int delta) {
		if (!started) {
			return;
		}
		time += delta;
		if (time >= duration.get(position)) {
			time = 0;
			if (position < msg.size()-1) {
				position++;
			} else {
				stop();
			}
		}
	}
	
	public void insertMsg(String msg, int duration) {
		//duration in ms
		this.msg.addElement(msg);
		this.duration.addElement(duration);
	}
	
	public String removeMsg(int index) {
		this.duration.remove(index);
		return this.msg.remove(index);
	}

	public String getMsg() {
		return msg.get(position);
	}

	public int getDuration() {
		return duration.get(position);
	}

	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		if (position >= 0 && position < msg.size()) {
			this.position = position;
		}
	}

	public int getTime() {
		return time;
	}

	public boolean isStarted() {
		return started;
	}

}
