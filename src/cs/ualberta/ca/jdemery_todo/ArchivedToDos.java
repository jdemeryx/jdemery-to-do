/*
 * Class used to show archived tweets, and provides options for the user to
 * remove/delete/email to-do items.
 * 
 * Mirrors the layout of MainActivity, and resuses some of the same code.
 * 
 * License can be located at the top of MainActivity.java
 */

package cs.ualberta.ca.jdemery_todo;

import java.util.ArrayList;
import com.example.jdemery_todo.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ArchivedToDos extends Activity {
	private ListView savedToDos;
	private ArrayList<ToDo> toDos;
	private ArrayAdapter<ToDo> adapter;
	private ArchivedModel mod2;
	private TextView textView;

	//Generic init of the Activity, and locates/assigns the elements to vars
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.archived_todos);
		savedToDos = (ListView) findViewById(R.id.archivedListView);
		textView = (TextView) findViewById(R.id.archivedCounter);
		setUpButtons();
	}

	//Starts the main views and sets the adapters to listen for changes
	@Override
	public void onStart() {
		super.onStart();
		mod2 = ArchivedModel.get(getApplicationContext());
		toDos = mod2.getToDos();
		adapter = new ArrayAdapter<ToDo>(this, R.layout.to_do_view, toDos);
		savedToDos.setAdapter(adapter);
		updateTextCount();
	}

	//Keeps the text count of the current activity up to date. Called on each click
	private void updateTextCount() {
		int numChecked = mod2.getCountChecked();
		int numTotal = mod2.getCountTotal();
		int numUnChecked = numTotal-numChecked;

		textView.setText("Checked: "+numChecked+" | UnChecked: "+numUnChecked+" | Total: "+numTotal);
	}

	//Method to call a new Activity to return todos back to being active
	public void returnToDos() {
		Intent intent = new Intent(ArchivedToDos.this, UnArchiveActivity.class);
		startActivity(intent);
		adapter.notifyDataSetChanged();
		savedToDos.invalidateViews();
	}

	//Same as above, just to delete instead.
	public void deleteToDos() {
		Intent intent = new Intent(ArchivedToDos.this, DeleteArchiveActivity.class);
		startActivity(intent);
		adapter.notifyDataSetChanged();
		savedToDos.invalidateViews();
	}

	//sets up basic listeners for the user interface, and performs actions accordingly
	private void setUpButtons() {
		Button archivedReturnToDo = (Button) findViewById(R.id.archiveReturnButton);
		Button archivedEmailToDo = (Button) findViewById(R.id.archiveEmailButton);
		Button archivedDeleteToDo = (Button) findViewById(R.id.archiveDeleteButton);

		//Button to initiate returning of ToDos
		archivedReturnToDo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				returnToDos();
			}
		});

		//Skeleton for selecting multiple ToDos to email
		archivedEmailToDo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
			}
		});

		//Same as returns above
		archivedDeleteToDo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				deleteToDos();
			}
		});
		//Allows us to track clicks on the listview, and toggles the checkbox + attributes
		savedToDos.setOnItemClickListener(new OnItemClickListener () {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				//Round about way to get around not being able to get checkboxes to work
				//Sets the checked variable in the model, and updates the UI appearance
				if (!mod2.getToDo(position).getIsChecked()) {
					mod2.setChecked(true, position);
					mod2.getToDo(position).setMsg(mod2.getToDo(position).getMsg().replace("[   ]", "[ X ]"));
				}
				else	{
					mod2.setChecked(false, position);
					mod2.getToDo(position).setMsg(mod2.getToDo(position).getMsg().replace("[ X ]", "[   ]"));
				}
				adapter.notifyDataSetChanged();
				mod2.save();
				updateTextCount();
			}
		});
	}
}