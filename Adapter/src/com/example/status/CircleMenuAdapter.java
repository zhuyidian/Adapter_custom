package com.example.status;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CircleMenuAdapter extends BaseAdapter{
	List<MenuItem> mMenuItems;
	
	public CircleMenuAdapter(List<MenuItem> menuItems) {
		// TODO Auto-generated constructor stub
		mMenuItems=menuItems;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mMenuItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mMenuItems.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	//加载菜单项布局，并初始化每个菜单
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		LayoutInflater mInflater = LayoutInflater.from(arg2.getContext());
		View itemView = mInflater.inflate(R.layout.circle_menu_item, arg2,false);
		initMenuItem(itemView,arg0);
		return itemView;
	}
	
	private void initMenuItem(View itemView,int childIndex){
		//获取数据项
		final MenuItem item = (MenuItem) getItem(childIndex);
		ImageView iv = (ImageView) itemView.findViewById(R.id.id_circle_menu_item_image);
		TextView tv = (TextView)itemView.findViewById(R.id.id_circle_menu_item_text);
		//数据绑定
		iv.setImageResource(item.imageID);
		tv.setText(item.title);
	}
}
