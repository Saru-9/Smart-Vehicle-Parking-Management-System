import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private SlotDAO slotDAO;

    public MainFrame() {
        slotDAO = new SlotDAO();
        setTitle("Smart Vehicle Parking - Dashboard");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table
        model = new DefaultTableModel(new Object[]{"ID", "Slot No", "Status", "Vehicle No", "Booked At"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // all cells read-only
            }
        };
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        JButton addBtn = new JButton("Add Slot");
        JButton bookBtn = new JButton("Book Slot");
        JButton releaseBtn = new JButton("Release Slot");
        JButton refreshBtn = new JButton("Refresh");

        buttonPanel.add(addBtn);
        buttonPanel.add(bookBtn);
        buttonPanel.add(releaseBtn);
        buttonPanel.add(refreshBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load initial data
        loadSlots();

        // Button actions

        addBtn.addActionListener(e -> addSlot());
        bookBtn.addActionListener(e -> bookSelectedSlot());
        releaseBtn.addActionListener(e -> releaseSelectedSlot());
        refreshBtn.addActionListener(e -> loadSlots());
    }

    private void loadSlots() {
        model.setRowCount(0);
        List<Slot> slots = slotDAO.getAllSlots();
        for (Slot s : slots) {
            model.addRow(new Object[]{
                    s.getId(),
                    s.getSlotNumber(),
                    s.getStatus(),
                    s.getVehicleNo(),
                    s.getBookedAt()
            });
        }
    }

    private void addSlot() {
        String slotNum = JOptionPane.showInputDialog(this, "Enter new Slot Number (e.g. S4):");
        if (slotNum == null || slotNum.trim().isEmpty()) {
            return;
        }
        boolean ok = slotDAO.addSlot(slotNum.trim());
        if (ok) {
            JOptionPane.showMessageDialog(this, "Slot added successfully!");
            loadSlots();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add slot.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void bookSelectedSlot() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a slot from the table.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        String status = (String) model.getValueAt(row, 2);

        if (!"AVAILABLE".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this, "Slot is already BOOKED.");
            return;
        }

        String vehicleNo = JOptionPane.showInputDialog(this, "Enter Vehicle Number:");
        if (vehicleNo == null || vehicleNo.trim().isEmpty()) {
            return;
        }

        boolean ok = slotDAO.bookSlot(id, vehicleNo.trim());
        if (ok) {
            JOptionPane.showMessageDialog(this, "Slot booked successfully!");
            loadSlots();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to book slot.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void releaseSelectedSlot() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a slot from the table.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        String status = (String) model.getValueAt(row, 2);

        if (!"BOOKED".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this, "Slot is not booked.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Release this slot?", "Confirm", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        boolean ok = slotDAO.releaseSlot(id);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Slot released successfully!");
            loadSlots();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to release slot.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
