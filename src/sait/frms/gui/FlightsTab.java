package sait.frms.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.*;

import sait.frms.manager.FlightManager;
import sait.frms.manager.ReservationManager;
import sait.frms.problemdomain.Flight;

/**
 * Holds the components for the flights tab.
 * 
 */
public class FlightsTab extends TabBase 
{
	/**
	 * Instance of flight manager.
	 */
	private FlightManager flightManager;
	
	/**
	 * Instance of reservation manager.
	 */
	private ReservationManager reservationManager;
	
	/**
	 * List of flights.
	 */
	private JList<Flight> flightsList;
	
	private DefaultListModel<Flight> flightsModel;

	private JScrollPane scrollPane;
	private JButton btnReserve;
	private JButton btnFlightFind;
	private JTextField txtReserveList;
	private JComboBox<String> cBoxFlightFind;


	/**
	 * Creates the components for flights tab.
	 */
	/**
	 * Creates the components for flights tab.
	 * 
	 * @param flightManager Instance of FlightManager.
	 * @param reservationManager Instance of ReservationManager
	 */
	public FlightsTab(FlightManager flightManager, ReservationManager reservationManager) {
		this.flightManager = flightManager;
		this.reservationManager = reservationManager;
		
		panel.setLayout(new BorderLayout());

		JPanel northPanel = createNorthPanel();
		panel.add(northPanel, BorderLayout.NORTH);

		JPanel centerPanel = createFlightsPanel();
		JPanel reservePanel = createReservePanel();
		JPanel flightFinderPanel = createFlightFinderPanel();

		flightFinderPanel.setPreferredSize(new Dimension(900, 200));
		reservePanel.setPreferredSize(new Dimension(250, 400));
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(reservePanel, BorderLayout.EAST);
		panel.add(flightFinderPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Creates the north panel.
	 * @return JPanel that goes in north.
	 */
	private JPanel createNorthPanel() 
	{
		JPanel panel = new JPanel();
		
		JLabel title = new JLabel("Flights", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		panel.add(title);
		
		return panel;
	}
	
	/**
	 * Creates the center panel.
	 * @return JPanel that goes in center.
	 */
	private JPanel createFlightsPanel()
	{
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		
		flightsModel = new DefaultListModel<>();
		flightsList = new JList<>(flightsModel);
		
		// User can only select one item at a time.
		flightsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// Wrap JList in JScrollPane so it is scrollable.
		scrollPane = new JScrollPane(this.flightsList);
		
		flightsList.addListSelectionListener(new MyListSelectionListener());
		
		panel.add(scrollPane);

		return panel;
	}

	private JPanel createReservePanel() {
		JPanel panel = new JPanel();
		JLabel reserve = new JLabel("Reserve");
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 25));
		reserve.setFont(new Font("Serif", Font.PLAIN, 39));
		
		panel.add(reserve);
		panel.add(reserveInfoList());
		panel.add(reserveButton());
		return panel;
	}

	private JButton reserveButton() {
		btnReserve = new JButton("Reserve");
		btnReserve.addActionListener(new reserveButtonListener());
		btnReserve.setPreferredSize(new Dimension(225, 25));
		return btnReserve;
	}

	private JPanel reserveListPanels(String label) {
		JPanel panel = new JPanel();
		JLabel labelText = new JLabel(label);
		txtReserveList = new JTextField(14);

		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panel.setPreferredSize(new Dimension(250, 30));
		txtReserveList.setFont(new Font("Times New Roman", Font.BOLD, 15));
		txtReserveList.setEditable(false);

		if(label.equals("Citizenship:") || label.equals("Name:")) {
			txtReserveList.setEditable(true);
		}


		panel.add(labelText);
		panel.add(txtReserveList);
		return panel;
	}

	private JPanel reserveInfoList() {
		JPanel panel = new JPanel(new GridBagLayout());

		panel.add(reserveListPanels("Flight:"), createCon(0));
		panel.add(reserveListPanels("Airline:"), createCon(1));
		panel.add(reserveListPanels("Day:"), createCon(2));
		panel.add(reserveListPanels("Time:"), createCon(3));
		panel.add(reserveListPanels("Cost:"), createCon(4));
		panel.add(reserveListPanels("Name:"), createCon(5));
		panel.add(reserveListPanels("Citizenship:"), createCon(6));

		return panel;
	}

	private JPanel createFlightFinderPanel() {
		JPanel fFPanel = new JPanel();
		JLabel flightFinder = new JLabel("Flight Finder");
		fFPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
		flightFinder.setFont(new Font("Serif", Font.PLAIN, 34));

		fFPanel.add(flightFinder);
		fFPanel.add(fFInfoList());


		return fFPanel;
	}

	private JPanel createFFComboBox(String[] options, String label) {
		cBoxFlightFind = new JComboBox<>(options);
		JPanel fFBoxPanel = new JPanel();
		JLabel fFLabel = new JLabel(label);

		fFBoxPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		fFBoxPanel.setPreferredSize(new Dimension(800, 30));
		cBoxFlightFind.setPreferredSize(new Dimension(750, 25));
		cBoxFlightFind.addActionListener(new fFComboBoxListener());
		fFBoxPanel.add(fFLabel);
		fFBoxPanel.add(cBoxFlightFind);

		return fFBoxPanel;
	}

	private JPanel fFInfoList() {
		JPanel fFPanel = new JPanel(new GridBagLayout());

		String[] options = {"test1", "test2", "test3"};

		fFPanel.add(createFFComboBox(options, "From:"), createCon(0));
		fFPanel.add(createFFComboBox(options, "To:"), createCon(1));
		fFPanel.add(createFFComboBox(options, "Day:"), createCon(2));
		fFPanel.add(fFButton(), createCon(3));

		return fFPanel;
	}

	private JPanel fFButton() {
		JPanel buttonPanel = new JPanel();
		btnFlightFind = new JButton("Find Flights");

		btnFlightFind.addActionListener(new fFButtonListener());
		btnFlightFind.setPreferredSize(new Dimension(790, 25));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setPreferredSize(new Dimension(890, 30));
		buttonPanel.add(btnFlightFind);

		return buttonPanel;
	}

	private GridBagConstraints createCon(int y) {
		GridBagConstraints constraint = new GridBagConstraints();
		constraint.gridx = 0;
		constraint.gridy = y;

		return constraint;
	}

	private class fFButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	private class fFComboBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	private class  reserveButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

		}
	}
	
	private class MyListSelectionListener implements ListSelectionListener 
	{
		/**
		 * Called when user selects an item in the JList.
		 */
		@Override
		public void valueChanged(ListSelectionEvent e) {
			
		}
		
	}
}
	/*private GridBagConstraints createGbc(int x, int y) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;

		gbc.anchor = (x == 0) ? GridBagConstraints.WEST : GridBagConstraints.EAST;
		gbc.fill = (x == 0) ? GridBagConstraints.BOTH
				: GridBagConstraints.HORIZONTAL;

		gbc.insets = (x == 0) ? WEST_INSETS : EAST_INSETS;
		gbc.weightx = (x == 0) ? 0.1 : 1.0;
		gbc.weighty = 1.0;
		return gbc;
	}*/