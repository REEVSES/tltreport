package com.allinpay.xmltrans.demo;

import java.io.InputStream;

public class Test {

	
	public static void main(String[] args) throws Exception {/*
		String tpl="201606";
		int month1=Integer.parseInt(tpl.substring(4, 6));
		String year=tpl.substring(0, 4);
		String	date=tpl.substring(0, 6);
			int month=Integer.parseInt(tpl.substring(4, 6));
			int monthlast=12-month;
			String datelast = null;
			int yearlast=0;
			if(monthlast>0){
			 yearlast=Integer.parseInt(year)-1;
				if(month>=10){
					 datelast=String.valueOf(yearlast)+month;
				}
				else{
					 datelast=String.valueOf(yearlast)+"0"+month;

				}
				
			}
		System.out.println(date+datelast);
	*/
		 String result = "11709270817091800000000000000701085";
			InputStream in = null;  
		/*Process pro = Runtime.getRuntime().exec("grep '测试案例流程商户号&200604000001247' /app/appserver/tomcat.aipg.std/logs/aipg.log");  
        pro.waitFor();  
        in = pro.getInputStream();  
        BufferedReader read = new BufferedReader(new InputStreamReader(in));  
        result = read.readLine();  */
        System.out.println("INFO:"+result.length());  	
	
	}
	
	
	
}
