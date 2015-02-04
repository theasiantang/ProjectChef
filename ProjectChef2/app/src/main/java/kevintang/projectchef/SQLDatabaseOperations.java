package kevintang.projectchef;

import android.content.ContentValues;
import android.content.Context;
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
    public static final String SQL_Create_Table = "CREATE TABLE" + SQLDatabase.TableContent.Table_Name + " (" +
            SQLDatabase.TableContent._ID + " INTEGER PRIMARY KEY," + SQLDatabase.TableContent.Column_Title + DataType + Comma +
            SQLDatabase.TableContent.Column_Difficulty + DataType + Comma + SQLDatabase.TableContent.Column_Servings + DataType + Comma +
            SQLDatabase.TableContent.Column_Time + DataType + Comma + SQLDatabase.TableContent.Column_Ingredients + DataType +
            Comma + SQLDatabase.TableContent.Column_Instructions + DataType + Comma + " )";

    public static final String SQL_Delete_Table = "DROP TABLE IF EXISTS " + SQLDatabase.TableContent.Table_Name;

    public SQLDatabaseOperations(Context context){
        super(context, SQLDatabase.TableContent.Database_Name, null, DatabaseVersion);
        Log.d("Database Operations", "Database Created");
    }

    public void onCreate(SQLiteDatabase Database){
        Database.execSQL(SQL_Create_Table);
        Log.d("Database Operations", "Table Created");
    }

    public void onUpgrade(SQLiteDatabase Database, int OldVersion, int NewVersion){
        Database.execSQL(SQL_Delete_Table);
        onCreate(Database);
    }

    public void onDowngrade(SQLiteDatabase Database, int OldVersion, int NewVersion){
        onUpgrade(Database, OldVersion, NewVersion);
    }

    public void DataEntry(SQLDatabaseOperations Database, String Title, String Difficulty, String Servings,
                          String TotalTime, String Ingredients, String Instructions){

        // Gets the data repository in write mode
        SQLiteDatabase db = Database.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues Values = new ContentValues();
        Values.put(SQLDatabase.TableContent.Column_Title, Title);
        Values.put(SQLDatabase.TableContent.Column_Difficulty, Difficulty);
        Values.put(SQLDatabase.TableContent.Column_Servings, Servings);
        Values.put(SQLDatabase.TableContent.Column_Time, TotalTime);
        Values.put(SQLDatabase.TableContent.Column_Ingredients, Ingredients);
        Values.put(SQLDatabase.TableContent.Column_Instructions, Instructions);

        // Insert the new row, returning the primary key value of the new row
        long RowID = db.insert(SQLDatabase.TableContent.Table_Name, null, Values);
        Log.d("Database Operations", "New Data Entry Inserted");
    }
}
