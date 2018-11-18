package I4A;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by acer on 10/5/2018.
 */

public class DataSaver {
    private static final String MY_PREFS_NAME = "I4A_SHARED_STORE";

    public static boolean setArray(Context context, String key, ArrayList<String> arr)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt(key+"_size", arr.size());

        for(int i=0;i<arr.size();i++)
        {
            editor.remove(key+"_" + i);
            editor.putString(key+"_" + i, arr.get(i));
        }
        editor.apply();
        return editor.commit();
    }
    public static ArrayList<String> getArray(Context context,String key)
    {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int size = prefs.getInt(key+"_size", 0);
        ArrayList<String> arr=new ArrayList<String>();
        for(int i=0;i<size;i++)
        {
            arr.add(prefs.getString(key+"_" + i, null));
        }
        return arr;
    }
    public static void saveString(Context context,String key,String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
        editor.commit();
    }
    public static String getString(Context context,String key){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String uuid=prefs.getString(key,"");
        return  uuid;
    }
    public static void saveLong(Context context,String key,Long value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putLong(key, value);
        editor.apply();
        editor.commit();
    }
    public static Long getLong(Context context,String key){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Long uuid=prefs.getLong(key,-1);
        return  uuid;
    }
}
