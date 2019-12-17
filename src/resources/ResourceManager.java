package resources;

import java.util.HashMap;

import resources.loader.FontLoadable;
import resources.loader.FontLoaderData;
import resources.loader.ImageLoadable;
import resources.loader.MusicLoadable;
import resources.loader.ResourceLoader;
import resources.loader.SoundLoadable;

public final class ResourceManager {
	public final ResourceManager instance = new ResourceManager();
	
	private HashMap<String, ResourceLoader<ImageLoadable, String>> images;
	private HashMap<String, ResourceLoader<MusicLoadable, String>> musics;
	private HashMap<String, ResourceLoader<SoundLoadable, String>> sounds;
	private HashMap<String, ResourceLoader<FontLoadable, FontLoaderData>> fonts;

	private ResourceManager() {
		// TODO Auto-generated constructor stub
		images = new HashMap<String, ResourceLoader<ImageLoadable, String>>();
		musics = new HashMap<String, ResourceLoader<MusicLoadable, String>>();
		sounds = new HashMap<String, ResourceLoader<SoundLoadable, String>>();
		fonts = new HashMap<String, ResourceLoader<FontLoadable, FontLoaderData>>();
		
		// TODO load images
		
		// load musics
		musics.put("titlescreen", new ResourceLoader<MusicLoadable, String>("Music/titlescreen.ogg", MusicLoadable.getInstantiator()));
		musics.put("battle", new ResourceLoader<MusicLoadable, String>("Music/battle.ogg", MusicLoadable.getInstantiator()));
		musics.put("shop", new ResourceLoader<MusicLoadable, String>("Music/shop.ogg", MusicLoadable.getInstantiator()));
		musics.put("settings", new ResourceLoader<MusicLoadable, String>("Music/settings.ogg", MusicLoadable.getInstantiator()));
		musics.put("mapmonde", new ResourceLoader<MusicLoadable, String>("Music/mapmonde.ogg", MusicLoadable.getInstantiator()));
		musics.put("garage", new ResourceLoader<MusicLoadable, String>("Music/garage.ogg", MusicLoadable.getInstantiator()));
		
		// load fonts
		fonts.put("title", new ResourceLoader<FontLoadable, FontLoaderData>(new FontLoaderData("Font/spaceboy.ttf", 32, false, false), FontLoadable.getInstantiator()));
		fonts.put("dialog", new ResourceLoader<FontLoadable, FontLoaderData>(new FontLoaderData("Font/spaceboy.ttf", 14, false, false), FontLoadable.getInstantiator()));
		fonts.put("basic", new ResourceLoader<FontLoadable, FontLoaderData>(new FontLoaderData("Font/spacebit.ttf", 32, true, false), FontLoadable.getInstantiator()));
	}
	
	public ResourceLoader<ImageLoadable, String> getImage(String key) {
		return images.get(key);
	}
	
	public ResourceLoader<MusicLoadable, String> getMusic(String key) {
		return musics.get(key);
	}
	
	public ResourceLoader<SoundLoadable, String> getSound(String key) {
		return sounds.get(key);
	}
	
	public ResourceLoader<FontLoadable, FontLoaderData> getFonts(String key) {
		return fonts.get(key);
	}

}
