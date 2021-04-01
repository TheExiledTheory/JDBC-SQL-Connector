/*

     _ ___  ___  ___    ___                      _             __ __  ____   _____  ___  _   __ 
  _ | |   \| _ )/ __|  / __|___ _ _  _ _  ___ __| |_ ___ _ _  | _|  \/  \ \ / / __|/ _ \| | |_ |
 | || | |) | _ \ (__  | (__/ _ \ ' \| ' \/ -_) _|  _/ _ \ '_| | || |\/| |\ V /\__ \ (_) | |__| |
  \__/|___/|___/\___|  \___\___/_||_|_||_\___\__|\__\___/_|   | ||_|  |_| |_| |___/\__\_\____| |
                                                              |__|                          |__|
                                                                   
 */


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.Component;
import javax.swing.border.LineBorder;


public class GUI {

	// Global GUI elements 
	private JFrame mainWindow;
	static JTextField passwordTextBox;
	static JTextField usernameTextBox;
	static JLabel databaseInfoLabel;
	static JLabel databaseCommandLabel;
	static JLabel databaseResponseLabel;
	static JLabel connectionStatusLabel;
	static JLabel connectionStatusActiveLabel;
	static JButton connectDatabaseButton;
	static JButton clearCommandButton;
	static JButton executeCommandButton;
	static JButton clearResponseButton;
	static JLabel usernameLabel; 
	static JLabel passwordLabel;
	static JLabel jdbcDriverLabel;
	static JLabel databaseURLLabel; 
	static JComboBox jdbcDropDownBox;
	static JComboBox databaseURLDropDownBox;
	static JTextArea commandTextBox;
	static JButton closeConnectionButton; 
	static JButton exitButton;
	static JTable responseTextBox;
	static JScrollPane scrollPane;
	
	// Global variables 
	public static String driverURL = "jdbc:mysql://localhost:3306/project3";
	public static String driver = "com.mysql.jdbc.Driver";
	boolean connection2DB = false; 
	Connection connection = null;
	MysqlDataSource dataSource = null;
	ResultSet resultSet = null; 
	Statement statement = null; 
	String command = null;
	String user = "";
	String pass = ""; 
	ResultSetMetaData rsmd = null; 
	
	// Main 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.mainWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI() throws ClassNotFoundException, SQLException, IOException {
		initialize();
	}

	private void initialize() {
		mainWindow = new JFrame();
		mainWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// Check for active connection 
				if (connection2DB = true) {
					try {
						// Attempt to close the connection 
						connection.close();
					}
					catch (SQLException e3) {
						e3.printStackTrace();
					}
				}
				// System shutdown 
				System.exit(0);
			}
		});
		mainWindow.setBounds(100, 100, 733, 501);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.getContentPane().setLayout(null);
		
		databaseInfoLabel = new JLabel("Enter Database Information:");
		databaseInfoLabel.setBounds(38, 11, 196, 21);
		mainWindow.getContentPane().add(databaseInfoLabel);
		
		databaseCommandLabel = new JLabel("Enter Database Commands:");
		databaseCommandLabel.setBounds(294, 11, 196, 21);
		mainWindow.getContentPane().add(databaseCommandLabel);
		
		databaseResponseLabel = new JLabel("Reponse:");
		databaseResponseLabel.setBounds(38, 208, 156, 21);
		mainWindow.getContentPane().add(databaseResponseLabel);
		
		connectionStatusLabel = new JLabel("Connection Status:");
		connectionStatusLabel.setBackground(SystemColor.desktop);
		connectionStatusLabel.setBounds(38, 152, 119, 14);
		mainWindow.getContentPane().add(connectionStatusLabel);
		
		connectionStatusActiveLabel = new JLabel("Not connected :c");
		connectionStatusActiveLabel.setBounds(167, 152, 323, 14);
		connectionStatusActiveLabel.setForeground(Color.RED);
		mainWindow.getContentPane().add(connectionStatusActiveLabel);
		
		connectDatabaseButton = new JButton("Connect to database");
		connectDatabaseButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// Make sure that there is no active connection 
				if (connection2DB == false) {
					try {
						// Initialize the connection 
						if (startConnection()) {
							// Show success dialog 
							JOptionPane.showMessageDialog(null, "Connected! Now enter commands to execute!");
							// Start with a fresh command box 
							commandTextBox.setText("");
						}

					} catch (ClassNotFoundException | SQLException | IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Pre-exisitng connection exists!");
				}
			}

		});
		connectDatabaseButton.setForeground(Color.BLACK);
		connectDatabaseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Make sure that there is no active connection 
				if (connection2DB == false) {
					try {
						// Initialize the connection 
						if (startConnection()) {
							// Show success dialog 
							JOptionPane.showMessageDialog(null, "Connected! Now enter commands to execute!");
							// Start with a fresh command box 
							commandTextBox.setText("");
						}

					} catch (ClassNotFoundException | SQLException | IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Pre-exisitng connection exists!");
				}
			}
		});
		connectDatabaseButton.setBounds(130, 179, 175, 23);
		mainWindow.getContentPane().add(connectDatabaseButton);
		
		clearCommandButton = new JButton("Clear commands");
		clearCommandButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Simply clear command box 
				commandTextBox.setText("");
			}
		});
		clearCommandButton.setBounds(315, 179, 175, 23);
		mainWindow.getContentPane().add(clearCommandButton);
		
		executeCommandButton = new JButton("Execute command(s)");
		executeCommandButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Retrieve the commands as a string array
				command = commandTextBox.getText();
				
				// Make sure that an active connection exists 
				if (connection2DB == true) {
					
					try {	
						// Remove any residual resultSet
						clearTable();
						// Execute commands 
						sendCommands(); 
						
					} catch (Exception ee) {
						JOptionPane.showMessageDialog(null, ee.getMessage(), "Error executing command!\n", 0);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Cannot execute commands, no active connection!\n");
				}
				
			}
		});
		executeCommandButton.setBounds(500, 179, 175, 23);
		mainWindow.getContentPane().add(executeCommandButton);
		
		clearResponseButton = new JButton("Clear response");
		clearResponseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Clear Jtable
				clearTable(); 
			}
		});
		clearResponseButton.setBounds(38, 428, 175, 23);
		mainWindow.getContentPane().add(clearResponseButton);
		
		usernameLabel = new JLabel("Username: ");
		usernameLabel.setBounds(38, 92, 83, 14);
		mainWindow.getContentPane().add(usernameLabel);
		
		passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(38, 117, 83, 14);
		mainWindow.getContentPane().add(passwordLabel);
		
		passwordTextBox = new JTextField();
		passwordTextBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				// Set global once user leaves pass box 
				pass = passwordTextBox.getText().trim();
				// If the pass field is not empty hide it 
				if (passwordTextBox.getText().compareTo("") != 0) {
					passwordTextBox.setText("************");
				}
				
			}
			@Override
			public void focusGained(FocusEvent e) {
				// Take the password 
				passwordTextBox.setText(pass);
			}
		});
		passwordTextBox.setBounds(157, 114, 127, 20);
		mainWindow.getContentPane().add(passwordTextBox);
		passwordTextBox.setColumns(10);
		
		usernameTextBox = new JTextField();
		usernameTextBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				// Set global once user leaves user box 
				user = usernameTextBox.getText().trim();
				// If the user field is not empty hide it 
				if (usernameTextBox.getText().compareTo("") != 0) {
					usernameTextBox.setText("************");
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				// Take the username
				usernameTextBox.setText(user);
			}
		});
		usernameTextBox.setColumns(10);
		usernameTextBox.setBounds(157, 89, 127, 20);
		mainWindow.getContentPane().add(usernameTextBox);
		
		jdbcDriverLabel = new JLabel("JDBC Driver:");
		jdbcDriverLabel.setBounds(38, 43, 73, 14);
		mainWindow.getContentPane().add(jdbcDriverLabel);
		
		databaseURLLabel = new JLabel("Database URL:");
		databaseURLLabel.setBounds(38, 68, 83, 14);
		mainWindow.getContentPane().add(databaseURLLabel);
		
		jdbcDropDownBox = new JComboBox();
		jdbcDropDownBox.setModel(new DefaultComboBoxModel(new String[] {"com.mysql.jdbc.Driver", "oracle.jdbc.driver.OracleDriver", "COM.ibm.db2.jdbc.net.DB2Driver", "	com.sybase.jdbc.SybDriver"}));
		jdbcDropDownBox.setBounds(127, 39, 157, 22);
		mainWindow.getContentPane().add(jdbcDropDownBox);
		
		databaseURLDropDownBox = new JComboBox();
		databaseURLDropDownBox.setModel(new DefaultComboBoxModel(new String[] {"jdbc:mysql://localhost:3306/project3 ", " "}));
		databaseURLDropDownBox.setBounds(127, 64, 157, 22);
		mainWindow.getContentPane().add(databaseURLDropDownBox);
		
		commandTextBox = new JTextArea();
		commandTextBox.setLineWrap(true);
		commandTextBox.setBounds(294, 38, 381, 128);
		mainWindow.getContentPane().add(commandTextBox);
		
		closeConnectionButton = new JButton("Close Connection!");
		closeConnectionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Check for active connection 
				if (connection2DB == true) {
					try {
						// Empty any results in table 
						clearTable();
						// Update connection status settings 
						connectionStatusActiveLabel.setForeground(Color.RED);
						connectionStatusActiveLabel.setText("Not connected :c");
						connection2DB = false; 
						connection.close();
						JOptionPane.showMessageDialog(null, "Connection closed!\n");
						
					} catch (SQLException e2) {
						e2.printStackTrace();
					} 
				}
				else {
					JOptionPane.showMessageDialog(null, "No active connection to close\n");
				}
			}
		});
		closeConnectionButton.setForeground(Color.RED);
		closeConnectionButton.setBounds(223, 428, 175, 23);
		mainWindow.getContentPane().add(closeConnectionButton);
		
		exitButton = new JButton("Exit Application");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Before closing look for active connection 
				if (connection2DB == true) {
					try {
						// Close connection 
						connection.close();
					} catch (SQLException e2) {
						e2.printStackTrace();
					} 
				}
				// Shutdown the system
				System.exit(0);
			}
		});
		exitButton.setForeground(Color.RED);
		exitButton.setBounds(408, 428, 175, 23);
		mainWindow.getContentPane().add(exitButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 240, 640, 177);
		mainWindow.getContentPane().add(scrollPane);
		
		responseTextBox = new JTable();
		scrollPane.setViewportView(responseTextBox);
		responseTextBox.setEnabled(false);
		responseTextBox.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		responseTextBox.setColumnSelectionAllowed(true);
		responseTextBox.setCellSelectionEnabled(true);
		responseTextBox.setFillsViewportHeight(true);
	}
	

//	  _____                                        __                  _   _                 
//	 |  __ \                                      / _|                | | (_)                
//	 | |__) | __ ___   __ _ _ __ __ _ _ __ ___   | |_ _   _ _ __   ___| |_ _  ___  _ __  ___ 
//	 |  ___/ '__/ _ \ / _` | '__/ _` | '_ ` _ \  |  _| | | | '_ \ / __| __| |/ _ \| '_ \/ __|
//	 | |   | | | (_) | (_| | | | (_| | | | | | | | | | |_| | | | | (__| |_| | (_) | | | \__ \
//	 |_|   |_|  \___/ \__, |_|  \__,_|_| |_| |_| |_|  \__,_|_| |_|\___|\__|_|\___/|_| |_|___/
//	                   __/ |                                                                 
//	                  |___/                                                                  

	
	// Send the SQL statments to the server
	public void sendCommands() {

		int i = 0;
		int invoke = 0; 
		long id; 
		int columnCount = 0; 
		int rowCount = 0; 
		String s; 
        String success = "Success";
        Object o = success;

		// If no command is present 
		if (command.matches("")) {
			JOptionPane.showMessageDialog(null, 
                    "Please enter a valid command silly goose\n", 
                    "Warning!", 
                    JOptionPane.WARNING_MESSAGE);
		}
		
		try {
			// Handling of query command 
			if (command.toLowerCase().contains("select")) {   
				try {
					// Conduct the query on connected DB
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
					resultSet = statement.executeQuery(command);
					
					// Will get the names of the columns 
                	rsmd = resultSet.getMetaData();
                	columnCount = rsmd.getColumnCount(); 
                	
                  	// Move the cursor to last rowto get number of rows 
                  	//resultSet.last(); 
                  	//rowCount = resultSet.getRow();
                  	//resultSet.first(); 
                  	
                  	// Configure the Jtable acccording to db size
                  	responseTextBox.setEnabled(true);
                  	responseTextBox.setModel(new DefaultTableModel(0,columnCount));	// <- This must be 1,col so that it output no more than the needed number of rows/col
                  	//responseTextBox.setModel(new DefaultTableModel(rowCount, columnCount));
          	
                  	// Array of objets size of columnCount 
                	Object[] row = new Object[columnCount]; 
                  	
					// Retrieve the rows and set them in table 
                    while (resultSet.next()) {
                    	
                    	// Retrieve the rows from resultSet and store in obj array 
                    	for (i = 1; i <= columnCount; i++) {
                    		row[i - 1] = resultSet.getObject(i);
                        	//((DefaultTableModel) responseTextBox.getModel()).insertRow(resultSet.getRow() - 1 , row);
                    	}
                    	// Insert the newest row that has been read
                    	((DefaultTableModel) responseTextBox.getModel()).insertRow(resultSet.getRow() - 1 , row);
                        
                    }
                    
                    // Used to update Table column model 
                    JTableHeader header = responseTextBox.getTableHeader(); 
                    TableColumnModel colMod = header.getColumnModel(); 
                    TableColumn tabCol;

                    // Lastly we will insert the column names in the table 
                	// Get the names of columns and set in the table 
                  	for (i = 1; i < columnCount + 1; i++) {
                  		
                  		// Store the column name as an object 
                		Object col = rsmd.getColumnName(i);
                  		
                		//responseTextBox.setValueAt(col, 0, i - 1); // Sets the column name inside the rows rather than header
                		
                		// Get current value, change, update 
                		tabCol = colMod.getColumn(i - 1);
                        tabCol.setHeaderValue(col);
                        header.repaint();
                  		
                  	}
                    
				} catch (IllegalStateException | SQLException e1) {
					// Display fault 
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Command error!\n", 0);
					// Clear table  
					clearTable(); 
				}
			}
			// Handling of insert command 
			else if (command.toLowerCase().contains("insert")) {
				try {
					// Conduct the query
					statement = connection.createStatement();
					invoke = statement.executeUpdate(command);
                  	
                  	// Configure the Jtable acccording to db size
                  	responseTextBox.setEnabled(true);
                  	responseTextBox.setModel(new DefaultTableModel(1,1));	
          	
                  	// Setup String object to display  
                  	s = "The number of rows affected: " + String.valueOf(invoke);

                    // Insert the string showing alteration into row 
                    ((DefaultTableModel) responseTextBox.getModel()).insertRow(1, new Object[] {s});
                    
                    // Retrieve the address of header in table 
                    JTableHeader header = responseTextBox.getTableHeader(); 
                    TableColumnModel colMod = header.getColumnModel(); 
                  	TableColumn tabCol = colMod.getColumn(0);  
                    
                  	// Set custom string and update
                    tabCol.setHeaderValue(o);
                    header.repaint();
                  		
               	}
				catch (IllegalStateException | SQLException e1) {
					// Display fault 
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Command error!\n", 0);
					// Clear table  
					clearTable(); 
				}
			}
			// Handling of update command
			else if (command.toLowerCase().contains("update")) {
				try {
					// Conduct the query
					statement = connection.createStatement();
					invoke = statement.executeUpdate(command);
                  	
                  	// Configure the Jtable acccording to db size
                  	responseTextBox.setEnabled(true);
                  	responseTextBox.setModel(new DefaultTableModel(1,1));	
          	
                  	// Setup String object to display  
                  	s = "The number of rows affected: " + String.valueOf(invoke);

                    // Insert the string showing alteration into row 
                    ((DefaultTableModel) responseTextBox.getModel()).insertRow(1, new Object[] {s});
                    
                    // Retrieve the address of header in table 
                    JTableHeader header = responseTextBox.getTableHeader(); 
                    TableColumnModel colMod = header.getColumnModel(); 
                  	TableColumn tabCol = colMod.getColumn(0);  
                    
                  	// Set custom string and update
                    tabCol.setHeaderValue(o);
                    header.repaint();
                  		
               	}
				catch (IllegalStateException | SQLException e1) {
					// Display fault 
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Command error!\n", 0);
					// Clear table  
					clearTable(); 
				}
			}
			// Handling of delete command 
			else if (command.toLowerCase().contains("delete")) {
				try {
					// Conduct the query
					statement = connection.createStatement();
					invoke = statement.executeUpdate(command);
                  	
                  	// Configure the Jtable acccording to db size
                  	responseTextBox.setEnabled(true);
                  	responseTextBox.setModel(new DefaultTableModel(1,1));	
          	
                  	// Setup String object to display  
                  	s = "The number of rows affected: " + String.valueOf(invoke);

                    // Insert the string showing alteration into row 
                    ((DefaultTableModel) responseTextBox.getModel()).insertRow(1, new Object[] {s});
                    
                    // Retrieve the address of header in table 
                    JTableHeader header = responseTextBox.getTableHeader(); 
                    TableColumnModel colMod = header.getColumnModel(); 
                  	TableColumn tabCol = colMod.getColumn(0);  
                    
                  	// Set custom string and update
                    tabCol.setHeaderValue(o);
                    header.repaint();
                  		
               	}
				catch (IllegalStateException | SQLException e1) {
					// Display fault 
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Command error!\n", 0);
					// Clear table  
					clearTable(); 
				}
			}
			
		} catch (Exception e1) {
			// Error in handling statement protocol
			System.out.println("Error in command functionality()\n");
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Un-found error\n", 0);
		}
	}
	
	// Clear the table and model
	public void clearTable() {
		
		try {
			// Get our tables current model 
			DefaultTableModel dm = (DefaultTableModel) responseTextBox.getModel(); 
			
		    // Move through the rows and columns and clear
			for (int i = 0; i < dm.getRowCount(); i++) {
		        for (int j = 0; j < dm.getColumnCount(); j++) {
		            dm.setValueAt("", i, j);
		        }
		    }
			// Generate an empty table 
			responseTextBox.setModel(new DefaultTableModel(0,0));
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error clearing table!\n", 0);
		}

	}
	
	// Generate connection with database 
	public boolean startConnection() throws SQLException, ClassNotFoundException, IOException {
	 
		try {
		dataSource = new MysqlDataSource();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error setting up datasource object!\n", 0);
		}
		// Make sure a user name was entered   
		if (user.compareTo("") != 0) {
			//Set the username field
			dataSource.setUser(user);
		}
		// Make sure a password was entered
		if (pass.compareTo("") != 0) {
			//Set the password field 
			dataSource.setPassword(pass);
		}
				
		try {

			// Set the dataSource configuration 
			dataSource.setServerName("localhost");
			dataSource.setPort(3306);
			dataSource.setUser(user);
			dataSource.setPassword(pass);
			dataSource.setDatabaseName("project3");
			dataSource.setCharacterEncoding("utf-8");
			
			// Generate the connection 
			connection = dataSource.getConnection();
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error in data source configuration!\n", 0);
			// Clear table 
			clearTable();
			
			// Update display settings on failure
			connectionStatusActiveLabel.setForeground(Color.RED);
			connectionStatusActiveLabel.setText("Not connected :c");
			connection2DB = false; 
			return false; 
		}
		
		try {
			// Establish database connection 
			DatabaseMetaData dbMetaData = connection.getMetaData();
			
			//System.out.println("Database connected!");
			//System.out.println("JDBC Driver name " + dbMetaData.getDriverName());
			//System.out.println("JDBC Driver version " + dbMetaData.getDriverVersion());
			//System.out.println("Driver Major version " + dbMetaData.getDriverMajorVersion());
			//System.out.println("Driver Minor version " + dbMetaData.getDriverMinorVersion());
			
			// Update display settings 
			Color green = new Color(0,153, 0);
			connectionStatusActiveLabel.setForeground(green);
			connectionStatusActiveLabel.setText("Connected as: " + user);
			connection2DB = true; 
			return true;
		}
		
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error establishing connection!\n", 0);
			
			// Update display settings 
			connectionStatusActiveLabel.setForeground(Color.RED);
			connectionStatusActiveLabel.setText("Not connected :c");
			connection2DB = false; 
			return false; 
		}
	}
}
