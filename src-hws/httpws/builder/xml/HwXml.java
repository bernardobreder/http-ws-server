package httpws.builder.xml;

import java.util.ArrayList;
import java.util.List;

import httpws.builder.HwStack;

public class HwXml {
	
	protected HwStack<HwParentTag> stack;
	
	private HwParentTag root;
	
	public HwXml() {
		stack = new HwStack<HwParentTag>();
		stack.push(root = new HwParentTag(null, true));
	}
	
	public HwXml $eof() {
		return this;
	}
	
	public HwXml $text(String text, HwAttributeTag... attributes) {
		HwContentTagHtml tag = new HwContentTagHtml(null, text);
		if (attributes != null && attributes.length > 0) {
			tag.addAttributes(attributes);
		}
		stack.peek().addChild(tag);
		return this;
	}
	
	public HwXml $(String name, HwAttributeTag... attributes) {
		HwContentTagHtml tag = new HwContentTagHtml(name, null);
		if (attributes != null && attributes.length > 0) {
			tag.addAttributes(attributes);
		}
		stack.peek().addChild(tag);
		return this;
	}
	
	public HwXml $(String name, Runnable r, HwAttributeTag... attributes) {
		HwParentTag tag = new HwParentTag(name, r != null);
		if (attributes != null && attributes.length > 0) {
			tag.addAttributes(attributes);
		}
		stack.peek().addChild(tag);
		stack.push(tag);
		r.run();
		stack.pop();
		return this;
	}
	
	public static HwAttributeTag $attr(String key, Object value) {
		return new HwAttributeTag(key, value == null ? "null" : value.toString());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return root.toString();
	}
	
	public static abstract class HwTag {
		
		public final String tag;
		
		public List<HwAttributeTag> attributes;
		
		public HwTag(String tag) {
			super();
			this.tag = tag;
		}
		
		public void addAttribute(HwAttributeTag attr) {
			if (attributes == null) {
				attributes = new ArrayList<HwAttributeTag>(4);
			}
			attributes.add(attr);
		}
		
		public void addAttributes(HwAttributeTag[] attrs) {
			if (attributes == null) {
				attributes = new ArrayList<HwAttributeTag>(attrs.length);
			}
			for (int n = 0; n < attrs.length; n++) {
				attributes.add(attrs[n]);
			}
		}
		
		public abstract int getLength();
		
		@Override
		public abstract String toString();
		
	}
	
	public static class HwContentTagHtml extends HwTag {
		
		public String content;
		
		/**
		 * Construtor
		 *
		 * @param tag
		 * @param content
		 */
		public HwContentTagHtml(String tag, String content) {
			super(tag);
			this.content = content;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public int getLength() {
			if (tag == null) { return content.length(); }
			int length = 1 + tag.length();
			if (attributes != null && !attributes.isEmpty()) {
				for (int n = 0; n < attributes.size(); n++) {
					length += attributes.get(n).getLength();
				}
			}
			length++;
			if (content != null) {
				length += content.length();
			}
			length += 2 + tag.length() + 1;
			return length;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			if (tag == null) { return content; }
			StringBuilder sb = new StringBuilder();
			sb.append('<');
			sb.append(tag);
			if (attributes != null && !attributes.isEmpty()) {
				for (int n = 0; n < attributes.size(); n++) {
					sb.append(attributes.get(n));
				}
			}
			sb.append('>');
			if (content != null) {
				sb.append(content);
			}
			sb.append("</");
			sb.append(tag);
			sb.append('>');
			return sb.toString();
		}
		
	}
	
	public static class HwParentTag extends HwTag {
		
		public List<HwTag> children;
		
		public final boolean forceBody;
		
		public HwParentTag(String tag, boolean forceBody) {
			super(tag);
			this.forceBody = forceBody;
		}
		
		public HwTag addChild(HwTag child) {
			if (children == null) {
				children = new ArrayList<HwTag>(8);
			}
			children.add(child);
			return this;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public int getLength() {
			int length = 0;
			boolean hasChildren = children != null && !children.isEmpty();
			if (forceBody || hasChildren) {
				if (tag != null) {
					length += 1 + tag.length();
					if (attributes != null && !attributes.isEmpty()) {
						for (int n = 0; n < attributes.size(); n++) {
							length += attributes.get(n).getLength();
						}
					}
					length++;
				}
				if (hasChildren) {
					for (int n = 0; n < children.size(); n++) {
						length += children.get(n).getLength();
					}
				}
				if (tag != null) {
					length += 2 + tag.length() + 1;
				}
			} else {
				length += 1 + tag.length();
				if (attributes != null && !attributes.isEmpty()) {
					for (int n = 0; n < attributes.size(); n++) {
						length += attributes.get(n).getLength();
					}
				}
				length += 2;
			}
			return length;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			boolean hasChildren = children != null && !children.isEmpty();
			StringBuilder sb = new StringBuilder();
			if (forceBody || hasChildren) {
				if (tag != null) {
					sb.append('<');
					sb.append(tag);
					if (attributes != null && !attributes.isEmpty()) {
						for (int n = 0; n < attributes.size(); n++) {
							sb.append(attributes.get(n));
						}
					}
					sb.append('>');
				}
				if (hasChildren) {
					for (int n = 0; n < children.size(); n++) {
						sb.append(children.get(n));
					}
				}
				if (tag != null) {
					sb.append("</");
					sb.append(tag);
					sb.append('>');
				}
			} else {
				sb.append('<');
				sb.append(tag);
				if (attributes != null && !attributes.isEmpty()) {
					for (int n = 0; n < attributes.size(); n++) {
						sb.append(attributes.get(n));
					}
				}
				sb.append("/>");
			}
			return sb.toString();
		}
		
	}
	
	public static class HwAttributeTag {
		
		public final String key;
		
		public final String value;
		
		/**
		 * Construtor
		 *
		 * @param key
		 * @param value
		 */
		public HwAttributeTag(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}
		
		/**
		 * @return comprimento
		 */
		public int getLength() {
			return 1 + key.length() + 2 + value.length() + 1;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(' ');
			sb.append(key);
			sb.append('=');
			sb.append('\'');
			sb.append(value);
			sb.append('\'');
			return sb.toString();
		}
		
	}
	
}
