Fashion Detection App
An Android application that uses a machine learning model to classify fashion items into categories such as Topwear, Bottomwear, Shoes, Accessories, and more. The app is powered by TensorFlow Lite for efficient on-device inference and features an intuitive UI for users to capture or upload images.

Features
Real-Time Image Classification:
Capture an image with your device’s camera or select an image from the gallery for classification.
Multiple Fashion Categories:
Classifies items into 45 categories, including Accessories, Shoes, Watches, Bottomwear, and more.
Fast and Accurate:
Utilizes a TensorFlow Lite model optimized for Android devices.
Clean and Modern UI:
Designed with user experience in mind for seamless interaction.

Tech Stack
Android Studio: IDE for app development.
Java: For app logic and TensorFlow Lite integration.
TensorFlow Lite: For on-device machine learning inference.
Custom Dataset: Trained on the Kaggle Fashion Product Images Dataset.
GoogleColab for Training the models with CNN

Screenshot
![alt text](image.png)

How It Works
User Interaction:
Users can either take a picture using their device’s camera or select an image from the gallery.
Model Processing:
The image is resized and normalized for input into the TensorFlow Lite model.
Classification Output:
The app displays the predicted category of the fashion item based on the model's confidence scores.

Setup Instructions
Prerequisites
Android Studio installed on your machine.
Android device or emulator for testing.
Steps
Clone the repository:

bash
Copy code
git clone https://github.com/ismayilmehili/Fashion-Detection-App.git
cd Fashion-Detection-App
Open the project in Android Studio.

Build and run the app on an emulator or connected Android device:

Make sure the device has a working camera and access to a gallery.
Dataset
The model is trained on the Fashion Product Images Dataset from Kaggle. The dataset includes images across 45 categories, allowing the model to classify a wide range of fashion items.

Future Enhancements
Add recommendations for complementary fashion items.
Improve model accuracy with more diverse training data.
Include a history feature to save past classifications.
Multi-language support.
