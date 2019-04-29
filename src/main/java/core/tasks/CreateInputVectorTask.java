package core.tasks;

import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.transferlearning.TransferLearningHelper;
import org.deeplearning4j.zoo.PretrainedType;
import org.deeplearning4j.zoo.model.VGG16;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.VGG16ImagePreProcessor;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.io.IOException;

public class CreateInputVectorTask {
    private double[] result;
    private File faceImageFile;
    private TransferLearningHelper transferLearningHelper;
    private NativeImageLoader nativeImageLoader;
    private DataNormalization scaler;
    private Boolean isInitialized = false;

    public CreateInputVectorTask() {
        transferLearningHelper = new TransferLearningHelper(getObjComputationGraph(), "pool4");
        nativeImageLoader = new NativeImageLoader(224, 224, 3);
        scaler = new VGG16ImagePreProcessor();
        isInitialized = true;

    }

    public double[] runTask(File faceImageFile) {
        if (!isInitialized) return null;

        this.faceImageFile = faceImageFile;

        INDArray imageMatrix = getImageMatrix();
        scaler.transform(imageMatrix);
        DataSet objDataSet = new DataSet(imageMatrix, Nd4j.create(new float[]{0, 0}));

        DataSet objFeaturized = transferLearningHelper.featurize(objDataSet);
        INDArray featuresArray = objFeaturized.getFeatures();

        int reshapeDimension = 1;
        for (int dimension : featuresArray.shape()) {
            reshapeDimension *= dimension;
        }

        featuresArray = featuresArray.reshape(1, reshapeDimension);

        result = featuresArray.data().asDouble();

        return result;
    }

    public double[] getResult() {
        return result;
    }

    private ComputationGraph getObjComputationGraph() {
        try {
            PretrainedType pretrainedType = PretrainedType.VGGFACE;
            VGG16 vgg16 = new VGG16();
            ComputationGraph computationGraph = (ComputationGraph) vgg16.initPretrained(pretrainedType);
            return computationGraph;
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    private INDArray getImageMatrix() {
        try {
            return nativeImageLoader.asMatrix(this.faceImageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
