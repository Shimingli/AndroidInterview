package com.android.interview.listview_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.interview.R;

import java.util.ArrayList;

/**
 * author： Created by shiming on 2018/6/20 17:22
 * mailbox：lamshiming@sina.com
 */

class MyAdapter extends BaseAdapter {

    private ArrayList<String> mData = new ArrayList();
    private LayoutInflater mInflater;

    public MyAdapter(Context context) {
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final String item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    /*
06-20 17:36:01.691 29379-29379/com.android.interview I/System.out: getView 0 null
06-20 17:36:01.695 29379-29379/com.android.interview I/System.out: getView 1 null
06-20 17:36:01.697 29379-29379/com.android.interview I/System.out: getView 2 null
06-20 17:36:01.700 29379-29379/com.android.interview I/System.out: getView 3 null
06-20 17:36:01.703 29379-29379/com.android.interview I/System.out: getView 4 null
06-20 17:36:01.705 29379-29379/com.android.interview I/System.out: getView 5 null
06-20 17:36:01.708 29379-29379/com.android.interview I/System.out: getView 6 null
06-20 17:36:01.710 29379-29379/com.android.interview I/System.out: getView 7 null
06-20 17:36:01.712 29379-29379/com.android.interview I/System.out: getView 8 null
06-20 17:36:01.714 29379-29379/com.android.interview I/System.out: getView 9 null
06-20 17:36:01.716 29379-29379/com.android.interview I/System.out: getView 10 null
06-20 17:36:13.145 29379-29379/com.android.interview I/System.out: getView 11 null
06-20 17:36:13.507 29379-29379/com.android.interview I/System.out: getView 12 android.widget.FrameLayout{fce2484 V.E...... ........ 0,-146-1080,4}
06-20 17:36:13.986 29379-29379/com.android.interview I/System.out: getView 13 android.widget.FrameLayout{bfdf46d V.E...... ........ 0,-146-1080,4}
06-20 17:36:15.964 29379-29379/com.android.interview I/System.out: getView 2 android.widget.FrameLayout{4e878a2 V.E...... ........ 0,-146-1080,4}
06-20 17:36:16.062 29379-29379/com.android.interview I/System.out: getView 1 android.widget.FrameLayout{bfdf46d V.E...... ........ 0,1525-1080,1675}
06-20 17:36:16.591 29379-29379/com.android.interview I/System.out: getView 0 android.widget.FrameLayout{fce2484 V.E...... ........ 0,1552-1080,1702}
     */
    //上面是输出的结果
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*
       android.widget.FrameLayout{4745a49 V.E...... ........ 0,1398-1080,1548}
       直接看 View类的toString的方法
       android.widget.FrameLayout ==== getClass().getName()
       4745a49 ====是java根据对象在内存中的地址算出来的一个数值，不同的地址算出来的结果是不一样的
       V ===VISIBLE
       E=====ENABLED
        . 0,1398-1080,1548  这行代码的意思如下
        out.append(' ');
        out.append(mLeft);
        out.append(',');
        out.append(mTop); 该视图相对于父视图的顶部位置。
        out.append('-');
        out.append(mRight);
        out.append(',');
        out.append(mBottom);
         */
        // TODO: 2018/6/20 开头进来的 都是为null  所以所 这里需要
        System.out.println("getView " + position + " " + convertView);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.textView = (TextView)convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.textView.setText(mData.get(position));
        return convertView;
    }
    public static class ViewHolder {
        public TextView textView;
    }
}


