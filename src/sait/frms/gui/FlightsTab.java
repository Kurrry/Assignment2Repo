package sait.frms.gui;

import java.awt.*;
import java.util.Arrays;
import javax.swing.*;

import sait.frms.exception.NoCitizenshipException;
import sait.frms.exception.NoNameException;
import sait.frms.exception.NoSeatsAvailableException;
import sait.frms.manager.FlightManager;
import sait.frms.manager.ReservationManager;
import sait.frms.problemdomain.Flight;
import sait.frms.problemdomain.Reservation;

/**
 * Holds the components for the flights tab.
 */
public class FlightsTab extends TabBase {
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
    private JPanel jListPanel;

    private JScrollPane scrollPane;
    private JButton btnReserve;
    private JButton btnFlightFind;
    private JTextField flightReserveText;
    private JTextField airlineReserveText;
    private JTextField dayReserveText;
    private JTextField timeReserveText;
    private JTextField costReserveText;
    private JTextField nameReserveText;
    private JTextField citizenReserveText;
    private JComboBox<String> cBoxFromFlightFind;
    private JComboBox<String> cBoxToFlightFind;
    private JComboBox<String> cBoxDayFlightFind;
    private String[] toFromOptions;
    private static final String[] days = {"Any", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
            "Saturday", "Sunday"};

    /**
     * Creates the components for flights tab.
     *
     * @param flightManager      Instance of FlightManager.
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
     *
     * @return JPanel that goes in north.
     */
    private JPanel createNorthPanel() {
        JPanel panel = new JPanel();

        JLabel title = new JLabel("Flights", SwingConstants.CENTER);
        title.setFont(new Font("serif", Font.PLAIN, 29));
        panel.add(title);

        return panel;
    }

    /**
     * Creates the center panel.
     *
     * @return JPanel that goes in center.
     */
    private JPanel createFlightsPanel() {
        jListPanel = new JPanel();

        jListPanel.setLayout(new BorderLayout());

        flightsModel = new DefaultListModel<>();

        flightsList = new JList<>(flightsModel);

        // User can only select one item at a time.
        flightsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Wrap JList in JScrollPane so it is scrollable.
        scrollPane = new JScrollPane(this.flightsList);

        flightsList.addListSelectionListener(e -> {
            Flight tempFlight = flightsList.getSelectedValue();
            if (tempFlight != null) {
                flightReserveText.setText(tempFlight.getFlightCode());
                airlineReserveText.setText(tempFlight.getAirlineName());
                dayReserveText.setText(tempFlight.getWeekday());
                timeReserveText.setText(tempFlight.getTime());
                costReserveText.setText(String.valueOf(tempFlight.getCostPerSeat()));
            }
        });

        jListPanel.add(scrollPane);

        return jListPanel;
    }

    /**
     * create the east panel containing reserve info
     *
     * @return panel east panel
     */
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

    /**
     * method for creating the reserve button
     * @return btnReserve button for reserving a seat on a flight
     */
    private JButton reserveButton() {
        btnReserve = new JButton("Reserve");
        btnReserve.addActionListener(e -> {
            JFrame frame = new JFrame();
            frame.setSize(new Dimension(200, 200));
            Reservation temp = null;
            try {
                temp = reservationManager.makeReservation(flightsList.getSelectedValue(), nameReserveText.getText(),
                        citizenReserveText.getText());
            } catch (NoCitizenshipException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Citizenship entered. Please try again");
            } catch (NoSeatsAvailableException ex) {
                JOptionPane.showMessageDialog(frame, "No seats available on this flight. Please try again");
            } catch (NoNameException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid name entered. Please try again");
            }
            if (temp != null) {
                JOptionPane.showMessageDialog(frame, "Reservation created. You code is " + temp.getReservationCode());
            }
        });
        btnReserve.setPreferredSize(new Dimension(225, 25));
        return btnReserve;
    }

    /**
     * method for creating the flight textfield
     *
     * @return panel panel containing the flight textfield
     */
    private JPanel flightTextPanel() {
        JPanel panel = new JPanel();
        JLabel labelText = new JLabel("Flight:");
        flightReserveText = new JTextField(14);

        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel.setPreferredSize(new Dimension(250, 30));
        flightReserveText.setFont(new Font("Times New Roman", Font.BOLD, 15));
        flightReserveText.setEditable(false);

        panel.add(labelText);
        panel.add(flightReserveText);
        return panel;
    }

    /**
     * method for creating the airline textfield
     * @return panel panel containing the airline textfield
     */
    private JPanel airlineTextPanel() {
        JPanel panel = new JPanel();
        JLabel labelText = new JLabel("Airline:");
        airlineReserveText = new JTextField(14);

        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel.setPreferredSize(new Dimension(250, 30));
        airlineReserveText.setFont(new Font("Times New Roman", Font.BOLD, 15));
        airlineReserveText.setEditable(false);

        panel.add(labelText);
        panel.add(airlineReserveText);
        return panel;
    }

    /**
     * method for creating the day textfield
     * @return panel panel containing the day textfield
     */
    private JPanel dayTextPanel() {
        JPanel panel = new JPanel();
        JLabel labelText = new JLabel("Day:");
        dayReserveText = new JTextField(14);

        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel.setPreferredSize(new Dimension(250, 30));
        dayReserveText.setFont(new Font("Times New Roman", Font.BOLD, 15));
        dayReserveText.setEditable(false);

        panel.add(labelText);
        panel.add(dayReserveText);
        return panel;
    }

    /**
     * method for creating the time textfield
     * @return panel panel containing the time textfield
     */
    private JPanel timeTextPanel() {
        JPanel panel = new JPanel();
        JLabel labelText = new JLabel("Time:");
        timeReserveText = new JTextField(14);

        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel.setPreferredSize(new Dimension(250, 30));
        timeReserveText.setFont(new Font("Times New Roman", Font.BOLD, 15));
        timeReserveText.setEditable(false);

        panel.add(labelText);
        panel.add(timeReserveText);
        return panel;
    }

    /**
     * method for creating the cost textfield
     * @return panel panel containing the cost textfield
     */
    private JPanel costTextPanel() {
        JPanel panel = new JPanel();
        JLabel labelText = new JLabel("Cost:");
        costReserveText = new JTextField(14);

        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel.setPreferredSize(new Dimension(250, 30));
        costReserveText.setFont(new Font("Times New Roman", Font.BOLD, 15));
        costReserveText.setEditable(false);

        panel.add(labelText);
        panel.add(costReserveText);
        return panel;
    }

    /**
     * method for creating the name textfield
     * @return panel panel containing the name textfield
     */
    private JPanel nameTextPanel() {
        JPanel panel = new JPanel();
        JLabel labelText = new JLabel("Name:");
        nameReserveText = new JTextField(14);

        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel.setPreferredSize(new Dimension(250, 30));
        nameReserveText.setFont(new Font("Times New Roman", Font.BOLD, 15));
        nameReserveText.setEditable(true);

        panel.add(labelText);
        panel.add(nameReserveText);
        return panel;
    }

    /**
     * method for creating the citizen textfield
     * @return panel the panel containing citisenship textfield
     */

    private JPanel citizenTextPanel() {
        JPanel panel = new JPanel();
        JLabel labelText = new JLabel("Citizenship:");
        citizenReserveText = new JTextField(14);

        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel.setPreferredSize(new Dimension(250, 30));
        citizenReserveText.setFont(new Font("Times New Roman", Font.BOLD, 15));
        citizenReserveText.setEditable(true);

        panel.add(labelText);
        panel.add(citizenReserveText);
        return panel;
    }

    /**
     * panel containing editable and uneditable textfields with their appropriate labels.
     * used for formatting sub-panels.
     *
     * @return panel GridBag panel
     */
    private JPanel reserveInfoList() {
        JPanel panel = new JPanel(new GridBagLayout());

        panel.add(flightTextPanel(), createCon(0));
        panel.add(airlineTextPanel(), createCon(1));
        panel.add(dayTextPanel(), createCon(2));
        panel.add(timeTextPanel(), createCon(3));
        panel.add(costTextPanel(), createCon(4));
        panel.add(nameTextPanel(), createCon(5));
        panel.add(citizenTextPanel(), createCon(6));

        return panel;
    }

    /**
     * south panel containing flightFinder info
     *
     * @return fFpanel south panel
     */
    private JPanel createFlightFinderPanel() {
        JPanel fFPanel = new JPanel();
        JLabel flightFinder = new JLabel("Flight Finder");
        fFPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
        flightFinder.setFont(new Font("Serif", Font.PLAIN, 34));

        fFPanel.add(flightFinder);
        fFPanel.add(fFInfoList());


        return fFPanel;
    }

    /**
     * method to create the from combo box in the flight finder panel
     *
     * @return fFBoxPanel the panel housing xBoxFromFlightFind
     */
    private JPanel createFromFFComboBox() {
        toFromOptions = flightManager.getAirports().toArray(new String[0]);
        cBoxFromFlightFind = new JComboBox<>(toFromOptions);
        JPanel fFBoxPanel = new JPanel();
        JLabel fFLabel = new JLabel("From:");

        fFBoxPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        fFBoxPanel.setPreferredSize(new Dimension(800, 30));
        cBoxFromFlightFind.setPreferredSize(new Dimension(750, 25));
        fFBoxPanel.add(fFLabel);
        fFBoxPanel.add(cBoxFromFlightFind);

        return fFBoxPanel;
    }

    /**
     * method to create the to combo box in the flight finder panel
     * @return fFBoxPanel the panel housing cBoxToFlightFind
     */
    private JPanel createToFFComboBox() {
        toFromOptions = flightManager.getAirports().toArray(new String[0]);
        cBoxToFlightFind = new JComboBox<>(toFromOptions);
        JPanel fFBoxPanel = new JPanel();
        JLabel fFLabel = new JLabel("To:");

        fFBoxPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        fFBoxPanel.setPreferredSize(new Dimension(800, 30));
        cBoxToFlightFind.setPreferredSize(new Dimension(750, 25));
        fFBoxPanel.add(fFLabel);
        fFBoxPanel.add(cBoxToFlightFind);

        return fFBoxPanel;
    }

    /**
     * Method to create the day combo box in the flight finder panel
     * @return fFBoxPanel the panel housing cBoxDayFlightFind
     */
    private JPanel createDayFFComboBox() {
        cBoxDayFlightFind = new JComboBox<>(FlightsTab.days);
        JPanel fFBoxPanel = new JPanel();
        JLabel fFLabel = new JLabel("Day:");

        fFBoxPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        fFBoxPanel.setPreferredSize(new Dimension(800, 30));
        cBoxDayFlightFind.setPreferredSize(new Dimension(750, 25));
        fFBoxPanel.add(fFLabel);
        fFBoxPanel.add(cBoxDayFlightFind);

        return fFBoxPanel;
    }

    /**
     * panel used to format comboBox
     *
     * @return fFPanel containg comboBoxes
     */
    private JPanel fFInfoList() {
        JPanel fFPanel = new JPanel(new GridBagLayout());

        fFPanel.add(createFromFFComboBox(), createCon(0));
        fFPanel.add(createToFFComboBox(), createCon(1));
        fFPanel.add(createDayFFComboBox(), createCon(2));
        fFPanel.add(fFButton(), createCon(3));

        return fFPanel;
    }

    /**
     * Panel containing button
     *
     * @return buttonPanel containing button
     */
    private JPanel fFButton() {
        JPanel buttonPanel = new JPanel();
        btnFlightFind = new JButton("Find Flights");

        btnFlightFind.addActionListener(e -> {
            String from = Arrays.toString(new String[]{(String) cBoxFromFlightFind.getSelectedItem()});
            from = from.substring(1, 4);
            String to = Arrays.toString(new String[]{(String) cBoxToFlightFind.getSelectedItem()});
            to = to.substring(1, 4);
            String day = Arrays.toString(new String[]{(String) cBoxDayFlightFind.getSelectedItem()});
            day = day.substring(1, day.length() - 1);
            flightsModel.removeAllElements();
            for (Flight f : flightManager.findFlights(from, to, day)) {
                flightsModel.addElement(f);
            }
        });
        btnFlightFind.setPreferredSize(new Dimension(790, 25));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setPreferredSize(new Dimension(890, 30));
        buttonPanel.add(btnFlightFind);

        return buttonPanel;
    }

    /**
     * method to create constraints
     *
     * @param y gridy position
     * @return constraint the GridBagConstraint
     */
    private GridBagConstraints createCon(int y) {
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.gridx = 0;
        constraint.gridy = y;

        return constraint;
    }
}
