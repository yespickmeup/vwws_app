package com.vwwsapp.helpers;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParsePosition;
import java.text.MessageFormat;
public class FitIn {
    public static double toDouble(final String value) {
        try {
            if (value == null || value.isEmpty()) {
                return 0.0;
            }
            if (value.charAt(0) != '-' && value.charAt(0) != '.' && !Character.isDigit(value.charAt(0))) {
                return 0.0;
            }
            final String pattern = "{0,number,#,###.##}";
            final MessageFormat mf = new MessageFormat(pattern);
            final String sv = value.trim();
            final Object[] os = mf.parse(sv.isEmpty() ? "0" : sv, new ParsePosition(0));
            return ((Number)os[0]).doubleValue();
        }
        catch (Exception e) {
            return 0.0;
        }
    }

    public static int toInt(final String value) {
        try {
            if (value == null || value.isEmpty()) {
                return 0;
            }
            if (value.charAt(0) != '-' && !Character.isDigit(value.charAt(0))) {
                return 0;
            }
            final String pattern = "{0,number,#,###.##}";
            final MessageFormat mf = new MessageFormat(pattern);
            final String sv = value.trim();
            final Object[] os = mf.parse(sv.isEmpty() ? "0" : sv, new ParsePosition(0));
            return ((Number)os[0]).intValue();
        }
        catch (Exception e) {
            return 0;
        }
    }

    public static long toLong(final String value) {
        try {
            if (value == null || value.isEmpty()) {
                return 0L;
            }
            if (value.charAt(0) != '-' && !Character.isDigit(value.charAt(0))) {
                return 0L;
            }
            final String pattern = "{0,number,#,###.##}";
            final MessageFormat mf = new MessageFormat(pattern);
            final String sv = value.trim();
            final Object[] os = mf.parse(sv.isEmpty() ? "0" : sv, new ParsePosition(0));
            return ((Number)os[0]).longValue();
        }
        catch (Exception e) {
            return 0L;
        }
    }

    public static String fmt_wc_0(final double value) {
        final String pattern = "{0,number,#,##0.00}";
        final Object[] args = { value };
        final String strFormat = MessageFormat.format(pattern, args);
        return strFormat;
    }

    public static String fmt_wc(final double value) {
        final String pattern = "{0,number,#,###.##}";
        final Object[] args = { value };
        final String strFormat = MessageFormat.format(pattern, args);
        return strFormat;
    }

    public static String fmt_woc_0(final double value) {
        final String pattern = "{0,number,0.00}";
        final Object[] args = { value };
        final String strFormat = MessageFormat.format(pattern, args);
        return strFormat;
    }

    public static String fmt_woc(final double value) {
        final String pattern = "{0,number,#.##}";
        final Object[] args = { value };
        final String strFormat = MessageFormat.format(pattern, args);
        return strFormat;
    }

    public static String fmt_wc_0(final String value) {
        final String pattern = "{0,number,#,##0.00}";
        final Object[] args = { toDouble(value) };
        final String strFormat = MessageFormat.format(pattern, args);
        return strFormat;
    }

    public static String fmt_wc(final String value) {
        final String pattern = "{0,number,#,###.##}";
        final Object[] args = { toDouble(value) };
        final String strFormat = MessageFormat.format(pattern, args);
        return strFormat;
    }

    public static String fmt_woc_0(final String value) {
        final String pattern = "{0,number,0.00}";
        final Object[] args = { toDouble(value) };
        final String strFormat = MessageFormat.format(pattern, args);
        return strFormat;
    }

    public static String fmt_woc(final String value) {
        final String pattern = "{0,number,#.##}";
        final Object[] args = { toDouble(value) };
        final String strFormat = MessageFormat.format(pattern, args);
        return strFormat;
    }

    public static String fmt_number(final Number num, final String format) {
        return MessageFormat.format("{0,number," + format + "}", num);
    }

    public static String fmt_date(final Date date, final String format) {
        return MessageFormat.format("{0,date," + format + "}", date);
    }

    public static Date to_date(final String date_str, final String format) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date_str.trim());
        }
        catch (Exception e) {
            return new Date();
        }
    }

    public static Date to_date_strict(final String date_str, final String format) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date_str.trim());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Number to_number(final String number_str, final String format) {
        final String pattern = "{0,number," + format + "}";
        final MessageFormat mf = new MessageFormat(pattern);
        final String sv = number_str.trim();
        final Object[] os = mf.parse(sv.isEmpty() ? "0" : sv, new ParsePosition(0));
        return (Number)os[0];
    }

    public static boolean toBoolean(final String boolean_str) {
        try {
            return Boolean.parseBoolean(boolean_str);
        }
        catch (Exception e) {
            return false;
        }
    }

    public static void main(final String[] args) {
        final String num = "-100";
        System.out.println(toDouble(num));
    }
}
