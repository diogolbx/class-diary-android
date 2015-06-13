package cc.classdiary;

import java.util.ArrayList;
import java.util.List;

import cc.classdiary.adapter.SyncAdapter;
import cc.classdiary.db.DatabaseManager;
import cc.classdiary.model.Student;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ClassActivity extends ListActivity {

	private TextView text;
	private List<String> listValues;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class);

		text = (TextView) findViewById(R.id.mainText);
		syncDisciplineClass();
		ArrayAdapter<String> myAdapter = new ArrayAdapter<String>
										(this,R.layout.row_layout, R.id.listText, listValues);
		setListAdapter(myAdapter);
	}

	private void syncDisciplineClass() {
		listValues = new ArrayList<String>();
		SyncDisciplineClassTask syncTask = new SyncDisciplineClassTask(getApplicationContext());
		syncTask.execute();
		// select da disciplina disponivel baseado na tabela alunoturma
		listValues.add("Persistência e Armazenamento");
	}

	@Override
	protected void onListItemClick(ListView list, View view, int position, long id) {
		super.onListItemClick(list, view, position, id);

		// select disciplinas para fazer check-in
		new Handler().postDelayed(new Runnable() {
            public void run() {
            	// populate extra data getting from database(table student - discipline id)
                    Intent frequencyActivity = new Intent(ClassActivity.this, FrequencyActivity.class);
                    frequencyActivity.putExtra("student", "5");
                    frequencyActivity.putExtra("discipline", "1");
					startActivity(frequencyActivity);
                    finishActivity(1);
            }
    },0);
	}
	
	public class SyncDisciplineClassTask extends AsyncTask<Student, Context, Boolean>{
		
		private SQLiteDatabase sqlLite;
		
		public SyncDisciplineClassTask(Context applicationContext) {
			DatabaseManager entityManager = new DatabaseManager(applicationContext);
		}

		@Override
		protected Boolean doInBackground(Student... students) {			
			SyncAdapter sync = new SyncAdapter();
			sync.syncDisciplineClassesByStudent(students[0]);
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Context... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
		
	}
}
