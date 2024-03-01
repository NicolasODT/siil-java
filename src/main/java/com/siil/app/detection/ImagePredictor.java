package com.siil.app.detection;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.datavec.image.loader.NativeImageLoader;
import java.io.File;

public class ImagePredictor {

    private MultiLayerNetwork model;

    public ImagePredictor(MultiLayerNetwork model) {
        this.model = model;
    }

    public INDArray predict(String imagePath) throws Exception {
        NativeImageLoader loader = new NativeImageLoader(512, 512, 3);
        INDArray image = loader.asMatrix(new File(imagePath));

        ImagePreProcessingScaler scaler = new ImagePreProcessingScaler(0, 1);
        scaler.transform(image);

        INDArray output = model.output(image);
        return output;
    }
}
