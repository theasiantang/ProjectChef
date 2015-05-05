package kevintang.projectchef;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Kevin on 04/02/2015.
 */
public class SQLDatabaseOperations extends SQLiteOpenHelper{

    public static final int DatabaseVersion = 1;
    public static final String DataType = " TEXT";
    public static final String Comma = ",";

    // Table creation command
    public static final String SQL_Create_Table = "CREATE TABLE " + SQLDatabase.TableContent.Table_Name + "(" +
            SQLDatabase.TableContent._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + SQLDatabase.TableContent.Column_Title + DataType + Comma +
            SQLDatabase.TableContent.Column_Difficulty + DataType + Comma + SQLDatabase.TableContent.Column_Servings + DataType + Comma +
            SQLDatabase.TableContent.Column_Time + DataType + Comma + SQLDatabase.TableContent.Column_Ingredients + DataType +
            Comma + SQLDatabase.TableContent.Column_Instructions + DataType + Comma +
            SQLDatabase.TableContent.Column_ImageFilePath + DataType + ");";

    public static final String SQL_Delete_Table = "DROP TABLE IF EXISTS " + SQLDatabase.TableContent.Table_Name;

    public SQLDatabaseOperations(Context context){
        super(context, SQLDatabase.TableContent.Database_Name, null, DatabaseVersion);
        Log.d("Database Operations", "Database Opened");
    }

    public void onCreate(SQLiteDatabase Database){
        Database.execSQL(SQL_Create_Table);
        Log.d("Database Operations", "Table Created");
    }

    public void onUpgrade(SQLiteDatabase Database, int OldVersion, int NewVersion){
        Database.execSQL(SQL_Delete_Table);
        Log.d("Database Operations", "Table Deleted");
        onCreate(Database);
    }

    public void onDowngrade(SQLiteDatabase Database, int OldVersion, int NewVersion){
        onUpgrade(Database, OldVersion, NewVersion);
    }

    public void DataEntry(SQLDatabaseOperations Database, Recipe mRecipe){

        // Gets the recipe data from recipe object
        String Title = mRecipe.getTitle();
        String Difficulty = mRecipe.getDifficulty();
        String Servings = mRecipe.getServings();
        String TotalTime = mRecipe.getTime();
        String Ingredients = mRecipe.getIngredients();
        String Instructions = mRecipe.getInstructions();
        String ImageFilePath = mRecipe.getImageFilePath();

        // Gets the data repository in write mode
        SQLiteDatabase DB = Database.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues Values = new ContentValues();
        Values.put(SQLDatabase.TableContent.Column_Title, Title);
        Values.put(SQLDatabase.TableContent.Column_Difficulty, Difficulty);
        Values.put(SQLDatabase.TableContent.Column_Servings, Servings);
        Values.put(SQLDatabase.TableContent.Column_Time, TotalTime);
        Values.put(SQLDatabase.TableContent.Column_Ingredients, Ingredients);
        Values.put(SQLDatabase.TableContent.Column_Instructions, Instructions);
        Values.put(SQLDatabase.TableContent.Column_ImageFilePath, ImageFilePath);

        // Insert the new row, returning the primary key value of the new row
        long RowID;
        RowID = DB.insert(SQLDatabase.TableContent.Table_Name, null, Values);
        Log.d("Database Operations", "New Data Entry Inserted");
    }

    public Cursor GetInformation(SQLDatabaseOperations Database){
        SQLiteDatabase DB = Database.getReadableDatabase();
        String[] Columns = {SQLDatabase.TableContent._ID, SQLDatabase.TableContent.Column_Title, SQLDatabase.TableContent.Column_Difficulty, SQLDatabase.TableContent.Column_Servings,
                SQLDatabase.TableContent.Column_Time, SQLDatabase.TableContent.Column_Ingredients, SQLDatabase.TableContent.Column_Instructions, SQLDatabase.TableContent.Column_ImageFilePath};
        Cursor cursor = DB.query(SQLDatabase.TableContent.Table_Name, Columns, null, null, null, null, null);
        return cursor;
    }

    public Recipe GetRow(SQLDatabaseOperations Database, String ID){
        SQLiteDatabase DB = Database.getReadableDatabase();
        String[] Columns = {SQLDatabase.TableContent._ID, SQLDatabase.TableContent.Column_Title, SQLDatabase.TableContent.Column_Difficulty, SQLDatabase.TableContent.Column_Servings,
                SQLDatabase.TableContent.Column_Time, SQLDatabase.TableContent.Column_Ingredients, SQLDatabase.TableContent.Column_Instructions, SQLDatabase.TableContent.Column_ImageFilePath};
        Cursor cursor = DB.query(SQLDatabase.TableContent.Table_Name, Columns, SQLDatabase.TableContent._ID+"=?", new String[]{ID}, null, null, null);
        cursor.moveToFirst();

        Recipe mRecipe = new Recipe();
        mRecipe.setTitle(cursor.getString(cursor.getColumnIndex(SQLDatabase.TableContent.Column_Title)));
        mRecipe.setDifficulty(cursor.getString(cursor.getColumnIndex(SQLDatabase.TableContent.Column_Difficulty)));
        mRecipe.setServings(cursor.getString(cursor.getColumnIndex(SQLDatabase.TableContent.Column_Servings)));
        mRecipe.setTime(cursor.getString(cursor.getColumnIndex(SQLDatabase.TableContent.Column_Time)));
        mRecipe.setIngredients(cursor.getString(cursor.getColumnIndex(SQLDatabase.TableContent.Column_Ingredients)));
        mRecipe.setInstructions(cursor.getString(cursor.getColumnIndex(SQLDatabase.TableContent.Column_Instructions)));
        mRecipe.setImageFilePath(cursor.getString(cursor.getColumnIndex(SQLDatabase.TableContent.Column_ImageFilePath)));
        mRecipe.setPrimaryKey(cursor.getString(cursor.getColumnIndex(SQLDatabase.TableContent._ID)));
        return mRecipe;
    }

    public Recipe SaveChanges(SQLDatabaseOperations Database, Recipe mRecipe){
        String Title = mRecipe.getTitle();
        String Difficulty = mRecipe.getDifficulty();
        String Servings = mRecipe.getServings();
        String TotalTime = mRecipe.getTime();
        String Ingredients = mRecipe.getIngredients();
        String Instructions = mRecipe.getInstructions();
        String ImageFilePath = mRecipe.getImageFilePath();
        String PrimaryKey = mRecipe.getPrimaryKey();

        SQLiteDatabase DB = Database.getWritableDatabase();
        ContentValues EditRecipe = new ContentValues();
        EditRecipe.put(SQLDatabase.TableContent.Column_Title, Title);
        EditRecipe.put(SQLDatabase.TableContent.Column_Difficulty, Difficulty);
        EditRecipe.put(SQLDatabase.TableContent.Column_Servings, Servings);
        EditRecipe.put(SQLDatabase.TableContent.Column_Time, TotalTime);
        EditRecipe.put(SQLDatabase.TableContent.Column_Ingredients, Ingredients);
        EditRecipe.put(SQLDatabase.TableContent.Column_Instructions, Instructions);
        EditRecipe.put(SQLDatabase.TableContent.Column_ImageFilePath, ImageFilePath);
        DB.update(SQLDatabase.TableContent.Table_Name, EditRecipe,"_ID=" + PrimaryKey, null);
        Log.d("Database Operations", "Recipe Updated");
        return mRecipe;
    }
}
