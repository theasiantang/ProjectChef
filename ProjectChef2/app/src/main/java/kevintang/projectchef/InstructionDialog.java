package kevintang.projectchef;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kevin on 06/04/2015.
 */
public class InstructionDialog extends DialogFragment{
    LayoutInflater inflater;
    View rootView;
    EditText DescriptionEditText;
    static TextView StepNumberTextView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.dialog_instruction, null);
        AlertDialog.Builder DialogBuilder = new AlertDialog.Builder(getActivity());

        DescriptionEditText = (EditText)rootView.findViewById(R.id.DescriptionEditText);
        StepNumberTextView = (TextView)rootView.findViewById(R.id.StepNumberTextView);
        StepNumberTextView.setText("Step 1");

        DialogBuilder.setView(rootView);
        DialogBuilder.setTitle("Add Step");
        DialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Default positive button will be override later
            }
        });

        DialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return DialogBuilder.create();
    }

    @Override
    public void onStart(){
        super.onStart();
        AlertDialog mDialog = (AlertDialog)getDialog();
        if(mDialog != null){
            // This new function overrides the default positive button so when the
            // user fails to fill out the form the dialog box remains and posts a toast
            Button PositiveButton = mDialog.getButton(Dialog.BUTTON_POSITIVE);
            PositiveButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    String Description = DescriptionEditText.getText().toString();
                    String InstructionsText = AddRecipeFragment.InstructionsText.getText().toString();

                    if(Description.matches("")){    // Checks if description has been filled
                        Toast.makeText(getActivity(), "Step description required", Toast.LENGTH_SHORT).show();
                    }
                    else if(InstructionsText.matches("")){  // Checks if the edit text is empty, if so, its the first step
                        dismiss(); // closes dialog box
                    }
                    else{   // if all pass then it means its the next step and description has been filled
                        dismiss(); // closes dialog box
                    }

                    Toast.makeText(getActivity(), "Step Added", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
