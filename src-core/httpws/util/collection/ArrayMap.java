package httpws.util.collection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A classe {@link ArrayList} de inteiro.
 *
 * @author Tecgraf
 * @param <K>
 * @param <V>
 */
public class ArrayMap<K extends Comparable<K>, V> {
	
	/** Elementos */
	protected Entry<K, V>[] array;
	
	/** Tamanho */
	protected int size;
	
	/** Capacidade */
	private int capacity;
	
	/**
	 * Construtor padrão
	 */
	public ArrayMap() {
		this(8);
	}
	
	/**
	 * Construtor
	 *
	 * @param capacity
	 */
	public ArrayMap(int capacity) {
		super();
		this.capacity = Math.max(1, capacity);
	}
	
	/**
	 * Quantidade de elementos
	 *
	 * @return quantidade de elementos
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Indica se está vazio
	 *
	 * @return está vazio?
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Indica se tem um elemento com a chave
	 *
	 * @param key
	 * @return tem um elemento com a chave?
	 */
	public boolean contains(K key) {
		if (array == null) { return false; }
		int index = search(key);
		if (index < 0) { return false; }
		return true;
	}
	
	/**
	 * Recupera o valor de uma chave
	 *
	 * @param key
	 * @return valor ou nulo
	 */
	public V get(K key) {
		if (array == null) { return null; }
		int index = search(key);
		if (index < 0) { return null; }
		return array[index].value;
	}
	
	/**
	 * Altera o valor de uma chave. Caso não tenha a chave, nada será efetuado
	 *
	 * @param key
	 * @param value
	 * @return this
	 */
	public ArrayMap<K, V> set(K key, V value) {
		if (array == null) { return this; }
		int index = search(key);
		if (index < 0) { return this; }
		array[index].value = value;
		return this;
	}
	
	/**
	 * Adiciona ou altera um valor para uma chave
	 *
	 * @param key
	 * @param value
	 * @return this
	 */
	public ArrayMap<K, V> put(K key, V value) {
		if (array == null) {
			array = array(capacity);
		} else if (size + 1 > array.length) {
			grow(size + 1);
		}
		int i = search(key);
		if (i < 0) {
			i = -(i + 1);
		}
		if (size - i > 0) {
			System.arraycopy(array, i, array, i + 1, size - i);
		}
		array[i] = new Entry<K, V>(key, value);
		size++;
		return this;
	}
	
	/**
	 * Remove um valor de uma chave
	 *
	 * @param key
	 * @return this
	 */
	public ArrayMap<K, V> remove(K key) {
		if (array == null) { return this; }
		int index = search(key);
		if (index < 0) { return this; }
		int numMoved = size - index - 1;
		if (numMoved > 0) {
			System.arraycopy(array, index + 1, array, index, numMoved);
		}
		size--;
		return this;
	}
	
	/**
	 * Remove todos os elementos
	 *
	 * @return this
	 */
	public ArrayMap<K, V> clear() {
		array = null;
		size = 0;
		return this;
	}
	
	/**
	 * Retorna todas as chaves numa array criado
	 *
	 * @return todas as chaves
	 */
	public K[] keys() {
		@SuppressWarnings("unchecked")
		K[] result = (K[]) Array.newInstance(Comparable.class.getComponentType(), size);
		for (int n = 0; n < size; n++) {
			result[n] = array[n].key;
		}
		return result;
	}
	
	/**
	 * Retorna todos os valores numa array criado
	 *
	 * @return todos os valores
	 */
	public V[] values() {
		@SuppressWarnings("unchecked")
		V[] result = (V[]) new Object[size];
		for (int n = 0; n < size; n++) {
			result[n] = array[n].value;
		}
		return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int n = 0; n < size; n++) {
			sb.append(array[n]);
			if (n != size - 1) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * Reduz a capacidade do array até o limite
	 *
	 * @return this
	 */
	public ArrayMap<K, V> trimToSize() {
		if (array == null) { return this; }
		int oldCapacity = array.length;
		if (size < oldCapacity) {
			array = Arrays.copyOf(array, size);
		}
		return this;
	}
	
	/**
	 * Buscar por um elemento
	 *
	 * @param key
	 * @return indice
	 */
	protected int search(K key) {
		int low = 0;
		int high = size - 1;
		while (low <= high) {
			int mid = ((low + high) >> 1);
			K midVal = array[mid].key;
			int cmp = midVal.compareTo(key);
			if (cmp < 0) {
				low = mid + 1;
			} else if (cmp > 0) {
				high = mid - 1;
			} else {
				return mid;
			}
		}
		return -(low + 1);
	}
	
	/**
	 * @param capacity
	 * @return array
	 */
	@SuppressWarnings("unchecked")
	protected Entry<K, V>[] array(int capacity) {
		return new Entry[capacity];
	}
	
	/**
	 * @param minCapacity
	 */
	protected void grow(int minCapacity) {
		array = Arrays.copyOf(array, array.length << 1);
	}
	
	/**
	 * Entidades
	 *
	 * @author Bernardo Breder
	 * @param <K>
	 * @param <V>
	 */
	public static class Entry<K extends Comparable<K>, V> implements Comparable<Entry<K, V>> {
		
		/** Chave */
		protected K key;
		
		/** Hash */
		protected int hash;
		
		/** Valor */
		protected V value;
		
		/**
		 * Construtor
		 *
		 * @param key
		 * @param value
		 */
		public Entry(K key, V value) {
			super();
			this.key = key;
			this.hash = key.hashCode();
			this.value = value;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public int compareTo(Entry<K, V> o) {
			if (key == o.key) { return 0; }
			int cmd = hash - o.hash;
			if (cmd == 0) {
				cmd = System.identityHashCode(key) - System.identityHashCode(o.key);
			}
			return cmd;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return key + "=" + value;
		}
		
	}
	
}
