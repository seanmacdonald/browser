import java.awt.*; 
import java.awt.event.*;
import java.util.Stack;

import javax.swing.*; 
import javax.swing.event.*; 

public class Browser extends JFrame{
	
	private JTextField addressBar; 
	private JEditorPane display; 
	
	private JPanel navBar; 
	private JButton backButton; 
	private JButton forwardButton;
	private Stack<String> urlStack;  
	private String currentURL = new String();
	private boolean backButtonPressed = false; 
	
	//constructor *********************************************************************************************
	public Browser(){
		super("Sean's Browser"); //this title appears at the very top of your screen/application  
		currentURL = ""; //current URL gets an empty string
		
		
		//SET UP THE TEXT FIELD AND THE ACTION LISTENER FOR IT 
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

		//SETUP ACTION LISTENER FOR FORWARD BUTTON
		forwardButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					forwardButtonPressed(); 
				}
			}
		);
		
		//SETUP ACTION LISTER FOR THE EDITOR PANE 
		display = new JEditorPane(); 
		display.setEditable(false); //would only be true for a program like notepad where you actually changed stuff inside. for this web browser you just view stuff 
		display.setFont(new Font("SansSerif", Font.BOLD, 20));
		display.addHyperlinkListener(  //so you can click on links on the page and it will put it in the adressBar
			new HyperlinkListener(){
				public void hyperlinkUpdate(HyperlinkEvent event){ //this method will be called whenever a hyperlink event happens 
					if(event.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
						loadWebInformation(event.getURL().toString()); 
						//urlStack.push(event.getURL().toString());
					}
					else if(event.getEventType() == HyperlinkEvent.EventType.ENTERED){
						System.out.println("Bold the hyperlink"); 
					}
				}
			}
		);
		add(new JScrollPane(display), BorderLayout.CENTER);
		setSize(900, 900); 
		setVisible(true); 
	}
	//********************************************************************************************************
	
	/**
	 *  loadWebInformation - displays the html retrieved from the given url on the screen 
	 * 
	 * @param userText - the url of the website that we would like to view. 
	 */
	private void loadWebInformation(String userText){ //userText is the url that gets passed into the addressBar
		try{
			display.setPage(userText); // setPage is a VERY POWERFUL METHOD. DOES THE WORK FOR US
			addressBar.setText(userText); //This keeps the same url in the address box
			if(!backButtonPressed){
				urlStack.push(currentURL); 
				currentURL = userText; 
			}
			else{
				backButtonPressed = false; 
			}
		}catch (Exception e){
			System.out.println("WHOOPS! That's not a valid URL");
			display.setText("That Site Cannot Be Reached!!!");
			addressBar.setText("ERROR");
		}
				
	}
	
	/**
	 * backButton - this method is called when the user presses the back button.
	 *  
	 */
	private void backButtonPressed(){
		System.out.println("Back Button Pressed"); 
		if(!(urlStack.isEmpty())){
			backButtonPressed = true; 
			loadWebInformation(urlStack.pop());
			//System.out.println(urlStack.pop()); 
		}
		else{
			System.out.println("Stack is empty"); 
		}
	}
	
	/**
	 * forwardButton - this method is called when the user presses the forward button. 
	 * 
	 */
	private void forwardButtonPressed(){
		System.out.println("Forward Button Pressed");
	}
}
