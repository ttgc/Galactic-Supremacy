package resources.loader;

public class FontLoaderData {

	public String path;
	public int size;
	public boolean bold;
	public boolean italic;
	
	public FontLoaderData(String path, int size, boolean bold, boolean italic) {
		this.path = path;
		this.size = size;
		this.bold = bold;
		this.italic = italic;
	}

}
