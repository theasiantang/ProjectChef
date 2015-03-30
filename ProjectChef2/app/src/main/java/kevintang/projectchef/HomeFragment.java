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
    String Response;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.home_layout, container, false);

        ResponseDataView = (TextView)rootView.findViewById(R.id.HttpResponse);
        RequestButton = (Button)rootView.findViewById(R.id.HttpRequestButton);

        RequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.HttpRequestButton){
                    //Intent intent = new Intent(getActivity().getBaseContext(), TestActivityForResponse.class);
                    //startActivity(intent);
                    GetData();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onTaskCompleted(String HttpData){
        // What to do with the Response
        Response = HttpData;
        ResponseDataView.setText(HttpData);
        Toast.makeText(getActivity().getBaseContext(), "Response Retrieved", Toast.LENGTH_LONG).show();
    }

    public void GetData(){
        GetDataAsync GetData = new GetDataAsync(this);
        GetData.execute("http://08309.net.dcs.hull.ac.uk/api/admin/register?firstname=Joe&Surname=Bloggs&username=joebloggs&password=secret");
    }
}
