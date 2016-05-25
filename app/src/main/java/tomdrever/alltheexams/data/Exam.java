package tomdrever.alltheexams.data;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Minutes;
import org.joda.time.Period;
import org.joda.time.Weeks;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Exam {
    // Name of the exam, e.g "French"
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    protected String name;

    // Exam details, e.g. "Unit 2"
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    protected String details;

    // Exam datetime fields
    public Boolean getIsExamDone() {
        int daysLeft = Days.daysBetween(DateTime.now().toLocalDate(), dateTime.toLocalDate()).getDays();

        if (daysLeft < 0) {
            return true; // Finished
        }
        else if (daysLeft == 0){
            int minutesLeft = Minutes.minutesBetween(DateTime.now().toLocalTime(), dateTime.toLocalTime()).getMinutes();

            // Finished earlier today
            return minutesLeft <= 0;
        }
        else {
            return false;
        }
    }
    public String getDaysLeftString() {
        int daysLeft = Days.daysBetween(DateTime.now().toLocalDate(), dateTime.toLocalDate()).getDays();
        // Less than one day away - already done
        if (daysLeft < 0) {
            return "Finished exam";
        }
        // Either finished earlier today, or to do later
        else if (daysLeft == 0) {
            if (Minutes.minutesBetween(DateTime.now().toLocalTime(), dateTime.toLocalTime()).getMinutes() >= 0) {
                return "Later today";
            }
            else {
                return "Finished exam";
            }
        }
        // One day left
        else if (daysLeft == 1) {
            return "Tomorrow";
        }
        // More than one day left
        else {
            // More than a week left
            if (daysLeft >= 7) {
                int weeksLeft = Weeks.weeksBetween(DateTime.now().toLocalDate(), dateTime.toLocalDate()).getWeeks();
                if (weeksLeft == 1){
                    return Integer.toString(weeksLeft) + " week until exam";
                }
                else {
                    return Integer.toString(weeksLeft) + " weeks until exam";
                }

            }
            // Less than a week
            else {
                return Integer.toString(Days.daysBetween(DateTime.now().toLocalDate(), dateTime.toLocalDate()).getDays()) + " days until exam";
            }
        }
    }
    public DateTime getDateTime() {

        return dateTime;
    }
    public String getDateString() {
        // Should follow e.g. Tuesday 17 May
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("EEEE dd MMMM");
        return dateFormat.print(dateTime);
    }
    public String getTimeString() {
        // Should follow e.g. 09:30PM
        DateTimeFormatter timeFormat = DateTimeFormat.forPattern("KK:mmaa");
        return timeFormat.print(dateTime);
    }
    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
    protected DateTime dateTime;

    // Duration of the exam
    public Period getDuration() {
        return duration;
    }
    public String getDurationString() {
        return PeriodFormat.getDefault().print(duration.normalizedStandard());
    }
    public void setDuration(Period duration) {
        this.duration = duration;
    }
    protected Period duration;

    // Plain-old-java-class used to store details of an exam in JSON easier (as
    // joda-time objects have a lot of unnecessary stuff)
    public static class JSONExam {
        private int year;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonthOfYear() {
            return monthOfYear;
        }

        public void setMonthOfYear(int monthOfYear) {
            this.monthOfYear = monthOfYear;
        }

        public int getDayOfMonth() {
            return dayOfMonth;
        }

        public void setDayOfMonth(int dayOfMonth) {
            this.dayOfMonth = dayOfMonth;
        }

        public int getHourOfDay() {
            return hourOfDay;
        }

        public void setHourOfDay(int hourOfDay) {
            this.hourOfDay = hourOfDay;
        }

        public int getMinuteOfHour() {
            return minuteOfHour;
        }

        public void setMinuteOfHour(int minuteOfHour) {
            this.minuteOfHour = minuteOfHour;
        }

        public int getDurationInMinutes() {
            return durationInMinutes;
        }

        public void setDurationInMinutes(int durationInMinutes) {
            this.durationInMinutes = durationInMinutes;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        private int monthOfYear;
        private int dayOfMonth;
        private int hourOfDay;
        private int minuteOfHour;
        private int durationInMinutes;
        private String name;
        private String details;
    }

    // Deserialize exam/s from JSON code
    public static Exam getExamFromJson(@NonNull String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JSONExam jsonExam = mapper.readValue(json, JSONExam.class);

        return fromJsonExam(jsonExam);
    }

    public static ArrayList<Exam> getExamsFromJson(@NonNull String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JSONExam[] jsonExams = mapper.readValue(json, JSONExam[].class);

        ArrayList<Exam> exams = new ArrayList<>();
        for (JSONExam jsonExam:jsonExams) {
            exams.add(fromJsonExam(jsonExam));
        }

        return exams;
    }

    // Serialize exams/s into JSON code
    public static String getJsonFromExam(@NonNull Exam exam) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JSONExam jsonExam = Exam.toJsonExam(exam);

        return mapper.writeValueAsString(jsonExam);
    }

    public static String getJsonFromExams(@NonNull List<Exam> exams) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<JSONExam> jsonExams = new ArrayList<>();

        for (Exam exam:exams) {
            jsonExams.add(toJsonExam(exam));
        }

        return mapper.writeValueAsString(jsonExams);
    }

    // Convert exams to and from jsonexams for storage in JSON
    private static JSONExam toJsonExam(Exam exam) {
        JSONExam jsonExam = new JSONExam();
        jsonExam.year = exam.dateTime.getYear();
        jsonExam.monthOfYear = exam.dateTime.getMonthOfYear();
        jsonExam.dayOfMonth = exam.dateTime.getDayOfMonth();
        jsonExam.hourOfDay = exam.dateTime.getHourOfDay();
        jsonExam.minuteOfHour = exam.dateTime.getMinuteOfHour();
        jsonExam.durationInMinutes = exam.duration.getMinutes();
        jsonExam.name = exam.name;
        jsonExam.details = exam.details;

        return jsonExam;
    }

    private static Exam fromJsonExam(JSONExam jsonExam) {
        Exam exam = new Exam();
        exam.name = jsonExam.name;
        exam.details = jsonExam.details;
        exam.duration = new Period().withMinutes(jsonExam.durationInMinutes);
        exam.dateTime = new DateTime(jsonExam.year, jsonExam.monthOfYear, jsonExam.dayOfMonth, jsonExam.hourOfDay, jsonExam.minuteOfHour);
        return exam;
    }
}
