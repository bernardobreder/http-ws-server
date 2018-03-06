package httpws.builder.sql;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import httpws.builder.HwStack;

public class HwSql {
	
	private final HwStack<HwSqlBlock> blocks;
	
	private HwSqlBlock block;
	
	private final Reference<HwSql> ref = new WeakReference<HwSql>(this);
	
	private final HwSqlNull nullValue = new HwSqlNull(ref);
	
	private final HwSqlBoolean trueValue = new HwSqlBoolean(ref, true);
	
	private final HwSqlBoolean falseValue = new HwSqlBoolean(ref, false);
	
	public HwSql() {
		blocks = new HwStack<HwSqlBlock>();
		block = new HwSqlBlock(ref);
	}
	
	public HwSqlSelect $select(HwSqlValue lvalue, HwSqlValue... arguments) {
		return null;
	}
	
	public HwSql $eof() {
		blocks.peek().$close();
		return this;
	}
	
	public HwSql $reset() {
		blocks.clear();
		blocks.push(block = new HwSqlBlock(ref));
		return this;
	}
	
	public HwSqlValue $col(String c) {
		return null;
	}
	
	public HwSqlValue $eq(HwSqlValue left, HwSqlValue right) {
		return null;
	}
	
	public HwSqlValue $and(HwSqlValue left, HwSqlValue right) {
		return null;
	}
	
	public HwSqlValue $val(Object value) {
		if (value == null) {
			return nullValue;
		} else if (value instanceof HwSqlValue) {
			return (HwSqlValue) value;
		} else if (value instanceof Integer) {
			return new HwSqlInteger(ref, Long.valueOf(((Integer) value).longValue()));
		} else if (value instanceof Long) {
			return new HwSqlInteger(ref, (Long) value);
		} else if (value instanceof Float) {
			return new HwSqlFloat(ref, Double.valueOf(((Float) value).doubleValue()));
		} else if (value instanceof Double) {
			return new HwSqlFloat(ref, (Double) value);
		} else if (value instanceof String) {
			return new HwSqlString(ref, (String) value);
		} else if (value instanceof HwJsMethod) {
			return new HwJsFunction(ref, (HwJsMethod) value);
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
		return block.toString(0);
	}
	
	public interface HwJsMethod {
		
		public void action();
		
	}
	
	private static class HwSqlNull extends HwSqlValue {
		
		public HwSqlNull(Reference<HwSql> js) {
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
	
	private static class HwSqlBoolean extends HwSqlValue {
		
		private Boolean flag;
		
		public HwSqlBoolean(Reference<HwSql> js, boolean flag) {
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
	
	private static class HwSqlInteger extends HwSqlValue {
		
		private Long value;
		
		public HwSqlInteger(Reference<HwSql> js, Long value) {
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
	
	private static class HwSqlFloat extends HwSqlValue {
		
		private Double value;
		
		public HwSqlFloat(Reference<HwSql> js, Double value) {
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
	
	private static class HwSqlString extends HwSqlValue {
		
		private String value;
		
		/**
		 * @param js
		 * @param value
		 */
		public HwSqlString(Reference<HwSql> js, String value) {
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
	
	private static class HwJsFunction extends HwSqlValue {
		
		private static final String[] OBJECTS = new String[0];
		
		private HwSqlBlock value;
		
		/**
		 * @param js
		 * @param method
		 */
		public HwJsFunction(Reference<HwSql> js, HwJsMethod method) {
			super(js);
			value = new HwSqlBlock(js);
			// method.action(OBJECTS);
			value.$close();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			sb.append("function () {\n");
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
			return 14 + value.toLength(level + 1) + level + 2;
		}
		
	}
	
	private static class HwSqlIdentifier extends HwSqlValue {
		
		private String name;
		
		public HwSqlIdentifier(Reference<HwSql> js, String name) {
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
	
	private static class HwSqlEqual extends HwSqlValue {
		
		private HwSqlValue left;
		
		private HwSqlValue right;
		
		public HwSqlEqual(Reference<HwSql> js, HwSqlValue left, HwSqlValue right) {
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
			sb.append(left.toString(level));
			sb.append(" = ");
			sb.append(right.toString(level));
			return sb.toString();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected int toLength(int level) {
			return left.toLength(0) + 3 + right.toLength(0);
		}
		
	}
	
	protected static abstract class HwSqlValue extends HWJSNode {
		
		public HwSqlValue(Reference<HwSql> js) {
			super(js);
		}
		
		public HwSqlValue $in(HwSqlValue... $col) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public HwSqlValue $eq(HwSqlValue $col) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public HwSqlValue $and(Object $eq) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	protected static class HwSqlSelect extends HWJSNode {
		
		public HwSqlSelect(Reference<HwSql> js) {
			super(js);
		}
		
		@Override
		protected String toString(int level) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		protected int toLength(int level) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		public HwSqlSelect $from(String string) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public HwSqlSelect $join(String string, HwSqlValue v) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public HwSql $close() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public HwSqlSelect $where(HwSqlValue condition) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public HwSqlSelect $group(HwSqlValue... values) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public HwSqlSelect $order(HwSqlValue... $eq) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	private static abstract class HwSqlCommand extends HWJSNode {
		
		public HwSqlCommand(Reference<HwSql> js) {
			super(js);
		}
		
	}
	
	private static class HwSqlBlock extends HwSqlCommand {
		
		protected final List<HwSqlCommand> children;
		
		public HwSqlBlock(Reference<HwSql> js) {
			super(js);
			children = new ArrayList<HwSqlCommand>(8);
			js.get().blocks.push(this);
		}
		
		public List<HwSqlCommand> children() {
			return children;
		}
		
		/**
		 * @return conteudo
		 */
		public HwSqlBlock $close() {
			getJavascript().blocks.pop();
			return this;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected String toString(int level) {
			StringBuilder sb = new StringBuilder(toLength(level));
			List<HwSqlCommand> children = children();
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
			List<HwSqlCommand> children = children();
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
	
	private static abstract class HWJSNode {
		
		protected Reference<HwSql> jsReference;
		
		public HWJSNode(Reference<HwSql> jsReference) {
			this.jsReference = jsReference;
		}
		
		public HwSql getJavascript() {
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
