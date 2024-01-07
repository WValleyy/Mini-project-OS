package Reader_Writer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReaderWriterMonitor {
    private final Lock lock = new ReentrantLock();
    private final Condition canRead = lock.newCondition();
    private final Condition canWrite = lock.newCondition();
    private int waitingWriters;
    private int activeWriters;
    private int waitingReaders;
    private int activeReaders;


    public ReaderWriterMonitor() {
        waitingWriters = 0;
        activeWriters = 0;
        waitingReaders = 0;
        activeReaders = 0;
    }


    public void BeginWrite(int id) {
        lock.lock();
        try {
            while (activeWriters == 1 || activeReaders > 0) {
                ++waitingWriters;
                System.out.println("Writer " + id + " is waiting to write");
                canWrite.await();
                --waitingWriters;
            }
            System.out.println("Writer " + id + " is writting");
            activeWriters = 1;
        } catch (InterruptedException e) {
            System.out.println("Writer " + id + "was interrupted");
        } finally {
            lock.unlock();
        }
    }

    public void EndWrite(int id) {
        lock.lock();
        try {
            activeWriters = 0;
            System.out.println("Writer " + id + " has finished writing");
            if (waitingReaders>0)
                canRead.signal();
            else
                canWrite.signal();


        } finally {
            lock.unlock();
        }
    }


    public void BeginRead(int id) {
        lock.lock();
        try {
            while (activeWriters == 1) {
                ++waitingReaders;
                System.out.println("Reader " + id + ": waiting to read");
                canRead.await();
                --waitingReaders;
            }
            ++activeReaders;
            System.out.println("Reader " + id + " is reading");
            canRead.signal();

        } catch (InterruptedException e) {
            System.out.println("Reader " + id + "was interrupted");
        } finally {
            lock.unlock();
        }
    }


    public void EndRead(int id) {
        lock.lock();
        System.out.println("Reader " + id + ": finished reading");
        try {
            --activeReaders;
            if (activeReaders == 0 && waitingWriters > 0) {
                canWrite.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}
