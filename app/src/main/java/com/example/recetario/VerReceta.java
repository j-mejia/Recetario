package com.example.recetario;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.TextView;
import android.view.Menu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class VerReceta extends AppCompatActivity {
    private static final int EDITAR = Menu.FIRST;
    private static final int BORRAR = Menu.FIRST + 1;
    private static final int SALIR = Menu.FIRST + 2;
    String title,content;
    TextView TITLE,CONTENT;
    AdaptadorBD DB;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_receta);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = this.getIntent().getExtras();

        title = bundle.getString("title");
        content = bundle.getString("content");

        TITLE = (TextView)findViewById(R.id.textView_Titulo);
        CONTENT = (TextView)findViewById(R.id.textView_Contenido);
        TITLE.setText(title);
        CONTENT.setText(content);
    }
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        super.onCreateOptionsMenu(menu);
        menu.add(1,EDITAR,0,R.string.menu_editar);
        menu.add(2,BORRAR,0,R.string.menu_eliminar);
        menu.add(3,SALIR,0,R.string.menu_salir);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case EDITAR:
                actividad("edit");
                return true;
            case BORRAR:
                alert();
                return true;
            case SALIR:
                actividad("delete");
                return true;
            //break;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void actividad(String f){
        if(f.equals("edit")){
            String type = "edit";
            Intent intent = new Intent(VerReceta.this,AgregarReceta.class);
            intent.putExtra("type",type);
            intent.putExtra("title",title);
            intent.putExtra("content",content);
            startActivity(intent);

        }else{
            if (f.equals("delete")){
                CookieSyncManager.createInstance(this);
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeAllCookie();
                Intent intent = new Intent(VerReceta.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

    private void alert(){
        AlertDialog alerta;
        alerta = new AlertDialog.Builder(this).create();
        alerta.setTitle("Mensaje de Confirmación");
        alerta.setMessage("¿Desea eliminar la receta?");
        alerta.setButton(Dialog.BUTTON_POSITIVE,"Delete receta", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete();
            }
        });
        alerta.setButton(Dialog.BUTTON_NEGATIVE,"Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        alerta.show();
    }


    private void delete(){
        DB = new AdaptadorBD(this);
        DB.deleteReceta(title);
        actividad("delete");
    }

}
