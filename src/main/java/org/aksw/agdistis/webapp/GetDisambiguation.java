package org.aksw.agdistis.webapp;

import java.util.ArrayList;
import java.util.HashMap;

import org.aksw.agdistis.algorithm.DisambiguationAlgorithm;
import org.aksw.agdistis.algorithm.NEDAlgo_HITS;
import org.aksw.agdistis.algorithm.NEDSpotlightPoster;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import datatypeshelper.utils.doc.Document;
import datatypeshelper.utils.doc.DocumentText;
import datatypeshelper.utils.doc.ner.NamedEntitiesInText;
import datatypeshelper.utils.doc.ner.NamedEntityInText;

public class GetDisambiguation extends ServerResource {
	private static Logger log = LoggerFactory.getLogger(GetDisambiguation.class);
	private DisambiguationAlgorithm agdistis;
	private DisambiguationAlgorithm spotlight;

	public GetDisambiguation() {
		String languageTag = "en"; // de
		String dataDirectory = "/home/rusbeck/AGDISTIS/";///data/r.usbeck"; // "/home/rusbeck/AGDISTIS/";
		String nodeType = "http://dbpedia.org/resource/";// "http://yago-knowledge.org/resource/"
		String edgeType = "http://dbpedia.org/ontology/";// "http://yago-knowledge.org/resource/"

		agdistis = new NEDAlgo_HITS(1, languageTag, dataDirectory, nodeType, edgeType);
		spotlight = new NEDSpotlightPoster();
	}

	@Post
	public String postText(String req) {
		log.info("Start working on Request");
		String text = req.split("&")[0].substring("text='".length(), req.split("&")[0].length() - 1);
		String type = req.split("&")[1].substring("type='".length(), req.split("&")[1].length() - 1);
		log.info(text);
		log.info(type);
		JSONArray arr = new org.json.simple.JSONArray();
		HashMap<NamedEntityInText, String> results = null;
		Document d = textToDocument(text);
		if (type.equals("agdistis")) {
			results = results(d, agdistis);
		} else if (type.equals("spotlight")) {
			results = results(d, spotlight);
		} else {
			return "ERROR: wrong type";
		}
		for (NamedEntityInText namedEntity : results.keySet()) {
			String disambiguatedURL = results.get(namedEntity);
			JSONObject obj = new JSONObject();
			obj.put("namedEntity", namedEntity.getLabel());
			obj.put("start", namedEntity.getStartPos());
			obj.put("offset", namedEntity.getLength());
			obj.put("disambiguatedURL", disambiguatedURL);
			arr.add(obj);
		}
		log.info("\t" + arr.toString());
		log.info("Finished Request");
		return arr.toString();

	}

	public static Document textToDocument(String preAnnotatedText) {
		Document document = new Document();
		ArrayList<NamedEntityInText> list = new ArrayList<NamedEntityInText>();
		log.info("\tText: " + preAnnotatedText);
		int startpos = 0, endpos = 0;
		StringBuilder sb = new StringBuilder();
		startpos = preAnnotatedText.indexOf("<entity>", startpos);
		while (startpos >= 0) {
			sb.append(preAnnotatedText.substring(endpos,startpos));
			startpos += 8;
			endpos = preAnnotatedText.indexOf("</entity>", startpos);
			int newStartPos = sb.length();
			String entityLabel = preAnnotatedText.substring(startpos, endpos);
			list.add(new NamedEntityInText(newStartPos, entityLabel.length(), entityLabel));
			sb.append(entityLabel);
			endpos += 9;
			startpos = preAnnotatedText.indexOf("<entity>", startpos);
		}

		NamedEntitiesInText nes = new NamedEntitiesInText(list);
		DocumentText text = new DocumentText(preAnnotatedText.replaceAll("<entity>", "").replaceAll("</entity>", ""));

		document.addProperty(text);
		document.addProperty(nes);
		return document;
	}

	private static HashMap<NamedEntityInText, String> results(Document document, DisambiguationAlgorithm algo) {
		algo.run(document);
		NamedEntitiesInText namedEntities = document.getProperty(NamedEntitiesInText.class);
		HashMap<NamedEntityInText, String> results = new HashMap<NamedEntityInText, String>();
		for (NamedEntityInText namedEntity : namedEntities) {
			String disambiguatedURL = algo.findResult(namedEntity);
			results.put(namedEntity, disambiguatedURL);
		}
		return results;
	}
}