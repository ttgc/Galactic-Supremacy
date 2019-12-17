package resources.loader;

import java.awt.Font;
import java.util.function.Function;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.HieroSettings;
import org.newdawn.slick.font.effects.ColorEffect;

public class FontLoadable extends UnicodeFont implements ResourceLoadable {
	public FontLoadable(Font font) {
		super(font);
		// TODO Auto-generated constructor stub
	}

	public FontLoadable(String ttfFileRef, String hieroFileRef) throws SlickException {
		super(ttfFileRef, hieroFileRef);
		// TODO Auto-generated constructor stub
	}

	public FontLoadable(String ttfFileRef, HieroSettings settings) throws SlickException {
		super(ttfFileRef, settings);
		// TODO Auto-generated constructor stub
	}

	public FontLoadable(Font font, String hieroFileRef) throws SlickException {
		super(font, hieroFileRef);
		// TODO Auto-generated constructor stub
	}

	public FontLoadable(Font font, HieroSettings settings) {
		super(font, settings);
		// TODO Auto-generated constructor stub
	}

	public FontLoadable(String ttfFileRef, int size, boolean bold, boolean italic) throws SlickException {
		super(ttfFileRef, size, bold, italic);
		// TODO Auto-generated constructor stub
	}

	public FontLoadable(Font font, int size, boolean bold, boolean italic) {
		super(font, size, bold, italic);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public static Function<FontLoaderData, ResourceLoadable> getInstantiator() {
		// TODO Auto-generated method stub
		return (FontLoaderData data) -> {
			try {
				FontLoadable font = new FontLoadable(data.path, data.size, data.bold, data.italic);
				font.addAsciiGlyphs();
				font.getEffects().add(new ColorEffect());
				font.loadGlyphs();
				return font;
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				return new EmptyResource(e);
			}
		};
	}

	@Override
	public boolean hasFailed() {
		// TODO Auto-generated method stub
		return false;
	}

}
