package kevintang.projectchef;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Kevin on 03/02/2015.
 */
public class AddRecipeFragment extends Fragment{
    View rootView;
    EditText TitleText, DifficultyText, ServingsText, TimeText, IngredientsText, InstructionsText;
    String mTitle, mDifficulty, mServings, mTime, mIngredients, mInstructions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.add_recipe_layout, container, false);

        TitleText = (EditText)rootView.findViewById(R.id.titleText);
        DifficultyText = (EditText)rootView.findViewById(R.id.difficultyText);
        ServingsText = (EditText)rootView.findViewById(R.id.servingsText);
        TimeText = (EditText)rootView.findViewById(R.id.timeText);
        IngredientsText = (EditText)rootView.findViewById(R.id.ingredientsText);
        InstructionsText = (EditText)rootView.findViewById(R.id.instructionsText);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        if(menu != null){
            menu.findItem(R.id.action_new).setVisible(false);
            menu.findItem(R.id.action_search).setVisible(false);
        }
        inflater.inflate(R.menu.add_recipe_actionbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle your other action bar items...
        Fragment fragment = null;

        switch(item.getItemId()){
            case R.id.action_save:

                // These obtain the data typed by the user in the text fields
                mTitle = TitleText.getText().toString();
                mDifficulty = DifficultyText.getText().toString();
                mServings = ServingsText.getText().toString();
                mTime = TimeText.getText().toString();
                mIngredients = IngredientsText.getText().toString();
                mInstructions = InstructionsText.getText().toString();

                if(mTitle.matches("") || mDifficulty.matches("") || mServings.matches("")
                        || mTime.matches("") || mIngredients.matches("") || mInstructions.matches("")){

                    Toast.makeText(getActivity().getBaseContext(), "Complete all fields", Toast.LENGTH_LONG).show();
                }
                else{
                    SQLDatabaseOperations Database = new SQLDatabaseOperations(getActivity().getApplicationContext());
                    Database.DataEntry(Database, mTitle, mDifficulty, mServings, mTime, mIngredients, mInstructions);
                    Toast.makeText(getActivity().getBaseContext(), "Recipe Saved", Toast.LENGTH_LONG).show();

                    TitleText.setText("");
                    DifficultyText.setText("");
                    ServingsText.setText("");
                    TimeText.setText("");
                    IngredientsText.setText("");
                    InstructionsText.setText("");
                }
                return true;
            case R.id.action_settings:
                fragment = new SettingsFragment();
                getActivity().setTitle(R.string.action_settings); // Sets action bar title
                break;
        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main, fragment)
                .commit();

        return super.onOptionsItemSelected(item);
    }
}
