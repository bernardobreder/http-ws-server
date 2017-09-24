package httpws.builder;

import java.util.Arrays;

/**
 * Estrutura de fila de objetos
 *
 * @author Bernardo Breder
 * @param <E>
 */
public class HwQueue<E> {

	/** Elementos */
	private Object[] elements;

	/** Cabeça da fila */
	private int head;

	/** Final da fila */
	private int tail;

	/** Capacidade */
	private int capacity;

	/**
	 * Construtor
	 */
	public HwQueue() {
		this(8);
	}

	/**
	 * Construtor
	 *
	 * @param capacity
	 */
	public HwQueue(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * Retorna o último elemento
	 *
	 * @return último elemento
	 */
	public E peek() {
		if (tail - head == 0) { return null; }
		@SuppressWarnings("unchecked")
		E element = (E) elements[head];
		return element;
	}

	/**
	 * Retorna o i-ésino elemento
	 *
	 * @param index
	 * @return i-ésino elemento
	 */
	public E peek(int index) {
		if (index >= tail - head) { return null; }
		@SuppressWarnings("unchecked")
		E element = (E) elements[head + index];
		return element;
	}

	/**
	 * Indica se encontrou o elemento
	 *
	 * @param element
	 * @return encontrou
	 */
	public boolean contain(E element) {
		if (tail - head == 0) { return false; }
		for (int n = head; n < tail; n++) {
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
		if (tail - head == 0) { return -1; }
		for (int n = head; n < tail; n++) {
			Object item = elements[n];
			if (!(element == null ? item == null : element == item ? true : element.equals(item))) { return n - head; }
		}
		return -1;
	}

	/**
	 * Acrescenta um elemento na pilha
	 *
	 * @param value
	 * @return this
	 */
	public HwQueue<E> offer(E value) {
		if (elements == null) {
			elements = new Object[capacity];
		}
		if (tail >= elements.length) {
			elements = Arrays.copyOfRange(elements, head, tail + ((tail - head) >> 1) + 1);
			tail -= head;
			head = 0;
		}
		elements[tail++] = value;
		return this;
	}

	/**
	 * Retira um elemento da pilha
	 *
	 * @return elemento retirado
	 */
	public E poll() {
		if (tail - head <= 0) { return null; }
		@SuppressWarnings("unchecked")
		E element = (E) elements[head];
		elements[head] = null;
		head++;
		if (tail - head <= 0) {
			head = tail = 0;
			elements = null;
		}
		return element;
	}

	/**
	 * Limpa toda pilha
	 *
	 * @return this
	 */
	public HwQueue<E> clear() {
		head = tail = 0;
		elements = null;
		return this;
	}

	/**
	 * Quantidade de elementos
	 *
	 * @return quantidade
	 */
	public int size() {
		return tail - head;
	}

	/**
	 * Indica se está vazio
	 *
	 * @return vazio
	 */
	public boolean isEmpty() {
		return (tail - head) == 0;
	}

	/**
	 * Compacta a estrutura
	 *
	 * @return this
	 */
	public HwQueue<E> trim() {
		if (head > 0 || tail < elements.length) {
			elements = Arrays.copyOfRange(elements, head, tail);
			tail -= head;
			head = 0;
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		if (elements == null) { return 0; }
		int result = 1;
		for (int n = head; n < tail; n++) {
			Object element = elements[n];
			result = 31 * result + (element == null ? 0 : element.hashCode());
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		HwQueue<?> other = (HwQueue<?>) obj;
		int size = tail - head;
		if (size != other.size()) { return false; }
		for (int n = 0; n < size; n++) {
			Object o1 = elements[head + n];
			Object o2 = other.elements[other.head + n];
			if (!(o1 == null ? o2 == null : o1 == o2 ? true : o1.equals(o2))) { return false; }
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		int size = tail - head;
		if (size == 0) { return "[]"; }
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (int n = 0; n < size; n++) {
			@SuppressWarnings("unchecked")
			E e = (E) elements[head + n];
			sb.append(e.toString());
			if (n != size - 1) {
				sb.append(',').append(' ');
			}
		}
		sb.append(']');
		return sb.toString();
	}

	/**
	 * Estrututra de Fila concorrente
	 *
	 * @author Bernardo Breder
	 * @param <E>
	 */
	public static class HwConcurrentQueue<E> extends HwQueue<E> {

		/**
		 * Construtor
		 */
		public HwConcurrentQueue() {
			super();
		}

		/**
		 * Construtor
		 *
		 * @param capacity
		 */
		public HwConcurrentQueue(int capacity) {
			super(capacity);
		}

		/**
		 * Se o primeiro elemento não for o do parametro, será adicionado na fila
		 *
		 * @param element
		 * @return this
		 */
		public synchronized HwQueue<E> peekAndPush(E element) {
			E peek = super.peek();
			if (element != peek && !peek.equals(element)) {
				super.offer(element);
			}
			return this;
		}

		/**
		 * Se não encontrar o elemento do parametro, será adicionado na fila
		 *
		 * @param element
		 * @return this
		 */
		public synchronized HwQueue<E> containAndPush(E element) {
			if (!super.contain(element)) {
				super.offer(element);
			}
			return this;
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
		public synchronized HwQueue<E> offer(E value) {
			return super.offer(value);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized E poll() {
			return super.poll();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public synchronized HwQueue<E> clear() {
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
		public synchronized HwQueue<E> trim() {
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
