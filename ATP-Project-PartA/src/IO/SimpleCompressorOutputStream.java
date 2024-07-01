package IO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SimpleCompressorOutputStream extends OutputStream {
    private OutputStream out;

    public SimpleCompressorOutputStream(OutputStream os){
        this.out = os;
    }

    @Override
    public void write(int b) throws IOException {
        this.out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException{
        this.out.write(compress(b));
    }

    private byte[] compress(byte[] data) {
        ByteArrayOutputStream compressed = new ByteArrayOutputStream();
        int count = 0;
        byte currentByte = 0;

        for (byte b : data) {
            if (b == currentByte) {
                count++;
                if (count == 256) {
                    compressed.write(255);
                    compressed.write(0);
                    count = 1;
                }
            } else {
                compressed.write(count);
                currentByte = b;
                count = 1;
            }
        }
        compressed.write(count);
        return compressed.toByteArray();
    }
}
