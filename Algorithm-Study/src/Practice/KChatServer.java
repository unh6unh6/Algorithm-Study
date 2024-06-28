package Practice;

import java.net.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.*; // JFrame, JPanel, ...
import java.awt.*; // Canvas, Graphics, Color
import java.awt.event.*;

public class KChatServer {
      protected  Vector<KChatHandler> vhandler;  /* ����� ��� ����Ʈ (Vector) */
      int id = 0;

      KChatServer (int port)  throws IOException {
	  ServerSocket server = new ServerSocket (port);
	  vhandler  = new Vector<KChatHandler> (2, 5);
/**/	  System.out.println("KChatRoom@"+ port +" ready ...");
	  while (true) {
	      Socket csock = server.accept();
	      KChatHandler c = new KChatHandler (this, csock);
	      vhandler.addElement (c);   // user ���(handler as the user)
	      System.out.println( ++id + "�� �մ� �޾ƶ� (���� " + vhandler.size() + "��)");
	      c.start();
	}
      }
      public int numChatters() { return vhandler.size(); }

      public static void main (String args[]) throws IOException {
	  if (args.length != 1)
	      throw new RuntimeException ("Syntax: ChatServer port");
	  new KChatServer (Integer.parseInt (args[0]));
      }
}

class KChatHandler extends Thread {
      protected KChatServer server;
      protected Socket sock;
      protected DataInputStream is;
      protected DataOutputStream os;

      KChatHandler (KChatServer server, Socket sock) throws IOException {
	this.server = server;
	this.sock = sock;
	// create I/O streasm to send & receive messags
	is = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
	os = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
      }

      public void run() {
	String name = "";
	try {
		// (1) receive login name
		name = is.readUTF();
		if (name.equals(""))  name = "������";

		// (2) ����Ѵ� - '����' ����
		broadcast(name + "�� ����!");

		// (3) �ݺ��Ѵ�: ���� ��� ���� ��� ������ ����Ѵ�
		while (true)  { broadcast (name + ": " + is.readUTF()); }

	} catch (IOException e) { System.out.println("... ??? ...");}
	finally {
		// ����� ������ ���� ����̴�. ������ �Ѵ�.
		System.out.println(name +" ����.");
		server.vhandler.removeElement(this);
	}
      }

      protected void broadcast (String message) {
	synchronized (server.vhandler) {
	      Enumeration en = server.vhandler.elements();   // ���ʷ� �о� �ִ� ��ü
	      while (en.hasMoreElements()) {
		KChatHandler c = (KChatHandler) en.nextElement();
		// ��� �����/���� ����� KChatHandler �� ���� ���� �������� ���� ����.
		try {
		c.os.writeUTF(message);
		c.os.flush();
		} catch(IOException e) {}
	      }
	}
      }
}

