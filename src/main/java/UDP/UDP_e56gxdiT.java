package UDP;

//[Mã câu hỏi (qCode): e56gxdiT].  Thông tin khách hàng được yêu cầu thay đổi định dạng lại cho phù hợp với khu vực, cụ thể:
//a.	Tên khách hàng cần được chuẩn hóa theo định dạng mới. Ví dụ: nguyen van hai duong -> DUONG, Nguyen Van Hai
//b.	Ngày sinh của khách hàng đang ở dạng mm-dd-yyyy, cần được chuyển thành định dạng dd/mm/yyyy. Ví dụ: 10-11-2012 -> 11/10/2012
//c.	Tài khoản khách hàng được tạo từ các chữ cái in thường được sinh tự động từ họ tên khách hàng. Ví dụ: nguyen van hai duong -> nvhduong
//
//
//Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client giao tiếp với server theo mô tả sau:
//a.	Đối tượng trao đổi là thể hiện của lớp UDP.Customer được mô tả như sau
//•	Tên đầy đủ của lớp: UDP.Customer
//•	Các thuộc tính: id String, code String, name String, , dayOfBirth String, userName String
//•	Một Hàm khởi tạo với đầy đủ các thuộc tính được liệt kê ở trên
//•	Trường dữ liệu: private static final long serialVersionUID = 20151107;
//
//b.	Client giao tiếp với server theo các bước
//•       Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;EE29C059”
//
//        •	Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Customer từ server. Trong đó, các thuộc tính id, code, name,dayOfBirth đã được thiết lập sẵn.
//        •	Yêu cầu thay đổi thông tin các thuộc tính như yêu cầu ở trên và gửi lại đối tượng khách hàng đã được sửa đổi lên server với cấu trúc:
//        08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Customer đã được sửa đổi.
//•	Đóng socket và kết thúc chương trình.

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class Customer implements Serializable {
    private static final long serialVersionUID = 20151107;
    private String id;
    private String code;
    private String name;
    private String dayOfBirth;
    private String userName;

    public Customer(String id, String code, String name, String dayOfBirth, String userName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.dayOfBirth = dayOfBirth;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(String dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", dayOfBirth='" + dayOfBirth + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}

public class UDP_e56gxdiT {
    public static void main(String[] args) throws Exception {
        String studentCode = "B21DCCN324";
        String qCode = "e56gxdiT";
        String host = "203.162.10.109";
        int port = 2209;
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName(host);
        String code = ";" + studentCode + ";" + qCode;
        DatagramPacket sendPacket = new DatagramPacket(code.getBytes(), code.length(), address, port);
        socket.send(sendPacket);
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        byte[] data = receivePacket.getData();
        String requestId = new String(data, 0, 8);
        byte[] customerData = new byte[data.length - 8];
        System.arraycopy(data, 8, customerData, 0, data.length - 8);
        ByteArrayInputStream in = new ByteArrayInputStream(customerData);
        ObjectInputStream is = new ObjectInputStream(in);
        UDP.Customer customer = (UDP.Customer) is.readObject();
        String[] nameParts = customer.getName().split(" ");
        StringBuilder name = new StringBuilder();
        name.append(nameParts[nameParts.length - 1].toUpperCase()).append(", ");
        for(int i = 0; i < nameParts.length - 1; i++) {
            name.append(nameParts[i].substring(0, 1).toUpperCase()).append(nameParts[i].substring(1).toLowerCase()).append(" ");
        }
        customer.setName(name.toString().trim());
        String[] parts = customer.getDayOfBirth().split("-");
        customer.setDayOfBirth(parts[1] + "/" + parts[0] + "/" + parts[2]);
        StringBuilder userName = new StringBuilder();
        for(int i = 0; i < nameParts.length - 1; i++) {
            userName.append(nameParts[i].substring(0, 1).toLowerCase());
        }
        userName.append(nameParts[nameParts.length - 1].toLowerCase());
        customer.setUserName(userName.toString());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(customer);
        byte[] sendData = out.toByteArray();
        byte[] sendRequestId = requestId.getBytes();
        byte[] send = new byte[sendRequestId.length + sendData.length];
        System.arraycopy(sendRequestId, 0, send, 0, sendRequestId.length);
        System.arraycopy(sendData, 0, send, sendRequestId.length, sendData.length);
        DatagramPacket sendPacket2 = new DatagramPacket(send, send.length, address, port);
        socket.send(sendPacket2);

        socket.close();
    }
}
