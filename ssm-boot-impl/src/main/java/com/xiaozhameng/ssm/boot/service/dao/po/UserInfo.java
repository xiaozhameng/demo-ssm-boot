/**    
 * <p> xiaozhameng</p>
 * <p>All Rights Reserved. 保留所有权利. </p>
 */

package com.xiaozhameng.ssm.boot.service.dao.po;

import lombok.Data;

/**
 * @author xiaozhameng
 */
@Data
public class UserInfo {

    /** id */
    private Long id;

    /** 用户ID */
    private String userId;

    /** 用户姓名 */
    private String userName;

    /** 昵称 */
    private String nickName;

    /** 邮箱 */
    private String email;

    /** 性别 */
    private String sex;

    /** 手机号 */
    private String mobile;

}
