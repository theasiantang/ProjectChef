package kevintang.projectchef;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Kevin on 06/04/2015.
 */
public class IngredientDialog extends DialogFragment {
    LayoutInflater inflater;
    View rootView;
    EditText QuantityEditText, UnitEditText, IngredientNameEditText;
    private int IngredientNumber = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.dialog_ingredient, null);
        AlertDialog.Builder DialogBuilder = new AlertDialog.Builder(getActivity());

        DialogBuilder.setView(rootView).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                QuantityEditText = (EditText) rootView.findViewById(R.id.QuantityEditText);
                UnitEditText = (EditText)rootView.findViewById(R.id.UnitEditText);
                IngredientNameEditText = (EditText)rootView.findViewById(R.id.IngredientNameEditText);

                String Quantity = QuantityEditText.getText().toString();
                String Unit = UnitEditText.getText().toString();
                String NameIngredient = IngredientNameEditText.getText().toString();
                String Ingredient = Quantity + " " + Unit + " " + NameIngredient;   // Ingredient
                String IngredientsText = AddRecipeFragment.IngredientsText.getText().toString();    // Grabs the ingredients inside the EditText from the
                                                                                                    // fragment and puts it into a string variable here for use later

                // Add Ingredient button adds a new ingredient to the Editable text box for the user include ingredients for a recipe
                // Each new ingredient added is given a newline
                if(IngredientsText.matches("")){
                    AddRecipeFragment.IngredientsText.setText(Ingredient);
                }
                else{
                    AddRecipeFragment.IngredientsText.setText(IngredientsText + "\n" + Ingredient);
                }
                Toast.makeText(getActivity(), "Ingredient Added", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return DialogBuilder.create();
    }
}
