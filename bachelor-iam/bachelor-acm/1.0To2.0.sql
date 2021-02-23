ALTER TABLE cmn_auth_menu RENAME TO cmn_acm_menu;
ALTER TABLE cmn_auth_obj_domain RENAME TO cmn_acm_obj_domain;
ALTER TABLE cmn_auth_obj_operation RENAME TO cmn_acm_obj_operation;
ALTER TABLE cmn_auth_objects RENAME TO cmn_acm_obj_permission;
ALTER TABLE cmn_auth_org_menu RENAME TO cmn_acm_org_menu;
ALTER TABLE cmn_auth_org_permission RENAME TO cmn_acm_org_permission;
ALTER TABLE cmn_auth_role RENAME TO cmn_acm_role;
ALTER TABLE cmn_auth_role_menu RENAME TO cmn_acm_role_menu;
ALTER TABLE cmn_auth_role_permission RENAME TO cmn_acm_role_permission;
ALTER TABLE cmn_auth_user_menu RENAME TO cmn_acm_user_menu;
ALTER TABLE cmn_auth_user_permission RENAME TO cmn_acm_user_permission;
ALTER TABLE cmn_auth_user_role RENAME TO cmn_acm_user_role;

ALTER TABLE cmn_acm_obj_domain ADD COLUMN `IS_SYS` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否为系统默认' AFTER `CODE`;
ALTER TABLE cmn_acm_obj_permission ADD COLUMN `IS_SYS` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否为系统默认' AFTER `DOMAIN_CODE`;
ALTER TABLE cmn_acm_obj_permission ADD COLUMN `SERVE_FOR` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否为系统默认' AFTER `DOMAIN_CODE`;
-- ALTER TABLE cmn_acm_menu ADD COLUMN `COMPONENT` varchar(255) NULL DEFAULT NULL COMMENT '组件位置' AFTER `URI`;