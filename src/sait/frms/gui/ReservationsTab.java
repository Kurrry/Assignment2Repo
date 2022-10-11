package sait.frms.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sait.frms.manager.ReservationManager;
import sait.frms.problemdomain.Reservation;

/**
 * Holds the components for the reservations tab.
 * 
 */
public class ReservationsTab extends TabBase {
	/**
	 * Instance of reservation manager.
	 */
	private ReservationManager reservationManager;
	
	private JList<Reservation> reservationsList;
	
	private DefaultListModel<Reservation> reservationsModel;
	
	/**
	 * Textfields and button used in east(Reservations) panel.
	 */
	private JTextField codeTextBox;
	private JTextField flightTextBox;
	private JTextField airlineTextBox;
	private JTextField costTextBox;
	private JTextField nameTextBox;
	private JTextField citizTextBox;
	private JComboBox statusComboBox;
	private JButton updateButton; 
	
	/**
	 * Textfields and button used in south(Search) panel
	 */
	private JTextField searchCodeTF;
	private JTextField searchAirlineTF;
	private JTextField searchNameTF;
	private JButton searchButton;
	
	/**
	 * Creates the components for reservations tab.
	 */
	public ReservationsTab(ReservationManager reservationManager) {
		this.reservationManager = reservationManager;
		panel.setLayout(new BorderLayout());
		
		JPanel northPanel = createNorthPanel();
		panel.add(northPanel, BorderLayout.NORTH);
		
		JPanel centerPanel = createCenterPanel();
		panel.add(centerPanel, BorderLayout.CENTER);
		
		JPanel eastPanel = createEastPanel();
		panel.add(eastPanel, BorderLayout.EAST);
		
		JPanel southPanel = createSouthPanel();
		panel.add(southPanel, BorderLayout.SOUTH);
	}
	
	
	/**
	 * Creates the north (title) panel used in the ReservationsTab.
	 * @return JPanel northPanel.
	 */
	private JPanel createNorthPanel() 
	{
		JPanel panel = new JPanel();
		
		JLabel title = new JLabel("Reservations", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 45));
		panel.add(title);
		
		return panel;
	}
	
	/**
	 * Creates the center (List) panel used in ReservationsTab. 
	 * @return JPanel centerPanel
	 */
	private JPanel createCenterPanel() {
		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());

		reservationsModel = new DefaultListModel<>();
		reservationsList = new JList<>(reservationsModel);

		// User can only select one item at a time.
		reservationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Wrap JList in JScrollPane so it is scrollable.
		JScrollPane scrollPane = new JScrollPane(this.reservationsList);

		reservationsList.addListSelectionListener(new MyListSelectionListener());

		panel.add(scrollPane, BorderLayout.CENTER);

		return panel;
	}
	
	/**
	 * Creates the east (Reservation) panel used in ReservationsTab.
	 * @return JPanel eastPanel
	 */
	private JPanel createEastPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JLabel label = new JLabel("Reservations", SwingConstants.CENTER);
		label.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		
		codeTextBox = createReservationTF();
		codeTextBox.setEditable(false);
		
		flightTextBox = createReservationTF();
		flightTextBox.setEditable(false);
		
		airlineTextBox = createReservationTF();
		airlineTextBox.setEditable(false);
		
		costTextBox = createReservationTF();
		costTextBox.setEditable(false);
		
		nameTextBox = createReservationTF();
		
		citizTextBox = createReservationTF();
		
		String[] options = {"Active", "Inactive"};
		statusComboBox = new JComboBox(options);
		statusComboBox.setBackground(Color.WHITE);
		statusComboBox.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		JPanel content = createResGridBox();
		JPanel bPanel = updateButton();
		
		panel.add(bPanel, BorderLayout.SOUTH);
		panel.add(content, BorderLayout.CENTER);
		panel.add(label, BorderLayout.NORTH);	
		return panel;
	}
	
	/**
	 * Creates the south (Search) panel used in ReservationsTab.
	 * @return JPanel southPanel
	 */
	private JPanel createSouthPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JLabel label = new JLabel("Search", SwingConstants.CENTER);
		label.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		
		searchCodeTF = new JTextField();
		searchCodeTF.setColumns(70);
		
		searchAirlineTF = new JTextField();
		searchAirlineTF.setColumns(70);
		
		searchNameTF = new JTextField();
		searchNameTF.setColumns(70);
		
		JPanel content = createSearchGridBox();
		JPanel sPanel = searchButton();
		
		panel.add(sPanel, BorderLayout.SOUTH);
		panel.add(content, BorderLayout.CENTER);
		panel.add(label, BorderLayout.NORTH);
		return panel;
	}
	
	/**
	 * Creates and returns panel used in eastPanel, contentPanel. Creates grid with labels on left and textfields/combobox on the right.
	 * 
	 * General row construction:
	 * 	
	 * -Label
	 * JLabel Label = labelGenerator("Code:"); 			// Sends string to be generated and formatted in a label
	 * GridBagConstraints LabelCon= createLabelCon(0); 	// Generates the label constraints, int sent is the row number
	 * panel.add(Label, LabelCon); 						// Adds label with constraints to panel
	 * 
	 * -Panel
	 * GridBagConstraints TFCon = createFieldCon(0); 	// Generates the field constraints, int sent is the row number
	 * panel.add(TextBox, TFCon);						// Adds textfield/combobox with constraints to panel
	 * 
	 * @return JPanel reservationContentPanel
	 */
	private JPanel createResGridBox() {
		JPanel panel = new JPanel(new GridBagLayout());
		
		JLabel codeLabel = labelGenerator("Code:");
		GridBagConstraints codeLabelCon= createLabelCon(0);
		panel.add(codeLabel, codeLabelCon);

		GridBagConstraints codeTFCon = createFieldCon(0);
		panel.add(codeTextBox, codeTFCon);
		
		JLabel flightLabel = labelGenerator("Flight:");
		GridBagConstraints flightLabelCon = createLabelCon(1);
		panel.add(flightLabel, flightLabelCon);
		
		GridBagConstraints flightTFCon = createFieldCon(1);
		panel.add(flightTextBox, flightTFCon);
		
		JLabel airlineLabel = labelGenerator("Airline:");
		GridBagConstraints airlineLabelCon = createLabelCon(2);
		panel.add(airlineLabel, airlineLabelCon);

		GridBagConstraints airlineTFCon = createFieldCon(2);
		panel.add(airlineTextBox, airlineTFCon);
		
		JLabel costLabel = labelGenerator("Cost:");
		GridBagConstraints costLabelCon = createLabelCon(3);
		panel.add(costLabel, costLabelCon);

		GridBagConstraints costTFCon = createFieldCon(3);
		panel.add(costTextBox, costTFCon);
		
		JLabel nameLabel = labelGenerator("Name:");
		GridBagConstraints nameLabelCon = createLabelCon(4);
		panel.add(nameLabel, nameLabelCon);

		GridBagConstraints nameTFCon = createFieldCon(4);
		panel.add(nameTextBox, nameTFCon);
		
		JLabel citizLabel = labelGenerator("Citizenship:");
		GridBagConstraints citizLabelCon = createLabelCon(5);
		panel.add(citizLabel, citizLabelCon);

		GridBagConstraints citizTFCon = createFieldCon(5);
		panel.add(citizTextBox, citizTFCon);
		
		JLabel statusLabel = labelGenerator("Status:");
		GridBagConstraints statusLabelCon = createLabelCon(6);
		panel.add(statusLabel, statusLabelCon);

		GridBagConstraints statusTFCon = createFieldCon(6);
		statusTFCon.fill = GridBagConstraints.HORIZONTAL;
		panel.add(statusComboBox, statusTFCon);
		
		return panel;
	}
	
	/**
	 * Creates and returns panel used in eastPanel, bPanel. Creates button used to update a reservation.
	 * @return JPanel updateButton
	 */
	private JPanel updateButton() {
		JPanel panel = new JPanel();
		
		updateButton = new JButton("Update");
		updateButton.setPreferredSize(new Dimension(225, 25));
		updateButton.addActionListener(new ReservationActionListener());
		panel.add(updateButton);
		return panel;
	}
	
	/**
	 * Creates and returns panel used in southPanel, contentPanel. Creates grid with labels on left and textfields on the right.
	 * 
	 * General row construction:
	 * 	
	 * -Label
	 * JLabel Label = labelGenerator("Code:"); 			// Sends string to be generated and formatted in a label
	 * GridBagConstraints LabelCon= createLabelCon(0); 	// Generates the label constraints, int sent is the row number
	 * panel.add(Label, LabelCon); 						// Adds label with constraints to panel
	 * 
	 * -Panel
	 * GridBagConstraints TFCon = createFieldCon(0); 	// Generates the field constraints, int sent is the row number
	 * panel.add(TextBox, TFCon);						// Adds textfield with constraints to panel
	 * 
	 * @return JPanel searchContentPanel
	 * @return
	 */
	private JPanel createSearchGridBox() {
		JPanel panel = new JPanel(new GridBagLayout());
		
		JLabel searchCodeLabel = labelGenerator("Code:");
		GridBagConstraints searchCodeLabelCon = createLabelCon(0);
		panel.add(searchCodeLabel, searchCodeLabelCon);

		GridBagConstraints searchCodeTFCon = createFieldCon(0);
		panel.add(searchCodeTF, searchCodeTFCon);
		
		JLabel searchAirlineLabel = labelGenerator("Airline:");
		GridBagConstraints searchAirlineLabelCon = createLabelCon(1);
		panel.add(searchAirlineLabel, searchAirlineLabelCon);

		GridBagConstraints searchAirlineTFCon = createFieldCon(1);
		panel.add(searchAirlineTF, searchAirlineTFCon);
		
		JLabel searchNameLabel = labelGenerator("Name:");
		GridBagConstraints searchNameLabelCon = createLabelCon(2);
		panel.add(searchNameLabel, searchNameLabelCon);

		GridBagConstraints searchNameTFCon = createFieldCon(2);
		panel.add(searchNameTF, searchNameTFCon);
		
		
		return panel;
	}
	
	/**
	 * Creates and returns panel used in southPanel, sPanel. Creates button used to search for a reservation.
	 * @return JPanel searchButton
	 */
	private JPanel searchButton() {
		JPanel panel = new JPanel();
		
		searchButton = new JButton("Find Reservations");
		searchButton.setPreferredSize(new Dimension(890, 30));
		searchButton.addActionListener(new ReservationActionListener());
		panel.add(searchButton);
		
		return panel;
	}
	
	/**
	 * Generates and returns a formatted textfield used in the eastPanel contentPanel
	 * @return JTextField textfield
	 */
	private JTextField createReservationTF() {
		JTextField textfield = new JTextField(14);
		textfield.setBackground(Color.WHITE);
		textfield.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		return textfield;
	}
	
	/**
	 * Generates and returns the GridBagConstraints used for the labels in the southPanel & eastPanel, contentPanel
	 * Takes in the desired row number of the label
	 * @param y Row number
	 * @return GridBagConstraints constraints
	 */
	private GridBagConstraints createLabelCon(int y) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.LINE_END;
		constraints.gridx = 0;
		constraints.gridy = y;
		constraints.insets = new Insets(5,0,5,0);
		
		return constraints;
	}
	
	/**
	 * Generates and returns the GridBagConstraints used for the textfields/combobox in the southPanel & eastPanel, contentPanel
	 * Takes in the desired row number of the textfield/combobox
	 * @param y Row number
	 * @return GridBagConstraints constraints
	 */
	private GridBagConstraints createFieldCon(int y) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = y;
		constraints.insets = new Insets(5,5,5,10);
		
		return constraints;
	}
	
	/**
	 * Generates and returns a formatted label used to hold the names of the textfield/combobox
	 * @param text Label text
	 * @return JLabel Formatted label
	 */
	private JLabel labelGenerator(String text) {
		JLabel label = new JLabel(text);
		label.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		return label;
	}
	
	
	
	
	
	
	
	private class ReservationActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == updateButton) {
				System.out.println(nameTextBox.getText() + "\n" + citizTextBox.getText());
			}
			
		}
		
	}

	private class MyListSelectionListener implements ListSelectionListener {
		/**
		 * Called when user selects an item in the JList.
		 */
		@Override
		public void valueChanged(ListSelectionEvent e) {

		}

	}
}