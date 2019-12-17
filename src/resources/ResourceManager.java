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
		
		// load images
		images.put("life", new ResourceLoader<ImageLoadable, String>("Pictures/life.png", ImageLoadable.getInstantiator()));
		images.put("laser", new ResourceLoader<ImageLoadable, String>("Pictures/laser.png", ImageLoadable.getInstantiator()));
		images.put("wall", new ResourceLoader<ImageLoadable, String>("Pictures/wall.png", ImageLoadable.getInstantiator()));
		images.put("power-ray", new ResourceLoader<ImageLoadable, String>("Pictures/raypower.png", ImageLoadable.getInstantiator()));
		images.put("asteroid", new ResourceLoader<ImageLoadable, String>("Pictures/asteroid.png", ImageLoadable.getInstantiator()));
		images.put("blackhole-opened", new ResourceLoader<ImageLoadable, String>("Pictures/blackhole.png", ImageLoadable.getInstantiator()));
		images.put("blackhole-closed", new ResourceLoader<ImageLoadable, String>("Pictures/blackhole-closed.png", ImageLoadable.getInstantiator()));
		images.put("ship", new ResourceLoader<ImageLoadable, String>("Pictures/ship.png", ImageLoadable.getInstantiator()));
		images.put("rocket", new ResourceLoader<ImageLoadable, String>("Pictures/rocket.png", ImageLoadable.getInstantiator()));
		images.put("canon-basic", new ResourceLoader<ImageLoadable, String>("Pictures/canon.png", ImageLoadable.getInstantiator()));
		images.put("canon-double", new ResourceLoader<ImageLoadable, String>("Pictures/double_canon.png", ImageLoadable.getInstantiator()));
		images.put("canon-triple", new ResourceLoader<ImageLoadable, String>("Pictures/triple_canon.png", ImageLoadable.getInstantiator()));
		images.put("canon-quintuple", new ResourceLoader<ImageLoadable, String>("Pictures/quintuple_canon.png", ImageLoadable.getInstantiator()));
		images.put("shield", new ResourceLoader<ImageLoadable, String>("Pictures/shield.png", ImageLoadable.getInstantiator()));
		images.put("starcup", new ResourceLoader<ImageLoadable, String>("Pictures/starcup.png", ImageLoadable.getInstantiator()));
		images.put("starball", new ResourceLoader<ImageLoadable, String>("Pictures/starball.png", ImageLoadable.getInstantiator()));
		images.put("boss-starcup", new ResourceLoader<ImageLoadable, String>("Pictures/starcupBoss.png", ImageLoadable.getInstantiator()));
		images.put("starroll", new ResourceLoader<ImageLoadable, String>("Pictures/starroll.png", ImageLoadable.getInstantiator()));
		images.put("starshooter", new ResourceLoader<ImageLoadable, String>("Pictures/starshooter.png", ImageLoadable.getInstantiator()));
		images.put("boss-starshooter", new ResourceLoader<ImageLoadable, String>("Pictures/starshooter_boss.png", ImageLoadable.getInstantiator()));
		images.put("powerup-burn", new ResourceLoader<ImageLoadable, String>("Pictures/burn.png", ImageLoadable.getInstantiator()));
		images.put("powerup-cdup", new ResourceLoader<ImageLoadable, String>("Pictures/cd-up.png", ImageLoadable.getInstantiator()));
		images.put("powerup-energyup", new ResourceLoader<ImageLoadable, String>("Pictures/energy-up.png", ImageLoadable.getInstantiator()));
		images.put("powerup-health", new ResourceLoader<ImageLoadable, String>("Pictures/health.png", ImageLoadable.getInstantiator()));
		images.put("powerup-heatdown", new ResourceLoader<ImageLoadable, String>("Pictures/heat-down.png", ImageLoadable.getInstantiator()));
		images.put("powerup-money", new ResourceLoader<ImageLoadable, String>("Pictures/money.png", ImageLoadable.getInstantiator()));
		images.put("powerup-power", new ResourceLoader<ImageLoadable, String>("Pictures/poweritem.png", ImageLoadable.getInstantiator()));
		images.put("powerup-repair", new ResourceLoader<ImageLoadable, String>("Pictures/repair.png", ImageLoadable.getInstantiator()));
		images.put("powerup-cddown", new ResourceLoader<ImageLoadable, String>("Pictures/cd-down.png", ImageLoadable.getInstantiator()));
		images.put("powerup-energydown", new ResourceLoader<ImageLoadable, String>("Pictures/energy-down.png", ImageLoadable.getInstantiator()));
		images.put("powerup-heatup", new ResourceLoader<ImageLoadable, String>("Pictures/heat-up.png", ImageLoadable.getInstantiator()));
		
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
