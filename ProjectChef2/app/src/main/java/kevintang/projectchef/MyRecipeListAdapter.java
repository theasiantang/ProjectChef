package kevintang.projectchef;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kevin on 07/04/2015.
 */
public class MyRecipeListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] mTitle;
    private final Integer[] mImageId;

    public MyRecipeListAdapter(Activity context, String[] title, Integer[] imageId){
        super(context, R.layout.my_recipes_layout, title);

        this.context=context;
        this.mTitle=title;
        this.mImageId=imageId;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.my_recipes_layout, null, true);

        TextView TitleText = (TextView)rowView.findViewById(R.id.MyRecipeTitleView);
        ImageView Image = (ImageView)rowView.findViewById(R.id.MyRecipeImageView);

        TitleText.setText(mTitle[position]);
        Image.setImageResource(mImageId[position]);
        return rowView;
    }
}
