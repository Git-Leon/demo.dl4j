package presentation.mainview;

import presentation.IPresenter;

import java.io.File;

public interface IMainViewPresenter extends IPresenter<IMainView> {
  void extractFaces(File imageFilePath);
  File showFileDialogChooser();
}
