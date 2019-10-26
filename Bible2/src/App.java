import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class App {

	Connection con, conn;
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	private JFrame frame;
	private JTextField textFieldVerse;
	private JButton btnSearch;
	private JLabel lblDisplayVerse, lblError;
	private String input, txtFld, query1, query2, url, str;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 550, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textFieldVerse = new JTextField();
		textFieldVerse.setBounds(10, 50, 75, 30);
		frame.getContentPane().add(textFieldVerse);
		textFieldVerse.setColumns(10);
		
		lblDisplayVerse = new JLabel();
		lblDisplayVerse.setBounds(120, 50, 400, 80);
		frame.getContentPane().add(lblDisplayVerse);
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fetch();
			}
		});
		btnSearch.setBounds(10, 100, 75, 30);
		frame.getContentPane().add(btnSearch);
		
		lblError = new JLabel();
		lblError.setBounds(120, 140, 200, 20);
		frame.getContentPane().add(lblError);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewTable = new JMenu("Tabs");
		menuBar.add(mnNewTable);
		
		JMenuItem mntmCreateNewTab = new JMenuItem("Create New Tab");
		mntmCreateNewTab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// SQLite connection string
		        //url = "jdbc:sqlite:C:\\\\Eclipse\\\\Programs\\\\Bible2\\\\BibleDB.db";
		        url = "jdbc:sqlite:C:\\\\Users\\\\alext\\\\git\\\\BibleApp\\\\Bible2\\\\BibleDB.db";
		        input = JOptionPane.showInputDialog("Enter name of new Tab");
		        if(input.isEmpty()) {
		        	lblError.setText("You didn't enter anything");
		        }
		        else {
		        	// SQL statement for creating a new table
			        query2 = "CREATE TABLE '"+input+"' (field1	TEXT, field2	TEXT)";		        
			        try (Connection conn = DriverManager.getConnection(url);
			                Statement stmt = conn.createStatement()) {
			            // create a new table
			            stmt.execute(query2);
			        } catch (SQLException ee) {
			            System.out.println(ee.getMessage());
			            lblError.setText("Couldn't Create New Tab");
			        }			        	
		        }					
			}
		});
		mnNewTable.add(mntmCreateNewTab);
		
		JMenuItem mntmDeleteTab = new JMenuItem("Delete Tab");
		mntmDeleteTab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// SQLite connection string
		        url = "jdbc:sqlite:C:\\\\Eclipse\\\\Programs\\\\Bible2\\\\BibleDB.db";
		        input = JOptionPane.showInputDialog("Select Tab to delete");
			}
		});
		mnNewTable.add(mntmDeleteTab);
	}
	
	public void fetch() {
		try {
			txtFld = textFieldVerse.getText().toLowerCase();
			con = DriverManager.getConnection("jdbc:sqlite:C:\\\\Eclipse\\\\Programs\\\\Bible2\\\\BibleDB.db");
			query1 = "SELECT field2 "
					+ "FROM Bible "
					+ "WHERE field1 like '"+txtFld+"' ";	
			pst = con.prepareStatement(query1);
			rs = pst.executeQuery();
			str = rs.getString("field2");
			
			lblDisplayVerse.setText("<html> "+str+"</html>");		}
		catch(Exception e) {
			lblError.setText("No Such Result");
		}
	}
}
