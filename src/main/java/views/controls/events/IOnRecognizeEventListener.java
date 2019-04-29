package views.controls.events;

import views.controls.FaceControlView;

import java.io.File;

public interface IOnRecognizeEventListener {
  void onRecognize(FaceControlView sender, File personImageFile);
}
