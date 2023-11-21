package test;


import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class SwingNotepad extends JFrame implements ActionListener {
  // ��� ���� ����
  JTextArea ta; // ������ �� �ִ� ���� �� �Է¶�
  JMenuBar menubar; // �޴���
  JMenu fileMenu, editMenu; // ���� �޴�, ���� �޴�
  JMenuItem newItem, openItem, saveItem, exitItem;// �޴�������
  JMenuItem copyItem, cutItem, pasteItem, allItem, dateItem;

  //  ������ ����
  public SwingNotepad() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// X Ŭ�� => ����
    setTitle("���� �޸���");// ����
    ta = new JTextArea();// ta ����
    JScrollPane scroll = new JScrollPane(ta);// ��ũ�ѿ� ta �� �ִ´�.
    add(scroll);// ��ũ���� �������� �߾ӿ� ���δ�.
    // getContentPane().add(scroll); // JDK 1.4�� ����� �ڵ�
    menubar = new JMenuBar();// �޴��� ����
    setJMenuBar(menubar);// �޴��ٸ� ���δ�.
    fileMenu = new JMenu("����(F)");

    fileMenu.setMnemonic('F');//Alt + F
    menubar.add(fileMenu);// �޴��ٿ� ���� �޴� ���̱�
    newItem = new JMenuItem("���� �����");
    openItem = new JMenuItem("����");
    saveItem = new JMenuItem("����");
    exitItem = new JMenuItem("������");

 

    newItem.setAccelerator(KeyStroke.getKeyStroke('N',Event.CTRL_MASK));//Ctrl + N

    openItem.setAccelerator(KeyStroke.getKeyStroke('O',Event.CTRL_MASK));//Ctrl + O

    saveItem.setAccelerator(KeyStroke.getKeyStroke('S',Event.CTRL_MASK));//Ctrl + S

    exitItem.setAccelerator(KeyStroke.getKeyStroke('X',Event.CTRL_MASK));//Ctrl + X

 

    fileMenu.add(newItem);
    fileMenu.add(openItem);
    fileMenu.add(saveItem);
    fileMenu.addSeparator();// ���м� �ֱ�
    fileMenu.add(exitItem);
    newItem.addActionListener(this); // ������ ���̱�
    openItem.addActionListener(this);
    saveItem.addActionListener(this);
    exitItem.addActionListener(this);
    editMenu = new JMenu("����(E)");// ���� �޴� ����

    editMenu.setMnemonic('E');
    menubar.add(editMenu);// �޴��ٿ� ���� �޴� ���̱�
    copyItem = new JMenuItem("����");
    cutItem = new JMenuItem("�߶󳻱�");
    pasteItem = new JMenuItem("�ٿ��ֱ�");
    allItem = new JMenuItem("��� ����");
    dateItem = new JMenuItem("��¥/�ð�");

 

    copyItem.setAccelerator(KeyStroke.getKeyStroke('C',Event.CTRL_MASK));//Ctrl + C
    cutItem.setAccelerator(KeyStroke.getKeyStroke('X',Event.CTRL_MASK));//Ctrl + X
    pasteItem.setAccelerator(KeyStroke.getKeyStroke('V',Event.CTRL_MASK));//Ctrl + V
    allItem.setAccelerator(KeyStroke.getKeyStroke('A',Event.CTRL_MASK));//Ctrl + A
    dateItem.setAccelerator(KeyStroke.getKeyStroke("F5"));


    editMenu.add(copyItem);
    editMenu.add(cutItem);
    editMenu.add(pasteItem);
    editMenu.add(allItem);
    editMenu.add(dateItem);
    copyItem.addActionListener(this); // ������ ���̱�
    cutItem.addActionListener(this);
    pasteItem.addActionListener(this);
    allItem.addActionListener(this);
    dateItem.addActionListener(this);
    setBounds(300, 300, 300, 200);// ũ�� ����(x ��ǥ, y ��ǥ, ����, ����)
    setVisible(true);// ���̱�
  }// ������

  // �̺�Ʈ ó��
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    if (obj == exitItem) {
      System.exit(0); // ���α׷� ����
    }
    if (obj == newItem) {
      ta.setText(""); // ta �� �����.
    }
    if (obj == openItem) {
      openFile(); // ���� ���� �޼ҵ� ȣ��
    }// if
    if (obj == saveItem) {
      saveFile(); // ���� ���� �޼ҵ� ȣ��
    }// if
    if (obj == copyItem) { // �����ϱ� ���ý�
      ta.copy();
      copyItem.setEnabled(false);
      cutItem.setEnabled(false);
    }// if
    if (obj == cutItem) {
      ta.cut();
      copyItem.setEnabled(false);
      cutItem.setEnabled(false);
    }// if
    if (obj == pasteItem) {
      ta.paste();
      copyItem.setEnabled(true);
      cutItem.setEnabled(true);
    }// if
    if (obj == allItem) {
      ta.selectAll(); // ��� ����
    }// if
    if (obj == dateItem) {
      java.util.Date date = new java.util.Date();
      ta.append(date.toLocaleString());// ������ ��¥/�ð� �߰�
    }// if
  }// actionPerformed

  private void openFile() {  //  ���� ����
    JFileChooser chooser = new JFileChooser();
    int returnVal = chooser.showOpenDialog(this);// ���� ��ȭ����
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      System.out.println(file.getName() + "���õ�");
      // �� �κ��� I/O ���� �� ����^^
      BufferedReader br = null;
      try {
        br = new BufferedReader(new FileReader(file));
        String text = "";
        while ((text = br.readLine()) != null) {
          ta.append(text + "\r\n");// �ٹٲ�( Win : \r\n, Unix : \n )
        }// while
        br.close();
      } catch (Exception e) {
        e.printStackTrace();// ���� �޽��� �ڼ��� ���
      }// catch
    }// if
  }// openFile

  private void saveFile() {  //  ���� ����
    JFileChooser chooser = new JFileChooser();
    int returnVal = chooser.showSaveDialog(this);// ���� ��ȭ����
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      System.out.println(file.getName() + "���õ�");
      BufferedWriter bw = null;
      try {
        bw = new BufferedWriter(new FileWriter(file));
        bw.write(ta.getText());// ta ���� �о ���Ͽ� ����
        bw.flush();// ��� ����
        bw.close();// �ݱ�
      } catch (Exception e) {
        e.printStackTrace();// ���� �޽��� �ڼ��� ���
      }// catch
    }// if
  }// saveFile

  public static void main(String[] args) {
    new SwingNotepad();
  }// main
}// end

