// @author Karla Jacquelin Guzman Sanchez

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class A_Star extends JPanel {
    
    List<Node> openList = new ArrayList<>();
    List<Node> closedList = new ArrayList<>();
    
    int mtx[][];
    int c = 20, xI, yI, xF, yF, id = 1;
    
    boolean found = false;
    
    public A_Star(int w, int h){
        mtx = new int[w][h];
        
        for(int i = 0; i < mtx.length ; i++){
            for(int j = 0; j < mtx[0].length ; j++){
                mtx[i][j] = Integer.MAX_VALUE;
            }
        }
        
        for(int i = 0; i < 70; i++){
            mtx[(int) (Math.random()*(mtx.length - 1))][(int) (Math.random()*(mtx[0].length - 1))] = -1;
        }
        
        xI = (int) (Math.random()*(mtx.length - 1)); yI = (int) (Math.random()*(mtx[0].length - 1));
        mtx[xI][yI] = 0;
        
        xF = (int) (Math.random()*(mtx.length - 1)); yF = (int) (Math.random()*(mtx[0].length - 1));
        
        openList.add(new Node(1, xI, yI, getH(xI,yI), getG(xI,yI), getF(xI,yI), null));
        id++;
        
    }

    public A_Star(int[][] mtx){
        this.mtx = mtx;
        
         for(int i = 0; i < 70; i++){
            this.mtx[(int) (Math.random()*(mtx.length - 1))][(int) (Math.random()*(mtx[0].length - 1))] = -1;
        }
        
        xI = (int) (Math.random()*(mtx.length - 1)); yI = (int) (Math.random()*(mtx[0].length - 1));
        this.mtx[xI][yI] = 0;
        
        xF = (int) (Math.random()*(mtx.length - 1)); yF = (int) (Math.random()*(mtx[0].length - 1));
        
        openList.add(new Node(1, xI, yI, getH(xI,yI), getG(xI,yI), getF(xI,yI), null));
        id++;
    }
    
    public A_Star(int[][] mtx, int xI, int yI, int xF, int yF){
        this.mtx = mtx;
        
         for(int i = 0; i < 70; i++){
            this.mtx[(int) (Math.random()*(mtx.length - 1))][(int) (Math.random()*(mtx[0].length - 1))] = -1;
        }
        
        this.xI = xI; this.yI = yI;
        this.mtx[xI][yI] = 0;
        
        this.xF = xF; this.yF = yF;
        
        openList.add(new Node(1, xI, yI, getH(xI,yI), getG(xI,yI), getF(xI,yI), null));
        id++;
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponents(g);
        g.setColor(Color.BLACK);
        g.setFont( new Font("Tahoma", Font.LAYOUT_LEFT_TO_RIGHT, 10) );

        // Initial Matrix
        for(int i = 0; i < mtx.length ; i++){
            for(int j = 0; j < mtx[0].length ; j++){
                g.drawRect(i*c, j*c, c, c);
                
                if(mtx[i][j] == -1)
                    g.fillRect(i*c, j*c, c, c);
                
                if(i == xI && j == yI){
                    g.setColor(Color.red);
                    g.fillRect(i*c, j*c, c, c);
                    g.setColor(Color.BLACK);
                    g.drawString("PI", xI*c+5, yI*c+(3*c/6));
                }
                
                if(i == xF && j == yF){
                    g.setColor(Color.red);
                    g.fillRect(i*c, j*c, c, c);
                    g.setColor(Color.BLACK);
                    g.drawString("PF", xF*c+5, yF*c+(3*c/6));
                }
            }
        }
        
        if(!found){
            explore();
            if(inClosedList(xF,yF)) found = true;
        }        
        
        pintar(g);
        // Grid
        for(int i = 0 ; i < mtx.length ; i++){
            for(int j = 0 ; j < mtx[0].length ; j++){
                g.drawRect(i*c, j*c, c, c);
            }
        }
        
        if(found){
            makePath(g);
        }
    }
    
    public void explore(){
        order();

        int x = openList.get(0).x;
        int y = openList.get(0).y;
        
        closedList.add(openList.get(0));
        
        // Up
        Node up = new Node(id, x, y-1, getH(x,y-1), getG(x,y-1), getF(x,y-1), openList.get(0));
        if(y-1 >= 0 && y < mtx[0].length && x < mtx.length && x >= 0 && mtx[x][y-1] != -1 && !inClosedList(up)){
            if(inOpenList(up)!= -1){
                if(openList.get(inOpenList(up)).getF() > up.getF())
                    openList.get(inOpenList(up)).setF(up.getF());
            }else{
                openList.add(up);
                id++;
            }
        }
        
        // Down
        Node down = new Node(id, x, y+1, getH(x,y+1), getG(x,y+1), getF(x,y+1), openList.get(0));
        if( y+1 < mtx[0].length && x < mtx.length && x >= 0 && y >= 0 && mtx[x][y+1] != -1 && !inClosedList(down)){
            if(inOpenList(down)!= -1){
                if(openList.get(inOpenList(down)).getF() > down.getF())
                    openList.get(inOpenList(down)).setF(down.getF());
            }else{
                openList.add(down);
                id++;
            }
        }
        
        // Left
        Node left = new Node(id, x-1, y, getH(x-1,y), getG(x-1,y), getF(x-1,y), openList.get(0));
        if(x-1 >= 0 && x < mtx[0].length && y < mtx[0].length && y >= 0 && mtx[x-1][y] != -1 && !inClosedList(left)){
            if(inOpenList(left)!= -1){
                if(openList.get(inOpenList(left)).getF() > left.getF())
                    openList.get(inOpenList(left)).setF(left.getF());
            }else{
                openList.add(left);
                id++;
            }
        }
        
        // Right
        Node right = new Node(id, x+1, y, getH(x+1,y), getG(x+1,y), getF(x+1,y), openList.get(0));
        if( x+1 < mtx.length && y < mtx[0].length && x >= 0 && y >= 0 && mtx[x+1][y] != -1 && !inClosedList(right)){
            if(inOpenList(right)!= -1){
                if(openList.get(inOpenList(right)).getF() > right.getF())
                    openList.get(inOpenList(right)).setF(right.getF());
            }else{
                openList.add(right);
                id++;
            }
        }
        
        // Up-Left
        Node upLeft = new Node(id, x-1, y-1, getH(x-1,y-1), getG(x-1,y-1), getF(x-1,y-1), openList.get(0));
        if(x-1 >= 0 && x < mtx[0].length && y < mtx[0].length && y-1 >= 0 && mtx[x-1][y-1] != -1 && !inClosedList(upLeft)){
            if(inOpenList(upLeft)!= -1){
                if(openList.get(inOpenList(upLeft)).getF() > upLeft.getF())
                    openList.get(inOpenList(upLeft)).setF(upLeft.getF());
            }else{
                openList.add(upLeft);
                id++;
            }
        }
        
        // Up-Right
        Node upRight = new Node(id, x+1, y-1, getH(x+1,y-1), getG(x+1,y-1), getF(x+1,y-1), openList.get(0));
        if( x+1 < mtx.length && x >= 0 && y-1 >= 0 && y < mtx[0].length && mtx[x+1][y-1] != -1 && !inClosedList(upRight)){
            if(inOpenList(upRight)!= -1){
                if(openList.get(inOpenList(upRight)).getF() > upRight.getF())
                    openList.get(inOpenList(upRight)).setF(upRight.getF());
            }else{
                openList.add(upRight);
                id++;
            }
        }
        
        // Down-Left
        Node downLeft = new Node(id, x-1, y+1, getH(x-1,y+1), getG(x-1,y+1), getF(x-1,y+1), openList.get(0));
        if(x-1 >= 0 && x < mtx[0].length && y+1 < mtx[0].length && y >= 0 && mtx[x-1][y+1] != -1 && !inClosedList(downLeft)){
            if(inOpenList(downLeft)!= -1){
                if(openList.get(inOpenList(downLeft)).getF() > downLeft.getF())
                    openList.get(inOpenList(downLeft)).setF(downLeft.getF());
            }else{
                openList.add(downLeft);
                id++;
            }
        }
        
        // Down-Right
        Node downRight = new Node(id, x+1, y+1, getH(x+1,y+1), getG(x+1,y+1), getF(x+1,y+1), openList.get(0));
        if( x+1 < mtx.length && y+1 < mtx[0].length && x >= 0 && y >= 0 && mtx[x+1][y+1] != -1 && !inClosedList(downRight)){
            if(inOpenList(downRight)!= -1){
                if(openList.get(inOpenList(downRight)).getF() > downRight.getF())
                    openList.get(inOpenList(downRight)).setF(downRight.getF());
            }else{
                openList.add(downRight);
                id++;
            }
        }
        
        openList.remove(0);
    }

    // paint
    public void pintar(Graphics g){
        
        for(int i = 0 ; i < mtx.length ; i++){
            for(int j = 0 ; j < mtx[0].length ; j++){
                g.setColor(Color.WHITE);
                g.fillRect(i*c, j*c, c, c);
                g.setColor(Color.BLACK);
                g.drawRect(i*c, j*c, c, c);
                
                if(mtx[i][j] == -1)
                    g.fillRect(i*c, j*c, c, c);
                
                if(i == xI && j == yI){
                    g.setColor(Color.red);
                    g.fillRect(i*c, j*c, c, c);
                    g.setColor(Color.BLACK);
                    g.drawString("PI", xI*c+5, yI*c+(3*c/6));
                }
                
                if(i == xF && j == yF){
                    g.setColor(Color.red);
                    g.fillRect(i*c, j*c, c, c);
                    g.setColor(Color.BLACK);
                    g.drawString("PF", xF*c+5, yF*c+(3*c/6));
                }
            }
        }
        
        for(int i = 0 ; i < openList.size() ; i++){
            Node aux = openList.get(i);
            g.setColor(Color.BLUE);
            g.fillRect(aux.x*c, aux.y*c, c, c);
            g.setColor(Color.WHITE);
            g.drawString(String.format("%d", aux.id), aux.x*c+c/6, aux.y*c+(3*c/6));
        }
        for(int i = 0 ; i < closedList.size() ; i++){
            Node aux = closedList.get(i);
            g.setColor(Color.ORANGE);
            g.fillRect(aux.x*c, aux.y*c, c, c);
            g.setColor(Color.WHITE);
            g.drawString(String.format("%d", aux.id), aux.x*c+c/6, aux.y*c+(3*c/6));
        }
        g.setColor(Color.BLACK);
    }
    
    public void makePath(Graphics g){
        Node f = closedList.get(closedList.size()-1);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(3));
        while(f != null){
            g.setColor(Color.RED);
            g.drawRect(f.x*c, f.y*c, c, c);
            g.setColor(Color.BLACK);
            g.drawString(f.id+"", f.x*c+5, f.y*c+(3*c/6));
            
            if(f.x == xI && f.y == yI){
                g.setColor(Color.RED);
                g.fillRect(f.x*c, f.y*c, c, c);
                g.setColor(Color.BLACK);
                g.drawString("I,"+f.id, f.x*c+5, f.y*c+(3*c/6));
            }
            if(f.x == xF && f.y == yF){
                g.setColor(Color.RED);
                g.fillRect(f.x*c, f.y*c, c, c);
                g.setColor(Color.BLACK);
                g.drawString("F,"+f.id, f.x*c+5, f.y*c+(3*c/6));
            }
            f = f.getParent();
        }
        g2d.setStroke(new BasicStroke(1));
    }
    
    public void order(){
        
        for (int i = 0; i < openList.size(); i++){
            for (int j = i + 1; j < openList.size(); j++) { 
                if (openList.get(i).getF() > openList.get(j).getF()) {
                    Node temp = openList.get(i);
                    openList.set(i, openList.get(j));
                    openList.set(j, temp);
                }
            }
        }
    }
    
    public boolean inClosedList(Node n){
        for(int i = 0 ; i < closedList.size() ; i++){
            if(closedList.get(i).x == n.x && closedList.get(i).y == n.y) return true;
        }
        
        return false;
    }
    
    public boolean inClosedList(int x, int y){
        for(int i = 0 ; i < closedList.size() ; i++){
            if(closedList.get(i).x == x && closedList.get(i).y == y) return true;
        }
        
        return false;
    }
    
    public int inOpenList(Node n){
        for(int i = 0 ; i < openList.size() ; i++){
            if(openList.get(i).x == n.x && openList.get(i).y == n.y) return i;
        }
        
        return -1;
    }
    
    public float getG(int x, int y){
        return dist(x,y,xI,yI);
    }
    
    public float getH(int x, int y){
        return dist(x,y,xF,yF);
    }
    
    public float getF(int x, int y){
        return getH(x,y) + getG(x,y);
    }
    
    public float dist(int x1, int y1, int x2, int y2){
        return (float) Math.sqrt( Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2) );
    }
} 
