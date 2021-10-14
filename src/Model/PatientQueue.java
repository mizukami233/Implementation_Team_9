package Model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Date;

public class PatientQueue {
    private Queue<Appointment> queue = new LinkedList<Appointment>();

    public PatientQueue(ArrayList<Appointment> appointments, Date timeNow) {
        // Select the appointments with check in time
        appointments.removeIf(a -> a.getCheckInTime() == null);
        appointments.removeIf(a -> a.getCheckInTime().getTime() > timeNow.getTime());

        // Sort the appointments according to the time
        appointments.sort((t2, t1) -> t2.getCheckInTime().compareTo(t1.getCheckInTime()));

        // Convert the ArrayList to LinkedList, and then to queue
        this.queue = new LinkedList<>(appointments);
    }

    public void enQueue(Appointment appointment) {
        queue.add(appointment);
    }

    public void deQueue() {
        queue.poll();
    }

    public void deQueue(Appointment appointment) {
        queue.remove(appointment);
    }

    public void updateSeq(Date timeNow) {
        while ((int) ((timeNow.getTime() - queue.peek().getCheckInTime().getTime()) / (1000 * 60)) > 45) {
           deQueue();
        }
    }

    public void printList() {
        for (Appointment appointment : queue) {
            System.out.println(appointment.getCheckInTime());
        }
    }

    public int countFront(int appid) {
        int i = 0;
        for (Appointment appointment : queue) {
            if (appointment.getAppId() == appid) {
                break;
            }
            i ++;
        }
        return i;
    }
}
