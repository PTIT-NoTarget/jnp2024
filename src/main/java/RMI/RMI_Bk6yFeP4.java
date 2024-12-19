package RMI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

//[Mã câu hỏi (qCode): Bk6yFeP4].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý chuỗi.
//Giao diện từ xa:
//public interface CharacterService extends Remote {
//    public String requestCharacter(String studentCode, String qCode) throws RemoteException;
//    public void submitCharacter(String studentCode, String qCode, String strSubmit) throws RemoteException;
//}
//Trong đó:
//        •	Interface CharacterService được viết trong package RMI.
//•	Đối tượng cài đặt giao diện từ xa CharacterService được đăng ký với RegistryServer với tên là: RMICharacterService.
//Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với chuỗi được nhận từ RMI Server:
//a. Triệu gọi phương thức requestCharacter để nhận chuỗi ngẫu nhiên từ server với định dạng: "Từ khóa;Chuỗi đầu vào"
//b. Thực hiện thao tác mã hóa Vigenère cho chuỗi nhận được. Biết rằng, mã hóa Vigenère thực hiện mã hóa mỗi ký tự trong chuỗi đầu vào được dịch đi một khoảng bằng với vị trí tương ứng của ký tự trong từ khóa. (Từ khóa được lặp lại để khớp với độ dài của chuỗi)
//Ví dụ: chuỗi ban đầu "PTIT;HELLO" -> từ khóa "PTIT" và chuỗi mã hóa là: "WXTED"
//c. Triệu gọi phương thức submitCharacter để gửi chuỗi đã được mã hóa trở lại server.
//d. Kết thúc chương trình client.

interface CharacterService extends Remote {
    public String requestCharacter(String studentCode, String qCode) throws RemoteException;
    public void submitCharacter(String studentCode, String qCode, String strSubmit) throws RemoteException;
}

public class RMI_Bk6yFeP4 {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        String studentCode = "B21DCCN324";
        String qCode = "Bk6yFeP4";
        CharacterService characterService = (CharacterService) Naming.lookup("rmi://203.162.10.109/RMICharacterService");
        String str = characterService.requestCharacter(studentCode, qCode);
        System.out.println("Received string: " + str);
        String[] parts = str.split(";");
        String keyword = parts[0];
        String input = parts[1];
        String encrypted = encryptVigenere(input, keyword);
        System.out.println("Encrypted string: " + encrypted);
        characterService.submitCharacter(studentCode, qCode, encrypted);
    }

    public static String encryptVigenere(String input, String keyword) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            char k = keyword.charAt(i % keyword.length());
            if(c >= 'A' && c <= 'Z') {
                encrypted.append((char) ((c + k - 2 * 'A') % 26 + 'A'));
            } else if(c >= 'a' && c <= 'z') {
                encrypted.append((char) ((c + k - 2 * 'a') % 26 + 'a'));
            } else {
                encrypted.append(c);
            }
        }
        return encrypted.toString();
    }
}
