package kevintang.projectchef;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kevin on 31/05/2015.
 */
public class SearchedRecipeFragment extends Fragment implements GetHttpData{
    View rootView;
    String RecipeID;
    TextView RecipeObject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.searched_recipe_layout, container, false);

        RecipeObject = (TextView)rootView.findViewById(R.id.SearchedRecipeObject);

        String Recipe = getArguments().getString("recipe");
        try{
            JSONObject RecipeObject = new JSONObject(Recipe);
            RecipeID = RecipeObject.getString("RecipeID");
        }
        catch(JSONException e){
            Log.e("JSONException", e.getMessage());
        }

        String BigOvenApiKey = "dvxm9HNL7ZnjR65Jevjz1T3jXh80JY62";
        GetDataAsync GetData = new GetDataAsync(this);
        GetData.execute("http://api.bigoven.com/recipe/" + RecipeID + "?api_key=" + BigOvenApiKey);
        return rootView;
    }

    @Override
    public void onTaskCompleted(String HttpData) {
        RecipeObject.setText(HttpData);
    }
}
