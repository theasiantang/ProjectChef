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

import java.io.File;

/**
 * Created by Kevin on 03/02/2015.
 */
public class RecipeFragment extends Fragment{
    View rootView;
    ImageView RecipeImageView;
    TextView TitleTextView, DifficultyTextView,ServingsTextView, TimeTextView,IngredientsTextView,InstructionsTextView, NoImageView;
    String PrimaryKey, Title, Difficulty, Servings, Time, Ingredients, Instructions, ImageFilePath;

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
                RecipeData.putString("Recipe_PrimaryKey", PrimaryKey);
                RecipeData.putString("Recipe_Title", Title);
                RecipeData.putString("Recipe_Difficulty", Difficulty);
                RecipeData.putString("Recipe_Servings", Servings);
                RecipeData.putString("Recipe_Time", Time);
                RecipeData.putString("Recipe_Ingredients", Ingredients);
                RecipeData.putString("Recipe_Instructions", Instructions);
                RecipeData.putString("Recipe_ImageFilePath", ImageFilePath);
                Fragment fragment = new RecipeEditFragment();
                fragment.setArguments(RecipeData);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main, fragment).commit();
                break;
            case R.id.action_settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Recipe(){
        PrimaryKey = getArguments().getString("Recipe_PrimaryKey");
        Title = getArguments().getString("Recipe_Title");
        ImageFilePath = getArguments().getString("Recipe_ImageFilePath");

        SQLDatabaseOperations DB = new SQLDatabaseOperations(getActivity());
        Cursor cursor = DB.GetRow(DB, PrimaryKey);
        cursor.moveToFirst();
        Difficulty = cursor.getString(cursor.getColumnIndex(SQLDatabase.TableContent.Column_Difficulty));
        Servings = cursor.getString(cursor.getColumnIndex(SQLDatabase.TableContent.Column_Servings));
        Time = cursor.getString(cursor.getColumnIndex(SQLDatabase.TableContent.Column_Time));
        Ingredients = cursor.getString(cursor.getColumnIndex(SQLDatabase.TableContent.Column_Ingredients));
        Instructions = cursor.getString(cursor.getColumnIndex(SQLDatabase.TableContent.Column_Instructions));

        TitleTextView.setText(Title);
        DifficultyTextView.setText(Difficulty);
        ServingsTextView.setText(Servings);
        TimeTextView.setText(Time);
        IngredientsTextView.setText(Ingredients);
        InstructionsTextView.setText(Instructions);
        if(ImageFilePath == null){
                NoImageView.setText("No Image");
        }
        else{
            RecipeImageView.setImageURI(Uri.parse(new File(ImageFilePath).toString()));
        }
    }
}
