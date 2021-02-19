
/**
 * A very very basic driver for CS1501 Project 1
 * @author	Dr. Farnan
 */


public class App {
	public static void main(String[] args) {
		BST<Integer> tree = new BST<>();
		tree.put(10);
		tree.put(5);
		tree.put(2);
		tree.put(37);
		tree.put(45);
		tree.put(12);
		tree.put(6);
		tree.put(44);
		tree.put(43);


		tree.delete(10);
		tree.delete(2);


		boolean c = tree.contains(8);
		System.out.println(tree.inOrderTraversal());
		boolean b = tree.isBalanced();
		System.out.println(b);
		System.out.println(tree.serialize());
		System.out.println(tree.serialize());
		//System.out.println(tree.reverse().serialize());
		//System.out.println(c);
		//System.out.println("Successfully built an empty tree!");


	}
}
