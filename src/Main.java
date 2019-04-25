import java.io.IOException;

public class Main {

    private static Object lock = new Object();
    private static ShapeGenerator generator = new ShapeGenerator();
    private static FileManager menago = new FileManager(lock);

    public static void main(String[] args) {

        myWindow okno = new myWindow("Generator livestream");
        myWindow oknoDuo = new myWindow("Data from file");


        Thread t2 = new Thread(()->{
            while (true) {
                okno.repaint();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });



         Thread t1 = new Thread(()->{
             Shape tmp;

             while(true) {
                 tmp = generator.generateShape();
                 okno.addToContainer(tmp);
                 menago.saveShape(tmp);
                 System.out.println(okno.container.getCount());

                 try {
                     Thread.sleep(3500);
                     System.out.println("Shape added");

                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }

         });

         Thread t3 = new Thread(()->{
             while(true){
                 try {
                     menago.readFile(oknoDuo.getContainer(), menago.getCurrentFile());
                 } catch (IOException e) {
                     e.printStackTrace();
                 }

                 oknoDuo.repaint();

                 try {
                     Thread.sleep(500);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }


             }

         });



         t1.start();
         t2.start();
         t3.start();

    }
}
