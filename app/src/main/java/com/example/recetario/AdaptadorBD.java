package com.example.recetario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdaptadorBD extends SQLiteOpenHelper {
    public static final String TABLE_ID = "_idReceta";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    private static final String DATABASE = "Receta";
    private static final String TABLE = "Recetas";


    public AdaptadorBD(Context context){
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+ TABLE +" ("+
                TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                TITLE +" TEXT,"+ CONTENT +" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE);
        onCreate(db);
    }
    public void addReceta(String title,String content) {
        ContentValues valores = new ContentValues();
        valores.put(TITLE,title);
        valores.put(CONTENT,content);
        this.getWritableDatabase().insert(TABLE,null,valores);
    }
    public Cursor getReceta(String condition){
        String columnas[]={TABLE_ID,TITLE,CONTENT};
        String[] args = new String[] {condition};
        Cursor c = this.getReadableDatabase().query(TABLE, columnas, TITLE+"=?",args,null,null, null);
        return c;
    }
    public void deleteReceta(String condition){
        String args[]={condition};
        this.getWritableDatabase().delete(TABLE,TITLE +"=?",args);
    }
    public void updateReceta(String title, String content, String condition){
        String args[]={condition};
        ContentValues valores = new ContentValues();
        valores.put(TITLE,title);
        valores.put(CONTENT,content);
        this.getWritableDatabase().update(TABLE,valores,TITLE +"=?",args);
    }

}
