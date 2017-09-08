package marc.com.bubbleview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

/**
 * Created by 王成达
 * Date: 2017/9/7
 * Time: 16:23
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/
public class BubbleViewListner implements View.OnTouchListener ,BubbleView.BubbleActionlistner{

	private Context mContext;
	private WindowManager mWindowManager;
	private BubbleView mBubbleView;
	private WindowManager.LayoutParams mParams;
	private View mStaticView;
	private FrameLayout mFrame;
	private ImageView mBoomImage;
	private BubbleView.BubbleDisappearListner mDisappearListner;

	public BubbleViewListner(View view,Context context,BubbleView.BubbleDisappearListner listner) {
		this.mStaticView = view;
		this.mContext = context;
		this.mDisappearListner = listner;

		mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

		mBubbleView = new BubbleView(mContext);
		mBubbleView.setActionListner(this);

		mParams = new WindowManager.LayoutParams();
		mParams.format = PixelFormat.TRANSPARENT;//WindowManager背景透明
		mParams.flags =FLAG_TRANSLUCENT_STATUS;//WindowManager全屏

		mFrame = new FrameLayout(mContext);

		mBoomImage = new ImageView(mContext);

		mBoomImage.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
				, ViewGroup.LayoutParams.WRAP_CONTENT));

		mFrame.addView(mBoomImage);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:
				//
				int[] xy = new int[2];
				mStaticView.getLocationOnScreen(xy);
				Bitmap b = BubbleUtil.getBitmapByView(mStaticView);
				mWindowManager.addView(mBubbleView,mParams);
				mBubbleView.initPoint(xy[0]+b.getWidth()/2,xy[1] + b.getHeight()/2);
				mBubbleView.setDragBitmap(b);
				mStaticView.setVisibility(View.INVISIBLE);
				break;
			case MotionEvent.ACTION_MOVE:
				mBubbleView.updateDragPoint(event.getRawX(),event.getRawY()/*-BubbleUtil.getStatusHeight(mContext)*/);
				break;
			case MotionEvent.ACTION_UP:
				mBubbleView.handleActionUp();
				break;
		}
		return true;
	}

	@Override
	public void restore() {
		mStaticView.setVisibility(View.VISIBLE);
		mWindowManager.removeView(mBubbleView);
	}

	@Override
	public void boom(PointF position) {
		mWindowManager.removeView(mBubbleView);

		mBoomImage.setBackgroundResource(R.drawable.anim_bubble_pop);
		AnimationDrawable ad = (AnimationDrawable) mBoomImage.getBackground();

		mBoomImage.setX(position.x - ad.getIntrinsicWidth()/2 );
		mBoomImage.setY(position.y - ad.getIntrinsicHeight()/2);
		ad.start();

		long time = BubbleUtil.getAnimationDrawableTime(ad);
		mBoomImage.postDelayed(new Runnable() {
			@Override
			public void run() {
				mWindowManager.removeView(mFrame);
				mDisappearListner.disappear(mStaticView);
			}
		},time);
		mWindowManager.addView(mFrame,mParams);
	}
}
