package structure;

public class FileBase {
	private String  Id, Path, Type;
	 
	//getter methods
	public String getId(){
		return Id;
	}
	
	public String getPath(){
		return Path;
	}
	
	
	public String getType() {
		return Type;
	}
	
	//setter methods
	public void setId( String id){
		 this.Id = id;
	}
	
	public void setPath( String Path){
		 this.Path = Path;
	}

	public void setType(String type) {
		Type = type;
	}
	
}
