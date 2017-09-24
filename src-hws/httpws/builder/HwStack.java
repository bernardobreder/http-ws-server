package httpws.builder;

import java.util.Arrays;

/**
 * Estrutura de pilha de objetos
 *
 * @author Bernardo Breder
 * @param <E>
 */
public class HwStack<E> {

	/** Elementos */
	private Object[] elements;

	/** Tamanho */
	private int size;

	/** Capacidade */
	private int capacity;

	/**
	 * Construtor
	 */
	public HwStack() {
		this(8);
	}

	/**
	 * Construtor
	 *
	 * @param capacity
	 */
	public HwStack(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * Retorna o último elemento
	 *
	 * @return último elemento
	 */
	public E peek() {
		if (size == 0) { return null; }
		@SuppressWarnings("unchecked")
		E element = (E) elements[size - 1];
		return element;
	}

	/**
	 * Retorna o i-ésino elemento
	 *
	 * @param index
	 * @return i-ésino elemento
	 */
	public E peek(int index) {
		if (index >= size) { return null; }
		@SuppressWarnings("unchecked")
		E element = (E) elements[size - 1 - index];
		return element;
	}

	/**
	 * Indica se encontrou o elemento
	 *
	 * @param element
	 * @return encontrou
	 */
	public boolean contain(E element) {
		if (size == 0) { return false; }
		for (int n = size - 1; n >= 0; n--) {
			Object item = elements[n];
			if (!(element == null ? item == null : element == item ? true : element.equals(item))) { return true; }
		}
		return false;
	}

	/**
	 * Indica se encontrou o elemento
	 *
	 * @param element
	 * @return encontrou
	 */
	public int indexOf(E element) {
		if (size == 0) { return -1; }
		for (int n = size - 1; n >= 0; n--) {
			Object item = elements[n];
			if (!(element == null ? item == null : element == item ? true : element.equals(item))) { return (size - 1) - n; }
		}
		return -1;
	}

	/**
	 * Acrescenta um elemento na pilha
	 *
	 * @param value
	 * @return this
	 */
	public HwStack<E> push(E value) {
		if (elements == null) {
			elements = new Object[capacity];
		}
		if (size >= elements.length) {
			int newLength = elements.length + (elements.length >> 1) + 1;
			elements = Arrays.copyOf(elements, newLength);
		}
		elements[size++] = value;
		return this;
	}

	/**
	 * Retira um elemento da pilha
	 *
	 * @return elemento retirado
	 */
	public E pop() {
		@SuppressWarnings("unchecked")
		E element = (E) elements[--size];
		elements[size] = null;
		if (size == 0) {
			elements = null;
		}
		return element;
	}

	/**
	 * Limpa toda pilha
	 *
	 * @return this
	 */
	public HwStack<E> clear() {
		size = 0;
		elements = null;
		return this;
	}

	/**
	 * Quantidade de elementos
	 *
	 * @return quantidade
	 */
	public int size() {
		return size;
	}

	/**
	 * Indica se está vazio
	 *
	 * @return vazio
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Compacta a estrutura
	 *
	 * @return this
	 */
	public HwStack<E> trim() {
		if (size != elements.length) {
			elements = Arrays.copyOf(elements, size);
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Arrays.hashCode(elements);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		HwStack<?> other = (HwStack<?>) obj;
		if (size != other.size) { return false; }
		for (int n = 0; n < size; n++) {
			Object o1 = elements[n];
			Object o2 = other.elements[n];
			if (!(o1 == null ? o2 == null : o1 == o2 ? true : o1.equals(o2))) { return false; }
		}
		if (!Arrays.equals(elements, other.elements)) { return false; }
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (size == 0) { return "[]"; }
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (int n = 0; n < size; n++) {
			@SuppressWarnings("unchecked")
			E e = (E) elements[n];
			sb.append(e.toString());
			if (n != size - 1) {
				sb.append(',').append(' ');
			}
		}
		sb.append(']');
		return sb.toString();
	}

	/**
	 * Estrutura de pilha concorrente
	 *
	 * @author Bernardo Breder
	 * @param <E>
	 */
	public static class HwConcurrentStack<E> extends HwStack<E> {

		/**
		 * Construtor padrão
		 */
		public HwConcurrentStack() {
			super();
		}

		/**
		 * Construtor com capacidade atribuida
		 *
		 * @param capacity
		 */
		public HwConcurrentStack(int capacity) {
			super(capacity);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized E peek() {
			return super.peek();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized E peek(int index) {
			return super.peek(index);
		}

		/**
		 * Se o primeiro elemento não for o do parametro, será adicionado na fila
		 *
		 * @param element
		 * @return this
		 */
		public synchronized HwStack<E> peekAndPush(E element) {
			E peek = super.peek();
			if (element != peek && !peek.equals(element)) {
				super.push(element);
			}
			return this;
		}

		/**
		 * Se não encontrar o elemento do parametro, será adicionado na fila
		 *
		 * @param element
		 * @return this
		 */
		public synchronized HwStack<E> containAndPush(E element) {
			if (!super.contain(element)) {
				super.push(element);
			}
			return this;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized boolean contain(E element) {
			return super.contain(element);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized int indexOf(E element) {
			return super.indexOf(element);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized HwStack<E> push(E value) {
			return super.push(value);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized E pop() {
			return super.pop();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized HwStack<E> clear() {
			return super.clear();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized int size() {
			return super.size();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized boolean isEmpty() {
			return super.isEmpty();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized HwStack<E> trim() {
			return super.trim();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized int hashCode() {
			return super.hashCode();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized boolean equals(Object obj) {
			return super.equals(obj);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized String toString() {
			return super.toString();
		}

	}

}
