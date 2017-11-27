This project implements a way to serialize and deserialize a binary tree. 

To serialize the tree, we traverse the tree depth first and concatenate the nodes in a string in pre-order. The level of each node is also preserved with the each node. For example, for the following tree:

                1
              /   \
             2     3
            / \   / \
           4   5 6   7

the serialized string will be: 1,0;2,-1;4,-2;5,2;3,1;6,-2;7,2 

where each node is represented by two number separated by a comma: <value>,<level>, and the nodes are separated by semicolons. The leve is a negative number when the node is a left child.

To deserialize, we deliminate the string first by semicolons and then by commas. We then created a stack. We traverse the nodes in the string and create the nodes. The leve of the node just created is compared with that of the node at the top of the stack:

1) The node is pushed into the stack if 
   i)  the stack is tmpty
   ii) the leve of the node at the top of the stack is lower than the leve of the current node. In this case the current node is added as the child of the node at the top of the stack before being pushed into the stack.
2) The node at the top of the stack is popped if its level is higher or equal to the level of the current node

After clone the project use the following command to build:

mvn clean install

Use the following command to run the program

java -jar target/serialize-deserialize-tree-0.0.1-SNAPSHOT.jar

