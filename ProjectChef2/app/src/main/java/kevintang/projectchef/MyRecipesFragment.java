package kevintang.projectchef;

import android.app.FragmentManager;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.app.Fragment;
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
    String rKey, rTitle, rImage;
    ArrayList<String> TitlesList = new ArrayList<>();
    ArrayList<Integer> ImagesList = new ArrayList<>();
    ArrayList<String> PrimaryKeysList = new ArrayList<>();
    String[] PrimaryKeys, Titles;
    Integer[] Images;
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
        else if(cursor != null && cursor.moveToFirst()){ // move pointer to first row
            PopulateMyRecipesFragment();
        }
        return rootView;
    }

    public void PopulateMyRecipesFragment(){
        // For each record the Primary Key, Title and Image is obtained from their respective entry and loaded into a array list
        do{
            rKey = cursor.getString(cursor.getColumnIndex(SQLDatabase.TableContent._ID));
            rTitle = cursor.getString(cursor.getColumnIndex(SQLDatabase.TableContent.Column_Title));
            rImage = cursor.getString(cursor.getColumnIndex(SQLDatabase.TableContent.Column_ImageFilePath));

            PrimaryKeysList.add(rKey);
            TitlesList.add(rTitle);

            if(rImage == null) {
                ImagesList.add(R.drawable.ic_launcher);
            }
            else {
                //ImagesList.add();
            }
        }while(cursor.moveToNext());                // condition is that this loop will continue as long there is a next row

        // Arrays are created for each item as the same size as the array lists
        Titles = new String[TitlesList.size()];
        Images = new Integer[ImagesList.size()];
        PrimaryKeys = new String[PrimaryKeysList.size()];

        // Array lists are copied into their respective arrays, PrimaryKeys array is used later
        Titles = TitlesList.toArray(Titles);
        Images = ImagesList.toArray(Images);
        PrimaryKeys = PrimaryKeysList.toArray(PrimaryKeys);

        // This custom adapter takes the Title and Image arrays and populates the list view within this layout
        MyRecipeListAdapter Adapter = new MyRecipeListAdapter(getActivity(), Titles, Images);
        RecipeList.setAdapter(Adapter);

        // Item listener which listens for user clicks on the list view
        RecipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Creates a bundle which will hold the Primary Key of the clicked item
                Bundle RecipeData = new Bundle();
                RecipeData.putString("Recipe_PrimaryKey", PrimaryKeys[position]);

                // Opens the detail Recipe Fragment and sends the Primary Key information to the fragment
                Fragment fragment = new RecipeFragment();
                fragment.setArguments(RecipeData);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main, fragment).commit();
            }
        });
    }
}
