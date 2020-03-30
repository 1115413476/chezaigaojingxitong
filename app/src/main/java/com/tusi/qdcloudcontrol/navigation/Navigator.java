package com.tusi.qdcloudcontrol.navigation;

import android.content.Context;
import android.content.Intent;

import com.tusi.qdcloudcontrol.activity.HomeActivity;

import javax.inject.Inject;

/**
 * Created by linfeng on 2018/10/15  12:26
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class Navigator {
    @Inject
    public Navigator() {
    }

    public void navigateToHome(Context context) {
        final Intent intentToHome = HomeActivity.getCallingIntent(context);
        context.startActivity(intentToHome);
    }
}
