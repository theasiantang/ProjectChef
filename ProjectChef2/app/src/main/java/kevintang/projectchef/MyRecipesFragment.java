package kevintang.projectchef;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Kevin on 16/01/2015.
 */
public class MyRecipesFragment extends Fragment {
    View rootView;
    //String mTitle, mDifficulty, mServings, mTime, mIngredients, mInstructions;
    ListView RecipeList;
    ArrayList<String> RecipeTitles2 = new ArrayList<>();
    ArrayList<Integer> ImagesList = new ArrayList<>();
    String[] RecipeTitles;
    Integer[] Images;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.my_recipe_list_layout, container, false);
        RecipeList = (ListView)rootView.findViewById(R.id.RecipesListView);

        SQLDatabaseOperations DB = new SQLDatabaseOperations(getActivity());
        Cursor cursor = DB.GetInformation(DB);
        cursor.moveToFirst(); // move pointer to first row

        // For each record the Title and Image is obtained from their respective entry and loaded into a list
        // which is then passed into an array which is an argument for the adapter when set
        // the List View in this layout is updated as the database is updated
        do{
            String Title = cursor.getString(cursor.getColumnIndex(SQLDatabase.TableContent.Column_Title));
            RecipeTitles2.add(Title);
            ImagesList.add(R.drawable.ic_launcher);
            RecipeTitles = new String[RecipeTitles2.size()];
            Images = new Integer[ImagesList.size()];
        }while(cursor.moveToNext());                // condition is that this loop will continue as long there is a next row

        RecipeTitles = RecipeTitles2.toArray(RecipeTitles);
        Images = ImagesList.toArray(Images);
        MyRecipeListAdapter Adapter = new MyRecipeListAdapter(getActivity(), RecipeTitles, Images);
        RecipeList.setAdapter(Adapter);             // populates the list view with the database entries (titles and images only)

        // Item listener which listens for user clicks on the list view
        RecipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "You clicked at " + RecipeTitles[+position], Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
}
