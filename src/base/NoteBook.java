package base;

//import java.io.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteBook implements java.io.Serializable{
	private ArrayList<Folder> folders;
	
	private static final long serialVersionUID = 1L;
	
	public NoteBook(){
		super();
		folders = new ArrayList<Folder>();
	}
	
	public boolean createTextNote(String folderName, String title){
		TextNote note = new TextNote(title);
		return insertNote(folderName,note);
	}
	
	public boolean createImageNote(String folderName, String title){
		ImageNote note = new ImageNote(title);
		return insertNote(folderName,note);
	}
	
	//@Overload
	public boolean createTextNote(String folderName, String title, String content){
		TextNote note = new TextNote(title, content);
		return insertNote(folderName, note);
	}
	
	public ArrayList<Folder> getFolders(){
		return folders;
	}
	
	public boolean insertNote(String folderName, Note note)
	{
		
		Folder f = null;
		for (Folder f1 : folders) {
			if (f1.getName().equals(folderName)) {
				f = f1;
			}
		}
		if (f == null) {
			f  = new Folder(folderName);
			folders.add(f);
		}
		for (Note n : f.getNotes()) {
			if (n.getTitle().equals(note.getTitle())) {
				System.out.println("Creating note " + n.getTitle() + " under folder " + folderName + " failed");
				return false;
			}
		}
				f.addNote(note);
		return true;
		
	}
	
	public void sortFolders(){
		for(Folder fi : folders ){
			fi.sortNotes();
		}
		
		Collections.sort(folders);
	}
	
	public List<Note> searchNotes(String keywords){
		ArrayList<Note> result = new ArrayList<Note>();
		for( Folder f : folders){
			result.addAll(f.searchNotes(keywords));
		}
		
		return result;
	}
	
	
	
	public boolean save(String file){
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
		fos = new FileOutputStream(file);
		out = new ObjectOutputStream(fos);
		out.writeObject(this);
		out.close();

		} catch (Exception e) {
		return false;
		}
		return true;
		}
	
	public NoteBook(String file){
		// TODO
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
		fis = new FileInputStream(file);
		in = new ObjectInputStream(fis);
		NoteBook n = (NoteBook) in.readObject();
		this.folders=n.folders;
		in.close();
		} catch (Exception e) {
		e.printStackTrace();
		}
		// TODO
		}
	
	
	
	
}
