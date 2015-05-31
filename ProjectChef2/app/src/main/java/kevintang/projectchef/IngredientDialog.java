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
import android.widget.Toast;

/**
 * Created by Kevin on 06/04/2015.
 */
public class IngredientDialog extends DialogFragment {
    LayoutInflater inflater;
    View rootView;
    EditText QuantityEditText, UnitEditText, IngredientNameEditText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.dialog_ingredient, null);
        AlertDialog.Builder DialogBuilder = new AlertDialog.Builder(getActivity());

        QuantityEditText = (EditText) rootView.findViewById(R.id.QuantityEditText);
        UnitEditText = (EditText)rootView.findViewById(R.id.UnitEditText);
        IngredientNameEditText = (EditText)rootView.findViewById(R.id.IngredientNameEditText);

        DialogBuilder.setView(rootView);
        DialogBuilder.setTitle("Add Ingredient");
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
                    String Quantity = QuantityEditText.getText().toString();
                    String Unit = UnitEditText.getText().toString();
                    String NameIngredient = IngredientNameEditText.getText().toString();
                    String Ingredient = Quantity + " " + Unit + " " + NameIngredient;   // Ingredient

                    // Grabs the ingredients inside the EditText from the
                    // fragment and puts it into a string variable here for checking later
                    String IngredientsText = getArguments().getString("IngredientData");

                    // Add Ingredient button adds a new ingredient to the Editable text box for the user include ingredients for a recipe
                    // Each new ingredient added is given a newline
                    if(Quantity.matches("") || Unit.matches("") || NameIngredient.matches("")){
                        Toast.makeText(getActivity(), "All fields required", Toast.LENGTH_SHORT).show();
                    }
                    else if(IngredientsText.matches("")){
                        AddRecipeFragment.IngredientsText.setText(Ingredient);
                        dismiss();
                    }
                    else{
                        AddRecipeFragment.IngredientsText.setText(IngredientsText + "\n" + Ingredient);
                        dismiss();
                    }
                    Toast.makeText(getActivity(), "Ingredient Added", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
