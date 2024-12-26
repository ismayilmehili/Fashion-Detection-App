package com.example.fashion_detection;

import android.content.Context;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
import com.example.fashion_detection.ml.FashionClassifier;

import java.io.IOException;
import java.nio.ByteBuffer;

public class FashionDetection {

    public static String classifyFashionItem(Context context, ByteBuffer byteBuffer) {
        try {
            // Load the TensorFlow Lite model
            FashionClassifier model = FashionClassifier.newInstance(context);

            // Prepare the input tensor
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 128, 128, 3}, DataType.FLOAT32);
            inputFeature0.loadBuffer(byteBuffer);

            // Run inference
            FashionClassifier.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            // Extract confidence scores
            float[] confidences = outputFeature0.getFloatArray();
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }

            // Class labels (ensure these match your dataset labels)
            String[] classNames = {
                    "Accessories", "Apparel Set", "Bags", "Bath and Body", "Beauty Accessories",
                    "Belts", "Bottomwear", "Cufflinks", "Dress", "Eyes", "Eyewear", "Flip Flops",
                    "Fragrance", "Free Gifts", "Gloves", "Hair", "Headwear", "Home Furnishing",
                    "Innerwear", "Jewellery", "Lips", "Loungewear and Nightwear", "Makeup",
                    "Mufflers", "Nails", "Perfumes", "Sandal", "Saree", "Scarves",
                    "Shoe Accessories", "Shoes", "Skin", "Skin Care", "Socks", "Sports Accessories",
                    "Sports Equipment", "Stoles", "Ties", "Topwear", "Umbrellas", "Vouchers",
                    "Wallets", "Watches", "Water Bottle", "Wristbands"
            };

            // Release model resources
            model.close();

            // Return the predicted class name
            return classNames[maxPos];

        } catch (IOException e) {
            e.printStackTrace();
            return "Error loading model.";
        }
    }
}
