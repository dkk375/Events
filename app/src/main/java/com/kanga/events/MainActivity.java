package com.kanga.events;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import static android.provider.BaseColumns._ID;
import static com.kanga.events.Constants.TABLE_NAME;
import static com.kanga.events.Constants.TIME;
import static com.kanga.events.Constants.TITLE;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    private EventsData events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        events = new EventsData(this);
        try

        {
            addEvent("Hello, Android!");
            Cursor cursor = getEvents();
            showEvents(cursor);
        }
        finally

        {
            events.close();
        }
    }

    private void addEvent(String string) {
// Insère un nouvel enregistrement dans la source de données Events
        SQLiteDatabase db = events.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TIME, System.currentTimeMillis());
        values.put(TITLE, string);
        db.insertOrThrow(TABLE_NAME, null, values);
    }

    private static String[] FROM = { _ID, TIME, TITLE, };
    private static String ORDER_BY = TIME + " DESC" ;
    private Cursor getEvents() {
        SQLiteDatabase db = events.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null,
                null, ORDER_BY);
        startManagingCursor(cursor);
        return cursor;
    }

    private void showEvents(Cursor cursor) {
// On les met tous dans une grosse chaine de caractères
        StringBuilder builder = new StringBuilder(
                "Evènements enregistrés:\n" );
        while (cursor.moveToNext()) {
//On peut utiliser getColumnIndexOrThrow() pour récupérer les indexes
            long id = cursor.getLong(0);
            long time = cursor.getLong(1);
            String title = cursor.getString(2);
            builder.append(id).append(": " );
            builder.append(time).append(": " );
            builder.append(title).append("\n" );
        }
//Afficher à l’écran
        TextView text = (TextView) findViewById(R.id.text);
        text.setText(builder);
    }



}