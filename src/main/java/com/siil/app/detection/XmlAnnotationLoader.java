package com.siil.app.detection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;

public class XmlAnnotationLoader {
    public static void loadAnnotations(String folderPath) {
        try {
            File folder = new File(folderPath);
            File[] listOfFiles = folder.listFiles();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".xml")) {
                    Document doc = dBuilder.parse(file);
                    doc.getDocumentElement().normalize();
                    NodeList nList = doc.getElementsByTagName("object");

                    for (int temp = 0; temp < nList.getLength(); temp++) {
                        Node nNode = nList.item(temp);

                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;
                            String label = eElement.getElementsByTagName("name").item(0).getTextContent();
                            int xmin = Integer.parseInt(eElement.getElementsByTagName("xmin").item(0).getTextContent());
                            int ymin = Integer.parseInt(eElement.getElementsByTagName("ymin").item(0).getTextContent());
                            int xmax = Integer.parseInt(eElement.getElementsByTagName("xmax").item(0).getTextContent());
                            int ymax = Integer.parseInt(eElement.getElementsByTagName("ymax").item(0).getTextContent());

                            // Ici, vous pouvez traiter les annotations comme vous le souhaitez
                            // Par exemple, en les ajoutant à une liste ou en les utilisant directement pour charger des images
                            System.out.println("Label: " + label + " Coords: [" + xmin + "," + ymin + "," + xmax + "," + ymax + "]");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Remplacez "path/to/annotations" par le chemin réel vers vos fichiers XML d'annotation
        loadAnnotations("path/to/annotations");
    }
}
