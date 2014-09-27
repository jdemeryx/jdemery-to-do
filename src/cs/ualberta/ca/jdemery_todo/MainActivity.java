/*
To-Do List application: Display/create a to-do list, and allow
other functionality like emailing, archiving, deleting, etc. This is the
main view of the application, which allows you to move throughout the
application to achieve the tasks you decide to use.

Copyright (C) 2014 Jonathan Emery jdemery@ualberta.ca
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package cs.ualberta.ca.jdemery_todo;
import java.util.ArrayList;
import com.example.jdemery_todo.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends Activity {

	private EditText toDoText;
	private ListView savedToDos;
	private ArrayList<ToDo> toDos;
	private ArrayAdapter<ToDo> adapter;
	private ToDoModel mod;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		toDoText = (EditText) findViewById(R.id.addToDo);
		savedToDos = (ListView) findViewById(R.id.todoListView);
		textView = (TextView) findViewById(R.id.counters);
		setUpButtons();
	}

	//Basic onStart routine similar to all the other Activities in the proj.
	@Override
	public void onStart() {
		super.onStart();
		mod = ToDoModel.get(getApplicationContext());
		toDos = mod.getToDos();
		adapter = new ArrayAdapter<ToDo>(this, R.layout.to_do_view, toDos);
		savedToDos.setAdapter(adapter);
		updateTextCount();
	}

	//Ensure everything using the model knows if changes have occured.
	@Override
	public void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
		savedToDos.invalidateViews();
	}

	//Creates the simple menu to enable further functionality in the app.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	//Method called by onClicks on the menu, displays archived to dos.
	public void viewArchivedItems(MenuItem menu) {
		Intent intent = new Intent(MainActivity.this, ArchivedToDos.class);
		startActivity(intent);
		adapter.notifyDataSetChanged();
	}

	//Uses an intent to send an email with both files of ToDos together.
	//Populates the intents data to provide a better email template.
	public void emailALLToDos(MenuItem menu) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("vnd.android.cursor.dir/email");
		String address[] = {"jdemery@ualberta.ca"};
		intent.putExtra(Intent.EXTRA_EMAIL, address);
		intent.putExtra(Intent.EXTRA_STREAM, "active.sav");
		intent.putExtra(Intent.EXTRA_STREAM, "archive.sav");
		intent.putExtra(Intent.EXTRA_SUBJECT, "testing email");
		startActivity(Intent.createChooser(intent, "Emailing"));
	}

	//Same as above, but only for current to-dos.
	public void emailCurrentToDos(MenuItem menu) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("vnd.android.cursor.dir/email");
		String address[] = {"jdemery@ualberta.ca"};
		intent.putExtra(Intent.EXTRA_EMAIL, address);
		intent.putExtra(Intent.EXTRA_STREAM, "active.sav");
		intent.putExtra(Intent.EXTRA_SUBJECT, "testing email");
		startActivity(Intent.createChooser(intent, "Emailing"));
	}

	//This one is for archived todos only.
	public void emailArchivedToDos(MenuItem menu) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("vnd.android.cursor.dir/email");
		String address[] = {"jdemery@ualberta.ca"};
		intent.putExtra(Intent.EXTRA_EMAIL, address);
		intent.putExtra(Intent.EXTRA_STREAM, "archive.sav");
		intent.putExtra(Intent.EXTRA_SUBJECT, "testing email");
		startActivity(Intent.createChooser(intent, "Emailing"));
	}

	//Calls the Activity to archive current to dos, updates the view
	public void addArchiveItems() {
		Intent intent = new Intent(MainActivity.this, AddArchiveActivity.class);
		startActivity(intent);
		adapter.notifyDataSetChanged();
		savedToDos.invalidateViews();
	}

	//Same, but for deleting.
	public void deleteToDoItems() {
		Intent intent = new Intent(MainActivity.this, DeleteToDoActivity.class);
		startActivity(intent);
		adapter.notifyDataSetChanged();
	}

	//Skeleton for emailing a selection of current to dos
	public void emailToDoItems() {
		adapter.notifyDataSetChanged();
	}

	//Keeps the text count uptodate everytime a click occurs
	private void updateTextCount() {
		int numChecked = mod.getCountChecked();
		int numTotal = mod.getCountTotal();
		int numUnChecked = numTotal-numChecked;

		textView.setText("Checked: "+numChecked+" | UnChecked: "+numUnChecked+" | Total: "+numTotal);
	}

	//Listeners to invoke the above methods on listClick/ButtonClick
	private void setUpButtons() {
		Button addToDo = (Button) findViewById(R.id.addButton);
		Button archiveToDo = (Button) findViewById(R.id.archiveButton);
		Button emailToDo = (Button) findViewById(R.id.emailButton);
		Button deleteToDo = (Button) findViewById(R.id.deleteButton);
		addToDo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				String text = toDoText.getText().toString();
				toDos = mod.addToDos(new ToDo("[   ] "+text));
				adapter.notifyDataSetChanged();
				updateTextCount();
			}
		});
		archiveToDo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				addArchiveItems();
			}
		});
		emailToDo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
			}
		});
		deleteToDo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				deleteToDoItems();
			}
		});
		savedToDos.setOnItemClickListener(new OnItemClickListener () {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (!mod.getToDo(position).getIsChecked()) {
					mod.setChecked(true, position);
					mod.getToDo(position).setMsg(mod.getToDo(position).getMsg().replace("[   ]", "[ X ]"));
				}
				else	{
					mod.setChecked(false, position);
					mod.getToDo(position).setMsg(mod.getToDo(position).getMsg().replace("[ X ]", "[   ]"));
				}
				adapter.notifyDataSetChanged();
				mod.save();
				updateTextCount();
			}       	
		});
	}
}