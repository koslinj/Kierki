package koslin.jan.projekt;

import java.io.Serializable;

public class Visit implements Serializable {
//    private String surname;
//    private int hour;
//    private DayOfTheWeek day;
//    private boolean cancel;
//    private boolean quit;
//
//    public Visit(String surname, int hour, DayOfTheWeek day, boolean cancel, boolean quit) {
//        this.surname = surname;
//        this.hour = hour;
//        this.day = day;
//        this.cancel = cancel;
//        this.quit = quit;
//    }
//
//    public Visit(boolean quit) {
//        this.quit = quit;
//    }
//
//    public int mapToListNumber() {
//        int dayValue = day.ordinal();
//        int row = hour - HairSalon.MIN_HOUR;
//        return HairSalon.NUMBER_OF_WORKING_DAYS * row + dayValue;
//    }
//
//    public String getSurname() {
//        return surname;
//    }
//
//    public boolean isCancel() {
//        return cancel;
//    }
//
//    public int getHour() {
//        return hour;
//    }
//
//    public DayOfTheWeek getDay() {
//        return day;
//    }
//
//    public boolean isQuit() {
//        return quit;
//    }
//
//    @Override
//    public String toString() {
//        return surname + " " + day + " " + hour + ":00";
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null || getClass() != obj.getClass()) {
//            return false;
//        }
//
//        Visit otherVisit = (Visit) obj;
//        return surname.equals(otherVisit.surname) &&
//                hour == otherVisit.hour &&
//                day == otherVisit.day;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = surname.hashCode();
//        result = 31 * result + hour;
//        result = 31 * result + day.hashCode();
//        return result;
//    }
}
