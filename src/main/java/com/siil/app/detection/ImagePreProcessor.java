package com.siil.app.detection;

import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;

public class ImagePreProcessor {

    public void preprocessImages(RecordReaderDataSetIterator data) {
        ImagePreProcessingScaler scaler = new ImagePreProcessingScaler(0, 1);
        data.setPreProcessor(scaler);
    }
}
