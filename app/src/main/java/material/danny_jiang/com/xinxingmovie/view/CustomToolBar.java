package material.danny_jiang.com.xinxingmovie.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import material.danny_jiang.com.xinxingmovie.R;

/**
 * Created by axing on 16/6/16.
 */
public class CustomToolBar extends RelativeLayout {


    private final Drawable leftDrawable;
    private final Drawable rightDrawable;
    private final String leftTabText;
    private final String rightTabText;
    private final float tabTextSize;
    private final int tabTextColor;
    private final float imgSize;
    private final Button btnLeft;
    private final Button rightBtn;

    private TextView titleTextView;
    private ImageView leftImage;
    private ImageView rightImage;

    private ImgClickListener imgClickListener;
    private BtnClickListener btnClickListener;

    public void setImgClickListener(ImgClickListener imgClickListener) {
        this.imgClickListener = imgClickListener;
    }

    public void setBtnClickListener(BtnClickListener btnClickListener) {
        this.btnClickListener = btnClickListener;
    }

    public interface ImgClickListener {
        public void onLeftImgClicked(View view);

        public void onRightImgClicked(View view);
    }

    public interface BtnClickListener {
        public void onLeftBtnClicked(View view);

        public void onRightBtnClicked(View view);
    }

    public CustomToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        //通过context获取无类型数组
        TypedArray typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.CustomToolBar);
        //通过无类型数组获取所有的自定义属性的值
        leftTabText = typedArray.getString(R.styleable.CustomToolBar_leftTabText);
        rightTabText = typedArray.getString(R.styleable.CustomToolBar_rightTabText);
        tabTextSize = typedArray.getDimension(R.styleable.CustomToolBar_tabTextSize, 14);
        tabTextColor = typedArray.getColor(R.styleable.CustomToolBar_tabTextColors, Color.WHITE);
        leftDrawable = typedArray.getDrawable(R.styleable.CustomToolBar_leftImg);
        rightDrawable = typedArray.getDrawable(R.styleable.CustomToolBar_rightImg);
        imgSize = typedArray.getDimension(R.styleable.CustomToolBar_imgSize, 48);

        typedArray.recycle();

        /**
         * 分别初始化四个UI控件，并设置相应的属性值
         */
        LinearLayout ll = new LinearLayout(context);
        btnLeft = new Button(context);
        LinearLayout.LayoutParams leftTabParam = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, 36);
        leftTabParam.gravity = Gravity.CENTER;
        btnLeft.setLayoutParams(leftTabParam);
        btnLeft.setText(leftTabText);
        btnLeft.setTextSize(tabTextSize);
        btnLeft.setGravity(Gravity.CENTER);
        setBtnLeftBackground(true);
        ll.addView(btnLeft);

        rightBtn = new Button(context);
        final LinearLayout.LayoutParams rightTabParam = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, 36);
        rightTabParam.gravity = Gravity.CENTER;
        rightBtn.setLayoutParams(rightTabParam);
        rightBtn.setText(rightTabText);
        rightBtn.setTextSize(tabTextSize);
        rightBtn.setGravity(Gravity.CENTER);
        setBtnRightBackground(false);
        ll.addView(rightBtn);

        leftImage = new ImageView(context);
        leftImage.setImageDrawable(leftDrawable);

        rightImage = new ImageView(context);
        rightImage.setImageDrawable(rightDrawable);


        /**
         * 将三个UI控件摆放在正确的位置
         */
        LayoutParams paramCenter = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        paramCenter.addRule(CENTER_IN_PARENT);
        ll.setLayoutParams(paramCenter);
        addView(ll);

        LayoutParams paramLeft = new LayoutParams((int)imgSize, (int)imgSize);
        paramLeft.addRule(ALIGN_PARENT_LEFT);
        paramLeft.addRule(CENTER_VERTICAL);
        leftImage.setLayoutParams(paramLeft);
        addView(leftImage);

        LayoutParams paramRight = new LayoutParams((int)imgSize, (int)imgSize);
        paramRight.addRule(ALIGN_PARENT_RIGHT);
        paramRight.addRule(CENTER_VERTICAL);
        rightImage.setLayoutParams(paramRight);
        addView(rightImage);

        /**
         * 设置两个ImageView的点击事件
         */
        leftImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgClickListener != null) {
                    imgClickListener.onLeftImgClicked(v);
                }
            }
        });

        rightImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgClickListener != null) {
                    imgClickListener.onRightImgClicked(v);
                }
            }
        });

        btnLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtnLeftBackground(true);
                setBtnRightBackground(false);
                if (btnClickListener != null) {
                    btnClickListener.onLeftBtnClicked(v);
                }
            }
        });

        rightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtnLeftBackground(false);
                setBtnRightBackground(true);
                if (btnClickListener != null) {
                    btnClickListener.onRightBtnClicked(v);
                }
            }
        });
    }

    private void setBtnLeftBackground(boolean selected) {
        if (selected) {
            btnLeft.setTextColor(tabTextColor);
            btnLeft.setBackground(getResources().getDrawable(
                    R.drawable.custome_toolbar_leftbtn_selected));
        } else {
            btnLeft.setTextColor(getResources().getColor(android.R.color.white));
            btnLeft.setBackground(getResources().getDrawable(
                    R.drawable.custome_toolbar_leftbtn_unselected));
        }
    }

    private void setBtnRightBackground(boolean selected) {
        if (selected) {
            rightBtn.setTextColor(tabTextColor);
            rightBtn.setBackground(getResources().getDrawable(
                    R.drawable.custome_toolbar_rightbtn_selected));
        } else {
            rightBtn.setTextColor(getResources().getColor(android.R.color.white));
            rightBtn.setBackground(getResources().getDrawable(
                    R.drawable.custome_toolbar_rightbtn_unselected));
        }
    }
}
