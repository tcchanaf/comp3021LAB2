package base;

import java.util.ArrayList;

public class NoteBook {
	private ArrayList<Folder> folders;
	
	public NoteBook(){
		folders = new ArrayList<Folder>();
	}
	
	public boolean creatTextNote(String folderName, String title){
		TextNote note = new TextNote(title);
		return insertNote(folderName,note);
	}
	
	public boolean creatImageNote(String folderName, String title){
		ImageNote note = new ImageNote(title);
		return insertNote(folderName,note);
	}
	
	public ArrayList<Folder> getFolder(){
		return folders;
	}
	
	public boolean insertNote(String folderName, Note note)
	{
		boolean checkExist = false;
		for (int i = 0; i < folders.size(); i++)
		{
			if (folders.get(i).getName() == folderName) checkExist = true;
		}
		
		if(!checkExist)	folders.add(new Folder(folderName));
		
	}
}
