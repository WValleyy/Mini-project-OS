package Reader_Writer;

public class MainRW {
	public static void main(String[] args){
		ReaderWriterMonitor monitor = new ReaderWriterMonitor();
		
		Writer[] wt = new Writer[10];
		Reader[] rt = new Reader[10];
		
		wt[0] = new Writer(monitor, 1);
		wt[1] = new Writer(monitor, 2);
		wt[2] = new Writer(monitor, 3);
		wt[3] = new Writer(monitor, 4);

		
		rt[0] = new Reader(monitor, 1);
		rt[1] = new Reader(monitor, 2);
		rt[2] = new Reader(monitor, 3);
		rt[3] = new Reader(monitor, 4);

		
		for(int i = 0; i < wt.length; i++){
			wt[i].start();
			rt[i].start();
		}
		
		


		
		
	}
}
