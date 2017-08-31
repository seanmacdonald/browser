import java.awt.*; 
import java.awt.event.*;
import java.util.Stack;

import javax.swing.*; 
import javax.swing.event.*; 

public class ReadFile extends JFrame{
	
	private JTextField addressBar; 
	private JEditorPane display; 
	
	private JPanel navBar; 
	private JButton backButton; 
	private JButton forwardButton;
	private Stack<String> urlStack;  
	
	//constructor *********************************************************************************************
	public ReadFile(){
		super("Sean's Browser"); //this title appears at the very top of your screen/application  
		
		
		//SET UP ACTION LISTERN FOR TEXT FIELD 
		addressBar = new JTextField("https://en.wikipedia.org"); //they will have to delete this in order to put a real one in  
		addressBar.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent event){
					loadWebInformation(event.getActionCommand());  //event.getActionCommand gets the string that was passed into the adressBar
					//loadWebInformation will be responsible for reading html files and loading them onto the screen
				}
			}
		);
		addressBar.setPreferredSize( new Dimension( 500, 40) );
		addressBar.setFont(new Font("SansSerif", Font.BOLD, 20));
		//add(addressBar, BorderLayout.NORTH);
		
		//SETTING UP THE PANEL 
		navBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		navBar.setBackground(Color.YELLOW); 
		backButton = new JButton("Back");
		forwardButton = new JButton("Forward"); 
		urlStack = new Stack<String>(); 
		
		navBar.add(backButton);
		navBar.add(forwardButton); 
		navBar.add(addressBar); 
		add(navBar, BorderLayout.NORTH); 
		
		//SETUP ACTION LISTENER FOR BACK BUTTON
		backButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					backButtonPressed(); 
				}
			}
		);
		
		//SETUP ACTION LISTER FOR THE EDITOR PANE 
		display = new JEditorPane(); 
		display.setEditable(false); //would only be true for a program like notepad where you actually changed stuff inside. for this web browser you just view stuff 
		display.addHyperlinkListener(  //so you can click on links on the page and it will put it in the adressBar
			new HyperlinkListener(){
				public void hyperlinkUpdate(HyperlinkEvent event){ //this method will be called whenever a hyperlink event happens 
					if(event.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
						loadWebInformation(event.getURL().toString()); 
					}
					else if(event.getEventType() == HyperlinkEvent.EventType.ENTERED){
						System.out.println("Bold the hyperlink"); 
					}
				}
			}
		);
		add(new JScrollPane(display), BorderLayout.CENTER);
		setSize(800, 400); 
		setVisible(true); 
	}
	//********************************************************************************************************
	
	//load web information to display on the screen 
	//make it private because we are not going to be using it in any class 
	private void loadWebInformation(String userText){ //userText is the url that gets passed into the addressBar
		try{
			display.setPage(userText); // setPage is a VERY POWERFUL METHOD. DOES THE WORK FOR US
			addressBar.setText(userText); //This keeps the same url in the address box
			urlStack.push(userText); 
		}catch (Exception e){
			System.out.println("WHOOPS! That's not a valid URL"); 
		}
				
	}
	
	//backButton method 
	private void backButtonPressed(){
		System.out.println("Back Button Pressed"); 
		if(!(urlStack.isEmpty())){
			loadWebInformation(urlStack.pop());
			//System.out.println(urlStack.pop()); 
		}
		else{
			System.out.println("Stack is empty"); 
		}
	}
}
