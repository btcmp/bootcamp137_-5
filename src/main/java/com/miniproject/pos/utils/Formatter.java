package com.miniproject.pos.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;


public class Formatter {

	public static String date(Date date, String format) {
		SimpleDateFormat sm = new SimpleDateFormat(format);
	    String strDate ="";
		if(date != null) {
			strDate = sm.format(date);
	    }
		return strDate;
	}
	
	public static String currency(double value) {
		DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("Rp");
        dfs.setMonetaryDecimalSeparator('.');
        dfs.setGroupingSeparator(',');
        df.setDecimalSeparatorAlwaysShown(false);
        df.setMaximumFractionDigits(0);
        df.setDecimalFormatSymbols(dfs);
        return df.format(value);
	}
}
