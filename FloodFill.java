import java.awt.*;
import java.awt.Robot;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.*;
import javax.swing.border.*;
import java.io.*;
/**
 * @author Leigh Rubin
 * Assignment #10: GameOfLife.java
 * Conway's Game Of Life
 */
public class FloodFill implements ActionListener
{
    private JFrame f = new JFrame("Flood Fill");
    private int x = 10;
    private int y = 10;
    private final int WIDTH;
    private final int HEIGHT;
    private int moves = 0;
    private JButton[][] lab;
    private ArrayList<JLabel> labels = new ArrayList<JLabel>();
    private ArrayList<JButton> colors = new ArrayList<JButton>();
    private Color[] c = {Color.red,Color.green,Color.orange,Color.blue,Color.cyan,Color.magenta};
    private Color co;
    private String type = "";
    private int h = 99999;
    private JButton reset = new JButton("Play Again");
    private Stack<JButton> j = new Stack<JButton>();

    private String[] options = {"Beginner", "Intermediate", "Advanced", "Expert"};
    private JOptionPane o = new JOptionPane();
    private int selected = o.showOptionDialog(null, null, "Level Select", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("bucket.png"), options, null);

    private String n = "" + (String)(o.showInputDialog(null, "Enter you name here", "Who is playing?", JOptionPane.QUESTION_MESSAGE, new ImageIcon("bucket.png"), null, null));
    public FloodFill()
    {
        try
        {
            if(selected == 0){x = 5; y = 5;type = "beginner";Scanner sc = new Scanner(new File("levels\\" + type + ".txt"));h = Integer.parseInt(sc.nextLine());}
            else if(selected == 1){x = 10; y = 10;type = "intermediate";Scanner sc = new Scanner(new File("levels\\" + type + ".txt"));h = Integer.parseInt(sc.nextLine());}
            else if(selected == 2){x = 20; y = 20;type = "advanced";Scanner sc = new Scanner(new File("levels\\" + type + ".txt"));h = Integer.parseInt(sc.nextLine());}
            else if(selected == 3){x = 30; y = 30;type = "expert";Scanner sc = new Scanner(new File("levels\\" + type + ".txt"));h = Integer.parseInt(sc.nextLine());}
        }
        catch(Exception e){}
        lab = new JButton[x][y];
        WIDTH = 700/x;
        HEIGHT = 700/y;

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.pack();
        f.setVisible(true);
        f.setSize(new Dimension(1040,825));
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setDefaultLookAndFeelDecorated(true);
        f.setContentPane(new JLabel(new ImageIcon("background.gif")));

        Random gen = new Random();
        for(int i = 0; i < lab.length; i++)
        {
            for(int j = 0; j < lab[i].length; j++)
            {
                lab[i][j] = new JButton();
                lab[i][j].setBounds(i*WIDTH,j*HEIGHT,WIDTH,HEIGHT);
                lab[i][j].setBackground(c[gen.nextInt(6)]);
                f.add(lab[i][j]);
                lab[i][j].setEnabled(false);
            }
        }

        addButtons();
        addLabels();
        f.repaint();
    }

    public void actionPerformed(ActionEvent e)
    {
        Object temp = e.getSource();
        JButton b = (JButton)temp;
        if(b.getText().equals("Play Again"))
        {
            replay();
        }
        else
        {
            co = lab[0][0].getBackground();
            fill(b.getBackground(),0,0);
            for(JButton t: colors)
                t.setEnabled(true);
            changeLabels();
            b.setEnabled(false);
            boolean same = true;
            Color col = lab[0][0].getBackground();
            outer:for(JButton[] x: lab)
            {
                for(JButton x2: x)
                {
                    if(x2.getBackground() != col){
                        same = false;
                        break outer;
                    }
                }
            }
            if(same){
                for(JButton t: colors)
                    t.setEnabled(false);
                reset.setVisible(true);
                if(moves < h){
                    try{ 
                        File fold=new File("levels\\" + type + ".txt");
                        fold.delete();
                        PrintWriter writer = new PrintWriter("levels\\" + type+ ".txt", "UTF-8");
                        writer.println(Integer.toString(moves));
                        writer.close();
                        h = moves;
                    }catch(IOException x){}
                }
            }
            //color(b.getBackground());
        }
    }

    public void fill(Color c, int a, int b)
    {
        //j.push(lab[x][y]);
        lab[a][b].setBackground(c);
        if(a+1 < x && lab[a+1][b].getBackground() == co){
            fill(c,a+1,b);
        }
        if(b+1 < y && lab[a][b+1].getBackground() == co){
            fill(c,a,b+1);
        }
        if(a-1 >= 0 && lab[a-1][b].getBackground() == co){
            fill(c,a-1,b);
        }
        if(b-1 >= 0 && lab[a][b-1].getBackground() == co){
            fill(c,a,b-1);
        }
    }

    public void color(Color c)
    {
        while(!(j.empty()))
        {
            JButton temp = j.pop();
            temp.setEnabled(true);
            temp.setBackground(c);
            temp.setEnabled(false);
            f.repaint();
        }
    }

    public void addButtons()
    {
        JButton red = new JButton();
        red.setBackground(Color.red);
        red.setBounds(0,700,116,100);
        red.setBorder(new LineBorder(Color.black, 8));
        red.addActionListener(this);
        JButton orange = new JButton();
        orange.setBackground(Color.orange);
        orange.setBounds(116,700,117,100);
        orange.setBorder(new LineBorder(Color.black, 8));
        orange.addActionListener(this);
        JButton green = new JButton();
        green.setBackground(Color.green);
        green.setBounds(233,700,117,100);
        green.setBorder(new LineBorder(Color.black, 8));
        green.addActionListener(this);
        JButton blue = new JButton();
        blue.setBackground(Color.blue);
        blue.setBounds(350,700,116,100);
        blue.setBorder(new LineBorder(Color.black, 8));
        blue.addActionListener(this);
        JButton cyan = new JButton();
        cyan.setBackground(Color.cyan);
        cyan.setBounds(466,700,117,100);
        cyan.setBorder(new LineBorder(Color.black, 8));
        cyan.addActionListener(this);
        JButton magenta = new JButton();
        magenta.setBackground(Color.magenta);
        magenta.setBounds(583,700,117,100);
        magenta.setBorder(new LineBorder(Color.black, 8));
        magenta.addActionListener(this);

        f.add(red);
        colors.add(red);
        f.add(orange);
        colors.add(orange);
        f.add(green);
        colors.add(green);
        f.add(blue);
        colors.add(blue);
        f.add(cyan);
        colors.add(cyan);
        f.add(magenta);
        colors.add(magenta);

        reset.setBounds(720,700,300,70);
        reset.setFont(new Font("Tahoma", Font.PLAIN, 32));
        reset.addActionListener(this);
        f.add(reset);
        reset.setVisible(false);

        f.repaint();
    }

    public void addLabels()
    {
        Color temp = lab[0][0].getBackground();

        JLabel moves = new JLabel("Current Moves");
        moves.setFont(new Font("Tahoma", Font.BOLD, 43));
        moves.setForeground(temp);
        moves.setBounds(710,10,400,100);
        labels.add(moves);
        f.add(moves);

        JLabel moves_ = new JLabel("0");
        moves_.setFont(new Font("Tahoma", Font.BOLD, 35));
        moves_.setForeground(temp);
        moves_.setBounds(858,55,200,100);
        labels.add(moves_);
        f.add(moves_);

        JLabel high = new JLabel("Best Moves");
        high.setFont(new Font("Tahoma", Font.BOLD, 45));
        high.setForeground(temp);
        high.setBounds(740,250,400,100);
        labels.add(high);
        f.add(high);

        JLabel high_ = new JLabel(Integer.toString(h));
        high_.setFont(new Font("Tahoma", Font.BOLD, 35));
        high_.setForeground(temp);
        high_.setBounds(858 - (high_.getText().length()*4),295,200,100);
        labels.add(high_);
        f.add(high_);

        JLabel name = new JLabel("Player Name");
        name.setFont(new Font("Tahoma", Font.BOLD, 45));
        name.setForeground(temp);
        name.setBounds(730,490,400,100);
        labels.add(name);
        f.add(name);

        JLabel name_ = new JLabel(n);
        name_.setFont(new Font("Tahoma", Font.BOLD, 35));
        name_.setForeground(temp);
        name_.setBounds(858 - (name_.getText().length()*8),545,500,100);
        labels.add(name_);
        f.add(name_);
    }

    public void changeLabels()
    {
        for(JLabel j: labels)
        {
            j.setForeground(lab[0][0].getBackground());
        }
        moves++;
        labels.get(1).setText(Integer.toString(moves));
    }

    public void replay()
    {
        reset.setVisible(false);
        for(JButton t: colors)
            t.setEnabled(true);
        Random gen = new Random();
        for(int i = 0; i < lab.length; i++)
        {
            for(int j = 0; j < lab[i].length; j++)
            {
                lab[i][j].setBackground(c[gen.nextInt(6)]);
            }
        }
        for(JLabel j: labels)
        {
            j.setForeground(lab[0][0].getBackground());
        }
        moves = 0;
        labels.get(1).setText(Integer.toString(moves));
        labels.get(3).setText(Integer.toString(h));
    }

    public static void main(String[] args)
    {
        new FloodFill();
    }
}