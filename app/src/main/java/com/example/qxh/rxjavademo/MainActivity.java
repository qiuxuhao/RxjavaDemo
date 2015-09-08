package com.example.qxh.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.t1)
    TextView t1;
    @Bind(R.id.listView)
    RecyclerView listView;
    private List<String> mDatas;
    private HomeAdapter mAdapter;
    private LinearLayoutManager mLinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Logger.init("qxh");

        t1.setOnClickListener((View v) -> {
            t1.setText("11111111");
        });
        initData();
        listView.setLayoutManager(mLinear = new LinearLayoutManager(this));
        listView.setAdapter(mAdapter = new HomeAdapter());
        mLinear.scrollToPosition(9);

        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mLinear.findFirstVisibleItemPosition() == 0&&dy<0){
                    mLinear.scrollToPosition(9+(mLinear.findLastVisibleItemPosition()-mLinear.findFirstVisibleItemPosition()));
                }
                if (mLinear.findLastVisibleItemPosition() == mLinear.getItemCount()-1&&dy>0){
                    mLinear.scrollToPosition(9-(mLinear.findLastCompletelyVisibleItemPosition()-mLinear.findFirstCompletelyVisibleItemPosition())+1);
                }
            }
        });
    }
    private void initData() {
        mDatas = new ArrayList<String>();

        for (int i = 0; i <10 ; i++) {
            mDatas.add(i+"");
        }
        for (int i = 9; i >0 ; i--) {
            mDatas.add(0,i+"");
        }
        mDatas.add(0,"");
        mDatas.add("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.list_item,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.mTv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.list_item_tv)TextView mTv;
            public MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

}
