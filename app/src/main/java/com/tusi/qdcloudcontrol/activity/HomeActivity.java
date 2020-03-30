package com.tusi.qdcloudcontrol.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tusi.qdcloudcontrol.R;
import com.tusi.qdcloudcontrol.fragment.HomeFragment;
import com.tusi.qdcloudcontrol.internal.di.HasComponent;
import com.tusi.qdcloudcontrol.internal.di.components.DaggerDataComponent;
import com.tusi.qdcloudcontrol.internal.di.components.DataComponent;

/**
 * Created by linfeng on 2018/10/13  22:21
 * Email zhanglinfengdev@163.com
 *
 * @function details...
 */
public class HomeActivity extends BaseActivity implements HasComponent<DataComponent> {


    private DataComponent dataComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);//设置这句话所在的Activity采用 R.layout的activity_home 布局文件进行布局。
        initHomeFragment();
        initializeInjector();
    }

    AlertDialog alertDialog;

    @Override
    public void onBackPressed() {
        if (alertDialog == null) {//弹出对话框
            alertDialog = new AlertDialog.Builder(this).setTitle("系统提示")
                    .setMessage("确定退出 启迪云控 ?")
                    .setPositiveButton("YES", (DialogInterface dialogInterface, int which) -> {
                        alertDialog.dismiss();
                        super.onBackPressed();
                        System.exit(0);
                    })
                    .setNegativeButton("NO", (DialogInterface dialogInterface, int which) -> {
                        alertDialog.dismiss();
                    }).create();
        }
        alertDialog.show();
    }

    private void initializeInjector() {
        dataComponent = DaggerDataComponent.builder().appComponent(getApplicationComponent()).activityModule(getActivityModule()).build();
    }

    private void initHomeFragment() {
        addFragment(R.id.fl_home_content, HomeFragment.class);
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @Override
    public DataComponent getComponent() {
        return this.dataComponent;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
