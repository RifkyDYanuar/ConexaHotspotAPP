package com.media.conexahotspot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.media.conexahotspot.Item.PaketItem;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailPaket extends AppCompatActivity {
    TextView descPaket, SyaratKet;
    ImageView toggleKlik, toggleKlik2, Back;
    CardView cardView ;

    private boolean isDescPaket = false;
    private boolean isSyaratKet = false;
    int Height = 450;
    private List<PaketItem> paketItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_paket);
        descPaket = findViewById(R.id.deskripsi_paket);
        SyaratKet = findViewById(R.id.syarat_ketentuan);
        toggleKlik = findViewById(R.id.klik);
        toggleKlik2 = findViewById(R.id.klik2);
        cardView = findViewById(R.id.card2);
        Back = findViewById(R.id.back);
        TextView nama = findViewById(R.id.nama_paket);
        TextView harga_paket = findViewById(R.id.harga);
        TextView p_inet = findViewById(R.id.p_inet);
        TextView showbiayaPemasangan = findViewById(R.id.biaya_pemasangan);
        TextView showTotal = findViewById(R.id.total);
        ImageView imageView = findViewById(R.id.detail_image);



        String namaPaket = getIntent().getStringExtra("nama_paket");
        String pInet = getIntent().getStringExtra("p_inet");
//        int imageResId = getIntent().getIntExtra("imageId", R.drawable.cable2);
        List<String> deskripsiList = getIntent().getStringArrayListExtra("deskripsi");

        Long harga = getIntent().getLongExtra("harga", 0);
        String formatHarga = NumberFormat.getInstance(Locale.getDefault()).format(harga);
        Long biaya = getIntent().getLongExtra("biaya_pemasangan",0);
        String formatBiaya = NumberFormat.getInstance(Locale.getDefault()).format(biaya);

//    Menghitung total ;
        Long total = harga + biaya;
        String formatTotal = NumberFormat.getInstance(Locale.getDefault()).format(total);
        showTotal.setText("Rp " + formatTotal);

        StringBuilder deskripsiBuilder = new StringBuilder();
        if (deskripsiList != null) {
            for (int i = 0; i < deskripsiList.size(); i++) {
                deskripsiBuilder.append(i + 1)
                        .append(". ")
                        .append(deskripsiList.get(i))
                        .append("\n");
            }
        }

        descPaket.setText(deskripsiBuilder.toString());


//        imageView.setImageResource(imageResId);
        nama.setText(namaPaket);
        harga_paket.setText("Rp. " + formatHarga);
        p_inet.setText(pInet);
        showbiayaPemasangan.setText("Rp " + formatBiaya);


        toggleKlik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDescPaket) {
                    descPaket.setVisibility(View.GONE);
                    toggleKlik.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);

                }else {
                    descPaket.setVisibility(View.VISIBLE);

                }
                isDescPaket = !isDescPaket;

                allCardView();

            }
        });
        toggleKlik2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSyaratKet){
                    SyaratKet.setVisibility(View.GONE);
                    toggleKlik2.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);

                }else {
                    SyaratKet.setVisibility(View.VISIBLE);

                }
                isSyaratKet = !isSyaratKet;
                allCardView();

            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        


    }
    private void allCardView() {
        int startHeight = cardView.getHeight();
        int endHeight;
        cardView.measure(
                View.MeasureSpec.makeMeasureSpec(cardView.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        );
        endHeight = cardView.getMeasuredHeight();

        Animasi(cardView, startHeight, endHeight);
        cardView.requestLayout();
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