package kevintang.projectchef;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Kevin on 04/04/2015.
 */
public class SearchMenuFragment extends Fragment implements GetHttpData{
    View rootView;
    Button KeywordButton, IngredientButton;
    EditText KeywordEditText, IngredientEditText;
    TextView HttpResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.search_menu_layout, container, false);

        KeywordButton = (Button)rootView.findViewById(R.id.SearchKeyword);
        IngredientButton = (Button)rootView.findViewById(R.id.SearchIngredient);
        KeywordEditText = (EditText)rootView.findViewById(R.id.KeywordEditText);
        IngredientEditText = (EditText)rootView.findViewById(R.id.IngredientEditText);
        HttpResponse = (TextView)rootView.findViewById(R.id.HttpResponse);

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
        HttpResponse.setText(HttpData);
        //Toast.makeText(getActivity().getBaseContext(), "Response Retrieved", Toast.LENGTH_SHORT).show();
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
