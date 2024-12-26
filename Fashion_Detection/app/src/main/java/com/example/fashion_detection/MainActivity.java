package com.example.fashion_detection;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MainActivity extends AppCompatActivity {

    Button cameraButton, galleryButton;
    ImageView imageView;
    TextView resultTextView;
    int imageSize = 128;  // Model input size

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        cameraButton = findViewById(R.id.button);
        galleryButton = findViewById(R.id.button2);
        imageView = findViewById(R.id.imageView);
        resultTextView = findViewById(R.id.result);

        // Handle Camera Button Click
        cameraButton.setOnClickListener(view -> {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 3);  // Camera request code
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);  // Request camera permission
            }
        });

        // Handle Gallery Button Click
        galleryButton.setOnClickListener(view -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, 1);  // Gallery request code
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Bitmap image = null;

            try {
                // Handle camera input
                if (requestCode == 3 && data.getExtras() != null) {
                    image = (Bitmap) data.getExtras().get("data");
                }

                // Handle gallery input
                if (requestCode == 1) {
                    Uri imageUri = data.getData();
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                }

                // Process and classify the image
                if (image != null) {
                    imageView.setImageBitmap(image);
                    image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);  // Resize image for model
                    classifyImage(image);
                } else {
                    Log.e("MainActivity", "No image captured or selected.");
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("MainActivity", "Error loading image.", e);
            }
        } else {
            Log.e("MainActivity", "Operation canceled or no data returned.");
        }
    }

    // Classify the input image using the FashionDetection class
    public void classifyImage(Bitmap image) {
        try {
            // Preprocess the image into ByteBuffer
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            for (int pixel : intValues) {
                byteBuffer.putFloat(((pixel >> 16) & 0xFF) / 255.0f); // Red
                byteBuffer.putFloat(((pixel >> 8) & 0xFF) / 255.0f);  // Green
                byteBuffer.putFloat((pixel & 0xFF) / 255.0f);         // Blue
            }

            // Use the FashionDetection class for classification
            String result = FashionDetection.classifyFashionItem(this, byteBuffer);
            resultTextView.setText("Classified as: " + result);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity", "Error during image classification.", e);
            resultTextView.setText("Error during classification.");
        }
    }
}
