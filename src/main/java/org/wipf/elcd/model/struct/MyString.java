package org.wipf.elcd.model.struct;

/**
 * @author wipf
 *
 */
public class MyString {
	public static final String REGEX = "^[a-zA-z 0-9öäü?!().;.\\-_+*ß]+$";

	private String s;

	public MyString(String s) {
		setS(s);
	}

	/**
	 * @return
	 */
	public String getS() {
		return s;
	}

	/**
	 * @param s
	 */
	public void setS(String s) {
		if (s.matches(REGEX))
			this.s = s;
		else
			this.s = "fail";
	}

}