package IO;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return this.in.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        // Use an InflaterInputStream to decompress the data
        try (InflaterInputStream inflaterInputStream = new InflaterInputStream(this.in)) {
            int idx = 0;
            while (inflaterInputStream.available() != 0)    // Read bytes until no more are available
            {
                // Read a byte from the InflaterInputStream and store it in the buffer
                b[idx] = (byte) (inflaterInputStream.read());
                idx++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
