package httpws.builder.http;

import httpws.builder.js.HwJavascript;

import org.junit.Assert;
import org.junit.Test;

public class HwHtmlTest extends HwHtml {

	@Test
	public void testHtml() {
		$html(() -> {
			$head(() -> {
				$title("...");
			});
			$body(() -> {
				$a("...", $a.$href("#"));
				$p($class("a"), $class("b"), $attr("id", 5));
			});
		});
		$body(() -> {
			$text("aaaa");
			$a("...", $a.$href("#"));
			$text("aaaa");
			$strong($a("...", $a.$href("#")));
			$small($text("aaaa"));
		});
		$body(() -> {
			String id = "a";
			$p(() -> {
				$text("aaaa", $id(id));
			}, $class("b"), $class("c"), $attr("id", 5));
			$button($onclick(new ButtonJs(id)));
		});
	}

	@Test
	public void testExprHtml() {
		eq("<html></html>", new HwHtml().$html());
		eq("<html><head></head></html>", new HwHtml().$head());
		eq("<html><body></body></html>", new HwHtml().$body());
		eq("<html><head></head></html>", new HwHtml().$head().$head());
		eq("<html><body></body></html>", new HwHtml().$body().$body());
		eq("<html><body>...</body></html>", new HwHtml().$body().$text("..."));
		eq("...", new HwHtml().$text("..."));
		eq("a<a>b</a>c", new HwHtml().$text("a").$a("b").$text("c"));
		eq("<a class='b'>a</a>", new HwHtml().$a("a", $class("b")));
		eq("<html><body><a>...</a></body></html>", new HwHtml().$body().$a("..."));
		eq("<html><body><a href='#'>...</a></body></html>", new HwHtml().$body().$a("...", $a.$href("#")));
		eq("<html><body><a>...</a><a>,,,</a></body></html>", new HwHtml().$body().$a("...").$a(",,,"));
	}

	@Test
	public void testBlock() {
		eq("<html><body><a>...</a><a>,,,</a>---</body></html>", $body(() -> {
			$a("...");
			$a(",,,");
			$text("---");
		}));
	}

	@Test
	public void testHeadTitle() {
		eq("<html><head><title>...</title></head></html>", $html(() -> {
			$head(() -> {
				$title("...");
			});
		}));
	}

	@Test
	public void testBodyText() {
		eq("<html><body>...</body></html>", $html(() -> {
			$body(() -> {
				$text("...");
			});
		}));
	}

	@Test
	public void testBodyTextVal() {
		eq("<html><head><title>...</title></head><body>...</body></html>", $html(() -> {
			$head(() -> {
				$title("...");
			});
			$body(() -> {
				$text("...");
			});
		}));
	}

	public static class ButtonJs extends HwJavascript {

		public ButtonJs(String id) {
			$("#" + id, INNER_TEXT).$assign("Ok");
		}

	}

	private void eq(String expected, HwHtml html) {
		Assert.assertEquals(expected, html.$eof().toString());
		html.$reset();
	}

}
