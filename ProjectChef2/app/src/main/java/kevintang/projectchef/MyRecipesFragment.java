package kevintang.projectchef;

import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Kevin on 16/01/2015.
 */
public class MyRecipesFragment extends Fragment {
    View rootView;
    ListView RecipeList;
    Recipe mRecipe;
    ArrayList<Recipe> RecipesList = new ArrayList<>();
    Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.my_recipe_list_layout, container, false);
        RecipeList = (ListView)rootView.findViewById(R.id.RecipesListView);

        SQLDatabaseOperations DB = new SQLDatabaseOperations(getActivity());
        cursor = DB.GetInformation(DB);
        if(cursor == null){
            return null;
        }
        else if(cursor != null && cursor.moveToFirst()){    // move pointer to first row
            GetDatabaseData();
        }

        // Item listener which listens for user clicks on the list view
        RecipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Creates a bundle which will hold the Recipe Object of the clicked item
                Bundle RecipeData = new Bundle();
                RecipeData.putSerializable("recipe", RecipesList.get(position));

                // Opens the detail Recipe Fragment and sends the Recipe Object information to the fragment
                Fragment fragment = new RecipeFragment();
                fragment.setArguments(RecipeData);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main, fragment).commit();
            }
        });
        return rootView;
    }

    public void GetDatabaseData(){
        // For each record the Primary Key, Title and Image is obtained from their respective entry and loaded into a array list
        do{
            mRecipe = new Recipe();
            mRecipe.setPrimaryKey(cursor.getString(cursor.getColumnIndex(SQLDatabase.TableContent._ID)));
            mRecipe.setTitle(cursor.getString(cursor.getColumnIndex(SQLDatabase.TableContent.Column_Title)));
            mRecipe.setImageFilePath(cursor.getString(cursor.getColumnIndex(SQLDatabase.TableContent.Column_ImageFilePath)));
            RecipesList.add(mRecipe);
        }while(cursor.moveToNext());                // condition is that this loop will continue as long there is a next row

        // This custom adapter takes the Title and Image arrays and populates the list view within this layout
        MyRecipeListAdapter Adapter = new MyRecipeListAdapter(getActivity(), R.layout.my_recipes_layout, RecipesList);
        RecipeList.setAdapter(Adapter);
    }
}
