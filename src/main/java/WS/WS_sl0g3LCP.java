package WS;

import vn.medianews.ObjectService;
import vn.medianews.ObjectService_Service;
import vn.medianews.ProductY;

//[Mã câu hỏi (qCode): sl0g3LCP].  Một dịch vụ web được định nghĩa và mô tả trong tệp ObjectService.wsdl, được triển khai trên server tại URL http://<Exam_IP>:8080/JNPWS/ObjectService?wsdl để xử lý các bài toán với đối tượng.
//Yêu cầu: Viết chương trình tại máy trạm (WS client) để giao tiếp với ObjectService thực hiện các công việc sau:
//a. Triệu gọi phương thức requestProductY với tham số đầu vào là mã sinh viên (studentCode) và mã câu hỏi (qCode) để nhận về một đối tượng ProductY từ server. Đối tượng này có các thuộc tính:
//        •	price (giá gốc): float, đại diện cho giá của sản phẩm.
//•	taxRate (thuế): float, đại diện cho phần trăm thuế áp dụng trên giá gốc.
//•	discount (chiết khấu): float, đại diện cho phần trăm chiết khấu áp dụng trên giá gốc.
//b. Thực hiện
//•	Tính toán giá cuối cùng của sản phẩm (finalPrice) theo công thức:
//finalPrice = price * (1 + taxRate / 100) * (1 - discount / 100)
//        •	Cập nhật thuộc tính finalPrice trong đối tượng Product với giá trị đã tính toán.
//c. Triệu gọi phương thức submitProductY(String studentCode, String qCode, ProductY object) để gửi đối tượng ProductY với giá cuối cùng đã được tính toán trở lại server.
//Ví dụ: Nếu đối tượng ProductY có các thuộc tính price = 100.0, taxRate = 10.0, và discount = 5.0, thì finalPrice sẽ được tính là:
//finalPrice = 100 * (1 + 10/100) * (1 - 5/100) = 104.5
//Đối tượng ProductY với giá trị finalPrice = 104.5 sẽ được gửi lại server qua phương thức submitProductY.
//d. Kết thúc chương trình client.
public class WS_sl0g3LCP {
    public static void main(String[] args) {
        String studentCode = "B21DCCN324";
        String qCode = "sl0g3LCP";
        ObjectService_Service service = new ObjectService_Service();
        ObjectService port = service.getObjectServicePort();
        ProductY productY = port.requestProductY(studentCode, qCode);
        System.out.println(productY);
        float finalPrice = productY.getPrice() * (1 + productY.getTaxRate() / 100) * (1 - productY.getDiscount() / 100);
        productY.setFinalPrice(finalPrice);
        System.out.println(productY);
        port.submitProductY(studentCode, qCode, productY);
    }
}
