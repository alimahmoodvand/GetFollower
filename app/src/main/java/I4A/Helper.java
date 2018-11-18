package I4A;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by acer on 10/5/2018.
 */

public class Helper {
    public static   boolean getPermission(Activity activity, String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity.checkSelfPermission(permission)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(activity, new String[]{permission}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }
}
