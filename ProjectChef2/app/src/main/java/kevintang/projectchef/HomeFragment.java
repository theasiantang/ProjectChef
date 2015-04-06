package kevintang.projectchef;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kevin on 16/01/2015.
 */
public class HomeFragment extends Fragment implements GetHttpData{
    View rootView;
    TextView RecipeDataView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.home_layout, container, false);
        RecipeDataView = (TextView)rootView.findViewById(R.id.RecipeOfTheDay);
        GetData();
        return rootView;
    }

    @Override
    public void onTaskCompleted(String HttpData){
        // What to do with the Response
        RecipeDataView.setText(HttpData);
        //Toast.makeText(getActivity().getBaseContext(), "Response Retrieved", Toast.LENGTH_SHORT).show();
    }

    public void GetData(){
        String BigOvenApiKey = "dvxm9HNL7ZnjR65Jevjz1T3jXh80JY62";
        String BigOvenTestRid = "170602";

        GetDataAsync GetData = new GetDataAsync(this);
        GetData.execute("http://api.bigoven.com/recipes?&pg=1&rpp=20&api_key=" + BigOvenApiKey); // grabs all recipes
    }
}
