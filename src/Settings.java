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

public class Settings implements Serializable {
	private static final long serialVersionUID = -1089513243433716745L;
	private boolean minimal_hud;
	private boolean fullscreen;
	private boolean auto_swap;
	private Keymap map;
	private int music_volume;
	private int sound_volume;
	private boolean music_mute;
	private boolean sound_mute;

	public Settings() {
		// TODO Auto-generated constructor stub
		minimal_hud = false;
		fullscreen = false;
		auto_swap = true;
		map = new Keymap();
		music_volume = 100;
		sound_volume = 100;
		music_mute = false;
		sound_mute = false;
	}
	
	public void save() {
		File f = new File("settings.conf");
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
	
	public static boolean isloadable() {
		File f = new File("settings.conf");
		if (!f.exists() || !f.isFile()) {
			return false;
		}
		return true;
	}
	
	public static Settings load() {
		if (!isloadable()) {
			return null;
		}
		File f = new File("settings.conf");
		ObjectInputStream file = null;
		boolean problem = false;
		Settings set = null;
		try {
			file = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
			try {
				set = (Settings)file.readObject();
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
		return set;
	}
	
	public boolean isMinimal_hud() {
		return minimal_hud;
	}

	public void setMinimal_hud(boolean minimal_hud) {
		this.minimal_hud = minimal_hud;
	}

	public boolean isFullscreen() {
		return fullscreen;
	}

	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}

	public boolean isAuto_swap() {
		return auto_swap;
	}

	public void setAuto_swap(boolean auto_swap) {
		this.auto_swap = auto_swap;
	}

	public Keymap getMap() {
		return map;
	}

	public int getMusic_volume() {
		return music_volume;
	}

	public void setMusic_volume(int music_volume) {
		if (music_volume < 0 || music_volume > 100) {
			return;
		}
		this.music_volume = music_volume;
	}

	public int getSound_volume() {
		return sound_volume;
	}

	public void setSound_volume(int sound_volume) {
		if (sound_volume < 0 || sound_volume > 100) {
			return;
		}
		this.sound_volume = sound_volume;
	}

	public boolean isMusic_mute() {
		return music_mute;
	}

	public void setMusic_mute(boolean music_mute) {
		this.music_mute = music_mute;
	}

	public boolean isSound_mute() {
		return sound_mute;
	}

	public void setSound_mute(boolean sound_mute) {
		this.sound_mute = sound_mute;
	}
	
}
