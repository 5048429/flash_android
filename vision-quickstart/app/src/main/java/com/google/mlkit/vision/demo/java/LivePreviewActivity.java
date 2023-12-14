/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.mlkit.vision.demo.java;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback;
import androidx.core.content.ContextCompat;
import com.google.android.gms.common.annotation.KeepName;
import com.google.mlkit.common.model.LocalModel;
import com.google.mlkit.vision.demo.CameraSource;
import com.google.mlkit.vision.demo.CameraSourcePreview;
import com.google.mlkit.vision.demo.GraphicOverlay;
import com.google.mlkit.vision.demo.R;
import com.google.mlkit.vision.demo.java.barcodescanner.BarcodeScannerProcessor;
import com.google.mlkit.vision.demo.java.facedetector.FaceDetectorProcessor;
import com.google.mlkit.vision.demo.java.labeldetector.LabelDetectorProcessor;
import com.google.mlkit.vision.demo.java.objectdetector.ObjectDetectorProcessor;
import com.google.mlkit.vision.demo.java.posedetector.PoseDetectorProcessor;
import com.google.mlkit.vision.demo.java.segmenter.SegmenterProcessor;
import com.google.mlkit.vision.demo.java.textdetector.TextRecognitionProcessor;
import com.google.mlkit.vision.demo.preference.PreferenceUtils;
import com.google.mlkit.vision.demo.preference.SettingsActivity;
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions;
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions;
import com.google.mlkit.vision.pose.PoseDetectorOptionsBase;
import com.google.mlkit.vision.text.chinese.ChineseTextRecognizerOptions;
import com.google.mlkit.vision.text.devanagari.DevanagariTextRecognizerOptions;
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions;
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Live preview demo for ML Kit APIs. */
@KeepName
public final class LivePreviewActivity extends AppCompatActivity
        implements OnRequestPermissionsResultCallback,
        OnItemSelectedListener,
        CompoundButton.OnCheckedChangeListener {
  private static final String OBJECT_DETECTION = "目标检测";
  private static final String POSE_DETECTION1= "深蹲";

  private static final String POSE_DETECTION2="引体";

  private static final String POSE_DETECTION3="肩推";


  private static final String TAG = "LivePreviewActivity";
  private static final int PERMISSION_REQUESTS = 1;

  private CameraSource cameraSource = null;
  private CameraSourcePreview preview;
  private GraphicOverlay graphicOverlay;
  private String selectedModel = OBJECT_DETECTION;
  private TextView lastClickedTextView; // Add this line as a class variable
  private PoseDetectorOptionsBase createPoseOptionsForAction1() {
    // Customize PoseDetectorOptionsBase for Action 1
    return PreferenceUtils.getPoseDetectorOptionsForLivePreview(this);
  }

  private PoseDetectorOptionsBase createPoseOptionsForAction2() {
    // Customize PoseDetectorOptionsBase for Action 2
    return PreferenceUtils.getPoseDetectorOptionsForLivePreview(this);
  }
  private void setupPoseProcessor(
          PoseDetectorOptionsBase poseDetectorOptions, int actionNumber) {
    Log.i(TAG, "Using Pose Detector for Action " + actionNumber + " with options " + poseDetectorOptions);
    boolean shouldShowInFrameLikelihood =
            PreferenceUtils.shouldShowPoseDetectionInFrameLikelihoodLivePreview(this);
    boolean visualizeZ = PreferenceUtils.shouldPoseDetectionVisualizeZ(this);
    boolean rescaleZ = PreferenceUtils.shouldPoseDetectionRescaleZForVisualization(this);
    boolean runClassification = PreferenceUtils.shouldPoseDetectionRunClassification(this);
    cameraSource.setMachineLearningFrameProcessor(
            new PoseDetectorProcessor(
                    this,
                    poseDetectorOptions,
                    "aaa",
                    shouldShowInFrameLikelihood,
                    visualizeZ,
                    rescaleZ,
                    runClassification,
                    /* isStreamMode = */ true));
  }
  private PoseDetectorOptionsBase createPoseOptionsForAction3() {
    // Customize PoseDetectorOptionsBase for Action 3
    return PreferenceUtils.getPoseDetectorOptionsForLivePreview(this);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate");

    setContentView(R.layout.activity_vision_live_preview);

    preview = findViewById(R.id.preview_view);
    if (preview == null) {
      Log.d(TAG, "Preview is null");
    }
    graphicOverlay = findViewById(R.id.graphic_overlay);
    if (graphicOverlay == null) {
      Log.d(TAG, "graphicOverlay is null");
    }

    TextView objectDetectionTextView = findViewById(R.id.textObjectDetection);
    TextView poseDetectionTextView1 = findViewById(R.id.shendun);
    TextView poseDetectionTextView2 = findViewById(R.id.yingti);
    TextView poseDetectionTextView3 = findViewById(R.id.jiantui);
    objectDetectionTextView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        updateTextViewBackgroundColor(objectDetectionTextView);
        selectedModel = OBJECT_DETECTION;
        Log.d(TAG, "选择的模型：" + selectedModel);
        preview.stop();
        if (allPermissionsGranted()) {
          createCameraSource(selectedModel);
          startCameraSource();
        } else {
          getRuntimePermissions();
        }
      }
    });
    poseDetectionTextView1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        updateTextViewBackgroundColor(poseDetectionTextView1);
        selectedModel = POSE_DETECTION1;
        Log.d(TAG, "选择的模型：" + selectedModel);
        preview.stop();
        if (allPermissionsGranted()) {
          createCameraSource(selectedModel);
          startCameraSource();
        } else {
          getRuntimePermissions();
        }
      }
    });


    poseDetectionTextView2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        updateTextViewBackgroundColor(poseDetectionTextView2);
        selectedModel = POSE_DETECTION2;
        Log.d(TAG, "选择的模型：" + selectedModel);
        preview.stop();
        if (allPermissionsGranted()) {
          createCameraSource(selectedModel);
          startCameraSource();
        } else {
          getRuntimePermissions();
        }
      }
    });


    poseDetectionTextView3.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        updateTextViewBackgroundColor(poseDetectionTextView3);
        selectedModel = POSE_DETECTION3;
        Log.d(TAG, "选择的模型：" + selectedModel);
        preview.stop();
        if (allPermissionsGranted()) {
          createCameraSource(selectedModel);
          startCameraSource();
        } else {
          getRuntimePermissions();
        }
      }
    });

    ToggleButton facingSwitch = findViewById(R.id.facing_switch);
    facingSwitch.setOnCheckedChangeListener(this);

    ImageView settingsButton = findViewById(R.id.settings_button);
    settingsButton.setOnClickListener(
            v -> {
              Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
              intent.putExtra(
                      SettingsActivity.EXTRA_LAUNCH_SOURCE, SettingsActivity.LaunchSource.LIVE_PREVIEW);
              startActivity(intent);
            });

    if (allPermissionsGranted()) {
      createCameraSource(selectedModel);
    } else {
      getRuntimePermissions();
    }
  }
  // Add this method to update the background color of the clicked TextView
  private void updateTextViewBackgroundColor(TextView textView) {
    // Reset background color for the last clicked TextView
    if (lastClickedTextView != null) {
      lastClickedTextView.setBackgroundColor(Color.TRANSPARENT);  // or set to the original color
    }

    // Set the background color for the current clicked TextView
    textView.setBackgroundColor(Color.parseColor("#2589FF"));

    // Update the last clicked TextView
    lastClickedTextView = textView;
  }

  @Override
  public synchronized void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    // An item was selected. You can retrieve the selected item using
    // parent.getItemAtPosition(pos)
    selectedModel = parent.getItemAtPosition(pos).toString();
    Log.d(TAG, "Selected model: " + selectedModel);
    preview.stop();
    if (allPermissionsGranted()) {
      createCameraSource(selectedModel);
      startCameraSource();
    } else {
      getRuntimePermissions();
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {
    // Do nothing.
  }

  @Override
  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    Log.d(TAG, "Set facing");
    if (cameraSource != null) {
      if (isChecked) {
        cameraSource.setFacing(CameraSource.CAMERA_FACING_FRONT);
      } else {
        cameraSource.setFacing(CameraSource.CAMERA_FACING_BACK);
      }
    }
    preview.stop();
    startCameraSource();
  }

  private void createCameraSource(String model) {
    // If there's no existing cameraSource, create one.
    if (cameraSource == null) {
      cameraSource = new CameraSource(this, graphicOverlay);
    }

    try {
      switch (model) {
        case OBJECT_DETECTION:
          Log.i(TAG, "Using Object Detector Processor");
          ObjectDetectorOptions objectDetectorOptions =
                  PreferenceUtils.getObjectDetectorOptionsForLivePreview(this);
          cameraSource.setMachineLearningFrameProcessor(
                  new ObjectDetectorProcessor(this, objectDetectorOptions));
          break;

        case POSE_DETECTION1:
          PoseDetectorOptionsBase poseDetectorOptions1 = createPoseOptionsForAction1();
          setupPoseProcessor(poseDetectorOptions1, 1);
          Log.i(TAG, "Using Pose Detector with options " + poseDetectorOptions1);


          boolean shouldShowInFrameLikelihood1 = PreferenceUtils.shouldShowPoseDetectionInFrameLikelihoodLivePreview(this);
          boolean visualizeZ1 = PreferenceUtils.shouldPoseDetectionVisualizeZ(this);
          boolean rescaleZ1 = PreferenceUtils.shouldPoseDetectionRescaleZForVisualization(this);
          boolean runClassification1 = PreferenceUtils.shouldPoseDetectionRunClassification(this);
          cameraSource.setMachineLearningFrameProcessor(
                  new PoseDetectorProcessor(
                          this,
                          poseDetectorOptions1,
                          "shendun",
                          shouldShowInFrameLikelihood1,
                          visualizeZ1,
                          rescaleZ1,
                          runClassification1,
                          /* isStreamMode = */ true));

        case POSE_DETECTION2:
          PoseDetectorOptionsBase poseDetectorOptions2 = createPoseOptionsForAction1();
          setupPoseProcessor(poseDetectorOptions2, 1);
          Log.i(TAG, "Using Pose Detector with options " + poseDetectorOptions2);


          boolean shouldShowInFrameLikelihood2 = PreferenceUtils.shouldShowPoseDetectionInFrameLikelihoodLivePreview(this);
          boolean visualizeZ2 = PreferenceUtils.shouldPoseDetectionVisualizeZ(this);
          boolean rescaleZ2 = PreferenceUtils.shouldPoseDetectionRescaleZForVisualization(this);
          boolean runClassification2 = PreferenceUtils.shouldPoseDetectionRunClassification(this);
          cameraSource.setMachineLearningFrameProcessor(
                  new PoseDetectorProcessor(
                          this,
                          poseDetectorOptions2,
                          "yingti",
                          shouldShowInFrameLikelihood2,
                          visualizeZ2,
                          rescaleZ2,
                          runClassification2,
                          /* isStreamMode = */ true));
        case POSE_DETECTION3:
          PoseDetectorOptionsBase poseDetectorOptions3 = createPoseOptionsForAction1();
          setupPoseProcessor(poseDetectorOptions3, 1);
          Log.i(TAG, "Using Pose Detector with options " + poseDetectorOptions3);


          boolean shouldShowInFrameLikelihood3 = PreferenceUtils.shouldShowPoseDetectionInFrameLikelihoodLivePreview(this);
          boolean visualizeZ3 = PreferenceUtils.shouldPoseDetectionVisualizeZ(this);
          boolean rescaleZ3 = PreferenceUtils.shouldPoseDetectionRescaleZForVisualization(this);
          boolean runClassification3 = PreferenceUtils.shouldPoseDetectionRunClassification(this);
          cameraSource.setMachineLearningFrameProcessor(
                  new PoseDetectorProcessor(
                          this,
                          poseDetectorOptions3,
                          "jiantui",
                          shouldShowInFrameLikelihood3,
                          visualizeZ3,
                          rescaleZ3,
                          runClassification3,
                          /* isStreamMode = */ true));
          break;
        default:
          Log.e(TAG, "Unknown model: " + model);
      }
    } catch (RuntimeException e) {
      Log.e(TAG, "Can not create image processor: " + model, e);
      Toast.makeText(
                      getApplicationContext(),
                      "Can not create image processor: " + e.getMessage(),
                      Toast.LENGTH_LONG)
              .show();
    }
  }

  /**
   * Starts or restarts the camera source, if it exists. If the camera source doesn't exist yet
   * (e.g., because onResume was called before the camera source was created), this will be called
   * again when the camera source is created.
   */
  private void startCameraSource() {
    if (cameraSource != null) {
      try {
        if (preview == null) {
          Log.d(TAG, "resume: Preview is null");
        }
        if (graphicOverlay == null) {
          Log.d(TAG, "resume: graphOverlay is null");
        }
        preview.start(cameraSource, graphicOverlay);
      } catch (IOException e) {
        Log.e(TAG, "Unable to start camera source.", e);
        cameraSource.release();
        cameraSource = null;
      }
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.d(TAG, "onResume");
    createCameraSource(selectedModel);
    startCameraSource();
  }

  /** Stops the camera. */
  @Override
  protected void onPause() {
    super.onPause();
    preview.stop();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (cameraSource != null) {
      cameraSource.release();
    }
  }

  private String[] getRequiredPermissions() {
    try {
      PackageInfo info =
              this.getPackageManager()
                      .getPackageInfo(this.getPackageName(), PackageManager.GET_PERMISSIONS);
      String[] ps = info.requestedPermissions;
      if (ps != null && ps.length > 0) {
        return ps;
      } else {
        return new String[0];
      }
    } catch (Exception e) {
      return new String[0];
    }
  }

  private boolean allPermissionsGranted() {
    for (String permission : getRequiredPermissions()) {
      if (!isPermissionGranted(this, permission)) {
        return false;
      }
    }
    return true;
  }

  private void getRuntimePermissions() {
    List<String> allNeededPermissions = new ArrayList<>();
    for (String permission : getRequiredPermissions()) {
      if (!isPermissionGranted(this, permission)) {
        allNeededPermissions.add(permission);
      }
    }

    if (!allNeededPermissions.isEmpty()) {
      ActivityCompat.requestPermissions(
              this, allNeededPermissions.toArray(new String[0]), PERMISSION_REQUESTS);
    }
  }

  @Override
  public void onRequestPermissionsResult(
          int requestCode, String[] permissions, int[] grantResults) {
    Log.i(TAG, "Permission granted!");
    if (allPermissionsGranted()) {
      createCameraSource(selectedModel);
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  private static boolean isPermissionGranted(Context context, String permission) {
    if (ContextCompat.checkSelfPermission(context, permission)
            == PackageManager.PERMISSION_GRANTED) {
      Log.i(TAG, "Permission granted: " + permission);
      return true;
    }
    Log.i(TAG, "Permission NOT granted: " + permission);
    return false;
  }
}
