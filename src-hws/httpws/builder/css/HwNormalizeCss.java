package httpws.builder.css;

/**
 * {@link "https://necolas.github.io/normalize.css/"}
 *
 * @author Bernardo Breder
 */
public class HwNormalizeCss extends HwCss {
	
	private static final String BUTTON = "button";
	
	private static final String VISIBLE = "visible";
	
	private static final String NORMAL = "normal";
	
	private static final String BORDER_BOX = "border-box";
	
	private static final String POINTER = "pointer";
	
	private static final String BASELINE = "baseline";
	
	private static final String INLINE_BLOCK = "inline-block";
	
	private static final String BLOCK = "block";
	
	private static final String RELATIVE = "relative";
	
	private static final String INHERIT = "inherit";
	
	private static final String HIDDEN = "hidden";
	
	private static final String NONE = "none";
	
	private static final String TRANSPARENT = "transparent";
	
	private static final String CONTENT_BOX = "content-box";
	
	private static final String AUTO = "auto";
	
	private static final String COLLAPSE = "collapse";
	
	public HwNormalizeCss() {
		$html().$fontFamily($FontFamily.SANS_SERIF).$("-ms-text-size-adjust", "100%").$("-webkit-text-size-adjust", "100%");
		$body().$margin(0);
		$($article(), $aside(), $details(), $figcaption(), $figure(), $footer(), $header(), $hgroup(), $main(), $menu(), $nav(), $section(), $summary()).$display(BLOCK);
		$($audio(), $canvas(), $progress(), $video()).$display(INLINE_BLOCK).$verticalAlign(BASELINE);
		$("audio:not([controls])").$display(NONE).$height(0);
		$("[hidden]", $template()).$display(NONE);
		$a().$backgroundColor(TRANSPARENT);
		$("a:active", "a:hover").$outline(0);
		$("abbr[title]").$borderBottom("1px dotted");
		$($b(), $strong()).$fontWeight(HSFontType.BOLD);
		$dfn().$fontStyle(HSFontStyle.ITALIC);
		$h1().$fontSize("2em").$margin("0.67em 0");
		$mark().$background("#ff0").$color("#000");
		$small().$fontSize("80%");
		$($sub(), $sup()).$fontSize("75%").$lineHeight(0).$position(RELATIVE).$verticalAlign(BASELINE);
		$sup().$top("-0.5em");
		$sub().$bottom("-0.25em");
		$img().$border(0);
		$("svg:not(:root)").$overflow(HIDDEN);
		$figure().$margin("1em 40px");
		$hr().$boxSizingMoz(CONTENT_BOX).$boxSizing(CONTENT_BOX).$height(0);
		$pre().$overflow(AUTO);
		$($code(), $kbd(), $pre(), $samp()).$fontFamily("monospace, monospace").$fontSize("1em");
		$($button(), $input(), $optgroup(), $select(), $textarea()).$color(INHERIT).$font(INHERIT).$margin(0);
		$button().$overflow(VISIBLE);
		$($button(), $select()).$textTransform(NONE);
		$($button(), $("html input[type='button']"), $("input[type='reset']"), $("input[type='submit']")).$appearanceWebkit(BUTTON).$cursor(POINTER);
		$($("button[disabled]"), $("html input[disabled]")).$cursor("default");
		$($("button::-moz-focus-inner"), $("input::-moz-focus-inner")).$border(0).$padding(0);
		$input().$lineHeight(NORMAL);
		$($("input[type='checkbox']"), $("input[type='radio']")).$boxSizing(BORDER_BOX).$padding(0);
		$($("input[type='number']::-webkit-inner-spin-button", "input[type='number']::-webkit-outer-spin-button")).$height(AUTO);
		$("input[type='search']").$appearanceWebkit("textfield").$boxSizingMoz(CONTENT_BOX).$boxSizingWebKit(CONTENT_BOX).$boxSizing(CONTENT_BOX);
		$($("input[type='search']::-webkit-search-cancel-button"), $("input[type='search']::-webkit-search-decoration")).$appearanceWebkit(NONE);
		$fieldset().$border("1px solid #c0c0c0").$margin("0 2px").$padding("0.35em 0.625em 0.75em");
		$legend().$border(0).$padding(0);
		$textarea().$overflow(AUTO);
		$optgroup().$fontWeight(HSFontType.BOLD);
		$table().$borderCollapse(COLLAPSE).$borderSpacing(0);
		$($td(), $th()).$padding(0);
	}
	
	public static void main(String[] args) {
		System.out.println(new HwNormalizeCss().toString());
	}
	
}
