package com.aipg.payreq;

import java.util.ArrayList;
import java.util.List;

public class Body {
	private Trans_Sum TRANS_SUM;
	
	private List TRANS_DETAILS = new ArrayList( );
	
	
	
	public Trans_Sum getTRANS_SUM() {
		return TRANS_SUM;
	}
	public void setTRANS_SUM(Trans_Sum trans_sum) {
		TRANS_SUM = trans_sum;
	}
	public List getDetails() {
		return TRANS_DETAILS;
	}
	public void setDetails(List TRANS_DETAILS) {
		this.TRANS_DETAILS = TRANS_DETAILS;
	}
	
	public void addDetail(Trans_Detail detail) {
		TRANS_DETAILS.add(detail);
	}
	
	
}
