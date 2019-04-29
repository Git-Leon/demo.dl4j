import presentation.IApplicationController;
import views.ApplicationController;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    IApplicationController applicationController = new ApplicationController(primaryStage);
    applicationController.showMainView();
  }
}
