package com.appspot.hihanjoong.main;
import com.appspot.hihanjoong.ftp.controller.FTPController;
import com.appspot.hihanjoong.ftp.model.FTPModel;
import com.appspot.hihanjoong.ftp.view.FTPViewer;



public class FtpMain {
	
	public static void main(String[] args) {
		
		FTPModel model = new FTPModel();
		FTPController controller = new FTPController(model);
		FTPViewer ftpViewer = new FTPViewer(model, controller);
		
		ftpViewer.setSize(350,500);
		ftpViewer.setVisible(true);
	}

}
