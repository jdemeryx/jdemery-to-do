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
	private ListView savedToDos;
	private ArrayList<ToDo> toDos;
	private ArrayList<ToDo> toDosA;
	private ArrayAdapter<ToDo> adapter;
	private ToDoModel mod;
	private ArchivedModel modA;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_archive);
		savedToDos = (ListView) findViewById(R.id.archiveAddToDoList);
		setUpButtons();
	}
	
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_archive, menu);
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
	
	private void setUpButtons() {
		savedToDos.setOnItemClickListener(new OnItemClickListener () {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
	
				modA.addToDos(mod.getToDo(position));
				ToDoModel.get(getApplicationContext()).removeToDos(position);
				Toast.makeText(AddArchiveActivity.this, "Click position "+position +" ", Toast.LENGTH_SHORT).show();	
				savedToDos.invalidateViews();
				adapter.notifyDataSetChanged();
				ToDoModel.get(getApplicationContext()).save();
				ArchivedModel.get(getApplicationContext()).save();
			}       	
        });
	}
}
