public class Slot {
    private int id;
    private String slotNumber;
    private String status;
    private String vehicleNo;
    private String bookedAt; // display as String in table

    public Slot(int id, String slotNumber, String status, String vehicleNo, String bookedAt) {
        this.id = id;
        this.slotNumber = slotNumber;
        this.status = status;
        this.vehicleNo = vehicleNo;
        this.bookedAt = bookedAt;
    }

    public int getId() {
        return id;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public String getBookedAt() {
        return bookedAt;
    }
}
