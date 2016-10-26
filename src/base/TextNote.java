package base;

import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

public class TextNote extends Note implements java.io.Serializable{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private String content;
	
	public TextNote(String title){
		super(title);
	}
	
	public TextNote( String title, String content){
		super(title);
		this.content = content;
	}
	
	
	
	/**
	* load a TextNote from File f
	*
	* the tile of the TextNote is the name of the file
	* the content of the TextNote is the content of the file
	*
	* @param File f
	*/
	public TextNote(File f) {
	super(f.getName());
	try{
	this.content = getTextFromFile(f.getAbsolutePath());}
	catch(FileNotFoundException ex){
		System.out.println("File not Found!");
	}
	}
	
	
	
	/**
	* get the content of a file
	*
	* @param absolutePath of the file
	* @return the content of the file
	 * @throws FileNotFoundException 
	*/
	private String getTextFromFile(String absolutePath) throws FileNotFoundException {
		String result = "";
		// TODO
	
		Scanner input = new Scanner(new File(absolutePath));
		while (input.hasNext())
			result += input.next() + " ";
		input.close(); // important
	
		// input from console
		//	Scanner console = new Scanner(System.in);
		//	console.close();
		return result;
	}
	
	
	
	/**
	* export text note to file
	*
	*
	* @param pathFolder path of the folder where to export the note
	* the file has to be named as the title of the note with extension ".txt"
	*
	* if the tile contains white spaces " " they has to be replaced with underscores "_"
	 * @throws FileNotFoundException 
	*
	*
	*/
	public void exportTextToFile(String pathFolder) throws FileNotFoundException{
		//TODO
		File file = new File( pathFolder + super.getTitle().replaceAll(" ", "_") + ".txt");
		// TODO
		if (file.exists()) {
			System.out.println("File exists");
			System.exit(0); }
			PrintWriter output = new PrintWriter(file);
			output.print(content);
			output.close(); // important
		}
	
	
	
	
	
	public String getContent(){
		return this.content;
	}
	
	
	public void setContent(String content){
		this.content = content;
	}
	
	
	
	public Character countLetters(){
		HashMap<Character,Integer> count = new HashMap<Character,Integer>();
		String a = this.getTitle() + this.getContent();
		int b = 0;
		Character r = ' ';
		for (int i = 0; i < a.length(); i++) {
			Character c = a.charAt(i);
			if (c <= 'Z' && c >= 'A' || c <= 'z' && c >= 'a') {
				if (!count.containsKey(c)) {
					count.put(c, 1);
				} else {
					count.put(c, count.get(c) + 1);
					if (count.get(c) > b) {
						b = count.get(c);
						r = c;
					}
				}
			}
		}
		return r;
	}
	
}
