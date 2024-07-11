package IO;

import java.io.IOException;
import java.io.InputStream;

public class SimpleDecompressorInputStream extends InputStream{
    private InputStream in;

    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return this.in.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        int idx = 0;
        byte currentByte = 0;

        while (true) {
            int count = in.read();
            if (count == -1) break; // End of stream

            for (int i = 0; i < count; i++) {
                if (idx < b.length) {
                    b[idx] = currentByte;
                    idx++;
                }
            }
            currentByte = (byte) (1 - currentByte);
        }

        return idx;
    }
}
