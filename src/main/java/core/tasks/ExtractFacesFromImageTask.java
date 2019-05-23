package core.tasks;

import core.BaseTask;
import misplacedutils.MultiChannelMatrixAdapter;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_objdetect;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import presentation.IApplicationController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExtractFacesFromImageTask extends BaseTask<List<File>> {
    private File imageFilePath;
    private File haarFile;
    private File tempFolder;
    private double scaleFactor;
    private int minNeighbours;
    private opencv_core.Size minFaceSize;
    private opencv_core.Size maxFaceSize;

    private opencv_objdetect.CascadeClassifier classifier;

    private boolean initialized = false;

    ExtractFacesFromImageTask(String taskName, IApplicationController applicationController,
                              File imageFilePath, File haarFile, File tempFolder,
                              double scaleFactor, int minNeighbours, opencv_core.Size minFaceSize, opencv_core.Size maxFaceSize) {
        super(taskName, applicationController);

        this.imageFilePath = imageFilePath;
        this.haarFile = haarFile;
        this.tempFolder = tempFolder;
        this.scaleFactor = scaleFactor;
        this.minNeighbours = minNeighbours;
        this.minFaceSize = minFaceSize;
        this.maxFaceSize = maxFaceSize;
        this.classifier = new opencv_objdetect.CascadeClassifier(this.haarFile.getAbsolutePath());
        this.initialized = true;
    }

    @Override
    protected List<File> runTask() {
        List<File> faceList = new ArrayList<>();
        MatOfRect matFaceList = new MatOfRect();

        opencv_core.Mat image = MultiChannelMatrixAdapter.toJavaCVMat(Imgcodecs.imread(this.imageFilePath.getAbsolutePath()));
        this.classifier.detectMultiScale(
                image,
                new opencv_core.RectVector(),
                this.scaleFactor,
                this.minNeighbours,
                0,
                this.minFaceSize,
                this.maxFaceSize);


        if (!matFaceList.empty()) {
            int i = 0;
            for (Rect faceRectangle : matFaceList.toArray()) {
                Mat openCvImage = MultiChannelMatrixAdapter.toOpenCVMat(image);
                Mat faceImage = openCvImage.submat(faceRectangle);
                String fileName = this.tempFolder + "\\Facethis." + i + ".jpg";
                Imgcodecs.imwrite(fileName, faceImage);
                faceList.add(new File(fileName));

                updateMessage(fileName);
                i++;
            }
        }

        super.updateValue(faceList);
        return faceList;
    }
}
