package com.vsm.business.common;

public interface AppConstant {
    public interface Message {
        public static final String CREATED = "Thêm mới thành công";
        public static final String UPDATED = "Cập nhật thành công";
        public static final String DELETED = "Đã thực hiện xóa thành công";
        public static final String LOADED = "Tải dữ liệu thành công";
    }

    public interface Error {
        public static final String CREATE_FAIL = "Thêm mới thất bại";
        public static final String UPDATE_FAIL = "Cập nhật thất bại";
        public static final String DELETE_FAIL = "Thực hiện xóa thất bại";
        public static final String LOAD_FAIL = "Tải dữ liệu thất bại";
        public static final String EXIST_ID = "Đã tồn tại Id";
        public static final String MISSING_PARAMETER = "Thiếu tham số";
        public static final String DID_NOT_EXIST = "Không tồn tại dữ liệu";
        public static final String MISSING_INFORMATION = "Thiếu thông tin";
        public static final String MATCHING_FAIL = "Thông tin không trùng khớp";
        public static final String USERNAME_NOT_FOUND = "Tài khoản không tồn tại";

        public interface ErrorCode {
            public static final String FILE_NOT_FOUND = "0x01";
            public static final String SIGN = "0x02";
        }

    }

    public interface FileConstant {
        public static final String UNSAFE_SYMBOL_REPLACEMENT = "_";
    }

    public interface RequestGroupConstant {
        public static final String CONG_VAN_DEN = "Công văn đến";
        public static final String TO_TRINH = "Tờ trình";
    }
}
