import java.util.*;

public class Node
{   
    private String data;
    private ArrayList<Node> children;
 
    public Node(String data)
    {   this.data = data;
        this.children = new ArrayList<Node>();
    }

    public String getData() 
    {   return data; 
    }

    public ArrayList<Node> getChildren()
    {   return children; 
    }
 
    public void setData(String data) 
    {   this.data = data; 
    }
    
    public void setChild(Node child)
    {   this.children.add(child);
    }
}
