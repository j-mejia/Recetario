package com.example.recetario;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class VerReceta extends AppCompatActivity {
    String title,content;
    TextView TITLE,CONTENT;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_receta);

        Bundle bundle = this.getIntent().getExtras();

        title = bundle.getString("title");
        content = bundle.getString("content");

        TITLE = (TextView)findViewById(R.id.textView_Titulo);
        CONTENT = (TextView)findViewById(R.id.textView_Contenido);
        TITLE.setText(title);
        CONTENT.setText(content);
    }
}
