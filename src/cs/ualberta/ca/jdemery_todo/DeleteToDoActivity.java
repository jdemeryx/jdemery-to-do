/* 
 * Very basic activity to delete active ToDo items permanently.
 * Same as DeleteArchiveActivity, just with the different models
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DeleteToDoActivity extends Activity {

	private ListView savedToDos;
	private ArrayList<ToDo> toDos;
	private ArrayAdapter<ToDo> adapter;
	private ToDoModel mod;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_to_do);
		savedToDos = (ListView) findViewById(R.id.mainDeleteToDoList);
		setUpButtons();
	}

	@Override
	public void onStart() {
		super.onStart();
		mod = ToDoModel.get(getApplicationContext());
		toDos = mod.getToDos();
		adapter = new ArrayAdapter<ToDo>(this, R.layout.to_do_view, toDos);
		savedToDos.setAdapter(adapter);
	}

	private void setUpButtons() {
		savedToDos.setOnItemClickListener(new OnItemClickListener () {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				//Uses variables to hold references easier, to help cut down on explicit
				//function calls, and neaten up the code.
				ToDoModel.get(getApplicationContext()).removeToDos(position);
				toDos = ToDoModel.get(getApplicationContext()).getToDos();
				adapter = new ArrayAdapter<ToDo>(DeleteToDoActivity.this, R.layout.to_do_view, toDos);
				savedToDos.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}       	
		});

	}
}
