package httpws.builder.js;

public class HwJsOpcodeReaderClass extends HwJavascript {
	
	protected static final String FILE_READER = "file_reader";
	
	protected static final String BLOB = "blob";
	
	protected static final String ARRAY = "array";
	
	protected static final String INDEX = "index";
	
	protected static final String FUNC = "func";
	
	public static class $OpcodeReader {
		
		protected static final String UINT8 = "uint8";
		
		protected static final String UINT32 = "uint32";
		
		protected static final String STRING = "string";
		
		protected static final String EMPTY = "empty";
	}

	public static final String OPCODE_READER = "OpcodeReader";
	
	public HwJsOpcodeReaderClass() {
		$class(HwJsOpcodeReaderClass.OPCODE_READER, () -> {
			$def("self", $(THIS));
			$def(FILE_READER, $new($JSClass.FILE_READER));
			$(FILE_READER, "onload").$assign($func(() -> {
				$("self", ARRAY).$assign($new($JSClass.UINT8_ARRAY, $("evt", "target", "result")));
				$("self", INDEX).$assign($val(0));
				$var(FUNC).$call($this()).$exec();
			} , "evt"));
			$(FILE_READER, "readAsArrayBuffer", $call($(BLOB))).$exec();
		} , "blob", "func");
		
		$method(HwJsOpcodeReaderClass.OPCODE_READER, $OpcodeReader.UINT8, () -> {
			$return($(THIS, ARRAY, $array($(THIS, INDEX).$inc())));
		});
		
		$method(HwJsOpcodeReaderClass.OPCODE_READER, $OpcodeReader.UINT32, () -> {
			$def("c1", $(THIS, ARRAY, $array($(THIS, INDEX).$inc())));
			$if($cval($var("c1").$andbit(0x80)).$eq(0), () -> {
				$return($var("c1"));
			});
			$("c1").$assignsub(0x80);
			$def("c2", $(THIS, ARRAY, $array($(THIS, INDEX).$inc())));
			$if($cval($("c2").$andbit($val(0x80))).$eq($val(0)), () -> {
				$return($("c1").$sum($cval($("c2").$lshift(7))));
			});
			$("c2").$assignsub(0x80);
			$def("c3", $(THIS, ARRAY, $array($(THIS, INDEX).$inc())));
			$if($cval($("c3").$andbit($val(0x80))).$eq(0), () -> {
				$return($("c1").$sum($cval($("c2").$lshift(7))).$sum($cval($("c3").$lshift(14))));
			});
			$("c3").$assignsub(0x80);
			$def("c4", $(THIS, ARRAY, $array($(THIS, INDEX).$inc())));
			$if($cval($("c4").$andbit($val(0x80))).$eq(0), () -> {
				$return($("c1").$sum($cval($("c2").$lshift(7))).$sum($cval($("c3").$lshift(14))).$sum($cval($("c4").$lshift(21))));
			});
			$("c4").$assignsub(0x80);
			$def("c5", $(THIS, ARRAY, $array($(THIS, INDEX).$inc())));
			$return($("c1").$sum($cval($("c2").$lshift(7))).$sum($cval($("c3").$lshift(14))).$sum($cval($("c4").$lshift(21))).$sum($cval($("c5").$lshift(28))));
		});
		
		$method(HwJsOpcodeReaderClass.OPCODE_READER, $OpcodeReader.STRING, () -> {
			$def("len", $(THIS, $OpcodeReader.UINT32, $call()));
			$def("subarray", $(THIS, "array", "subarray", $call($(THIS, "index"), $(THIS, "index").$sum($var("len")))));
			$(THIS, "index").$assignsum($("len"));
			$def("text", $("String", "fromCharCode", "apply", $call(null, $("subarray"))));
			$return($("decodeURIComponent", $call($("escape", $call($("text"))))));
		});
		
		$method(HwJsOpcodeReaderClass.OPCODE_READER, $OpcodeReader.EMPTY, () -> {
			$return($(THIS, "index").$ge($(THIS, "array", "length")));
		});
	}
	
}
