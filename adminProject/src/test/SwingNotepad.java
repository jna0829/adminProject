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
  // 멤버 변수 정의
  JTextArea ta; // 편집할 수 있는 여러 줄 입력란
  JMenuBar menubar; // 메뉴바
  JMenu fileMenu, editMenu; // 파일 메뉴, 편집 메뉴
  JMenuItem newItem, openItem, saveItem, exitItem;// 메뉴아이템
  JMenuItem copyItem, cutItem, pasteItem, allItem, dateItem;

  //  생성자 정의
  public SwingNotepad() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// X 클릭 => 종료
    setTitle("스윙 메모장");// 제목
    ta = new JTextArea();// ta 생성
    JScrollPane scroll = new JScrollPane(ta);// 스크롤에 ta 를 넣는다.
    add(scroll);// 스크롤을 프레임의 중앙에 붙인다.
    // getContentPane().add(scroll); // JDK 1.4일 경우의 코드
    menubar = new JMenuBar();// 메뉴바 생성
    setJMenuBar(menubar);// 메뉴바를 붙인다.
    fileMenu = new JMenu("파일(F)");

    fileMenu.setMnemonic('F');//Alt + F
    menubar.add(fileMenu);// 메뉴바에 파일 메뉴 붙이기
    newItem = new JMenuItem("새로 만들기");
    openItem = new JMenuItem("열기");
    saveItem = new JMenuItem("저장");
    exitItem = new JMenuItem("끝내기");

 

    newItem.setAccelerator(KeyStroke.getKeyStroke('N',Event.CTRL_MASK));//Ctrl + N

    openItem.setAccelerator(KeyStroke.getKeyStroke('O',Event.CTRL_MASK));//Ctrl + O

    saveItem.setAccelerator(KeyStroke.getKeyStroke('S',Event.CTRL_MASK));//Ctrl + S

    exitItem.setAccelerator(KeyStroke.getKeyStroke('X',Event.CTRL_MASK));//Ctrl + X

 

    fileMenu.add(newItem);
    fileMenu.add(openItem);
    fileMenu.add(saveItem);
    fileMenu.addSeparator();// 구분선 넣기
    fileMenu.add(exitItem);
    newItem.addActionListener(this); // 감지기 붙이기
    openItem.addActionListener(this);
    saveItem.addActionListener(this);
    exitItem.addActionListener(this);
    editMenu = new JMenu("편집(E)");// 편집 메뉴 생성

    editMenu.setMnemonic('E');
    menubar.add(editMenu);// 메뉴바에 편집 메뉴 붙이기
    copyItem = new JMenuItem("복사");
    cutItem = new JMenuItem("잘라내기");
    pasteItem = new JMenuItem("붙여넣기");
    allItem = new JMenuItem("모두 선택");
    dateItem = new JMenuItem("날짜/시간");

 

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
    copyItem.addActionListener(this); // 감지기 붙이기
    cutItem.addActionListener(this);
    pasteItem.addActionListener(this);
    allItem.addActionListener(this);
    dateItem.addActionListener(this);
    setBounds(300, 300, 300, 200);// 크기 지정(x 좌표, y 좌표, 가로, 세로)
    setVisible(true);// 보이기
  }// 생성자

  // 이벤트 처리
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    if (obj == exitItem) {
      System.exit(0); // 프로그램 종료
    }
    if (obj == newItem) {
      ta.setText(""); // ta 를 지운다.
    }
    if (obj == openItem) {
      openFile(); // 파일 열기 메소드 호출
    }// if
    if (obj == saveItem) {
      saveFile(); // 파일 저장 메소드 호출
    }// if
    if (obj == copyItem) { // 복사하기 선택시
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
      ta.selectAll(); // 모두 선택
    }// if
    if (obj == dateItem) {
      java.util.Date date = new java.util.Date();
      ta.append(date.toLocaleString());// 간단한 날짜/시간 추가
    }// if
  }// actionPerformed

  private void openFile() {  //  파일 열기
    JFileChooser chooser = new JFileChooser();
    int returnVal = chooser.showOpenDialog(this);// 열기 대화상자
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      System.out.println(file.getName() + "선택됨");
      // 이 부분은 I/O 공부 후 진행^^
      BufferedReader br = null;
      try {
        br = new BufferedReader(new FileReader(file));
        String text = "";
        while ((text = br.readLine()) != null) {
          ta.append(text + "\r\n");// 줄바꿈( Win : \r\n, Unix : \n )
        }// while
        br.close();
      } catch (Exception e) {
        e.printStackTrace();// 예외 메시지 자세히 출력
      }// catch
    }// if
  }// openFile

  private void saveFile() {  //  파일 저장
    JFileChooser chooser = new JFileChooser();
    int returnVal = chooser.showSaveDialog(this);// 저장 대화상자
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      System.out.println(file.getName() + "선택됨");
      BufferedWriter bw = null;
      try {
        bw = new BufferedWriter(new FileWriter(file));
        bw.write(ta.getText());// ta 내용 읽어서 파일에 저장
        bw.flush();// 즉시 저장
        bw.close();// 닫기
      } catch (Exception e) {
        e.printStackTrace();// 예외 메시지 자세히 출력
      }// catch
    }// if
  }// saveFile

  public static void main(String[] args) {
    new SwingNotepad();
  }// main
}// end

