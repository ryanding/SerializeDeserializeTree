package com.spritelogic.serialize_deserialize_tree;

import java.util.Stack;

class Node {
	int data;
	Node left, right;
	
	public Node(int data) {
		this.data = data;
		left = right = null;
	}
}

class NodeLevel {
	Node node;
	int level;
	
	public NodeLevel(Node node, int level) {
		this.node = node;
		this.level = level;
	}
}

public class SerializeDeserializeTree {
	public String serialize(Node node) {
		StringBuilder serializedTree = new StringBuilder();
		
		serializeHelper(node, 0, serializedTree);
		
		return serializedTree.toString();
	}

	public Node deserialize(String serializedTree) {
		Stack<NodeLevel> stack = new Stack<>();
		
		String[] nodes = serializedTree.split(";");
		for (String node : nodes) {
			String[] dataLevel = node.split(",");
			if (dataLevel.length != 2) {
				System.out.println("Invalid serialization of the tree");
				return null;
			}
			int data = Integer.valueOf(dataLevel[0]);
			int level = Integer.valueOf(dataLevel[1]);
			int absLevel = Math.abs(level);
			
			// create a new node;
			Node newNode = new Node(data);
			
			// create a new node level object
			NodeLevel newNodeLevel = new NodeLevel(newNode, Math.abs(level));
			
			if (stack.isEmpty()) {
				stack.push(newNodeLevel);
			}
			else {
				do {
					int topLevel = stack.peek().level;
					if (topLevel >= absLevel) {
						stack.pop();
					} else {
						break;
					}
				}
				while (true);
				
				Node topNode = stack.peek().node;
				if (level < 0) {	// left child
					topNode.left = newNode;
				} else {
					topNode.right = newNode;
				}
				
				stack.push(newNodeLevel);
			}
		}
		
		// pop the stack and return the root
		Node root = null;
		while (!stack.isEmpty()) {
			root = stack.pop().node;
		}
		
		return root;
	}
	
	private void serializeHelper(Node node, int level, StringBuilder serializedTree) {
		if (node != null) {
			int absL = Math.abs(level);
			if (serializedTree.length() != 0) {
				serializedTree.append(";");
			}
			serializedTree.append(String.valueOf(node.data) + 
					"," + 
					String.valueOf(level));
			serializeHelper(node.left, -(absL+1), serializedTree);
			serializeHelper(node.right, absL+1, serializedTree);
		}
	}
	
	public static void main(String[] args) {
		/* Set up a tree:
		        1
		      /   \
		     2     3
		    / \   / \
		   4   5 6   7
		*/
		Node root = new Node(1);
		root.left = new Node(2);
		root.right = new Node(3);
		root.left.left = new Node(4);
		root.left.right = new Node(5);
		root.right.left = new Node(6);
		root.right.right = new Node(7);
		
		SerializeDeserializeTree sdTree = new SerializeDeserializeTree();
		
		// Serialize the tree
		String serializedTree = sdTree.serialize(root);
		System.out.println("Serialized Tree: " + serializedTree);
		
		// Deserialize the tree
		Node deserializedRoot = sdTree.deserialize(serializedTree);
		
		// Serialize again and it should be the same
		String newSerializedTree = sdTree.serialize(deserializedRoot);
		
		System.out.println("New Serialized Tree: " + newSerializedTree);
		System.out.println("Serialize result is the same as before: " + 
				newSerializedTree.equals(serializedTree));
	}
}
