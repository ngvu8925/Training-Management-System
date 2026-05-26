# University Training Management System

Hệ thống quản lý đào tạo đại học được xây dựng bằng Spring Boot, SQL Server và giao diện quản trị AdminLTE. Dự án tập trung vào các nghiệp vụ cốt lõi của phòng đào tạo như quản lý sinh viên, người dùng, vai trò, quyền hạn, năm học, học kỳ, học phần, lớp học phần và phân công giảng dạy.

## Tổng quan

University Training Management System là một ứng dụng web hỗ trợ số hóa quy trình quản lý đào tạo trong môi trường đại học. Backend cung cấp RESTful API theo kiến trúc phân lớp, kết nối SQL Server thông qua Spring Data JPA, tích hợp validation dữ liệu đầu vào và tài liệu API bằng OpenAPI/Swagger UI.

Dự án phù hợp để thể hiện trong CV các năng lực:

- Thiết kế và triển khai REST API với Spring Boot.
- Xây dựng mô hình dữ liệu quan hệ cho bài toán quản lý đào tạo.
- Tổ chức source code theo mô hình Controller - Service - Repository - Entity - DTO.
- Xử lý CRUD, tìm kiếm, lọc dữ liệu và validation request.
- Tích hợp SQL Server, JPA/Hibernate và giao diện quản trị AdminLTE.

## Tính năng chính

- Quản lý người dùng, vai trò, quyền và phân quyền theo vai trò.
- Quản lý hồ sơ sinh viên, thêm/sửa/xóa/tìm kiếm sinh viên.
- Quản lý môn học, năm học, học kỳ và trạng thái học kỳ đang hoạt động.
- Quản lý lớp học phần, lọc theo học kỳ, môn học và trạng thái.
- Quản lý sinh viên đăng ký vào lớp học phần.
- Quản lý nhân sự/giảng viên và phân công giảng viên cho lớp học phần.
- Cung cấp API dạng REST phục vụ tích hợp frontend.
- Tích hợp giao diện quản trị tĩnh dựa trên AdminLTE.
- Tài liệu hóa API thông qua Swagger UI.

## Công nghệ sử dụng

| Nhóm | Công nghệ |
| --- | --- |
| Backend | Java 21, Spring Boot 3.4.1 |
| API | Spring Web, RESTful API |
| Persistence | Spring Data JPA, Hibernate |
| Database | Microsoft SQL Server |
| Validation | Jakarta Bean Validation |
| API Docs | Springdoc OpenAPI / Swagger UI |
| Frontend assets | HTML, CSS, JavaScript, AdminLTE |
| Build tool | Maven |

## Kiến trúc dự án

```text
src/main/java/com/example/demo
|-- DemoApplication.java
|-- n1                          # User, role, permission
|   |-- controller
|   |-- model/entity
|   |-- repository
|   `-- service
|-- n2                          # Student management
|   |-- controller
|   |-- dto
|   |-- exception
|   |-- model/entity
|   |-- repository
|   `-- service
|-- n5                          # Academic year, semester, course section
|   |-- controller
|   |-- dto
|   |-- exception
|   |-- model/entity
|   |-- repository
|   `-- service
`-- web                         # Static resource configuration
```

## Một số API tiêu biểu

| Module | Endpoint | Mô tả |
| --- | --- | --- |
| Users | `GET /api/users` | Lấy danh sách người dùng |
| Users | `POST /api/users` | Tạo người dùng |
| Users | `POST /api/users/{userId}/roles/{roleId}` | Gán vai trò cho người dùng |
| Roles | `POST /api/roles/{roleId}/permissions/{permId}` | Gán quyền cho vai trò |
| Students | `GET /api/students` | Lấy danh sách sinh viên |
| Students | `GET /api/students/search?fullname=...` | Tìm kiếm sinh viên theo họ tên |
| Students | `POST /api/students` | Tạo hồ sơ sinh viên |
| Students | `PUT /api/students/{id}` | Cập nhật hồ sơ sinh viên |
| Students | `DELETE /api/students/{id}` | Xóa sinh viên |
| Academic Years | `GET /api/academic-years` | Quản lý năm học |
| School Years | `GET /api/school-years` | Quản lý khóa/năm đào tạo |
| Semesters | `GET /api/semesters/active` | Lấy học kỳ đang hoạt động |
| Course Sections | `GET /api/course-sections/filter` | Lọc lớp học phần |
| Course Sections | `PUT /api/course-sections/{id}/status` | Cập nhật trạng thái lớp học phần |
| Lecturer Assignment | `GET /api/lecturer-course-classes/schedule` | Tra cứu lịch phân công giảng dạy |

## Cơ sở dữ liệu

Script khởi tạo cơ sở dữ liệu nằm tại:

```text
document/database/script_schoolmanager.sql
```

Database được thiết kế cho nghiệp vụ quản lý đào tạo, bao gồm các nhóm bảng chính:

- Người dùng, vai trò, quyền hạn.
- Sinh viên và trạng thái học tập.
- Giảng viên, nhân sự, khoa/bộ môn.
- Môn học, năm học, học kỳ.
- Lớp học phần, đăng ký học phần, phân công giảng viên.
- Phòng học, tòa nhà, lịch học và các dữ liệu mở rộng.

## Hình ảnh giao diện

Bạn có thể lưu ảnh giao diện vào thư mục:

```text
document/images
```

Sau đó thay tên file hoặc đường dẫn ảnh GitHub vào các vị trí bên dưới.

### Dashboard tổng quan

<img width="3839" height="2153" alt="image" src="https://github.com/user-attachments/assets/26449584-4f70-47f5-8f10-a6166e458908" />


### Quản lý sinh viên

<img width="3809" height="2142" alt="image" src="https://github.com/user-attachments/assets/1563dca0-5a88-40e2-b2d0-3b9b5eb7c6bf" />


### Quản lý lớp học phần

<img width="3819" height="1956" alt="image" src="https://github.com/user-attachments/assets/fbb09824-3040-4731-a28f-bd7baed0bd5a" />


### Quản lý sinh viên lớp học phần

<img width="3819" height="1957" alt="image" src="https://github.com/user-attachments/assets/391602c2-1d3c-4f17-b7bc-b3a50b054e32" />


### Phân công giảng viên lớp học phần

<img width="3839" height="1962" alt="image" src="https://github.com/user-attachments/assets/06a6f315-13fc-44c7-b7b7-a1a403516151" />


### Tài liệu API Swagger

![Tài liệu API Swagger](document/images/06-swagger-api.png)

## Cài đặt và chạy dự án

### Yêu cầu môi trường

- Java 21+
- Maven 3.9+ hoặc Maven Wrapper có sẵn trong dự án
- SQL Server
- Git

### 1. Clone repository

```bash
git clone <repository-url>
cd quanlydaotao
```

### 2. Tạo database

Tạo database SQL Server tên `schoolmanager`, sau đó chạy script:

```text
document/database/script_schoolmanager.sql
```

### 3. Cấu hình kết nối database

Cập nhật file:

```text
src/main/resources/application.properties
```

Ví dụ:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=schoolmanager;encrypt=true;trustServerCertificate=true;
spring.datasource.username=sa
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=none
```

### 4. Chạy ứng dụng

Trên Windows:

```bash
mvnw.cmd spring-boot:run
```

Trên macOS/Linux:

```bash
./mvnw spring-boot:run
```

Ứng dụng mặc định chạy tại:

```text
http://localhost:8080
```

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

## Giao diện quản trị

Dự án có tích hợp AdminLTE trong thư mục:

```text
src/main/resources/static/adminlte
```

Một số trang tĩnh đáng chú ý:

- `/students/students.html`
- `/n5/manage-n5.html`
- `/index.html`

## Điểm nổi bật kỹ thuật

- Thiết kế API theo chuẩn REST với các HTTP method rõ ràng.
- Tách DTO request/response để kiểm soát dữ liệu trao đổi qua API.
- Sử dụng `@Valid` và Jakarta Validation để kiểm tra dữ liệu đầu vào.
- Tổ chức service layer riêng để xử lý nghiệp vụ và giảm phụ thuộc giữa controller và repository.
- Sử dụng UUID làm khóa định danh cho nhiều entity.
- Hỗ trợ tìm kiếm, lọc và cập nhật trạng thái ở các nghiệp vụ quan trọng.
- Tài liệu API tự động bằng Springdoc OpenAPI.

## Định hướng phát triển

- Hoàn thiện xác thực và phân quyền bằng Spring Security/JWT.
- Bổ sung unit test và integration test cho service/controller.
- Chuẩn hóa response format và error response toàn hệ thống.
- Thêm dashboard thống kê đào tạo.
- Triển khai CI/CD và deploy lên môi trường cloud.
- Tách frontend thành ứng dụng SPA độc lập nếu cần mở rộng trải nghiệm người dùng.

## Vai trò cá nhân

- Phân tích nghiệp vụ quản lý đào tạo đại học.
- Thiết kế database và mô hình entity.
- Xây dựng REST API với Spring Boot, JPA và SQL Server.
- Triển khai các module quản lý sinh viên, học kỳ, học phần, lớp học phần và phân quyền.
- Tích hợp giao diện quản trị AdminLTE và tài liệu API Swagger.

## Tác giả

Dự án được phát triển nhằm phục vụ học tập, rèn luyện kỹ năng xây dựng backend thực tế và bổ sung portfolio/CV cho vị trí Java Backend Developer.
