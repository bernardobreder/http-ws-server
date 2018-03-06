package httpws.builder.css;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import httpws.util.Charset;

/**
 * Estilo através de estrutura Java
 *
 * @author Bernardo Breder
 */
public class HwCss {
	
	protected final Map<String, Map<String, String>> tags;
	
	protected final String content;
	
	public HwCss() {
		tags = new TreeMap<String, Map<String, String>>();
		content = null;
	}
	
	public HwCss(InputStream in, int length) throws IOException {
		if (length <= 0) {
			length = 1024;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream(length);
		byte[] bytes = new byte[1024];
		for (int n; (n = in.read(bytes)) != -1;) {
			out.write(bytes, 0, n);
		}
		content = Charset.utf8(out.toByteArray());
		tags = null;
	}
	
	public HSStyleTag $(Object... names) {
		List<String> list = new ArrayList<String>(names.length);
		for (int n = 0; n < names.length; n++) {
			Object name = names[n];
			if (name instanceof HSStyleTag) {
				HSStyleTag tag = (HSStyleTag) name;
				for (int m = 0; m < tag.tags.length; m++) {
					list.add(tag.tags[m]);
				}
			} else {
				list.add(name.toString());
			}
		}
		return new HSStyleTag(list.toArray(new String[list.size()]));
	}
	
	public HSStyleTag $a() {
		return new HSStyleTag("a");
	}
	
	public HSStyleTag $abbr() {
		return new HSStyleTag("abbr");
	}
	
	public HSStyleTag $address() {
		return new HSStyleTag("address");
	}
	
	public HSStyleTag $area() {
		return new HSStyleTag("area");
	}
	
	public HSStyleTag $article() {
		return new HSStyleTag("article");
	}
	
	public HSStyleTag $aside() {
		return new HSStyleTag("aside");
	}
	
	public HSStyleTag $audio() {
		return new HSStyleTag("audio");
	}
	
	public HSStyleTag $b() {
		return new HSStyleTag("b");
	}
	
	public HSStyleTag $dfn() {
		return new HSStyleTag("dfn");
	}
	
	public HSStyleTag $mark() {
		return new HSStyleTag("mark");
	}
	
	public HSStyleTag $base() {
		return new HSStyleTag("base");
	}
	
	public HSStyleTag $bdo() {
		return new HSStyleTag("bdo");
	}
	
	public HSStyleTag $blockquote() {
		return new HSStyleTag("blockquote");
	}
	
	public HSStyleTag $body() {
		return new HSStyleTag("body");
	}
	
	public HSStyleTag $br() {
		return new HSStyleTag("blockquote");
	}
	
	public HSStyleTag $button() {
		return new HSStyleTag("button");
	}
	
	public HSStyleTag $canvas() {
		return new HSStyleTag("canvas");
	}
	
	public HSStyleTag $caption() {
		return new HSStyleTag("caption");
	}
	
	public HSStyleTag $cite() {
		return new HSStyleTag("cite");
	}
	
	public HSStyleTag $code() {
		return new HSStyleTag("code");
	}
	
	public HSStyleTag $col() {
		return new HSStyleTag("col");
	}
	
	// Terminou aqui http://www.w3schools.com/tags/
	public HSStyleTag $kbd() {
		return new HSStyleTag("kbd");
	}
	
	public HSStyleTag $samp() {
		return new HSStyleTag("samp");
	}
	
	public HSStyleTag $details() {
		return new HSStyleTag("details");
	}
	
	public HwCss $eof() {
		return this;
	}
	
	public HSStyleTag $figcaption() {
		return new HSStyleTag("figcaption");
	}
	
	public HSStyleTag $figure() {
		return new HSStyleTag("figure");
	}
	
	public HSStyleTag $footer() {
		return new HSStyleTag("footer");
	}
	
	public HSStyleTag $h1() {
		return new HSStyleTag("h1");
	}
	
	public HSStyleTag $h2() {
		return new HSStyleTag("h2");
	}
	
	public HSStyleTag $h3() {
		return new HSStyleTag("h3");
	}
	
	public HSStyleTag $h4() {
		return new HSStyleTag("h4");
	}
	
	public HSStyleTag $h5() {
		return new HSStyleTag("h5");
	}
	
	public HSStyleTag $h6() {
		return new HSStyleTag("h6");
	}
	
	public HSStyleTag $head() {
		return new HSStyleTag("head");
	}
	
	public HSStyleTag $header() {
		return new HSStyleTag("header");
	}
	
	public HSStyleTag $hgroup() {
		return new HSStyleTag("hgroup");
	}
	
	public HSStyleTag $html() {
		return new HSStyleTag("html");
	}
	
	public HSStyleTag $main() {
		return new HSStyleTag("main");
	}
	
	public HSStyleTag $menu() {
		return new HSStyleTag("menu");
	}
	
	public HSStyleTag $nav() {
		return new HSStyleTag("nav");
	}
	
	public HSStyleTag $p() {
		return new HSStyleTag("p");
	}
	
	public HSStyleTag $progress() {
		return new HSStyleTag("progress");
	}
	
	public void $reset() {
		tags.clear();
	}
	
	public HSStyleTag $section() {
		return new HSStyleTag("section");
	}
	
	public HSStyleTag $small() {
		return new HSStyleTag("small");
	}
	
	public HSStyleTag $sub() {
		return new HSStyleTag("sub");
	}
	
	public HSStyleTag $img() {
		return new HSStyleTag("img");
	}
	
	public HSStyleTag $hr() {
		return new HSStyleTag("hr");
	}
	
	public HSStyleTag $pre() {
		return new HSStyleTag("pre");
	}
	
	public HSStyleTag $sup() {
		return new HSStyleTag("sup");
	}
	
	public HSStyleTag $strong() {
		return new HSStyleTag("strong");
	}
	
	public HSStyleTag $summary() {
		return new HSStyleTag("summary");
	}
	
	public HSStyleTag $template() {
		return new HSStyleTag("template");
	}
	
	public HSStyleTag $input() {
		return new HSStyleTag("input");
	}
	
	public HSStyleTag $fieldset() {
		return new HSStyleTag("fieldset");
	}
	
	public HSStyleTag $legend() {
		return new HSStyleTag("legend");
	}
	
	public HSStyleTag $table() {
		return new HSStyleTag("table");
	}
	
	public HSStyleTag $td() {
		return new HSStyleTag("td");
	}
	
	public HSStyleTag $th() {
		return new HSStyleTag("th");
	}
	
	public HSStyleTag $optgroup() {
		return new HSStyleTag("optgroup");
	}
	
	public HSStyleTag $select() {
		return new HSStyleTag("select");
	}
	
	public HSStyleTag $textarea() {
		return new HSStyleTag("textarea");
	}
	
	public HSStyleTag $video() {
		return new HSStyleTag("video");
	}
	
	protected static String $idName(Enum<?> item) {
		return item.toString().toLowerCase().replace('_', '-');
	}
	
	protected void put(String[] tags, String key, String value) {
		if (content != null) { throw new IllegalArgumentException(); }
		for (int n = 0; n < tags.length; n++) {
			String tag = tags[n];
			Map<String, String> map = this.tags.get(tag);
			if (map == null) {
				this.tags.put(tag, map = new TreeMap<String, String>());
			}
			map.put(key, value);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (content != null) { return content; }
		StringBuilder sb = new StringBuilder();
		for (Entry<String, Map<String, String>> entry : tags.entrySet()) {
			String tag = entry.getKey();
			Map<String, String> value = entry.getValue();
			sb.append(tag);
			sb.append(" {\n");
			for (Entry<String, String> item : value.entrySet()) {
				sb.append('\t');
				sb.append(item.getKey());
				sb.append(": ");
				sb.append(item.getValue());
				sb.append(";\n");
			}
			sb.append("}\n");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
	
	public static enum $a {
		NOT
	}
	
	public static enum $attr {
		TYPE
	}
	
	public static enum $b {
		CONTROLS(), ROOT(":root");
		
		private $b() {
		}
		
		private $b(String t) {
		}
	}
	
	public static enum $FontFamily {
		SANS_SERIF;
	}
	
	public enum HSFontStyle {
		ITALIC;
	}
	
	public enum HSFontType {
		BOLD
	}
	
	public class HSStyleTag {
		
		private String[] tags;
		
		public HSStyleTag(HSStyleTag[] tags) {
			List<String> list = new ArrayList<String>(tags.length);
			for (int n = 0; n < tags.length; n++) {
				HSStyleTag tag1 = tags[n];
				for (int m = 0; m < tag1.tags.length; m++) {
					list.add(tag1.tags[m]);
				}
			}
			this.tags = list.toArray(new String[list.size()]);
		}
		
		public HSStyleTag(String tag) {
			this.tags = new String[] { tag };
		}
		
		public HSStyleTag(String[] tags) {
			this.tags = tags;
		}
		
		public HSStyleTag $(String attribute, String value) {
			put(tags, attribute, value);
			return this;
		}
		
		public HSStyleTag $background(String value) {
			put(tags, "background", value);
			return this;
		}
		
		public HSStyleTag $backgroundColor(String value) {
			put(tags, "background-color", value);
			return this;
		}
		
		public HwCss $close() {
			return HwCss.this;
		}
		
		public HSStyleTag $color(String value) {
			put(tags, "color", value);
			return this;
		}
		
		public HSStyleTag $display(String value) {
			put(tags, "display", value);
			return this;
		}
		
		public HSStyleTag $fontFamily(Enum<?> name) {
			put(tags, "font-family", $idName(name));
			return this;
		}
		
		public HSStyleTag $fontFamily(String name) {
			put(tags, "font-family", name);
			return this;
		}
		
		public HSStyleTag $fontSize(int pxs) {
			StringBuilder sb = new StringBuilder();
			sb.append(pxs);
			sb.append("px");
			put(tags, "font-size", sb.toString());
			return this;
		}
		
		public HSStyleTag $lineHeight(int pxs) {
			StringBuilder sb = new StringBuilder();
			sb.append(pxs);
			sb.append("px");
			put(tags, "line-height", sb.toString());
			return this;
		}
		
		public HSStyleTag $fontSize(String pxs) {
			put(tags, "font-size", pxs);
			return this;
		}
		
		public HSStyleTag $lineHeight(String pxs) {
			put(tags, "line-height", pxs);
			return this;
		}
		
		public HSStyleTag $font(String pxs) {
			put(tags, "font", pxs);
			return this;
		}
		
		public HSStyleTag $cursor(String pxs) {
			put(tags, "cursor", pxs);
			return this;
		}
		
		public HSStyleTag $width(String value) {
			put(tags, "width", value);
			return this;
		}
		
		public HSStyleTag $top(String value) {
			put(tags, "top", value);
			return this;
		}
		
		public HSStyleTag $left(String value) {
			put(tags, "left", value);
			return this;
		}
		
		public HSStyleTag $right(String value) {
			put(tags, "right", value);
			return this;
		}
		
		public HSStyleTag $bottom(String value) {
			put(tags, "bottom", value);
			return this;
		}
		
		public HSStyleTag $textTransform(String value) {
			put(tags, "text-transform", value);
			return this;
		}
		
		public HSStyleTag $border(String value) {
			put(tags, "border", value);
			return this;
		}
		
		public HSStyleTag $position(String value) {
			put(tags, "position", value);
			return this;
		}
		
		public HSStyleTag $overflow(String value) {
			put(tags, "overflow", value);
			return this;
		}
		
		public HSStyleTag $fontStyle(HSFontStyle type) {
			put(tags, "font-style", $idName(type));
			return this;
		}
		
		public HSStyleTag $fontWeight(HSFontType type) {
			put(tags, "font-weight", $idName(type));
			return this;
		}
		
		public HSStyleTag $borderCollapse(String value) {
			put(tags, "border-collapse", value);
			return this;
		}
		
		public HSStyleTag $appearanceWebkit(String value) {
			put(tags, "-webkit-appearance", value);
			return this;
		}
		
		public HSStyleTag $height(int pxs) {
			put(tags, "height", new StringBuilder().append(pxs).append("px").toString());
			return this;
		}
		
		public HSStyleTag $borderSpacing(int pxs) {
			put(tags, "border-spacing", new StringBuilder().append(pxs).append("px").toString());
			return this;
		}
		
		public HSStyleTag $height(String value) {
			put(tags, "height", value);
			return this;
		}
		
		public HSStyleTag $border(int pxs) {
			put(tags, "border", new StringBuilder().append(pxs).append("px").toString());
			return this;
		}
		
		public HSStyleTag $margin(int size) {
			StringBuilder sb = new StringBuilder();
			sb.append(size);
			sb.append("px");
			put(tags, "margin", sb.toString());
			return this;
		}
		
		public HSStyleTag $margin(int top, int left, int bottom, int right) {
			StringBuilder sb = new StringBuilder();
			sb.append(top);
			sb.append("px ");
			sb.append(left);
			sb.append("px ");
			sb.append(bottom);
			sb.append("px ");
			sb.append(right);
			sb.append("px");
			put(tags, "margin", sb.toString());
			return this;
		}
		
		public HSStyleTag $margin(String value) {
			put(tags, "margin", value);
			return this;
		}
		
		public HSStyleTag $boxSizing(String value) {
			put(tags, "box-sizing", value);
			return this;
		}
		
		public HSStyleTag $borderBottom(String value) {
			put(tags, "border-bottom", value);
			return this;
		}
		
		public HSStyleTag $boxSizingMoz(String value) {
			put(tags, "-moz-box-sizing", value);
			return this;
		}
		
		public HSStyleTag $boxSizingWebKit(String value) {
			put(tags, "-webkit-box-sizing", value);
			return this;
		}
		
		public HSStyleTag $outline(int pxs) {
			put(tags, "outline", new StringBuilder().append(pxs).append("px").toString());
			return this;
		}
		
		public HSStyleTag $padding(int size) {
			StringBuilder sb = new StringBuilder();
			sb.append(size);
			sb.append("px");
			put(tags, "padding", sb.toString());
			return this;
		}
		
		public HSStyleTag $padding(String value) {
			put(tags, "padding", value);
			return this;
		}
		
		public HSStyleTag $padding(int top, int left, int bottom, int right) {
			StringBuilder sb = new StringBuilder();
			sb.append(top);
			sb.append("px ");
			sb.append(left);
			sb.append("px ");
			sb.append(bottom);
			sb.append("px ");
			sb.append(right);
			sb.append("px");
			put(tags, "padding", sb.toString());
			return this;
		}
		
		public HSStyleTag $verticalAlign(String value) {
			put(tags, "vertical-align", value);
			return this;
		}
		
	}
	
}
