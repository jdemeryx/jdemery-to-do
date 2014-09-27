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
	private Integer checkedBoxes = 0;
	private Integer selectedToDos = 0;
	private Integer totalToDos = 0;
	
	private ToDoModel(Context context) {
		appContext = context;
		
		aToDo = fileOp.loadToDos(appContext);
    	if (aToDo == null) aToDo = new ArrayList<ToDo>();
    	countChecked();
    	totalToDos = aToDo.size();
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
		totalToDos++;
		save();
		return getToDos();
	}
	
	public ArrayList<ToDo> removeToDos(Integer i) {
		if (aToDo.get(i).getIsChecked())
			checkedBoxes--;
		if (aToDo.get(i).getIsSelected())
			selectedToDos--;
		aToDo.remove(aToDo.get(i));
		totalToDos--;
		save();
		return getToDos();
	}
	
	public void setChecked(Boolean bool, Integer i) {
		aToDo.get(i).isChecked(bool);
		if (bool)
			checkedBoxes++;
		if (!bool)
			checkedBoxes--;
	}
	
	public void setSelected(Boolean bool, Integer i) {
		aToDo.get(i).isSelected(bool);
		if (bool)
			selectedToDos++;
		if (!bool)
			selectedToDos--;
	}
	
	private void countChecked() {
		for (int i = 0; i < aToDo.size(); i++) {
			if (aToDo.get(i).getIsChecked())
				checkedBoxes++;
			if (aToDo.get(i).getIsSelected())
				selectedToDos++;
		}
	}
	
	public Integer getCountChecked() {
		return checkedBoxes;
	}
	
	public Integer getCountSelected() {
		return selectedToDos;
	}
	
	public Integer getCountTotal() {
		return totalToDos;
	}
	
	public void save() {
		fileOp.saveToDos(aToDo, appContext);
	}
}
