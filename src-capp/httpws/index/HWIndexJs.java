package httpws.index;

import httpws.builder.js.HwJavascript;
import httpws.builder.js.HwJsOpcodeReaderClass;

public class HWIndexJs extends HwJavascript {
	
	public static enum $var {
		FILE_READER, WS, READER
	}
	
	public HWIndexJs(String wsUrl) {
		$def($var.FILE_READER, $new($JSClass.FILE_READER));
		$def($var.WS, $new($JSClass.WEB_SOCKET, $val(wsUrl)));
		$def(CANVAS, $(DOCUMENT, $JSDocument.CREATE_ELEMENT).$call($val(CANVAS)));
		$(WINDOW, ONRESIZE).$assign($func(() -> {
			$(CANVAS, WIDTH).$assign($(WINDOW, INNER_WIDTH));
			$(CANVAS, HEIGHT).$assign($(WINDOW, INNER_HEIGHT));
		}));
		$(WINDOW, ONHASHCHANGE).$assign($func(() -> {

		}));
		$(WINDOW, ONLOAD).$assign($func(() -> {
			$exec($(DOCUMENT, BODY, $JSElement.APPEND_CHILD).$call($var(CANVAS)));
			$(CANVAS, WIDTH).$assign($(WINDOW, INNER_WIDTH));
			$(CANVAS, HEIGHT).$assign($(WINDOW, INNER_HEIGHT));
			$(WINDOW, "ctx").$assign($(CANVAS, GET_CONTEXT).$call($val("2d")));
			$($var.WS, "onopen").$assign($func(() -> {
				$exec($($var.WS, "send").$call($val("open:").$sum($(LOCATION, HASH)).$sum($val("\\n"))));
			}));
			$($var.WS, "onclose").$assign($func(() -> {
			}));
			$($var.WS, "onerror").$assign($func(() -> {
			}));
			$($var.WS, "onmessage").$assign($func(() -> {
				$def("reader", $new(HwJsOpcodeReaderClass.OPCODE_READER, $("evt", "data"), $func(() -> {
					$while($($var.READER, "empty").$call().$not(), () -> {
						$switch($($var.READER, "opcode").$call(), null);
					});
				})));
			}, "evt"));
		}));
		$add(new HwJsOpcodeReaderClass());
	}
}
