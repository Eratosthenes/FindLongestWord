//only relies on Node.java
//to run, use: java FindLongestWord [file name]
import java.util.*;
import java.io.*;

public class FindLongestWord
{
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        //add the contents of the file to the ArrayList words
        ArrayList<String> words = new ArrayList<String>();
        BufferedReader r = new BufferedReader(new FileReader(args[0]));
        String word;
        while ((word = r.readLine()) != null)
        {   words.add(word);   
        }

        //sort words in ascending length
        Collections.sort(words, new Comparator<String>() 
        {   
            public int compare(String s1, String s2) 
            {   return (s1.length() > s2.length()) ? 1 : 0;
            }
        });
        
        //create variables
        Node<String> root = new Node<String>(null);
        Node<String> trie_node = new Node<String>();
        Node<String> last_trie_node = new Node<String>();
        ArrayList<String> conglomerates = new ArrayList<String>();
        int word_depth;

        //iterate through the words
        for (int i=0; i<words.size(); i++)
        {   
            word = words.get(i);
            word_depth = 0;
            trie_node = last_trie_node = root;

            //create a node array for the letters of the word
            ArrayList<Node<String>> node_array = new ArrayList<Node<String>>();
            for (int j=0; j<word.length(); j++)
            {   node_array.add(new Node<String>(word.substring(j,j+1)));  
            }

            //check the children of trie_node for matches
            ArrayList<Node<String>> trie_node_children = trie_node.getChildren();
            for (int j=0; ; j++) 
            {   
                //if none of the children matched 
                if (j==trie_node_children.size()) 
                {   
                    //if trie_node is the root
                    if (trie_node.getData() == null)
                    {   
                        //add word to the trie, starting at last_trie_node
                        last_trie_node.setChild(node_array.get(word_depth));
                        for (int k=word_depth; k<(node_array.size()-1); k++) 
                        {   node_array.get(k).setChild(node_array.get(k+1));
                        }
                        //add a null character to the last node
                        Node<String> last_node_array = node_array.get(node_array.size()-1); 
                        last_node_array.setData(last_node_array.getData()+"\0");
                        break;
                    }
                    trie_node = root;
                    trie_node_children = trie_node.getChildren();
                    j = -1;
                    continue;
                }

                //if one of the children matches at word depth
                if (trie_node_children.get(j).getData().substring(0,1).equals(node_array.get(word_depth).getData().substring(0,1)))
                {   
                    //if the letter is the last one in the word
                    if (word_depth == (node_array.size()-1) )
                    {   
                        //if trie node is the end of a word
                        if (trie_node_children.get(j).getData().length()>1)
                        {   conglomerates.add(word); 
                            break; 
                        }
                        else
                        {
                            //add word to the trie, starting at last_trie_node
                            last_trie_node.setChild(node_array.get(word_depth));
                            for (int k=word_depth; k<(node_array.size()-1); k++) 
                            {   node_array.get(k).setChild(node_array.get(k+1));
                            }
                            //add null character to the last node
                            Node<String> last_node_array = node_array.get(node_array.size()-1); 
                            last_node_array.setData(last_node_array.getData()+"\0");
                            break;
                        }
                    }
                    word_depth++;
                    trie_node = trie_node_children.get(j);
                    last_trie_node = trie_node;
                    j=-1;

                    //if the trie node has no children
                    if (trie_node.getChildren().size() == 0)
                    {   
                        trie_node = root;
                        trie_node_children = trie_node.getChildren();
                    }
                    else
                    {   
                        trie_node_children = trie_node.getChildren();
                    }
                }
            }

        }
        
        System.out.println("Number of conglomerates: "+conglomerates.size());
        System.out.println("Longest conglomerate: "+conglomerates.get(conglomerates.size()-1));
        System.out.println("Second-longest conglomerate: "+conglomerates.get(conglomerates.size()-2));
    }
}
