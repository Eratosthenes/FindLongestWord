//alternative solution; only relies on Trie.java
//to run, use: java FindLongestWord2[file name]
import java.util.*;
import java.io.*;

public class FindLongestWord2
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
        ArrayList<String> conglomerates = new ArrayList<String>();
        Trie myTrie = new Trie();

        //iterate through the words
        for (int i=0; i<words.size(); i++)
        {   
            word = words.get(i);
            if (myTrie.isMember(word, 0, myTrie.root))
            {   conglomerates.add(word);
            }
        }

        System.out.println("Number of conglomerates: "+conglomerates.size());
        System.out.println("Longest conglomerate: "+conglomerates.get(conglomerates.size()-1));
        System.out.println("Second-longest conglomerate: "+conglomerates.get(conglomerates.size()-2));

    }
}
