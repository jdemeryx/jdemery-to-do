/*
 * Copyright information can be located in MainActivity.java
 * 
 * This was a class made to control a singleton implementation 
 * of my ToDo Array, so that only one instance of the array was
 * created. Allows adding and deleting of todos, which corresponds 
 * to being able to archive todos. Handles a lot of the logic of 
 * the model. Similar implementation to ToDoModel, but required
 * differences due to the types of Models created. Allows for
 * a second single-ton, to be used by the application. 
 * 
 * Hides information from other classes, requiring them to invoke
 * pre-defined methods so that only this class can perform changes
 * to the data in the model.
 */

package cs.ualberta.ca.jdemery_todo;

import java.util.ArrayList;

import android.content.Context;

public class ArchivedModel {
	// Main aspect that creates this into a singleton.
	private static ArchivedModel aToDoModel;		
	private Context appContext;
	private ArrayList<ToDo> aToDo;
	private StoreToDos fileOp = new StoreToDos();
	private Integer checkedBoxes = 0;
	private Integer selectedToDos= 0;
	private Integer totalToDos = 0;

	//Constructor to initialize some of the counts, loads archived ToDos for other
	//parts of the program to utilize.
	private ArchivedModel(Context context) {
		appContext = context;

		//Calls a class to import the saved archives.
		aToDo = fileOp.loadArchivedToDos(appContext);
		if (aToDo == null) aToDo = new ArrayList<ToDo>();
		countChecked();
		totalToDos = aToDo.size();
		save();
	}

	//Returns the singleton aspect of this class, specific to an ApplicationContext
	public static ArchivedModel get(Context c) {
		if (aToDoModel == null) {
			aToDoModel = new ArchivedModel(c.getApplicationContext());
		}
		return aToDoModel;
	}

	//Allows external methods to get a list of the ToDos.
	public ArrayList<ToDo> getToDos() {
		return aToDo;
	}

	//Returns a specific element of the ToDo List
	public ToDo getToDo(Integer id) {
		return aToDo.get(id);
	}

	//Allows external methods to add new to dos into the model, without
	//granting explicit access to internal data. Also increases the counter
	public ArrayList<ToDo> addToDos(ToDo newToDo) {
		aToDo.add(newToDo);
		totalToDos++;
		save();
		return getToDos();
	}

	//Removes a to do, and does checks to ensure the counter stays accurate when deleting 
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

	//Sets a simple boolean used to make decisions 
	public void setChecked(Boolean bool, Integer i) {
		aToDo.get(i).isChecked(bool);
		if (bool)
			checkedBoxes++;
		if (!bool)
			checkedBoxes--;
	}

	//Same as above
	public void setSelected(Boolean bool, Integer i) {
		aToDo.get(i).isSelected(bool);
		if (bool)
			selectedToDos++;
		if (!bool)
			selectedToDos--;
	}

	//Counts how many elements in the list are checked off
	private void countChecked() {
		for (int i = 0; i < aToDo.size(); i++) {
			if (aToDo.get(i).getIsChecked())
				checkedBoxes++;
			if (aToDo.get(i).getIsSelected())
				selectedToDos++;
		}
	}

	//Allows external methods to get the internal counts
	public Integer getCountChecked() {
		return checkedBoxes;
	}

	//Allows external methods to get the internal counts
	public Integer getCountSelected() {
		return selectedToDos;
	}

	//Allows external methods to get the internal counts
	public Integer getCountTotal() {
		return totalToDos;
	}

	//Saves the data incase of a crash/for the future. Called anytime the data changes
	public void save() {
		fileOp.saveArchivedToDos(aToDo, appContext);
	}
}
