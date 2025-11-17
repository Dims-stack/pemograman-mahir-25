import java.util.Stack;
import java.util.Scanner;

public class TextEditor {

    private String currentText;
    private Stack<String> historyStack;
    private Stack<String> futureStack;

    public TextEditor() {
        this.currentText = "";
        this.historyStack = new Stack<>();
        this.futureStack = new Stack<>();
        System.out.println("Text Editor berhasil diinisialisasi.");
    }

    public void write(String newText) {
        historyStack.push(this.currentText); 
        this.currentText += newText;
        futureStack.clear(); 
        
        System.out.println("\n[INFO] Teks berhasil ditambahkan.");
        System.out.println("[INFO] Future Stack dikosongkan.");
        show();
    }

    public void show() {
        System.out.println("\n=============================================");
        System.out.println("ISI TEKS EDITOR SAAT INI:");
        System.out.println(this.currentText.isEmpty() ? "<Tidak ada teks>" : this.currentText);
        System.out.println("=============================================");
        System.out.println("Status: History (" + historyStack.size() + "), Future (" + futureStack.size() + ")");
    }

    public void undo() {
        if (historyStack.isEmpty()) {
            System.out.println("\n[PERINGATAN] Tidak ada riwayat untuk di-undo.");
            return;
        }

        futureStack.push(this.currentText);
        this.currentText = historyStack.pop();
        
        System.out.println("\n[INFO] Undo berhasil dilakukan.");
        show();
    }

    public void redo() {
        if (futureStack.isEmpty()) {
            System.out.println("\n[PERINGATAN] Tidak ada operasi untuk di-redo.");
            return;
        }

        historyStack.push(this.currentText);
        this.currentText = futureStack.pop();
        
        System.out.println("\n[INFO] Redo berhasil dilakukan.");
        show();
    }

    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        editor.currentText = "Selamat datang di Text Editor Java.";
        editor.historyStack.push("");

        while (running) {
            System.out.println("\n---------------------------------------------");
            System.out.println("Pilih Fungsi:");
            System.out.println("1. WRITE (Tambahkan Teks)");
            System.out.println("2. UNDO (Kembalikan Teks)");
            System.out.println("3. REDO (Pulihkan Teks)");
            System.out.println("4. SHOW (Tampilkan Teks Saat Ini)");
            System.out.println("0. EXIT (Keluar)");
            System.out.print("Masukkan pilihan: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("Masukkan teks untuk ditambahkan: ");
                        String textToAdd = scanner.nextLine();
                        editor.write(textToAdd + "\n");
                        break;
                    case 2:
                        editor.undo();
                        break;
                    case 3:
                        editor.redo();
                        break;
                    case 4:
                        editor.show();
                        break;
                    case 0:
                        running = false;
                        System.out.println("Simulasi Text Editor selesai. Sampai jumpa!");
                        break;
                    default:
                        System.out.println("\n[PERINGATAN] Pilihan tidak valid. Silakan masukkan angka 0-4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n[KESALAHAN] Masukkan harus berupa angka. Silakan coba lagi.");
            }
        }
        scanner.close();
    }
}
