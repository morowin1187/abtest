package com.aiccj.abtest.model;

import com.aiccj.abtest.common.Constant;
import com.aiccj.abtest.common.Password;
import com.aiccj.abtest.common.exception.BusinessException;
import com.aiccj.abtest.common.exception.BusinessMsg;
import com.aiccj.abtest.common.token.LoginType;
import jodd.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author morowin
 * @Date 2022/4/18 22:44
 */
@Data
@Slf4j
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String name;

    private String email;

    private String password;

    private String phone;

    private String role;

    private String loginType;

    public void checkLoginParam() {
        if (StringUtil.isBlank(this.getEmail())) {
            throw new BusinessException(BusinessMsg.MCLC0001);
        }
    }

    public void validatePassword(User dbResp) {
        if (dbResp == null)
            throw new BusinessException(BusinessMsg.LOGIN_NOT_EXIST);
        String encrypt = Password.encrypt(this.getPassword());


        boolean equals = encrypt.equals(dbResp.getPassword());
        if (!equals) {
            throw new BusinessException(BusinessMsg.MCLC0002);
        }
    }

    public void checkCreate() {
        //参数自检
        if (StringUtil.isBlank(email))
            throw new BusinessException(BusinessMsg.MCLC0003);
        if (StringUtil.isBlank(password))
            throw new BusinessException(BusinessMsg.MCLC0004);
        return;
    }

    public void createForAdmin() {
        this.setRole(Constant.ROLE_USER);
        this.setLoginType(LoginType.SINGLE.name());
        this.create();
    }

    /**
     * 注册调用
     */
    public void createForUser() {
        //待扩展
        this.create();
    }

    private void create() {
        this.id = null;
        this.password = Password.encrypt(password);
    }

    public void editForAdmin() {
        this.edit();
    }

    private void edit() {
        this.setPassword(Password.encrypt(password));
    }
}
