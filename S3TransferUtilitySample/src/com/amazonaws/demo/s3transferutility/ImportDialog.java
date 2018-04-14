package com.amazonaws.demo.s3transferutility;

/**
 * Created by brian on 3/26/18.
 */

import android.app.Activity;
import android.app.AlertDialog;

public class ImportDialog {final CharSequence[] items = { "Take Photo From Gallery",
        "Take Photo From Camera" };
    Activity activity;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    String detailProvader;

    public ImportDialog(Activity a, String detailProvader) {
        this.activity = a;
        this.detailProvader = detailProvader;
        builder = new AlertDialog.Builder(a);
    }

    public void showDialog() {

        builder.setTitle("wifi Provider Details");
        builder.setMessage(detailProvader);

        AlertDialog alert = builder.create();
        alert.show();
    }
}
