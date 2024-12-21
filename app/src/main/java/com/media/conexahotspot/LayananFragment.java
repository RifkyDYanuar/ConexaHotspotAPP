package com.media.conexahotspot;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LayananFragment extends Fragment {

    private boolean isDesc = false;

    public LayananFragment() {
    }


    public static LayananFragment newInstance() {
        LayananFragment fragment = new LayananFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layanan, container, false);
        TextView DescInetLate = view.findViewById(R.id.dekripsi_inet_lambat);
        Button SpeedTest = view.findViewById(R.id.speed_test);
        TextView DescNotConn = view.findViewById(R.id.dekrips_not_koneksi);
        TextView DescLampuLos = view.findViewById(R.id.deskripsi_lampu_los);
        CardView cardView1 = view.findViewById(R.id.los_internet);
        CardView cardView2 = view.findViewById(R.id.not_koneksi);
        CardView cardView3 = view.findViewById(R.id.lampu_merah);
        LinearLayout btnInetLate = view.findViewById(R.id.btn_1);
        LinearLayout btnNotConn = view.findViewById(R.id.btn_2);
        LinearLayout btnLampuLos = view.findViewById(R.id.btn_3);

        btnInetLate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDesc) {
                    DescInetLate.setVisibility(View.GONE);
                    SpeedTest.setVisibility(View.GONE);
                }else {
                    DescInetLate.setVisibility(View.VISIBLE);
                    SpeedTest.setVisibility(View.VISIBLE);
                }
                isDesc = !isDesc;
                CardView1(view);

            }

        });
        btnNotConn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isDesc) {
                    DescNotConn.setVisibility(View.GONE);
                }else {
                    DescNotConn.setVisibility(View.VISIBLE);
                }
                isDesc = !isDesc;
                CardView2(view);

            }


        });
        btnLampuLos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDesc) {
                    DescLampuLos.setVisibility(View.GONE);
                }else {
                    DescLampuLos.setVisibility(View.VISIBLE);
                }
                isDesc = !isDesc;
                CardView3(view);

            }
        });
        SpeedTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Url = "https://www.speedtest.net/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(Url));
                startActivity(intent);
            }
        });
        return view;
    }


    private void CardView1(View view) {
        CardView cardView1 = view.findViewById(R.id.los_internet);
        int startHeight = cardView1.getHeight();
        int endHeight;
        cardView1.measure(
                View.MeasureSpec.makeMeasureSpec(cardView1.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        );
        endHeight = cardView1.getMeasuredHeight();

        Animasi(cardView1, startHeight, endHeight);
        cardView1.requestLayout();
    }
    private void CardView2(View view) {
        CardView cardView2 = view.findViewById(R.id.not_koneksi);
        int startHeight = cardView2.getHeight();
        int endHeight;
        cardView2.measure(
                View.MeasureSpec.makeMeasureSpec(cardView2.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        );
        endHeight = cardView2.getMeasuredHeight();

        Animasi(cardView2, startHeight, endHeight);
        cardView2.requestLayout();
    }
    private void CardView3(View view) {
        CardView cardView3 = view.findViewById(R.id.lampu_merah);
        int startHeight = cardView3.getHeight();
        int endHeight;
        cardView3.measure(
                View.MeasureSpec.makeMeasureSpec(cardView3.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        );
        endHeight = cardView3.getMeasuredHeight();

        Animasi(cardView3, startHeight, endHeight);
        cardView3.requestLayout();
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