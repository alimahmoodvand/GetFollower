package AsyncTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.Date;

import I4A.Auth;
import dev.niekirk.com.instagram4android.requests.InstagramSearchUsernameRequest;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUser;
import ir.alimahmoodvan.getfollower.MainActivity;

import static android.content.ContentValues.TAG;


/**
 * Created by acer on 10/6/2018.
 */

public class MainAsync extends AsyncTask<String, Integer, InstagramUser> {
    MainActivity main;
    private final String TAG = "MainAsyncTAG";

    public MainAsync(MainActivity main){
        this.main=main;
    }
    protected InstagramUser doInBackground(String... urls) {
        try {
            Auth auth = new Auth(main);
            auth.reLogin();
            main.instagram = auth.getI4A();
            InstagramUser user = main.instagram.sendRequest(new InstagramSearchUsernameRequest(main.instagram.getUsername())).getUser();
            return user;
        }catch (Exception ex){
            Log.d(TAG, "doInBackground: "+ex.getMessage());
        }
        return null;
    }
    protected void onPostExecute(InstagramUser user) {

        main.usernameText.setText(Html.fromHtml("<a href=\"https://instagram.com/"+user.username+"\">@"+user.username+"</a> "));
        main.usernameText.setMovementMethod(LinkMovementMethod.getInstance());
        main.nameText.setText(user.full_name);
        main.infoText.setText("follower : "+user.follower_count+"    following : "+user.following_count);
        Glide.with(main).load(user.profile_pic_url).into(main.profileImage);
        main.pbar.setVisibility(View.GONE);
    }
}