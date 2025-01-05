package com.media.conexahotspot;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.media.conexahotspot.Item.PaketItem;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
        Button pendaftaran = findViewById(R.id.btn_daftar);
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

        pendaftaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPendaftaran();
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

    private void showPendaftaran(){
        View view = LayoutInflater.from(DetailPaket.this).inflate(R.layout.pendaftaran_pemasangan,null);
        TextInputEditText Alamat = view.findViewById(R.id.alamat);
        TextInputEditText Tanggal = view.findViewById(R.id.tanggal_pemasangan);
        TextInputEditText NoTelepon = view.findViewById(R.id.telepon);
        TextInputEditText Nama = view.findViewById(R.id.name);
        Button daftar = view.findViewById(R.id.submit);
        ProgressBar loading = view.findViewById(R.id.progress_bar);
        loading.setVisibility(View.GONE);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserId = Nama.getText().toString();
                String alamat = Alamat.getText().toString();
                String noTelepon = NoTelepon.getText().toString();
                String tanggalPemasangan = Tanggal.getText().toString();
                String paketId = "";
                loading.setVisibility(View.VISIBLE);

                if(UserId.isEmpty() || alamat.isEmpty() || noTelepon.isEmpty() || tanggalPemasangan.isEmpty()){
                    if (UserId.isEmpty())Nama.setError("Nama tidak boleh kosong");
                    if (alamat.isEmpty())Alamat.setError("Alamat tidak boleh kosong");
                    if (noTelepon.isEmpty())NoTelepon.setError("Nomor Telepon tidak boleh kosong");
                    if (tanggalPemasangan.isEmpty())Tanggal.setError("Tanggal Pemasangan tidak boleh kosong");
                    loading.setVisibility(View.GONE);
                } else if (UserId.isEmpty() && alamat.isEmpty() && noTelepon.isEmpty() && tanggalPemasangan.isEmpty()) {
                    loading.setVisibility(View.GONE);
                    Toast.makeText(DetailPaket.this, "Semua kolom wajib di isi!", Toast.LENGTH_SHORT).show();
                }else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loading.setVisibility(View.GONE);
                            view.setVisibility(View.GONE);
                            addTransaction( UserId,alamat,noTelepon, tanggalPemasangan, paketId);
                            KonfirmasiPembayaran();
                        }

                    }, 5000);
                }


            }
        });
        Tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTanggal(v);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(DetailPaket.this);
        final AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();


    }

    private void setTanggal (View v){
        TextInputEditText Tanggal = v.findViewById(R.id.tanggal_pemasangan);
        final Calendar calendar = Calendar.getInstance();
        int tanggal = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int tanggal) {
                Tanggal.setText(tanggal + "-" + (month + 1) + "-" + year);
            }
        },tanggal, month, year);
        datePickerDialog.show();
    }

    private void KonfirmasiPembayaran(){
        View view = LayoutInflater.from(DetailPaket.this).inflate(R.layout.konfirmasi_pembayaran,null);
        TextView Paket = view.findViewById(R.id.show_paket);
        TextView Total = view.findViewById(R.id.total_akumulasi);
        ImageView copy = view.findViewById(R.id.copy);
        TextView noRekening = view.findViewById(R.id.no_rekening);
        Button Konfirmasi = view.findViewById(R.id.konfirmasi_whatapp);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String Rekening = noRekening.getText().toString();
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData data = ClipData.newPlainText("text", Rekening);
                clipboardManager.setPrimaryClip(data);
                Toast.makeText(getApplicationContext(), "Berhasil Disalin", Toast.LENGTH_SHORT).show();


            }
        });

//
        Konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = getIntent();
                    String Paket = intent.getStringExtra("nama_paket");
                    ArrayList<String[]>customerData = new ArrayList<>();
                    customerData.add(new String[]{"Nama", getIntent().getStringExtra("nama")});
                    customerData.add(new String[]{"Nomor HP", getIntent().getStringExtra("notelp")});
                    customerData.add(new String[]{"Alamat", getIntent().getStringExtra("address")});
                    customerData.add(new String[]{"Paket", Paket});
                    customerData.add(new String[]{"Jadwal Pemasangan", getIntent().getStringExtra("tanggal_pemasangan")});
                    String headerPesan = "Hallo admin conexa, tiket pemasangan internet baru telah muncul!\n\n" +
                            "Berikut Rincian informasi data pelanggan baru:\n";

                    StringBuilder builder = new StringBuilder(headerPesan);
                    int maxLength = 0;
                    for (String[] data : customerData) {
                        if (data[0].length() > maxLength) {
                            maxLength = data[0].length();

                        }
                    }
                    for (String[] data : customerData) {
                        String key = String.format("%-" + maxLength + "s", data[0]); // Ratakan key
                        builder.append(key).append(" : ").append(data[1]).append("\n");
                    }
                    String number = "+62 82116747973";

                    String Message = builder.toString();
                    String url = "https://api.whatsapp.com/send?phone="+number+ "&text=" + Uri.encode("```" + Message + "```");
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    i.setPackage("com.whatsapp");
                    startActivity(i);

                }

        });
        String namaPaket = getIntent().getStringExtra("nama_paket");
        Long harga = getIntent().getLongExtra("harga", 0);
        String formatHarga = NumberFormat.getInstance(Locale.getDefault()).format(harga);
        Long biaya = getIntent().getLongExtra("biaya_pemasangan",0);
        String formatBiaya = NumberFormat.getInstance(Locale.getDefault()).format(biaya);
        Long total = harga + biaya;
        String formatTotal = NumberFormat.getInstance(Locale.getDefault()).format(total);
         Paket.setText(namaPaket);
         Total.setText(getString(R.string.total_pembayaran)+ " Rp " + formatTotal);
        Button Back = view.findViewById(R.id.home);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(DetailPaket.this);
        final AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    private void addTransaction(String userId, String alamat, String noTelepon, String tanggalPemasangan, String paketId) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String kodeTransaksi = database.child("Transactions").push().getKey();
        String tanggalTransaksi = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        database.child("paket_item").child(paketId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String namaPaket = getIntent().getStringExtra("nama_paket");
                    Long harga = getIntent().getLongExtra("harga", 0);
                    Long biaya = getIntent().getLongExtra("biaya_pemasangan",0);
                    Long total = (harga != null ? harga : 0) + (biaya != null ? biaya : 0);
                    Map<String, Object> transaksi = new HashMap<>();
                    transaksi.put("kode_transaksi", kodeTransaksi);
                    transaksi.put("name", userId);
                    transaksi.put("notelp", noTelepon);
                    transaksi.put("address", alamat);
                    transaksi.put("tanggal_transaksi", tanggalTransaksi);
                    transaksi.put("tanggal_pemasangan", tanggalPemasangan);
                    transaksi.put("biaya_pemasangan", biaya);
                    transaksi.put("nama_paket", namaPaket);
                    transaksi.put("harga", harga);
                    transaksi.put("total", total);
                    transaksi.put("status", "success");
                    database.child("Transactions").child(kodeTransaksi).setValue(transaksi)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    FirebaseAuth auth = FirebaseAuth.getInstance();
                                    String userID = auth.getCurrentUser().getUid();
                                    database.child("Users").child(userID).child("Transactions").child(kodeTransaksi).setValue(transaksi);

                                    getIntent().putExtra("tanggal_pemasangan", tanggalPemasangan);
                                    getIntent().putExtra("nama", userId);
                                    getIntent().putExtra("notelp", noTelepon);
                                    getIntent().putExtra("address", alamat);

                                    Toast.makeText(getApplicationContext(), "Pendaftaran Paket Berhasil", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Gagal menyimpan transaksi.", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "Paket tidak ditemukan!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}