import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

public class HttpClient {
	
	
	public static void main(String[] args) throws IOException{
		
		System.out.println(urlExist("http://www.if-schleife.de/"));
		get("http://www.if-schleife.de/");
		
		
		/* String url = "http://www.if-schleife.de/";

        boolean urlExists;
        String content;
        String date;

        urlExists = urlExist(url);
        System.out.println(urlExists);
        if (urlExists) {
            date = getLastDate(url);
            System.out.println(date);

            content = getResponseBody(url);
            System.out.println(content);
        } */
		
	}
	
	public static void get(String targetURL) throws IOException{
		
		URL url = new URL(targetURL);
		int port = url.getPort();
		if(port == -1){
			port = 80;
		}
        Socket httpSocket = new Socket(url.getHost(), port);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(httpSocket.getOutputStream()));

        bw.write("GET " + targetURL + " HTTP/0.9\n");
        bw.write("Host:\n");
        bw.write("\n");
        bw.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(httpSocket.getInputStream()));
        String result;
        while ((result = br.readLine()) != null) {
            System.out.println(result);
        }
        br.close();
        
        httpSocket.close();
		
	}
	
	
	public static boolean urlExist(String targetURL){
		boolean exist = false;
        try {

            URL url = new URL(targetURL);
            int port = url.getPort();
    		if(port == -1){
    			port = 80;
    		}
            Socket httpSocket = new Socket(url.getHost(), port);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(httpSocket.getOutputStream()));
            bw.write("GET " + targetURL + " HTTP/0.9\n");
            bw.write("Host:\n");
            bw.write("\n");
            bw.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(httpSocket.getInputStream()));
            String result = br.readLine();
            
            String[] resultLine = result.split("\\s+");
            
            if (resultLine[1].equals("200")) {
                exist = true;
            } else {
            	System.out.println("Response Code:" + resultLine[1]);
            	exist = false;
            }
            br.close();
            httpSocket.close();

        } catch (MalformedURLException e) {
        	System.out.println("MalformedURLException");
            exist = false;
        } catch (IOException e) {
        	System.out.println("IOException");
        	exist = false;
        }

        return exist;
	}
	
	public static String getResponseBody(String targetURL){
		
		StringBuffer responseBody = new StringBuffer();
		
		
		try {

            URL url = new URL(targetURL);
            int port = url.getPort();
    		if(port == -1){
    			port = 80;
    		}
    		
            Socket httpSocket = new Socket(url.getHost(), port);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(httpSocket.getOutputStream()));
            bw.write("GET " + targetURL + " HTTP/0.9\n");
            bw.write("Host:\n");
            bw.write("\n");
            bw.flush();
            
            BufferedReader br = new BufferedReader(new InputStreamReader(httpSocket.getInputStream()));
            String result;
            int start = 0;
            while ((result = br.readLine()) != null) {
                if (containsString(result, "DOCTYPE")) {
                    start = 1;
                }
                if (start == 1) {
                	responseBody.append(result  + "\n");
                }
            }
            
            br.close();
            httpSocket.close();
            
            
		} catch (MalformedURLException e) {
        	System.out.println("MalformedURLException");
        } catch (IOException e) {
        	System.out.println("IOException");
        }

		
		
		
		
		return responseBody.toString();
	}

	
	 private static boolean containsString( String s, String subString ) {
	        return s.indexOf( subString ) > -1 ? true : false;
	 }
	 
	 
	 public static String getLastDate(String targetURL) {
		 
		 String date = "";
		 
		 try {

	            URL url = new URL(targetURL);
	            int port = url.getPort();
	    		if(port == -1){
	    			port = 80;
	    		}
	            Socket httpSocket = new Socket(url.getHost(), port);
	            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(httpSocket.getOutputStream()));
	            bw.write("GET " + targetURL + " HTTP/0.9\n");
	            bw.write("Host:\n");
	            bw.write("\n");
	            bw.flush();

	            BufferedReader br = new BufferedReader(new InputStreamReader(httpSocket.getInputStream()));
	            String result;
	            int start = 0;
	            while ((result = br.readLine()) != null) {
	                if (result.startsWith("Date: ")) {
	                    date = result;
	                    break;
	                }
	            }
	            br.close();
	            httpSocket.close();
	            
	      

	        } catch (MalformedURLException e) {
	        	System.out.println("MalformedURLException");
	        } catch (IOException e) {
	        	System.out.println("IOException");
	        }

		 return date;
		 
	 }
}
