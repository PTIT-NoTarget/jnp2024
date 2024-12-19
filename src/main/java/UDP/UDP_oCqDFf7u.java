package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//[Mã câu hỏi (qCode): oCqDFf7u].  Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client tương tác với server kịch bản dưới đây:
//a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;EE29C059”
//b.	Nhận thông điệp từ server theo định dạng “requestId; data”
//        -	requestId là một chuỗi ngẫu nhiên duy nhất
//-	data là chuỗi dữ liệu đầu vào cần xử lý
//Ex: “requestId;Qnc8d5x78aldSGWWmaAAjyg3”
//c.	Tìm kiếm ký tự xuất hiện nhiều nhất trong chuỗi và gửi lên server theo định dạng “requestId;ký tự xuất hiện nhiều nhất: các vị trí xuất hiện ký tự đó”
//ví dụ: “requestId;8:4,9,”
//d.	Đóng socket và kết thúc chương trình

public class UDP_oCqDFf7u {
    public static void main(String[] args) throws Exception {
        String studentCode = "B21DCCN324";
        String qCode = "oCqDFf7u";
        String host = "203.162.10.109";
        int port = 2208;

        DatagramSocket socket = new DatagramSocket();

        InetAddress address = InetAddress.getByName(host);
        String code = ";" + studentCode + ";" + qCode;
        DatagramPacket sendPacket = new DatagramPacket(code.getBytes(), code.length(), address, port);
        socket.send(sendPacket);

        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);

        String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
        System.out.println(response);
        String[] parts = response.split(";");
        String requestId = parts[0];

        String data = parts[1];
        int[] count = new int[256];
        for (int i = 0; i < data.length(); i++) {
            count[data.charAt(i)]++;
        }
        int max = 0;
        char c = 0;
        for (int i = 0; i < data.length(); i++) {
            if (count[data.charAt(i)] > max) {
                max = count[data.charAt(i)];
                c = data.charAt(i);
                count[data.charAt(i)] = 0;
            }
        }
        StringBuilder result = new StringBuilder(requestId + ";" + c + ":");
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == c) {
                result.append(i + 1).append(",");
            }
        }
        System.out.println(result);
        DatagramPacket sendPacket2 = new DatagramPacket(result.toString().getBytes(), result.length(), address, port);
        socket.send(sendPacket2);

        socket.close();
    }
}
