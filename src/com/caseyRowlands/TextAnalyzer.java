package com.caseyRowlands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TextAnalyzer {

	public static void main(String[] args) throws IOException {
		
		
		String html = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
    	
		Document doc = Jsoup.connect(html).get();
		
		Elements content = doc.getElementsByClass("chapter");
		
		String contentText = content.text();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("src/testFile.txt"));
		
		writer.write(contentText);
	
    	writer.close();
		
		// Read each line of the file
        BufferedReader reader = new BufferedReader(new FileReader("src/testFile.txt"));
        String line;      
        Map<String, Integer> wordFreq = new HashMap<>();
        
        while ((line = reader.readLine()) != null) {   	
        	
            // Split the line into words and eliminate all characters except letters, then count the frequency of each word
            String[] words = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
            
            for (String word : words) {
                if (word.length() > 0) {
                    wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
                }
            }
        }
        
        reader.close();        
        
        System.out.println("All Words From The Poem Sorted By Highest Frequency:");
        System.out.println("");
        
        firstTwentySorted(wordFreq);

	}
	
	public static void firstTwentySorted(Map<String, Integer> unsortedMap) {
    	// Turn the list into a LinkedHashMap to keep the insertion order and then sort and return the top 20 words with the highest frequencies
        LinkedHashMap<String, Integer> sortedMap = unsortedMap.entrySet()
      	      .stream()
      	      .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
      	      .limit(20)
      	      .collect(Collectors.toMap(
      	    	  e -> e.getKey(),
      	    	  e -> e.getValue(),
      	          (e1, e2) -> e1, LinkedHashMap::new));
      
        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
	
//	public static void fullSortedlist(Map<String, Integer> unsortedMap) {
//    	// Turn the list into a LinkedHashMap to keep the insertion order and then sort all the word in descending order
//        LinkedHashMap<String, Integer> sortedMap = unsortedMap.entrySet()
//      	      .stream()
//      	      .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//      	      .collect(Collectors.toMap(
//      	    	  e -> e.getKey(),
//      	    	  e -> e.getValue(),
//      	          (e1, e2) -> e1, LinkedHashMap::new));
//      
//        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }
//    }

}
