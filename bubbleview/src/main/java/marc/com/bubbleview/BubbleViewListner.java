package marc.com.bubbleview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

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

	public BubbleViewListner(Context context) {
		this.mContext = context;
		mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		mBubbleView = new BubbleView(mContext);
		mParams = new WindowManager.LayoutParams();
		mParams.format = PixelFormat.TRANSPARENT;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:
				//
				int[] xy = new int[2];
				v.getLocationOnScreen(xy);
				Bitmap b = BubbleUtil.getBitmapByView(v);
				mWindowManager.addView(mBubbleView,mParams);
				mBubbleView.initPoint(xy[0]+b.getWidth()/2,xy[1]-BubbleUtil.getStatusHeight(mContext)+b.getHeight()/2);
				mBubbleView.setDragBitmap(b);
				v.setVisibility(View.INVISIBLE);
				break;
			case MotionEvent.ACTION_MOVE:
				mBubbleView.updateDragPoint(event.getRawX(),event.getRawY()-BubbleUtil.getStatusHeight(mContext));
				break;
			case MotionEvent.ACTION_UP:
				mBubbleView.handleActionUp();
				break;
		}
		return true;
	}

	@Override
	public void restore() {

	}

	@Override
	public void boom() {

	}
}
