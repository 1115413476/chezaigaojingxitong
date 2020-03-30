package com.tusi.qdcloudcontrol.fragment;

import android.support.v4.app.Fragment;

import com.blankj.utilcode.util.ToastUtils;
import com.tusi.qdcloudcontrol.internal.di.HasComponent;

/**
 * Created by linfeng on 2018/10/13  22:20
 * Email zhanglinfengdev@163.com
 *
 * @function details...
 */
public class BaseFragment extends Fragment {

    protected void longToast(String msg) {
        ToastUtils.showLong(msg);
    }

    protected void shortToast(String msg) {
        ToastUtils.showShort(msg);
    }

    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

}
