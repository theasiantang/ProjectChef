package kevintang.projectchef;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Kevin on 07/04/2015.
 */
public class MyRecipeListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> mTitle;
    private final ArrayList<String> mImage;

    public MyRecipeListAdapter(Activity context, ArrayList<String> title, ArrayList<String> imageId){
        super(context, R.layout.my_recipes_layout, title);

        this.context=context;
        this.mTitle=title;
        this.mImage=imageId;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.my_recipes_layout, null, true);

        TextView TitleText = (TextView)rowView.findViewById(R.id.MyRecipeTitleView);
        ImageView Image = (ImageView)rowView.findViewById(R.id.MyRecipeImageView);
        TitleText.setText(mTitle.get(position));

        if(mImage.get(position) == null){
            Image.setImageResource(R.drawable.ic_launcher);
        }
        else{
            Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(mImage.get(position)), 640, 360);
            Image.setImageBitmap(ThumbImage);
        }

        return rowView;
    }
}
