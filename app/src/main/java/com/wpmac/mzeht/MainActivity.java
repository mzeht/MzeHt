package com.wpmac.mzeht;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.wpmac.mzeht.RollTextView.RollTextViewActivity;
import com.wpmac.mzeht.diffutil.DiffUtilActivity;
import com.wpmac.mzeht.mvp.PostSearchActivity;
import com.wpmac.mzeht.okhttp.OkHttpActivity;
import com.wpmac.mzeht.retrofit.RetrofitActivity;
import com.wpmac.mzeht.rxjava.RxjavaActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.databing)
    Button dataBindingButton;

    @BindView(R.id.okhttp)
    Button okhttpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ButterKnife.bind(this);
//        initListener();
        final PackageManager pm=getApplicationContext().getPackageManager();

    }



    @OnClick(R.id.okhttp)
    void okHttp(){
        Intent Intent = new Intent(getApplicationContext(), OkHttpActivity.class);
        startActivity(Intent);
    }

    @OnClick(R.id.mvp)
    void mvp(){
        Intent Intent = new Intent(getApplicationContext(), PostSearchActivity.class);
        startActivity(Intent);

    }

    @OnClick(R.id.BezierCurve)
    void BezierCurve(){
        
    }
//    private void initListener() {
//        dataBindingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), DataBindingActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        okhttpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent Intent = new Intent(getApplicationContext(), OkHttpActivity.class);
//                startActivity(Intent);
//            }
//        });
//
//
//    }


    @OnClick(R.id.retrofit)
    void retrofit(){
        Intent intent = new Intent(getApplicationContext(), RetrofitActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.diffutil)
    void diffUtil(){
        Intent intent= new Intent(getApplicationContext(), DiffUtilActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.Rxjava)
    void Rxjava(){
        Intent intent= new Intent(getApplicationContext(), RxjavaActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.roll_textview)
    void RollTextView(){
        Intent intent= new Intent(getApplicationContext(), RollTextViewActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
