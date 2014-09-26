/*
 * Copyright information can be located in MainActivity.java
 * 
 * This was a class made to control a singleton implementation 
 * of my ToDo Array, so that only one instance of the array was
 * created. Allows adding and deleting of todos, which corresponds 
 * to being able to archive todos. 
 * 
 */

package cs.ualberta.ca.jdemery_todo;

import java.util.ArrayList;

import android.content.Context;

public class ToDoModel {
	private static ToDoModel sToDoModel;
	private Context appContext;
	private ArrayList<ToDo> aToDo;
	private StoreToDos fileOp = new StoreToDos();
	
	private ToDoModel(Context context) {
		appContext = context;
		
		aToDo = fileOp.loadToDos(appContext);
    	if (aToDo == null) aToDo = new ArrayList<ToDo>();
	}
	
	public static ToDoModel get(Context c) {
		if (sToDoModel == null) {
			sToDoModel = new ToDoModel(c.getApplicationContext());
		}
		return sToDoModel;
	}
	
	public ArrayList<ToDo> getToDos() {
		return aToDo;
	}
	
	public ToDo getToDo(Integer id) {
		return aToDo.get(id);
	}
	
	public ArrayList<ToDo> addToDos(ToDo newToDo) {
		aToDo.add(newToDo);
		return getToDos();
	}
}
