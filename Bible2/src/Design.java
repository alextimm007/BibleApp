import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Design {

	private JFrame frame;
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel model;
	private DefaultTableCellRenderer cellRenderer;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Design window = new Design();
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
	public Design() {
		initialize();	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 900, 600);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.getContentPane().setLayout(null);
		
		
		frame.setTitle("JTable Test");
	      frame.getContentPane().setLayout(new FlowLayout());
	      scrollPane = new JScrollPane();
	      table = new JTable();
	      scrollPane.setViewportView(table);
	      model = (DefaultTableModel)table.getModel();
	      model.addColumn("List");
	      model.addColumn("Description");   
	      
	      Object[] rowData = new Object[2];
	        
	        for(int i = 0; i < getUsers().size(); i++){            
	           
	             rowData[0] = getUsers().get(i).getField1();
	              rowData[1] = getUsers().get(i).getField2();
	               model.addRow(rowData);
	        }
	        	                
	        table.setModel(model);
	        
	        // set the column width for each column
	      table.getColumnModel().getColumn(0).setPreferredWidth(100);
	      table.getColumnModel().getColumn(1).setPreferredWidth(400);

	      cellRenderer = new DefaultTableCellRenderer();
	      cellRenderer.setHorizontalAlignment(JLabel.CENTER);
	      table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);

	      frame.getContentPane().add(scrollPane);
	      frame.setResizable(false);
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.setLocationRelativeTo(null);
	      frame.setVisible(true);
	      
	      
		//frame.getContentPane().add(table);
	}	
		
	static ArrayList<Users> getUsers(){		        
        ArrayList<Users> users = new ArrayList<Users>();		        
        Connection con = getConnection();		        
        Statement st;		        
        ResultSet rs;		        
        Users u;
        
        try {		            
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM Bible");		            
            while(rs.next()){		                
                u = new Users(
                        rs.getString("field1"),
                        rs.getString("field2")
                );		                
                users.add(u);
            }		             
        } catch (SQLException ex) {
            Logger.getLogger(Design.class.getName()).log(Level.SEVERE, null, ex);
        }		
        return users;
    }

	static Connection getConnection(){
	    Connection con = null;		    
	    try {
	        con = DriverManager.getConnection("jdbc:sqlite:C:\\\\Eclipse\\\\Programs\\\\BibleApp\\\\BibleDB.db");
	    } catch (SQLException ex) {
	        Logger.getLogger(Design.class.getName()).log(Level.SEVERE, null, ex);
	    }		    
	    return con;
	}
}
