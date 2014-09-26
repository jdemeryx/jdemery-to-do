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
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
public class MainActivity extends Activity {
	private EditText toDoText;
	private ListView savedToDos;
	private ArrayList<ToDo> toDos;
	private ArrayAdapter<ToDo> adapter;
	private StoreToDos fileOp = new StoreToDos();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		toDoText = (EditText) findViewById(R.id.addToDo);
		savedToDos = (ListView) findViewById(R.id.todoListView);
		setUpButtons();
	}
	@Override
	public void onStart() {
		super.onStart();
		//toDos = fileOp.loadToDos(getApplicationContext());
		//if (toDos == null) toDos = new ArrayList<ToDo>();
		toDos = ToDoModel.get(getApplicationContext()).getToDos();
		adapter = new ArrayAdapter<ToDo>(this, R.layout.to_do_view, toDos);
		savedToDos.setAdapter(adapter);
	}
	@Override
	public void onResume() {
		super.onResume();
	}
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
	public void viewArchivedItems(MenuItem menu) {
		Toast.makeText(this, "View Archived Items", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(MainActivity.this, ArchivedToDos.class);
		startActivity(intent);
	}
	private void setUpButtons() {
		Button addToDo = (Button) findViewById(R.id.addButton);
		Button archiveToDo = (Button) findViewById(R.id.archiveButton);
		Button emailToDo = (Button) findViewById(R.id.emailButton);
		Button deleteToDo = (Button) findViewById(R.id.deleteButton);
		addToDo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				String text = toDoText.getText().toString();
				//toDos.add(new ToDo(text));
				toDos = ToDoModel.get(getApplicationContext()).addToDos(new ToDo(text));
				adapter.notifyDataSetChanged();
				fileOp.saveToDos(toDos, getApplicationContext());
			}
		});
		archiveToDo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				Toast.makeText(MainActivity.this, "Archive", Toast.LENGTH_SHORT).show();
			}
		});
		emailToDo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				Toast.makeText(MainActivity.this, "Email", Toast.LENGTH_SHORT).show();
			}
		});
		deleteToDo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				Toast.makeText(MainActivity.this, "Delete", Toast.LENGTH_SHORT).show();
			}
		});
        savedToDos.setOnItemClickListener(new OnItemClickListener () {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(MainActivity.this, "Click position "+position, Toast.LENGTH_SHORT).show();
				
			}
        	
        });
	}
}