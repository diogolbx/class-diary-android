package cc.classdiary.adapter;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cc.classdiary.model.Frequency;
import cc.classdiary.model.Student;

public class SyncAdapter {

	public static final String SYNC_BASE_URL = "http://192.168.1.4:8080/vraptor-template";
	
	public SyncAdapter() {
	}
	
	public boolean checkLogin(String login,String password) {
		HttpURLConnection conn = null;
		int responseCode = 0;
		boolean isAuthenticated = false;
		try {
			URL url = new URL(SYNC_BASE_URL + "/student/login");
			conn = (HttpURLConnection) url.openConnection();
		    conn.setReadTimeout(10000);
		    conn.setConnectTimeout(10000);
		    conn.setRequestMethod("POST");
		    conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
		    conn.setDoInput(true);
		    conn.setDoOutput(true);
		    String body = String.format("student.email=%s&student.password=%s",login,password);
		    OutputStream output = new BufferedOutputStream(conn.getOutputStream());
		    output.write(body.getBytes());
		    output.flush();
		    responseCode = conn.getResponseCode();
		    if(responseCode == 200)
		    		isAuthenticated = true;
 
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
		    conn.disconnect();
		}
		return isAuthenticated;
	}

	public boolean checkInFrequency(Frequency frequency) {
		HttpURLConnection conn = null;
		int responseCode = 0;
		boolean ok = false;
		try {
			URL url = new URL(SYNC_BASE_URL +"/frequency/add");
			conn = (HttpURLConnection) url.openConnection();
		    conn.setReadTimeout(10000);
		    conn.setConnectTimeout(15000);
		    conn.setRequestMethod("PUT");
		    conn.setRequestProperty("content-type", "application/xml");
		    conn.setDoInput(true);
		    conn.setDoOutput(true);
		    String body = String.format("<frequency><student><id>%s</id></student><disciplineClass><id>%s</id></disciplineClass><classNumber>%s</classNumber><presence>P</presence></frequency>",frequency.getStudent().getId(),frequency.getDisciplineClass().getId(), frequency.getClassNumber());
		    OutputStream output = new BufferedOutputStream(conn.getOutputStream());
		    output.write(body.getBytes());
		    output.flush();
		    responseCode = conn.getResponseCode();
		    if(responseCode == 200)
		    		ok = true;
 
		} catch (Exception e) {
			System.out.println("Erro ao enviar frequencia");
			e.printStackTrace();
		}finally {
		    conn.disconnect();
		}
		
		return ok;
	}

	public void syncDisciplineClassesByStudent(Student student) {
		
	}
}
