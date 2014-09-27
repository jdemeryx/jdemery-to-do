/*
 * Simple activity that allows the deletion of archived to do items
 * Mirrors DeleteToDoActivity
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

public class DeleteArchiveActivity extends Activity {
	private ListView savedToDos;
	private ArrayList<ToDo> toDos;
	private ArrayAdapter<ToDo> adapter;
	private ArchivedModel mod;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_archive);
		savedToDos = (ListView) findViewById(R.id.deleteArchivedToDo);
		setUpButtons();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		mod = ArchivedModel.get(getApplicationContext());
		toDos = mod.getToDos();
		adapter = new ArrayAdapter<ToDo>(this, R.layout.to_do_view, toDos);
		savedToDos.setAdapter(adapter);
	}
	
	private void setUpButtons() {
		savedToDos.setOnItemClickListener(new OnItemClickListener () {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				ArchivedModel.get(getApplicationContext()).removeToDos(position);
				toDos = ArchivedModel.get(getApplicationContext()).getToDos();
				adapter = new ArrayAdapter<ToDo>(DeleteArchiveActivity.this, R.layout.to_do_view, toDos);
				savedToDos.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}       	
		});	
	}
}
