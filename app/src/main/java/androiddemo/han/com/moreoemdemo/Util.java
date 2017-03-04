package androiddemo.han.com.moreoemdemo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Created by hanch on 3/4/2017.
 */

public class Util {


    public static int getMetaDataInt(Context context,String name) {
        try {
            ApplicationInfo appInfo = context.getApplicationContext().getPackageManager()
                    .getApplicationInfo(context.getApplicationContext().getPackageName(),
                            PackageManager.GET_META_DATA);
            int logo = appInfo.metaData.getInt(name);
            return logo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
