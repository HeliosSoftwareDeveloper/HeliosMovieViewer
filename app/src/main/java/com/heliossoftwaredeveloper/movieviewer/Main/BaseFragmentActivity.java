package com.heliossoftwaredeveloper.movieviewer.Main;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.heliossoftwaredeveloper.movieviewer.R;

/**
 * Base Class for Activities with toolbar
 *
 * Created by rngrajo on 31/01/2018.
 */

public class BaseFragmentActivity extends FragmentActivity {

    private Toolbar toolbar;
    private BaseFragmentActivityCallback callback;

    public interface BaseFragmentActivityCallback{
        void onToolbarNavigationClicked();
    }

    public void initBaseActivity(BaseFragmentActivityCallback pCallBack){
        this.callback = pCallBack;
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        if(callback != null)
            toolbarNavigationListener();
    }

    public void toolbarNavigationListener(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                callback.onToolbarNavigationClicked();
            }
        });
    }

    public void setToolbarTitle(String title){
        toolbar.setTitle(title);
    }

    public void setToolbarNavigationButtonIcon(int imageResource){
        toolbar.setNavigationIcon(imageResource);
    }

    public void setToolbarTextColor(int color){
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
    }
}
