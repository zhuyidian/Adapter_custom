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

	//Բ��ֱ��
	private int mRadius;
	//��������child item��Ĭ�ϳߴ�
	private static final float RADIO_DEFAULT_CHILD_DIMENSION = 1 / 4f;
	//���������ڱ߾࣬����padding���ԣ�����Ҫ�߾����øñ���
	private static final float RADIO_PADDING_LAYOUT=1/12f;
	//���������ڱ߾࣬����padding���ԣ�����Ҫ�߾����øñ���
	private float mPadding;
	//����ʱ�Ŀ�ʼ�Ƕ�
	private double mStartAngle = 0;
	//�˵�����ı�
	private String[] mItemTexts;
	//�˵����ͼ��
	private int[] mItemImgs;
	//�˵��ĸ���
	private int mMenuItemCount;
	//�˵�������Դid
	private int mMenuItemLayoutId = R.layout.circle_menu_item;
	//MenuItem�ĵ���¼��ӿ�
	private OnItemClickListener mOnMenuItemClickListener;
	//����������
	private CircleMenuAdapter mListAdapter;
	
	public CircleMenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setPadding(0,0,0,0);
	}
	
	public void setsAdapter(CircleMenuAdapter mAdapter){
		this.mListAdapter=mAdapter;
	}
	
	//����MenuItem�ĵ���¼��ӿ�
	public void setOnItemClickListener(OnItemClickListener mOnMenuItemClickListener){
		this.mOnMenuItemClickListener=mOnMenuItemClickListener;
	}
		
	private void buildMenuItems(){
		//�����û����õĲ�������ʼ��menu item
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
	
	//���ò��ֵĿ��,������Menu item���
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		//��������ĳߴ�
		measureMyself(widthMeasureSpec,heightMeasureSpec);
		//�����˵���ߴ�
		measureChildViews();
	}

	private void measureMyself(int widthMeasureSpec, int heightMeasureSpec){
		int resWidth=0;
		int resHeight=0;
		
		//���ݴ���Ĳ������ֱ��ȡ����ģʽ�Ͳ���ֵ
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		
		//�����͸ߵĲ���ģʽ�Ǿ�ȷֵ
		if(widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY){
			//��Ҫ����Ϊ����ͼ�ĸ߶�
			resWidth=getSuggestedMinimumWidth();
			//���δ���ñ���ͼƬ��������Ϊ��Ļ�Ŀ�ߵ�Ĭ��ֵ
			resWidth= resWidth==0 ? getWidth(): resWidth;
			//��Ҫ����Ϊ����ͼ�ĸ߶�
			resHeight=getSuggestedMinimumHeight();
			//���δ���ñ���ͼƬ��������Ϊ��Ļ�Ŀ�ߵ�Ĭ��ֵ
			resHeight= resHeight==0 ? getHeight(): resHeight;
		}else{
			//���������Ϊ��ȷֵ����ֱ��ȡСֵ
			resWidth = resHeight = Math.min(width,height);
		}
		
		setMeasuredDimension(resWidth, resHeight);
	}

	private void measureChildViews(){
		//��ȡ�뾶
		mRadius = Math.max(getMeasuredWidth(), getMeasuredHeight());
		//menu item����
		final int count = getChildCount();
		//menu item�ߴ�
		int childSize = (int)(mRadius*RADIO_DEFAULT_CHILD_DIMENSION);
		//menu item����ģʽ
		int childMode = MeasureSpec.EXACTLY;
		//��������
		for(int i=0;i<count;i++){
			final View child = getChildAt(i);
			if(child.getVisibility() == GONE){
				continue;
			}
			//����Menu item�ĳߴ磬�Լ����úõ�ģʽ��ȥ��item���в���
			int makeMeasureSpec = -1;
			makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize, childMode);
			child.measure(makeMeasureSpec, makeMeasureSpec);
		}
		mPadding = RADIO_PADDING_LAYOUT * mRadius;
	}

	//����menu itme��λ��
	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		final int childCount = getChildCount();
		int left ,top;
		//Menu itme�ĳߴ�
		int itemWidth = (int)(mRadius*RADIO_DEFAULT_CHILD_DIMENSION);
		//����menu itme�ĸ���������item�Ĳ���ռ�õĽǶ�
		float angleDelay = 360 / childCount;
		
		//������Ӵ�Ĳ˵���������ǵ�λ��
		for(int i=0;i<childCount;i++){
			final View child = getChildAt(i);
			if(child.getVisibility() == GONE){
				continue;
			}
			//�˵������ʼ�Ƕ�
			mStartAngle %= 360;
			//�������ĵ㵽menu item���ĵľ���
			float distanceFromCenter = mRadius / 2f - itemWidth /2 - mPadding;
			//distanceFromCenter cosa ��menu item���ĵ��left����
			left = mRadius/2 + (int)Math.round(distanceFromCenter*Math.cos(Math.toRadians(mStartAngle))-1 / 2f * itemWidth);
			//distanceFromCenter sina ��menu item��������
			top = mRadius/2 + (int)Math.round(distanceFromCenter*Math.sin(Math.toRadians(mStartAngle))-1 / 2f * itemWidth);
			
			//����child view
			child.layout(left, top, left+itemWidth, top+itemWidth);
			//���ӳߴ�
			mStartAngle+=angleDelay;
		}
	}
}
