package kevintang.projectchef;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kevin on 10/04/2015.
 */
public class RecipeEditFragment extends Fragment {
    View rootView;
    static EditText IngredientsEdit, InstructionsEdit;
    EditText TitleEdit, DifficultyEdit, ServingsEdit, TimeEdit;
    TextView NoImageEdit;
    String PrimaryKey, ImageFilePath;
    ImageView RecipeImageEdit;
    Button AddStepEdit, AddIngredientEditButton;
    Recipe mRecipe;
    private static final int Capture_Image_Request_Code = 100;
    private static final int Media_Type_Image = 1;
    private Uri FileUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.recipe_edit_layout, container, false);

        TitleEdit = (EditText)rootView.findViewById(R.id.TitleEditText);
        DifficultyEdit = (EditText)rootView.findViewById(R.id.DifficultyEditText);
        ServingsEdit = (EditText)rootView.findViewById(R.id.ServingsEditText);
        TimeEdit = (EditText)rootView.findViewById(R.id.TimeEditText);
        IngredientsEdit = (EditText)rootView.findViewById(R.id.IngredientsEditText);
        InstructionsEdit = (EditText)rootView.findViewById(R.id.InstructionsEditText);
        NoImageEdit = (TextView)rootView.findViewById(R.id.NoImageEdit);
        RecipeImageEdit = (ImageView)rootView.findViewById(R.id.RecipeImageEdit);
        AddStepEdit = (Button)rootView.findViewById(R.id.AddStepEditButton);
        AddIngredientEditButton = (Button)rootView.findViewById(R.id.AddIngredientEditButton);

        ReadyToEditRecipe();

        AddIngredientEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle IngredientData = new Bundle();
                IngredientData.putString("IngredientData", IngredientsEdit.getText().toString());
                IngredientEditDialog Dialog = new IngredientEditDialog();
                Dialog.setArguments(IngredientData);
                Dialog.show(getFragmentManager(), "Ingredient_Dialog");
            }
        });
        AddStepEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle InstructionData = new Bundle();
                InstructionData.putString("InstructionData", InstructionsEdit.getText().toString());
                InstructionEditDialog Dialog = new InstructionEditDialog();
                Dialog.setArguments(InstructionData);
                Dialog.show(getFragmentManager(), "Instruction_Dialog");
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
        // handles the creation of the action bar
        // sets the search and add new to invisible
        if(menu != null){
            menu.findItem(R.id.action_new).setVisible(false);
            menu.findItem(R.id.action_search).setVisible(false);
        }
        inflater.inflate(R.menu.recipe_edit_actionbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // handles touch events on the actionbar
        switch(item.getItemId()){
            case R.id.action_accept:
                AcceptChanges();
                GoBack();
                break;
            case R.id.action_camera_edit:
                // Create intent to take a picture and return control to the calling application
                Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                FileUri = getOutputMediaFileUri(Media_Type_Image); // Create a file to save the image
                CameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileUri); // Set the image file name

                // Start the image capture intent
                startActivityForResult(CameraIntent, Capture_Image_Request_Code);
                return true;
            case R.id.action_settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ReadyToEditRecipe(){
        mRecipe = (Recipe)getArguments().getSerializable("recipe");

        TitleEdit.setText(mRecipe.getTitle());
        DifficultyEdit.setText(mRecipe.getDifficulty());
        ServingsEdit.setText(mRecipe.getServings());
        TimeEdit.setText(mRecipe.getTime());
        IngredientsEdit.setText(mRecipe.getIngredients());
        InstructionsEdit.setText(mRecipe.getInstructions());
        if(mRecipe.getImageFilePath().matches("")){
            NoImageEdit.setText("No Image");
        }
        else{
            RecipeImageEdit.setImageURI(Uri.parse(new File(mRecipe.getImageFilePath()).toString()));
            NoImageEdit.setText("");
        }
    }

    public void AcceptChanges(){
        mRecipe.setTitle(TitleEdit.getText().toString());
        mRecipe.setDifficulty(DifficultyEdit.getText().toString());
        mRecipe.setServings(ServingsEdit.getText().toString());
        mRecipe.setTime(TimeEdit.getText().toString());
        mRecipe.setIngredients(IngredientsEdit.getText().toString());
        mRecipe.setInstructions(InstructionsEdit.getText().toString());

        SQLDatabaseOperations Database = new SQLDatabaseOperations(getActivity());
        Database.SaveChanges(Database, mRecipe);
        Toast.makeText(getActivity(), "Recipe changes saved", Toast.LENGTH_LONG).show();
    }

    public void GoBack(){
        Bundle RecipeData = new Bundle();
        RecipeData.putSerializable("recipe", mRecipe);

        Fragment fragment = new RecipeFragment();
        fragment.setArguments(RecipeData);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main, fragment).commit();
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
                Uri CapturedImage = FileUri;
                getActivity().getContentResolver().notifyChange(CapturedImage, null);
                ContentResolver CR = getActivity().getContentResolver();
                ImageFilePath = FileUri.getPath();
                mRecipe.setImageFilePath(ImageFilePath);

                try{
                    Bitmap BMPImage = MediaStore.Images.Media.getBitmap(CR, CapturedImage);
                    RecipeImageEdit.setImageBitmap(BMPImage);
                    NoImageEdit.setText("");
                    Log.d("Image Capture", "Image Capture Successful");
                    Toast.makeText(getActivity(), CapturedImage.toString(), Toast.LENGTH_LONG).show();
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
