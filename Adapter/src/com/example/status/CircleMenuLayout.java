package com.example.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import android.content.Context;

import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CircleMenuLayout extends ViewGroup{

	//圆形直径
	private int mRadius;
	//该容器内child item的默认尺寸
	private static final float RADIO_DEFAULT_CHILD_DIMENSION = 1 / 4f;
	//该容器的内边距，无视padding属性，如需要边距请用该变量
	private static final float RADIO_PADDING_LAYOUT=1/12f;
	//改容器的内边距，无视padding属性，如需要边距请用该变量
	private float mPadding;
	//布局时的开始角度
	private double mStartAngle = 0;
	//菜单项的文本
	private String[] mItemTexts;
	//菜单项的图标
	private int[] mItemImgs;
	//菜单的个数
	private int mMenuItemCount;
	//菜单布局资源id
	private int mMenuItemLayoutId = R.layout.circle_menu_item;
	//MenuItem的点击事件接口
	private OnItemClickListener mOnMenuItemClickListener;
	//设置适配器
	private CircleMenuAdapter mListAdapter;
	
	public CircleMenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setPadding(0,0,0,0);
	}
	
	public void setsAdapter(CircleMenuAdapter mAdapter){
		this.mListAdapter=mAdapter;
	}
	
	//设置MenuItem的点击事件接口
	public void setOnItemClickListener(OnItemClickListener mOnMenuItemClickListener){
		this.mOnMenuItemClickListener=mOnMenuItemClickListener;
	}
		
	private void buildMenuItems(){
		//根据用户设置的参数，初始化menu item
		for(int i=0;i<mListAdapter.getCount();i++){
			//View itemView = inflateMenuView(i);
			final View itemView = mListAdapter.getView(i, null, this);
			final int position=i;
			itemView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(mOnMenuItemClickListener!=null){
						mOnMenuItemClickListener.onItemClick(null, arg0, position, 0);
					}
				}
			});

			addView(itemView);
		}
	}
	
	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		if(mListAdapter != null){
			buildMenuItems();
		}
		super.onAttachedToWindow();
	}
	
	//设置布局的宽高,并测量Menu item宽高
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		//测量自身的尺寸
		measureMyself(widthMeasureSpec,heightMeasureSpec);
		//测量菜单项尺寸
		measureChildViews();
	}

	private void measureMyself(int widthMeasureSpec, int heightMeasureSpec){
		int resWidth=0;
		int resHeight=0;
		
		//根据传入的参数，分别获取测量模式和测量值
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		
		//如果宽和高的测量模式非精确值
		if(widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY){
			//主要设置为背景图的高度
			resWidth=getSuggestedMinimumWidth();
			//如果未设置背景图片，则设置为屏幕的宽高的默认值
			resWidth= resWidth==0 ? getWidth(): resWidth;
			//主要设置为背景图的高度
			resHeight=getSuggestedMinimumHeight();
			//如果未设置背景图片，则设置为屏幕的宽高的默认值
			resHeight= resHeight==0 ? getHeight(): resHeight;
		}else{
			//如果都设置为精确值。则直接取小值
			resWidth = resHeight = Math.min(width,height);
		}
		
		setMeasuredDimension(resWidth, resHeight);
	}

	private void measureChildViews(){
		//获取半径
		mRadius = Math.max(getMeasuredWidth(), getMeasuredHeight());
		//menu item数量
		final int count = getChildCount();
		//menu item尺寸
		int childSize = (int)(mRadius*RADIO_DEFAULT_CHILD_DIMENSION);
		//menu item测量模式
		int childMode = MeasureSpec.EXACTLY;
		//迭代测量
		for(int i=0;i<count;i++){
			final View child = getChildAt(i);
			if(child.getVisibility() == GONE){
				continue;
			}
			//计算Menu item的尺寸，以及设置好的模式，去对item进行测量
			int makeMeasureSpec = -1;
			makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize, childMode);
			child.measure(makeMeasureSpec, makeMeasureSpec);
		}
		mPadding = RADIO_PADDING_LAYOUT * mRadius;
	}

	//布局menu itme的位置
	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		final int childCount = getChildCount();
		int left ,top;
		//Menu itme的尺寸
		int itemWidth = (int)(mRadius*RADIO_DEFAULT_CHILD_DIMENSION);
		//根据menu itme的个数，计算item的布局占用的角度
		float angleDelay = 360 / childCount;
		
		//遍历所哟的菜单项，设置他们的位置
		for(int i=0;i<childCount;i++){
			final View child = getChildAt(i);
			if(child.getVisibility() == GONE){
				continue;
			}
			//菜单项的起始角度
			mStartAngle %= 360;
			//计算中心点到menu item中心的距离
			float distanceFromCenter = mRadius / 2f - itemWidth /2 - mPadding;
			//distanceFromCenter cosa 即menu item中心点的left坐标
			left = mRadius/2 + (int)Math.round(distanceFromCenter*Math.cos(Math.toRadians(mStartAngle))-1 / 2f * itemWidth);
			//distanceFromCenter sina 即menu item的纵坐标
			top = mRadius/2 + (int)Math.round(distanceFromCenter*Math.sin(Math.toRadians(mStartAngle))-1 / 2f * itemWidth);
			
			//布局child view
			child.layout(left, top, left+itemWidth, top+itemWidth);
			//叠加尺寸
			mStartAngle+=angleDelay;
		}
	}
}
