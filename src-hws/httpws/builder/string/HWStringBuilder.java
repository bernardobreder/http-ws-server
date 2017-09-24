package httpws.builder.string;

public class HWStringBuilder {
	
	/**  */
	private StringBuilder value;
	
	/**
	 * Construtor
	 */
	public HWStringBuilder() {
		this(256);
	}
	
	/**
	 * @param initialCapacity
	 */
	public HWStringBuilder(int initialCapacity) {
		value = new StringBuilder(initialCapacity);
	}
	
	/**
	 * @return length
	 * @see java.lang.StringBuilder#length()
	 */
	public int length() {
		return value.length();
	}
	
	/**
	 * @param obj
	 * @return this
	 * @see java.lang.StringBuilder#append(java.lang.Object)
	 */
	public HWStringBuilder append(Object obj) {
		value.append(String.valueOf(obj));
		return this;
	}
	
	/**
	 * @param str
	 * @return this
	 * @see java.lang.StringBuilder#append(java.lang.String)
	 */
	public HWStringBuilder append(String str) {
		value.append(eval(str));
		return this;
	}
	
	/**
	 * @param b
	 * @return this
	 * @see java.lang.StringBuilder#append(boolean)
	 */
	public HWStringBuilder append(boolean b) {
		value.append(b);
		return this;
	}
	
	/**
	 * @param i
	 * @return this
	 * @see java.lang.StringBuilder#append(int)
	 */
	public HWStringBuilder append(int i) {
		value.append(i);
		return this;
	}
	
	/**
	 * @param lng
	 * @return this
	 * @see java.lang.StringBuilder#append(long)
	 */
	public HWStringBuilder append(long lng) {
		value.append(lng);
		return this;
	}
	
	/**
	 * @param f
	 * @return this
	 * @see java.lang.StringBuilder#append(float)
	 */
	public HWStringBuilder append(float f) {
		value.append(f);
		return this;
	}
	
	/**
	 * @param d
	 * @return this
	 * @see java.lang.StringBuilder#append(double)
	 */
	public HWStringBuilder append(double d) {
		value.append(d);
		return this;
	}
	
	/**
	 * @param string
	 * @return texto convertido
	 */
	protected String eval(String string) {
		String[] marks = new String[] { "---", "___", "***", "|||", "///" };
		StringBuilder sb = new StringBuilder((int) (string.length() * 1.2));
		char[] chars = string.toCharArray();
		int length = chars.length;
		for (int i = 0; i < length; i++) {
			boolean changed = false;
			char c = chars[i];
			switch (c) {
				case '*':
					if (i + 2 < length && chars[i + 1] == '*' && chars[i + 2] == '*') {
						int j = i + 3;
						boolean hasFinal = false;
						while (j + 2 < length) {
							if (chars[j] == '*' && chars[j + 1] == '*' && chars[j + 2] == '*') {
								hasFinal = true;
								break;
							}
							j++;
						}
						if (!hasFinal) {
							break;
						}
						changed = true;
						sb.append("<b>");
						sb.append(chars, i + 3, j - i - 3);
						sb.append("</b>");
						i = j + (3 - 1);
					}
					break;
				case '-':
					if (i + 2 < length && chars[i + 1] == '-' && chars[i + 2] == '-') {
						int j = i + 3;
						boolean hasFinal = false;
						while (j + 2 < length) {
							if (chars[j] == '-' && chars[j + 1] == '-' && chars[j + 2] == '-') {
								hasFinal = true;
								break;
							}
							j++;
						}
						if (!hasFinal) {
							break;
						}
						changed = true;
						sb.append("<s>");
						sb.append(chars, i + 3, j - i - 3);
						sb.append("</s>");
						i = j + (3 - 1);
					}
					break;
				case '_':
					if (i + 2 < length && chars[i + 1] == '_' && chars[i + 2] == '_') {
						int j = i + 3;
						boolean hasFinal = false;
						while (j + 2 < length) {
							if (chars[j] == '_' && chars[j + 1] == '_' && chars[j + 2] == '_') {
								hasFinal = true;
								break;
							}
							j++;
						}
						if (!hasFinal) {
							break;
						}
						changed = true;
						sb.append("<u>");
						sb.append(chars, i + 3, j - i - 3);
						sb.append("</u>");
						i = j + (3 - 1);
					}
					break;
				case '|':
					if (i + 2 < length && chars[i + 1] == '|' && chars[i + 2] == '|') {
						int j = i + 3;
						boolean hasFinal = false;
						while (j + 2 < length) {
							if (chars[j] == '|' && chars[j + 1] == '|' && chars[j + 2] == '|') {
								hasFinal = true;
								break;
							}
							j++;
						}
						if (!hasFinal) {
							break;
						}
						changed = true;
						sb.append("<mark>");
						sb.append(chars, i + 3, j - i - 3);
						sb.append("</mark>");
						i = j + (3 - 1);
					}
					break;
				case '/':
					if (i + 2 < length && chars[i + 1] == '/' && chars[i + 2] == '/') {
						int j = i + 3;
						boolean hasFinal = false;
						while (j + 2 < length) {
							if (chars[j] == '/' && chars[j + 1] == '/' && chars[j + 2] == '/') {
								hasFinal = true;
								break;
							}
							j++;
						}
						if (!hasFinal) {
							break;
						}
						changed = true;
						sb.append("<i>");
						sb.append(chars, i + 3, j - i - 3);
						sb.append("</i>");
						i = j + (3 - 1);
					}
					break;
			}
			if (!changed) {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * @return string
	 * @see java.lang.StringBuilder#toString()
	 */
	@Override
	public String toString() {
		return value.toString();
	}
	
}
