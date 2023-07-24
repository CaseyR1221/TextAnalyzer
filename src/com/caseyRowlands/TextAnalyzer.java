package com.caseyRowlands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TextAnalyzer {

	public static void main(String[] args) throws Exception {

		String html = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";

		Document doc = Jsoup.connect(html).get();

		Elements content = doc.getElementsByClass("chapter");
		Elements title = doc.getElementsByTag("h1");
		Elements author = doc.getElementsByTag("h2");

		String contentText = content.text();
		String titleText = title.text();
		String authorText = author.text();

		BufferedWriter writer = new BufferedWriter(new FileWriter("src/testFile.txt"));

		writer.write(titleText + " ");
		writer.write(authorText + " ");
		writer.write(contentText);

		writer.close();

		// Read each line of the file
		BufferedReader reader = new BufferedReader(new FileReader("src/testFile.txt"));
		String line;
		Map<String, Integer> wordFreq = new HashMap<>();

		while ((line = reader.readLine()) != null) {

			// Split the line into words and eliminate all characters except letters, then
			// count the frequency of each word
			String[] words = line.replaceAll("[^A-Za-z— ]", "").toLowerCase().split("\\s+|—");

			for (String word : words) {
				if (word.length() > 0) {
					wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
				}
			}
		}

		reader.close();
		
		DatabaseInserter db = new DatabaseInserter();
		db.insertAndReadFromDB(wordFreq);

	}

}
