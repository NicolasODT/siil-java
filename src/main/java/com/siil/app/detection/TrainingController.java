package com.siil.app.detection;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainingController {

    @Autowired
    private ModelTrainer modelTrainer;

    @Autowired
    private ModelEvaluator modelEvaluator;

    @Autowired
    private ModelService modelService; // Assurez-vous que ce service peut charger le modèle entraîné

    @GetMapping("/train")
    public String trainModel() {
        try {
            modelTrainer.trainModel();
            return "Entraînement du modèle terminé avec succès.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de l'entraînement du modèle: " + e.getMessage();
        }
    }

    @GetMapping("/eval")
    public String evaluateModel() {
        try {
            MultiLayerNetwork model = modelService.getTrainedModel(); // Méthode hypothétique pour récupérer le modèle
            return modelEvaluator.evaluateModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de l'évaluation du modèle: " + e.getMessage();
        }
    }
}
