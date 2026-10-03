package OS.skeleton;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class JioChannel {

    public static void copyTraditional(Path from, Path to)throws IOException{
        byte[] buffer = new byte[64 * 1024];

        try(InputStream in = Files.newInputStream(from); OutputStream out = Files.newOutputStream(to)){
            
            int n;
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
        }
    }

    public static void copyZeroCopy(Path from, Path to)throws IOException{
        try(FileChannel source = FileChannel.open(from, StandardOpenOption.READ);
            FileChannel destination = FileChannel.open(to, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)){

                long position = 0;
                long size = source.size();

                while (position < size) {
                    long n = source.transferTo(position, size - position, destination);

                    if(n <= 0) throw new IOException("tranferTo made no progress");
                    position += n;
                }
            }
    }

    public static void main(String[] args) throws Exception{
        if(args.length != 3){
            System.out.println("Usage java JioChannel <source> <dest> <traditional | zerocopy>");
            return;
        }

        Path from = Path.of(args[0]);
        Path to = Path.of(args[1]);
        long start = System.nanoTime();

        if ("traditional".equalsIgnoreCase(args[2])) {
            copyTraditional(from, to);
        }
        else if ("zerocopy".equalsIgnoreCase(args[2])) {
            copyZeroCopy(from, to);
        }
        else{
            System.out.println("mode must be traditional or zerocopy");
            return;
        }

        double ms = (System.nanoTime() - start) / 1_000_000.0;
        System.out.printf("Time: %.2f ms%n", ms);
    }
}
