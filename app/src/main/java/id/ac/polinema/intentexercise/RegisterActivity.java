package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    EditText etFuName, etEmail, etPass, etConPass, etHome, etAbout;
    Button BtnOK;
    ImageView avImage;
    CircleImageView civAvatar;
    Uri AvatarU;

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFuName = findViewById(R.id.text_fullname);
        etEmail = findViewById(R.id.text_email);
        etPass = findViewById(R.id.text_password);
        etConPass = findViewById(R.id.text_confirm_password);
        etHome = findViewById(R.id.text_homepage);
        etAbout = findViewById(R.id.text_about);
        BtnOK = findViewById(R.id.button_ok);
        avImage = findViewById(R.id.imageView);
        civAvatar = findViewById(R.id.image_profile);

        BtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String FullName = etFuName.getText().toString();
                String Email = etEmail.getText().toString();
                String home = etHome.getText().toString();
                String about = etAbout.getText().toString();
                String Pass = etPass.getText().toString();
                String ConPass = etConPass.getText().toString();

                if (!Objects.equals(Pass, ConPass)){
                    Toast.makeText(RegisterActivity.this, "Password tidak sama", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent pindah = new Intent(RegisterActivity.this, ProfileActivity.class);

                pindah.putExtra("fullname", FullName);
                pindah.putExtra("email", Email);
                pindah.putExtra("homepage", home);
                pindah.putExtra("about", about);

                if (!Objects.equals(AvatarU, null)){
                    pindah.putExtra("avatar", AvatarU);
                }

                startActivity(pindah);


            }
        });

        avImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GALLERY_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED){
            Toast.makeText(RegisterActivity.this, "Batal tambah gambar",
                    Toast.LENGTH_LONG).show();
            return;
    }
        else if (requestCode == GALLERY_REQUEST_CODE){
            if (!Objects.equals(data, null)){
                try {
                    AvatarU = data.getData();
                    Bitmap avatarBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), AvatarU);
                    civAvatar.setImageBitmap(avatarBitmap);
                } catch (IOException e){
                    Toast.makeText(RegisterActivity.this, "Gambar tidak ada",
                            Toast.LENGTH_LONG).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
}
}
