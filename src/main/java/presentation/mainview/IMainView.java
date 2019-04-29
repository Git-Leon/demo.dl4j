package presentation.mainview;

import presentation.IView;
import views.controls.FaceControlView;

public interface IMainView extends IView<IMainViewPresenter> {
  void addFaceControlView(FaceControlView faceControlView);
  void clearFaces();
}
