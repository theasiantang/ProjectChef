package kevintang.projectchef;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kevin on 30/03/2015.
 */
public class TestActivityForResponse extends Activity implements GetHttpData{
    TextView ResponseDataView;
    Button RequestButton;
    String Response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        ResponseDataView = (TextView)findViewById(R.id.HttpResponse);
        RequestButton = (Button)findViewById(R.id.HttpRequestButton);
    }

    @Override
    public void onTaskCompleted(String HttpData){
        // What to do with the Response
        Response = HttpData;
        ResponseDataView.setText(HttpData);
        Toast.makeText(this, "Response Retrieved", Toast.LENGTH_LONG).show();
    }

    public void HttpRequestButtonClicked(View v) {
        if(v.getId() == R.id.HttpRequestButton){
            GetData();
        }
    }

    public void GetData(){
        GetDataAsync GetData = new GetDataAsync(this);
        GetData.execute("http://08309.net.dcs.hull.ac.uk/api/admin/register?firstname=Joe&Surname=Bloggs&username=joebloggs&password=secret");
    }
}
