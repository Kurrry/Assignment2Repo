package sait.frms.gui;

import java.awt.*;

import javax.swing.*;

import sait.frms.manager.FlightManager;
import sait.frms.manager.ReservationManager;
import sait.frms.problemdomain.Reservation;

/**
 * Holds the components for the reservations tab.
 */
public class ReservationsTab extends TabBase {
    /**
     * Instance of reservation manager.
     */
    private ReservationManager reservationManager;
    private FlightManager flightManager;

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
    private JComboBox<String> statusComboBox;
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
    public ReservationsTab(ReservationManager reservationManager, FlightManager flightManager) {
        this.reservationManager = reservationManager;
        this.flightManager = flightManager;
        panel.setLayout(new BorderLayout());

        panel.add(createNorthPanel(), BorderLayout.NORTH);
        panel.add(createCenterPanel(), BorderLayout.CENTER);
        panel.add(createEastPanel(), BorderLayout.EAST);
        panel.add(createSouthPanel(), BorderLayout.SOUTH);
    }


    /**
     * Creates the north (title) panel used in the ReservationsTab.
     *
     * @return JPanel northPanel.
     */
    private JPanel createNorthPanel() {
        JPanel panel = new JPanel();

        panel.add(titleGenerator("Reservations", 45));

        return panel;
    }

    /**
     * Creates the center (List) panel used in ReservationsTab.
     *
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

        reservationsList.addListSelectionListener(e -> {
            Reservation tempReserve = reservationsList.getSelectedValue();
            if (tempReserve != null) {
                codeTextBox.setText(tempReserve.getReservationCode());
                flightTextBox.setText(tempReserve.getFlightCode());
                airlineTextBox.setText(tempReserve.getAirline());
                costTextBox.setText(String.valueOf(tempReserve.getCost()));
                nameTextBox.setText(tempReserve.getName());
                citizTextBox.setText(tempReserve.getCitizenship());
                String tempText = "Inactive";
                if (tempReserve.isActive()) {
                    tempText = "Active";
                }
                statusComboBox.setSelectedItem(tempText);
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Creates the east (Reservation) panel used in ReservationsTab.
     *
     * @return JPanel eastPanel
     */
    private JPanel createEastPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        codeTextBox = createReservationTF(14);
        codeTextBox.setEditable(false);

        flightTextBox = createReservationTF(14);
        flightTextBox.setEditable(false);

        airlineTextBox = createReservationTF(14);
        airlineTextBox.setEditable(false);

        costTextBox = createReservationTF(14);
        costTextBox.setEditable(false);

        nameTextBox = createReservationTF(14);

        citizTextBox = createReservationTF(14);

        String[] options = {"Active", "Inactive"};
        statusComboBox = new JComboBox<>(options);
        statusComboBox.setBackground(Color.WHITE);
        statusComboBox.setFont(new Font("Times New Roman", Font.BOLD, 15));

        panel.add(updateButton(), BorderLayout.SOUTH);
        panel.add(createResGridBox(), BorderLayout.CENTER);
        panel.add(titleGenerator("Reservations", 30), BorderLayout.NORTH);
        return panel;
    }

    /**
     * Creates the south (Search) panel used in ReservationsTab.
     *
     * @return JPanel southPanel
     */
    private JPanel createSouthPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Search", SwingConstants.CENTER);
        label.setFont(new Font("Times New Roman", Font.PLAIN, 30));

        searchCodeTF = createReservationTF(60);

        searchAirlineTF = createReservationTF(60);

        searchNameTF = createReservationTF(60);

        panel.add(searchButton(), BorderLayout.SOUTH);
        panel.add(createSearchGridBag(), BorderLayout.CENTER);
        panel.add(titleGenerator("Search", 30), BorderLayout.NORTH);
        return panel;
    }

    /**
     * Creates and returns panel used in eastPanel, bPanel. Creates button used to update a reservation.
     *
     * @return JPanel updateButton
     */
    private JPanel updateButton() {
        JPanel panel = new JPanel();

        updateButton = new JButton("Update");
        updateButton.setPreferredSize(new Dimension(225, 25));
        updateButton.addActionListener(e -> {
            boolean status = statusComboBox.getSelectedItem() != "Inactive";
            reservationManager.updateReservation(flightManager.findFlightByCode(flightTextBox.getText()),
                    codeTextBox.getText(), nameTextBox.getText(), citizTextBox.getText(), status);
            JFrame frame = new JFrame();
            frame.setSize(new Dimension(200, 200));
            JOptionPane.showMessageDialog(frame, "Reservation updated for " + codeTextBox.getText());
        });
        panel.add(updateButton);
        return panel;
    }

    /**
     * Creates and returns panel used in southPanel, sPanel. Creates button used to search for a reservation.
     *
     * @return JPanel searchButton
     */
    private JPanel searchButton() {
        JPanel panel = new JPanel();

        searchButton = new JButton("Find Reservations");
        searchButton.setPreferredSize(new Dimension(890, 30));
        searchButton.addActionListener(e -> {
            reservationsModel.removeAllElements();
            for (Reservation r : reservationManager.findReservations(searchCodeTF.getText(), searchAirlineTF.getText(),
                    searchNameTF.getText())) {
                reservationsModel.addElement(r);
            }
        });
        panel.add(searchButton);

        return panel;
    }

    /**
     * Generates and returns a formatted textfield used in the eastPanel and southPanel, contentPanel
     *
     * @param width The width of the textfield in columns
     * @return JTextField textfield
     */
    private JTextField createReservationTF(int width) {
        JTextField textField = new JTextField(width);
        textField.setBackground(Color.WHITE);
        textField.setFont(new Font("Times New Roman", Font.BOLD, 15));

        return textField;
    }

    /**
     * Generates and returns a formatted label used to hold the names of the textfield/combobox
     *
     * @param text Label text
     * @return JLabel Formatted label
     */
    private JLabel labelGenerator(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Times New Roman", Font.BOLD, 15));

        return label;
    }

    /**
     * Generates and returns a formatted label used to hold the names of the textfield/combobox
     *
     * @param text Label text
     * @param size Label text font size
     * @return JLabel Formatted label
     */
    private JLabel titleGenerator(String text, int size) {
        JLabel title = new JLabel(text, SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.PLAIN, size));

        return title;
    }

    /**
     * Creates and returns panel used in eastPanel, contentPanel. Creates grid with labels on left and textfields/combobox on the right.
     * <p>
     * General row construction:
     * <p>
     * -Label
     * JLabel Label = labelGenerator("Code:"); 			// Sends string to be generated and formatted in a label
     * GridBagConstraints LabelCon= createLabelCon(0); 	// Generates the label constraints, int sent is the row number
     * panel.add(Label, LabelCon); 						// Adds label with constraints to panel
     * <p>
     * -Panel
     * GridBagConstraints TFCon = createFieldCon(0); 	// Generates the field constraints, int sent is the row number
     * panel.add(TextBox, TFCon);						// Adds textfield/combobox with constraints to panel
     *
     * @return JPanel reservationContentPanel
     */
    private JPanel createResGridBox() {
        JPanel panel = new JPanel(new GridBagLayout());

        panel.add(labelGenerator("Code:"), createLabelCon(0));
        panel.add(codeTextBox, createFieldCon(0));
        panel.add(labelGenerator("Flight:"), createLabelCon(1));
        panel.add(flightTextBox, createFieldCon(1));
        panel.add(labelGenerator("Airline:"), createLabelCon(2));
        panel.add(airlineTextBox, createFieldCon(2));
        panel.add(labelGenerator("Cost:"), createLabelCon(3));
        panel.add(costTextBox, createFieldCon(3));
        panel.add(labelGenerator("Name:"), createLabelCon(4));
        panel.add(nameTextBox, createFieldCon(4));
        panel.add(labelGenerator("Citizenship:"), createLabelCon(5));
        panel.add(citizTextBox, createFieldCon(5));
        panel.add(labelGenerator("Status:"), createLabelCon(6));

        GridBagConstraints statusTFCon = createFieldCon(6);
        statusTFCon.fill = GridBagConstraints.HORIZONTAL;
        panel.add(statusComboBox, statusTFCon);

        return panel;
    }

    /**
     * Creates and returns panel used in southPanel, contentPanel. Creates grid with labels on left and textfields on the right.
     * <p>
     * General row construction:
     * <p>
     * -Label
     * JLabel Label = labelGenerator("Code:"); 			// Sends string to be generated and formatted in a label
     * GridBagConstraints LabelCon= createLabelCon(0); 	// Generates the label constraints, int sent is the row number
     * panel.add(Label, LabelCon); 						// Adds label with constraints to panel
     * <p>
     * -Panel
     * GridBagConstraints TFCon = createFieldCon(0); 	// Generates the field constraints, int sent is the row number
     * panel.add(TextBox, TFCon);						// Adds textfield with constraints to panel
     *
     * @return JPanel searchContentPanel
     */
    private JPanel createSearchGridBag() {
        JPanel panel = new JPanel(new GridBagLayout());

        panel.add(labelGenerator("Code:"), createLabelCon(0));
        panel.add(searchCodeTF, createFieldCon(0));
        panel.add(labelGenerator("Airline:"), createLabelCon(1));
        panel.add(searchAirlineTF, createFieldCon(1));
        panel.add(labelGenerator("Name:"), createLabelCon(2));
        panel.add(searchNameTF, createFieldCon(2));

        return panel;
    }

    /**
     * Generates and returns the GridBagConstraints used for the labels in the southPanel & eastPanel, contentPanel
     * Takes in the desired row number of the label
     *
     * @param y Row number
     * @return GridBagConstraints constraints
     */
    private GridBagConstraints createLabelCon(int y) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.LINE_END;
        constraints.gridx = 0;
        constraints.gridy = y;
        constraints.insets = new Insets(5, 0, 5, 0);

        return constraints;
    }

    /**
     * Generates and returns the GridBagConstraints used for the textfields/combobox in the southPanel & eastPanel, contentPanel
     * Takes in the desired row number of the textfield/combobox
     *
     * @param y Row number
     * @return GridBagConstraints constraints
     */
    private GridBagConstraints createFieldCon(int y) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = y;
        constraints.insets = new Insets(5, 5, 5, 10);

        return constraints;
    }
}