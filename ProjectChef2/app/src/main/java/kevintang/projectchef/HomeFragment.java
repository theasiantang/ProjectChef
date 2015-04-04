package kevintang.projectchef;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kevin on 16/01/2015.
 */
public class HomeFragment extends Fragment implements GetHttpData{
    View rootView;
    TextView ResponseDataView;
    Button RequestButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.home_layout, container, false);

        ResponseDataView = (TextView)rootView.findViewById(R.id.HttpResponse);
        RequestButton = (Button)rootView.findViewById(R.id.HttpRequestButton);

        RequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.HttpRequestButton){
                    //GetData();
                }
            }
        });

        GetData();
        return rootView;
    }

    @Override
    public void onTaskCompleted(String HttpData){
        // What to do with the Response
        ResponseDataView.setText(HttpData);
        Toast.makeText(getActivity().getBaseContext(), "Response Retrieved", Toast.LENGTH_LONG).show();
    }

    public void GetData(){
        GetDataAsync GetData = new GetDataAsync(this);
        GetData.execute("http://api.campbellskitchen.com/brandservice.svc/api/recipe/26746?format=json&app_id=2e37b2ff&app_key=8c93684218cdcd342d3453a850e9564d");
    }
}
