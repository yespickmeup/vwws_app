package com.vwwsapp.helpers;

public class ReceiptIncrementor {
    public static String increment(final String receipt) {
        System.out.println(receipt);
        int f9 = 0;
        int s_at = 0;
        final char[] chs = receipt.toCharArray();
        for (int i = 0; i < chs.length; ++i) {
            if (Character.isDigit(chs[i])) {
                ++f9;
            }
            else {
                f9 = 0;
                s_at = i + 1;
            }
        }
        final String prepend = receipt.substring(0, s_at);
        final StringBuilder sb9 = new StringBuilder();
        for (int j = 0; j < f9; ++j) {
            sb9.append("9");
        }
        final String sb9s = sb9.toString();
        final long limit = Long.parseLong(sb9s.isEmpty() ? "0" : sb9s);
//        Lg.$.log(Level.SEVERE, "limit{0}", limit);
        String slast_no = receipt.substring(s_at, receipt.length());
        long last_no = Long.parseLong(slast_no.isEmpty() ? "0" : slast_no);
        if (last_no < limit) {
            ++last_no;
        }
        else {
            ++last_no;
        }
        slast_no = last_no + "";
        final int z0 = f9 - slast_no.length();
        final StringBuilder sb_app = new StringBuilder();
        sb_app.append(prepend);
        for (int k = 0; k < z0; ++k) {
            sb_app.append(0);
        }
        sb_app.append(slast_no);
        return sb_app.toString();
    }

    public static void main(final String[] args) {
        increment("");
    }
}
