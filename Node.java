// @author Karla Jacquelin Guzman Sanchez

public class Node {
    
    int x,y,id;
    private float h, g, f;
    private Node parent;
    
    public Node(int id, int x, int y, float h, float g, float f, Node parent){
        this.x = x;
        this.y = y;
        this.h = h;
        this.g = g;
        this.f = f;
        this.parent = parent;
        this.id = id;
    }
    
    public void replace(Node n){
        this.x = n.x;
        this.y = n.y;
        this.h = n.h;
        this.g = n.g;
        this.f = n.f;
        this.parent = n.parent;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setH(float h){
        this.h = h;
    }
    
    public void setG(float g){
        this.g = g;
    }
    
    public void setF(float f){
        this.f = f;
    }
    
    public void setParent(Node parent){
        this.parent = parent;
    }
    
    public float getH(){
        return h;
    }
    
    public float getG(){
        return g;
    }
    
    public float getF(){
        return f;
    }
    
    public Node getParent(){
        return parent;
    }

}
