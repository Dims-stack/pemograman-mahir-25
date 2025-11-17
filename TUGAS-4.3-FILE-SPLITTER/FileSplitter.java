import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class FileSplitter {

    private static final String FILE_NAME = "D:\\DATA (D2)\\PBO Java\\pm_25\\src\\pm_25\\input.txt";

    public static Queue<String> splitFile(String content, int numParts) {
        Queue<String> segmentsQueue = new LinkedList<>();
        
        if (content == null || content.isEmpty() || numParts <= 0) {
            return segmentsQueue;
        }

        int totalLength = content.length();
        int segmentSize = totalLength / numParts;
        int startIndex = 0;

        System.out.println("\n[INFO] Mulai proses pemotongan...");
        System.out.println("[INFO] Panjang total file: " + totalLength + " karakter.");
        System.out.println("[INFO] Ukuran dasar per segmen: " + segmentSize + " karakter.");

        for (int i = 0; i < numParts; i++) {
            int endIndex = startIndex + segmentSize;
            
            if (i == numParts - 1) {
                endIndex = totalLength;
            }

            String segment = content.substring(startIndex, endIndex);
            segmentsQueue.offer(segment); 
            
            System.out.println("[DEBUG] Segmen " + (i + 1) + ": Panjang " + segment.length());
            
            startIndex = endIndex;
        }

        return segmentsQueue;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileContent = "";

        try {
            fileContent = new String(Files.readAllBytes(Paths.get(FILE_NAME)));
            System.out.println(" File '" + FILE_NAME + "' berhasil dibaca.");
        } catch (IOException e) {
            System.out.println(" KESALAHAN: Gagal membaca file. Pastikan path absolut sudah benar, termasuk penggunaan backslash ganda.");
            e.printStackTrace();
            return;
        }

        int numParts = 0;
        while (numParts <= 0) {
            System.out.print("\nMasukkan jumlah bagian (N) untuk memotong file: ");
            if (scanner.hasNextInt()) {
                numParts = scanner.nextInt();
                if (numParts <= 0) {
                    System.out.println("[PERINGATAN] Jumlah bagian harus lebih dari 0.");
                }
            } else {
                System.out.println("[PERINGATAN] Input tidak valid. Masukkan angka.");
                scanner.next(); 
            }
        }
        scanner.close();

        Queue<String> fileSegments = splitFile(fileContent, numParts);

        System.out.println("\n=======================================================");
        System.out.println("HASIL PEMOTONGAN FILE (" + fileSegments.size() + " Bagian, Dibaca dari Queue):");
        System.out.println("=======================================================");
        
        int count = 1;
        while (!fileSegments.isEmpty()) {
            String segment = fileSegments.poll(); 
            System.out.println("\n--- BAGIAN " + count + " (Panjang: " + segment.length() + ") ---");
            System.out.println(segment);
            count++;
        }
        System.out.println("\n=======================================================");
        System.out.println(" Proses selesai. Queue sekarang kosong.");
    }
}
