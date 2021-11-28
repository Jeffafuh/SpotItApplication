package application.model;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.scene.image.Image;

/**
 * Subclass of an Image object for the express purpose of being able to store the file path of an image in the object itself
 * 
 * @author Jeff Dong
 * Fall 2021
 */
public class loadedImage extends Image{
	
	private String path;

	/**
	 * Class constructor, sets the path of the image and creates the Image superclass with the path
	 * 
	 * @param url File path to the image
	 * @throws IOException
	 */
	public loadedImage(String url) throws IOException{
		super(new FileInputStream(url));
		path = url;
	}

	/**
	 * Returns the file path for the image
	 * 
	 * @return String of the file path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the file path for the image
	 * 
	 * @param path File path to be set
	 */
	public void setPath(String path) {
		this.path = path;
	}

}
