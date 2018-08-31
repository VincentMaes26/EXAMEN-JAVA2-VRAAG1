package edu.ap.spring.view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.ap.spring.jpa.Quote;
import edu.ap.spring.jpa.QuoteService;

@Component
public class UI implements InitializingBean {
	private JFrame jFrame;
	private JTextField txtFilter;
	private JTextArea quotesArea;
	private JButton btnAdd, btnShow, btnFilter, btnClear;
	private JLabel lblAdded;
	private JPanel controlPanel1, controlPanel2;
	
	@Autowired
	QuoteService service;
	
	public void setUp() {
		jFrame = new JFrame("Quotes App");
    	jFrame.setLayout(new FlowLayout());
    	controlPanel1 = new JPanel();
    	
    	controlPanel1.setLayout(new GridLayout(5,1,1,10));
    	controlPanel2 = new JPanel();
    	controlPanel2.setLayout(new GridLayout(5,1,1,10));
    	
		quotesArea = new JTextArea();
		quotesArea.setSize(300,500);	
		lblAdded = new JLabel();
		txtFilter = new JTextField();
		
		btnAdd = new JButton("Add quotes to database");
		btnAdd.setSize(100,25);
		btnAdd.addActionListener(e -> {
			service.saveQuotes();
			lblAdded.setText("Quotes have been added to the database");
		});

		
		btnShow = new JButton("Show quotes");
		btnShow.setSize(100,25);
		btnShow.addActionListener(e -> {
			List<Quote> quoteList = service.getQuotes();
			for(Quote q : quoteList) {
				quotesArea.append(q.toString() + "\n");
			}
		});
		
		btnFilter = new JButton("Show filtered quotes");
		btnFilter.setSize(100,25);
		btnFilter.addActionListener(e -> {
			String keyword = txtFilter.getText();
			List<Quote> quoteList = service.findByKeyWord(keyword);
			for(Quote q : quoteList) {
				quotesArea.append(q.toString() + "\n");
			}
		});
		
		btnClear = new JButton("Clear");
		btnClear.setSize(100,50);
		btnClear.addActionListener(e ->{
			quotesArea.setText("");
		});

		controlPanel1.add(btnAdd);
    	controlPanel1.add(btnShow);
    	controlPanel1.add(txtFilter);
    	controlPanel1.add(btnFilter);
    	controlPanel1.add(lblAdded);
    	controlPanel2.add(quotesArea);
    	controlPanel1.add(btnClear);

		
    	jFrame.add(controlPanel1);
    	jFrame.add(controlPanel2);
		jFrame.setSize(1000, 750);
		jFrame.pack();
    	jFrame.setLocationRelativeTo(null);
    	jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);
	}
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		System.setProperty("java.awt.headless", "false");
	}
}
