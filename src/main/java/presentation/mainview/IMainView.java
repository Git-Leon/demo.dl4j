package presentation.mainview;

import presentation.IView;
import View.Controls.FaceControlView;

public interface IMainView extends IView<IMainViewPresenter> {
  void addFaceControlView(FaceControlView faceControlView);
  void clearFaces();
}
