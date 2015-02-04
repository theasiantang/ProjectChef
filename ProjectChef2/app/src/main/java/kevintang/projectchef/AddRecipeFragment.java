package kevintang.projectchef;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kevin on 03/02/2015.
 */
public class AddRecipeFragment extends Fragment{
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.add_recipe_layout, container, false);
        return rootView;
    }
}
