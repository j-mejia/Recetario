package com.example.recetario;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int ADD = Menu.FIRST;
    private static final int DELETE = Menu.FIRST + 1;
    private static final int EXIST = Menu.FIRST + 2;
    ListView lista;
    TextView textLista;
    AdaptadorBD DB;
    List<String> Item = null;
    String getTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textLista = (TextView) findViewById(R.id.textView_Lista);
        lista = (ListView) findViewById(R.id.listView_Lista);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add(1,ADD,0,R.string.menu_crear);
        menu.add(2,DELETE,0,R.string.menu_borrar_todas);
        menu.add(3,EXIST,0,R.string.menu_salir);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case ADD:
                actividad("add");
                return true;

            case DELETE:
                return true;
            case EXIST:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    public void actividad(String act){
        String type ="",content = "";
        if (act.equals("add")){
            type = "add";
            Intent intent = new Intent(MainActivity.this,AgregarReceta.class);
            intent.putExtra("type",type);
            startActivity(intent);
        }
    }
}
