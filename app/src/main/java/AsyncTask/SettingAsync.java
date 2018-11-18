package AsyncTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;

import I4A.Auth;
import dev.niekirk.com.instagram4android.requests.InstagramSearchUsernameRequest;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUser;
import ir.alimahmoodvan.getfollower.MainActivity;



/**
 * Created by acer on 10/6/2018.
 */

public class SettingAsync extends AsyncTask<String, Integer, String> {
    private final String TAG = "SettingAsyncTAG";
    String url="http://5.152.223.138:1082/savesetting";
    HashMap<String, Object> data=new HashMap<>();
    public SettingAsync(String requestURL,
                 HashMap<String, Object> postDataParams){
        url=requestURL;
        data=postDataParams;
    }
    protected String doInBackground(String... urls) {
        try {
            postData(url);
        }catch (Exception ex){
        }
        return null;
    }
    protected void onPostExecute(String res) {

    }
    public void postData(final String address) {
        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            JSONObject jsonParam = new JSONObject(data);

            Log.i("JSON", jsonParam.toString());
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            os.writeBytes(jsonParam.toString());

            os.flush();
            os.close();

            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG" , conn.getResponseMessage());

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}