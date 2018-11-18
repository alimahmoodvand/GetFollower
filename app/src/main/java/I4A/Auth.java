package I4A;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import dev.niekirk.com.instagram4android.requests.payload.InstagramLoginResult;
import okhttp3.Cookie;

/**
 * Created by acer on 10/5/2018.
 */

public class Auth {
    private final Context context;
    String TAG="AuthTAG";
    private I4A i4a=null;
    public Auth(Context context){
        this.context=context;
    }
    public void login(String username, String password) throws  Exception{
        try {

            I4A instagram = new I4A(username, password);
            instagram.setup();
            InstagramLoginResult ilr = instagram.login();
            Iterator var3 = instagram.getCookieStore().entrySet().iterator();
            ArrayList<String> names = new ArrayList<String>();
            while (var3.hasNext()) {
                Map.Entry<String, Cookie> entry = (Map.Entry) var3.next();
                Cookie cookie = (Cookie) entry.getValue();
                SerializableCookie sc = new SerializableCookie(cookie);
                sc.save(pathBuilder(username,cookie.name()));
                names.add(cookie.name());
            }
            DataSaver.setArray(context,username+"name", names);
            DataSaver.saveString(context,username+"uuid", instagram.getUuid());
            DataSaver.saveLong(context,username+"userId", instagram.getUserId());
            if (instagram.getAccessToken() != null)
                DataSaver.saveString(context,username+"accessToken", instagram.getAccessToken());
            i4a=instagram;
            DataSaver.saveString(context,"username", username);
            DataSaver.saveString(context,"password", password);

        }catch (Exception ex){
            Log.d(TAG, "login: "+ex);
            throw ex;
        }
    }
    public static String getUsername(Context context){
        String username=DataSaver.getString(context,"username");
        return username;
    }
    public void reLogin() throws  Exception {
        try {
            String username=DataSaver.getString(context,"username");
            String password=DataSaver.getString(context,"password");
            ArrayList<String> retrive = DataSaver.getArray(context, username + "name");
            HashMap<String, Cookie> hmcr = new HashMap<>();
            for (int i = 0; i < retrive.size(); i++) {
                SerializableCookie sc = new SerializableCookie(null);
                sc.readObject(pathBuilder(username,retrive.get(i)));
                hmcr.put(sc.getCookie().name(), sc.getCookie());
            }

            I4A instagram = new I4A(username, password);
            instagram.setCookieStore(hmcr);
            instagram.setUUId(DataSaver.getString(context, username + "uuid"));
            instagram.setUserId(DataSaver.getLong(context, username + "userId"));
            instagram.setAccessToken(DataSaver.getString(context, username + "accessToken"));
            instagram.setup();
            instagram.reLogin();
            i4a=instagram;
        }
        catch (Exception ex){
            Log.d(TAG, "reLogin: "+ex.getMessage());
            throw ex;
        }
    }
    private String pathBuilder(String username,String name){
        String dir =context.getApplicationInfo().dataDir;
                //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath();
        String path=dir+"/"+username+"_"+name+".txt";
        Log.d(TAG, "pathBuilder: "+path);
        return path;
    }
    public I4A getI4A(){
        return i4a;
    }
}
