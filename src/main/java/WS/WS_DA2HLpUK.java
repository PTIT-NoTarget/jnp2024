package WS;

import vn.medianews.CharacterService;
import vn.medianews.CharacterService_Service;

import java.util.Comparator;
import java.util.List;

//[Mã câu hỏi (qCode): DA2HLpUK].  Một dịch vụ web được định nghĩa và mô tả trong tệp CharacterService.wsdl, được triển khai trên server tại URL http://<Exam_IP>:8080/JNPWS/CharacterService?wsdl để xử lý các bài toán về chuỗi và ký tự.
//Yêu cầu: Viết chương trình tại máy trạm (WS client) để giao tiếp với CharacterService thực hiện các công việc sau:
//a. Triệu gọi phương thức requestStringArray với tham số đầu vào là mã sinh viên (studentCode) và mã câu hỏi (qCode) để nhận về một danh sách chuỗi (List<String>) từ server..
//b. Sắp xếp mảng theo độ dài của từ theo thứ tự tăng dần độ dài của từ.
//c. Triệu gọi phương thức submitCharacterStringArray(String studentCode, String qCode, List<String> data) để gửi mảng chuỗi đã sắp xếp trở lại server.
//Ví dụ: Nếu mảng chuỗi nhận được từ phương thức requestCharacter là ["apple", "banana", "fig", "pineapple"] mảng sắp xếp tăng dần theo độ dài sẽ là ["fig", "apple", "banana", "pineapple"].
//d. Kết thúc chương trình client.

public class WS_DA2HLpUK {
    public static void main(String[] args) {
        String studentCode = "B21DCCN324";
        String qCode = "DA2HLpUK";
        CharacterService_Service service = new CharacterService_Service();
        CharacterService port = service.getCharacterServicePort();
        List<String> data = port.requestStringArray(studentCode, qCode);
        System.out.println(data);
        data.sort(Comparator.comparingInt(String::length));
        System.out.println(data);
        port.submitCharacterStringArray(studentCode, qCode, data);
    }
}
