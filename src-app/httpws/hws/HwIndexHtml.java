package httpws.hws;

import httpws.builder.css.HwCss;
import httpws.builder.http.HwHtml;
import httpws.builder.js.HwJavascript;

/**
 * Página inicial do Index
 *
 * @author Tecgraf/PUC-Rio
 */
public class HwIndexHtml extends HwHtml {
	
	public HwIndexHtml(HwCss bootstrapCss, HwCss bootstrapThemeCss, HwCss hwCss, HwJavascript jqueryJs, HwJavascript bootstrapJs, HwJavascript hwJs) {
		$html(() -> {
			$head(() -> {
				$title("Index");
				$css(bootstrapCss);
				$css(bootstrapThemeCss);
				$css(hwCss);
				$js(jqueryJs);
				$js(bootstrapJs);
				$js(hwJs);
			});
			$body(() -> {
				
			});
		});
	}
	
}
