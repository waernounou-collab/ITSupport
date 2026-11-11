package org.example;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Main {

    // Fonction 1 : validation XML/XSD
    public static boolean validerXML() {

        try {
            SchemaFactory schemaFactory =
                    SchemaFactory.newInstance(
                            XMLConstants.W3C_XML_SCHEMA_NS_URI);

            schemaFactory.setProperty(
                    XMLConstants.ACCESS_EXTERNAL_DTD, "");

            schemaFactory.setProperty(
                    XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            Schema schema;

            try (InputStream xsd =
                         Main.class.getResourceAsStream(
                                 "/incidents.xsd")) {

                if (xsd == null) {
                    throw new IllegalStateException(
                            "Fichier incidents.xsd introuvable");
                }

                schema = schemaFactory.newSchema(
                        new StreamSource(xsd));
            }

            Validator validator = schema.newValidator();

            validator.setProperty(
                    XMLConstants.ACCESS_EXTERNAL_DTD, "");

            validator.setProperty(
                    XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            try (InputStream xml =
                         Main.class.getResourceAsStream(
                                 "/incidents.xml")) {

                if (xml == null) {
                    throw new IllegalStateException(
                            "Fichier incidents.xml introuvable");
                }

                validator.validate(new StreamSource(xml));
            }

            System.out.println(
                    "1. Validation XML/XSD : REUSSIE");

            return true;

        } catch (Exception e) {

            System.out.println(
                    "1. Validation XML/XSD : ECHEC");

            System.out.println(e.getMessage());

            return false;
        }
    }

    // Fonction 2 : charger le document XML pour XPath
    public static Document chargerXML() throws Exception {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();

        factory.setFeature(
                XMLConstants.FEATURE_SECURE_PROCESSING, true);

        factory.setFeature(
                "http://apache.org/xml/features/disallow-doctype-decl",
                true);

        factory.setAttribute(
                XMLConstants.ACCESS_EXTERNAL_DTD, "");

        factory.setAttribute(
                XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

        DocumentBuilder builder =
                factory.newDocumentBuilder();

        try (InputStream xml =
                     Main.class.getResourceAsStream(
                             "/incidents.xml")) {

            if (xml == null) {
                throw new IllegalStateException(
                        "Fichier incidents.xml introuvable");
            }

            return builder.parse(xml);
        }
    }

    // Fonction 3 : executer une requete XPath
    public static void rechercher(
            Document document,
            XPath xpath,
            String titre,
            String requete) throws Exception {

        XPathExpression expression =
                xpath.compile(requete);

        NodeList resultats =
                (NodeList) expression.evaluate(
                        document, XPathConstants.NODESET);

        System.out.println("\n" + titre);

        if (resultats.getLength() == 0) {
            System.out.println("Aucun resultat.");
        }

        for (int i = 0; i < resultats.getLength(); i++) {

            System.out.println(
                    resultats.item(i).getTextContent().trim());
        }
    }

    // Programme principal
    public static void main(String[] args) {

        // On ne poursuit que si le XML est valide
        if (!validerXML()) {
            return;
        }

        try {
            Document document = chargerXML();

            XPathFactory xpathFactory =
                    XPathFactory.newInstance();

            XPath xpath = xpathFactory.newXPath();

            // Recherche 1 : tous les titres
            rechercher(
                    document,
                    xpath,
                    "2. Tous les incidents :",
                    "/gestionIncidents/incidents/incident/titre");

            // Recherche 2 : priorite critique
            rechercher(
                    document,
                    xpath,
                    "3. Incidents critiques :",
                    "/gestionIncidents/incidents/incident"
                            + "[priorite='Critique']/titre");

            // Recherche 3 : incidents ouverts
            rechercher(
                    document,
                    xpath,
                    "4. Incidents ouverts :",
                    "/gestionIncidents/incidents/incident"
                            + "[statut='Ouvert']/titre");

            // Recherche 4 : incidents du technicien T002
            rechercher(
                    document,
                    xpath,
                    "5. Incidents du technicien T002 :",
                    "/gestionIncidents/incidents/incident"
                            + "[technicienId='T002']/titre");

            // Recherche 5 : incidents reseau
            rechercher(
                    document,
                    xpath,
                    "6. Incidents reseau :",
                    "/gestionIncidents/incidents/incident"
                            + "[categorie='Reseau']/titre");

        } catch (Exception e) {

            System.out.println(
                    "Erreur pendant les recherches XPath : "
                            + e.getMessage());

            e.printStackTrace();
        }
    }
}
