package marc.com.bubbleview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * Created by 王成达
 * Date: 2017/9/7
 * Time: 14:14
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/
public class BubbleView extends View {

	private final float GOLD_POINT = 0.618f;

	private int mFixedRadius;
	private int mDragRadius = 15;
	private Paint mPaint;
	private PointF mDragPoint,mFixedPoint;
	private int mMaxDistance = 80;
	private double mDistance;
	private Bitmap mDragBitmap;
	private BubbleActionlistner mActionListner;

	public BubbleView(Context context) {
		this(context,null);
	}

	public BubbleView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs,0);
	}

	public BubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		init();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if(mDragPoint == null || mFixedPoint == null)
			return;
		canvas.drawCircle(mDragPoint.x,mDragPoint.y,mDragRadius,mPaint);
		Path p = getBezier();
		if(p != null){
			canvas.drawCircle(mFixedPoint.x,mFixedPoint.y,mFixedRadius,mPaint);
			canvas.drawPath(p,mPaint);
		}
		if(mDragBitmap != null){
			canvas.drawBitmap(mDragBitmap,mDragPoint.x-mDragBitmap.getWidth()/2,mDragPoint.y-mDragBitmap.getHeight()/2,null);
		}

	}

	private void init() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setColor(Color.RED);

		mDragRadius = BubbleUtil.dip2px(getContext(),mDragRadius);
		mMaxDistance = BubbleUtil.dip2px(getContext(), mMaxDistance);
	}

	public void updateDragPoint(float x, float y) {
		mDragPoint.x = x;
		mDragPoint.y = y;
		invalidate();
	}

	public void initPoint(float x, float y) {
		mDragPoint = new PointF(x,y);
		mFixedPoint = new PointF(x,y);
		invalidate();
	}

	private Path getBezier(){
		mDistance = BubbleUtil.getDistance(mFixedPoint,mDragPoint);
		if(mDistance > mMaxDistance)
			return null;
		float scale = (float) ((mMaxDistance -mDistance)/ mMaxDistance);
		mFixedRadius = (int) (mDragRadius * scale);
		Path bezier = new Path();
		float dx = mDragPoint.x - mFixedPoint.x;
		float dy = mDragPoint.y - mFixedPoint.y;
		float tanA = dy/dx;
		double A = Math.atan(tanA);

		float p1x = (float) (mFixedPoint.x + Math.sin(A)*mFixedRadius);
		float p1y = (float) (mFixedPoint.y - Math.cos(A)*mFixedRadius);

		float p2x = (float) (mDragPoint.x + Math.sin(A)*mDragRadius);
		float p2y = (float) (mDragPoint.y - Math.cos(A)*mDragRadius);

		float p3x = (float) (mDragPoint.x - Math.sin(A)*mDragRadius);
		float p3y = (float) (mDragPoint.y + Math.cos(A)*mDragRadius);

		float p4x = (float) (mFixedPoint.x - Math.sin(A)*mFixedRadius);
		float p4y = (float) (mFixedPoint.y + Math.cos(A)*mFixedRadius);

		float controlX = mFixedPoint.x + (mDragPoint.x-mFixedPoint.x)*GOLD_POINT;
		float controlY = mFixedPoint.y + (mDragPoint.y-mFixedPoint.y)*GOLD_POINT;

		bezier.moveTo(p1x,p1y);
		bezier.quadTo(controlX,controlY,p2x,p2y);
		bezier.lineTo(p3x,p3y);
		bezier.quadTo(controlX,controlY,p4x,p4y);
		bezier.close();

		return bezier;
	}

	public static void attach(View view, BubbleDisappearListner listner) {
		view.setOnTouchListener(new BubbleViewListner(view,view.getContext(),listner));
	}

	public void setDragBitmap(Bitmap bitmap) {
		this.mDragBitmap = bitmap;
	}

	public void handleActionUp() {
		if(mDistance < mMaxDistance){
			//回弹
			ValueAnimator animator = ObjectAnimator.ofFloat(1);
			animator.setDuration(350);
			animator.setInterpolator(new OvershootInterpolator(3.0f));
			animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					float percent = (float) animation.getAnimatedValue();
					PointF pf = BubbleUtil.getPointByPercent(mFixedPoint,mDragPoint,percent);
					updateDragPoint(pf.x,pf.y);
				}
			});
			animator.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					if(mActionListner!=null){
						mActionListner.restore();
					}
				}
			});
			animator.start();
		}else{
			//爆炸
			if(mActionListner!=null){
				mActionListner.boom(mDragPoint);
			}
		}
	}

	public void setActionListner(BubbleActionlistner listner){
		this.mActionListner = listner;
	}

	public interface BubbleDisappearListner{
		void disappear(View view);
	}

	public interface BubbleActionlistner{
		void restore();
		void boom(PointF position);
	}
}
