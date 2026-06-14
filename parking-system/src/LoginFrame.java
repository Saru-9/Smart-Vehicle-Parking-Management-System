import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class LoginFrame extends JFrame {

    // Dark theme colors
    static final Color BG_DARK      = new Color(26, 26, 46);
    static final Color BG_CARD      = new Color(22, 33, 62);
    static final Color ACCENT_RED   = new Color(233, 69, 96);
    static final Color ACCENT_BLUE  = new Color(76, 201, 240);
    static final Color TEXT_PRIMARY = new Color(238, 238, 238);
    static final Color TEXT_MUTED   = new Color(136, 136, 136);
    static final Color BORDER_COLOR = new Color(15, 52, 96);
    static final Color INPUT_BG     = new Color(15, 33, 62);

    private JTextField userField;
    private JPasswordField passField;

    public LoginFrame() {
        setTitle("Smart Parking - Login");
        setSize(400, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 400, 480, 20, 20));

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG_DARK);
        root.setBorder(new LineBorder(BORDER_COLOR, 1));

        // --- Title bar ---
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(BG_CARD);
        titleBar.setPreferredSize(new Dimension(400, 44));
        titleBar.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_COLOR));

        JLabel appTitle = new JLabel("  \uD83C\uDD7F Smart Parking System");
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        appTitle.setForeground(TEXT_PRIMARY);
        titleBar.add(appTitle, BorderLayout.WEST);

        JButton closeBtn = new JButton("\u2715");
        closeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        closeBtn.setForeground(TEXT_MUTED);
        closeBtn.setBackground(BG_CARD);
        closeBtn.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
        closeBtn.setFocusPainted(false);
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> System.exit(0));
        closeBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { closeBtn.setForeground(ACCENT_RED); }
            public void mouseExited(MouseEvent e)  { closeBtn.setForeground(TEXT_MUTED); }
        });
        titleBar.add(closeBtn, BorderLayout.EAST);

        // Drag to move
        final Point[] dragPoint = {null};
        titleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) { dragPoint[0] = e.getPoint(); }
        });
        titleBar.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point loc = getLocation();
                setLocation(loc.x + e.getX() - dragPoint[0].x, loc.y + e.getY() - dragPoint[0].y);
            }
        });

        root.add(titleBar, BorderLayout.NORTH);

        // --- Center card ---
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(BG_DARK);
        center.setBorder(new EmptyBorder(40, 50, 40, 50));

        // Icon circle
        JLabel iconLabel = new JLabel("\uD83C\uDD7F", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ACCENT_RED);
                g2.fillOval(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        iconLabel.setPreferredSize(new Dimension(70, 70));
        iconLabel.setMaximumSize(new Dimension(70, 70));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel heading = new JLabel("Welcome Back");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(TEXT_PRIMARY);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Sign in to manage parking slots");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(TEXT_MUTED);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        center.add(iconLabel);
        center.add(Box.createVerticalStrut(16));
        center.add(heading);
        center.add(Box.createVerticalStrut(4));
        center.add(sub);
        center.add(Box.createVerticalStrut(30));

        // Username field
        center.add(makeLabel("Username"));
        center.add(Box.createVerticalStrut(6));
        userField = makeTextField();
        center.add(userField);
        center.add(Box.createVerticalStrut(16));

        // Password field
        center.add(makeLabel("Password"));
        center.add(Box.createVerticalStrut(6));
        passField = new JPasswordField();
        styleTextField(passField);
        center.add(passField);
        center.add(Box.createVerticalStrut(28));

        // Login button
        JButton loginBtn = new JButton("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed() ? ACCENT_RED.darker() : ACCENT_RED);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
        };
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setContentAreaFilled(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setFocusPainted(false);
        loginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginBtn.addActionListener(e -> doLogin());
        passField.addActionListener(e -> doLogin());

        center.add(loginBtn);

        JLabel hint = new JLabel("Default: admin / admin123");
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        hint.setForeground(TEXT_MUTED);
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(Box.createVerticalStrut(12));
        center.add(hint);

        root.add(center, BorderLayout.CENTER);
        setContentPane(root);
    }

    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(TEXT_MUTED);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JTextField makeTextField() {
        JTextField tf = new JTextField();
        styleTextField(tf);
        return tf;
    }

    private void styleTextField(JTextField tf) {
        tf.setBackground(INPUT_BG);
        tf.setForeground(TEXT_PRIMARY);
        tf.setCaretColor(ACCENT_BLUE);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        tf.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void doLogin() {
        String u = userField.getText();
        String p = new String(passField.getPassword());
        if ("admin".equals(u) && "admin123".equals(p)) {
            new MainFrame().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid username or password.",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}