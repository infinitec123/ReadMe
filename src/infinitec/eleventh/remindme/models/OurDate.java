
package infinitec.eleventh.remindme.models;

import android.annotation.SuppressLint;

/**
 * @author Sharath Pandeshwar
 */
public class OurDate {
    private int date;
    private int month;
    private int year;

    /**
     * @return the date
     */
    public int getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(int date) {
        this.date = date;
    }

    /**
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    public OurDate(int date, int month, int year) {
        this.date = date;
        this.month = month;
        this.year = year;
        if (this.year < 100) {
            this.year += 2000;
        }

        /*
         * If we confused DD/MM with MM/YY, a small attempt to correct them here
         */
        if (this.month > 12) {
            int temp = this.month;
            this.month = this.date;
            this.date = temp;
        }
    }

    @SuppressLint("DefaultLocale")
    public OurDate(String date, String month, String year) {
        this.date = Integer.parseInt(date);
        this.year = Integer.parseInt(year);
        if (this.year < 100) {
            this.year += 2000;
        }
        /*
         * If we confused DD/MM with MM/YY, a small attempt to correct them here
         */
        if (this.month > 12) {
            int temp = this.month;
            this.month = this.date;
            this.date = temp;
        }

        try {
            this.month = Integer.parseInt(month);
        } catch (NumberFormatException e) {
            if (month.toLowerCase().contains("jan")) {
                this.month = 1;
            }
            if (month.toLowerCase().contains("feb")) {
                this.month = 2;
            }
            if (month.toLowerCase().contains("mar")) {
                this.month = 3;
            }
            if (month.toLowerCase().contains("apr")) {
                this.month = 4;
            }
            if (month.toLowerCase().contains("may")) {
                this.month = 5;
            }
            if (month.toLowerCase().contains("jun")) {
                this.month = 6;
            }
            if (month.toLowerCase().contains("jul")) {
                this.month = 7;
            }
            if (month.toLowerCase().contains("aug")) {
                this.month = 8;
            }
            if (month.toLowerCase().contains("sep")) {
                this.month = 9;
            }
            if (month.toLowerCase().contains("oct")) {
                this.month = 10;
            }
            if (month.toLowerCase().contains("nov")) {
                this.month = 11;
            }
            if (month.toLowerCase().contains("dec")) {
                this.month = 12;
            }
        }
    }

    public String toString() {
        return this.date + "/" + this.month + "/" + this.year;
    }

    /**
     * @param D OurDate object
     * @return 1 if this OurDate object is > than D -1 if this OurDate object is
     *         < than D 0 otherwise
     */
    public int compareTo(OurDate D) {
        if (this.year != D.year) {
            return this.year - D.year;
        } else {
            if (this.month != D.month) {
                return this.month - D.month;
            } else {
                return this.date - D.date;
            }
        }
    }

}
