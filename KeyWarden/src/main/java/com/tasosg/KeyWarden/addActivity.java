package com.tasosg.KeyWarden;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Random;


public class addActivity extends AppCompatDialogFragment  {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextTitle;
    private ExampleDialogListener listener;
    DatabaseHelper dbhelper;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_add, null);

        builder.setView(view)
                .setTitle("New Entry")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbhelper=new DatabaseHelper(getActivity());
                        String username = editTextUsername.getText().toString();
                        String password = editTextPassword.getText().toString();
                        String title=editTextTitle.getText().toString();

                        dbhelper.insertData(title,username,password,getRandomColor());
                        Intent refresh = new Intent(addActivity.this.getActivity(), MainActivity.class);
                        startActivity(refresh);
                        addActivity.this.getActivity().finish();

                    }
                });

        editTextUsername = (EditText) view.findViewById(R.id.edit_username);
        editTextPassword = (EditText) view.findViewById(R.id.edit_password);
        editTextTitle= (EditText) view.findViewById(R.id.edit_title);


        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyTexts(String username, String password);
    }
    public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

}