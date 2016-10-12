package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Folder implements Comparable<Folder>, java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Note> notes;
	private String name;
	
	public Folder(String name){
		this.name = name;
		notes = new ArrayList<Note>();
		
	}
	
	public void addNote(Note note){
		notes.add(note);
	}
	
	public String getName(){
		return this.name;
	}
	
	public ArrayList<Note> getNotes(){
		return notes;
	}

	@Override
	public String toString() {
		int nText = 0;
		int nImage = 0;
		
		for ( int i = 0; i < notes.size(); i++){
			if ( notes.get(i) instanceof TextNote ) nText++;
		}
		
		for ( int i = 0; i < notes.size(); i++){
			if ( notes.get(i) instanceof ImageNote ) nImage++;
		}
		
		return name + ":" + nText + ":" + nImage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
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
		Folder other = (Folder) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		return true;
	}


	@Override
	public int compareTo(Folder o) {
		if(this.name.compareTo(o.name) > 0) return 1;
		else if(this.name.compareTo(o.name) < 0) return -1;
		else
			return 0;
		
	}
	
	
	
	public void sortNotes(){
		Collections.sort( notes );
	}
	
	
	public List<Note> searchNotes(String keywords){
//		String[] input = keywords.toLowerCase().split(" ");
//		ArrayList<Note> result = new ArrayList<Note>();
//		for(String i : input){
//			i = i.toLowerCase();

		List<Note> list = new ArrayList<Note>();
		String[] keyword = keywords.toLowerCase().split(" ");
		for(Note n : this.notes){
			
			//Just to have the method getClass on this object
			TextNote textNote = new TextNote("tmp_to_delete");
			
			String title=n.toString().toLowerCase();
			
			int index=0;
			int addedOnce=0;
			while(index < keyword.length && addedOnce == 0 ){
				if(index +1 < keyword.length && keyword[index+1]=="or"){
					if(n.getClass()==textNote.getClass()){
						if(title.contains(keyword[index])||((TextNote) n).getContent().contains(keyword[index])||title.contains(keyword[index+2])||((TextNote) n).getContent().contains(keyword[index+2])){
							list.add(n);
							addedOnce =1;
							//System.out.println("1:"+index);
						}
					}else{
						if(title.contains(keyword[index])||title.contains(keyword[index+2])){
							list.add(n);
							addedOnce =1;
							//System.out.println("2:"+index);
						}
					}
					index=index+3;
				}else{
					//Here TextNote and ImageNote
					if(n.getClass()==textNote.getClass()){
						if(title.contains(keyword[index])||((TextNote) n).getContent().contains(keyword[index])){
							list.add(n);
							addedOnce =1;
							//System.out.println("3:"+index);
						}
					}
					index++;
				}
			}
		}
		return list;
	}
	

}
