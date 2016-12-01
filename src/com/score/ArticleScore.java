package com.score;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.trie4j.patricia.PatriciaTrie;

import com.opencsv.CSVReader;

public class ArticleScore {

	private static final Object EMPTY_CHAR = "";
	static PatriciaTrie trie ;
	
	public static void main(String[] args) {
		trie = new PatriciaTrie();
		try{
			constructTrie();
			calculateScore("Barron800");
		}
		catch(IOException e){
			e.printStackTrace();
		}

	}

	private static void constructTrie() throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader("article.txt"));
		String line = reader.readLine();
		int wordCount =0;
		
		while(line!=null){
			String[] words = line.split(" ");
			for(String word : words){
				trie.insert(word.toLowerCase());
				wordCount++;
			}
			
			line  = reader.readLine();
		}
		System.out.println("Total "+ wordCount+" words in article.\n");
	}

	private static void calculateScore(String fileName) throws IOException{
		CSVReader reader = new CSVReader(new FileReader(fileName+".csv"));
	     String [] nextLine;
	     int count = 0;
	     
		while ((nextLine = reader.readNext()) != null) {
			if (EMPTY_CHAR.equals(nextLine[1])) {
				Iterable<String> list = trie.predictiveSearch(nextLine[0].toLowerCase());
				Iterator<String> wordIterator = list.iterator();

				if (wordIterator.hasNext()) {
					count++;
					System.out.println(nextLine[0]+" -> "+list);
				}

//				while (wordIterator.hasNext()) {
//					System.out.println(wordIterator.next());
//				}

			}

		}
		System.out.println("Total " + count + " unique word(s).");
	    	 		
	}

}
