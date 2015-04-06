package kevintang.projectchef;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kevin on 03/02/2015.
 */

public class AddRecipeFragment extends Fragment {
    View rootView;
    static EditText TitleText, DifficultyText, ServingsText, TimeText, IngredientsText, InstructionsText;
    String mTitle, mDifficulty, mServings, mTime, mIngredients, mInstructions;
    Button AddIngredientButton, AddStepButton;

    private static final int Capture_Image_Request_Code = 100;
    private static final int Media_Type_Image = 1;
    private Uri FileUri;
    int StepNumber = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.add_recipe_layout, container, false);

        TitleText = (EditText)rootView.findViewById(R.id.titleText);
        DifficultyText = (EditText)rootView.findViewById(R.id.difficultyText);
        ServingsText = (EditText)rootView.findViewById(R.id.servingsText);
        TimeText = (EditText)rootView.findViewById(R.id.timeText);
        IngredientsText = (EditText)rootView.findViewById(R.id.ingredientsText);
        InstructionsText = (EditText)rootView.findViewById(R.id.instructionsText);
        AddIngredientButton = (Button)rootView.findViewById(R.id.AddIngredientButton);
        AddStepButton = (Button)rootView.findViewById(R.id.AddStepButton);

        // Add Step button adds a new step to the Editable text box for the user include instructions for a recipe
        // As each step is added the step count is incremented by 1
        AddStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(InstructionsText.getText().toString().matches("")){
                    StepNumber += 1;
                    InstructionsText.setText("Step " + StepNumber + ":");
                }
                else{
                    if(StepNumber == StepNumber){
                        StepNumber += 1;
                        InstructionsText.setText(InstructionsText.getText().toString() + "\n" + "Step " + StepNumber + ":");
                    }
                }
                Toast.makeText(getActivity(), "Added Step", Toast.LENGTH_SHORT).show();
            }
        });

        AddIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IngredientDialog Dialog = new IngredientDialog();
                Dialog.show(getFragmentManager(), "Ingredient_Dialog");
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        if(menu != null){
            menu.findItem(R.id.action_new).setVisible(false);
            menu.findItem(R.id.action_search).setVisible(false);
        }
        inflater.inflate(R.menu.add_recipe_actionbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle your other action bar items...
        Fragment fragment = null;

        switch(item.getItemId()){
            case R.id.action_save:

                // These obtain the data typed by the user in the text fields
                mTitle = TitleText.getText().toString();
                mDifficulty = DifficultyText.getText().toString();
                mServings = ServingsText.getText().toString();
                mTime = TimeText.getText().toString();
                mIngredients = IngredientsText.getText().toString();
                mInstructions = InstructionsText.getText().toString();

                if(mTitle.matches("") || mDifficulty.matches("") || mServings.matches("")
                        || mTime.matches("") || mIngredients.matches("") || mInstructions.matches("")){

                    Toast.makeText(getActivity().getBaseContext(), "Complete all fields", Toast.LENGTH_LONG).show();
                }
                else{
                    SQLDatabaseOperations Database = new SQLDatabaseOperations(getActivity().getApplicationContext());
                    Database.DataEntry(Database, mTitle, mDifficulty, mServings, mTime, mIngredients, mInstructions);
                    Toast.makeText(getActivity().getBaseContext(), "Recipe Saved", Toast.LENGTH_LONG).show();

                    TitleText.setText("");
                    DifficultyText.setText("");
                    ServingsText.setText("");
                    TimeText.setText("");
                    IngredientsText.setText("");
                    InstructionsText.setText("");
                }
                return true;

            case R.id.action_camera:

                // Create intent to take a picture and return control to the calling application
                Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                FileUri = getOutputMediaFileUri(Media_Type_Image); // Create a file to save the image
                CameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileUri); // Set the image file name

                // Start the image capture intent
                startActivityForResult(CameraIntent, Capture_Image_Request_Code);
                return true;

            case R.id.action_settings:
                fragment = new SettingsFragment();
                getActivity().setTitle(R.string.action_settings); // Sets action bar title
                break;
        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main, fragment)
                .commit();

        return super.onOptionsItemSelected(item);
    }

    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type){
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled
        File MediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Project Chef Camera");

        // Create the storage directory if it does not exist
        if(!MediaStorageDir.exists()){
            if(!MediaStorageDir.mkdirs()){
                Log.d("Project Chef Camera", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String TimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File MediaFile;

        if(type == Media_Type_Image){
            MediaFile = new File(MediaStorageDir.getPath() + File.separator +
                    "IMG_" + TimeStamp + ".jpg");
        }
        else{
            return null;
        }
        return MediaFile;
    }

    @Override
    public void onActivityResult(int RequestCode, int ResultCode, Intent Data){
        super.onActivityResult(RequestCode, ResultCode, Data);

        if(RequestCode == Capture_Image_Request_Code){
            if(ResultCode == Activity.RESULT_OK){
                // Image captured and saved to fileUri specified in the Intent
                Uri SelectedImage = FileUri;
                getActivity().getContentResolver().notifyChange(SelectedImage, null);
                ImageView Image = (ImageView)rootView.findViewById(R.id.imageView);
                ContentResolver CR = getActivity().getContentResolver();
                Bitmap BMPImage;

                try{
                    BMPImage = MediaStore.Images.Media.getBitmap(CR, SelectedImage);
                    Image.setImageBitmap(BMPImage);

                    Log.d("Image Capture", "Image Capture Successful");
                    Toast.makeText(getActivity().getBaseContext(), SelectedImage.toString(), Toast.LENGTH_LONG).show();
                }catch(Exception e){
                    Log.e("Exception", e.toString());
                }
            }
            else if(ResultCode == Activity.RESULT_CANCELED){
                // User cancelled the image capture
                Log.d("Image Capture", "Image Capture Canceled");
                Toast.makeText(getActivity().getBaseContext(), "Image Capture Cancelled", Toast.LENGTH_LONG).show();
            }
            else{
                // Image capture failed, advise user
                Log.d("Image Capture", "Image Capture Failed");
                Toast.makeText(getActivity().getBaseContext(), "Retry Image Capture", Toast.LENGTH_LONG).show();
            }
        }
    }
}
