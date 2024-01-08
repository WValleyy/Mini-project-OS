package Reader_Writer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Reader extends Thread{
	private final int id;
	private final ReaderWriterMonitor monitor;
	private BufferedReader readFile;
	private String stringRead;

	public Reader(ReaderWriterMonitor monitor, int id){
		this.id = id;
		this.monitor = monitor;

	}
	

	public void run(){
		int count = 0;
		Random random = new Random();
		while(count < 3){
			monitor.BeginRead(id);
            try {
                Read("Database.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
			monitor.EndRead(id);
            try {
                Thread.sleep(random.nextInt(11));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            count++;
			
		}
		System.out.println("Reader " + id + " has finished reads " + count +" times." );
	}

	public void Read( String filename) throws IOException {
		try{
			readFile = new BufferedReader(new FileReader(filename));
		}
		catch(FileNotFoundException e){
			System.out.println("file wasn't found");
		}
		String fileToRead = readFile.toString();
		int c = 0;
		int charac;
		Random random = new Random();
		int readLength = random.nextInt(fileToRead.length());
		while (c < readLength)
		{
			charac = fileToRead.charAt(c);
			stringRead += charac;
			c += 1;
		}
		readFile.close();

	}
	public void printRead() {
		System.out.println(stringRead);
	}


}
