package com.example.status;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private CircleMenuLayout mCircleMenuLayout;
	private String[] mItemTexts = new String[]{
		"安全",
		"特色",
		"抽纸",
		"理财",
		"抓着",
		"新的"
	};
	private int[] mItemImgs= new int[]{
		R.drawable.tu1,	
		R.drawable.tu2,	
		R.drawable.tu3,	
		R.drawable.tu4,	
		R.drawable.tu5,	
		R.drawable.tu6
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		MenuItem mMenuItem0 = new MenuItem(mItemTexts[0],mItemImgs[0]);
		MenuItem mMenuItem1 = new MenuItem(mItemTexts[1],mItemImgs[1]);
		MenuItem mMenuItem2 = new MenuItem(mItemTexts[2],mItemImgs[2]);
		MenuItem mMenuItem3 = new MenuItem(mItemTexts[3],mItemImgs[3]);
		MenuItem mMenuItem4 = new MenuItem(mItemTexts[4],mItemImgs[4]);
		MenuItem mMenuItem5 = new MenuItem(mItemTexts[5],mItemImgs[5]);
		
		List<MenuItem> mMenuItems = new ArrayList<MenuItem>();
		mMenuItems.add(mMenuItem0);
		mMenuItems.add(mMenuItem1);
		mMenuItems.add(mMenuItem2);
		mMenuItems.add(mMenuItem3);
		mMenuItems.add(mMenuItem4);
		mMenuItems.add(mMenuItem5);

		//初始化圆形菜单
		mCircleMenuLayout = (CircleMenuLayout)findViewById(R.id.id_menulayout);
		//设置适配器
		mCircleMenuLayout.setsAdapter(new CircleMenuAdapter(mMenuItems));
		//设置菜单项点击事件
		mCircleMenuLayout.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				// TODO Auto-generated method stub
				System.out.println("onClick---arg2="+arg2);
			}
		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
