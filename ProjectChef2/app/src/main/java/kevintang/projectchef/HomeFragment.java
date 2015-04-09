package kevintang.projectchef;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Kevin on 16/01/2015.
 */
public class HomeFragment extends Fragment implements GetHttpData{
    View rootView;
    TextView RecipeDataView;
    Button Delete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.home_layout, container, false);
        RecipeDataView = (TextView)rootView.findViewById(R.id.RecipeOfTheDay);

        Delete =(Button)rootView.findViewById(R.id.DeleteOldButton);
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.DeleteOldButton){
                    SQLDatabaseOperations db = new SQLDatabaseOperations(getActivity());
                    SQLiteDatabase DB = db.getReadableDatabase();
                    db.onUpgrade(DB, 1, 1);
                }
            }
        });

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
