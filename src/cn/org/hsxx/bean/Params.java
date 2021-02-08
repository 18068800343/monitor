package cn.org.hsxx.bean;

public class Params {
	
	/*
	 * datatables分页所需bean
	 * sEcho:当前页
	 * iDisplayStart:起始页
	 * iDisplayLength:每页显示
	 */
	private int sEcho;
	private int iDisplayStart;
	private int iDisplayLength;
	public int getsEcho() {
		return sEcho;
	}
	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}
	public int getiDisplayStart() {
		return iDisplayStart;
	}
	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}
	public int getiDisplayLength() {
		return iDisplayLength;
	}
	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}	
}
