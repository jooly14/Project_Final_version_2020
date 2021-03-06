package server;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
//파일 전송할 서버
//포트번호를 같게 하면 전송하는 사람과 전송받는 사람 구분이 어려워서 따로 생성
public class FileSendServer extends Thread{
	ServerSocket serverSocket;
	ServerSocket serverSocket2;
	Socket clientSocket;
	Socket clientSocket2;
	BufferedInputStream bis = null;
	BufferedOutputStream bos = null;
	FileReceiveServer fileReceiveServer;
	public FileSendServer(FileReceiveServer fileReceiveServer) {
		this.fileReceiveServer = fileReceiveServer;
	}
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(4444);
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (!fileReceiveServer.getStart()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		serverSocket2 = fileReceiveServer.getServerSocket();
		clientSocket2 = fileReceiveServer.getClientSocket();
		try {
			int data;
			bis = new BufferedInputStream(clientSocket.getInputStream());
			bos = new BufferedOutputStream(clientSocket2.getOutputStream());
			while ((data = bis.read())!=-1) {	//clientSocket의 bis에서 읽어와서 clientSocket2의 bos에 씀
				bos.write(data);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
				try {
					if(bis!=null){
						bis.close();
					}
					if(bos!=null){
						bos.close();
					}
					if(clientSocket!=null){
						clientSocket.close();
					}
					if(clientSocket2!=null){
						clientSocket2.close();
					}
					if(serverSocket!=null){
						serverSocket.close();
					}
					if(serverSocket2!=null){
						serverSocket2.close();
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}
}