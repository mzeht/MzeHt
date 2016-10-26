package com.wpmac.mzeht.mvp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.wpmac.mzeht.LogisticsAdapter;
import com.wpmac.mzeht.R;
import com.wpmac.mzeht.pojo.PostQueryInfo;

import java.util.ArrayList;
import java.util.List;

public class PostSearchActivity extends AppCompatActivity implements View.OnClickListener, MainView{


    private EditText post_name_et;
    private EditText post_id_et;
    private ListView post_list_lv;
    private Button post_search_bn;
    private PostPresenter postPresenter;
    private List<PostQueryInfo.DataBean> dataArray = new ArrayList<>();
    private LogisticsAdapter logisticsAdapter;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_search);
        setTitle("Mvp订单查询");
        initView();
        initData();
        initEvent();
    }
    private void initView() {
        post_name_et = (EditText) findViewById(R.id.post_name_et);
        post_id_et = (EditText) findViewById(R.id.post_id_et);
        post_list_lv = (ListView) findViewById(R.id.post_list_lv);
        post_search_bn = (Button) findViewById(R.id.post_search_bn);
    }
    private void initData() {
        logisticsAdapter = new LogisticsAdapter(getApplicationContext(),dataArray);
        postPresenter = new PostPresenter(this);
        post_list_lv.setAdapter(logisticsAdapter);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("查询中...");
    }
    private void initEvent() {
        post_search_bn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.post_search_bn:
                postPresenter.requestHomeData(post_name_et.getText().toString(),post_id_et.getText().toString());
                break;
        }
    }
    @Override
    public void updateListUI(PostQueryInfo postQueryInfo) {
        dataArray.clear();
        dataArray.addAll(postQueryInfo.getData());
        logisticsAdapter.notifyDataSetChanged();
    }
    @Override
    public void errorToast(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }
    @Override
    public void showProgressDialog(){
        if(progressDialog!=null){
            progressDialog.show();
        }
    }
    @Override
    public void hideProgressDialog(){
        if(progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
    @Override
    protected void onDestroy(){
        //防止activity销毁,postPresenter对象还存在造成的内存泄漏
        if(postPresenter!=null) postPresenter.detach();
        super.onDestroy();
    }
}
