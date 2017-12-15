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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ShopSave implements Serializable {
	private static final long serialVersionUID = 8841831792493043301L;
	private static String name;
	private boolean[] bougth;
	private boolean[] onlyonetime;

	public ShopSave() {
		// TODO Auto-generated constructor stub
		bougth = ShopManager.getBought();
		onlyonetime = ShopManager.getOnlyonetime();
		name = Game.player.getName();
	}
	
	public void save() {
		File f = new File("Save/shop-"+name+".sav");
		ObjectOutputStream file = null;
		try {
			file = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
			file.writeObject(this);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static ShopSave load() {
		File f = new File("Save/shop-"+name+".sav");
		ObjectInputStream file = null;
		boolean problem = false;
		ShopSave sav = null;
		try {
			file = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
			try {
				sav = (ShopSave)file.readObject();
			} catch (ClassNotFoundException e) {
				problem = true;
			}
		} catch (FileNotFoundException e) {
			problem = true;
		} catch (IOException e) {
			problem = true;
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					problem = true;
				}
			}
		}
		if (problem) {
			return null;
		}
		return sav;
	}

	public boolean[] getBougth() {
		return bougth;
	}

	public boolean[] getOnlyonetime() {
		return onlyonetime;
	}

}
