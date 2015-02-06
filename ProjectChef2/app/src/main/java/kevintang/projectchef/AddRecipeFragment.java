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
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.add_recipe_layout, container, false);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        TitleText = (EditText)getActivity().findViewById(R.id.titleText);
        DifficultyText = (EditText)getActivity().findViewById(R.id.difficultyText);
        ServingsText = (EditText)getActivity().findViewById(R.id.servingsText);
        TimeText = (EditText)getActivity().findViewById(R.id.timeText);
        IngredientsText = (EditText)getActivity().findViewById(R.id.ingredientsText);
        InstructionsText = (EditText)getActivity().findViewById(R.id.instructionsText);
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

                if(TitleText.getText() == null || DifficultyText.getText() == null || ServingsText.getText() == null
                        || TimeText.getText() == null || IngredientsText.getText() == null || InstructionsText.getText() == null){

                    Toast.makeText(getActivity().getBaseContext(), "Complete all fields", Toast.LENGTH_LONG).show();
                }
                else{
                    SQLDatabaseOperations Database = new SQLDatabaseOperations(mContext);
                    Database.DataEntry(Database, mTitle, mDifficulty, mServings, mTime, mIngredients, mInstructions);
                    Toast.makeText(getActivity().getBaseContext(), "Recipe Saved", Toast.LENGTH_LONG).show();
                    //getActivity().getFragmentManager().beginTransaction().remove(this).commit();
                }
                break;
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
