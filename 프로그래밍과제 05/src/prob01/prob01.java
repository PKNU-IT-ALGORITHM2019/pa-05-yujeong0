package prob01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class prob01 {
	static BST T = new BST();
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		read();
		
		while(true) {
			System.out.print("$ ");
			String s = sc.next();
			if(s.compareToIgnoreCase("size") == 0) {
				System.out.println(T.n);
			}
			else if(s.compareToIgnoreCase("find") == 0) {
				s = sc.nextLine().trim();
				Node n = tree_search(T.root, s);
				if(n == null) System.out.println("Not found.");
				else System.out.println(n.word + " : " + n.meaning);
			}
			else if(s.compareToIgnoreCase("add") == 0) {
				s = sc.nextLine();
				System.out.print("word : ");
				String word = sc.nextLine();
				System.out.print("class : ");
				String type = sc.nextLine();
				System.out.print("meaning : ");
				String meaning = sc.nextLine();
				tree_insert(word, type, meaning);
			}
			else if(s.compareToIgnoreCase("delete") == 0) {
				s = sc.nextLine().trim();
				if(tree_delete(s) == true) 
					System.out.println("Deleted successfully.");
				
				else System.out.println("Not found.");
			}
			else if(s.compareToIgnoreCase("deleteall") == 0) {
				s = sc.nextLine().trim();
				tree_deleteall(s);
			}
			else if(s.compareToIgnoreCase("print") == 0) {
				inorder_tree_walk(T.root);
			}
			else if(s.compareToIgnoreCase("exit") == 0)
				break;
		}
		sc.close();
	}
	static void inorder_tree_walk(Node n) {
		if(n != null) {
			inorder_tree_walk(n.left);
			System.out.println(n.toString() + " " + n.toString_type() + " : " + n.toString_mean());
			inorder_tree_walk(n.right);
		}
	}
	static void tree_deleteall(String str) {
		try {
			Scanner s = new Scanner(new File(str));
			int i = 0;
			while(s.hasNext()) {
				tree_delete(s.nextLine());
				i++;
			}
			System.out.println(i + " words were deleted successfully.");
			s.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("No File.");
		}
	}
	
	static boolean tree_delete(String word) {
		boolean found = false;
		Node z = tree_search(T.root, word);
		if(z == null) return false;
		while(z != null) {
			Node x, y;
			if(z.left == null || z.right == null) y = z;
			else y = tree_successor(z);
			if(y.left != null) x = y.left;
			else x = y.right;
			if(x != null) x.p = y.p;
			if(y.p == null) T.root = x;
			else if(y == y.p.left) y.p.left = x;
			else y.p.right = x;
			if(y != z) {
				z.word = y.word;
				z.type = y.type;
				z.meaning = y.meaning;
			}
			T.n --;
			z = tree_search(T.root, word);
		}
		return true;
	}
	
	static Node tree_successor(Node x) {
		//노드 x의 successor란 key[x]보다 크면서 가장 작은 키를 가진 노드
		if(x.right != null) return tree_minimum(x.right);
		Node y = x.p;
		while(y != null && x == y.right) {
			x = y;
			y = y.p;
		}
		return y;
	}
	
	static Node tree_minimum(Node x) {
		while(x.left != null)
			x = x.left;
		return x;
	}
	
	static Node tree_search(Node n, String k) {
		if(n == null || k.equalsIgnoreCase(n.toString())) return n;
		if(k.compareToIgnoreCase(n.toString()) < 0) 
			return tree_search(n.left, k);
		else return tree_search(n.right, k);
	}
	
	static void read() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("shuffled_dict.txt")); 
			String data = ""; 
			
			if(data.length() == 0) 
				data = reader.readLine();
				
			int id1 = data.indexOf("(");
			int id2 = data.indexOf(")");
			
			String s1 = data.substring(0, id1 - 1);
			String s2 = data.substring(id1, id2 + 1);
			String s3 = data.substring(id2 + 1); 
			
			T.root = new Node(s1, s2, s3);
			
			while ((data = reader.readLine()) != null) { 
				if(data.length() == 0) 
					data = reader.readLine();
				
				int idx1 = data.indexOf("(");
				int idx2 = data.indexOf(")");
				
				String str1 = data.substring(0, idx1 - 1);
				String str2 = data.substring(idx1, idx2 + 1);
				String str3 = data.substring(idx2 + 1); // 설명이 없는 단어도 있어서 +1로 처리
			
				tree_insert(str1, str2, str3);
			} 
			reader.close(); 
		} catch (FileNotFoundException e) {
			System.out.println("No File");
		} catch (IOException e) {
			System.out.println("예외");
		}
		
	}
	
	static void tree_insert(String word, String type, String meaning) {
		Node z = new Node(word, type, meaning); //insert할 노드
		Node y = null;
		Node x = T.root;
		while(x != null) {
			y = x;
			if(z.toString().compareToIgnoreCase(x.toString()) < 0) x = x.left;
			else x =x.right;
		}
		z.p = y;
		if(y == null) T.root = z;
		else if(z.toString().compareToIgnoreCase(y.toString()) < 0) y.left = z;
		else y.right = z;
		
		T.n ++;
	}
}