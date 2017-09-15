package com.zabuzalabs.ardemoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zabuzalabs.arlibrary.InitializeAR;
import com.zabuzalabs.arlibrary.Tools.TrackerSessionController;

public class ArActivity extends AppCompatActivity implements TrackerSessionController {

    /*
    * Marker file path from local storage
    */
    private static String filePath = "/storage/emulated/0/media/sowhatorb_marker_dataset.yml";

    /*
    * AR Tracker instance
    */
    private InitializeAR mInitializeAR;

    private TextView mTargetTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInitializeAR = new InitializeAR(this,filePath,this);

        /*
        * add layout as renderer overlay view, to display identified target name
        */

        View layout = View.inflate(this, R.layout.activity_ar,null);

        RelativeLayout renderLayout = (RelativeLayout) layout;
        mTargetTextView = (TextView)layout.findViewById(R.id.targetText);

        addContentView(renderLayout,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

    }

    /*
    * De-initialize AR Tracker instance
    */
    @Override
    protected void onResume() {
        super.onResume();
        mInitializeAR.resumeAR();
    }

    /*
    * Initialize AR Tracker instance
    */
    @Override
    protected void onPause() {
        super.onPause();
        mInitializeAR.pauseAR();
    }

    @Override
    public void onTrackingResult(final String markerId) {
        System.out.println("Target name : " + markerId);
        if(mTargetTextView != null) {
            if (markerId.equalsIgnoreCase("")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTargetTextView.setVisibility(View.GONE);
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTargetTextView.setVisibility(View.VISIBLE);
                        mTargetTextView.setText(markerId);
                    }
                });
            }
        }
    }
}
