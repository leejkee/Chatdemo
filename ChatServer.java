package chatdemopkg;
// 
import javax.swing.JFrame;//窗口容器
import javax.swing.JButton;//按钮
import javax.swing.JPanel;//面板容器
import javax.swing.JScrollPane;//滚动区域
import javax.swing.JTextArea;//文本区域
import javax.swing.JTextField;//文本框
import java.awt.BorderLayout;//布局

import java.io.BufferedReader;//字符缓冲输入流
import java.io.BufferedWriter;//字符缓冲输出流
import java.io.IOException;//IO异常
// InputStreamReader使用指定的字符集读取字节并将它们解码为字符。
// OutputStreamWriter将字符流转换为字节流。
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
// 
import java.awt.event.*;

// 一：窗口设计
// 需要GUI界面显示，需要继承Jframe类（或者Frame类）
// 定义JFrame窗口的组件，包含两部分，一部分是消息文字显示，另一部分是消息输入和发送
// 消息文字显示为一个滚动面板，容器内包含一个文本域，该部分用户不可编辑
// 消息输入和发送部分为一个控制面板，容器内包含一个文本框和一个消息发送按钮，文本框用户可编辑
// 完成窗口设计并设置整个窗口为可见
// 二：网络编程完成消息传输（TCP/IP）
// 使用socket编程完成主要步骤
// 实现 ”Send“ 按钮的监听点击事件

public class ChatServer extends JFrame implements ActionListener{
    //  
    // 定义消息显示区域
    private JTextArea _message_view_area;
    // 
    // 定义滚动面板，该类创建的对象是容器，通过鼠标滚动显示超出页面的消息记录
    private JScrollPane _scroll_bar;
    // 
    // 定义控制面板，该类创建的对象同样为容器，面板包含消息输入文本框和发送消息按钮
    private JPanel _The_panel;
    // 
    // 定义消息输入文本框
    private JTextField _field_text;
    // 
    // 定义发送消息按钮
    private JButton _send_button;
    // 
    // 
    private BufferedWriter buffer_out = null;
    // 构造方法
    public ChatServer(){
        // 
        // 实例化消息显示区域
        _message_view_area = new JTextArea();
        // 
        // 设置消息显示区域不可编辑
        _message_view_area.setEditable(false);
        // 
        // 实例化滚动面板，并把消息显示区域添加到滚动面板，实现水平和垂直滚动
        _scroll_bar = new JScrollPane(_message_view_area);
        // 
        // 实例化控制面板区域，消息输入框，发送按钮
        _The_panel = new JPanel();
        _field_text = new JTextField(12);
        _send_button = new JButton("Send");
        // 
        // 添加组件
        _The_panel.add(_field_text);
        _The_panel.add(_send_button);
        // 
        // 最后需要添加两个容器到窗口中，窗口类创建的对象本身也是个容器，
        // 整个窗口对象由滚动显示消息容器和控制面板容器构成
        this.add(_scroll_bar, BorderLayout.CENTER);
        this.add(_The_panel, BorderLayout.SOUTH);
        // 
        // 面板
        // 窗口标题
        this.setTitle("ChatApplicationServer");
        // 窗口大小
        this.setSize(600, 800);
        // 窗口位置
        this.setLocation(750, 750);
        // 窗口关闭方式，使用Syetem exit方法退出应用程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 窗口可见
        this.setVisible(true);
        // 
        /*********** TCP Server Start ************/
        // 
        // 给发送按钮绑定一个监听点击事件
        _send_button.addActionListener(this);
        // 定义ServerSocket类
        try {
            // 
            // 创建服务端套接字
            ServerSocket server_socket = new ServerSocket(8888);//port 8888
            // 
            // 等待客户端的连接
            Socket socket = server_socket.accept();
            // 
            // 得到socket通道输入流
            BufferedReader buffer_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 
            // 获取socket通道输出流，写一行换一行，刷新
            buffer_out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String line = null;
            while((line = buffer_in.readLine()) != null){
                // 
                // 在上方文本区域中显示line和Syetem.lineSeparator()（换行符）
                _message_view_area.append(line + System.lineSeparator());
            }
            // 
            // 关闭socket通道
            server_socket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        /*********** TCP Server End ************/
    }

    // 一旦点击Send按钮，就执行该方法
    public void actionPerformed(ActionEvent e) {
        // 测试语句
        // System.out.println("Send click");
        // 
        // 获取输入文本框中的内容
        String text = _field_text.getText();
        // 
        // 拼接需要发送的数据内容,实际上我们希望发送输入的内容，习惯上会一带在消息开头标出发出者
        text = "Server: " + text;
        //
        _message_view_area.append(text);
        // 
        try{
            buffer_out.write(text);
        } catch (IOException e1){
            e1.printStackTrace();
        }
    }
}