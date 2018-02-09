# CircularReveal
Android circularReveal Animation

# 简介
CircularReveal是Google在Api版本21实现的一种动画，视觉效果类似于涟漪，主要实现api是通过ViewAnimationUtils的createCircularReveal方法。

# 效果图
![](https://github.com/kuangxiaoguo0123/CircularReveal/blob/master/screenshots/first.gif)

## 方法
```
/**
     * @param view        动画view
     * @param centerX     扩展圆的圆心x坐标
     * @param centerY     扩展圆的圆心y坐标
     * @param startRadius 开始半径
     * @param endRadius   结束半径
     */
    public static Animator createCircularReveal(View view,
                                                int centerX, int centerY, float startRadius, float endRadius) {
        return new RevealAnimator(view, centerX, centerY, startRadius, endRadius);
    }
```
# 原理图
![](https://github.com/kuangxiaoguo0123/CircularReveal/blob/master/screenshots/firstimage.png)
# xml文件
```
<Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="30dp"
        android:onClick="doAnimation"
        android:text="开始动画" />

    <View
        android:id="@+id/animation_view"
        android:layout_width="240dp"
        android:layout_height="160dp"
        android:layout_centerInParent="true"
        android:background="#f00" />
```

# 实现
```
public class MainActivity extends AppCompatActivity {

    View mAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAnimationView = findViewById(R.id.animation_view);
    }

    public void doAnimation(View view) {
        //动画最低支持Api21
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        int width = mAnimationView.getWidth();
        int height = mAnimationView.getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.max(width, height);
        if (mAnimationView.isShown()) {
            //消失动画
            Animator dismissAnimation = ViewAnimationUtils
                    .createCircularReveal(mAnimationView, centerX, centerY, radius, 0);
            dismissAnimation.setDuration(1000);
            dismissAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAnimationView.setVisibility(View.INVISIBLE);
                }
            });
            dismissAnimation.start();
        } else {
            //显示动画
            Animator showAnimation = ViewAnimationUtils
                    .createCircularReveal(mAnimationView, centerX, centerY, 0, radius);
            showAnimation.setDuration(1000);
            mAnimationView.setVisibility(View.VISIBLE);
            showAnimation.start();
        }
    }

}
```
# 修改圆心
通过改变扩展圆的圆心坐标和半径，我们可以实现不同的扩散效果。
![](https://github.com/kuangxiaoguo0123/CircularReveal/blob/master/screenshots/second.gif)
# 分析
上面这种效果我们扩展圆的圆心为(width, 0),扩展半径为对角线的长度。
![](https://github.com/kuangxiaoguo0123/CircularReveal/blob/master/screenshots/second.png)
# 实现
很简单，我们只需要把圆心坐标和半径处理下就OK了。
```
public void doAnimation(View view) {
        //动画最低支持Api21
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        int width = mAnimationView.getWidth();
        int height = mAnimationView.getHeight();
        //指定圆心位于view右上角
        int centerX = width;
        int centerY = 0;
        //半径为对角线长度
        int radius = (int) Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));
        if (mAnimationView.isShown()) {
            //消失动画
            Animator dismissAnimation = ViewAnimationUtils
                    .createCircularReveal(mAnimationView, centerX, centerY, radius, 0);
            dismissAnimation.setDuration(1000);
            dismissAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAnimationView.setVisibility(View.INVISIBLE);
                }
            });
            dismissAnimation.start();
        } else {
            //显示动画
            Animator showAnimation = ViewAnimationUtils
                    .createCircularReveal(mAnimationView, centerX, centerY, 0, radius);
            showAnimation.setDuration(1000);
            mAnimationView.setVisibility(View.VISIBLE);
            showAnimation.start();
        }
    }
```
# Sample source code
[https://github.com/kuangxiaoguo0123/CircularReveal](https://github.com/kuangxiaoguo0123/CircularReveal)

# More information
[http://blog.csdn.net/kuangxiaoguo0123/article/details/79247181](http://blog.csdn.net/kuangxiaoguo0123/article/details/79247181)
