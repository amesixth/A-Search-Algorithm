// @author Karla Jacquelin Guzman Sanchez

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Main extends JFrame {
    
    int c = 20;
    JTextArea lOpen, lClosed;
    String lO=" ", lC=" ";
    
    public Main(int w, int h) throws InterruptedException{
        
        int[][] mtx = new int[30][30];
        for(int i = 0; i < mtx.length ; i++){
            for(int j = 0; j < mtx[0].length ; j++){
                mtx[i][j] = Integer.MAX_VALUE;
            }
        }
        
        mtx[6][4] = -1; mtx[7][4] = -1; mtx[8][4] = -1; mtx[9][4] = -1; mtx[10][4] = -1; mtx[11][4] = -1; mtx[12][4] = -1; mtx[13][4] = -1;
        mtx[14][4] = -1; mtx[15][4] = -1; mtx[16][4] = -1; mtx[17][4] = -1; mtx[18][4] = -1; mtx[19][4] = -1; mtx[20][4] = -1; mtx[21][4] = -1;
        mtx[10][5] = -1; mtx[10][6] = -1; mtx[10][7] = -1; mtx[10][8] = -1; mtx[10][9] = -1; mtx[10][10] = -1; mtx[10][11] = -1;
        
        A_Star a = new A_Star(mtx, 12, 1, (int) (Math.random()*(mtx.length - 1)), (int) (Math.random()*(mtx[0].length - 1)));
        //A_Estrella a = new A_Estrella(w,h);
        
        a.setPreferredSize(new Dimension(w*c,h*c));
        JScrollPane spA=new JScrollPane(a);
        
        JPanel pd = new JPanel();
        pd.setPreferredSize(new Dimension(200,h*c+c*2));
        pd.setLayout(new GridLayout(4,0));
        
        lOpen = new JTextArea(100,50);
        lOpen.setPreferredSize(new Dimension(200,50));
        lClosed = new JTextArea(100,50);
        lClosed.setPreferredSize(new Dimension(200,50));
        for(int i = 0; i < a.openList.size() ; i++){
            Node aux = a.openList.get(i);
            lO = aux.id+",";
        }
        lOpen.setText(lC);
            
        for(int i = 0; i < a.closedList.size() ; i++){
            Node aux = a.closedList.get(i);
            lC = lC + aux.id+",";
        }
        lClosed.setText(lC);

        JScrollPane spOpen=new JScrollPane(lOpen);
        pd.add(spOpen);
        JScrollPane spClosed=new JScrollPane(lClosed);
        pd.add(spClosed);
        
        pd.add(new JLabel("Open List:  id: (h - g - f)"));
        pd.add(spOpen);
        pd.add(new JLabel("Closed List:"));
        pd.add(spClosed);
        
        this.setLayout(new BorderLayout());
        this.add(pd, BorderLayout.EAST);
        this.add(spA, BorderLayout.CENTER);
        
        this.setBounds(new Rectangle(w*c+c*2+200,h*c+c*3));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        while(!a.found){
            a.repaint();
            
            Thread.sleep(200);
            lO = lC = "";
            for(int i = 0; i < a.openList.size() ; i++){
                Node aux = a.openList.get(i);
                lO = lO + aux.id +": ("+String.format("%.2f", aux.getH())+" - "+String.format("%.2f", aux.getG())+" - "+String.format("%.2f", aux.getF())+ ")\n";
            }
            lOpen.setText(lO);
            
            for(int i = 0; i < a.closedList.size() ; i++){
                Node aux = a.closedList.get(i);
                lC = lC + aux.id +": ("+String.format("%.2f", aux.getH())+" - "+String.format("%.2f", aux.getG())+" - "+String.format("%.2f", aux.getF())+ ")\n";
            }
            lClosed.setText(lC);
        }
        
        this.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent we) {
                a.repaint();
            }
        });
        
    }
    
    public static void main(String[] args) throws InterruptedException {
        Main w = new Main(30,30);
    }

}
