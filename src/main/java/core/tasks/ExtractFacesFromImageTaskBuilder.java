package core.tasks;

import org.opencv.core.Size;
import presentation.IApplicationController;

import java.io.File;

public class ExtractFacesFromImageTaskBuilder {
    private String taskName;
    private IApplicationController applicationController;
    private File imageFilePath;
    private File haarFile;
    private File tempFolder;
    private double scaleFactor;
    private int minNeighbours;
    private Size minFaceSize;
    private Size maxFaceSize;

    public ExtractFacesFromImageTaskBuilder() {
    }

    public ExtractFacesFromImageTaskBuilder setTaskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

    public ExtractFacesFromImageTaskBuilder setApplicationController(IApplicationController applicationController) {
        this.applicationController = applicationController;
        return this;
    }

    public ExtractFacesFromImageTaskBuilder setImageFilePath(File imageFilePath) {
        this.imageFilePath = imageFilePath;
        return this;
    }

    public ExtractFacesFromImageTaskBuilder setHaarFile(File haarFile) {
        this.haarFile = haarFile;
        return this;
    }

    public ExtractFacesFromImageTaskBuilder setTempFolder(File tempFolder) {
        this.tempFolder = tempFolder;
        return this;
    }

    public ExtractFacesFromImageTaskBuilder setScaleFactor(double scaleFactor) {
        this.scaleFactor = scaleFactor;
        return this;
    }

    public ExtractFacesFromImageTaskBuilder setMinNeighbours(int minNeighbours) {
        this.minNeighbours = minNeighbours;
        return this;
    }

    public ExtractFacesFromImageTaskBuilder setMinFaceSize(Size minFaceSize) {
        this.minFaceSize = minFaceSize;
        return this;
    }

    public ExtractFacesFromImageTaskBuilder setMaxFaceSize(Size maxFaceSize) {
        this.maxFaceSize = maxFaceSize;
        return this;
    }

    public ExtractFacesFromImageTask createExtractFacesFromImageTask() {
        return new ExtractFacesFromImageTask(taskName, applicationController, imageFilePath, haarFile, tempFolder, scaleFactor, minNeighbours, minFaceSize, maxFaceSize);
    }
}