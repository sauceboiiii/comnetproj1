
import java.net.*;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.*;

public class Client implements Runnable{
	private static final String IP = "139.62.210.153";
	private static final int PORT = 1058;
	public static int exit = 0;
    public static int option = 0;
    public static int numRequest = 0;
	private static Scanner in = new Scanner(System.in);
    @Override
    public void run(){
    	
    	//establish socket for thread
    	Socket socket = new Socket(IP, PORT);

    	//Socket socket = new Socket(host, port);
    	OutputStream out = socket.getOutputStream();
    	InputStreamReader inStream = new InputStreamReader(socket.getInputStream());
    	PrintWriter pw = new PrintWriter(out, true);
    	BufferedReader results = new BufferedReader(inStream);
	
        do
        {
			//present a menu of commands to the user for command to run
			System.out.println("Select a request to make: ");
            System.out.println("1 - Date");
            System.out.println("2 - Uptime");
            System.out.println("3 - Memory");
            System.out.println("4 - Netstat");
            System.out.println("5 - Users");
            System.out.println("6 - Processes");
            System.out.println("7 - Exit");
            
            //query for the command
            while(option > 7 || option < 1){
                try {   
                    option = in.nextInt();
                    if(option > 7 || option < 1)
                        System.out.println("Please select a request (1-7)");
                }
                catch (InputMismatchException e) {
                    in.next();
                    System.out.println("Please select a request (1-7)");
                }
                if(option == 7)
                break;
                
			//query for number of requests to make on the server
            System.out.println("How many requests do you want to generate? 1, 5, 10, 15, 20, or 25");
            while((numRequest != 1) & (numRequest != 5) & (numRequest != 10) & (numRequest != 15) & (numRequest != 20) & (numRequest != 25)) {
                try {
                    numRequest = in.nextInt();
                    if((numRequest != 1) & (numRequest != 5) & (numRequest != 10) & (numRequest != 15) & (numRequest != 20) & (numRequest != 25))
                    System.out.println("Please enter one of the following values: 1, 5, 10, 15, 20, 25");
                }
                catch (InputMismatchException e) { 
                    in.next();
                    System.out.println("Please enter one of the following values: 1, 5, 10, 15, 20, 25");
                }
            }
            
          //start timer
            
			long start = System.currentTimeMillis();
        
            for(int i = 0; i < numRequest; i++) {
            	
                //[in each thread]: issue request to server over network
            	
                switch(option){
                    case 1:
                        pw.println("1");
                        break;
                    case 2:
                        pw.println("2");
                        break;
                    case 3:
                        pw.println("3");
                        break;
                    case 4:
                        pw.println("4");
                        break;
                    case 5: 
                        pw.println("5");
                        break;
                    case 6:
                        pw.println("6");
                        break;
                    default:
                }
                //gather result from server
                try {
                System.out.println(results.readLine());
            }
            catch(IOException e) {
            	System.out.println("oopsie");
            }
            //stop timer
            long stop = System.currentTimeMillis();
			//total time-timer
			long timer = stop - start;
			//average time= total/number
            float avg = (float)timer / numRequest;
            System.out.println("Average time of responses = "+avg+"ms\n");
            System.out.println("Total turn around time = "+timer+"ms\n");
            socket.close();
            }
            in.close();
            numRequest = 0;
            option = 0;
            }
        }while(option != 7);
        socket.close();
        in.close();
    }
    
	public static void main(String[] args) throws IOException {
        //request network address and port to which to connect
        //System.out.println("Please input host: ");
        //String host = in.nextLine();
        //System.out.println("Please input port: ");
        //int port = in.nextInt();
        
      //make threads(number)
        for(int i = 0; i < numRequest; i++) {
            Client obj = new Client();
            Thread requests = new Thread[numRequest];
            requests[i].setName("#" + (i+1));
            requests[i].start();
        
            try {
                requests[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
	}
}