import java.io.*;
import java.util.*;

public class Trie 
{   
    public Node root;
    private Node last_trie_node;

    public Trie()
    {   root = new Node(null);
        last_trie_node = root;
        //root doesn't have any children (yet)
    }

    private ArrayList<Node> convertWord(String word)
    {   
        ArrayList<Node> node_array = new ArrayList<Node>();
        for (int i=0; i<word.length(); i++)
        {   Node letter_node = new Node(word.substring(i,i+1));
            node_array.add(letter_node);
        }
        return node_array;
    }

    private void addWord(String word, Node last_trie_node, int word_depth)
    {   
        ArrayList<Node> node_array = convertWord(word);

        last_trie_node.setChild(node_array.get(word_depth));
        for (int i=word_depth; i<(node_array.size()-1); i++) 
        {   node_array.get(i).setChild(node_array.get(i+1));
        }
        //add a null character to the last node
        Node last_node_array = node_array.get(node_array.size()-1); 
        last_node_array.setData(last_node_array.getData()+"\0");
    }

    public boolean isMember(String word, int word_depth, Node trie_node)
    {   
        //convert word to node_array
        ArrayList<Node> node_array = convertWord(word);
        
        //for each child at the trie_node
        ArrayList<Node> trie_node_children = trie_node.getChildren();
        for (int i=0; i<=trie_node_children.size(); i++) //be careful here...
        {   
            //if there are no matches
            if (i == trie_node_children.size())
            {   
                //if trie_node is the root
                if (trie_node.getData() == null)
                {
                    if (word_depth == 0)
                    {   addWord(word, root, 0);
                        return false;
                    }
                    addWord(word, last_trie_node, word_depth);
                    return false;
                }
                return isMember(word, word_depth, root);
            }

            //if one of the children matches at word_depth
            if (trie_node_children.get(i).getData().substring(0,1).equals(node_array.get(word_depth).getData().substring(0,1)))
            {   
                last_trie_node = trie_node_children.get(i);

                //if the word_depth is terminal
                if (word_depth == (node_array.size()-1) )
                {   
                    //if the trie_node child is terminal 
                    if (trie_node_children.get(i).getData().length() > 1)
                    {   return true;
                    }
                    else
                    {   addWord(word, last_trie_node, word_depth);
                        return false;
                    }
                }
                //if the trie_node has no children
                if (trie_node_children.get(i).getChildren().size() == 0)
                {   return isMember(word, word_depth+1, root);
                }
                // if the trie_node has children
                return isMember(word, word_depth+1, trie_node_children.get(i));
            }
        }
        System.out.println("Something went wrong.");
        return false;
    }
}
