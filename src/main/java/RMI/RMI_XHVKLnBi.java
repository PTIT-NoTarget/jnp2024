package RMI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

//[Mã câu hỏi (qCode): XHVKLnBi].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý dữ liệu.
//Giao diện từ xa:
//public interface DataService extends Remote {
//    public Object requestData(String studentCode, String qCode) throws RemoteException;
//    public void submitData(String studentCode, String qCode, Object data) throws RemoteException;
//}
//Trong đó:
//        •	Interface DataService được viết trong package RMI.
//•	Đối tượng cài đặt giao diện từ xa DataService được đăng ký với RegistryServer với tên là: RMIDataService.
//Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu nhận được từ RMI Server:
//a. Triệu gọi phương thức requestData để nhận một số nguyên dương lớn từ server, gọi là N.
//b. Thực hiện phân rã số N thành các thừa số nguyên tố. Kết quả trả về là danh sách các thừa số nguyên tố của N.
//Ví dụ: Với N = 84, kết quả là danh sách “2, 2, 3, 7”.
//c. Triệu gọi phương thức submitData để gửi danh sách các thừa số nguyên tố đã tìm được trở lại server.
//d. Kết thúc chương trình client.

interface DataService extends Remote {
    public Object requestData(String studentCode, String qCode) throws RemoteException;
    public void submitData(String studentCode, String qCode, Object data) throws RemoteException;
}

public class RMI_XHVKLnBi {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        String studentCode = "B21DCCN324";
        String qCode = "XHVKLnBi";
        DataService dataService = (DataService) Naming.lookup("rmi://203.162.10.109/RMIDataService");
        int N = (int) dataService.requestData(studentCode, qCode);
        System.out.println("N = " + N);
        List<Integer> primeFactors = getPrimeFactors(N);
        System.out.println("Prime factors of N: " + primeFactors);
        dataService.submitData(studentCode, qCode, primeFactors);

    }

    public static List<Integer> getPrimeFactors(int N) {
        List<Integer> primeFactors = new ArrayList<>();
        for (int i = 2; i <= N; i++) {
            while (N % i == 0) {
                primeFactors.add(i);
                N /= i;
            }
        }
        return primeFactors;
    }
}
