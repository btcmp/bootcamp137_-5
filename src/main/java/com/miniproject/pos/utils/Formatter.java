package com.miniproject.pos.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		NumberFormat nf = DecimalFormat.getInstance();
		String uangS = nf.format(value);
        return "Rp "+uangS;
	}
}
