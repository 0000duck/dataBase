package structure;

import java.io.File;
import java.util.ArrayList;

public class GroupInfo {

	private String Id, Header, Description, Version, Keywords, Autor, Type, Path;
	private ArrayList<File> arraylist;

	public GroupInfo() {
		arraylist = new ArrayList<File>();
	}

	// getter methods

	public ArrayList<File> getArraylist() {
		return arraylist;
	}

	public String getType() {
		return Type;
	}

	public String getAutor() {
		return Autor;
	}

	public String getHeader() {
		return Header;
	}

	public String getDescription() {
		return Description;
	}

	public String getPath() {
		return Path;
	}

	public String getVersion() {
		return Version;
	}

	public String getKeyWords() {
		return Keywords;
	}

	public String getId() {
		return Id;
	}

	// setter methods

	public void setFileListElement(File file) {
		arraylist.add(file);
	}

	public void setId(String id) {
		this.Id = id;
	}

	public void setType(String type) {
		Type = type;
	}

	public void setAutor(String autor) {
		Autor = autor;
	}

	public void setHeader(String Header) {
		this.Header = Header;
	}

	public void setDescription(String Description) {
		this.Description = Description;
	}

	public void setKeywords(String Keywords) {
		this.Keywords = Keywords;
	}

	public void setPath(String path) {
		Path = path;
	}

	public void setVersion(String Version) {
		this.Version = Version;
	}

}
