package com.tusi.qdcloudcontrol.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tusi.qdcloudcontrol.QDApplication;
import com.tusi.qdcloudcontrol.internal.di.components.AppComponent;
import com.tusi.qdcloudcontrol.internal.di.modules.ActivityModule;
import com.tusi.qdcloudcontrol.navigation.Navigator;

import javax.inject.Inject;

/**
 * Created by linfeng on 2018/10/13  22:19
 * Email zhanglinfengdev@163.com
 *
 * @function details...
 */
public class BaseActivity extends AppCompatActivity {//AppCompatActivity比activity头部多一点内容
    @Inject
    Navigator mNavigator;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupStatusBar();
        getApplicationComponent().inject(this);
    }

    protected void setupStatusBar() {
        BarUtils.setStatusBarAlpha(this);//半透明，沉浸式，渐变颜色
        BarUtils.setStatusBarColor(this, Color.RED);
    }

    protected AppComponent getApplicationComponent() {
        return ((QDApplication) getApplication()).getApplicationComponent();
    }
    //ToastUtils弹出显示
    protected void longToast(String msg) {
        ToastUtils.showLong(msg);
    }

    protected void shortToast(String msg) {
        ToastUtils.showShort(msg);
    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     */
    protected void addFragment(int containerViewId, Class fragmentClass) {
        final String fragmentName = fragmentClass.getName();

        final FragmentManager fragmentManager = this.getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentName);

        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (mCurrentFragment != null) {
            fragmentTransaction.detach(mCurrentFragment);
            if (fragment == mCurrentFragment) {
                fragment = null;
            }
        }

        if (fragment == null) {
            final Fragment instantiate = Fragment.instantiate(this, fragmentName);
            instantiate.setArguments(null);
            if (containerViewId <= 0) {
                throw new RuntimeException("FragmentStubId not zero");
            }
            fragmentTransaction.replace(containerViewId, instantiate, fragmentName);
        } else {
            final Bundle arguments = fragment.getArguments();
            if (arguments != null) {
                arguments.putAll(arguments);
            }
            fragmentTransaction.attach(fragment);
        }

        mCurrentFragment = fragment;

        fragmentTransaction.commitAllowingStateLoss();
    }


    /**
     * Get an Activity module for dependency injection.
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
