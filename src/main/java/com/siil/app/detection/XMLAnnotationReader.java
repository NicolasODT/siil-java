package com.siil.app.detection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLAnnotationReader {

    public Map<String, List<BoundingBox>> readXMLAnnotations(String folderPath) {
        Map<String, List<BoundingBox>> annotations = new HashMap<>();
        try {
            File folder = new File(folderPath);
            for (File file : folder.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".xml")) {
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(file);
                    doc.getDocumentElement().normalize();
                    NodeList nList = doc.getElementsByTagName("object");
                    List<BoundingBox> boxes = new ArrayList<>();
                    for (int temp = 0; temp < nList.getLength(); temp++) {
                        Node nNode = nList.item(temp);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;
                            String label = eElement.getElementsByTagName("name").item(0).getTextContent();
                            int xmin = Integer.parseInt(eElement.getElementsByTagName("xmin").item(0).getTextContent());
                            int ymin = Integer.parseInt(eElement.getElementsByTagName("ymin").item(0).getTextContent());
                            int xmax = Integer.parseInt(eElement.getElementsByTagName("xmax").item(0).getTextContent());
                            int ymax = Integer.parseInt(eElement.getElementsByTagName("ymax").item(0).getTextContent());
                            BoundingBox box = new BoundingBox(xmin, ymin, xmax, ymax, label);
                            boxes.add(box);
                        }
                    }
                    String imageFileName = file.getName().replace(".xml", ".jpg"); // Assuming naming convention
                    annotations.put(imageFileName, boxes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return annotations;
    }
}
