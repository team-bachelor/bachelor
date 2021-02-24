## bachelor快速开发框架

### 目录介绍

#### 1. bachelor-dependencies
&emsp;管理框架中用到的所有引用。

#### 2. bachelor-fw
&emsp;核心框架。

##### &emsp;1) bachelor-fw-core
&emsp;&emsp;核心框架核心组件，包括错误处理机制，上下文处理机制，核心事件处理等。

##### &emsp;2) bachelor-fw-web
&emsp;&emsp;核心框架WEB支持，包括国际化支持，web上下文处理机制，错误处理，json处理，翻页，面向用户的消息处理。

#### 3. bachelor-fw-ext
&emsp;核心框架扩展，即技术类扩展。

##### &emsp;1) bachelor-fw-cache-redis
&emsp;&emsp;redis扩展。

#### 4. bachelor-iam
&emsp;框架iam组件

##### &emsp;1) bachelor-oauth2
&emsp;&emsp;实现了OAuth2协议的登录支持和接口调用的规范与默认实现。

##### &emsp;2) bachelor-acm
&emsp;&emsp;系统访问管理和访问控制模块（权限管理），包括了基于角色的权限管理模块和JWT相关功能，访问控制功能，及前端页面样例和与默认用户平台交互的服务层组件。

#### 5. bachelor-ms
&emsp;微服务架构体系实例,其中注册中心和网关经过改造。注册中心增加了可以显示swagger-ui的功能；网关增加了JWT解析功能。

#### 6. bachelor-demo
&emsp;基于该框架的各种实例（待更新）。

### 配置说明

#### &emsp;1) web工程配置
| 配置路径 | 数据类型 | 配置说明 | 默认值 |
| --- | --- | --- | --- |
| bachelor.swagger.base-packages | String[] | swagger扫描的基础包 |  |
| bachelor.swagger.description | String | 模块描述 |  |
| bachelor.swagger.title | String | swagger页面标题 |  |
| bachelor.swagger.version | String | API版本 |  |

#### &emsp;2) iam通用配置
| 配置路径 | 数据类型 | 配置说明 | 默认值 |
| --- | --- | --- | --- |
| bachelor.iam.enable-user-access-control | Boolean | 是否对当前登录用户鉴权 | true |
| bachelor.iam.enable-user-identify | Boolean | 是否获取当前用户信息 | true |

#### &emsp;3) idm与用户平台交互配置
| 配置路径 | 数据类型 | 配置说明 | 默认值 |
| --- | --- | --- | --- |
| bachelor.iam.client.as-url.access-token | String | 获取token的接口地址 | /accessToken |
| bachelor.iam.client.as-url.authorize | String | 授权的接口地址 | /authorize |
| bachelor.iam.client.as-url.base | String | 认证服务的根接口地址 |  |
| bachelor.iam.client.as-url.logout | String | 登出的接口地址 | /logout |
| bachelor.iam.client.as-url.user-info | String | 获取用户基本信息的接口地址 | /userInfo |
| bachelor.iam.client.id | String | 用户平台中的client_id |  |
| bachelor.iam.client.login-filter-enable | Boolean | 是否启用登录过滤器(过滤未登录的请求) | true |
| bachelor.iam.client.login-redirect-url | String | 登录的重定向回调地址 |  |
| bachelor.iam.client.logout-redirect-url | String | 登出的重定向回调地址 |  |
| bachelor.iam.client.rs-url.app | String | 获取应用信息的接口地址 | /app |
| bachelor.iam.client.rs-url.apps-by-user-id | String | 获取用户可访问的应用的接口地址 | /users/authorizedApp |
| bachelor.iam.client.rs-url.base | String | 资源服务根地址 |  |
| bachelor.iam.client.rs-url.dept-details | String | 获取部门详细信息的接口地址 | /deptDetails |
| bachelor.iam.client.rs-url.depts | String | 获取部门信息的接口地址 | /depts |
| bachelor.iam.client.rs-url.mt-user-roles | String | 获取用户角色信息的接口地址 | /mtUserRoles |
| bachelor.iam.client.rs-url.orgs | String | 获取组织结构信息的接口地址 | /orgs |
| bachelor.iam.client.rs-url.user-by-client-i-d | String | 获取可访问应用的全部用户的接口地址 | /users/authorizeByApp |
| bachelor.iam.client.rs-url.user-by-ids | String | 根据用户id获取用户信息的接口地址  | ../userapi/user/getUserByIds |
| bachelor.iam.client.rs-url.user-details | String | 获取用户详细信息的接口地址  | /user_details |
| bachelor.iam.client.rs-url.user-info-detail | String | 获取用户详细信息的接口地址  | /userInfoDetail |
| bachelor.iam.client.rs-url.user-role | String | 获取用户角色信息的接口地址  | /userRoles |
| bachelor.iam.client.rs-url.users | String | 获取用户信息的接口地址  | /users |
| bachelor.iam.client.secret | String | 用户平台中的client_secret  |  |
| bachelor.iam.client.target-url | String | 成功获取astoken后的回调地址，仅跳转  |  |
| bachelor.iam.client.to-login-redirect-url | Boolean | 认证失败时是否导向到登录的重定向地址 | true |

