package zpi.flattery.webscrapper.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OlxUtil {

    private static final Logger LOGGER = Logger.getLogger(OlxUtil.class.getName());

    public static LocalDateTime getDateFromString(String dateString) {
        LocalDate currentDate = LocalDate.now();

        if (dateString.startsWith("dzisiaj") || dateString.startsWith("wczoraj")) {
            if (dateString.startsWith("wczoraj")) {
                currentDate = currentDate.minusDays(1);
            }
            dateString = dateString.substring(7, dateString.length());
            dateString = dateString.replaceAll("\\s+", "");
            String[] minAndHour = dateString.split(":");

            LocalTime offerTime = LocalTime.of(Integer.parseInt(minAndHour[0]), Integer.parseInt(minAndHour[1]));

            return LocalDateTime.of(currentDate, offerTime);
        } else {
            String[] dayAndMonth = dateString.split("\\s");
            LocalDate dateWithoutYearCheck = LocalDate.of(currentDate.getYear(), getIntFromMonthName(dayAndMonth[1]), Integer.parseInt(dayAndMonth[0]));
            if (dateWithoutYearCheck.isAfter(currentDate)) {
                return LocalDateTime.of(dateWithoutYearCheck.minusYears(1), LocalTime.of(12, 0));
            } else {
                return LocalDateTime.of(dateWithoutYearCheck, LocalTime.of(12, 0));
            }
        }
    }

    private static int getIntFromMonthName(String monthName) {
        int month = 0;
        switch (monthName) {
            case "sty":
                month = 1;
                break;
            case "lut":
                month = 2;
                break;
            case "mar":
                month = 3;
                break;
            case "kwi":
                month = 4;
                break;
            case "maj":
                month = 5;
                break;
            case "cze":
                month = 6;
                break;
            case "lip":
                month = 7;
                break;
            case "sie":
                month = 8;
                break;
            case "wrz":
                month = 9;
                break;
            case "paź":
                month = 10;
                break;
            case "lis":
                month = 11;
                break;
            case "gru":
                month = 12;
                break;
            default:
                LOGGER.log(Level.FINE, "Error: Couldn't parse month name to integer.");
                break;
        }
        return month;
    }
}