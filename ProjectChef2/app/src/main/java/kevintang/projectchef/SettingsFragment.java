package kevintang.projectchef;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Kevin on 16/01/2015.
 */
public class SettingsFragment extends Fragment {
    View rootView;
    TextView Count;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.settings_layout, container, false);
        Count = (TextView)rootView.findViewById(R.id.Count);
        return rootView;
    }
}
