package com.example.android.servurance.data;

import android.provider.BaseColumns;

/**
 * Created by tanujanuj on 26/08/17.
 */

public class SignUpContract {
    public static final class  SignUpEntry implements BaseColumns{
        public static final String TABLE_NAME="userdetails";
        public static final String COLUMN_NAME="name";
        public static final String COLUMN_DATE="date";
        public static final String COLUMN_FATHER_NAME="fn";
        public static final String COLUMN_PAN_NUMBER="pn";

    }
}
