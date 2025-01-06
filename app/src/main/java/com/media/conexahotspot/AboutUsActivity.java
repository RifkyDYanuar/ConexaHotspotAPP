package com.media.conexahotspot;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AboutUsActivity extends AppCompatActivity {
    ImageView back;
    ImageView btnConexa, btnDevelop;
    TextView Content;
    LinearLayout namaDevelop;
    private boolean isVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_us);
        back = findViewById(R.id.back);
        btnConexa = findViewById(R.id.btnTampil);
        btnDevelop = findViewById(R.id.btnTampil_development);
        Content = findViewById(R.id.content);
        namaDevelop = findViewById(R.id.layout_development);

        btnConexa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible) {
                    Content.setVisibility(View.GONE);
                } else {
                    Content.setVisibility(View.VISIBLE);
                }
                isVisible = !isVisible;
                showAboutconexa(v);
            }
        });
        btnDevelop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible) {
                    namaDevelop.setVisibility(View.GONE);
                } else {
                    namaDevelop.setVisibility(View.VISIBLE);
                }
                isVisible = !isVisible;
                showAboutDevelop(v);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void showAboutconexa(View view){
        TextView Content = findViewById(R.id.title);
        int startHeight = Content.getHeight();
        int endHeight;
        Content.measure(
                View.MeasureSpec.makeMeasureSpec(Content.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        );
        endHeight = Content.getMeasuredHeight();
        Animasi(Content, startHeight, endHeight);
        Content.requestLayout();
    }
    private void showAboutDevelop(View v){
        LinearLayout namaDevelop = findViewById(R.id.layout_development);
        int startHeight = namaDevelop.getHeight();
        int endHeight;
        namaDevelop.measure(
                View.MeasureSpec.makeMeasureSpec(namaDevelop.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        );
        endHeight = namaDevelop.getMeasuredHeight();
        Animasi(namaDevelop, startHeight, endHeight);
        namaDevelop.requestLayout();
    }
    private void Animasi (final View view, int starHeight, int endHeight){
        ValueAnimator animator = ValueAnimator.ofInt(starHeight,endHeight);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                if (params.height != animatedValue) {
                    params.height = animatedValue;
                    view.setLayoutParams(params);
                }
            }

        });
        animator.start();
    }
}