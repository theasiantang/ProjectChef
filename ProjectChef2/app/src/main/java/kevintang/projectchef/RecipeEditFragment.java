package kevintang.projectchef;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by Kevin on 10/04/2015.
 */
public class RecipeEditFragment extends Fragment {
    View rootView;
    EditText TitleEdit, DifficultyEdit, ServingsEdit, TimeEdit, IngredientsEdit, InstructionsEdit;
    TextView NoImageEdit;
    String PrimaryKey, Title, Difficulty, Servings, Time, Ingredients, Instructions, ImageFilePath;
    ImageView RecipeImageEdit;
    Button AddStepEdit, AddIngredientEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.recipe_edit_layout, container, false);

        TitleEdit = (EditText)rootView.findViewById(R.id.TitleEditText);
        DifficultyEdit = (EditText)rootView.findViewById(R.id.DifficultyEditText);
        ServingsEdit = (EditText)rootView.findViewById(R.id.ServingsEditText);
        TimeEdit = (EditText)rootView.findViewById(R.id.TimeEditText);
        IngredientsEdit = (EditText)rootView.findViewById(R.id.IngredientsEditText);
        InstructionsEdit = (EditText)rootView.findViewById(R.id.InstructionsEditText);
        NoImageEdit = (TextView)rootView.findViewById(R.id.NoImageEdit);
        RecipeImageEdit = (ImageView)rootView.findViewById(R.id.RecipeImageEdit);
        AddStepEdit = (Button)rootView.findViewById(R.id.AddStepEditButton);
        AddIngredientEdit = (Button)rootView.findViewById(R.id.AddIngredientEditButton);

        ReadyToEditRecipe();
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
        inflater.inflate(R.menu.recipe_edit_actionbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // handles touch events on the actionbar
        switch(item.getItemId()){
            case R.id.action_accept:
                AcceptChanges();
                GoBack();
                break;
            case R.id.action_camera_edit:
                break;
            case R.id.action_settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ReadyToEditRecipe(){
        PrimaryKey = getArguments().getString("Recipe_PrimaryKey");
        Title = getArguments().getString("Recipe_Title");
        Difficulty = getArguments().getString("Recipe_Difficulty");
        Servings = getArguments().getString("Recipe_Servings");
        Time = getArguments().getString("Recipe_Time");
        Ingredients = getArguments().getString("Recipe_Ingredients");
        Instructions = getArguments().getString("Recipe_Instructions");
        ImageFilePath = getArguments().getString("Recipe_ImageFilePath");

        TitleEdit.setText(Title);
        DifficultyEdit.setText(Difficulty);
        ServingsEdit.setText(Servings);
        TimeEdit.setText(Time);
        IngredientsEdit.setText(Ingredients);
        InstructionsEdit.setText(Instructions);
        if(ImageFilePath == null){
            NoImageEdit.setText("No Image");
        }
        else{
            RecipeImageEdit.setImageURI(Uri.parse(new File(ImageFilePath).toString()));
        }
    }

    public void AcceptChanges(){
        ContentValues EditRecipe = new ContentValues();
        EditRecipe.put(SQLDatabase.TableContent.Column_Title, TitleEdit.getText().toString());
        EditRecipe.put(SQLDatabase.TableContent.Column_Difficulty, DifficultyEdit.getText().toString());
        EditRecipe.put(SQLDatabase.TableContent.Column_Servings, ServingsEdit.getText().toString());
        EditRecipe.put(SQLDatabase.TableContent.Column_Time, TimeEdit.getText().toString());
        EditRecipe.put(SQLDatabase.TableContent.Column_Ingredients, IngredientsEdit.getText().toString());
        EditRecipe.put(SQLDatabase.TableContent.Column_Instructions, InstructionsEdit.getText().toString());
        //EditRecipe.put(SQLDatabase.TableContent.Column_ImageFilePath, ImageFilePath);
        SQLDatabaseOperations DB = new SQLDatabaseOperations(getActivity());
        SQLiteDatabase Database = DB.getWritableDatabase();
        Database.update(SQLDatabase.TableContent.Table_Name, EditRecipe,"_ID=" + PrimaryKey, null);
        Log.d("Database Operations", "Recipe Updated");
    }

    public void GoBack(){
        Bundle RecipeData = new Bundle();
        RecipeData.putString("Recipe_PrimaryKey",PrimaryKey);
        RecipeData.putString("Recipe_Title", TitleEdit.getText().toString());
        RecipeData.putString("Recipe_ImageFilePath",ImageFilePath);

        Fragment fragment = new RecipeFragment();
        fragment.setArguments(RecipeData);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main, fragment).commit();
    }
}
