package UDP;


//[Mã câu hỏi (qCode): BD3237UC].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
//a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;371EA16D”
//b.	Nhận thông điệp là một chuỗi từ server theo định dạng “requestId; n; A1,A2,...An”, với
//-	requestId là chuỗi ngẫu nhiên duy nhất
//-	n là một số ngẫu nhiên nhỏ hơn 100.
//        -            A1, A2 ... Am với m <= n là các giá trị nguyên liên tiếp, nhỏ hơn hoặc bằng n và không trùng nhau.
//Ví dụ: requestId;10;2,3,5,6,9
//c.	Tìm kiếm các giá trị còn thiếu và gửi lên server theo định dạng “requestId;B1,B2,...,Bm”
//Ví dụ: requestId;1,4,7,8,10
//d.	Đóng socket và kết thúc chương trình.

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_BD3237UC {
    public static void main(String[] args) throws Exception {
        String studentCode = "B21DCCN324";
        String qCode = "BD3237UC";
        String host = "203.162.10.109";
        int port = 2207;

        DatagramSocket socket = new DatagramSocket();

        InetAddress address = InetAddress.getByName(host);
        String code = ";" + studentCode + ";" + qCode;
        DatagramPacket sendPacket = new DatagramPacket(code.getBytes(), code.length(), address, port);
        socket.send(sendPacket);

        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);

        String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
        String[] parts = response.split(";");
        String requestId = parts[0];
        int n = Integer.parseInt(parts[1]);
        String[] values = parts[2].split(",");
        StringBuilder missing = new StringBuilder();
        int[] arr = new int[n+1];
        for (String value : values) {
            arr[Integer.parseInt(value)] = 1;
        }
        for (int i = 1; i <= n; i++) {
            if (arr[i] == 0) {
                missing.append(i).append(",");
            }
        }
        missing.deleteCharAt(missing.length() - 1);
        String result = requestId + ";" + missing;
        System.out.println(result);
        DatagramPacket sendPacket2 = new DatagramPacket(result.getBytes(), result.length(), address, port);
        socket.send(sendPacket2);

        socket.close();
    }
}
