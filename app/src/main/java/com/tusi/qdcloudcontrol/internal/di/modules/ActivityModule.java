package com.tusi.qdcloudcontrol.internal.di.modules;

import android.app.Activity;

import com.tusi.qdcloudcontrol.internal.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by linfeng on 2018/10/15  14:05
 * Email zhanglinfengdev@163.com
 * Function details...
 */
@Module
public class ActivityModule {
    private final Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }


    @Provides
    @PerActivity
    Activity activity() {
        return this.mActivity;
    }

}
