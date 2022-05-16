
package chatdemopkg;
// 
// 服务端程序
import javax.swing.JFrame;//窗口容器
import javax.swing.JButton;//按钮
import javax.swing.JPanel;//面板容器
import javax.swing.JScrollPane;//滚动区域
import javax.swing.JTextArea;//文本区域
import javax.swing.JTextField;//文本框
import java.awt.BorderLayout;//布局
import java.io.IOException;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

// 
// 类的成员变量均以下划线开头

public class ChatClient extends JFrame{
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
    // 构造方法
    public ChatClient(){
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
        // 设置面板面板属性
        // 设置窗口标题
        this.setTitle("ChatApplicationClient");
        // 设置窗口大小
        this.setSize(600, 800);
        // 设置窗口位置
        this.setLocation(1350, 750);
        // 设置窗口关闭方式，使用Syetem exit方法退出应用程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口可见
        this.setVisible(true);
        // 
        /*********** TCP Client Start ************/
        try {
            // 
            // 创建客户端套接字，尝试连接服务端
            Socket socket = new Socket("127.0.0.1", 8888);
            // 获取socket输入流，
            BufferedReader buffer_in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
            // 
            // 获取socket输出流
            BufferedWriter buffer_out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // 循环读取数据，并拼接到文本域
            String line = null;
            while ((line = buffer_in.readLine()) != null){
                // 在文本域显示接受到的消息并换行
                _message_view_area.append(line + System.lineSeparator());
            }
            // 
            // 关闭socket通道
            socket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        /*********** TCP Client End ************/
    }
}