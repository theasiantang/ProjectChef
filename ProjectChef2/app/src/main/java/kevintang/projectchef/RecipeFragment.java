package kevintang.projectchef;

import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Kevin on 03/02/2015.
 */
public class RecipeFragment extends Fragment{
    View rootView;
    ImageView RecipeImageView;
    TextView TitleTextView, DifficultyTextView,ServingsTextView, TimeTextView,IngredientsTextView,InstructionsTextView, NoImageView;
    String PrimaryKey;
    Recipe mRecipe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.recipe_layout, container, false);

        TitleTextView = (TextView)rootView.findViewById(R.id.TitleTextView);
        DifficultyTextView = (TextView)rootView.findViewById(R.id.DifficultyTextView);
        ServingsTextView = (TextView)rootView.findViewById(R.id.ServingsTextView);
        TimeTextView = (TextView)rootView.findViewById(R.id.TimeTextView);
        IngredientsTextView = (TextView)rootView.findViewById(R.id.IngredientsTextView);
        InstructionsTextView = (TextView)rootView.findViewById(R.id.InstructionsTextView);
        NoImageView = (TextView)rootView.findViewById(R.id.NoImageView);
        RecipeImageView = (ImageView)rootView.findViewById(R.id.RecipeImageView);

        Recipe();
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        // handles the creation of the action bar
        // sets the search and add new to invisible
        if(menu != null){
            menu.findItem(R.id.action_new).setVisible(false);
            menu.findItem(R.id.action_search).setVisible(false);
        }
        inflater.inflate(R.menu.recipe_actionbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // handles touch events on the actionbar
        switch(item.getItemId()){
            case R.id.action_share:
                break;
            case R.id.action_edit:
                Bundle RecipeData = new Bundle();
                RecipeData.putSerializable("recipe", mRecipe);

                Fragment fragment = new RecipeEditFragment();
                fragment.setArguments(RecipeData);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main, fragment).commit();
                break;
            case R.id.action_settings:
                break;
            case R.id.action_delete:
                Delete();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Recipe(){
        mRecipe = (Recipe)getArguments().getSerializable("recipe");
        PrimaryKey = mRecipe.getPrimaryKey();

        SQLDatabaseOperations DB = new SQLDatabaseOperations(getActivity());
        mRecipe = DB.GetRow(DB, PrimaryKey);

        TitleTextView.setText(mRecipe.getTitle());
        if (mRecipe.getDifficulty().matches("")) {
            DifficultyTextView.setText("N/A");
        }
        else{
            DifficultyTextView.setText(mRecipe.getDifficulty());
        }
        if (mRecipe.getServings().matches("")) {
            ServingsTextView.setText("N/A");
        }
        else{
            ServingsTextView.setText(mRecipe.getServings());
        }
        if (mRecipe.getTime().matches("")) {
            TimeTextView.setText("N/A");
        }
        else{
            TimeTextView.setText(mRecipe.getTime() + " minutes");
        }
        if (mRecipe.getIngredients().matches("")) {
            IngredientsTextView.setText("Please add a list of ingredients used, to edit click the 'pencil' icon top right.");
        }
        else{
            IngredientsTextView.setText(mRecipe.getIngredients());
        }
        if (mRecipe.getInstructions().matches("")) {
            InstructionsTextView.setText("Please add instructions to the recipe, to edit click the 'pencil' icon top right.");
        }
        else{
            InstructionsTextView.setText(mRecipe.getInstructions());
        }
        if(mRecipe.getImageFilePath().matches("")){
                NoImageView.setText("No Image, image can be added by editing");
        }
        else{
            RecipeImageView.setImageURI(Uri.parse(new File(mRecipe.getImageFilePath()).toString()));
        }
    }

    public void Delete(){
        SQLDatabaseOperations DB = new SQLDatabaseOperations(getActivity());
        DB.DeleteRecipe(DB, PrimaryKey);
        Toast.makeText(getActivity(), "Recipe Deleted", Toast.LENGTH_SHORT).show();

        Fragment fragment = new MyRecipesFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main, fragment).commit();
    }
}
