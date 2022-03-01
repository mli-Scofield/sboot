SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
CREATE TABLE `pc_authority` (
                                `id` bigint NOT NULL COMMENT 'id',
                                `pc_authority_belong_id` bigint DEFAULT NULL COMMENT '权限模块id',
                                `pc_authority_belong_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限模块name',
                                `pc_authority_datatype` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限类型(0：观察者，1:管理员，2：品质审核，3：签收人员，4：erp录入，5：抄送人员，6：班组，7:生产计划管理者。8：生产部计时申请人)',
                                `pc_authority_users_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '权限用户组id',
                                `pc_authority_users_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '权限用户组name',
                                `pc_authority_modify_time` timestamp NULL DEFAULT NULL COMMENT '权限修改时间',
                                `pc_authority_data` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限模块数据(type=6为月计划生产量)',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;


CREATE TABLE `sb_user` (
                           `id` int unsigned NOT NULL AUTO_INCREMENT,
                           `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                           `passwd` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                           `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                           `created_at` timestamp NULL DEFAULT NULL,
                           `updated_at` timestamp NULL DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `sys_menu` (
                            `menu_id` bigint NOT NULL AUTO_INCREMENT,
                            `parent_id` bigint DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
                            `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单名称',
                            `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单URL',
                            `perms` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
                            `type` int DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
                            `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单图标',
                            `order_num` int DEFAULT NULL COMMENT '排序',
                            `module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限所属项目',
                            PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1495930030517932035 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='菜单管理';

CREATE TABLE `sys_role` (
                            `role_id` bigint NOT NULL AUTO_INCREMENT,
                            `role_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色名称',
                            `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
                            `create_user_id` bigint DEFAULT NULL COMMENT '创建者ID',
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色所属项目',
                            `flag` int DEFAULT '1' COMMENT '1：存在 0：删除',
                            PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1481889624213950467 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色';

CREATE TABLE `sys_role_menu` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `role_id` bigint DEFAULT NULL COMMENT '角色ID',
                                 `menu_id` bigint DEFAULT NULL COMMENT '菜单ID',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1494213085510803462 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色与菜单对应关系';