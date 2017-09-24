package httpws.builder.js;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import httpws.builder.HwStack;
import httpws.util.Charset;

public class HwJavascript {
	
	private final String content;
	
	private final HwStack<HwJsBlock> blocks;
	
	private HwJsBlock block;
	
	private final Reference<HwJavascript> ref = new WeakReference<HwJavascript>(this);
	
	private final HwJsNull nullValue = new HwJsNull(ref);
	
	private final HwJsBoolean trueValue = new HwJsBoolean(ref, true);
	
	private final HwJsBoolean falseValue = new HwJsBoolean(ref, false);
	
	protected static class $JSClass {
		
		public static final String FILE_READER = "FileReader";
		
		public static final String UINT8_ARRAY = "Uint8Array";
		
		public static final String WEB_SOCKET = "WebSocket";
		
		public static final String ARRAY_BUFFER = "ArrayBuffer";
		
	}
	
	protected static class $JSWindow {
		
		public static final String SET_TIMEOUT = "setTimeout";
		
	}
	
	protected static class $JSString {
		
		public static final String LENGTH = "length";
		
	}
	
	protected static class $JSArrayBuffer {
		
		public static final String SLICE = "slice";
		
	}
	
	protected static class $JSWs {
		
		public static final String ON_OPEN = "onopen";
		
		public static final String ON_CLOSE = "onclose";
		
		public static final String ON_LOAD = "onload";
		
		public static final String ON_ERROR = "onerror";
		
		public static final String ON_MESSAGE = "onmessage";
		
		public static final String SEND = "send";
	}
	
	protected static class $JSElement {
		
		public static final String ON_CLICK = "onclick";
		
		public static final String APPEND_CHILD = "appendChild";
		
		public static final String REMOVE_CHILD = "removeChild";
		
		public static final String CHILDREN = "children";
		
		public static final String CREATE_ATTRIBUTE = "createAttribute";
		
		public static final String REMOVE_ATTRIBUTE = "removeAttribute";
		
		public static final String SET_ATTRIBUTE = "setAttributeNode";
		
		public static final String CLASS_LIST = "classList";
		
	}
	
	protected static class $JSArray {
		
		public static final String PUSH = "push";
		
		public static final String POP = "pop";
		
		public static final String SHIFT = "shift";
		
	}
	
	protected static class $JSDocument {
		
		public static final String CREATE_ELEMENT = "createElement";
		
	}
	
	protected static final String INNER_TEXT = "innerText";
	
	protected static final String I = "i";
	
	protected static final String DOCUMENT = "document";
	
	protected static final String ADD = "add";
	
	protected static final String REMOVE = "remove";
	
	protected static final String VALUE = "value";
	
	protected static final String CLASS = "class";
	
	protected static final String WINDOW = "window";
	
	protected static final String LENGTH = "length";
	
	protected static final String THIS = "this";
	
	protected static final String BODY = "body";
	
	protected static final String ONHASHCHANGE = "onhashchange";
	
	protected static final String ONRESIZE = "onresize";
	
	protected static final String HEIGHT = "height";
	
	protected static final String WIDTH = "width";
	
	protected static final String ONLOAD = "onload";
	
	protected static final String INNER_HEIGHT = "innerHeight";
	
	protected static final String INNER_WIDTH = "innerWidth";
	
	protected static final String HASH = "hash";
	
	protected static final String LOCATION = "location";
	
	protected static final String GET_CONTEXT = "getContext";
	
	protected static final String CANVAS = "canvas";
	
	public HwJavascript() {
		content = null;
		blocks = new HwStack<HwJsBlock>();
		block = new HwJsBlock(ref);
	}
	
	public HwJavascript(InputStream in, int length) throws IOException {
		if (length <= 0) {
			length = 1024;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream(length);
		byte[] bytes = new byte[1024];
		for (int n; (n = in.read(bytes)) != -1;) {
			out.write(bytes, 0, n);
		}
		content = Charset.utf8(out.toByteArray());
		blocks = null;
		block = null;
	}
	
	public HwJavascript(Runnable r) {
		this();
		r.run();
	}
	
	public HwJsValue $window() {
		return new HwJsWindow(ref);
	}
	
	public HwJsValue $document() {
		return new HwJsDocument(ref);
	}
	
	public HwJsValue $call(Object... arguments) {
		return new HwJsCall(ref, null, $vals(arguments));
	}
	
	public HwJsValue $array(Object item) {
		return new HwJsGetArray(ref, null, $val(item));
	}
	
	public HwJsValue $get(HwJsValue lvalue, String name) {
		if (lvalue == null) { throw new IllegalArgumentException("lvalue is null"); }
		if (name == null) { throw new IllegalArgumentException("name is null"); }
		return new HwJsGet(ref, lvalue, new HwJsIdentifier(ref, name));
	}
	
	public HwJsValue $get(HwJsValue lvalue, Enum<?> name) {
		return $get(lvalue, $idVar(name));
	}
	
	public HwJsValue $var(String name) {
		if (name == null) { throw new IllegalArgumentException("name is null"); }
		return new HwJsIdentifier(ref, name.toString());
	}
	
	public HwJsValue $(Object... names) {
		if (names.length == 0) { return $this(); }
		HwJsValue value = $var($idVar(names[0]));
		for (int n = 1; n < names.length; n++) {
			Object name = names[n];
			if (name instanceof HwJsCall) {
				HwJsCall $call = (HwJsCall) name;
				$call.left = value;
				value = $call;
			} else if (name instanceof HwJsGetArray) {
				HwJsGetArray $getarray = (HwJsGetArray) name;
				$getarray.left = value;
				value = $getarray;
			} else {
				value = value.$get($idVar(name));
			}
		}
		return value;
	}
	
	public HwJsValue $not(HwJsValue value) {
		return new HwJsNot(ref, value);
	}
	
	public HwJsValue $var(Enum<?> name) {
		if (name == null) { throw new IllegalArgumentException("name is null"); }
		return new HwJsIdentifier(ref, $idVar(name));
	}
	
	public HwJsValue $this() {
		return new HwJsThis(ref);
	}
	
	public HwJsValue $new(Enum<?> classname, Object... arguments) {
		return $new($idClass(classname), arguments);
	}
	
	public HwJsValue $new(String classname, Object... arguments) {
		return new HwJsNew(ref, classname, $vals(arguments));
	}
	
	public HwJsValue $getarray(HwJsValue left, HwJsValue right) {
		return new HwJsGetArray(ref, left, right);
	}
	
	public HwJsValue $newarray(HwJsValue... values) {
		return new HwJsNewArray(ref, values == null || values.length == 0 ? null : values);
	}
	
	public HwJsValue $and(HwJsValue left, HwJsValue right) {
		return new HwJsAnd(ref, left, right);
	}
	
	public HwJsValue $or(HwJsValue left, HwJsValue right) {
		return new HwJsOr(ref, left, right);
	}
	
	public HwJsValue $andbit(HwJsValue left, HwJsValue right) {
		return new HwJsAndBit(ref, left, right);
	}
	
	public HwJsValue $orbit(HwJsValue left, HwJsValue right) {
		return new HwJsOrBit(ref, left, right);
	}
	
	public HwJsValue $newmap() {
		return new HwJsNewMap(ref, null);
	}
	
	public HwJsValue $id(String name) {
		if (name == null) { throw new IllegalArgumentException("name is null"); }
		return new HwJsIdentifier(ref, name);
	}
	
	public HwJsValue $preinc(HwJsValue lvalue) {
		return new HwJsPreInc(ref, lvalue);
	}
	
	public HwJsValue $predec(HwJsValue lvalue) {
		return new HwJsPreDec(ref, lvalue);
	}
	
	public HwJsValue $posinc(HwJsValue lvalue) {
		return new HwJsPosInc(ref, lvalue);
	}
	
	public HwJsValue $posdec(HwJsValue lvalue) {
		return new HwJsPosDec(ref, lvalue);
	}
	
	public HwJsValue $eq(HwJsValue left, HwJsValue right) {
		return new HwJsEqual(ref, left, right);
	}
	
	public HwJsValue $neq(HwJsValue left, HwJsValue right) {
		return new HwJsNotEqual(ref, left, right);
	}
	
	public HwJsValue $lt(HwJsValue left, HwJsValue right) {
		return new HwJsLowerThan(ref, left, right);
	}
	
	public HwJsValue $le(HwJsValue left, HwJsValue right) {
		return new HwJsLowerEqual(ref, left, right);
	}
	
	public HwJsValue $gt(HwJsValue left, HwJsValue right) {
		return new HwJsGreaterThan(ref, left, right);
	}
	
	public HwJsValue $ge(HwJsValue left, HwJsValue right) {
		return new HwJsGreaterEqual(ref, left, right);
	}
	
	public HwJsValue $lshift(HwJsValue left, HwJsValue right) {
		return new HwJsLeftShift(ref, left, right);
	}
	
	public HwJsValue $rshift(HwJsValue left, HwJsValue right) {
		return new HwJsRightShift(ref, left, right);
	}
	
	public HwJsValue $sum(HwJsValue left, HwJsValue right) {
		return new HwJsSum(ref, left, right);
	}
	
	public HwJsValue $sub(HwJsValue left, HwJsValue right) {
		return new HwJsSub(ref, left, right);
	}
	
	public HwJsValue $mul(HwJsValue left, HwJsValue right) {
		return new HwJsMul(ref, left, right);
	}
	
	public HwJsValue $div(HwJsValue left, HwJsValue right) {
		return new HwJsDiv(ref, left, right);
	}
	
	public HwJsValue $mod(HwJsValue left, HwJsValue right) {
		return new HwJsMod(ref, left, right);
	}
	
	public HwJsValue $func(Runnable runnable, String... parameters) {
		return new HwJsFunction(ref, runnable, parameters);
	}
	
	public HwJavascript $class(Enum<?> classname, Runnable body, String... parameters) {
		$class($idClass(classname), body, parameters);
		return this;
	}
	
	public HwJavascript $class(String classname, Runnable body, String... parameters) {
		HwJsClass node = new HwJsClass(ref, classname, parameters, body);
		blocks.peek().children().add(node);
		return this;
	}
	
	public HwJavascript $method(String classname, String name, Runnable block, String... parameters) {
		HwJsMethod node = new HwJsMethod(ref, classname, name, parameters, block);
		blocks.peek().children().add(node);
		return this;
	}
	
	public HwJavascript $method(Enum<?> classname, Enum<?> name, Runnable block, String... parameters) {
		return $method($idClass(classname), $idVar(name), block, parameters);
	}
	
	public HwJavascript $for(HwJsDefVar def, HwJsValue cond, HwJsValue inc, Runnable block) {
		HwJsFor node = new HwJsFor(ref, def, cond, inc, block);
		blocks.peek().children().add(node);
		return this;
	}
	
	public HwJavascript $fori(Object initValue, Object lowerThanValue, Runnable block) {
		$for($defv("i", $val(initValue)), $("i").$lt($val(lowerThanValue)), $("i").$inc(), block);
		return this;
	}
	
	public HwJavascript $repeat(Object condition, Runnable block) {
		HwJsRepeat node = new HwJsRepeat(ref, $val(condition), block);
		blocks.peek().children().add(node);
		return this;
	}
	
	public HwJavascript $while(Object condition, Runnable block) {
		HwJsWhile node = new HwJsWhile(ref, $val(condition), block);
		blocks.peek().children().add(node);
		return this;
	}
	
	public HwJavascript $return(Object condition) {
		HwJsReturn node = new HwJsReturn(ref, $val(condition));
		blocks.peek().children().add(node);
		return this;
	}
	
	public HwJavascript $break() {
		HwJsBreak node = new HwJsBreak(ref);
		blocks.peek().children().add(node);
		return this;
	}
	
	public HwJavascript $continue() {
		HwJsContinue node = new HwJsContinue(ref);
		blocks.peek().children().add(node);
		return this;
	}
	
	public HwJavascript $switch(Object condition, Runnable defaultBlock, HwJsCase... cases) {
		HwJsSwitch node = new HwJsSwitch(ref, $val(condition), defaultBlock, cases);
		blocks.peek().children().add(node);
		return this;
	}
	
	public HwJsCase $case(HwJsValue condition, Runnable block) {
		return new HwJsCase(ref, condition, block);
	}
	
	public HwJavascript $def(Enum<?> name, HwJsValue value) {
		return $def($idVar(name), value);
	}
	
	public HwJavascript $def(String name, Object value) {
		HwJsDefVar node = new HwJsDefVar(ref, new HwJsIdentifier(ref, $idVar(name)), $val(value));
		blocks.peek().children().add(node);
		return this;
	}
	
	public HwJsDefVar $defv(String name, Object value) {
		return new HwJsDefVar(ref, new HwJsIdentifier(ref, $idVar(name)), $val(value));
	}
	
	public HwJavascript $exec(HwJsValue value) {
		HWJSExpression node = new HWJSExpression(ref, value);
		blocks.peek().children().add(node);
		return this;
	}
	
	public HwJavascript $delete(HwJsValue value) {
		HWJSDelete node = new HWJSDelete(ref, value);
		blocks.peek().children().add(node);
		return this;
	}
	
	public HwJavascript $add(HwJavascript node) {
		blocks.peek().children().addAll(node.blocks.peek().children());
		return this;
	}
	
	private HwJavascript $assign(HwJsValue variable, HwJsValue value) {
		HwJsAssign node = new HwJsAssign(ref, variable, $val(value), "=");
		blocks.peek().children().add(node);
		return this;
	}
	
	private HwJavascript $assignsum(HwJsValue variable, HwJsValue value) {
		HwJsAssign node = new HwJsAssign(ref, variable, $val(value), "+=");
		blocks.peek().children().add(node);
		return this;
	}
	
	private HwJavascript $assignsub(HwJsValue variable, HwJsValue value) {
		HwJsAssign node = new HwJsAssign(ref, variable, $val(value), "-=");
		blocks.peek().children().add(node);
		return this;
	}
	
	private HwJavascript $assignmul(HwJsValue variable, HwJsValue value) {
		HwJsAssign node = new HwJsAssign(ref, variable, $val(value), "*=");
		blocks.peek().children().add(node);
		return this;
	}
	
	private HwJavascript $assigndiv(HwJsValue variable, HwJsValue value) {
		HwJsAssign node = new HwJsAssign(ref, variable, $val(value), "/=");
		blocks.peek().children().add(node);
		return this;
	}
	
	private HwJavascript $assignmod(HwJsValue variable, HwJsValue value) {
		HwJsAssign node = new HwJsAssign(ref, variable, $val(value), "%=");
		blocks.peek().children().add(node);
		return this;
	}
	
	public HwJavascript $if(HwJsValue condition, Runnable trueBlock) {
		HwJsIf node = new HwJsIf(ref, condition, trueBlock, null, null);
		blocks.peek().children().add(node);
		return this;
	}
	
	public HwJavascript $if(HwJsValue condition, Runnable trueBlock, Runnable elseBlock, HwJsElseIf... elseifNodes) {
		HwJsIf node = new HwJsIf(ref, condition, trueBlock, elseBlock, elseifNodes);
		blocks.peek().children().add(node);
		return this;
	}
	
	public HwJsElseIf $elseif(HwJsValue condition, Runnable block) {
		return new HwJsElseIf(ref, $val(condition), block);
	}
	
	public HwJavascript $eof() {
		blocks.peek().$close();
		return this;
	}
	
	public HwJavascript $reset() {
		blocks.clear();
		blocks.push(block = new HwJsBlock(ref));
		return this;
	}
	
	protected static String $idName(Object item) {
		if (item instanceof Enum<?>) { return item.toString().toLowerCase().replace('_', '-'); }
		return item.toString();
	}
	
	protected static String $idVar(Object item) {
		if (item instanceof Enum<?>) { return item.toString().toLowerCase(); }
		return item.toString();
	}
	
	protected static String[] $idNames(Object... item) {
		String[] result = new String[item.length];
		for (int n = 0; n < item.length; n++) {
			result[n] = $idName(item[n]);
		}
		return result;
	}
	
	protected static String $idClass(Object item) {
		String name = item.toString();
		StringBuilder str = new StringBuilder(name.length());
		int beginIndex = 0, endIndex = name.indexOf('_', beginIndex);
		while (endIndex >= 0) {
			str.append(Character.toUpperCase(name.charAt(beginIndex)));
			str.append(name.substring(beginIndex + 1, endIndex).toLowerCase());
			beginIndex = endIndex + 1;
			endIndex = name.indexOf('_', endIndex + 1);
		}
		str.append(Character.toUpperCase(name.charAt(beginIndex)));
		str.append(name.substring(beginIndex + 1, name.length()).toLowerCase());
		return str.toString();
	}
	
	public HwJsValue $cval(HwJsValue value) {
		return new HwJsCloseValue(ref, value);
	}
	
	public HwJsValue[] $vals(Object[] values) {
		HwJsValue[] array = new HwJsValue[values.length];
		for (int n = 0; n < values.length; n++) {
			array[n] = $val(values[n]);
		}
		return array;
	}
	
	public HwJsValue $val(Object value) {
		if (value == null) {
			return nullValue;
		} else if (value instanceof HwJsValue) {
			return (HwJsValue) value;
		} else if (value instanceof Integer) {
			return new HwJsInteger(ref, Long.valueOf(((Integer) value).longValue()));
		} else if (value instanceof Long) {
			return new HwJsInteger(ref, (Long) value);
		} else if (value instanceof Float) {
			return new HwJsFloat(ref, Double.valueOf(((Float) value).doubleValue()));
		} else if (value instanceof Double) {
			return new HwJsFloat(ref, (Double) value);
		} else if (value instanceof String) {
			return new HwJsString(ref, (String) value);
		} else if (value instanceof Map) {
			Map<Object, Object> map = (Map<Object, Object>) value;
			return new HwJsNewMap(ref, map);
		} else if (value instanceof Runnable) {
			return new HwJsFunction(ref, (Runnable) value, null);
		} else if (value instanceof Boolean) {
			if (((Boolean) value).booleanValue()) {
				return trueValue;
			} else {
				return falseValue;
			}
		}
		throw new IllegalArgumentException("type unknown");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (content != null) { return content; }
		return block.toString(0);
	}
	
	protected static class HwJsNull extends HwJsValue {
		
		public HwJsNull(Reference<HwJavascript> js) {
			super(js);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			return "null";
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return 4;
		}
		
	}
	
	protected static class HwJsBoolean extends HwJsValue {
		
		private Boolean flag;
		
		public HwJsBoolean(Reference<HwJavascript> js, boolean flag) {
			super(js);
			this.flag = Boolean.valueOf(flag);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			return flag.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return flag.toString().length();
		}
		
	}
	
	protected static class HwJsInteger extends HwJsValue {
		
		private Long value;
		
		public HwJsInteger(Reference<HwJavascript> js, Long value) {
			super(js);
			this.value = value;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			return value.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return value.toString().length();
		}
		
	}
	
	protected static class HwJsFloat extends HwJsValue {
		
		private Double value;
		
		public HwJsFloat(Reference<HwJavascript> js, Double value) {
			super(js);
			this.value = value;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			return value.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return value.toString().length();
		}
		
	}
	
	protected static class HwJsString extends HwJsValue {
		
		private String value;
		
		/**
		 * @param js
		 * @param value
		 */
		public HwJsString(Reference<HwJavascript> js, String value) {
			super(js);
			this.value = value;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(value.length() + 2);
			sb.append('\"');
			sb.append(value);
			sb.append('\"');
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return value.length() + 2;
		}
		
	}
	
	protected static class HwJsFunction extends HwJsValue {
		
		private HwJsBlock value;
		
		private String[] parameters;
		
		/**
		 * @param js
		 * @param method
		 * @param parameters
		 */
		public HwJsFunction(Reference<HwJavascript> js, Runnable method, String[] parameters) {
			super(js);
			this.value = new HwJsBlock(js, method);
			this.parameters = parameters == null || parameters.length == 0 ? null : parameters;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			sb.append("function (");
			if (parameters != null) {
				for (int n = 0; n < parameters.length; n++) {
					sb.append(parameters[n]);
					if (n != parameters.length - 1) {
						sb.append(", ");
					}
				}
			}
			sb.append(") {\n");
			sb.append(value.toString(level + 1));
			sb.append('\n');
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append('}');
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			int size = 14 + value.toLength(level + 1) + level + 2;
			if (parameters != null) {
				for (int n = 0; n < parameters.length; n++) {
					size += parameters[n].length();
					if (n != parameters.length - 1) {
						size += 2;
					}
				}
			}
			return size;
		}
		
	}
	
	protected static class HwJsIdentifier extends HwJsValue {
		
		private String name;
		
		public HwJsIdentifier(Reference<HwJavascript> js, String name) {
			super(js);
			this.name = name;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			return name;
		}
		
		@Override
		protected int toLength(int level) {
			return name.length();
		}
		
	}
	
	protected static class HwJsThis extends HwJsValue {
		
		public HwJsThis(Reference<HwJavascript> js) {
			super(js);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			return "this";
		}
		
		@Override
		protected int toLength(int level) {
			return 4;
		}
		
	}
	
	protected static class HwJsPreInc extends HwJsValue {
		
		private HwJsValue left;
		
		public HwJsPreInc(Reference<HwJavascript> js, HwJsValue left) {
			super(js);
			this.left = left;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			sb.append('+');
			sb.append('+');
			sb.append(left.toString(level));
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return left.toLength(0) + 2;
		}
		
	}
	
	protected static class HwJsPreDec extends HwJsValue {
		
		private HwJsValue left;
		
		public HwJsPreDec(Reference<HwJavascript> js, HwJsValue left) {
			super(js);
			this.left = left;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			sb.append('-');
			sb.append('-');
			sb.append(left.toString(level));
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return left.toLength(0) + 2;
		}
		
	}
	
	protected static class HwJsPosInc extends HwJsValue {
		
		private HwJsValue left;
		
		public HwJsPosInc(Reference<HwJavascript> js, HwJsValue left) {
			super(js);
			this.left = left;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			sb.append(left.toString(level));
			sb.append('+');
			sb.append('+');
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return left.toLength(0) + 2;
		}
		
	}
	
	protected static class HwJsPosDec extends HwJsValue {
		
		private HwJsValue left;
		
		public HwJsPosDec(Reference<HwJavascript> js, HwJsValue left) {
			super(js);
			this.left = left;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			sb.append(left.toString(level));
			sb.append('-');
			sb.append('-');
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return left.toLength(0) + 2;
		}
		
	}
	
	protected static class HwJsCloseValue extends HwJsValue {
		
		private HwJsValue left;
		
		public HwJsCloseValue(Reference<HwJavascript> js, HwJsValue left) {
			super(js);
			this.left = left;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			sb.append('(');
			sb.append(left.toString(level));
			sb.append(')');
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return left.toLength(0) + 2;
		}
		
	}
	
	protected static class HwJsEqual extends HwJsBinaryValue {
		
		private HwJsValue left;
		
		private HwJsValue right;
		
		public HwJsEqual(Reference<HwJavascript> js, HwJsValue left, HwJsValue right) {
			super(js, left, right, "==");
		}
		
	}
	
	protected static class HwJsAnd extends HwJsBinaryValue {
		
		private HwJsValue left;
		
		private HwJsValue right;
		
		public HwJsAnd(Reference<HwJavascript> js, HwJsValue left, HwJsValue right) {
			super(js, left, right, "&&");
		}
		
	}
	
	protected static class HwJsOr extends HwJsBinaryValue {
		
		private HwJsValue left;
		
		private HwJsValue right;
		
		public HwJsOr(Reference<HwJavascript> js, HwJsValue left, HwJsValue right) {
			super(js, left, right, "||");
		}
		
	}
	
	protected static class HwJsAndBit extends HwJsBinaryValue {
		
		private HwJsValue left;
		
		private HwJsValue right;
		
		public HwJsAndBit(Reference<HwJavascript> js, HwJsValue left, HwJsValue right) {
			super(js, left, right, "&");
		}
		
	}
	
	protected static class HwJsOrBit extends HwJsBinaryValue {
		
		private HwJsValue left;
		
		private HwJsValue right;
		
		public HwJsOrBit(Reference<HwJavascript> js, HwJsValue left, HwJsValue right) {
			super(js, left, right, "|");
		}
		
	}
	
	protected static class HwJsNotEqual extends HwJsBinaryValue {
		
		private HwJsValue left;
		
		private HwJsValue right;
		
		public HwJsNotEqual(Reference<HwJavascript> js, HwJsValue left, HwJsValue right) {
			super(js, left, right, "!=");
		}
		
	}
	
	protected static class HwJsLowerThan extends HwJsBinaryValue {
		
		private HwJsValue left;
		
		private HwJsValue right;
		
		public HwJsLowerThan(Reference<HwJavascript> js, HwJsValue left, HwJsValue right) {
			super(js, left, right, "<");
		}
		
	}
	
	protected static class HwJsLowerEqual extends HwJsBinaryValue {
		
		private HwJsValue left;
		
		private HwJsValue right;
		
		public HwJsLowerEqual(Reference<HwJavascript> js, HwJsValue left, HwJsValue right) {
			super(js, left, right, "<=");
		}
		
	}
	
	protected static class HwJsGreaterThan extends HwJsBinaryValue {
		
		private HwJsValue left;
		
		private HwJsValue right;
		
		public HwJsGreaterThan(Reference<HwJavascript> js, HwJsValue left, HwJsValue right) {
			super(js, left, right, ">");
		}
		
	}
	
	protected static class HwJsGreaterEqual extends HwJsBinaryValue {
		
		private HwJsValue left;
		
		private HwJsValue right;
		
		public HwJsGreaterEqual(Reference<HwJavascript> js, HwJsValue left, HwJsValue right) {
			super(js, left, right, ">=");
		}
		
	}
	
	protected static class HwJsLeftShift extends HwJsBinaryValue {
		
		public HwJsLeftShift(Reference<HwJavascript> js, HwJsValue left, HwJsValue right) {
			super(js, left, right, "<<");
		}
		
	}
	
	protected static class HwJsRightShift extends HwJsBinaryValue {
		
		public HwJsRightShift(Reference<HwJavascript> js, HwJsValue left, HwJsValue right) {
			super(js, left, right, ">>");
		}
		
	}
	
	protected static class HwJsSum extends HwJsBinaryValue {
		
		public HwJsSum(Reference<HwJavascript> js, HwJsValue left, HwJsValue right) {
			super(js, left, right, "+");
		}
		
	}
	
	protected static class HwJsSub extends HwJsBinaryValue {
		
		public HwJsSub(Reference<HwJavascript> js, HwJsValue left, HwJsValue right) {
			super(js, left, right, "-");
		}
		
	}
	
	protected static class HwJsMul extends HwJsBinaryValue {
		
		public HwJsMul(Reference<HwJavascript> js, HwJsValue left, HwJsValue right) {
			super(js, left, right, "*");
		}
		
	}
	
	protected static class HwJsDiv extends HwJsBinaryValue {
		
		public HwJsDiv(Reference<HwJavascript> js, HwJsValue left, HwJsValue right) {
			super(js, left, right, "/");
		}
		
	}
	
	protected static class HwJsMod extends HwJsBinaryValue {
		
		public HwJsMod(Reference<HwJavascript> js, HwJsValue left, HwJsValue right) {
			super(js, left, right, "%");
		}
		
	}
	
	protected static class HwJsGetArray extends HwJsValue {
		
		private HwJsValue left;
		
		private HwJsValue right;
		
		public HwJsGetArray(Reference<HwJavascript> js, HwJsValue left, HwJsValue right) {
			super(js);
			this.left = left;
			this.right = right;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			sb.append(left.toString(0));
			sb.append('[');
			sb.append(right.toString(0));
			sb.append(']');
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return left.toLength(0) + 2 + right.toLength(0);
		}
		
	}
	
	protected static class HwJsNewArray extends HwJsValue {
		
		private HwJsValue[] values;
		
		public HwJsNewArray(Reference<HwJavascript> js, HwJsValue[] values) {
			super(js);
			this.values = values;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			sb.append('[');
			if (values != null) {
				for (int n = 0; n < values.length; n++) {
					sb.append(values[n].toString(level));
					if (n != values.length - 1) {
						sb.append(", ");
					}
				}
			}
			sb.append(']');
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			int size = 2;
			if (values != null) {
				for (int n = 0; n < values.length; n++) {
					size += values[n].toLength(level);
					if (n != values.length - 1) {
						size += 2;
					}
				}
			}
			return size;
		}
	}
	
	protected static class HwJsNewMap extends HwJsValue {
		
		public HwJsNewMap(Reference<HwJavascript> js, Map<Object, Object> map) {
			super(js);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			sb.append('{');
			sb.append('}');
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			int size = 2;
			return size;
		}
		
	}
	
	protected static class HwJsNew extends HwJsValue {
		
		private String classname;
		
		private HwJsValue[] arguments;
		
		public HwJsNew(Reference<HwJavascript> js, String classname, HwJsValue[] arguments) {
			super(js);
			this.classname = classname;
			this.arguments = arguments;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			sb.append("new ");
			sb.append(classname);
			sb.append('(');
			if (arguments != null) {
				for (int n = 0; n < arguments.length; n++) {
					sb.append(arguments[n].toString(level));
					if (n != arguments.length - 1) {
						sb.append(", ");
					}
				}
			}
			sb.append(')');
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			int size = 4 + classname.length() + 1 + 1;
			if (arguments != null) {
				for (int n = 0; n < arguments.length; n++) {
					size += arguments[n].toLength(level);
					if (n != arguments.length - 1) {
						size += 2;
					}
				}
			}
			return size;
		}
		
	}
	
	protected static class HwJsNot extends HwJsValue {
		
		private HwJsValue value;
		
		public HwJsNot(Reference<HwJavascript> js, HwJsValue value) {
			super(js);
			this.value = value;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			sb.append('!');
			sb.append(value.toString(0));
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return value.toLength(0) + 1;
		}
		
	}
	
	protected static class HwJsBinaryValue extends HwJsValue {
		
		private HwJsValue left;
		
		private HwJsValue right;
		
		private String operation;
		
		public HwJsBinaryValue(Reference<HwJavascript> js, HwJsValue left, HwJsValue right, String operation) {
			super(js);
			this.left = left;
			this.right = right;
			this.operation = operation;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			sb.append(left.toString(level));
			sb.append(' ');
			sb.append(operation);
			sb.append(' ');
			sb.append(right.toString(level));
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return left.toLength(0) + 4 + right.toLength(0);
		}
		
	}
	
	protected static class HwJsWindow extends HwJsValue {
		
		public HwJsWindow(Reference<HwJavascript> js) {
			super(js);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			return "window";
		}
		
		@Override
		protected int toLength(int level) {
			return 6;
		}
		
	}
	
	protected static class HwJsDocument extends HwJsValue {
		
		public HwJsDocument(Reference<HwJavascript> js) {
			super(js);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			return "document";
		}
		
		@Override
		protected int toLength(int level) {
			return 8;
		}
		
	}
	
	protected static class HwJsAssign extends HwJsCommand {
		
		private HwJsValue variable;
		
		private HwJsValue value;
		
		private String operation;
		
		public HwJsAssign(Reference<HwJavascript> js, HwJsValue variable, HwJsValue value, String operation) {
			super(js);
			this.variable = variable;
			this.value = value;
			this.operation = operation;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append(variable.toString(level));
			sb.append(' ');
			sb.append(operation);
			sb.append(' ');
			sb.append(value.toString(level));
			sb.append(";");
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return level + variable.toLength(0) + 2 + operation.length() + value.toLength(0) + 1;
		}
		
	}
	
	protected static class HwJsDefVar extends HwJsCommand {
		
		private HwJsValue name;
		
		private HwJsValue value;
		
		public HwJsDefVar(Reference<HwJavascript> js, HwJsValue name, HwJsValue value) {
			super(js);
			this.name = name;
			this.value = value;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append("var ");
			sb.append(name.toString(level));
			sb.append(" = ");
			sb.append(value.toString(level));
			sb.append(";");
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return level + 4 + name.toLength(0) + 3 + value.toLength(0) + 1;
		}
		
	}
	
	protected static class HwJsIf extends HwJsCommand {
		
		private HwJsValue condition;
		
		private HwJsBlock trueValue;
		
		private HwJsBlock elseValue;
		
		private HwJsElseIf[] elseifNodes;
		
		/**
		 * Construtor padrão
		 *
		 * @param js
		 * @param condition
		 * @param trueValue
		 * @param elseValue
		 * @param elseifNodes
		 */
		public HwJsIf(Reference<HwJavascript> js, HwJsValue condition, Runnable trueValue, Runnable elseValue, HwJsElseIf[] elseifNodes) {
			super(js);
			this.condition = condition;
			if (elseifNodes != null && elseifNodes.length > 0) {
				this.elseifNodes = elseifNodes;
			}
			this.trueValue = new HwJsBlock(js, trueValue);
			if (elseValue != null) {
				this.elseValue = new HwJsBlock(js, elseValue);
			}
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			String trueString = trueValue.toString(level + 1);
			String elseString = elseValue == null ? null : elseValue.toString(level + 1);
			StringBuilder sb = new StringBuilder(toLength(level));
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append("if (");
			sb.append(condition.toString(level));
			sb.append(") {\n");
			sb.append(trueString);
			sb.append('\n');
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append('}');
			if (elseifNodes != null) {
				for (int n = 0; n < elseifNodes.length; n++) {
					sb.append(' ');
					sb.append(elseifNodes[n].toString(level));
				}
			}
			if (elseValue != null) {
				sb.append(" else {\n");
				sb.append(elseString);
				sb.append('\n');
				for (int n = 0; n < level; n++) {
					sb.append('\t');
				}
				sb.append("}");
			}
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			int size = level + 4 + condition.toLength(0) + 4 + trueValue.toLength(level + 1) + 1 + level + 1;
			if (elseifNodes != null) {
				for (int n = 0; n < elseifNodes.length; n++) {
					size += 1 + elseifNodes[n].toLength(level);
				}
			}
			if (elseValue != null) {
				size += 8 + elseValue.toLength(level + 1) + 1 + level + 1;
			}
			return size;
		}
	}
	
	protected static class HwJsWhile extends HwJsCommand {
		
		private HwJsValue condition;
		
		private HwJsBlock block;
		
		public HwJsWhile(Reference<HwJavascript> js, HwJsValue condition, Runnable trueValue) {
			super(js);
			this.condition = condition;
			this.block = new HwJsBlock(js, trueValue);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append("while (");
			sb.append(condition.toString(level));
			sb.append(") {\n");
			sb.append(block.toString(level + 1));
			sb.append('\n');
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append('}');
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return level + 7 + condition.toLength(0) + 4 + block.toLength(level + 1) + 1 + level + 1;
		}
	}
	
	protected static class HwJsReturn extends HwJsCommand {
		
		private HwJsValue condition;
		
		public HwJsReturn(Reference<HwJavascript> js, HwJsValue condition) {
			super(js);
			this.condition = condition;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append("return ");
			sb.append(condition.toString(0));
			sb.append(";");
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return level + 7 + condition.toLength(0) + 1;
		}
	}
	
	protected static class HwJsSwitch extends HwJsCommand {
		
		private HwJsValue condition;
		
		private HwJsCaseDefault defaultBlock;
		
		private HwJsCase[] cases;
		
		public HwJsSwitch(Reference<HwJavascript> js, HwJsValue condition, Runnable defaultBlock, HwJsCase[] cases) {
			super(js);
			this.condition = condition;
			this.cases = cases;
			if (defaultBlock != null) {
				this.defaultBlock = new HwJsCaseDefault(jsReference, defaultBlock);
			}
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append("switch (");
			sb.append(condition.toString(0));
			sb.append(") {\n");
			for (int n = 0; n < cases.length; n++) {
				sb.append(cases[n].toString(level + 1));
				sb.append('\n');
			}
			if (defaultBlock != null) {
				sb.append(defaultBlock.toString(level + 1));
				sb.append('\n');
			}
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append('}');
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			int size = level + 8 + condition.toLength(0) + 4 + level + 1;
			for (int n = 0; n < cases.length; n++) {
				size += cases[n].toLength(level + 1) + 1;
			}
			if (defaultBlock != null) {
				size += defaultBlock.toLength(level + 1) + 1;
			}
			return size;
		}
		
	}
	
	protected static class HwJsCase extends HwJsNode {
		
		/** Variável */
		private HwJsValue variable;
		
		/** Bloco */
		private HwJsBlock block;
		
		public HwJsCase(Reference<HwJavascript> js, HwJsValue condition, Runnable trueValue) {
			super(js);
			this.variable = condition;
			this.block = new HwJsBlock(js, trueValue);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append("case ");
			sb.append(variable.toString(0));
			sb.append(" : {\n");
			sb.append(block.toString(level + 1));
			sb.append('\n');
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append('}');
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return level + 5 + variable.toLength(0) + 5 + block.toLength(level + 1) + 1 + level + 1;
		}
		
	}
	
	protected static class HwJsCaseDefault extends HwJsNode {
		
		/** Bloco */
		private HwJsBlock block;
		
		public HwJsCaseDefault(Reference<HwJavascript> js, Runnable trueValue) {
			super(js);
			this.block = new HwJsBlock(js, trueValue);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append("default : {\n");
			sb.append(block.toString(level + 1));
			sb.append('\n');
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append('}');
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return level + 12 + block.toLength(level + 1) + 1 + level + 1;
		}
		
	}
	
	protected static class HwJsElseIf extends HwJsNode {
		
		private HwJsValue condition;
		
		private HwJsBlock block;
		
		public HwJsElseIf(Reference<HwJavascript> js, HwJsValue condition, Runnable trueValue) {
			super(js);
			this.condition = condition;
			this.block = new HwJsBlock(js, trueValue);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			sb.append("else if (");
			sb.append(condition.toString(level));
			sb.append(") {\n");
			sb.append(block.toString(level + 1));
			sb.append('\n');
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append('}');
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return 9 + condition.toLength(0) + 4 + block.toLength(level + 1) + 1 + level + 1;
		}
		
	}
	
	protected static class HwJsBreak extends HwJsCommand {
		
		public HwJsBreak(Reference<HwJavascript> js) {
			super(js);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append("break;");
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return level + 6;
		}
	}
	
	protected static class HwJsContinue extends HwJsCommand {
		
		public HwJsContinue(Reference<HwJavascript> js) {
			super(js);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append("continue;");
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return level + 9;
		}
	}
	
	protected static class HwJsRepeat extends HwJsCommand {
		
		private HwJsValue condition;
		
		private HwJsBlock block;
		
		public HwJsRepeat(Reference<HwJavascript> js, HwJsValue condition, Runnable trueValue) {
			super(js);
			this.condition = condition;
			this.block = new HwJsBlock(js, trueValue);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append("repeat (");
			sb.append(condition.toString(level));
			sb.append(") {\n");
			sb.append(block.toString(level + 1));
			sb.append('\n');
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append('}');
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return level + 8 + condition.toLength(0) + 4 + block.toLength(level + 1) + 1 + level + 1;
		}
		
	}
	
	protected static class HwJsFor extends HwJsCommand {
		
		private HwJsValue condition;
		
		private HwJsDefVar define;
		
		private HwJsValue next;
		
		private HwJsBlock block;
		
		public HwJsFor(Reference<HwJavascript> js, HwJsDefVar define, HwJsValue condition, HwJsValue next, Runnable block) {
			super(js);
			this.define = define;
			this.condition = condition;
			this.next = next;
			this.block = new HwJsBlock(js, block);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append("for (");
			if (define != null) {
				sb.append(define.toString(0));
			} else {
				sb.append(';');
			}
			if (condition != null) {
				sb.append(condition.toString(0));
			}
			sb.append(';');
			if (next != null) {
				sb.append(next.toString(0));
			}
			sb.append(") {\n");
			sb.append(block.toString(level + 1));
			sb.append('\n');
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append('}');
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			int size = level + 5;
			if (define != null) {
				size += define.toLength(0);
			} else {
				size++;
			}
			if (condition != null) {
				size += condition.toLength(0);
			}
			size++;
			if (next != null) {
				size += next.toLength(0);
			}
			size += 4 + block.toLength(level + 1) + level + 1;
			return size;
		}
		
	}
	
	protected static class HwJsClass extends HwJsCommand {
		
		private String classname;
		
		private String[] parameters;
		
		private HwJsBlock block;
		
		public HwJsClass(Reference<HwJavascript> ref, String classname, String[] parameters, Runnable body) {
			super(ref);
			this.classname = classname;
			this.parameters = parameters;
			this.block = new HwJsBlock(ref, body);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append("var ");
			sb.append(classname);
			sb.append(" = function (");
			if (parameters != null) {
				for (int n = 0; n < parameters.length; n++) {
					sb.append(parameters[n]);
					if (n != parameters.length - 1) {
						sb.append(", ");
					}
				}
			}
			sb.append(") {\n");
			sb.append(block.toString(level + 1));
			sb.append('\n');
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append('}');
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			int size = level + 4 + classname.length() + 13 + 4 + block.toLength(level + 1) + 1 + level + 1;
			if (parameters != null) {
				for (int n = 0; n < parameters.length; n++) {
					size += parameters[n].length();
					if (n != parameters.length - 1) {
						size += 2;
					}
				}
			}
			return size;
		}
	}
	
	protected static class HwJsMethod extends HwJsCommand {
		
		private String classname;
		
		private String name;
		
		private String[] parameters;
		
		private HwJsBlock block;
		
		public HwJsMethod(Reference<HwJavascript> ref, String classname, String name, String[] parameters, Runnable body) {
			super(ref);
			this.classname = classname;
			this.name = name;
			this.parameters = parameters;
			this.block = new HwJsBlock(ref, body);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append(classname);
			sb.append(".prototype.");
			sb.append(name);
			sb.append(" = function (");
			if (parameters != null) {
				for (int n = 0; n < parameters.length; n++) {
					sb.append(parameters[n]);
					if (n != parameters.length - 1) {
						sb.append(", ");
					}
				}
			}
			sb.append(") {\n");
			sb.append(block.toString(level + 1));
			sb.append('\n');
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append('}');
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			int size = level + classname.length() + 11 + name.length() + 13 + 4 + block.toLength(level + 1) + 1 + level + 1;
			if (parameters != null) {
				for (int n = 0; n < parameters.length; n++) {
					size += parameters[n].length();
					if (n != parameters.length - 1) {
						size += 2;
					}
				}
			}
			return size;
		}
	}
	
	protected static class HWJSExpression extends HwJsCommand {
		
		private HwJsValue value;
		
		public HWJSExpression(Reference<HwJavascript> js, HwJsValue value) {
			super(js);
			this.value = value;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			String trueString = value.toString(level);
			StringBuilder sb = new StringBuilder(toLength(level));
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append(value.toString(level));
			sb.append(';');
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return level + value.toLength(level) + 1;
		}
		
	}
	
	protected static class HWJSDelete extends HwJsCommand {
		
		private HwJsValue value;
		
		public HWJSDelete(Reference<HwJavascript> js, HwJsValue value) {
			super(js);
			this.value = value;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			String trueString = value.toString(level);
			StringBuilder sb = new StringBuilder(toLength(level));
			for (int n = 0; n < level; n++) {
				sb.append('\t');
			}
			sb.append("delete ");
			sb.append(value.toString(0));
			sb.append(';');
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return level + 7 + value.toLength(0) + 1;
		}
		
	}
	
	protected static class HwJsGet extends HwJsValue {
		
		private HwJsValue left;
		
		private HwJsValue name;
		
		public HwJsGet(Reference<HwJavascript> js, HwJsValue left, HwJsValue name) {
			super(js);
			this.left = left;
			this.name = name;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			sb.append(left.toString(level));
			sb.append('.');
			sb.append(name.toString(level));
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return left.toLength(0) + 1 + name.toLength(0);
		}
		
	}
	
	protected static class HwJsCall extends HwJsValue {
		
		private HwJsValue left;
		
		private HwJsValue[] arguments;
		
		public HwJsCall(Reference<HwJavascript> js, HwJsValue left, HwJsValue[] arguments) {
			super(js);
			this.left = left;
			this.arguments = arguments;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			sb.append(left.toString(level));
			sb.append('(');
			int size = arguments.length;
			for (int n = 0; n < size; n++) {
				sb.append(arguments[n].toString(level));
				if (n != size - 1) {
					sb.append(',');
					sb.append(' ');
				}
			}
			sb.append(')');
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			int size = left.toLength(level) + 1;
			for (int n = 0; n < arguments.length; n++) {
				size += arguments[n].toLength(level);
				if (n != arguments.length - 1) {
					size += 2;
				}
			}
			size++;
			return size;
		}
		
	}
	
	public static abstract class HwJsValue extends HwJsNode {
		
		public HwJsValue(Reference<HwJavascript> js) {
			super(js);
		}
		
		public HwJsValue $get(String name) {
			HwJavascript js = getJavascript();
			return js.$get(this, name);
		}
		
		public HwJsValue $call(Object... arguments) {
			HwJavascript js = getJavascript();
			return new HwJsCall(js.ref, this, js.$vals(arguments));
		}
		
		public HwJsValue $not() {
			HwJavascript js = getJavascript();
			return js.$not(this);
		}
		
		public void $assign(Object value) {
			HwJavascript js = getJavascript();
			js.$assign(this, js.$val(value));
		}
		
		public HwJsValue $inc() {
			HwJavascript js = getJavascript();
			return js.$posinc(this);
		}
		
		public HwJsValue $dec() {
			HwJavascript js = getJavascript();
			return js.$posdec(this);
		}
		
		public HwJsValue $preinc() {
			HwJavascript js = getJavascript();
			return js.$preinc(this);
		}
		
		public HwJsValue $predec() {
			HwJavascript js = getJavascript();
			return js.$predec(this);
		}
		
		public HwJavascript $assignsum(Object value) {
			HwJavascript js = getJavascript();
			return js.$assignsum(this, js.$val(value));
		}
		
		public HwJavascript $assignsub(Object value) {
			HwJavascript js = getJavascript();
			return js.$assignsub(this, js.$val(value));
		}
		
		public HwJavascript $assignmul(Object value) {
			HwJavascript js = getJavascript();
			return js.$assignmul(this, js.$val(value));
		}
		
		public HwJavascript $assigndiv(Object value) {
			HwJavascript js = getJavascript();
			return js.$assigndiv(this, js.$val(value));
		}
		
		public HwJavascript $assignmod(Object value) {
			HwJavascript js = getJavascript();
			return js.$assignmod(this, js.$val(value));
		}
		
		public HwJsValue $array(Object value) {
			HwJavascript js = getJavascript();
			return js.$getarray(this, js.$val(value));
		}
		
		public HwJsValue $andbit(Object value) {
			HwJavascript js = getJavascript();
			return js.$andbit(this, js.$val(value));
		}
		
		public HwJsValue $orbit(Object value) {
			HwJavascript js = getJavascript();
			return js.$orbit(this, js.$val(value));
		}
		
		public HwJsValue $and(Object value) {
			HwJavascript js = getJavascript();
			return js.$and(this, js.$val(value));
		}
		
		public HwJsValue $or(Object value) {
			HwJavascript js = getJavascript();
			return js.$or(this, js.$val(value));
		}
		
		public HwJsValue $eq(Object value) {
			HwJavascript js = getJavascript();
			return js.$eq(this, js.$val(value));
		}
		
		public HwJsValue $neq(Object value) {
			HwJavascript js = getJavascript();
			return js.$neq(this, js.$val(value));
		}
		
		public HwJsValue $lt(Object value) {
			HwJavascript js = getJavascript();
			return js.$lt(this, js.$val(value));
		}
		
		public HwJsValue $le(Object value) {
			HwJavascript js = getJavascript();
			return js.$le(this, js.$val(value));
		}
		
		public HwJsValue $gt(Object value) {
			HwJavascript js = getJavascript();
			return js.$gt(this, js.$val(value));
		}
		
		public HwJsValue $ge(Object value) {
			HwJavascript js = getJavascript();
			return js.$ge(this, js.$val(value));
		}
		
		public HwJsValue $lshift(Object value) {
			HwJavascript js = getJavascript();
			return js.$lshift(this, js.$val(value));
		}
		
		public HwJsValue $rshift(Object value) {
			HwJavascript js = getJavascript();
			return js.$rshift(this, js.$val(value));
		}
		
		public HwJsValue $sum(Object value) {
			HwJavascript js = getJavascript();
			return js.$sum(this, js.$val(value));
		}
		
		public HwJsValue $sub(Object value) {
			HwJavascript js = getJavascript();
			return js.$sub(this, js.$val(value));
		}
		
		public HwJsValue $mul(Object value) {
			HwJavascript js = getJavascript();
			return js.$mul(this, js.$val(value));
		}
		
		public HwJsValue $div(Object value) {
			HwJavascript js = getJavascript();
			return js.$div(this, js.$val(value));
		}
		
		public HwJsValue $mod(Object value) {
			HwJavascript js = getJavascript();
			return js.$mod(this, js.$val(value));
		}
		
		public void $exec() {
			getJavascript().$exec(this);
		}
		
	}
	
	protected static abstract class HwJsCommand extends HwJsNode {
		
		public HwJsCommand(Reference<HwJavascript> js) {
			super(js);
		}
		
	}
	
	protected static class HwJsBlock extends HwJsCommand {
		
		protected final List<HwJsCommand> children;
		
		public HwJsBlock(Reference<HwJavascript> js) {
			super(js);
			children = new ArrayList<HwJsCommand>(8);
			js.get().blocks.push(this);
		}
		
		public HwJsBlock(Reference<HwJavascript> js, Runnable runnable) {
			this(js);
			runnable.run();
			$close();
		}
		
		public List<HwJsCommand> children() {
			return children;
		}
		
		/**
		 * @return conteudo
		 */
		public HwJsBlock $close() {
			getJavascript().blocks.pop();
			return this;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			List<HwJsCommand> children = children();
			int size = children.size();
			for (int n = 0; n < size; n++) {
				sb.append(children.get(n).toString(level));
				if (n != size - 1) {
					sb.append('\n');
				}
			}
			return sb.toString();
		}
		
		@Override
		protected int toLength(int level) {
			int count = 0;
			List<HwJsCommand> children = children();
			int size = children.size();
			for (int n = 0; n < size; n++) {
				count += children.get(n).toLength(level);
				if (n != size - 1) {
					count++;
				}
			}
			return count;
		}
		
	}
	
	protected static abstract class HwJsNode {
		
		protected Reference<HwJavascript> jsReference;
		
		public HwJsNode(Reference<HwJavascript> jsReference) {
			this.jsReference = jsReference;
		}
		
		public HwJavascript getJavascript() {
			return jsReference.get();
		}
		
		protected abstract String toString(int level);
		
		protected abstract int toLength(int level);
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return toString(0);
		}
		
	}
	
}
