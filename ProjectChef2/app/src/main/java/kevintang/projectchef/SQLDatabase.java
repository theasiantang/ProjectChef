package kevintang.projectchef;

import android.provider.BaseColumns;

/**
 * Created by Kevin on 04/02/2015.
 */
public class SQLDatabase {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public SQLDatabase() {}

    // Defines the table contents
    public static abstract class TableContent implements BaseColumns{
        public static final String Table_Name = "user_recipes";
        //public static final String _ID = null;
        public static final String Column_Title = "title";
        public static final String Column_Difficulty = "difficulty";
        public static final String Column_Servings = "number_of_servings";
        public static final String Column_Time = "total_time";
        public static final String Column_Ingredients = "ingredients";
        public static final String Column_Instructions = "instructions";
        public static final String Database_Name = "recipes.db";
    }
}
