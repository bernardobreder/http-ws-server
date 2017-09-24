package httpws.builder.js;

import static httpws.opcode.HWClientOpcodeEnum.ADD_ATTR_INDEX_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.ADD_BODY_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.ADD_CLASS_INDEX_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.ADD_CLASS_NAME_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.APPEND_CHILD_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.BUTTON_ACTION_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.CREATE_ELEMENT_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.REMOVE_ATTR_INDEX_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.REMOVE_CHILD_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.REMOVE_CLASS_INDEX_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.REMOVE_CLASS_NAME_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.SET_LINK_CONTENT_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.SET_TEXT_CONTENT_OPCODE;
import static httpws.opcode.HWClientOpcodeEnum.SET_TEXT_INDEX_OPCODE;
import httpws.builder.js.HwJsOpcodeReaderClass.$OpcodeReader;
import httpws.hws.HwWsAppOpcode;
import httpws.opcode.HWAttributeKeyEnum;
import httpws.opcode.HWAttributeValueEnum;
import httpws.opcode.HWClassEnum;
import httpws.opcode.HWTagEnum;

public class HwVmJavascript extends HwJavascript {

	protected static final String UID = "uid";

	protected static final String STARTED = "started";

	protected static final String COMPONENTS = "components";

	protected static final String TAG_NAMES = "tag_names";

	protected static final String CLASS_NAMES = "class_names";

	protected static final String ATTR_KEY_KEYS = "attr_keys";

	protected static final String ATTR_KEY_VALUES = "attr_values";

	protected static final String BLOB = "blob";

	protected static final String ARRAY = "array";

	protected static final String INDEX = "index";

	protected static final String FUNC = "func";

	protected static final String WS = "ws";

	protected static final String READER = "reader";

	/**
	 * Construtor
	 *
	 * @param url
	 */
	public HwVmJavascript(String url) {
		$def(STARTED, false);
		$def(COMPONENTS, $newmap());
		$def(TAG_NAMES, $newarray($vals($idNames((Object[]) HWTagEnum.values()))));
		$def(CLASS_NAMES, $newarray($vals($idNames((Object[]) HWClassEnum.values()))));
		$def(ATTR_KEY_KEYS, $newarray($vals($idNames((Object[]) HWAttributeKeyEnum.values()))));
		$def(ATTR_KEY_VALUES, $newarray($vals($idNames((Object[]) HWAttributeValueEnum.values()))));
		$(WINDOW, "wsConnect").$assign($func(() -> {
			$(WINDOW, WS).$assign($new($JSClass.WEB_SOCKET, url));
			$(WS, $JSWs.ON_OPEN).$assign($func(() -> {
				onWsOpen();
			}));
			$(WS, $JSWs.ON_CLOSE).$assign($func(() -> {
				onWsClose();
			}));
			$(WS, $JSWs.ON_ERROR).$assign($func(() -> {
			}));
			$(WS, $JSWs.ON_MESSAGE).$assign($func(() -> {
				onWsMessage();
			}, "evt"));
		}));
		$(WINDOW, $JSWs.ON_LOAD).$assign($func(() -> {
			$(WINDOW, "wsConnect", $call()).$exec();
		}));
		$add(new HwJsOpcodeReaderClass());
		$add(new HwJsOpcodeWriterClass());
	}

	protected void onWsMessage() {
		$def("reader", $new(HwJsOpcodeReaderClass.OPCODE_READER, $("evt", "data"), $func(() -> {
			$while($(READER, $OpcodeReader.EMPTY, $call()).$not(), () -> {
				$switch($(READER, $OpcodeReader.UINT8, $call()), null, opCreateElement(), opSetLink(), opInnerTextContent(), opInnerTextIndex(), opButtonAction(), opAppendChild(), opRemoveChild(),
					opAppendBody(), opAddClassName(), opRemoveClassName(), opAddClassIndex(), opRemoveClassIndex(), opAddAttributeIndex(), opRemoveAttributeIndex());
			});
		})));
	}

	protected void onWsClose() {
		$($JSWindow.SET_TIMEOUT, $call($func(() -> {
			$(WINDOW, "wsConnect", $call()).$exec();
		}), 1000)).$exec();
	}

	protected void onWsOpen() {
		String MSG = "msg";
		$if($(STARTED), () -> {

		}, () -> {
			$def(MSG, $new(HwJsOpcodeWriterClass.CLASS));
			$(MSG, HwJsOpcodeWriterClass.$Method.UINT8, $call(HwWsAppOpcode.OPEN.ordinal())).$exec();
			$(MSG, HwJsOpcodeWriterClass.$Method.STRING, $call($(LOCATION, "hash"))).$exec();
			$(WS, $JSWs.SEND, $call($(MSG, HwJsOpcodeWriterClass.$Method.TRUNC, $call()))).$exec();
		});
		$(STARTED).$assign(true);
	}

	protected HwJsCase opCreateElement() {
		String TAGNAME = "tagname", CODE = "code", ELEMENT = "element";
		return $case($(CREATE_ELEMENT_OPCODE.ordinal()), () -> {
			$def(TAGNAME, $(TAG_NAMES, $array($(READER, $OpcodeReader.UINT8, $call()))));
			$def(CODE, $(READER, $OpcodeReader.UINT32, $call()));
			$def(ELEMENT, $(DOCUMENT, $JSDocument.CREATE_ELEMENT, $call($(TAGNAME))));
			$(ELEMENT, UID).$assign($(CODE));
			$(COMPONENTS, $array($(CODE))).$assign($(ELEMENT));
			$break();
		});
	}

	protected HwJsCase opSetLink() {
		String ELEMENT = "element";
		return $case($(SET_LINK_CONTENT_OPCODE.ordinal()), () -> {
			$def(ELEMENT, $(COMPONENTS, $array($(READER, $OpcodeReader.UINT32, $call()))));
			$(ELEMENT, "href").$assign($(READER, $OpcodeReader.STRING, $call()));
			$break();
		});
	}

	protected HwJsCase opInnerTextContent() {
		String ELEMENT = "element";
		return $case($(SET_TEXT_CONTENT_OPCODE.ordinal()), () -> {
			$def(ELEMENT, $(COMPONENTS, $array($(READER, $OpcodeReader.UINT32, $call()))));
			$(ELEMENT, INNER_TEXT).$assign($(READER, $OpcodeReader.STRING, $call()));
			$break();
		});
	}

	protected HwJsCase opInnerTextIndex() {
		String ELEMENT = "element";
		return $case($(SET_TEXT_INDEX_OPCODE.ordinal()), () -> {
			$def(ELEMENT, $(COMPONENTS, $array($(READER, $OpcodeReader.UINT32, $call()))));
			$(ELEMENT, INNER_TEXT).$assign($("lngs", $array($(READER, $OpcodeReader.STRING, $call()))));
			$break();
		});
	}

	protected HwJsCase opButtonAction() {
		String ELEMENT = "element", ID = "id", MSG = "msg";
		return $case($(BUTTON_ACTION_OPCODE.ordinal()), () -> {
			$def(ELEMENT, $(COMPONENTS, $array($(READER, $OpcodeReader.UINT32, $call()))));
			$(ELEMENT, $JSElement.ON_CLICK).$assign($func(() -> {
				$def(ID, $(THIS, UID));
				$def(MSG, $new($JSClass.UINT8_ARRAY, $new($JSClass.ARRAY_BUFFER, 5)));
				$(MSG, $array(0)).$assign(HwWsAppOpcode.CALL.ordinal());
				$(MSG, $array(1)).$assign($cval($(ID).$rshift(24)).$andbit(0xFF));
				$(MSG, $array(2)).$assign($cval($(ID).$rshift(16)).$andbit(0xFF));
				$(MSG, $array(3)).$assign($cval($(ID).$rshift(8)).$andbit(0xFF));
				$(MSG, $array(4)).$assign($(ID).$andbit(0xFF));
				$(WS, $JSWs.SEND, $call($(MSG))).$exec();
			}));
			$break();
		});
	}

	protected HwJsCase opAppendChild() {
		String PARENT = "parent", CHILD = "child";
		return $case($(APPEND_CHILD_OPCODE.ordinal()), () -> {
			$def(PARENT, $(COMPONENTS, $array($(READER, $OpcodeReader.UINT32, $call()))));
			$def(CHILD, $(COMPONENTS, $array($(READER, $OpcodeReader.UINT32, $call()))));
			$(PARENT, $JSElement.APPEND_CHILD, $call($(CHILD))).$exec();
			$break();
		});
	}

	protected HwJsCase opRemoveChild() {
		String PARENT = "parent", CHILD = "child", ARRAY = "array", COMPONENT = "component";
		String ITEM = "item";
		return $case($(REMOVE_CHILD_OPCODE.ordinal()), () -> {
			$def(PARENT, $(COMPONENTS, $array($(READER, $OpcodeReader.UINT32, $call()))));
			$def(CHILD, $(COMPONENTS, $array($(READER, $OpcodeReader.UINT32, $call()))));
			$def(ARRAY, $newarray($(CHILD)));
			$(PARENT, $JSElement.REMOVE_CHILD, $call($(CHILD))).$exec();
			$delete($(COMPONENTS, $array($(CHILD, UID))));
			$while($(ARRAY, LENGTH).$neq($(0)), () -> {
				$def(COMPONENT, $(ARRAY, $JSArray.SHIFT, $call()));
				$while($(COMPONENT, $JSElement.CHILDREN, LENGTH).$gt($(0)), () -> {
					$def(ITEM, $(COMPONENT, $JSElement.CHILDREN, $array($(0))));
					$(ARRAY, $JSArray.PUSH, $call($(ITEM))).$exec();
					$(COMPONENT, $JSElement.REMOVE_CHILD, $call($(ITEM))).$exec();
					$delete($(COMPONENTS, $array($(ITEM, UID))));
				});
			});
			$break();
		});
	}

	protected HwJsCase opAppendBody() {
		String ELEMENT = "element", I = "i";
		return $case($(ADD_BODY_OPCODE.ordinal()), () -> {
			$def(ELEMENT, $(COMPONENTS, $array($(READER, $OpcodeReader.UINT32, $call()))));
			$fori($(0), $(ELEMENT, $JSElement.CHILDREN, LENGTH), () -> {
				$(DOCUMENT, BODY, $JSElement.APPEND_CHILD, $call($(ELEMENT, $JSElement.CHILDREN, $array($(I))))).$exec();
			});
			$break();
		});
	}

	protected HwJsCase opAddClassName() {
		String ELEMENT = "element";
		return $case($(ADD_CLASS_NAME_OPCODE.ordinal()), () -> {
			$def(ELEMENT, $(COMPONENTS, $array($(READER, $OpcodeReader.UINT32, $call()))));
			$(ELEMENT, $JSElement.CLASS_LIST, ADD, $call($(READER, $OpcodeReader.STRING, $call()))).$exec();
			$break();
		});
	}

	protected HwJsCase opRemoveClassName() {
		String ELEMENT = "element";
		return $case($(REMOVE_CLASS_NAME_OPCODE.ordinal()), () -> {
			$def(ELEMENT, $(COMPONENTS, $array($(READER, $OpcodeReader.UINT32, $call()))));
			$(ELEMENT, $JSElement.CLASS_LIST, REMOVE, $call($(READER, $OpcodeReader.STRING, $call()))).$exec();
			$if($(ELEMENT, $JSElement.CLASS_LIST, LENGTH).$eq($val(0)), () -> {
				$(ELEMENT, $JSElement.REMOVE_ATTRIBUTE, $call(CLASS)).$exec();
			});
			$break();
		});
	}

	protected HwJsCase opAddClassIndex() {
		String ELEMENT = "element";
		return $case($(ADD_CLASS_INDEX_OPCODE.ordinal()), () -> {
			$def(ELEMENT, $(COMPONENTS, $array($(READER, $OpcodeReader.UINT32, $call()))));
			$(ELEMENT, $JSElement.CLASS_LIST, ADD, $call($(CLASS_NAMES, $array($(READER, $OpcodeReader.UINT32, $call()))))).$exec();
			$break();
		});
	}

	protected HwJsCase opRemoveClassIndex() {
		String ELEMENT = "element";
		return $case($(REMOVE_CLASS_INDEX_OPCODE.ordinal()), () -> {
			$def(ELEMENT, $(COMPONENTS, $array($(READER, $OpcodeReader.UINT32, $call()))));
			$(ELEMENT, $JSElement.CLASS_LIST, REMOVE, $call($(CLASS_NAMES, $array($(READER, $OpcodeReader.UINT32, $call()))))).$exec();
			$if($(ELEMENT, $JSElement.CLASS_LIST, LENGTH).$eq($val(0)), () -> {
				$(ELEMENT, $JSElement.REMOVE_ATTRIBUTE).$call(CLASS).$exec();
			});
			$break();
		});
	}

	protected HwJsCase opAddAttributeIndex() {
		String ELEMENT = "element";
		String ATTRIBUTE = "attribute";
		return $case($(ADD_ATTR_INDEX_OPCODE.ordinal()), () -> {
			$def(ELEMENT, $(COMPONENTS, $array($(READER, $OpcodeReader.UINT32, $call()))));
			$def(ATTRIBUTE, $(DOCUMENT, $JSElement.CREATE_ATTRIBUTE, $call($(ATTR_KEY_KEYS, $array($(READER, $OpcodeReader.UINT8, $call()))))));
			$(ATTRIBUTE, VALUE).$assign($(ATTR_KEY_VALUES, $array($(READER, $OpcodeReader.UINT8, $call()))));
			$(ELEMENT, $JSElement.SET_ATTRIBUTE, $call($(ATTRIBUTE))).$exec();
			$break();
		});
	}

	protected HwJsCase opRemoveAttributeIndex() {
		String ELEMENT = "element";
		return $case($(REMOVE_ATTR_INDEX_OPCODE.ordinal()), () -> {
			$def(ELEMENT, $(COMPONENTS, $array($(READER, $OpcodeReader.UINT32, $call()))));
			$(ELEMENT, $JSElement.REMOVE_ATTRIBUTE, $call($(ATTR_KEY_KEYS, $array($(READER, $OpcodeReader.UINT8, $call()))))).$exec();
			$break();
		});
	}

	public static void main(String[] args) {
		System.out.println(new HwVmJavascript("ws://localhost:8080").toString());
	}

}
