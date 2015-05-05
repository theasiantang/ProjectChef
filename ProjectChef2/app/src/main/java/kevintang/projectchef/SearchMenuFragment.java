package kevintang.projectchef;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.transform.Result;

/**
 * Created by Kevin on 04/04/2015.
 */
public class SearchMenuFragment extends Fragment implements GetHttpData{
    View rootView;
    Button KeywordButton, IngredientButton;
    EditText KeywordEditText, IngredientEditText;
    TextView SearchCount, SearchResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.search_menu_layout, container, false);

        KeywordButton = (Button)rootView.findViewById(R.id.SearchKeyword);
        IngredientButton = (Button)rootView.findViewById(R.id.SearchIngredient);
        KeywordEditText = (EditText)rootView.findViewById(R.id.KeywordEditText);
        IngredientEditText = (EditText)rootView.findViewById(R.id.IngredientEditText);
        SearchCount = (TextView)rootView.findViewById(R.id.SearchResultCountView);
        SearchResult = (TextView)rootView.findViewById(R.id.SearchResultView);

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

        return rootView;
    }

    @Override
    public void onTaskCompleted(String HttpData) {
        // handle response
        //SearchResult.setText(HttpData);
        try{
            JSONObject SearchObject = new JSONObject(HttpData);
            String CountObject = SearchObject.getString("ResultCount");
            JSONArray ResultsObject = SearchObject.getJSONArray("Results");
            String RecipeID = ResultsObject.getString(0);
            SearchCount.setText("Result: " + CountObject);
            SearchResult.setText(RecipeID);
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
