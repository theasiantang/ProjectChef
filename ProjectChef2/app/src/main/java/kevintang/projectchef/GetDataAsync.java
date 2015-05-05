package kevintang.projectchef;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Kevin on 29/03/2015.
 */
public class GetDataAsync extends AsyncTask<String, Void, String>{
    private GetHttpData listener;

    @Override
    protected String doInBackground(String...urls){
        String ResponseData = null;
        if(urls.length > 0){
            ResponseData = GetHttpData(urls[0]);
        }
        return ResponseData;
    }

    @Override
    protected void onPostExecute(String HttpData){
        listener.onTaskCompleted(HttpData);
    }

    public GetDataAsync(GetHttpData listener){
        this.listener = listener;
    }

    public String GetHttpData(String url){
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("Accept", "application/json");
        try{
            HttpResponse response = client.execute(httpget);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if(statusCode == 200){
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));

                String Line;
                while((Line = reader.readLine()) != null){
                    builder.append(Line);
                }
            }
        }catch(Exception e){
        }
        String dataString = builder.toString();
        return dataString;
    }
}
