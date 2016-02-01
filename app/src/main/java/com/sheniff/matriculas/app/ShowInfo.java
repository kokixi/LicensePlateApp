package com.sheniff.matriculas.app;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ShowInfo extends ActionBarActivity {

    private String matricula;
    private SQLiteDatabase dbR;
    private MibaseHelper mDbHelperR;

    private EditText ed;
    private TextView textView;
    private Cursor c;
    private ImageButton imageButton;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            ed = (EditText) findViewById(R.id.editText);
            matricula = extras.getString("matricula");
            ed.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
            ed.setVisibility(View.INVISIBLE);

            textView = (TextView) findViewById(R.id.textView);
            Typeface matEspa = Typeface.createFromAsset(getAssets(),"fonts/matesp.ttf");
            textView.setTypeface(matEspa);
            textView.setVisibility(View.VISIBLE);
            textView.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0f));
            textView.setText(matricula);

            imageButton = (ImageButton) findViewById(R.id.search_button);
            imageButton.setVisibility(View.INVISIBLE);
        }

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setAlpha((float) 0.2);


        initiate();

    }


    public void initiate(){
        mDbHelperR = new MibaseHelper(this);
        dbR = mDbHelperR.getReadableDatabase();

        String[] arg = new String[] {matricula};

        String[] projection = {
            MibaseContract.FeedEntry._ID,
            MibaseContract.FeedEntry.COLUMN_NIF,
            MibaseContract.FeedEntry.COLUMN_NOMBRE,
            MibaseContract.FeedEntry.COLUMN_DIRECCION,
            MibaseContract.FeedEntry.COLUMN_MATRICULA,
            MibaseContract.FeedEntry.COLUMN_MODELO,
            MibaseContract.FeedEntry.COLUMN_BASTIDOR,
            MibaseContract.FeedEntry.COLUMN_TIPO,
        };

        c = dbR.query(
                MibaseContract.FeedEntry.TABLE_NAME,
                projection,
                "matricula=?",
                arg,
                null,
                null,
                null
        );

        if( c.getCount() > 0 ) {
            LinearLayout ly = (LinearLayout) findViewById(R.id.layoutInfo);
            ly.setVisibility(View.VISIBLE);
            c.moveToFirst();
            fill();
        }else{
            LinearLayout ly = (LinearLayout) findViewById(R.id.layoutError);
            ly.setVisibility(View.VISIBLE);
        }
    }

    public void fill(){
        TextView nif = (TextView)findViewById(R.id.dniDatos);
        TextView nombre = (TextView)findViewById(R.id.nombreDatos);
        TextView direccion = (TextView)findViewById(R.id.direccionDatos);
        TextView modelo = (TextView)findViewById(R.id.modeloDatos);
        TextView tipo = (TextView)findViewById(R.id.tipoDatos);
        TextView bastidor = (TextView)findViewById(R.id.bastidorDatos);

        nif.setText(c.getString(1));
        nombre.setText(c.getString(2));
        direccion.setText(c.getString(3));
        modelo.setText(c.getString(5));
        tipo.setText(c.getString(7));
        bastidor.setText(c.getString(6));
    }
}
