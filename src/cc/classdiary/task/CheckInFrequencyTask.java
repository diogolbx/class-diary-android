package cc.classdiary.task;

import cc.classdiary.adapter.SyncAdapter;
import cc.classdiary.model.Frequency;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class CheckInFrequencyTask extends AsyncTask<Void, Context, Boolean> {

	private Frequency frequency;
	private SyncAdapter syncAdapter;

	public CheckInFrequencyTask(Frequency frequency) {
		this.frequency = frequency;
		this.syncAdapter = new SyncAdapter();
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		return syncAdapter.checkInFrequency(frequency);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
	}

}
