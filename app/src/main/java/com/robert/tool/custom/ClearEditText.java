package com.robert.tool.custom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.robert.tool.R;

@SuppressWarnings("ALL")
public class ClearEditText extends AppCompatEditText implements View.OnTouchListener, View.OnFocusChangeListener, TextWatcher {

	public interface Callback{
		public void onBtnClearClick();
	}

	private Callback mCallback = null;

	private Drawable mClearTextIcon;

	// private Drawable mOKTextIcon;

	private OnFocusChangeListener mOnFocusChangeListener;

	private OnTouchListener mOnTouchListener;

	private View line;

	private OnEditChange			onEditChange;

	private boolean					isVisible;

	private int						defaultLen	= 11;

	public ClearEditText(final Context context) {
		super(context);
		init(context);
	}

	public ClearEditText(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ClearEditText(final Context context, final AttributeSet attrs, final int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(final Context context) {
		// final Drawable drawableRight = ContextCompat.getDrawable(context,
		// R.mipmap.ic_ok);
		// final Drawable wrappedDrawableRight =
		// DrawableCompat.wrap(drawableRight);
		// mOKTextIcon = wrappedDrawableRight;
		// mOKTextIcon.setBounds(0, 0, mOKTextIcon.getIntrinsicHeight(),
		// mOKTextIcon.getIntrinsicHeight());

		final Drawable drawable = ContextCompat.getDrawable(context, R.mipmap.delete);
		final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
		mClearTextIcon = wrappedDrawable;
		mClearTextIcon.setBounds(0, 0, mClearTextIcon.getIntrinsicHeight(), mClearTextIcon.getIntrinsicHeight());

		setClearIconVisible(0);
		super.setOnTouchListener(this);
		super.setOnFocusChangeListener(this);
		addTextChangedListener(this);
	}

	public void setCallback(Callback callback){
		mCallback = callback;
	}

	@Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
		mOnFocusChangeListener = l;
	}

	@Override
    public void setOnTouchListener(OnTouchListener l) {
		mOnTouchListener = l;
	}

	@Override
    public void onFocusChange(View v, boolean hasFocus) {
		if (hasFocus) {
			setClearIconVisible(getText().length());
			if (line != null) {
				line.setSelected(true);
			}
		} else {
			// if (line != null) {
			// line.setSelected(false);
			// }
			// setClearIconVisible(0);
		}
		if (mOnFocusChangeListener != null) {
			mOnFocusChangeListener.onFocusChange(v, hasFocus);
		}
	}

	public void setOnEditChange(OnEditChange onEditChange) {
		this.onEditChange = onEditChange;
	}

	@Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
		final int x = (int) motionEvent.getX();
		// if (mOKTextIcon.isVisible() && x > getWidth() - getPaddingRight() -
		// mOKTextIcon.getIntrinsicWidth()) {
		// return true;
		// } else
		if (mClearTextIcon.isVisible() && x > getWidth() - getPaddingRight() - mClearTextIcon.getIntrinsicWidth()) {
			if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				setError(null);
				setText("");
				setClearIconVisible(0);
				if(null != mCallback){
					mCallback.onBtnClearClick();
				}
			}
			return true;
		}
		return mOnTouchListener != null && mOnTouchListener.onTouch(view, motionEvent);
	}

	@Override
    public final void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
		if (isFocused()) {
			setClearIconVisible(text.length());
		}
	}

	@Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
    public void afterTextChanged(Editable s) {

	}

	public void setDefaultLen(int len) {
		this.defaultLen = len;
	}

	private void setClearIconVisible(int len) {
		if (!isEnabled()) {
			mClearTextIcon.setVisible(false, false);
//			mOKTextIcon.setVisible(false, false);
			return;
		}
		boolean visible = len > 0;

		if (len == defaultLen) {
			mClearTextIcon.setVisible(visible, false);
//			mOKTextIcon.setVisible(true, false);
			final Drawable[] compoundDrawables = getCompoundDrawables();
			setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], mClearTextIcon, compoundDrawables[3]);
		} else {
//			mOKTextIcon.setVisible(false, false);
			mClearTextIcon.setVisible(visible, false);
			final Drawable[] compoundDrawables = getCompoundDrawables();
			setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], visible ? mClearTextIcon : null, compoundDrawables[3]);
		}

		if (onEditChange != null) {
			onEditChange.onVisible(visible);
			isVisible = visible;
		}

	}

	public void setLine(View line) {
		this.line = line;
	}

	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * 编辑变化
	 */
	public interface OnEditChange {

		/**
		 * 显示回调
		 * 
		 * @param bool
		 *            是否显示
		 */
		void onVisible(boolean bool);

	}
}
