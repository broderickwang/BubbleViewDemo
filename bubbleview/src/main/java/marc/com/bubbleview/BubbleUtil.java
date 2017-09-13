package marc.com.bubbleview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by 王成达
 * Date: 2017/9/7
 * Time: 14:22
 * Version: 1.0
 * Description:
 * Email:wangchengda1990@gmail.com
 **/
public class BubbleUtil {
	/**
	 * dip转px
	 * @param context
	 * @param dip
	 * @return
	 */
	public static int dip2px(Context context,int dip){
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,context.getResources().getDisplayMetrics());
	}

	/**
	 * 获取两点的直线距离
	 * @param start
	 * @param end
	 * @return
	 */
	public static double getDistance(PointF start,PointF end){
		double distance = Math.sqrt((end.y-start.y)*(end.y-start.y)+(end.x-start.x)*(end.x-start.x));
		return distance;
	}

	/**
	 * 获取状态栏高度
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context){
		int resouceId = context.getResources().getIdentifier("status_bar_height","dimen","android");
		if(resouceId > 0){
			return context.getResources().getDimensionPixelSize(resouceId);
		}
		return BubbleUtil.dip2px(context,25);
	}

	/**
	 * 获取view的bitmap，view的一个照片
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapByView(View view){
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return  bitmap;
	}

	public static PointF getPointByPercent(PointF start, PointF end, float percent) {
		PointF p = new PointF();
		p.x = start.x + (end.x-start.x)*(1-percent);
		p.y = start.y + (end.y-start.y)*(1-percent);
		return p;
	}

	public static long getAnimationDrawableTime(AnimationDrawable animationDrawable){
		long time = 0;
		for (int i=0;i<animationDrawable.getNumberOfFrames();i++){
			time += animationDrawable.getDuration(i);
		}
		return time;
	}
}
