package kevintang.projectchef;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kevin on 07/04/2015.
 */
public class MyRecipeListAdapter extends ArrayAdapter<Recipe> {

    private final Activity context;
    final ArrayList<Recipe> RecipesList;
    int LayoutResourceId;

    public MyRecipeListAdapter(Activity context, int LayoutResourceID, ArrayList<Recipe> mRecipe){
        super(context, R.layout.my_recipes_layout, mRecipe);

        this.context=context;
        this.LayoutResourceId = LayoutResourceID;
        this.RecipesList = mRecipe;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        View rowView = view;
        RecipeRow mRecipeRow = null;
        if(rowView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(LayoutResourceId, parent, false);
            mRecipeRow = new RecipeRow();
            mRecipeRow.TextView = (TextView)rowView.findViewById(R.id.MyRecipeTitleView);
            mRecipeRow.ImageView = (ImageView)rowView.findViewById(R.id.MyRecipeImageView);
            rowView.setTag(mRecipeRow);
        }
        else{
            mRecipeRow = (RecipeRow)rowView.getTag();
        }

        Recipe mRecipe = RecipesList.get(position);
        mRecipeRow.TextView.setText(mRecipe.getTitle());
        if(mRecipe.getImageFilePath().matches("")){
            mRecipeRow.ImageView.setImageResource(R.drawable.ic_launcher);
        }
        else{
            Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(mRecipe.getImageFilePath()), 640, 360);
            mRecipeRow.ImageView.setImageBitmap(ThumbImage);
        }
        return rowView;
    }

    static class RecipeRow {
        ImageView ImageView;
        TextView TextView;
    }
}
