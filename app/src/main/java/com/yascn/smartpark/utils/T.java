package com.yascn.smartpark.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * Toast统一管理类
 * 
 */
public class T
{

	private T()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static boolean isShow = true;
	private static long lastClickTime;
	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, CharSequence message)
	{
		long time = System.currentTimeMillis();
		if ( time - lastClickTime < 2 * AppContants.TOAST_DOUBLE_TIME_LIMIT) {
			return;
		}
		lastClickTime = time;

		if (isShow)
			 toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
//		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.show();
	}



	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, int message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, CharSequence message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, int message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 自定义显示Toast时间
	 * 
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, CharSequence message, int duration)
	{
		if (isShow)
			Toast.makeText(context, message, duration).show();
	}

	/**
	 * 自定义显示Toast时间
	 * 
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, int message, int duration)
	{
		if (isShow)
			Toast.makeText(context, message, duration).show();
	}



	public static void showCenterImageT(Context context,String text,int ImageResource){
		Toast toast = Toast.makeText(context,
				text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		ImageView imageCodeProject = new ImageView(context);
		imageCodeProject.setImageResource(ImageResource);
		toastView.addView(imageCodeProject, 0);
		toast.show();


	}

	public static void showSnackBar( View view,String msg){
		Snackbar.make(view,msg,Snackbar.LENGTH_SHORT).show();

	}


	private static Toast toast;

	public static void getSingleToast(Context context, String text) {
		if (toast == null) {
			//创建一个空的吐司
			toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
		}
		//给吐司的内容设置自己想要的值
		toast.setText(text.toString());

		toast.show();//弹出吐司

	}



	public static void showBottomToast(Context context,String text){
		Toast toast = Toast.makeText(context,
				text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.BOTTOM, 0, 0);

		toast.show();


	}

}