package com.sheniff.matriculas.app;

import android.provider.BaseColumns;

public final class MibaseContract {

    public MibaseContract(){}

    public static abstract class FeedEntry implements BaseColumns{
        public static final String TABLE_NAME = "Matriculas";
        public static final String _ID = "id";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_DIRECCION = "direccion";
        public static final String COLUMN_NIF = "NIF";
        public static final String COLUMN_MATRICULA = "matricula";
        public static final String COLUMN_TIPO = "tipo";
        public static final String COLUMN_MODELO = "modelo";
        public static final String COLUMN_BASTIDOR = "bastidor";
    }

}
