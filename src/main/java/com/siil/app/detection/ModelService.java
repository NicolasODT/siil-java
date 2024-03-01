package com.siil.app.detection;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ModelService {

    private MultiLayerNetwork model;
    private final String modelPath = "trainedModel.zip"; // Assurez-vous que le chemin est correct

    public synchronized MultiLayerNetwork getTrainedModel() {
        if (model == null) {
            try {
                File modelFile = new File(modelPath);
                model = ModelSerializer.restoreMultiLayerNetwork(modelFile);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Erreur lors du chargement du modèle", e);
            }
        }
        return model;
    }
}
