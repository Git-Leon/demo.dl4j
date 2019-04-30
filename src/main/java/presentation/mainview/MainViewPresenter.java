package presentation.mainview;

import views.controls.events.IOnRecognizeEventListener;
import views.controls.events.IOnTrainEventListener;
import views.controls.FaceControlView;
import core.math.EuclideanDistance;
import core.tasks.CreateInputVectorTask;
import core.tasks.ExtractFacesFromImageTask;
import core.tasks.ExtractFacesFromImageTaskBuilder;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.opencv.core.Size;
import presentation.IApplicationController;
import presentation.models.PersonModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainViewPresenter implements IMainViewPresenter, IOnTrainEventListener, IOnRecognizeEventListener {
    private final IMainView view;
    private final IApplicationController applicationController;

    private SimpleStringProperty message;

    private List<PersonModel> trainList;

    private CreateInputVectorTask createInputVector;

    private EuclideanDistance distance;

    public MainViewPresenter(IMainView view, IApplicationController applicationController) {
        this.view = view;
        this.view.setPresenter(this);
        this.applicationController = applicationController;

        System.out.println("Loading DL4J");
        createInputVector = new CreateInputVectorTask();
        System.out.println("Loaded DL4J");

        this.trainList = new ArrayList<>();
        distance = new EuclideanDistance();
        message = new SimpleStringProperty();
        message.addListener((observable, oldValue, newValue) -> {
            System.out.println("Extracted Face Location: " + newValue);
            if (newValue.trim().length() == 0)
                return;

            FaceControlView faceControlView = new FaceControlView();
            faceControlView.setImage(new File(newValue));
            faceControlView.addOnTrainListener((IOnTrainEventListener) view.getPresenter());
            faceControlView.addOnRecognizeListener((IOnRecognizeEventListener) view.getPresenter());
            Platform.runLater(() -> view.addFaceControlView(faceControlView));
        });
    }

    @Override
    public IMainView getView() {
        return view;
    }

    @Override
    public void extractFaces(File imageFilePath) {
        view.clearFaces();
        ExtractFacesFromImageTask extractFacesFromImageTask = new ExtractFacesFromImageTaskBuilder()
                .setTaskName("Extract Faces")
                .setApplicationController(applicationController)
                .setImageFilePath(imageFilePath)
                .setHaarFile(new File(System.getProperty("user.dir") + "/haarcascadefrontalfacealt.xml"))
                .setTempFolder(new File(System.getProperty("user.dir") + "/src/main/resources"))
                .setScaleFactor(1.05)
                .setMinNeighbours(7)
                .setMinFaceSize(new Size(10, 10))
                .setMaxFaceSize(new Size(200, 200))
                .createExtractFacesFromImageTask();

        message.bind(extractFacesFromImageTask.messageProperty());

        new Thread(extractFacesFromImageTask).start();
    }

    @Override
    public File showFileDialogChooser() {
        return applicationController.showChooseImageView();
    }

    @Override
    public void onTrain(String personName, File faceImageFile) {
        System.out.println("Train: " + faceImageFile.getName());

        double[] faceFeatureArray = createInputVector.runTask(faceImageFile);
        PersonModel objPersonModel = new PersonModel(personName, faceFeatureArray);
        trainList.add(objPersonModel);
    }

    @Override
    public void onRecognize(FaceControlView sender, File personImageFile) {
        System.out.println("Recognize: " + personImageFile.getName());
        double[] faceFeatureArray = createInputVector.runTask(personImageFile);
        INDArray array1 = Nd4j.create(faceFeatureArray);

        double minimalDistance = Double.MAX_VALUE;
        String result = "";
        for (PersonModel personModel : trainList) {
            INDArray array2 = Nd4j.create(personModel.getFaceFeatureArray());
            double distance = this.distance.run(array1, array2);
            if (distance < minimalDistance) {
                minimalDistance = distance;
                result = personModel.getPersonName();
            }
        }

        sender.setPersonName(result);
    }
}
