package d8cnpm;
import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

/**
 * Created by NgocKute on 22/04/2016.
 */
public class d8cnpm extends JFrame implements ActionListener {
    JButton N1=new JButton("nut 1");
    JButton N2=new JButton("nut 2");
    JLabel lb1=new JLabel("Nhập a:");
    JLabel lb2=new JLabel("Nhập b:");
    JLabel lb3=new JLabel("Nhập c:");
    JLabel lb4=new JLabel("x1:");
    JLabel lb5=new JLabel("x2:");

    JTextField tx1=new JTextField(10);
    JTextField tx2=new JTextField(10);
    JTextField tx3=new JTextField(10);
    JTextField tx4=new JTextField(10);
    JTextField tx5=new JTextField(10);

    JButton kq=new JButton("Kết Quả");
    JButton nl=new JButton("Nhập Lại");
    public d8cnpm()
    {
        //super luôn để trên đầu
        super("Hello");
        setSize(400,200);//kích thước của khung frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//khi đóng thoát khỏi ứng dụng
        setVisible(true);//true để hiện khung,để false nó k hiện

        JPanel p=new JPanel();//khung con của khung lớn 1 khung có nhiều khung con
        add(p);//add vào khung chính
      /*  JButton b1= new JButton("Nút ấn");
        JButton b2=new JButton("Nút 2");
        p.add(b1);//them  b1 vào khung con p
        p.add(b2);
    //----------------------------------------------------------------------//
        //textbox
        JTextField txt =new JTextField(50);
        //int a=Integer.parseInt(txt.getText());
       // double b= Double.parseDouble(txt.getText());
        txt.setText("Họ và tên");
        Color c= new Color(100,200,50);
        txt.setForeground(c);
        txt.setBackground(Color.lightGray);
        txt.setEditable(false);
        p.add(txt);
    //----------------------------------------------------------------------//
        //texArea
        JTextArea ta=new JTextArea(5,10);
        JScrollPane sp=new JScrollPane(ta);
        p.add(sp);

        JCheckBox ck=new JCheckBox("hi");

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        JMenuBar mb= new JMenuBar();
        setJMenuBar(mb);

        JMenu m1=new JMenu("file");
        JMenu m2=new JMenu("Edit");
        mb.add(m1);
        mb.add(m2);

        JMenuItem mi1=new JMenuItem("Open");
        JMenuItem mi2=new JMenuItem("Save");
        JMenuItem mi3=new JMenuItem("Save as");
        m1.add(mi1);m1.add(mi2);m1.add(mi3);

        JMenuItem mi11=new JMenuItem("Copy");
        JMenuItem mi21=new JMenuItem("Pase");
        JMenuItem mi31=new JMenuItem("Delete");
        m2.add(mi11);m2.add(mi21);m2.add(mi31);


//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        //chuong trinh luon chay cai dialog truoc sau do cac doan code khac moi dc thuc thi
       String st=JOptionPane.showInputDialog(null,"Nhap ho ten");
       int st2=JOptionPane.showConfirmDialog(null,"ban that su muon xoa file","Confirm Dialog",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
        String tmp="";
        if(st2==0) {
            tmp = "YES";
            JOptionPane.showMessageDialog(null,"file da bi xoa");
        }
        if(st2==1)tmp="NO";
        if(st2==2)tmp="CANCEL";
        JButton b=new JButton(tmp);
        p.add(b);



        JComboBox cb=new JComboBox();
        cb.addItem("PHP");
        cb.addItem("C#");
        cb.addItem(st);
        p.add(cb);


        //nut an
        JLabel lb=new JLabel("giao diện xử lý sự kiện:");
        p.add(lb);
        p.add(N1);
        p.add(N2);
        N1.addActionListener(this);
        N2.addActionListener(this);
        */
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        //tinh phuong trinh bac 2
        p.setLayout(new FlowLayout());
        p.setLayout(new GridLayout(0,2,5,5));//0:hàng 2:số cột,5:khoang cach giua cac hàng,5:khoang cach giữa các cột của mỗi hàng,số 0 có nghĩa là số hàng không bị giới hạn


        p.add(lb1);p.add(tx1);
        p.add(lb2);p.add(tx2);
        p.add(lb3);p.add(tx3);
        p.add(lb4);p.add(tx4);
        p.add(lb5);p.add(tx5);
        p.add(kq);p.add(nl);

        kq.addActionListener(this);
        nl.addActionListener(this);
        tx4.setEditable(false);
        tx5.setEditable(false);
    }
    public void actionPerformed(ActionEvent a)
    {
        JButton nguon=(JButton) a.getSource();
        /*if(nguon==N1) setTitle("Nhan nut 1");
        if(nguon==N2) setTitle("Nhan nut 2");*/
        NumberFormat nf=NumberFormat.getInstance();
        nf.setMaximumFractionDigits(5);
        if(nguon==kq)
        {
            double s1=Double.parseDouble(tx1.getText());
            double s2=Double.parseDouble(tx2.getText());
            double s3=Double.parseDouble(tx3.getText());

            double delta=s2*s2-4*s1*s3;
            double x1=0,x2=0;
            if(delta>0)
            {
                x1=(-s2+Math.sqrt(delta))/(2*s1);
                x2=(-s2-Math.sqrt(delta))/(2*s1);
                tx4.setText(nf.format(x1));
                tx5.setText(nf.format(x1));
            }
            else if(delta==0)
            {
                x1 =(-s2)/(2*s1);
                tx4.setText("phương trinh có nghiệm kép");
                tx5.setText(x1+"");
            }
            else
            {
                tx4.setText("phương trinh vô nghiệm");
                tx5.setText("phương trinh vô nghiệm");
            }
        }
        if(nguon==nl)
        {
            tx1.setText("");
            tx2.setText("");
            tx3.setText("");
            tx4.setText("");
            tx5.setText("");
        }
    }



    public static void main(String[]args)
    {

        d8cnpm demo=new d8cnpm();
    }

}
