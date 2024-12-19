package RMI;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

//[Mã câu hỏi (qCode): VxIlvHlr].  Một chương trình (tạm gọi là RMI server) cung cấp giao diện cho phép triệu gọi từ xa với thông tin như sau:
//        -	Giao diện từ xa
//public interface ObjectService extends Remote {
//    public Serializable requestObject(String studentCode, String qAlias) throws RemoteException;
//
//    public void submitObject(String studentCode, String qAlias, Serializable object) throws RemoteException;
//}
//-	Lớp Product gồm các thông tin: id String, code String, importPrice double, exportPrice double.
//Trường dữ liệu: private static final long serialVersionUID = 20151107L;
//    02 hàm khởi dựng
//public Product()
//public Product(int String, String code,double ImportPrice, double ExportPrice)
//Trong đó:
//        -	interface ObjectService và lớp Product được viết trong package RMI
//-	Đối tượng cài đặt giao diện từ xa ObjectService được đăng ký với RegistryServer: RMIObjectService
//
//Yêu cầu yêu cầu viết chương trình tại máy trạm (RMI client) thực hiện chuẩn hóa sản phẩm theo thứ tự:
//a.	Triệu gọi phương thức requestObject để lấy về đối tượng sản phẩm cần chuẩn hóa.
//b.	Thực hiện chuẩn hóa đối tượng nhận được theo nguyên tắc:
//        - Chuyển mã sản phẩm thành in hoa.
//        - Cập nhật giá xuất (exportPrice) bằng giá nhập (importPrice) + 20%
//
//c.  Triệu gọi phương thức submitObject để gửi dữ liệu đã chuẩn hóa
//d.  Kết thúc chương trình client

interface ObjectService extends Remote {
    public Serializable requestObject(String studentCode, String qAlias) throws RemoteException;

    public void submitObject(String studentCode, String qAlias, Serializable object) throws RemoteException;
}

class Product implements Serializable {
    private static final long serialVersionUID = 20151107L;
    private String id;
    private String code;
    private double importPrice;
    private double exportPrice;

    public Product() {
    }

    public Product(String id, String code, double importPrice, double exportPrice) {
        this.id = id;
        this.code = code;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
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

    public double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(double importPrice) {
        this.importPrice = importPrice;
    }

    public double getExportPrice() {
        return exportPrice;
    }

    public void setExportPrice(double exportPrice) {
        this.exportPrice = exportPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", importPrice=" + importPrice +
                ", exportPrice=" + exportPrice +
                '}';
    }
}

public class RMI_VxIlvHlr {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        String studentCode = "B21DCCN324";
        String qCode = "VxIlvHlr";
        ObjectService objectService = (ObjectService) Naming.lookup("rmi://203.162.10.109/RMIObjectService");
        RMI.Product product = (RMI.Product) objectService.requestObject(studentCode, qCode);
        System.out.println("Before: " + product);
        product.setCode(product.getCode().toUpperCase());
        product.setExportPrice(product.getImportPrice() * 1.2);
        objectService.submitObject(studentCode, qCode, product);
        System.out.println("After: " + product);
    }
}
