package com.MusicalSketches;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class DraggableImage extends View {

	private int startX;
	private int startY;
	private Bitmap image;
	private final Paint paint = new Paint();

	public DraggableImage(Context context) {
		super(context);
	}

	public void setImage(Bitmap bm) {
		this.image = bm;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(image, startX, startY, paint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			startX = (int) event.getX();
			startY = (int) event.getY();
			bringToFront();
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			startX = (int) event.getX();
			startY = (int) event.getY();
			invalidate();
			return true;
		} else {
			return super.onTouchEvent(event);
		}
	}
}