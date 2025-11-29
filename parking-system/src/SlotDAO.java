import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SlotDAO {

    public List<Slot> getAllSlots() {
        List<Slot> list = new ArrayList<>();

        String sql = "SELECT id, slot_number, status, vehicle_no, booked_at FROM parking_slots ORDER BY id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String slotNum = rs.getString("slot_number");
                String status = rs.getString("status");
                String vehicleNo = rs.getString("vehicle_no");
                Timestamp ts = rs.getTimestamp("booked_at");
                String bookedAt = (ts != null) ? ts.toString() : "";
                list.add(new Slot(id, slotNum, status, vehicleNo, bookedAt));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addSlot(String slotNumber) {
        String sql = "INSERT INTO parking_slots (slot_number, status) VALUES (?, 'AVAILABLE')";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, slotNumber);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean bookSlot(int id, String vehicleNo) {
        String sql = "UPDATE parking_slots SET status='BOOKED', vehicle_no=?, booked_at=NOW() " +
                     "WHERE id=? AND status='AVAILABLE'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, vehicleNo);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0; // will be 0 if already booked

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean releaseSlot(int id) {
        String sql = "UPDATE parking_slots SET status='AVAILABLE', vehicle_no=NULL, booked_at=NULL " +
                     "WHERE id=? AND status='BOOKED'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
