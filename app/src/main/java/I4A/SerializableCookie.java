package I4A;

/**
 * Created by acer on 10/5/2018.
 */

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


import okhttp3.Cookie;
public class SerializableCookie implements Serializable {
    private String TAG="SerializableCookieTAG";
    private transient Cookie cookie;

    public SerializableCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void save(String path) {
        try {
            File cookiesFile = new File(path);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cookiesFile));
            writeObject(oos);
            oos.writeObject(cookie.name());
            oos.writeObject(cookie.value());
            oos.writeObject(cookie.expiresAt());
            oos.writeObject(cookie.domain());
            oos.writeObject(cookie.path());
            oos.writeObject(cookie.secure());
            oos.writeObject(cookie.httpOnly());
            oos.flush();
            oos.close();
            Log.d(TAG, "writeObject: ");
        }
        catch (Exception ex){
            Log.d(TAG, "writeObjecterr: "+ex);
        }
    }
    private void writeObject(ObjectOutputStream oos) throws IOException{
//        oos.defaultWriteObject();
    }
    public Cookie readObject(String path) {
        try {
            File cookiesFile2 = new File(path);
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cookiesFile2));
//            ois.defaultReadObject();
            Cookie.Builder cb = new Cookie.Builder()
                    .name((String) ois.readObject())
                    .value((String) ois.readObject())
                    .expiresAt((Long) ois.readObject())
                    .domain((String) ois.readObject())
                    .path((String) ois.readObject());
            if ((boolean) ois.readObject()) {
                cb.secure();
            }
            if ((boolean) ois.readObject()) {
                cb.httpOnly();
            }
            ois.close();
            this.cookie = cb.build();
            Log.d(TAG, "readObject: ");
        }catch (Exception ex){
            Log.d(TAG, "readObjecterr: "+ex);
        }
        return this.cookie;
//                .secure((String) ois.readObject())
//                .path((String) ois.readObject())
        // this.name = builder.name;
//        this.value = builder.value;
//        this.expiresAt = builder.expiresAt;
//        this.domain = builder.domain;
//        this.path = builder.path;
//        this.secure = builder.secure;
//        this.httpOnly = builder.httpOnly;
//        this.persistent = builder.persistent;
//        this.hostOnly = builder.hostOnly;
    }

}
