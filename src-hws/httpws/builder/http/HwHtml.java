package httpws.builder.http;

import java.util.ArrayList;
import java.util.List;

import httpws.builder.HwStack;
import httpws.builder.css.HwCss;
import httpws.builder.js.HwJavascript;
import httpws.nio.HwBuffer;

public class HwHtml {

	protected HwStack<HwParentTagHtml> stack;

	private HwParentTagHtml root;

	private HwParentTagHtml html;

	private HwParentTagHtml body;

	private HwParentTagHtml head;

	public HwHtml() {
		stack = new HwStack<HwParentTagHtml>();
		stack.push(root = new HwParentTagHtml(null, true));
	}

	public HwHtml $eof() {
		return this;
	}

	public void $reset() {
		// TODO Auto-generated method stub

	}

	public static class $a {

		public static HwAttributeTagHtml $href(String link) {
			return new HwAttributeTagHtml("href", link);
		}

	}

	public HwHtml $html(Runnable r) {
		r.run();
		return this;
	}

	public HwHtml $html() {
		if (html == null) {
			root.addChild(html = new HwParentTagHtml("html", true));
		}
		return this;
	}

	public HwHtml $head(Runnable r) {
		$head();
		r.run();
		return this;
	}

	public HwHtml $head() {
		$html();
		if (head == null) {
			html.addChild(head = new HwParentTagHtml("head", true));
		}
		return this;
	}

	public HwHtml $body(Runnable r) {
		$body();
		r.run();
		return this;
	}

	public HwHtml $body() {
		$html();
		if (body == null) {
			html.addChild(body = new HwParentTagHtml("body", true));
			stack.push(body);
		}
		return this;
	}

	public void $title(Object t) {
		$head();
		head.addChild(new HwContentTagHtml("title", $val(t)));
	}

	public void $css(HwCss css) {
		$head();
		head.addChild(new HwContentTagHtml("style", css.toString()));
	}

	public void $js(HwJavascript js) {
		$head();
		head.addChild(new HwContentTagHtml("script", js.toString()));
	}

	public void $def(String t, Object value) {
		// TODO Auto-generated method stub

	}

	public String $val(Object value) {
		return value.toString();
	}

	public HwHtml $a(String text) {
		HwParentTagHtml tagHtml = new HwParentTagHtml("a", true);
		tagHtml.addChild(new HwContentTagHtml(null, text));
		stack.peek().addChild(tagHtml);
		return this;
	}

	public HwHtml $a(String text, HwAttributeTagHtml... attributes) {
		HwParentTagHtml tagHtml = new HwParentTagHtml("a", true);
		for (int n = 0; n < attributes.length; n++) {
			tagHtml.addAttribute(attributes[n]);
		}
		tagHtml.addChild(new HwContentTagHtml(null, text));
		stack.peek().addChild(tagHtml);
		return this;
	}

	public HwHtml $text(Object text, Object... attributes) {
		stack.peek().addChild(new HwContentTagHtml(null, $val(text)));
		return this;
	}

	public HwHtml $p(Object... attributes) {
		// TODO Auto-generated method stub
		return this;
	}

	public HwHtml $strong(Object... attributes) {
		// TODO Auto-generated method stub
		return this;
	}

	public HwHtml $small(Object... attributes) {
		// TODO Auto-generated method stub
		return this;
	}

	public HwHtml $button(Object... attributes) {
		// TODO Auto-generated method stub
		return this;
	}

	public HwHtml $onclick(HwJavascript js) {
		// TODO Auto-generated method stub
		return this;
	}

	public HwHtml $p(Runnable r, Object... attributes) {
		// TODO Auto-generated method stub
		return this;
	}

	public static HwAttributeTagHtml $class(String name) {
		return new HwAttributeTagHtml("class", name);
	}

	public static HwAttributeTagHtml $id(String value) {
		return new HwAttributeTagHtml("id", value);
	}

	public static HwAttributeTagHtml $attr(String key, Object value) {
		return new HwAttributeTagHtml(key, value == null ? "null" : value.toString());
	}

	public void write(HwBuffer buffer) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return root.toString();
	}

	private static abstract class HwTagHtml {

		public final String tag;

		public List<HwAttributeTagHtml> attributes;

		public HwTagHtml(String tag) {
			super();
			this.tag = tag;
		}

		public void addAttribute(HwAttributeTagHtml attr) {
			if (attributes == null) {
				attributes = new ArrayList<HwAttributeTagHtml>(4);
			}
			attributes.add(attr);
		}

		public abstract int getLength();

		@Override
		public abstract String toString();

	}

	private static class HwContentTagHtml extends HwTagHtml {

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

	private static class HwParentTagHtml extends HwTagHtml {

		public List<HwTagHtml> children;

		public final boolean forceBody;

		public HwParentTagHtml(String tag, boolean forceBody) {
			super(tag);
			this.forceBody = forceBody;
		}

		public HwTagHtml addChild(HwTagHtml child) {
			if (children == null) {
				children = new ArrayList<HwTagHtml>(8);
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

	public static class HwAttributeTagHtml {

		public final String key;

		public final String value;

		/**
		 * Construtor
		 *
		 * @param key
		 * @param value
		 */
		public HwAttributeTagHtml(String key, String value) {
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
