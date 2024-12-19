package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

//[Mã câu hỏi (qCode): aQNKoVsH].  Mật mã caesar, còn gọi là mật mã dịch chuyển, để giải mã thì mỗi ký tự nhận được sẽ được thay thế bằng một ký tự cách nó một đoạn s. Ví dụ: với s = 3 thì ký tự “A” sẽ được thay thế bằng ký tự “D”.
//Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2207 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng chương trình client tương tác với server trên, sử dụng các luồng byte (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
//a.	Gửi một chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;D68C93F7"
//b.	Nhận lần lượt chuỗi đã bị mã hóa caesar và giá trị dịch chuyển s nguyên
//c.	Thực hiện giải mã ra thông điệp ban đầu và gửi lên Server
//d.	Đóng kết nối và kết thúc chương trình.

public class TCP_aQNKoVsH {
    public static void main(String[] args) {
        String studentCode = "B21DCCN324";
        String qCode = "aQNKoVsH";
        String host = "203.162.10.109";
        int port = 2207;
        try (Socket socket = new Socket(host, port)) {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(studentCode + ";" + qCode);
            dos.flush();
            String encrypted = dis.readUTF();
            int s = dis.readInt();
            StringBuilder decrypted = new StringBuilder();
            for (int i = 0; i < encrypted.length(); i++) {
                char c = encrypted.charAt(i);
                if (Character.isAlphabetic(c)) {
                    if (Character.isLowerCase(c)) {
                        char ch = (char) (c - s);
                        if (ch < 'a') decrypted.append((char) (c + (26 - s)));
                        else decrypted.append(ch);
                    } else if (Character.isUpperCase(c)) {
                        char ch = (char) (c - s);
                        if (ch < 'A') decrypted.append((char) (c + (26 - s)));
                        else decrypted.append(ch);
                    }
                } else decrypted.append(c);

            }
            dos.writeUTF(decrypted.toString());
            dos.flush();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
