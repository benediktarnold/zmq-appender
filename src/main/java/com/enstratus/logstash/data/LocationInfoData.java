package com.enstratus.logstash.data;

import org.apache.log4j.spi.LocationInfo;

public class LocationInfoData {
	public String file;
	public String clazz;
	public String method;
	public String line;

	public LocationInfoData() {
		super();
	}

	public LocationInfoData(final LocationInfo info) {
		this();
		this.file = info.getFileName();
		this.clazz = info.getClassName();
		this.method = info.getMethodName();
		this.line = info.getLineNumber();
	}

	public LocationInfo toLocationInfo() {
		LocationInfo locationInfo = new LocationInfo(null,null);
		locationInfo.fullInfo = toFullInfo(this.file, this.clazz, this.method, this.line);
		return locationInfo;
	}

	/**
	 * The following stuff comes from log4j 1.2.16 wich is not available in
	 * jboss epp 5.1
	 * 
	 * /** When location information is not available the constant
	 * <code>NA</code> is returned. Current value of this string constant is
	 * <b>?</b>.
	 */
	public final static String NA = "?";

	/**
	 * Appends a location fragment to a buffer to build the full location info.
	 * 
	 * @param buf
	 *            StringBuffer to receive content.
	 * @param fragment
	 *            fragment of location (class, method, file, line), if null the
	 *            value of NA will be appended.
	 * @since 1.2.15
	 */
	private static final void appendFragment(final StringBuffer buf,
			final String fragment) {
		if (fragment == null) {
			buf.append(NA);
		} else {
			buf.append(fragment);
		}
	}

	/**
	 * Create new instance.
	 * 
	 * @param file
	 *            source file name
	 * @param classname
	 *            class name
	 * @param method
	 *            method
	 * @param line
	 *            source line number
	 *
	 * @since 1.2.15
	 */
	private static String toFullInfo(final String file, final String classname,
			final String method, final String line) {
		StringBuffer buf = new StringBuffer();
		appendFragment(buf, classname);
		buf.append(".");
		appendFragment(buf, method);
		buf.append("(");
		appendFragment(buf, file);
		buf.append(":");
		appendFragment(buf, line);
		buf.append(")");
		return buf.toString();
	}
}
