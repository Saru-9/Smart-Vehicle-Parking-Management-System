import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainFrame extends JFrame {

    // ── Colors (shared with LoginFrame) ──────────────────────────────────────
    static final Color BG_DARK      = new Color(26, 26, 46);
    static final Color BG_CARD      = new Color(22, 33, 62);
    static final Color BG_INPUT     = new Color(15, 33, 62);
    static final Color ACCENT_RED   = new Color(233, 69, 96);
    static final Color ACCENT_BLUE  = new Color(76, 201, 240);
    static final Color ACCENT_GREEN = new Color(74, 222, 128);
    static final Color ACCENT_AMBER = new Color(244, 162, 97);
    static final Color TEXT_PRIMARY = new Color(238, 238, 238);
    static final Color TEXT_MUTED   = new Color(136, 136, 136);
    static final Color BORDER_COLOR = new Color(15, 52, 96);
    static final Color ROW_HOVER    = new Color(26, 42, 74);

    // ── State ─────────────────────────────────────────────────────────────────
    private JTable table;
    private DefaultTableModel model;
    private SlotDAO slotDAO;

    // Stat labels
    private JLabel totalLabel, availLabel, bookedLabel;

    public MainFrame() {
        slotDAO = new SlotDAO();
        setTitle("Smart Parking - Dashboard");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);
        setLayout(new BorderLayout());

        add(buildTopBar(),    BorderLayout.NORTH);
        add(buildContent(),   BorderLayout.CENTER);

        loadSlots();
    }

    // ── Top Bar ───────────────────────────────────────────────────────────────
    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG_CARD);
        bar.setPreferredSize(new Dimension(0, 52));
        bar.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_COLOR));

        JLabel logo = new JLabel("  \uD83C\uDD7F  Smart Vehicle Parking  —  Dashboard");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logo.setForeground(TEXT_PRIMARY);
        bar.add(logo, BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        right.setOpaque(false);

        JLabel time = new JLabel();
        time.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        time.setForeground(TEXT_MUTED);
        right.add(time);

        Timer clock = new Timer(1000, e -> {
            time.setText(new java.text.SimpleDateFormat("dd MMM yyyy  HH:mm:ss")
                .format(new java.util.Date()));
        });
        clock.start();
        time.setText(new java.text.SimpleDateFormat("dd MMM yyyy  HH:mm:ss")
            .format(new java.util.Date()));

        JLabel admin = makeChip("Admin", ACCENT_BLUE);
        right.add(admin);

        JButton logoutBtn = makeIconButton("Logout", ACCENT_RED);
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        right.add(logoutBtn);

        bar.add(right, BorderLayout.EAST);
        return bar;
    }

    // ── Main content ──────────────────────────────────────────────────────────
    private JPanel buildContent() {
        JPanel content = new JPanel(new BorderLayout(0, 0));
        content.setBackground(BG_DARK);
        content.setBorder(new EmptyBorder(16, 16, 16, 16));

        content.add(buildStatsRow(), BorderLayout.NORTH);
        content.add(buildTableSection(), BorderLayout.CENTER);

        return content;
    }

    // ── Stats Row ─────────────────────────────────────────────────────────────
    private JPanel buildStatsRow() {
        JPanel row = new JPanel(new GridLayout(1, 3, 12, 0));
        row.setOpaque(false);
        row.setPreferredSize(new Dimension(0, 90));
        row.setBorder(new EmptyBorder(0, 0, 14, 0));

        totalLabel  = new JLabel("0");
        availLabel  = new JLabel("0");
        bookedLabel = new JLabel("0");

        row.add(buildStatCard("Total Slots",  totalLabel,  ACCENT_BLUE,  "\uD83D\uDDC4"));
        row.add(buildStatCard("Available",    availLabel,  ACCENT_GREEN, "\u2705"));
        row.add(buildStatCard("Occupied",     bookedLabel, ACCENT_RED,   "\uD83D\uDE97"));

        return row;
    }

    private JPanel buildStatCard(String label, JLabel numLabel, Color accent, String emoji) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 3, 0, 0, accent),
            new EmptyBorder(12, 16, 12, 16)
        ));

        JLabel emojiLabel = new JLabel(emoji);
        emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));

        numLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        numLabel.setForeground(TEXT_PRIMARY);

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(TEXT_MUTED);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(numLabel);
        textPanel.add(lbl);

        card.add(emojiLabel, BorderLayout.WEST);
        card.add(textPanel,  BorderLayout.CENTER);
        return card;
    }

    // ── Table Section ─────────────────────────────────────────────────────────
    private JPanel buildTableSection() {
        JPanel section = new JPanel(new BorderLayout(0, 10));
        section.setOpaque(false);

        section.add(buildToolbar(), BorderLayout.NORTH);
        section.add(buildTable(),   BorderLayout.CENTER);

        return section;
    }

    private JPanel buildToolbar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        bar.setOpaque(false);

        JButton addBtn     = makeActionButton("+ Add Slot",     BG_CARD,     ACCENT_BLUE);
        JButton bookBtn    = makeActionButton("\uD83D\uDD11 Book Slot",  new Color(20,83,45), ACCENT_GREEN);
        JButton releaseBtn = makeActionButton("\u21A9 Release",  new Color(67,20,7),  ACCENT_AMBER);
        JButton refreshBtn = makeActionButton("\u27F3 Refresh",  BG_CARD,     TEXT_MUTED);

        addBtn.addActionListener(e -> addSlot());
        bookBtn.addActionListener(e -> bookSelectedSlot());
        releaseBtn.addActionListener(e -> releaseSelectedSlot());
        refreshBtn.addActionListener(e -> loadSlots());

        bar.add(addBtn);
        bar.add(bookBtn);
        bar.add(releaseBtn);
        bar.add(refreshBtn);

        return bar;
    }

    private JScrollPane buildTable() {
        String[] cols = {"#", "Slot", "Status", "Vehicle No", "Booked At"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                c.setBackground(isRowSelected(row) ? BORDER_COLOR : (row % 2 == 0 ? BG_CARD : BG_DARK));
                return c;
            }
        };

        table.setBackground(BG_CARD);
        table.setForeground(TEXT_PRIMARY);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(38);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(BORDER_COLOR);
        table.setSelectionForeground(TEXT_PRIMARY);
        table.getTableHeader().setReorderingAllowed(false);

        // Header style
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(15, 52, 96));
        header.setForeground(TEXT_MUTED);
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setPreferredSize(new Dimension(0, 36));
        header.setBorder(BorderFactory.createEmptyBorder());

        // Column widths
        int[] widths = {40, 70, 110, 140, 160};
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        // Custom cell renderer for Status column
        table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean focus, int row, int col) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, val, sel, focus, row, col);
                String status = val != null ? val.toString() : "";
                if ("AVAILABLE".equalsIgnoreCase(status)) {
                    lbl.setText("  \u25CF  Available");
                    lbl.setForeground(ACCENT_GREEN);
                } else {
                    lbl.setText("  \u25CF  Booked");
                    lbl.setForeground(ACCENT_RED);
                }
                lbl.setBackground(sel ? BORDER_COLOR : (row % 2 == 0 ? BG_CARD : BG_DARK));
                lbl.setOpaque(true);
                return lbl;
            }
        });

        // Slot number renderer
        table.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean focus, int row, int col) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(t, val, sel, focus, row, col);
                lbl.setForeground(ACCENT_BLUE);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
                lbl.setBackground(sel ? BORDER_COLOR : (row % 2 == 0 ? BG_CARD : BG_DARK));
                lbl.setOpaque(true);
                return lbl;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(BG_CARD);
        scrollPane.getViewport().setBackground(BG_CARD);
        scrollPane.setBorder(new LineBorder(BORDER_COLOR, 1));
        return scrollPane;
    }

    // ── Data loading ──────────────────────────────────────────────────────────
    private void loadSlots() {
        model.setRowCount(0);
        List<Slot> slots = slotDAO.getAllSlots();
        int avail = 0, booked = 0;
        for (int i = 0; i < slots.size(); i++) {
            Slot s = slots.get(i);
            model.addRow(new Object[]{
                i + 1,
                s.getSlotNumber(),
                s.getStatus(),
                s.getVehicleNo() != null ? s.getVehicleNo() : "—",
                s.getBookedAt() != null && !s.getBookedAt().isEmpty() ? s.getBookedAt() : "—"
            });
            if ("AVAILABLE".equalsIgnoreCase(s.getStatus())) avail++;
            else booked++;
        }
        totalLabel.setText(String.valueOf(slots.size()));
        availLabel.setText(String.valueOf(avail));
        bookedLabel.setText(String.valueOf(booked));
    }

    // ── Actions ───────────────────────────────────────────────────────────────
    private void addSlot() {
        String slotNum = showStyledInput("Enter new slot number (e.g. S5):");
        if (slotNum == null || slotNum.trim().isEmpty()) return;
        if (slotDAO.addSlot(slotNum.trim())) {
            showSuccess("Slot '" + slotNum.trim() + "' added successfully!");
            loadSlots();
        } else {
            showError("Failed to add slot. It may already exist.");
        }
    }

    private void bookSelectedSlot() {
        int row = table.getSelectedRow();
        if (row == -1) { showError("Please select a slot from the table."); return; }
        int id = (int) model.getValueAt(row, 0);
        String slotNum  = (String) model.getValueAt(row, 1);
        String status   = (String) model.getValueAt(row, 2);

        // Get actual DB id from DAO
        List<Slot> all = slotDAO.getAllSlots();
        if (row >= all.size()) return;
        Slot selected = all.get(row);

        if (!"AVAILABLE".equalsIgnoreCase(status)) {
            showError("Slot " + slotNum + " is already BOOKED.");
            return;
        }
        String vehicleNo = showStyledInput("Enter vehicle number for Slot " + slotNum + ":");
        if (vehicleNo == null || vehicleNo.trim().isEmpty()) return;
        if (slotDAO.bookSlot(selected.getId(), vehicleNo.trim())) {
            showSuccess("Slot " + slotNum + " booked for " + vehicleNo.trim());
            loadSlots();
        } else {
            showError("Booking failed. Slot may have been taken.");
        }
    }

    private void releaseSelectedSlot() {
        int row = table.getSelectedRow();
        if (row == -1) { showError("Please select a slot from the table."); return; }
        String status  = (String) model.getValueAt(row, 2);
        String slotNum = (String) model.getValueAt(row, 1);

        List<Slot> all = slotDAO.getAllSlots();
        if (row >= all.size()) return;
        Slot selected = all.get(row);

        if (!"BOOKED".equalsIgnoreCase(status)) {
            showError("Slot " + slotNum + " is not currently booked.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
            "Release slot " + slotNum + "?", "Confirm Release", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        if (slotDAO.releaseSlot(selected.getId())) {
            showSuccess("Slot " + slotNum + " released successfully.");
            loadSlots();
        } else {
            showError("Release failed.");
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private String showStyledInput(String prompt) {
        JTextField tf = new JTextField(18);
        tf.setBackground(BG_INPUT);
        tf.setForeground(TEXT_PRIMARY);
        tf.setCaretColor(ACCENT_BLUE);
        tf.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            new EmptyBorder(6, 10, 6, 10)));
        JLabel lbl = new JLabel(prompt);
        lbl.setForeground(TEXT_MUTED);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(BG_CARD);
        panel.add(lbl, BorderLayout.NORTH);
        panel.add(tf,  BorderLayout.CENTER);
        int res = JOptionPane.showConfirmDialog(this, panel, "Input", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        return (res == JOptionPane.OK_OPTION) ? tf.getText() : null;
    }

    private void showSuccess(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private JButton makeActionButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(7, 14, 7, 14)));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton makeIconButton(String text, Color fg) {
        JButton btn = new JButton(text);
        btn.setBackground(BG_CARD);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(4, 10, 4, 10)));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JLabel makeChip(String text, Color color) {
        JLabel chip = new JLabel(text);
        chip.setBackground(BG_INPUT);
        chip.setForeground(color);
        chip.setFont(new Font("Segoe UI", Font.BOLD, 11));
        chip.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(color.darker(), 1, true),
            new EmptyBorder(3, 10, 3, 10)));
        chip.setOpaque(true);
        return chip;
    }
}
