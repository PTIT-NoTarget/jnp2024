package TCP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

//[Mã câu hỏi (qCode): oa3cfqUU].  [Loại bỏ ký tự đặc biệt, trùng và giữ nguyên thứ tự xuất hiện] Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác tới server sử dụng các luồng ký tự (BufferedReader/BufferedWriter) theo kịch bản dưới đây:
//a.	Gửi một chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;7D6265E3"
//b.	Nhận một chuỗi ngẫu nhiên từ server
//c.	Loại bỏ ký tự đặc biệt, số, ký tự trùng và giữ nguyên thứ tự xuất hiện của ký tự. Gửi chuỗi đã được xử lý lên server.
//d.	Đóng kết nối và kết thúc chương trình

public class TCP_oa3cfqUU {
    public static void main(String[] args) {
        String studentCode = "B21DCCN324";
        String qCode = "oa3cfqUU";
        String host = "203.162.10.109";
        int port = 2208;
        try (Socket socket = new Socket(host, port)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write(studentCode + ";" + qCode);
            bw.newLine();
            bw.flush();

            String response = br.readLine();
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < response.length(); i++) {
                char c = response.charAt(i);
                if (Character.isAlphabetic(c) && result.indexOf(String.valueOf(c)) == -1) {
                    result.append(c);
                }
            }
            System.out.println(response + " -> " + result);
            bw.write(result.toString());
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
