package kevintang.projectchef;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kevin on 03/02/2015.
 */
public class RecipeFragment extends Fragment{
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.recipe_layout, container, false);


        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        // handles the creation of the action bar
        // sets the search and add new to invisible
        if(menu != null){
            menu.findItem(R.id.action_new).setVisible(false);
            menu.findItem(R.id.action_search).setVisible(false);
        }
        inflater.inflate(R.menu.recipe_actionbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // handles touch events on the actionbar
        return super.onOptionsItemSelected(item);
    }
}
