package Reader_Writer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;

public class Writer extends Thread {
    private final int id;
    private final ReaderWriterMonitor monitor;
    private PrintWriter writeFile;

    public Writer(ReaderWriterMonitor monitor, int id) {
        this.id = id;
        this.monitor = monitor;

    }

    public void run() {
        int count = 0;
        Random random = new Random();
        while (count < 3) {
            monitor.BeginWrite(id);
            Write("Database.txt", random.nextInt(26));
            monitor.EndWrite(id);
            try {
                Thread.sleep(random.nextInt(11));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            count++;
        }
        System.out.println("Writer " + id + " has write " + count + " times.");
    }
    public void Write(String filename, int random) {
        try {
            writeFile = new PrintWriter(new FileOutputStream(filename, true));
        } catch (FileNotFoundException e) {
            System.out.println("Error opening the file " + filename);
        }
        for (int i = 0; i < random; i++) {
            char wChar = (char) (i + 'a');
            writeFile.print(wChar);
        }
        writeFile.close();
    }

}
