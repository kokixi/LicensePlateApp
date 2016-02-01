package com.sheniff.matriculas.app;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class SearchLicensePlate extends ActionBarActivity implements View.OnClickListener {

    private ImageButton b;
    private EditText ed;
    private String strLine;
    private String s;
    private TextView textView;
    private ImageButton imageButton;

    private int numFilas;

    private SQLiteDatabase db;
    private MibaseHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.sheniff.matriculas.app/databases/EntradaMatriculas.db", null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            mDbHelper = new MibaseHelper(this);
            db = mDbHelper.getWritableDatabase();


            AssetManager assetManager = getAssets();
            InputStream ims = null;
            try {
                ims = assetManager.open("data.csv");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            DataInputStream in = new DataInputStream(ims);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            try {
                strLine = br.readLine();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            readDataFromFile(br);

            try {
                in.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
        b = (ImageButton) findViewById(R.id.search_button);

        ed = (EditText) findViewById(R.id.editText);
        Typeface matEspa = Typeface.createFromAsset(getAssets(), "fonts/matesp.ttf");
        ed.setTypeface(matEspa);

    }

    @Override
    public void onResume(){
        super.onResume();
        b.setOnClickListener(this);
        ed.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.25f));
        ed.setVisibility(View.VISIBLE);

        textView = (TextView) findViewById(R.id.textView);
        textView.setVisibility(View.INVISIBLE);
        textView.setLayoutParams( new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));

        ed.setText("");
        ed.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

        imageButton = (ImageButton) findViewById(R.id.search_button);
        imageButton.setVisibility(View.VISIBLE);

        LinearLayout ly2 = (LinearLayout) findViewById(R.id.layoutError);
        ly2.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onClick(View v) {
        if( !ed.getText().toString().equals( "" ) ) {
            Intent intent = new Intent(this, ShowInfo.class);
            intent.putExtra("matricula", ed.getText().toString());

            startActivity(intent);
        }
    }

    private void loadData(LicensePlate licensePlate){
        ContentValues values = new ContentValues();
        values.put(MibaseContract.FeedEntry._ID, numFilas );
        values.put(MibaseContract.FeedEntry.COLUMN_NIF, licensePlate.dni);
        values.put(MibaseContract.FeedEntry.COLUMN_NOMBRE, licensePlate.name);
        values.put(MibaseContract.FeedEntry.COLUMN_DIRECCION, licensePlate.direccion);
        values.put(MibaseContract.FeedEntry.COLUMN_MATRICULA, licensePlate.licenseP);
        values.put(MibaseContract.FeedEntry.COLUMN_MODELO, licensePlate.modelo);
        values.put(MibaseContract.FeedEntry.COLUMN_BASTIDOR, licensePlate.bastidor);
        values.put(MibaseContract.FeedEntry.COLUMN_TIPO, licensePlate.tipo);

        db.insertWithOnConflict(MibaseContract.FeedEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    private void readDataFromFile(BufferedReader br){
        for(numFilas = 0; strLine != null; numFilas++) {
            LicensePlate lp = new LicensePlate();
            s = strLine.substring(0, strLine.indexOf(';'));
            lp.dni = s; // NIF

            strLine = strLine.substring(strLine.indexOf(';') + 1);
            s = strLine.substring(0, strLine.indexOf(';'));
            lp.name = s;

            strLine = strLine.substring(strLine.indexOf(';') + 1);
            s = strLine.substring(0, strLine.indexOf(';'));
            lp.direccion = s;

            strLine = strLine.substring(strLine.indexOf(';') + 1);
            s = strLine.substring(0, strLine.indexOf(';'));
            lp.licenseP = s;

            strLine = strLine.substring(strLine.indexOf(';') + 1);
            s = strLine.substring(0, strLine.indexOf(';'));
            lp.modelo = s;

            for(int i = 0; i < 3; i++) {
                strLine = strLine.substring(strLine.indexOf(';') + 1);
                s = strLine.substring(0, strLine.indexOf(';')); //SKIP SOME DATA
            }

            strLine = strLine.substring(strLine.indexOf(';') + 1);

            s = strLine.substring(0, strLine.indexOf(';'));
            lp.bastidor = s; //Nº BASTIDOR

            strLine = strLine.substring(strLine.indexOf(';') + 1);
            lp.tipo = strLine; //TYPE

            loadData(lp);

            try {
                strLine = br.readLine();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
