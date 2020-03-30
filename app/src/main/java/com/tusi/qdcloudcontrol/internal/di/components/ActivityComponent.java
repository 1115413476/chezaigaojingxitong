package com.tusi.qdcloudcontrol.internal.di.components;

import android.app.Activity;

import com.tusi.qdcloudcontrol.QDApplication;
import com.tusi.qdcloudcontrol.internal.PerActivity;
import com.tusi.qdcloudcontrol.internal.di.modules.ActivityModule;

import dagger.Component;

/**
 * Created by linfeng on 2018/10/15  10:30
 * Email zhanglinfengdev@163.com
 * Function details...
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    //Exposed to sub-graphs.
    Activity activity();
}
