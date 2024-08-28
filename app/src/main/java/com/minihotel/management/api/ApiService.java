package com.minihotel.management.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.minihotel.management.dto.ChiTietPhuThuRequest;
import com.minihotel.management.dto.ChiTietSuDungDichVuRequest;
import com.minihotel.management.dto.HoaDonResponse;
import com.minihotel.management.dto.KhachHangRequest;
import com.minihotel.management.dto.KhachThueRequest;
import com.minihotel.management.dto.PhieuThuePhongRequest;
import com.minihotel.management.dto.ResultResponse;
import com.minihotel.management.model.ChiTietPhieuThue;
import com.minihotel.management.model.ChiTietPhuThu;
import com.minihotel.management.model.ChiTietSuDungDichVu;
import com.minihotel.management.model.DichVu;
import com.minihotel.management.model.DoanhThuTheoNgay;
import com.minihotel.management.model.HangPhongDat;
import com.minihotel.management.model.HoaDonNgay;
import com.minihotel.management.model.KhachHang;
import com.minihotel.management.model.KhachThue;
import com.minihotel.management.model.NhanVien;
import com.minihotel.management.model.PhieuDat;
import com.minihotel.management.model.PhieuDatTheoNgay;
import com.minihotel.management.model.PhieuThue;
import com.minihotel.management.model.PhongHienTai;
import com.minihotel.management.model.PhongTrong;
import com.minihotel.management.model.PhuThu;
import com.minihotel.management.model.ThongTinHangPhong;
import com.minihotel.management.model.TrangThai;
import com.minihotel.management.utils.Containts;

import java.time.LocalDate;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl(Containts.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("api/thong-tin-phong/hien-tai")
    Call<List<PhongHienTai>> getAllPhongHienTai();

    @GET("api/phieu-dat/{id}")
    Call<PhieuDat> getPhieuDatById(@Path("id") int id);

    @GET("api/phieu-dat/chi-tiet-2/{id}")
    Call<List<HangPhongDat>> getChiTiet2sByIdPhieuDat(@Path("id") int id);

    @GET("api/thong-tin-phong/phong-trong")
    Call<List<PhongTrong>> getPhongTrongByIdHangPhong(@Query("idHangPhong") int idHangPhong,
                                                      @Query("ngayDenThue") String ngayDenThue,
                                                      @Query("ngayDiThue") String ngayDiThue);

    @GET("api/thong-tin-hang-phong/kiem-tra/thue-phong")
    Call<ResultResponse> kiemTraHangPhongTrongDeThue(@Query("idHangPhong") int idHangPhong,
                                                     @Query("ngayBatDauThueThem") String ngayBatDauThueThem,
                                                     @Query("ngayKetThucThueThem") String ngayKetThucThueThem);
    @GET("api/khach-hang/tim-kiem")
    Call<KhachHang> timKiemKhachHangTheoSdt(@Query("sdt") String sdt);

    @POST("api/phieu-thue/")
    Call<ResultResponse> thuePhongKhachSan(@Body PhieuThuePhongRequest phieuThuePhongRequest);

    @GET("api/chi-tiet/{id}")
    Call<ChiTietPhieuThue> getChiTietPhieuThueById(@Path("id") int id);
    @PUT("api/chi-tiet/ngay-di/{id}")
    Call<ChiTietPhieuThue> updateNgayDiChiTiet(@Path("id") int id, @Query("ngayDi") String ngayDi);

    @GET("api/dich-vu/all")
    Call<List<DichVu>> getAllDichVu();
    @GET("api/phu-thu/all")
    Call<List<PhuThu>> getAllPhuThu();

    @POST("api/chi-tiet/dich-vu")
    Call<List<ChiTietSuDungDichVu>> themChiTietSuDungDichVu(@Body ChiTietSuDungDichVuRequest chiTietSuDungDichVuRequest);

    @POST("api/chi-tiet/phu-thu")
    Call<List<ChiTietPhuThu>> themChiTietPhuThu(@Body ChiTietPhuThuRequest chiTietPhuThuRequest);

    @GET("api/chi-tiet/dich-vu/{idChiTietPhieuThue}")
    Call<List<ChiTietSuDungDichVu>> getChiTietSuDungDichVuByIdChiTietPhieuThue(@Path("idChiTietPhieuThue") int idChiTietPhieuThue);

    @GET("api/chi-tiet/phu-thu/{idChiTietPhieuThue}")
    Call<List<ChiTietPhuThu>> getChiTietPhuThuByIdChiTietPhieuThue(@Path("idChiTietPhieuThue") int idChiTietPhieuThue);

    @GET("api/phieu-dat/ngay")
    Call<List<PhieuDatTheoNgay>> getPhieuDatTheoNgay(@Query("ngay") String ngay);

    @GET("api/phieu-dat/thoi-gian")
    Call<List<PhieuDatTheoNgay>> getPhieuDatTheoThoiGian(@Query("ngayBatDauTim") String ngayBatDauTim,
                                                         @Query("ngayKetThucTim") String ngayKetThucTim);

    @GET("api/phieu-thue/{id}")
    Call<PhieuThue> getPhieuThueById(@Path("id") int idPhieuThue);

    @GET("api/phieu-thue/khach-hang")
    Call<List<PhieuThue>> getPhieuThueByCmnd(@Query("cmnd") String cmnd);

    @GET("api/chi-tiet/phieu-thue/{id}")
    Call<List<ChiTietPhieuThue>> getChiTietPhieuThueByIdPhieuThue(@Path("id") int idPhieuThue);

    @GET("api/trang-thai/all")
    Call<List<TrangThai>> getAllTrangThai();

    @POST("api/phong/trang-thai")
    Call<ResultResponse> capNhatTrangThaiPhong(@Query("idTrangThai") int idTrangThai,
                                               @Query("maPhong") String maPhong);

    @POST("api/khach-hang/")
    Call<KhachHang> themKhachHang(@Body KhachHangRequest request);

    @POST("api/chi-tiet/tra-phong")
    Call<HoaDonResponse> traPhongKhachLe(@Query("idNhanVien") Integer idNhanVien,
                                         @Query("tongTien") Long tongTien,
                                         @Query("ngayTao") String ngayTao,
                                         @Query("idChiTietPhieuThue") Integer idChiTietPhieuThue);

    @POST("api/chi-tiet/tra-phong/khach-doan")
    Call<HoaDonResponse> traPhongKhachDoan(@Query("idNhanVien") Integer idNhanVien,
                                         @Query("tongTien") Long tongTien,
                                         @Query("ngayTao") String ngayTao,
                                         @Query("idChiTietPhieuThues") List<Integer> idChiTietPhieuThues);

    @GET("api/hoa-don/hoa-don-ngay")
    Call<List<HoaDonNgay>> getHoaDonNgayHienTai();

    @GET("api/hoa-don/theo-ngay")
    Call<List<HoaDonNgay>> getHoaDonTheoNgay(@Query("ngay") String ngay);

    @GET("api/phieu-dat/khach-hang")
    Call<List<PhieuDat>> getPhieuDatTheoCMND(@Query("cmnd") String cmnd);

    @GET("api/thong-tin-hang-phong/thoi-gian-admin")
    Call<List<ThongTinHangPhong>> getThongTinHangPhongTheoThoiGian(@Query("ngayDenDat") String ngayDenDat,
                                                                   @Query("ngayDiDat") String ngayDiDat);

    @GET("api/hoa-don/doanh-thu/theo-ngay")
    Call<List<DoanhThuTheoNgay>> getDoanhThuTheoNgay(@Query("ngayBatDau") String ngayBatDau,
                                                     @Query("ngayKetThuc") String ngayKetThuc);

    @PUT("api/chi-tiet/doi-phong")
    Call<ResultResponse> doiPhongKhachSan(@Query("idChiTietPhieuThue") Integer idChiTietPhieuThue,
                                          @Query("maPhong") String maPhong);

    @GET("api/chi-tiet/khach-thue/hien-tai")
    Call<List<KhachThue>> getKhachThueHienTai();

    @POST("api/chi-tiet/add-khach-thue")
    Call<ResultResponse> addKhachThueToChiTietPhieuThue(@Body KhachThueRequest request);

    @DELETE("api/chi-tiet/del-khach-thue")
    Call<ResultResponse> delKhachThueInChiTietPhieuThue(@Query("idChiTietPhieuThue") int idChiTietPhieuThue,
                                                        @Query("idKhachThue") int idKhachThue);

    @GET("api/phieu-thue/kiem-tra-so-luong")
    Call<Boolean> kiemTraSoLuongPhongTra(@Query("idPhieuThue") int idPhieuThue,
                                         @Query("soLuong") int soLuong);

    @GET("api/khach-hang/phieu-dat")
    Call<KhachHang> getKhachHangByIdPhieuDat(@Query("idPhieuDat") int idPhieuDat);

    @POST("api/tai-khoan/nhan-vien/login")
    Call<NhanVien> getNhanVienDangNhap(@Query("tenDangNhap") String tenDangNhap,
                                       @Query("matKhau") String matKhau);
}
