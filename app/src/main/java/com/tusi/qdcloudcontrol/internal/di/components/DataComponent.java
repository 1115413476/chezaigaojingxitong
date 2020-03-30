package com.tusi.qdcloudcontrol.internal.di.components;

import com.tusi.qdcloudcontrol.fragment.HomeFragment;
import com.tusi.qdcloudcontrol.internal.PerActivity;
import com.tusi.qdcloudcontrol.internal.di.modules.ActivityModule;
import com.tusi.qdcloudcontrol.internal.di.modules.ApplicationModule;
import com.tusi.qdcloudcontrol.internal.di.modules.DataModule;

import dagger.Component;

/**
 * Created by linfeng on 2018/10/17  12:17
 * Email zhanglinfengdev@163.com
 * Function details...
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, DataModule.class})
public interface DataComponent extends ActivityComponent {
    void inject(HomeFragment fragment);
}
