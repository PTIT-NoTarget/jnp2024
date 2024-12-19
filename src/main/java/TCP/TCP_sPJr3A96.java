package TCP;

//[Mã câu hỏi (qCode): sPJr3A96].  Thông tin sản phẩm vì một lý do nào đó đã bị sửa đổi thành không đúng, cụ thể:
//a) Tên sản phẩm bị đổi ngược từ đầu tiên và từ cuối cùng, ví dụ: “lenovo thinkpad T520” bị chuyển thành “T520 thinkpad lenovo”
//b) Số lượng sản phẩm cũng bị đảo ngược giá trị, ví dụ từ 9981 thành 1899
//
//Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectInputStream / ObjectOutputStream) để gửi/nhận và sửa các thông tin bị sai của sản phẩm. Chi tiết dưới đây:
//a) Đối tượng trao đổi là thể hiện của lớp Product917 được mô tả như sau
//      •	Tên đầy đủ của lớp: TCP.Product917
//      •	Các thuộc tính: id int, code String, name String, quantity int
//      •	Hàm khởi tạo đầy đủ các thuộc tính được liệt kê ở trên
//      •	Trường dữ liệu: private static final long serialVersionUID = 917;
//b)	Tương tác với server theo kịch bản
//1)	Gửi đối tượng là chuỗi chứa mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;5AD2B818"
//        2)	Receive một đối tượng là thể hiện của lớp Product917 từ server
//3)	Sửa các thông tin sai của sản phẩm về tên và số lượng.  Gửi đối tượng vừa được sửa sai lên server
//4)	Đóng socket và kết thúc chương trình.
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class TCP_sPJr3A96 {
    public static void main(String[] args) {
        String studentCode = "B21DCCN324";
        String qCode = "sPJr3A96";
        String host = "203.162.10.109";
        int port = 2209;
        try (Socket socket = new Socket(host, port)) {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(studentCode + ";" + qCode);
            oos.flush();

            TCP.Laptop product = (TCP.Laptop) ois.readObject();
            System.out.println("Before: " + product);
            String[] name = product.getName().split(" ");
            StringBuilder newName = new StringBuilder();
            newName.append(name[name.length - 1]).append(" ");
            for (int i = 1; i < name.length - 1; i++) {
                newName.append(name[i]).append(" ");
            }
            newName.append(name[0]);
            product.setName(newName.toString());
            product.setQuantity(Integer.parseInt(new StringBuilder(String.valueOf(product.getQuantity())).reverse().toString()));
            System.out.println("After: " + product);
            oos.writeObject(product);
            oos.flush();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


class Laptop implements Serializable {
    private static final long serialVersionUID = 20150711;

    private int id;
    private String code;
    private String name;
    private int quantity;

    public Laptop(int id, String code, String name, int quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product917{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
