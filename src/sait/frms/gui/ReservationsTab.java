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
	
	private JTextField codeTextBox;
	private JTextField flightTextBox;
	private JTextField airlineTextBox;
	private JTextField costTextBox;
	private JTextField nameTextBox;
	private JTextField citizTextBox;
	private JComboBox statusComboBox;
	private JButton updateButton; 
	
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
	 * Creates the north panel.
	 * @return JPanel that goes in north.
	 */
	private JPanel createNorthPanel() 
	{
		JPanel panel = new JPanel();
		
		JLabel title = new JLabel("Reservations", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 45));
		panel.add(title);
		
		return panel;
	}
	
	
	
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
	
	private JPanel createEastPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JLabel label = new JLabel("Reservations", SwingConstants.CENTER);
		label.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		
		codeTextBox = new JTextField(14);
		codeTextBox.setEditable(false);
		codeTextBox.setBackground(Color.WHITE);
		codeTextBox.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		flightTextBox = new JTextField(14);
		flightTextBox.setEditable(false);
		flightTextBox.setBackground(Color.WHITE);
		flightTextBox.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		airlineTextBox = new JTextField(14);
		airlineTextBox.setEditable(false);
		airlineTextBox.setBackground(Color.WHITE);
		airlineTextBox.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		costTextBox = new JTextField(14);
		costTextBox.setEditable(false);
		costTextBox.setBackground(Color.WHITE);
		costTextBox.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		nameTextBox = new JTextField(14);
		nameTextBox.setBackground(Color.WHITE);
		nameTextBox.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		citizTextBox = new JTextField(14);
		citizTextBox.setBackground(Color.WHITE);
		citizTextBox.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
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
	
	
	private JPanel createResGridBox() {
		JPanel panel = new JPanel(new GridBagLayout());
		

		GridBagConstraints codeLabelCon = new GridBagConstraints();
		JLabel codeLabel = new JLabel("Code:");
		codeLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		codeLabelCon.anchor = GridBagConstraints.LINE_END;
		codeLabelCon.gridx = 0;
		codeLabelCon.gridy = 0;
		codeLabelCon.insets = new Insets(5,0,5,0);
		panel.add(codeLabel, codeLabelCon);

		GridBagConstraints codeTFCon = new GridBagConstraints();
		codeTFCon.gridx = 1;
		codeTFCon.gridy = 0;
		codeTFCon.insets = new Insets(5,5,5,10);
		panel.add(codeTextBox, codeTFCon);
		
		GridBagConstraints flightLabelCon = new GridBagConstraints();
		JLabel flightLabel = new JLabel("Flight:");
		flightLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		flightLabelCon.anchor = GridBagConstraints.LINE_END;
		flightLabelCon.gridx = 0;
		flightLabelCon.gridy = 1;
		flightLabelCon.insets = new Insets(5,0,5,0);
		panel.add(flightLabel, flightLabelCon);
		
		GridBagConstraints flightTFCon = new GridBagConstraints();
		flightTFCon.gridx = 1;
		flightTFCon.gridy = 1;
		flightTFCon.insets = new Insets(5,5,5,10);
		panel.add(flightTextBox, flightTFCon);
		
		GridBagConstraints airlineLabelCon = new GridBagConstraints();
		JLabel airlineLabel = new JLabel("Airline:");
		airlineLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		airlineLabelCon.anchor = GridBagConstraints.LINE_END;
		airlineLabelCon.gridx = 0;
		airlineLabelCon.gridy = 2;
		airlineLabelCon.insets = new Insets(5,0,5,0);
		panel.add(airlineLabel, airlineLabelCon);

		GridBagConstraints airlineTFCon = new GridBagConstraints();
		
		airlineTFCon.gridx = 1;
		airlineTFCon.gridy = 2;
		airlineTFCon.insets = new Insets(5,5,5,10);
		panel.add(airlineTextBox, airlineTFCon);
		
		GridBagConstraints costLabelCon = new GridBagConstraints();
		JLabel costLabel = new JLabel("Cost:");
		costLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		costLabelCon.anchor = GridBagConstraints.LINE_END;
		costLabelCon.gridx = 0;
		costLabelCon.gridy = 3;
		costLabelCon.insets = new Insets(5,0,5,0);
		panel.add(costLabel, costLabelCon);

		GridBagConstraints costTFCon = new GridBagConstraints();
		costTFCon.gridx = 1;
		costTFCon.gridy = 3;
		costTFCon.insets = new Insets(5,5,5,10);
		panel.add(costTextBox, costTFCon);
		
		GridBagConstraints nameLabelCon = new GridBagConstraints();
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		nameLabelCon.anchor = GridBagConstraints.LINE_END;
		nameLabelCon.gridx = 0;
		nameLabelCon.gridy = 4;
		nameLabelCon.insets = new Insets(5,0,5,0);
		panel.add(nameLabel, nameLabelCon);

		GridBagConstraints nameTFCon = new GridBagConstraints();
		nameTFCon.gridx = 1;
		nameTFCon.gridy = 4;
		nameTFCon.insets = new Insets(5,5,5,10);
		panel.add(nameTextBox, nameTFCon);
		
		GridBagConstraints citizLabelCon = new GridBagConstraints();
		JLabel citizLabel = new JLabel("Citizenship:");
		citizLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		citizLabelCon.anchor = GridBagConstraints.LINE_END;
		citizLabelCon.gridx = 0;
		citizLabelCon.gridy = 5;
		citizLabelCon.insets = new Insets(5,10,5,0);
		panel.add(citizLabel, citizLabelCon);

		GridBagConstraints citizTFCon = new GridBagConstraints();
		citizTFCon.gridx = 1;
		citizTFCon.gridy = 5;
		citizTFCon.insets = new Insets(5,5,5,10);
		panel.add(citizTextBox, citizTFCon);
		
		GridBagConstraints statusLabelCon = new GridBagConstraints();
		JLabel statusLabel = new JLabel("Status:");
		statusLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		statusLabelCon.anchor = GridBagConstraints.LINE_END;
		statusLabelCon.gridx = 0;
		statusLabelCon.gridy = 6;
		statusLabelCon.insets = new Insets(5,10,5,0);
		panel.add(statusLabel, statusLabelCon);

		GridBagConstraints statusTFCon = new GridBagConstraints();
		statusTFCon.gridx = 1;
		statusTFCon.gridy = 6;
		statusTFCon.insets = new Insets(5,5,5,10);
		statusTFCon.fill = GridBagConstraints.HORIZONTAL;
		panel.add(statusComboBox, statusTFCon);
		
		
		return panel;
	}
	
	private JPanel updateButton() {
		JPanel panel = new JPanel();
		
		updateButton = new JButton("Update");
		updateButton.setPreferredSize(new Dimension(225, 25));
		updateButton.addActionListener(new ReservationActionListener());
		panel.add(updateButton);
		return panel;
	}
	
	private JPanel createSearchGridBox() {
		JPanel panel = new JPanel(new GridBagLayout());
		
		GridBagConstraints searchCodeLabelCon = new GridBagConstraints();
		JLabel searchCodeLabel = new JLabel("Code:");
		searchCodeLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		searchCodeLabelCon.anchor = GridBagConstraints.LINE_END;
		searchCodeLabelCon.gridx = 0;
		searchCodeLabelCon.gridy = 0;
		searchCodeLabelCon.insets = new Insets(5,0,5,0);
		panel.add(searchCodeLabel, searchCodeLabelCon);

		GridBagConstraints searchCodeTFCon = new GridBagConstraints();
		searchCodeTFCon.gridx = 1;
		searchCodeTFCon.gridy = 0;
		searchCodeTFCon.insets = new Insets(5,5,5,10);
		panel.add(searchCodeTF, searchCodeTFCon);
		
		GridBagConstraints searchAirlineLabelCon = new GridBagConstraints();
		JLabel searchAirlineLabel = new JLabel("Airline:");
		searchAirlineLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		searchAirlineLabelCon.anchor = GridBagConstraints.LINE_END;
		searchAirlineLabelCon.gridx = 0;
		searchAirlineLabelCon.gridy = 1;
		searchAirlineLabelCon.insets = new Insets(5,0,5,0);
		panel.add(searchAirlineLabel, searchAirlineLabelCon);

		GridBagConstraints searchAirlineTFCon = new GridBagConstraints();
		searchAirlineTFCon.gridx = 1;
		searchAirlineTFCon.gridy = 1;
		searchAirlineTFCon.insets = new Insets(5,5,5,10);
		panel.add(searchAirlineTF, searchAirlineTFCon);
		
		GridBagConstraints searchNameLabelCon = new GridBagConstraints();
		JLabel searchNameLabel = new JLabel("Name:");
		searchNameLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
		searchNameLabelCon.anchor = GridBagConstraints.LINE_END;
		searchNameLabelCon.gridx = 0;
		searchNameLabelCon.gridy = 2;
		searchNameLabelCon.insets = new Insets(5,0,5,0);
		panel.add(searchNameLabel, searchNameLabelCon);

		GridBagConstraints searchNameTFCon = new GridBagConstraints();
		searchNameTFCon.gridx = 1;
		searchNameTFCon.gridy = 2;
		searchNameTFCon.insets = new Insets(5,5,5,10);
		panel.add(searchNameTF, searchNameTFCon);
		
		
		return panel;
	}
	
	private JPanel searchButton() {
		JPanel panel = new JPanel();
		
		searchButton = new JButton("Find Reservations");
		searchButton.setPreferredSize(new Dimension(890, 30));
		searchButton.addActionListener(new ReservationActionListener());
		panel.add(searchButton);
		
		return panel;
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