package general;

public class Interval {
	
	int startHour;
	int startMinute;
	int endHour;
	int endMinute;
	
	public Interval(String interval) {
		String[] time = interval.split("-");
		String[] start = time[0].split(":");
		String[] end = time[1].split(":");
		
		startHour = Integer.parseInt(start[0]);
		startMinute = Integer.parseInt(start[1]);
		endHour = Integer.parseInt(end[0]);
		endMinute = Integer.parseInt(end[1]);
	}

	public int getMinutes(){
		return (endHour-startHour)*60+(endMinute-startMinute);
	}
	
	public Double getHours(){
		return ((double) getMinutes())/60;
	}
	
	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public int getStartMinute() {
		return startMinute;
	}

	public void setStartMinute(int startMinute) {
		this.startMinute = startMinute;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	public int getEndMinute() {
		return endMinute;
	}

	public void setEndMinute(int endMinute) {
		this.endMinute = endMinute;
	}
}
