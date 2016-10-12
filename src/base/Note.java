package base;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Note implements Comparable<Note>, java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date date;
	private String title;;
	
	public Note(String title){
		this.title = title;
		date = new Date(System.currentTimeMillis());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Note other = (Note) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public String getTitle(){
		return title;
	}

	@Override
	public int compareTo(Note o) {
		if(this.date.before(o.date)) return 1;
		else if(this.date.after(o.date)) return -1;
		else
			return 0;
	}
	
	
	public String getContent() {
		return null;
	}

	
	public boolean search(String keyword){
		if(this instanceof TextNote) return ( title.toLowerCase().contains(keyword) | this.getContent().toLowerCase().contains(keyword) );
		else if(this instanceof ImageNote) return title.toLowerCase().contains(keyword);
		
		return false;
	}

	
	public boolean searchMulti(String[] keywords){
		ArrayList<Boolean> status = new ArrayList<Boolean>(Collections.nCopies(keywords.length, false));
		
		for( int i = 0; i < keywords.length; i++ ){
			//System.out.println(i);
			if (keywords[i].equals("or")){
				
				status.set(i,this.search(keywords[i-1]) | this.search(keywords[i+1]) | status.get(i-1));
				//System.out.println(this.search(keywords[i-1]));
				
				status.set(i-1,this.search(keywords[i-1]) | this.search(keywords[i+1]) | status.get(i-1));
				status.set(i+1,this.search(keywords[i-1]) | this.search(keywords[i+1]) | status.get(i-1));
				i+=2;
			}
			
		}
		boolean finStatus = true;
		for(boolean b : status ){
			finStatus = b & finStatus;
			
		}
		
		return finStatus;
		
	}
	
	
	
	public boolean contains(String key){
		return title.toLowerCase().contains(key.toLowerCase());
	}
	
	public boolean containsOr(List<String> keys) {
		for (int i = 0; i < keys.size(); i++){
			if (contains(keys.get(i)))
				return true;
		}
		return false;
	}
	
	
	
	
	
	public String toString(){
		return date.toString() + "\t" + title;
	}

}
