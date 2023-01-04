package com.example.mobileproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.example.mobileproject.databinding.ActivityCameraBinding;

public class ActivityCamera extends AppCompatActivity {

    ActivityCameraBinding binding;
    ActivityResultLauncher<Intent> launcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        binding = ActivityCameraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnTakePic.setOnClickListener(view->launcher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)));
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::fnAfterCam);

    }

    private void fnAfterCam(ActivityResult result){
        Bitmap bp = (Bitmap) result.getData().getExtras().get("data");
        binding.imgVwPic.setImageBitmap(bp);

    }

    public void fnTakePic(View vw)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        launcher.launch(intent);
    }
}