package core.tasks;

import core.BaseTask;
import nu.pattern.OpenCV;
import presentation.IApplicationController;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExtractFacesFromImageTask extends BaseTask<List<File>> {
    private File imageFilePath;
    private File haarFile;
    private File tempFolder;
    private double scaleFactor;
    private int minNeighbours;
    private Size minFaceSize;
    private Size maxFaceSize;

    private CascadeClassifier classifier;

    private boolean initialized = false;

    ExtractFacesFromImageTask(String taskName, IApplicationController applicationController,
                              File imageFilePath, File haarFile, File tempFolder,
                              double scaleFactor, int minNeighbours, Size minFaceSize, Size maxFaceSize) {
        super(taskName, applicationController);

        this.imageFilePath = imageFilePath;
        this.haarFile = haarFile;
        this.tempFolder = tempFolder;
        this.scaleFactor = scaleFactor;
        this.minNeighbours = minNeighbours;
        this.minFaceSize = minFaceSize;
        this.maxFaceSize = maxFaceSize;
        OpenCV.loadShared();
        this.classifier = new CascadeClassifier(this.haarFile.getAbsolutePath());
        this.initialized = true;
    }

    @Override
    protected List<File> runTask() {
        List<File> faceList = new ArrayList<>();
        MatOfRect matFaceList = new MatOfRect();

        Mat image = Imgcodecs.imread(this.imageFilePath.getAbsolutePath());
        this.classifier.detectMultiScale(image, matFaceList, this.scaleFactor, this.minNeighbours, 0, this.minFaceSize, this.maxFaceSize);

        if (!matFaceList.empty()) {
            int i = 0;
            for (Rect faceRectangle : matFaceList.toArray()) {
                Mat faceImage = image.submat(faceRectangle);
                String fileName = this.tempFolder + "\\Facethis." + i + ".jpg";
                Imgcodecs.imwrite(fileName, faceImage);
                faceList.add(new File(fileName));

                updateMessage(fileName);
                i++;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        updateValue(faceList);
        return faceList;
    }
}
