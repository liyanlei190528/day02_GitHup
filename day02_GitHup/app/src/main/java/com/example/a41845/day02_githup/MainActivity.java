package com.example.a41845.day02_githup;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a41845.day02_githup.adapter.MyAdapter;
import com.example.a41845.day02_githup.api.MyServer;
import com.example.a41845.day02_githup.bean.CaiBean;


import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//李艳雷  1811A
public class MainActivity extends AppCompatActivity {

    private RecyclerView mRv;
    /**
     * 0$
     */
    private TextView mPrices;
    private LinearLayout mLl;
    private ArrayList<CaiBean.DataBean> list;
    public HashMap<Integer, Boolean> map;
    private MyAdapter myAdapter;
    private int pice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDate();
    }

    private void initDate() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(MyServer.url)
                .build();
        MyServer myServer = retrofit.create(MyServer.class);
        Observable<CaiBean> data = myServer.getData();
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CaiBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CaiBean caiBean) {
                        if (caiBean!=null){
                            list.addAll(caiBean.getData());
                            myAdapter.setList(list);
                            myAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void initView() {
        mRv = (RecyclerView) findViewById(R.id.rv);
        mPrices = (TextView) findViewById(R.id.prices);
        mLl = (LinearLayout) findViewById(R.id.ll);

        list = new ArrayList<>();
        map = new HashMap<>();

        myAdapter = new MyAdapter(this, list);
        mRv.setAdapter(myAdapter);
        mRv.setLayoutManager(new LinearLayoutManager(this));

        myAdapter.setOnClick(new MyAdapter.OnClick() {
            @Override
            public void onItem(View view, CaiBean.DataBean dataBean) {
                int num = dataBean.getNum();
                CheckBox check = view.findViewById(R.id.check);
                if (check.isChecked()){
                    pice +=num;
                    mPrices.setText(pice+"$");
                }else {
                    pice -= num;
                    mPrices.setText(pice+"$");
                }
            }
        });

    }
}
