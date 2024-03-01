package com.siil.app.detection;

import org.datavec.image.recordreader.ImageRecordReader;
import org.datavec.api.io.labels.ParentPathLabelGenerator;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
public class ImageDataService {

    private static final int HEIGHT = 512;
    private static final int WIDTH = 512;
    private static final int CHANNELS = 3;
    private static final int BATCH_SIZE = 32;

    public DataSetIterator getDataSetIterator(String dataType) throws Exception {
        String path = "src/main/resources/" + dataType; // Chemin vers le dossier
        FileSplit fileSplit = new FileSplit(new File(path), new String[]{"jpg"}); // Filtrage direct des fichiers JPEG

        ImageRecordReader recordReader = new ImageRecordReader(HEIGHT, WIDTH, CHANNELS, new ParentPathLabelGenerator());
        recordReader.initialize(fileSplit);

        return new RecordReaderDataSetIterator(recordReader, BATCH_SIZE, 1, 3);
    }
}
