package org.shield.compiler.util;

import java.util.Vector;
import java.io.File;

public class IndentationManager {
	
	private Vector<String> nbuf;
	private Vector<Boolean> dbuf;
	private Vector<String> tbuf;
	
	private int maxlen;
	
	public IndentationManager() {
		nbuf = new Vector<String>(1,1);
		dbuf = new Vector<Boolean>(1,1);
		tbuf = new Vector<String>(1,1);
		maxlen = 0;
	}
	
	public void add(File st) {
		if(st.getName().length() > maxlen) maxlen = st.getName().length()+1;
		nbuf.addElement(st.getName());
		dbuf.addElement((st.isDirectory())?true:false);
		tbuf.addElement((st.isDirectory())?"":GlobalUtil.getCache().getExtDes(ShieldSwitch.getExtension(st.getName())));
	}
	
	public String[] getIndentedStrings() {
		String[] nst = new String[nbuf.size()];
		for(int i = 0; i < nst.length; i++)
			nst[i]=nbuf.elementAt(i);
		
		boolean[] bst = new boolean[dbuf.size()];
		for(int i = 0; i < bst.length; i++)
			bst[i]=dbuf.elementAt(i);
		
		String[] tst = new String[tbuf.size()];
		for(int i = 0; i < tst.length; i++)
			tst[i]=tbuf.elementAt(i);
		
		String[] iss = new String[nbuf.size()];
		for(int i = 0; i < nst.length; i++) 
			iss[i]=nst[i]+spaces(nst[i].length(), maxlen)+((bst[i])?" <DIR> ":"       ")+tst[i];
		return iss;
	}
	
	private String spaces(int len, int mlen) {
		int l = mlen - len;
		String st = " ";
		for(int i = 1; i <= l; i++)
			st+=" ";
		return st;
	}
	
}