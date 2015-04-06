package kevintang.projectchef;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Kevin on 16/01/2015.
 */
public class FeedbackFragment extends Fragment {
    View rootView;
    Button SendEmailButton;
    EditText NameEditText, FeedbackEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.feedback_layout, container, false);

        SendEmailButton = (Button)rootView.findViewById(R.id.SendEmailButton);
        NameEditText = (EditText)rootView.findViewById(R.id.NameEditText);
        FeedbackEditText = (EditText)rootView.findViewById(R.id.FeedbackEditText);

        SendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.SendEmailButton){
                    SendFeedback();
                }
            }
        });

        return rootView;
    }

    private void SendFeedback(){
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "ktango1223@gmail.com", null));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Project Chef Feedback");
        String EmailBody = "Name: " + NameEditText.getText().toString() + "\n" + FeedbackEditText.getText().toString();
        intent.putExtra(Intent.EXTRA_TEXT, EmailBody);
        startActivity(Intent.createChooser(intent, "Send email..."));
    }
}
