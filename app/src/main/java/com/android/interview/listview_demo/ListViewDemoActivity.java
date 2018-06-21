package com.android.interview.listview_demo;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.android.interview.R;

/**
 * ListViewDemoActivity   解释listview的缓存的机制的问题
  ListView之所以能够实现加载成百上千条数据都不会OOM，最主要在于它内部优秀的实现机制。虽然作为普通的使用者，我们大可不必关心ListView内部到底是怎么实现的，但是当你了解了它的内部原理之后，很多之前难以解释的问题都变得有理有据了
 * ListView在借助RecycleBin机制的帮助下，实现了一个生产者和消费者的模式，不管有任意多条数据需要显示，ListView中的子View其实来来回回就那么几个，移出屏幕的子View会很快被移入屏幕的数据重新利用起来，原理示意图如下所示：
 *
 * https://blog.csdn.net/guolin_blog/article/details/44996879
 */
public class ListViewDemoActivity extends AppCompatActivity {

    private ListView mListView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_demo);
        mListView = findViewById(R.id.list_view);
        mAdapter = new MyAdapter(this);

        /**
         * RecycleBin机制，这个机制也是ListView能够实现成百上千条数据都不会OOM最重要的一个原因。
         * 其实RecycleBin的代码并不多，只有300行左右，它是写在AbsListView中的一个内部类，
         * 所以所有继承自AbsListView的子类，也就是ListView和GridView，都可以使用这个机制。
         * 那我们来看一下RecycleBin中的主要代码，
         */
        for (int i = 0; i < 50; i++) {
            mAdapter.addItem("shiming  " + i);
        }
        mListView.setAdapter(mAdapter);


//        recyclerview
        RecyclerView dd=findViewById(R.id.dimensions);

    }
}
