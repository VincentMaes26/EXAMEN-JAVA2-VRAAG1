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
	private JTextArea quotesArea;
	private JButton btnAdd, btnShow, btnClear;
	private JLabel lbl;
	
	@Autowired
	QuoteService service;
	
	public void setUp() {
		jFrame = new JFrame("Spring JFrame");
		jFrame.setSize(500, 1000);
		jFrame.setLocationRelativeTo(null);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.getContentPane().setLayout(null);
		
		quotesArea = new JTextArea();
		lbl = new JLabel();
		
		btnAdd = new JButton("Add quotes to database");
		btnAdd.setBounds(100,13, 100,25);
		btnAdd.addActionListener(e -> {
			service.saveQuotes();
			lbl.setText("Quotes have been added to the database");
		});
		jFrame.getContentPane().add(btnAdd);

		
		btnShow = new JButton("Show quotes");
		btnAdd.setBounds(300,13, 100,25);
		btnShow.addActionListener(e -> {
			List<Quote> quoteList = service.getQuotes();
			for(Quote q : quoteList) {
				quotesArea.append(q.toString() + "\n");
			}
		});
		jFrame.getContentPane().add(btnShow);
		
		btnClear = new JButton("Clear");
		btnClear.setBounds(100,550, 100, 25);
		btnClear.addActionListener(e ->{
			quotesArea.setText("");
		});

		quotesArea.setBounds(100,25,500,1000);
		
		jFrame.getContentPane().add(quotesArea);
		jFrame.getContentPane().add(btnClear);

		jFrame.setVisible(true);
	}
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		System.setProperty("java.awt.headless", "false");
	}
}
