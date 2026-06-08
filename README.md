# Fashion Shop — Backend

REST API backend cho ứng dụng thương mại điện tử thời trang, xây dựng bằng Spring Boot 3.4.4. Hỗ trợ đầy đủ luồng mua hàng cho Customer và quản trị cho Admin.

---

## Tech Stack

| | |
|---|---|
| **Framework** | Spring Boot 3.4.4 · Java 21 |
| **Security** | Spring Security · JWT (Access Token + Refresh Token) · BCrypt |
| **Database** | MySQL 8 · Spring Data JPA · Hibernate |
| **API Docs** | SpringDoc OpenAPI (Swagger UI) |
| **Build** | Maven |
| **Thư viện khác** | Lombok · JJWT 0.12.3 |

---

## Yêu cầu

- Java 21+
- Maven 3.8+
- MySQL 8+

---

## Cài đặt và chạy

**1. Clone repository**
```bash
git clone https://github.com/vnkienHP2808/fashion-shop-be.git
cd fashion-shop-be/fashionshop
```

**2. Tạo database**
```sql
CREATE DATABASE fashionshop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

**3. Cấu hình `application.properties`**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fashionshop?useUnicode=true&characterEncoding=UTF-8
spring.datasource.username=your_username
spring.datasource.password=your_password

# Thư mục lưu ảnh sản phẩm (tạo thư mục này trước)
image.upload-dir=D:/your-path/images/

# JWT
jwt.secret=your-secret-key-at-least-32-characters-long
jwt.access-expiration=15       # phút
jwt.refresh-expiration=10080   # phút (7 ngày)
```

**4. Chạy ứng dụng**
```bash
mvn spring-boot:run
```

Server khởi động tại `http://localhost:8080`

---

## API Documentation

Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## API Endpoints

### Auth — `/auth`

| Method | Endpoint | Mô tả | Auth |
|--------|----------|-------|------|
| POST | `/auth/sign-up` | Đăng ký tài khoản | Public |
| POST | `/auth/sign-in` | Đăng nhập, nhận Access + Refresh Token | Public |
| POST | `/auth/refresh` | Cấp Access Token mới từ Refresh Token | Public |
| POST | `/auth/change-password` | Đổi mật khẩu | Customer, Admin |

### Products — `/api/products`

| Method | Endpoint | Mô tả | Auth |
|--------|----------|-------|------|
| GET | `/api/products` | Lấy danh sách sản phẩm (filter + phân trang) | Public |
| GET | `/api/products/new` | Sản phẩm mới về | Public |
| GET | `/api/products/sale` | Sản phẩm giảm giá | Public |
| GET | `/api/products/search` | Tìm kiếm sản phẩm | Public |
| GET | `/api/products/{id}` | Chi tiết sản phẩm | Public |
| GET | `/api/products/category/{id}` | Sản phẩm theo danh mục | Public |
| GET | `/api/products/category/{id}/subcategory/{id}` | Sản phẩm theo danh mục con | Public |
| PUT | `/api/products/update-quantity-and-sold` | Cập nhật tồn kho sau khi đặt hàng | Customer |
| POST | `/api/products/create` | Tạo sản phẩm mới (multipart) | Admin |
| PUT | `/api/products/{id}` | Cập nhật sản phẩm (multipart) | Admin |
| DELETE | `/api/products/{id}` | Xóa sản phẩm | Admin |

### Categories — `/api/categories`

| Method | Endpoint | Mô tả | Auth |
|--------|----------|-------|------|
| GET | `/api/categories` | Lấy danh sách danh mục | Public |

### Cart — `/api/cart`

| Method | Endpoint | Mô tả | Auth |
|--------|----------|-------|------|
| GET | `/api/cart/{userId}` | Xem giỏ hàng | Customer |
| POST | `/api/cart/add` | Thêm sản phẩm vào giỏ | Customer |
| PUT | `/api/cart/update` | Cập nhật số lượng | Customer |
| DELETE | `/api/cart/{userId}/remove/{productId}/{size}` | Xóa một sản phẩm | Customer |
| DELETE | `/api/cart/{userId}/clear` | Xóa toàn bộ giỏ hàng | Customer |

### Orders — `/api/orders`

| Method | Endpoint | Mô tả | Auth |
|--------|----------|-------|------|
| POST | `/api/orders` | Đặt hàng | Customer |
| GET | `/api/orders/user/{userId}` | Đơn hàng của user (filter + phân trang) | Customer |
| GET | `/api/orders` | Tất cả đơn hàng (filter + phân trang) | Admin |
| PUT | `/api/orders/{id}` | Cập nhật trạng thái đơn hàng | Admin |

### Users — `/api/users`

| Method | Endpoint | Mô tả | Auth |
|--------|----------|-------|------|
| GET | `/api/users/{id}` | Thông tin user | Customer, Admin |
| PUT | `/api/users/{id}/phones` | Cập nhật số điện thoại | Customer, Admin |
| PUT | `/api/users/{id}/addresses` | Cập nhật địa chỉ | Customer, Admin |
| GET | `/api/users` | Danh sách tất cả user | Admin |
| PUT | `/api/users/{id}/status` | Kích hoạt / vô hiệu hóa tài khoản | Admin |

---

## Cơ chế xác thực JWT

```
Đăng nhập
  POST /auth/sign-in → { accessToken (15 phút), refreshToken (7 ngày) }

Gọi API cần xác thực
  Header: Authorization: Bearer <accessToken>

Access Token hết hạn (401)
  POST /auth/refresh { refreshToken } → { accessToken mới, refreshToken mới }
```

Mỗi request đi qua `JwtFilter` — filter verify chữ ký, kiểm tra hết hạn, kiểm tra loại token (`access`/`refresh`), sau đó set `SecurityContext` để Spring Security xử lý phân quyền theo role.

---

## Cấu trúc project

```
src/main/java/com/example/fashionshop/
├── config/
│   ├── JwtFilter.java          # Filter xác thực JWT
│   └── SecurityConfig.java     # Cấu hình Spring Security + CORS
├── controller/                 # REST Controllers
├── dto/
│   ├── request/                # Request DTOs
│   ├── response/               # Response DTOs
│   └── entity/                 # Entity DTOs
├── entity/                     # JPA Entities
├── exception/                  # Custom Exceptions
├── repository/                 # Spring Data JPA Repositories
├── service/
│   ├── impl/                   # Service Implementations
│   └── *.java                  # Service Interfaces
└── util/
    ├── JwtUtil.java            # Tạo / verify JWT
    └── DTOMapper.java          # Entity ↔ DTO mapping
```

---

## Frontend Repository

https://github.com/vnkienHP2808/Web-fashion-shop