/*
 * Class used to store & load the data, whenever the data changes.
 * Stores/Loads both active ToDo Items, as well as Archived items.
 * Uses two different files to store this data, one for currently
 * tracked ToDos, the other for archived items.
 * 
 * Basic functionality inspired by the Lab 3 assignment, as well as
 * java doc descriptions.
 * 
 * License can be located at the top of MainActivity.java
 */

package cs.ualberta.ca.jdemery_todo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class StoreToDos {
	private static final String ACTIVETODOS = "active.sav";
	private static final String ARCHIVEDTODOS = "archive.sav";
	private FileInputStream input;
	private BufferedReader buffer;
	private FileOutputStream output;
	private OutputStreamWriter osw;
	private Gson gson;
	private ArrayList<ToDo> loadedToDos;
	
	
	
	public void saveToDos(ArrayList<ToDo> toDos, Context appContext) {
		try {
			output = appContext.openFileOutput(ACTIVETODOS, 0);
			gson = new Gson();
			osw = new OutputStreamWriter(output);
			gson.toJson(toDos, osw);
			osw.flush();
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void saveArchivedToDos(ArrayList<ToDo> toDos, Context appContext) {
		try {
			output = appContext.openFileOutput(ARCHIVEDTODOS, 0);
			gson = new Gson();
			osw = new OutputStreamWriter(output);
			gson.toJson(toDos, osw);
			osw.flush();
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ToDo> loadToDos(Context appContext) {
		try {
			input = appContext.openFileInput(ACTIVETODOS);
			buffer = new BufferedReader(new InputStreamReader(input));
			gson = new Gson();
			//Following line from: https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html - 2014-9-23
			Type listType = new TypeToken<List<ToDo>>() {}.getType();
			loadedToDos = gson.fromJson(buffer, listType);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loadedToDos;
	}
	
	public ArrayList<ToDo> loadArchivedToDos(Context appContext) {
		try {
			input = appContext.openFileInput(ARCHIVEDTODOS);
			buffer = new BufferedReader(new InputStreamReader(input));
			gson = new Gson();
			//Following line from: https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html - 2014-9-23
			Type listType = new TypeToken<List<ToDo>>() {}.getType();
			loadedToDos = gson.fromJson(buffer, listType);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loadedToDos;
	}
}
