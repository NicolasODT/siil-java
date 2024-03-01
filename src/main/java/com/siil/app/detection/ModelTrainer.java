package com.siil.app.detection;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ModelTrainer {

    @Autowired
    private ImageDataService imageDataService;

    public void trainModel() throws Exception {
        // Configuration du modèle
        int numClasses = 3; // Ajustez en fonction de votre cas
        int width = 512; // largeur de l'image
        int height = 512; // hauteur de l'image
        int channels = 3; // canaux de l'image (RGB)
        MultiLayerNetwork model = new MultiLayerNetwork(ModelConfiguration.createModelConfiguration(numClasses, height, width, channels));
        model.init();
        model.setListeners(new ScoreIterationListener(10));

        // Chargement des données d'entraînement
        DataSetIterator trainData = imageDataService.getDataSetIterator("train");
        
        // Entraînement du modèle
        int numEpochs = 10;
        for (int i = 0; i < numEpochs; i++) {
            model.fit(trainData);
            System.out.println("Epoch " + (i+1) + " complete");
        }

        // Sauvegarde du modèle entraîné
        File locationToSave = new File("trainedModel.zip"); // Modifiez le chemin selon vos besoins
        boolean saveUpdater = true; // Si vous voulez sauvegarder l'état de l'optimiseur (updater)
        
        ModelSerializer.writeModel(model, locationToSave, saveUpdater);
        System.out.println("Model saved to: " + locationToSave.getAbsolutePath());
    }
}
