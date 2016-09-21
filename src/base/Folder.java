package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Folder implements Comparable<Folder>{
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
		String[] input = keywords.toLowerCase().split(" ");
		ArrayList<Note> result = new ArrayList<Note>();
		for(String i : input){
			i = i.toLowerCase();
			
		}
		
		for ( Note i : notes){
			if (i.searchMulti(input) ){
				result.add(i);
			}
		}
		
		return result;
	}
	

}
