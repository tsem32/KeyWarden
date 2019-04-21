package com.tasosg.KeyWarden;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.database.Cursor;


public class detailsActivity extends AppCompatDialogFragment  {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextTitle;
    private ExampleDialogListener listener;
    private int id;
    DatabaseHelper dbhelper;




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_detail, null);
            id=this.getArguments().getInt("id");
        System.out.println(id);
        dbhelper=new DatabaseHelper(getActivity());
        Cursor data=dbhelper.getData(this.id);
        builder.setView(view)
                .setTitle("Details")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                });


        editTextUsername = (EditText) view.findViewById(R.id.edit_username);
        editTextPassword = (EditText) view.findViewById(R.id.edit_password);
        editTextTitle= (EditText) view.findViewById(R.id.edit_title);
        editTextUsername.setEnabled(false);
        editTextPassword.setEnabled(false);
        editTextTitle.setEnabled(false);

        if(data.moveToNext()){
            editTextPassword.setText(data.getString(3));
            editTextUsername.setText(data.getString(2));
            editTextTitle.setText(data.getString(1));
        }


        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
         // this.id=  Integer.parseInt(this.getActivity().getIntent().getExtras().getString("id"));;
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyTexts(String username, String password);
    }

}