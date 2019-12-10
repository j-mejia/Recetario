package com.example.recetario;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.database.Cursor;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AgregarReceta extends AppCompatActivity {
    Button Add;
    EditText TITLE,CONTENT;
    String type,getTitle;
    private static final int SALIR = Menu.FIRST;
    AdaptadorBD DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_receta);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Add = (Button) findViewById(R.id.button_Add);
        TITLE = (EditText)findViewById(R.id.editText_Titulo);
        CONTENT = (EditText)findViewById(R.id.editText_Receta);
        Bundle bundle = this.getIntent().getExtras();
        String content;
        getTitle = bundle.getString("title");
        content = bundle.getString("content");

        type = bundle.getString("type");

        if(type.equals("add")){
            Add.setText("Add Receta");
        }else{
            if(type.equals("edit")){
                TITLE.setText(getTitle);
                CONTENT.setText(content);
                Add.setText("Update receta");
            }
        }
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUpdateRecetas();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu);

        menu.add(1,SALIR,0,R.string.menu_salir);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case SALIR:
                CookieSyncManager.createInstance(this);
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeAllCookie();
                Intent intent = new Intent(AgregarReceta.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
             //break;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void addUpdateRecetas() {
        DB = new AdaptadorBD(this);
        String title,content,msj;
        title = TITLE.getText().toString();
        content = CONTENT.getText().toString();
        if (type.equals("add")){
            if(title.equals("")){
                msj = "Ingrese un titulo";
                TITLE.requestFocus();
                Mensaje(msj);
            }else{
                if(content.equals("")){
                    msj = "Ingrese la receta";
                    CONTENT.requestFocus();
                    Mensaje(msj);
                }else{
                    Cursor c = DB.getReceta(title);
                    String gettitle = "";
                    if(c.moveToFirst()){
                        do {
                            gettitle = c.getString(1);

                        }while (c.moveToNext());
                    }
                    if(gettitle.equals(title)){
                        TITLE.requestFocus();
                        msj = "El titulo de la receta ya existe";
                        Mensaje(msj);
                    }else{
                        DB.addReceta(title,content);
                        actividad(title,content);
                    }
                }
            }
        }else{
            if (type.equals("edit")){
                Add.setText("Update receta");
                if(title.equals("")){
                    msj = "Ingrese la receta";
                    TITLE.requestFocus();
                    Mensaje(msj);
                }else {
                    DB.updateReceta(title,content,getTitle);
                    actividad(title,content);
                }
            }
        }
    }

    public void Mensaje(String msj){
        Toast toast = Toast.makeText(this, msj, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
        toast.show();
    }
    public void actividad(String title, String content){
        Intent intent = new Intent(AgregarReceta.this,VerReceta.class);
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        startActivity(intent);
    }
}
