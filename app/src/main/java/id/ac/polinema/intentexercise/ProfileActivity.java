package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    TextView tvAbout, tvFull, tvEmail, tvHome;
    Button BtnHome;
    Uri avatarUri;
    CircleImageView civAvatar;

    private static final String TAG = RegisterActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvAbout = findViewById(R.id.label_about);
        tvFull = findViewById(R.id.label_fullname);
        civAvatar = findViewById(R.id.image_profile);
        tvEmail = findViewById(R.id.label_email);
        tvHome = findViewById(R.id.label_homepage);
        BtnHome = findViewById(R.id.button_homepage);

        String about = getIntent().getExtras().getString("about");
        String Email = getIntent().getExtras().getString("email");
        final String HomePage = getIntent().getExtras().getString("homepage");
        String FullName = getIntent().getExtras().getString("fullname");

        tvEmail.setText(Email);
        tvAbout.setText(about);
        tvHome.setText(HomePage);
        tvFull.setText(FullName);

        if (getIntent().hasExtra("avatar")){
            avatarUri = getIntent().getParcelableExtra("avatar");
            try {
                Bitmap avatarBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), avatarUri);
                civAvatar.setImageBitmap(avatarBitmap);
            } catch (IOException e){
                Toast.makeText(ProfileActivity.this, "Tidak ada gambar",
                        Toast.LENGTH_LONG).show();
                Log.e(TAG, e.getMessage());
            }
        }

        BtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Objects.equals(HomePage, null)){
                    Intent buka = new Intent(Intent.ACTION_VIEW, Uri.parse("https://"+HomePage));
                    startActivity(buka);
                } else {
                    Toast.makeText(ProfileActivity.this, "Tidak ada homepage", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
