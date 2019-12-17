package resources.loader;

import java.util.function.Function;

import org.newdawn.slick.SlickException;

public final class ResourceLoader<RESOURCE extends ResourceLoadable, DATA_INIT> {
	private RESOURCE res;
	private DATA_INIT data;
	private Function<DATA_INIT, ResourceLoadable> instantiator;
	private int refCount = 0;

	public ResourceLoader(DATA_INIT data, Function<DATA_INIT, ResourceLoadable> instantiator) {
		// TODO Auto-generated constructor stub
		this.data = data;
		this.instantiator = instantiator;
		flush();
	}

	public RESOURCE getRes() {
		return res;
	}
	
	public DATA_INIT getData() {
		return data;
	}
	
	@SuppressWarnings("unchecked")
	public RESOURCE load() throws SlickException {
		if (refCount == 0) {
			ResourceLoadable loaded = instantiator.apply(data);
			if (loaded.hasFailed()) ((EmptyResource) loaded).throwException();
			res = (RESOURCE) loaded;
		}
		refCount++;
		return getRes();
	}
	
	public void unload() {
		refCount--;
		if (refCount == 0) res = null;
	}
	
	public void flush() {
		refCount = 0;
		res = null;
	}

}
