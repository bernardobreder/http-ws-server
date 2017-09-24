package httpws.builder.js;

public class HwJsOpcodeWriterClass extends HwJavascript {

	public static final String CLASS = "OpcodeWriter";

	public static class $Method {

		protected static final String UINT8 = "uint8";

		protected static final String UINT32C = "uint32c";

		protected static final String UINT32 = "uint32";

		protected static final String STRING = "string";

		protected static final String TRUNC = "trunc";

	}

	String ARRAY = "array", CAPACITY = "capacity", SIZE = "size";

	String DATA = "data", GROW = "grow";

	public HwJsOpcodeWriterClass() {
		$class(CLASS, () -> {
			init();
		});
		$method(CLASS, GROW, () -> {
			grow();
		}, DATA);
		$method(CLASS, $Method.TRUNC, () -> {
			trunc();
		}, DATA);
		$method(CLASS, $Method.UINT8, () -> {
			uint8();
		}, DATA);
		$method(CLASS, $Method.UINT32, () -> {
			uint32();
		}, DATA);
		$method(CLASS, $Method.UINT32C, () -> {
			uint32c();
		}, DATA);
		$method(CLASS, $Method.STRING, () -> {
			string();
		}, DATA);
	}

	protected void init() {
		$(THIS, SIZE).$assign(0);
		$(THIS, CAPACITY).$assign(8);
		$(THIS, ARRAY).$assign($new($JSClass.UINT8_ARRAY, ($new($JSClass.ARRAY_BUFFER, $(THIS, CAPACITY)))));
	}

	protected void grow() {
		String ARRAY = "array";
		$if($(THIS, SIZE).$ge($(THIS, CAPACITY)), () -> {
			$(THIS, CAPACITY).$assignmul(2);
			$def(ARRAY, $new($JSClass.UINT8_ARRAY, ($new($JSClass.ARRAY_BUFFER, $(THIS, CAPACITY)))));
			$fori(0, $(THIS, SIZE), () -> {
				$(ARRAY, $array($(I))).$assign($(THIS, ARRAY, $array($("i"))));
			});
			$(THIS, ARRAY).$assign($(ARRAY));
		});
	}

	protected void trunc() {
		$if($(THIS, SIZE).$neq($(THIS, CAPACITY)), () -> {
			$(THIS, CAPACITY).$assign($(THIS, SIZE));
			$(THIS, ARRAY).$assign($(THIS, ARRAY, $JSArrayBuffer.SLICE, $call(0, $(THIS, SIZE))));
		});
		$return($(THIS, ARRAY));
	}

	protected void uint8() {
		$(THIS, GROW, $call(1)).$exec();
		$(THIS, ARRAY, $array($(THIS, SIZE).$inc())).$assign($(DATA));
		$return($(THIS));
	}

	protected void uint32c() {
		$if($(DATA).$lt($(0x80)), () -> {
			$(THIS, $Method.UINT8, $call($(DATA).$andbit(0x7F))).$exec();
		}, () -> {
			$(THIS, $Method.UINT8, $call($cval($(DATA).$andbit(0x7F)).$sum(0x80))).$exec();
			$(THIS, $Method.UINT8, $call($cval($cval($(DATA).$rshift(7)).$andbit(0x7F)).$sum(0x80))).$exec();
			$(THIS, $Method.UINT8, $call($cval($cval($(DATA).$rshift(14)).$andbit(0x7F)).$sum(0x80))).$exec();
			$(THIS, $Method.UINT8, $call($cval($cval($(DATA).$rshift(21)).$andbit(0x7F)).$sum(0x80))).$exec();
			$(THIS, $Method.UINT8, $call($cval($(DATA).$rshift(28)).$andbit(0x7F))).$exec();
		}, $elseif($(DATA).$lt($(0x4000)), () -> {
			$(THIS, $Method.UINT8, $call($cval($(DATA).$andbit(0x7F)).$sum(0x80))).$exec();
			$(THIS, $Method.UINT8, $call($cval($(DATA).$rshift(7)).$andbit(0x7F))).$exec();
		}), $elseif($(DATA).$lt($(0x200000)), () -> {
			$(THIS, $Method.UINT8, $call($cval($(DATA).$andbit(0x7F)).$sum(0x80))).$exec();
			$(THIS, $Method.UINT8, $call($cval($cval($(DATA).$rshift(7)).$andbit(0x7F)).$sum(0x80))).$exec();
			$(THIS, $Method.UINT8, $call($cval($(DATA).$rshift(14)).$andbit(0x7F))).$exec();
		}), $elseif($(DATA).$lt($(0x10000000)), () -> {
			$(THIS, $Method.UINT8, $call($cval($(DATA).$andbit(0x7F)).$sum(0x80))).$exec();
			$(THIS, $Method.UINT8, $call($cval($cval($(DATA).$rshift(7)).$andbit(0x7F)).$sum(0x80))).$exec();
			$(THIS, $Method.UINT8, $call($cval($cval($(DATA).$rshift(14)).$andbit(0x7F)).$sum(0x80))).$exec();
			$(THIS, $Method.UINT8, $call($cval($(DATA).$rshift(21)).$andbit(0x7F))).$exec();
		}));
		$return($(THIS));
	}

	protected void uint32() {
		$(THIS, $Method.UINT8, $call($cval($(DATA).$rshift(24)).$andbit(0xFF))).$exec();
		$(THIS, $Method.UINT8, $call($cval($(DATA).$rshift(16)).$andbit(0xFF))).$exec();
		$(THIS, $Method.UINT8, $call($cval($(DATA).$rshift(8)).$andbit(0xFF))).$exec();
		$(THIS, $Method.UINT8, $call($(DATA).$andbit(0xFF))).$exec();
		$return($(THIS));
	}

	protected void string() {
		String C = "c", LEN = "len";
		$def(LEN, $(DATA, $JSString.LENGTH));
		$fori(0, $(DATA, $JSString.LENGTH), () -> {
			$def(C, $(DATA, "charCodeAt", $call($(I))));
			$if($(C).$le(0x7f), () -> {
			}, () -> {
				$(LEN).$assignsum(2);
			}, $elseif($(C).$le(0x7FF), () -> {
				$(LEN).$assignsum(1);
			}));
		});
		$(THIS, $Method.UINT32C, $call($(LEN))).$exec();
		$fori(0, $(DATA, $JSString.LENGTH), () -> {
			$def(C, $(DATA, "charCodeAt", $call($(I))));
			$if($(C).$le(0x7f), () -> {
				$(THIS, $Method.UINT8, $call($(C))).$exec();
			}, () -> {
				$(THIS, $Method.UINT8, $call($cval($cval($cval($(C).$rshift(12)).$andbit(0xF)).$sum(0xE0)))).$exec();
				$(THIS, $Method.UINT8, $call($cval($cval($cval($(C).$rshift(6)).$andbit(0x3F)).$sum(0x80)))).$exec();
				$(THIS, $Method.UINT8, $call($cval($cval($(C).$andbit(0x3F)).$sum(0x80)))).$exec();
			}, $elseif($(C).$le(0x7FF), () -> {
				$(THIS, $Method.UINT8, $call($cval($cval($cval($(C).$rshift(6)).$andbit(0x1F)).$sum(0xC0)))).$exec();
				$(THIS, $Method.UINT8, $call($cval($cval($(C).$andbit(0x3F)).$sum(0x80)))).$exec();
			}));
		});
		$return($(THIS));
	}

	public static void main(String[] args) {
		System.out.println(new HwJsOpcodeWriterClass().toString());
	}

}
