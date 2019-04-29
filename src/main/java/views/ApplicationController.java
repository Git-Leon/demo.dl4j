package views;

import presentation.IApplicationController;
import presentation.mainview.IMainViewPresenter;
import presentation.mainview.MainViewPresenter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ApplicationController implements IApplicationController {
    private Stage _mainStage;

    public ApplicationController(Stage mainStage) {
        _mainStage = mainStage;
    }

    @Override
    public File showChooseImageView() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File chosenFile = fileChooser.showOpenDialog(_mainStage);
        return chosenFile;
    }

    @Override
    public void showMainView() {
        MainView mainView = new MainView(_mainStage);
        IMainViewPresenter mainViewPresenter = new MainViewPresenter(mainView, this);

        mainView.show();
    }
}
