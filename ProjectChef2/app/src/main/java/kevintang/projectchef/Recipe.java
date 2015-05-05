package kevintang.projectchef;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;

/**
 * Created by Kevin on 11/04/2015.
 */
public class Recipe implements Serializable{
    String Title = "";
    String Difficulty = "";
    String Servings = "";
    String Time = "";
    String Ingredients = "";
    String Instructions = "";
    String ImageFilePath = "";
    String PrimaryKey = "";

    public Recipe(){
    }

    public void setTitle(String title){
        Title = title;
    }

    public String getTitle(){
        return Title;
    }

    public void setDifficulty(String difficulty){
        Difficulty = difficulty;
    }

    public String getDifficulty(){
        return Difficulty;
    }

    public void setServings(String servings) {
        Servings = servings;
    }

    public String getServings() {
        return Servings;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTime() {
        return Time;
    }

    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setInstructions(String instructions) {
        Instructions = instructions;
    }

    public String getInstructions() {
        return Instructions;
    }

    public void setImageFilePath(String imageFilePath) {
        ImageFilePath = imageFilePath;
    }

    public String getImageFilePath() {
        return ImageFilePath;
    }

    public void setPrimaryKey(String primaryKey) {
        PrimaryKey = primaryKey;
    }

    public String getPrimaryKey() {
        return PrimaryKey;
    }

}
