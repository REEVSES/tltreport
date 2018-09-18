package com.aipg.ledger;

import java.util.ArrayList;
import java.util.List;

public class Ledgers {
	private List list;
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
	public void addTrx(Object o)
	{
		if(o==null) return ;
		if(list==null) list=new ArrayList();
		list.add(o);
	}
}
