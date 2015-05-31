package kevintang.projectchef;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

/**
 * Created by Kevin on 04/04/2015.
 */
public class SearchMenuFragment extends Fragment implements GetHttpData{
    View rootView;
    Button KeywordButton, IngredientButton;
    EditText KeywordEditText, IngredientEditText;
    TextView SearchCount, SearchResult;
    ListView SearchRecipesListView;
    ArrayList<String> RecipesList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.search_menu_layout, container, false);

        KeywordButton = (Button)rootView.findViewById(R.id.SearchKeyword);
        IngredientButton = (Button)rootView.findViewById(R.id.SearchIngredient);
        KeywordEditText = (EditText)rootView.findViewById(R.id.KeywordEditText);
        IngredientEditText = (EditText)rootView.findViewById(R.id.IngredientEditText);
        SearchCount = (TextView)rootView.findViewById(R.id.SearchResultCountView);
        SearchResult = (TextView)rootView.findViewById(R.id.SearchResultView);
        SearchRecipesListView = (ListView)rootView.findViewById(R.id.SearchRecipesListView);

        KeywordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.SearchKeyword) {
                    GetDataTitleKeyword();
                }
            }
        });

        IngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.SearchIngredient){
                    GetDataAnyKeyword();
                }
            }
        });

        SearchRecipesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Creates a bundle which will hold the Recipe Object of the clicked item
                Bundle RecipeData = new Bundle();
                RecipeData.putString("recipe", RecipesList.get(position));

                // Opens the detail Recipe Fragment and sends the Recipe Object information to the fragment
                Fragment fragment = new SearchedRecipeFragment();
                fragment.setArguments(RecipeData);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main, fragment).commit();
            }
        });
        return rootView;
    }

    @Override
    public void onTaskCompleted(String HttpData) {
        // handle response
        try{
            JSONObject SearchObject = new JSONObject(HttpData);
            String CountObject = SearchObject.getString("ResultCount");
            JSONArray ResultsObject = SearchObject.getJSONArray("Results");
            String RecipeID = ResultsObject.getString(0);
            SearchCount.setText("Result: " + CountObject);

            // Adds the searched recipes into a list
            RecipesList.add(RecipeID);
            ArrayAdapter<String> Adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, RecipesList);
            SearchRecipesListView.setAdapter(Adapter);
        }
        catch(JSONException e){
            Log.e("JSONException", e.getMessage());
        }
    }

    private void GetDataTitleKeyword(){
        String BigOvenApiKey = "dvxm9HNL7ZnjR65Jevjz1T3jXh80JY62";
        String UserInput = KeywordEditText.getText().toString().replaceAll(", ", "+").replaceAll(" ", "+");

        GetDataAsync GetData = new GetDataAsync(this);
        GetData.execute("http://api.bigoven.com/recipes?title_kw=" + UserInput + "&pg=1&rpp=20" + "&api_key=" + BigOvenApiKey);
    }

    private void GetDataAnyKeyword(){
        String BigOvenApiKey = "dvxm9HNL7ZnjR65Jevjz1T3jXh80JY62";
        String UserInput = IngredientEditText.getText().toString().replaceAll(", ", "+").replaceAll(" ", "+");

        GetDataAsync GetData = new GetDataAsync(this);
        GetData.execute("http://api.bigoven.com/recipes?any_kw=" + UserInput + "&pg=1&rpp=20" + "&api_key=" + BigOvenApiKey);
    }
}
