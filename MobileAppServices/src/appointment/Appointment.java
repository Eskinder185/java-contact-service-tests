package appointment;

import java.util.Date;

public class Appointment {
	private final String appointmentId;
    private Date appointmentDate;
    private String description;

    public Appointment(String appointmentId, Date appointmentDate, String description) {
        if(appointmentId == null || appointmentId.length() > 10) throw new IllegalArgumentException("Invalid Appointment ID");
        if(appointmentDate == null || appointmentDate.before(new Date())) throw new IllegalArgumentException("Invalid Date");
        if(description == null || description.length() > 50) throw new IllegalArgumentException("Invalid Description");

        this.appointmentId = appointmentId;
        this.appointmentDate = appointmentDate;
        this.description = description;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        if(appointmentDate != null && !appointmentDate.before(new Date())) {
            this.appointmentDate = appointmentDate;
        } else {
            throw new IllegalArgumentException("Invalid Date");
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description != null && description.length() <= 50) {
            this.description = description;
        } else {
            throw new IllegalArgumentException("Invalid Description");
        }
    }
}

