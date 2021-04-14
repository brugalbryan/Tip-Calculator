package com.example.tipcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.content.DialogInterface;
import android.os.Bundle;

import java.util.Objects;

public class info extends AppCompatDialogFragment {

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle("Detail")
                .setMessage("* “No” (the exact bill, tip, total will be used in calculations) \n \n" +
                        "* “Tip” (the tip will be rounded up before added to the bill to calculate the exact total) \n \n" +
                        "* “Total” (the bill and tip remain exact, but the total will be rounded up)")
                .setPositiveButton("Alright", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}
