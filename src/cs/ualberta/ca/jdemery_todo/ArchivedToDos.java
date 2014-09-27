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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ArchivedToDos extends Activity {
	private ListView savedToDos;
	private ArrayList<ToDo> toDos;
	private ArrayAdapter<ToDo> adapter;
	private ArchivedModel mod2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.archived_todos);
		savedToDos = (ListView) findViewById(R.id.archivedListView);
		setUpButtons();
	}

	@Override
	public void onStart() {
		super.onStart();
		mod2 = ArchivedModel.get(getApplicationContext());
		toDos = mod2.getToDos();
		adapter = new ArrayAdapter<ToDo>(this, R.layout.to_do_view, toDos);
		savedToDos.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.archived_to_dos, menu);
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
		Button archivedReturnToDo = (Button) findViewById(R.id.archiveReturnButton);
		Button archivedEmailToDo = (Button) findViewById(R.id.archiveEmailButton);
		Button archivedDeleteToDo = (Button) findViewById(R.id.archiveDeleteButton);

		archivedReturnToDo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				Toast.makeText(ArchivedToDos.this, "Return", Toast.LENGTH_SHORT).show();
			}
		});


		archivedEmailToDo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				Toast.makeText(ArchivedToDos.this, "Email", Toast.LENGTH_SHORT).show();

			}
		});

		archivedDeleteToDo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_OK);
				Toast.makeText(ArchivedToDos.this, "Delete", Toast.LENGTH_SHORT).show();
			}
		});
		savedToDos.setOnItemClickListener(new OnItemClickListener () {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (!mod2.getToDo(position).getIsChecked()) {
					mod2.setChecked(true, position);
					mod2.getToDo(position).setMsg(mod2.getToDo(position).getMsg().replace("[   ]", "[ X ]"));
				}
				else	{
					mod2.setChecked(false, position);
					mod2.getToDo(position).setMsg(mod2.getToDo(position).getMsg().replace("[ X ]", "[   ]"));
				}
				Toast.makeText(ArchivedToDos.this, "Click position "+position +" ", Toast.LENGTH_SHORT).show();	
				adapter.notifyDataSetChanged();
				mod2.save();
			}
		});
	}
}