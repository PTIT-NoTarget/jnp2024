package TCP;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

//[Mã câu hỏi (qCode): 6UvKRBlg].  Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
//a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;76B68B3B".
//b. Nhận dữ liệu từ server là một chuỗi các giá trị số nguyên được phân tách bởi ký tự ",". Ví dụ: 5,10,20,25,50,40,30,35.
//c. Tìm chuỗi con tăng dần dài nhất và gửi độ dài của chuỗi đó lên server theo format "chuỗi tăng dài nhất; độ dài". Ví dụ: 5,10,20,25,50;5
//d. Đóng kết nối và kết thúc chương trình.

public class TCP_6UvKRBlg {
    public static void main(String[] args) {
        String studentCode = "B21DCCN324";
        String qCode = "6UvKRBlg";
        String host = "203.162.10.109";
        int port = 2206;
        try (Socket socket = new Socket(host, port)) {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            String request = studentCode + ";" + qCode;
            os.write(request.getBytes());
            os.flush();


            byte[] data = new byte[2048];
            int len = is.read(data);
            String response = new String(data, 0, len);
            String[] str = response.split(",");
            List<Integer> list = new ArrayList<>();
            for (String item : str) {
                list.add(Integer.parseInt(item));
            }
            System.out.println(list);
            Collections.reverse(list);
            List<Integer> results = getResults(list);
            String result = results.stream().map(String::valueOf).collect(Collectors.joining(",")) + ";" + results.size();
            System.out.println(result);
            os.write(result.getBytes());
            os.flush();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    private static List<Integer> getResults(List<Integer> list) {
        List<Integer> results = new ArrayList<>();
        int[] dp = new int[list.size()];
        Arrays.fill(dp, 1);
        for (int i = 1; i < list.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (list.get(i) < list.get(j)) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int max = Arrays.stream(dp).max().orElseGet(() -> 0);
        int index = 0;
        for (int i = dp.length - 1; i >= 0; i--) {
            if (dp[i] == max) {
                index = i;
                break;
            }
        }
        System.out.println(index);
        for (int i = index; i >= 0; i--) {
            if (dp[i] == max) {
                results.add(list.get(i));
                max--;
            }
        }
        return results;
    }
}
