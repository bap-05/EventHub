package com.example.eventhub.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eventhub.Model.SuKien;
import com.example.eventhub.Model.SuKienSapToi;
import com.example.eventhub.R;


import java.util.ArrayList;
import java.util.List;

public class SuKienViewModel extends ViewModel {


    private static MutableLiveData<SuKienSapToi> sk = new MutableLiveData<>();
    private List<SuKienSapToi> suKienSapToiList = new ArrayList<>();
    private List<SuKien> suKienList = new ArrayList<>();
    public SuKienViewModel() {

    }

    public static MutableLiveData<SuKienSapToi> getSk() {
        return sk;
    }

    public void setSk(SuKienSapToi sk) {
        this.sk.postValue(sk);
    }

    public List loadSuKien()
    {
       suKienList.add(new SuKien("Ngày hội việc làm 2025",
               "https://drive.google.com/uc?export=view&id=1gcZhPW9rgqkcNyg_2aNU2CwFa-3AeLbC",
               "Sắp diễn ra",
               R.drawable.boy,
               R.drawable.boy,
               R.drawable.boy,
               R.drawable.boy,
               "2025-09-05 08:30:00"));
        suKienList.add(new SuKien(
                "Lễ hội văn hóa sinh viên 2025",
                "https://drive.google.com/uc?export=view&id=1gcZhPW9rgqkcNyg_2aNU2CwFa-3AeLbC",
                "Sắp diễn ra",
                R.drawable.boy,
                R.drawable.boy,
                R.drawable.boy,
                R.drawable.boy,
                "2025-10-12 17:00:00"
        ));

        suKienList.add(new SuKien(
                "Hội thảo công nghệ AI & IoT",
                "https://drive.google.com/uc?export=view&id=1gcZhPW9rgqkcNyg_2aNU2CwFa-3AeLbC",
                "Sắp diễn ra",
                R.drawable.boy,
                R.drawable.boy,
                R.drawable.boy,
                R.drawable.boy,
                "2025-11-20 13:00:00"
        ));

        suKienList.add(new SuKien(
                "Ngày hội câu lạc bộ 2025",
                "https://drive.google.com/uc?export=view&id=1gcZhPW9rgqkcNyg_2aNU2CwFa-3AeLbC",
                "Sắp diễn ra",
                R.drawable.boy,
                R.drawable.boy,
                R.drawable.boy,
                R.drawable.boy,
                "2025-08-18 08:00:00"
        ));

        suKienList.add(new SuKien(
                "Giải chạy Marathon sinh viên",
                "https://drive.google.com/uc?export=view&id=1gcZhPW9rgqkcNyg_2aNU2CwFa-3AeLbC",
                "Sắp diễn ra",
                R.drawable.boy,
                R.drawable.boy,
                R.drawable.boy,
                R.drawable.boy,
                "2025-12-03 06:00:00"
        ));
        return suKienList;
    }

    public List load_SK_danhchobn()
    {

        suKienSapToiList.add(new SuKienSapToi("Hội thao sinh viên 2025",R.drawable.poster,"Sắp diễn ra","2025-03-15 08:00:00","Cơ sở 1","Sân vận động trường","10","Sự kiện thể thao thường niên quy tụ sinh viên toàn trường với nhiều hoạt động hấp dẫn như bóng đá, cầu lông, chạy tiếp sức, kéo co... nhằm nâng cao tinh thần thể dục thể thao, đoàn kết giữa các khoa và lớp. Các đội chiến thắng sẽ nhận được huy chương và phần thưởng hấp dẫn từ ban tổ chức."));
        suKienSapToiList.add(new SuKienSapToi(
                "Ngày hội việc làm 2025",
                R.drawable.poster,
                "Sắp diễn ra",
                "2025-04-10 08:00:00",
                "Cơ sở 1",
                "Hội trường A",
                "5",
                "Ngày hội việc làm thường niên quy tụ hơn 50 doanh nghiệp, cung cấp cơ hội phỏng vấn, thực tập và tuyển dụng cho sinh viên năm cuối và sinh viên có nhu cầu."
        ));

        suKienSapToiList.add(new SuKienSapToi(
                "Hội thảo công nghệ AI & Blockchain",
                R.drawable.poster,
                "Sắp diễn ra",
                "2025-05-22 13:30:00",
                "Cơ sở 2",
                "Phòng hội thảo T2",
                "20",
                "Buổi hội thảo chia sẻ kiến thức về AI, Blockchain và ứng dụng trong đời sống. Khách mời gồm chuyên gia từ các công ty công nghệ hàng đầu."
        ));

        suKienSapToiList.add(new SuKienSapToi(
                "Lễ hội văn hóa quốc tế",
                R.drawable.poster,
                "Sắp diễn ra",
                "2025-06-01 17:00:00",
                "Cơ sở 1",
                "Sân trường",
                "15",
                "Sự kiện giao lưu văn hóa giữa sinh viên trường và sinh viên quốc tế với các tiết mục biểu diễn, ẩm thực, trò chơi truyền thống đặc sắc."
        ));

        suKienSapToiList.add(new SuKienSapToi(
                "Workshop: Kỹ năng thuyết trình",
                R.drawable.poster,
                "Sắp diễn ra",
                "2025-03-28 09:00:00",
                "Cơ sở 1",
                "Phòng 301",
                "30",
                "Workshop giúp sinh viên cải thiện kỹ năng nói trước đám đông, ngôn ngữ cơ thể, giọng nói và cách thu hút người nghe."
        ));

        suKienSapToiList.add(new SuKienSapToi(
                "Cuộc thi lập trình giải thuật 2025",
                R.drawable.poster,
                "Sắp diễn ra",
                "2025-07-12 08:30:00",
                "Cơ sở 2",
                "Phòng máy 5",
                "12",
                "Cuộc thi lập trình theo đội với các bài toán thuật toán từ cơ bản đến nâng cao. Đội thắng sẽ được cử đi thi vòng khu vực."
        ));
        return suKienSapToiList;
    }
}
