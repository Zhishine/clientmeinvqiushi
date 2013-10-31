package com.news.tool;

import java.io.File;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class NewsDatabase {

	private String rootDirectory = "/data/data/com.news.qiushi/";
	private final String DATABASE_FILENAME = "meinvqiushi.db";
	private Context m_context;

	public NewsDatabase(Context context) {
		m_context = context;
	}

	public SQLiteDatabase openDatabase() {
		try {
			String databaseFilename = rootDirectory + "/" + DATABASE_FILENAME;
			File dir = new File(rootDirectory);
			// if the directory is not found,built it
			if (!dir.exists())
				dir.mkdir();
			// database can not found,copy /raw/serviceinfo.db to
			// /data/data/com.kpt.serviceinfo/
			if (!(new File(databaseFilename)).exists()) {
				Log.v("GetDatabase", "file is not exist");
				return null;
			}
			// open database
			SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
					databaseFilename, null);
			return database;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
