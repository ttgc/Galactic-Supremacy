package settings;
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

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Input;

public class Keymap implements Serializable {
	private static final long serialVersionUID = 1778075373275552459L;
	private int up;
	private int down;
	private int left;
	private int right;
	private int shoot;
	private int rocket;
	private int shield;
	private int overclock;
	private int power;
	private int pause;

	public Keymap() {
		// TODO Auto-generated constructor stub
		up = Input.KEY_UP;
		down = Input.KEY_DOWN;
		left = Input.KEY_LEFT;
		right = Input.KEY_RIGHT;
		shoot = Input.KEY_SPACE;
		rocket = Keyboard.KEY_1;
		shield = Keyboard.KEY_2;
		overclock = Keyboard.KEY_3;
		power = Keyboard.KEY_4;
		pause = Keyboard.KEY_ESCAPE;
	}

	public int getUp() {
		return up;
	}

	public void setUp(int up) {
		this.up = up;
	}

	public int getDown() {
		return down;
	}

	public void setDown(int down) {
		this.down = down;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public int getShoot() {
		return shoot;
	}

	public void setShoot(int shoot) {
		this.shoot = shoot;
	}

	public int getRocket() {
		return rocket;
	}

	public void setRocket(int rocket) {
		this.rocket = rocket;
	}

	public int getShield() {
		return shield;
	}

	public void setShield(int shield) {
		this.shield = shield;
	}

	public int getOverclock() {
		return overclock;
	}

	public void setOverclock(int overclock) {
		this.overclock = overclock;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getPause() {
		return pause;
	}

	public void setPause(int pause) {
		this.pause = pause;
	}

}
