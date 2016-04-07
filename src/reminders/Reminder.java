package reminders;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Reminder implements Serializable
{
	private static final long serialVersionUID = -3945751016286710063L;
	
	public LocalDateTime dueDate;
	public boolean remind;
	public boolean reminderSent;
	
	public Reminder(LocalDateTime dueDate, Interval interval){
		this.dueDate = dueDate.minusMinutes(interval.getInterval());
		reminderSent = false;
	}
	
	public boolean remindTime() {
		LocalDateTime now = LocalDateTime.now();
		if (reminderSent == false) {
			if (dueDate.isBefore(now)) {
				reminderSent = true;
				return dueDate.isBefore(now);
			} 
		}
		return false;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public boolean isRemind() {
		return remind;
	}

	public void setRemind(boolean remind) {
		this.remind = remind;
	}

	public boolean isReminderSent() {
		return reminderSent;
	}

	public void setReminderSent(boolean reminderSent) {
		this.reminderSent = reminderSent;
	}
	
}
