package edu.ap.spring.jpa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuoteService {
	
	@Autowired
	QuoteRepository repository;
	
	
	public void saveQuotes(){
		String fileName = "oscar_wilde.txt";
		try {
			List<String> quotes = Files.lines(Paths.get(fileName))
									.collect(Collectors.toList());
		    Object[] quotesArray = quotes.toArray();							  

			for(int i = 0; i < quotesArray.length; i++) {
				Quote q = new Quote(quotesArray[i].toString());
				repository.save(q);
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Quote> getQuotes() {
		List<Quote>quotesList = (List<Quote>) repository.findAll() ;
		return quotesList;
	}
	

	public List<Quote> findByKeyWord(String keyword) {
		List<Quote>quotesList = (List<Quote>) repository.findAll();
		List<Quote> filteredList = new ArrayList<Quote>();
		for(Quote q : quotesList) {
			if(q.getQuote().contains(" "+keyword+" ")) {
				filteredList.add(q);
			}
		}
		
		return filteredList;
	}
	
	
}
