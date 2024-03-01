package com.siil.app.detection;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelEvaluator {

    @Autowired
    private ImageDataService imageDataService;

    public String evaluateModel(MultiLayerNetwork model) throws Exception {
        // Obtention de l'itérateur sur les données de test
        RecordReaderDataSetIterator testData = (RecordReaderDataSetIterator) imageDataService.getDataSetIterator("test");
        
        // Prétraitement des images (par exemple, normalisation)
        new ImagePreProcessor().preprocessImages(testData);

        // Initialisation de l'objet Evaluation
        Evaluation eval = new Evaluation();
        
        // Évaluation du modèle sur chaque batch de données de test
        while(testData.hasNext()) {
            DataSet ds = testData.next();
            INDArray features = ds.getFeatures();
            INDArray labels = ds.getLabels();
            
            // Prédiction du modèle sur le batch actuel
            INDArray output = model.output(features, false);
            
            // Accumulation des résultats d'évaluation
            eval.eval(labels, output);
        }

        // Renvoi des statistiques d'évaluation
        return eval.stats();
    }
}
