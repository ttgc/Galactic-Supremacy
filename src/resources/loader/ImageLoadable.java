package resources.loader;

import java.io.InputStream;
import java.util.function.Function;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.Texture;

public class ImageLoadable extends Image implements ResourceLoadable {

	public ImageLoadable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImageLoadable(Image other) {
		super(other);
		// TODO Auto-generated constructor stub
	}

	public ImageLoadable(ImageData arg0, int arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ImageLoadable(ImageData data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	public ImageLoadable(InputStream in, String ref, boolean flipped, int filter) throws SlickException {
		super(in, ref, flipped, filter);
		// TODO Auto-generated constructor stub
	}

	public ImageLoadable(InputStream in, String ref, boolean flipped) throws SlickException {
		super(in, ref, flipped);
		// TODO Auto-generated constructor stub
	}

	public ImageLoadable(int arg0, int arg1, int arg2) throws SlickException {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}

	public ImageLoadable(int width, int height) throws SlickException {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	public ImageLoadable(String arg0, boolean arg1, int arg2, Color arg3) throws SlickException {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public ImageLoadable(String ref, boolean flipped, int filter) throws SlickException {
		super(ref, flipped, filter);
		// TODO Auto-generated constructor stub
	}

	public ImageLoadable(String ref, boolean flipped) throws SlickException {
		super(ref, flipped);
		// TODO Auto-generated constructor stub
	}

	public ImageLoadable(String ref, Color trans) throws SlickException {
		super(ref, trans);
		// TODO Auto-generated constructor stub
	}

	public ImageLoadable(String ref) throws SlickException {
		super(ref);
		// TODO Auto-generated constructor stub
	}

	public ImageLoadable(Texture texture) {
		super(texture);
		// TODO Auto-generated constructor stub
	}
	
	public static Function<String, ResourceLoadable> getInstantiator() {
		// TODO Auto-generated method stub
		return (String path) -> {
			try {
				return new ImageLoadable(path);
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
