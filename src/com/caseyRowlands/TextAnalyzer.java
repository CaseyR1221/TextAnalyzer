package com.caseyRowlands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TextAnalyzer {

	public static void main(String[] args) throws IOException {
		
		
		try {
			String html = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
			
			Document doc = Jsoup.connect(html).get();
			
			Elements content = doc.getElementsByClass("chapter");
			
			String contentText = content.text();
			
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/testFile.txt"));
			
			System.out.println(contentText);
			
			try {
				writer.write(contentText);
			} catch(IOException e) {
				System.out.println(e);
			}
			
			writer.close();
			
		} catch (IOException e) {
			
			System.out.println(e);
		}

	}

}
