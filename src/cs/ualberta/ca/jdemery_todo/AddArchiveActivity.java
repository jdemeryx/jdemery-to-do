/*
 * This activity allows the user to move a ToDo item from the active view
 * into the archived view, and then deletes the item from the active view.
 * Ensures that the right counters are shown, and kept up-to-date. 
 * 
 * 
 */

package cs.ualberta.ca.jdemery_todo;

import java.util.ArrayList;

import com.example.jdemery_todo.R;
import com.example.jdemery_todo.R.id;
import com.example.jdemery_todo.R.layout;
import com.example.jdemery_todo.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AddArchiveActivity extends Activity {
	//Private variables to help do the storing of information required to swap
	private ListView savedToDos;
	private ArrayList<ToDo> toDos;
	private ArrayList<ToDo> toDosA;
	private ArrayAdapter<ToDo> adapter;
	private ToDoModel mod;
	private ArchivedModel modA;

	//Initializes the main activity view, allowing me to display the required
	//Info. setUpButtons allows the onClickListeners to be initialized.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_archive);
		savedToDos = (ListView) findViewById(R.id.mainDeleteToDoList);
		setUpButtons();
	}

	//Start of the UI creation, populates holder variables for use later on.
	//Also opted to use a few more variables to cut down on long ref. calls
	@Override
	public void onStart() {
		super.onStart();
		mod = ToDoModel.get(getApplicationContext());
		toDos = mod.getToDos();
		adapter = new ArrayAdapter<ToDo>(this, R.layout.to_do_view, toDos);
		savedToDos.setAdapter(adapter);
		modA = ArchivedModel.get(getApplicationContext());
		toDosA = modA.getToDos();
	}

	/*
	 * Creates an onClickListener to track where the user is clicking on the list
	 * and contains the logic to ensure the ToDo gets properly added to the archived
	 * to-do list, and then removed from the active list. Ensures to notify other
	 * aspects of the program that the dataset has changed, and to re-pull it.
	 */
	private void setUpButtons() {
		savedToDos.setOnItemClickListener(new OnItemClickListener () {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				modA.addToDos(mod.getToDo(position));
				ToDoModel.get(getApplicationContext()).removeToDos(position);
				toDos = ToDoModel.get(getApplicationContext()).getToDos();
				adapter = new ArrayAdapter<ToDo>(AddArchiveActivity.this, R.layout.to_do_view, toDos);
				savedToDos.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}       	
		});
	}
}
