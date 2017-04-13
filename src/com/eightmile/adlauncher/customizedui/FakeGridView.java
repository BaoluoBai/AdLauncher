package com.eightmile.adlauncher.customizedui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class FakeGridView extends LinearLayout implements OnClickListener {
	public interface FakeGridViewClickListener {
		public void	fgvClickListener(Object obj);
	}
	
	private static final int	N_PER_ROW			= 5;

	private Context						ctx			= null;
	private LinearLayout				row			= null;
	private FakeGridViewClickListener	listener	= null;
	private LinearLayout.LayoutParams	layparams	= new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f);

	public FakeGridView(Context context) {
		super(context);
		init(context);
	}
	public FakeGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public FakeGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		ctx = context;
		setOrientation(VERTICAL);
	}

	public void	setClickListener(FakeGridViewClickListener l) {
		synchronized (this) {
			listener = l;
		}
	}

	public void add(Drawable icon, Object obj) {
		if (null == row) {
			row = new LinearLayout(ctx);
			row.setOrientation(HORIZONTAL);
			for (int i=0; i<N_PER_ROW; i++) {
				ImageButton btn = new ImageButton(ctx);
				btn.setBackgroundColor(0x00FFFFFF);
				btn.setVisibility(INVISIBLE);
				btn.setOnClickListener(this);
				row.addView(btn, layparams);
			}
			addView(row);
		}
		for (int i=0; i<N_PER_ROW; i++) {
			ImageButton btn = (ImageButton)row.getChildAt(i);
			if (INVISIBLE != btn.getVisibility()) {
				continue;
			}
			btn.setTag(obj);
			btn.setImageDrawable(icon);
			btn.setVisibility(VISIBLE);
			return;
		}
		row = null;
		add(icon, obj);
	}

	@Override
	public void onClick(View arg0) {
		synchronized (this) {
			if (null != listener) {
				listener.fgvClickListener(arg0.getTag());
			}
		}
	}
}
