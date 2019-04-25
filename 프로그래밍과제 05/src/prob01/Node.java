package prob01;

public class Node <E> {
	public E word;
	public E type;
	public E meaning;
	public Node<E> left;
	public Node<E> right;
	public Node<E> p;
	
	public Node(E word, E type, E meaning) {
		this.word = word;
		this.type = type;
		this.meaning = meaning;
		
		left = null;
		right = null;
		p = null;
	}
	public String toString() {
		return word.toString();
	}
	public String toString_type() {
		return type.toString();
	}
	public String toString_mean() {
		return meaning.toString();
	}
}